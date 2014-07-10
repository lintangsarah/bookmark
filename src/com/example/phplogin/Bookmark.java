package com.example.phplogin;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Bookmark {
	public String id;
	public String update_at;
	public String commenter_name;
	public String message;
	//public String Expired;

	
	public Bookmark(String message, String commenter_name, String update_at, String id) {
		super();
		this.update_at = update_at;
		this.message = message;
		this.commenter_name = commenter_name;
		this.id = id;
		//this.Expired = new Date(getExpired()).toString();
		

	}
	
	public String getid() {
		return id;
	}
	
	public String getupdate_at() {
		return update_at;
	}
	public String getmessage() {
		return message;
	}
	public String getcommenter_name() {
		return commenter_name;
	}
	
	public long getExpired(){
		String updatedAt = getupdate_at();
		String modifiedUpdatedAt = updatedAt.replace("T", " ");
		String modifiedUpdatedAt2 = modifiedUpdatedAt.replace("Z", "");
		System.out.println("updateAT : " + modifiedUpdatedAt2);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date dt = new Date();
		long lama = -1;
		long Expired = -1;
		try {
			
			Date tgl = sdf.parse(modifiedUpdatedAt2);
			System.out.println("parse "+tgl);
			//Date dt = sdf.parse(dt);
			//Date c1 = (Date) sdf.parse(c1);
			//int days = Days.daysBetween(tgl, dt).getDays();
			lama = (int) Math.round((dt.getTime() - tgl.getTime()) / (double) 86400000);
			Expired = 6 - lama;
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Expired;
		
		
	}

	

	

}
	
	
	
