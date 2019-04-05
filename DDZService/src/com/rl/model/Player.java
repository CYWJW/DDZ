package com.rl.model;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Player {
	private Integer id;
	
	private String name;
	
	private String password;
	
	private Integer playId;
	
	private String totalMoney;
	
	private List<Poker>pokels=new ArrayList<>();
	
	private Socket socket;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Poker> getPokels() {
		return pokels;
	}
	public void setPokels(List<Poker> pokels) {
		this.pokels = pokels;
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}
	public Integer getPlayId() {
		return playId;
	}
	public void setPlayId(Integer playId) {
		this.playId = playId;
	}
	
	
	
}
