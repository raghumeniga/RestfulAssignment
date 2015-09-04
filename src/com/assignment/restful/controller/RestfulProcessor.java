package com.assignment.restful.controller;

/**
 * @author Raghavendra Prasad
 * 
 * This controller is responsible to handle all requests that are related to events..
 */

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.assignment.restful.model.Event;
import com.assignment.restful.model.EventDAO;
import com.assignment.restful.other.Codes;
import com.assignment.restful.other.Messages;


@Controller
public class RestfulProcessor {

	/*
	 * 
	 * This method is to process the /events request
	 * 
	 * Usage: curl -X POST "http://<application_host>/events" -H "Content-Type: application/json" -d '{"data":"NAME is now at LATITUDE/LONGITUDE"}'
	 * 
	 */
	@RequestMapping(value="/events", 
			 		method = RequestMethod.POST,
			 		consumes = MediaType.APPLICATION_JSON_VALUE
			 		)
	@ResponseBody
	public String eventsPost(@RequestBody String requestBody) {
		JSONObject output=new JSONObject();
		try{
			JSONObject inputObj=new JSONObject(requestBody);
			if(!inputObj.isNull("data")){
				
				Event event=new Event(inputObj.getString("data"));
				EventDAO dao=new EventDAO();
				dao.setEvent(event);
				if(dao.insertEvent()>0){
					output.put("status_code", Codes.SUCCESS);
					output.put("description", Messages.EVENT_PROCESS);
					output.put("event_info", event.toJSON());
				}
				
				
			}else{
				output.put("status_code", Codes.ERROR_NO_DATA);
				output.put("description", Messages.NO_DATA_ATTR);
			}
		}catch(Exception e){
			try{
			output.put("status_code", Codes.INVALID_JSON);
			output.put("description", Messages.INVALID_JSON);
			}catch(Exception ee){}
		}
		return  output.toString();
		
	}
	
	
	/*
	 * 
	 * This method is to get all the events
	 * 
	 * Usage: curl "http://<application_host>/events"
	 * 
	 */
	@RequestMapping(value="/events", 
			 		method = RequestMethod.GET
			 		)
	@ResponseBody
	public String eventsGet() {
		JSONObject output=new JSONObject();
		try{
			
			EventDAO dao=new EventDAO();
			ArrayList<Event> result=new ArrayList<Event>();
			result=dao.listAllEvents();
			output.put("status_code", Codes.SUCCESS);
			output.put("description", Messages.REQ_PROCESS);
			JSONArray events=new JSONArray();
			for(int i=0;i<result.size();i++){
				Event event=result.get(i);
				events.put(i,event.toJSON());
			}
			output.put("event_info",events);
			
			
		}catch(Exception e){
			try{
			output.put("status_code", Codes.INTERNAL_SERVER_ERROR);
			output.put("description",Messages.INTERNAL_SERVER_ERR);
			}catch(Exception ee){}
		}
		return  output.toString();
		
	}
	
	
	/*
	 * 
	 * This method is to delete all the events
	 * 
	 * Usage: curl -X DELETE "http://<application_host>/events"
	 * 
	 */
	@RequestMapping(value="/events", 
			 		method = RequestMethod.DELETE
			 		)
	@ResponseBody
	public String eventsDelete() {
		JSONObject output=new JSONObject();
		try{
			
			EventDAO dao=new EventDAO();
			dao.deleteAllEvents();
			output.put("status_code", Codes.SUCCESS);
			output.put("description", Messages.DELETE_ALL);
			
			
		}catch(Exception e){
			try{
			output.put("status_code", Codes.INTERNAL_SERVER_ERROR);
			output.put("description", Messages.INTERNAL_SERVER_ERR);
			}catch(Exception ee){}
		}
		return  output.toString();
		
	}
	
	
	
