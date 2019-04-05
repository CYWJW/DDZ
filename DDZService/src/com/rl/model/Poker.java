package com.rl.model;

public class Poker {
	private Integer id;
	private String name;
	//扑克大小
	private Integer num;
	
	//是否打出
	private Boolean ifOut;
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
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Boolean getIfOut() {
		return ifOut;
	}
	public void setIfOut(Boolean ifOut) {
		this.ifOut = ifOut;
	}
	@Override
	public String toString() {
		return "Poker [id=" + id + ", name=" + name + ", num=" + num + ", ifOut=" + ifOut + "]";
	}
	

}
