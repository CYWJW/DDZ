package com.rl.accept;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rl.dao.PlayerDao;
import com.rl.dao.PokerDao;
import com.rl.dao.impl.PlayerDaoImpl;
import com.rl.dao.impl.PokerDaoImpl;
import com.rl.model.Player;
import com.rl.model.Poker;
import com.rl.utils.Message;

public class AcceptServer {
	private List<Player>players=new ArrayList<>();
	private List<Poker>pokers=new ArrayList<>();
	//存底牌
	private List<Poker>lordPokers=new ArrayList<>();
	private PokerDao pokerDao=new PokerDaoImpl();
	private PlayerDao playerDao=new PlayerDaoImpl();
	private Integer	count=0;
	//共同步骤
	private int	step=0;
	//设置状态
	//private boolean flag;
	public AcceptServer()
	{
			//初始化牌
			initPoker();
			try {
				//创建服务器连接
				ServerSocket serverSocket=new ServerSocket(8888);
				System.out.println("服务器启动");
				while(true)
				{
					//设置状态
					//boolean flag=false;
					//监听端口连接 每连接一次都会创建一个socket实例
					Socket socket=	serverSocket.accept();
					AcceptThread aThread=new AcceptThread(socket);
					aThread.start();
				
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	//上线接受消息
	class AcceptThread extends Thread
	{
		private Socket socket;
		public AcceptThread(Socket socket) {
			this.socket=socket;
		}
		@Override
		public void run() {
			try {
				//数据读取流
				DataInputStream reader=new DataInputStream(socket.getInputStream());
				while(true)
				{
					String msg=reader.readUTF();
					if(step==0)
					{
						
						//添加在线人数
						Player player=new Player();
						String maString[]=	msg.split(",");
						player.setName(maString[0]);
						player.setPassword(maString[1]);
						player.setSocket(socket);
						List<Player>playerList=	playerDao.validataLogin(player);
						//进行数据库判断
						if(playerList!=null && playerList.size()>0){
								System.out.println(player.getName()+":上线了");
								player.setId(count++);
									players.add(player);
									System.out.println("当前在线人数"+players.size());
									DataOutputStream wStream=new DataOutputStream(player.getSocket().getOutputStream());
									wStream.writeUTF("yes");
									//如果人数到齐 发牌
									if(players.size()==3) {
										
										allotPoker();
										 step=1;
									}
						}
						else {
							DataOutputStream wStream=new DataOutputStream(player.getSocket().getOutputStream());
							wStream.writeUTF("no");
						}
					}
					//如果人数到齐下面接受的是抢地主消息
					else if(step==1)
					{
						System.out.println("接收抢地主的消息"+msg);
					
						JSONObject msgJsonObject=  JSON.parseObject(msg); 
					
						int typeid=msgJsonObject.getInteger("typeid");
						
					    int playerid=msgJsonObject.getInteger("playerid");
					    
					    String content=msgJsonObject.getString("content");
				    					    
					    //抢地主
					    if(typeid==2)
					    {
				    	//重新组将一个 消息对象 ，添加地主牌
				    	Message sendMessage=new Message(typeid,playerid,content,lordPokers);
				    	
				    	msg=JSON.toJSONString(sendMessage);		
				    	
				    	step=2;					    	
					    }
			    
					    //不抢 将客户端发过来的不抢的信息 原样群发到所有玩家
			    
					    sendMessageToClient(msg);	
					}
					else if(step==2) //出牌和不出牌和游戏结束
					{
						sendMessageToClient(msg); //转发到所有的客户端
						System.out.println(msg);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	//群发消息到客户端
	public void sendMessageToClient(String msg)
	{
		for(int i=0;i<players.size();i++)
		{
			   DataOutputStream dataOutputStream;
			try {
				dataOutputStream = new DataOutputStream(players.get(i).getSocket().getOutputStream());
				 dataOutputStream.writeUTF(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	//初始化扑克牌
	public void initPoker()
	{
		pokers=pokerDao.getAllPoker();
		
	}
	//发牌
	public void allotPoker()
	{
		//打乱集合(洗牌)
		Collections.shuffle(pokers);
		for(int i =0;i<pokers.size();i++)
		{
			//如果最后三张
			if(i>50)
			{
				lordPokers.add(pokers.get(i));
			}
			else {
				//轮流给三个人发牌
				players.get(i%3).getPokels().add(pokers.get(i));
				
			}
			
		}
		//发送玩家信息到客户端		
		
		//把玩家信息转换为json数组
		for (int i = 0; i < players.size(); i++) {
			String jsonMag=JSONArray.toJSONString(players);
			//发送到自己的客户端
			try {
				DataOutputStream write=new DataOutputStream(players.get(i).getSocket().getOutputStream());
				write.writeUTF(jsonMag);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
}
