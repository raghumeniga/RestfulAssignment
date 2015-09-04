package com.assignment.restful.model;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.assignment.restful.other.Constant;
import com.assignment.restful.other.Queries;

public class EventDAO {
	
	private Event event;
	
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	
	/*
	 * EventDAO Constructer : Check the db file present or not; if not exists create.
	 */
	public EventDAO(){
		try{
		Class.forName(Constant.DB_DRIVER_CLASS);
		File dbFile=new File(Constant.DB_FILE_PATH);
	 	if(!dbFile.exists())
	 	{
	 		createDB();
	 	}
		}catch(Exception e){}
	 	
	 }
	
	/*
	 * Create the event table
	 */
	public int createDB()
	 {
		
		Connection con = null;
	 	PreparedStatement stmt = null;
	 	int res=0;
	 	try
	 		{
	 			con = DriverManager.getConnection(Constant.DB_URL);
	 			stmt = con.prepareStatement(Queries.CREATE_EVENT_TABLE);
	 			res=stmt.executeUpdate();
	 			stmt.close();
	 			con.close();
	 			
	 		}catch(Exception e)
	 		{
	 			
	 			
	 		}finally
	 		{  try{
	 			if (stmt != null ) {
	 				stmt.close();
	 			} 
	 			if (con != null ) {
	 				con.close();
	 			}
	 		 }catch(Exception ex){}
	 		}
	 	return res;

	 }
	
	/*
	 * Insert the event into the table
	 */
	public int insertEvent(){
		Connection con = null;
	 	PreparedStatement stmt = null;
	 	int res=0;
	 	ResultSet rset=null;
	 	try
	 		{
	 			con = DriverManager.getConnection(Constant.DB_URL);
	 			stmt = con.prepareStatement(Queries.INSERT_EVENT);
	 			stmt.setString(1, this.event.getData());
	 			res=stmt.executeUpdate();
	 			stmt.close();
	 			stmt=con.prepareStatement(Queries.GET_LAST_INSERT_ROW_ID);
	 			rset=stmt.executeQuery();
	 			this.event.setId(rset.getInt("id"));
	 			rset.close();
	 			stmt.close();
	 			con.close();
	 			
	 		}catch(Exception e)
	 		{}finally
	 		{  try{
	 			if(rset != null){
	 				rset.close();
	 			}
	 			if (stmt != null ) {
	 				stmt.close();
	 			} 
	 			if (con != null ) {
	 				con.close();
	 			}
	 		 }catch(Exception ex){}
	 		}
	 	return res;
	}
	
	/*
	 * Update the event in the table
	 */
	public int updateEvent(){
		Connection con = null;
	 	PreparedStatement stmt = null;
	 	int res=0;
	 	try
	 		{
	 			con = DriverManager.getConnection(Constant.DB_URL);
	 			stmt = con.prepareStatement(Queries.UPDATE_EVENT);
	 			stmt.setString(1, this.event.getData());
	 			stmt.setInt(2, this.event.getId());
	 			res=stmt.executeUpdate();
	 			stmt.close();
	 			con.close();
	 			
	 		}catch(Exception e)
	 		{
	 			
	 			
	 		}finally
	 		{  try{
	 			if (stmt != null ) {
	 				stmt.close();
	 			} 
	 			if (con != null ) {
	 				con.close();
	 			}
	 		 }catch(Exception ex){}
	 		}
	 	return res;
	}
	
	/*
	 * Delete the event from the table
	 */
	public int deleteEvent(int id){
		Connection con = null;
	 	PreparedStatement stmt = null;
	 	int res=0;
	 	try
	 		{
	 			con = DriverManager.getConnection(Constant.DB_URL);
	 			stmt = con.prepareStatement(Queries.DELETE_EVENT);
	 			stmt.setInt(1, id);
	 			res=stmt.executeUpdate();
	 			stmt.close();
	 			con.close();
	 			
	 		}catch(Exception e)
	 		{}finally
	 		{  try{
	 			if (stmt != null ) {
	 				stmt.close();
	 			} 
	 			if (con != null ) {
	 				con.close();
	 			}
	 		 }catch(Exception ex){}
	 		}
	 	return res;
	}
	
	/*
	 * Delete all the events from the table
	 */
	public int deleteAllEvents(){
		Connection con = null;
	 	PreparedStatement stmt = null;
	 	int res=0;
	 	try
	 		{
	 			con = DriverManager.getConnection(Constant.DB_URL);
	 			stmt = con.prepareStatement(Queries.DELETE_ALL_EVENTS);
	 			res=stmt.executeUpdate();
	 			stmt.close();
	 			con.close();
	 			
	 		}catch(Exception e)
	 		{}finally
	 		{  try{
	 			if (stmt != null ) {
	 				stmt.close();
	 			} 
	 			if (con != null ) {
	 				con.close();
	 			}
	 		 }catch(Exception ex){}
	 		}
	 	return res;
	}
	
	/*
	 * List all the events from the table
	 */
	public ArrayList<Event> listAllEvents(){
		Connection con = null;
	 	PreparedStatement stmt = null;
	 	ResultSet rset=null;
	 	ArrayList<Event> result=new ArrayList<Event>();
	 	try
	 		{
	 			con = DriverManager.getConnection(Constant.DB_URL);
	 			stmt = con.prepareStatement(Queries.LIST_ALL_EVENTS);
	 			rset=stmt.executeQuery();
	 			while(rset.next()){
	 				result.add(new Event(rset.getInt("id"),rset.getString("data")));
	 			}
	 			stmt.close();
	 			con.close();
	 			
	 		}catch(Exception e)
	 		{}finally
	 		{  try{
	 			if (stmt != null ) {
	 				stmt.close();
	 			} 
	 			if (con != null ) {
	 				con.close();
	 			}
	 		 }catch(Exception ex){}
	 		}
	 	return result;
	}
	
	/*
	 * get event from the table
	 */
	public Event selectEvent(int id){
		Connection con = null;
	 	PreparedStatement stmt = null;
	 	ResultSet rset=null;
	 	Event event=null;
	 	try
	 		{
	 			con = DriverManager.getConnection(Constant.DB_URL);
	 			stmt = con.prepareStatement(Queries.GET_EVENT);
	 			stmt.setInt(1, id);
	 			rset=stmt.executeQuery();
	 			if(rset.next()){
	 				event=new Event(rset.getInt("id"),rset.getString("data"));
	 				
	 			}
	 			stmt.close();
	 			con.close();
	 			
	 		}catch(Exception e)
	 		{}finally
	 		{  try{
	 			if (stmt != null ) {
	 				stmt.close();
	 			} 
	 			if (con != null ) {
	 				con.close();
	 			}
	 		 }catch(Exception ex){}
	 		}
	 	return event;
	}

}
