package com.assignment.restful.model;

import org.json.JSONObject;

public class Event {
	
	private int id;
	private String data;
	
	public Event(int id,String data){
		this.id=id;
		this.data=data;
	}
	
	public Event(String data){
		this.data=data;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	public JSONObject toJSON(){
		try{
		JSONObject out=new JSONObject();
		out.put("id", this.id);
		out.put("data", this.data);
		return out;
		}catch(Exception e){return null;}
	}

}
