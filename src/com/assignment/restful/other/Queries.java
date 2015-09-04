package com.assignment.restful.other;

public class Queries {

	
	
	public static final String CREATE_EVENT_TABLE		=		"CREATE TABLE if not exists event (id INTEGER primary key autoincrement,data text not null)";
	
	public static final String INSERT_EVENT				=		"INSERT INTO event (data) VALUES (?)";
	
	public static final String DELETE_EVENT				=		"DELETE FROM event WHERE id=?";
	
	public static final String UPDATE_EVENT				=		"UPDATE event set data=? WHERE id=?";
	
	public static final String DELETE_ALL_EVENTS		=		"DELETE FROM event WHERE 1";
	
	public static final String LIST_ALL_EVENTS			=		"SELECT id,data FROM event";
	
	public static final String GET_EVENT				=		"SELECT id,data FROM event WHERE id=?";
	
	public static final String GET_LAST_INSERT_ROW_ID	=		"SELECT last_insert_rowid() as id";
	
	
	
}