	/*
	 * 
	 * This method is to get the particular event info
	 * 
	 * Usage: curl "http://<application_host>/event/{id}"
	 * 
	 */
	@RequestMapping(value="/events/{id}", 
			 		method = RequestMethod.GET
			 		)
	@ResponseBody
	public String eventGet(@PathVariable("id") int id) {
		JSONObject output=new JSONObject();
		try{
			if(id>0){
				
				EventDAO dao=new EventDAO();
				Event event=dao.selectEvent(id);
				if(event!=null){
					output.put("status_code", Codes.SUCCESS);
					output.put("description", Messages.REQ_PROCESS);
					output.put("event_info", event.toJSON());
				}else{
					output.put("status_code", Codes.INVALID_RESOURCE);
					output.put("description", Messages.INVALID_ID);
				}
				
			}else{
				try{
					output.put("status_code", Codes.INVALID_RESOURCE);
					output.put("description", Messages.INVALID_ID);
					}catch(Exception ee){}
			}
			
		}catch(Exception e){
			try{
			output.put("status_code", Codes.INTERNAL_SERVER_ERROR);
			output.put("description", Messages.INTERNAL_SERVER_ERR);
			}catch(Exception ee){}
		}
		return  output.toString();
		
	}
	
	/*
	 * 
	 * This method is to update the existing event info
	 * 
	 * Usage: curl -X PUT "http://<application_host>/events/{id}" -H "Content-Type: application/json" -d '{"data":"NAME is now at LATITUDE/LONGITUDE"}'
	 * 
	 */
	@RequestMapping(value="/events/{id}", 
			 		method = RequestMethod.PUT,
			 		consumes = MediaType.APPLICATION_JSON_VALUE
			 		)
	@ResponseBody
	public String eventUpdate(@PathVariable("id") int id,@RequestBody String requestBody) {
		JSONObject output=new JSONObject();
		try{
			if(id>0){
				JSONObject inputObj=new JSONObject(requestBody);
				if(!inputObj.isNull("data")){
					Event event=new Event(id,inputObj.getString("data"));
					EventDAO dao=new EventDAO();
					dao.setEvent(event);
					int res=dao.updateEvent();
					if(res>0){
						output.put("status_code", Codes.SUCCESS);
						output.put("description", Messages.REQ_PROCESS);
						output.put("event_info", event.toJSON());
					}else{
						output.put("status_code", Codes.INVALID_RESOURCE);
						output.put("description", Messages.INVALID_ID);
					}
				}else{
					output.put("status_code", Codes.ERROR_NO_DATA);
					output.put("description", Messages.NO_DATA_ATTR);
				}
				
				
				EventDAO dao=new EventDAO();
				Event event=dao.selectEvent(id);
				if(event!=null){
					output.put("status_code", Codes.SUCCESS);
					output.put("description", Messages.REQ_PROCESS);
					output.put("event_info", event.toJSON());
				}else{
					output.put("status_code", Codes.INVALID_RESOURCE);
					output.put("description", Messages.INVALID_ID);
				}
				
			}else{
				try{
					output.put("status_code", Codes.INVALID_RESOURCE);
					output.put("description", Messages.INVALID_ID);
					}catch(Exception ee){}
			}
			
		}catch(Exception e){
			try{
			output.put("status_code", Codes.INTERNAL_SERVER_ERROR);
			output.put("description", Messages.INTERNAL_SERVER_ERR);
			}catch(Exception ee){}
		}
		return  output.toString();
		
	}
	
	
	
	/*
	 * 
	 * This method is to get the particular event info
	 * 
	 * Usage: curl -X DELETE "http://<application_host>/event/{id}"
	 * 
	 */
	@RequestMapping(value="/events/{id}", 
			 		method = RequestMethod.DELETE
			 		)
	@ResponseBody
	public String eventDelete(@PathVariable("id") int id) {
		JSONObject output=new JSONObject();
		try{
			if(id>0){
				
				EventDAO dao=new EventDAO();
				int res=dao.deleteEvent(id);
				if(res>0){
					output.put("status_code", Codes.SUCCESS);
					output.put("description", Messages.DELETE_EVENT);
				}else{
					output.put("status_code", Codes.INVALID_RESOURCE);
					output.put("description", Messages.INVALID_ID);
				}
				
			}else{
				try{
					output.put("status_code", Codes.INVALID_RESOURCE);
					output.put("description", Messages.INVALID_ID);
					}catch(Exception ee){}
			}
			
		}catch(Exception e){
			try{
			output.put("status_code", Codes.INTERNAL_SERVER_ERROR);
			output.put("description", Messages.INTERNAL_SERVER_ERR);
			}catch(Exception ee){}
		}
		return  output.toString();
		
	}
	
	
}
