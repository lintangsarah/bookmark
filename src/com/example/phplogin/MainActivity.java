package com.example.phplogin;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;
//import android.support.v7.internal.widget.AdapterViewICS.OnItemLongClickListener;
//import com.example.eventapp.Bookmark;
//import com.example.eventapp.EventAdapter;

public class MainActivity extends Activity {
	protected static final int arg = 0;
	protected static final Bookmark b = null;
	private ListView ListViewBookmark;
	private ArrayList<Bookmark> listBookmark = new ArrayList<Bookmark>();
	private BookmarkAdapter bookmarkAdapter;
	private String myToken;
	private String id;
	//Button delete;
	//protected Bookmark b;
	//Button delete;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmark);
        myToken = getIntent().getStringExtra("token");
        getOverflowMenu();
        ListViewBookmark=(ListView)findViewById(R.id.listView1);
        bookmarkAdapter = new BookmarkAdapter(this, R.layout.item_bookmark, listBookmark);
        ListViewBookmark.setAdapter(bookmarkAdapter);
        ListViewBookmark.setOnItemLongClickListener(new OnItemLongClickListener() {
        	public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				Bookmark b = (Bookmark) ListViewBookmark.getItemAtPosition(arg2);
				//Toast.makeText(MainActivity.this, b.message, Toast.LENGTH_LONG).show();
				longclick(b);
				return true;
        	}	
        });
        ListViewBookmark=(ListView)findViewById(R.id.listView1);
        ListViewBookmark.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
				// TODO Auto-generated method stub
				Bookmark c = (Bookmark) ListViewBookmark.getItemAtPosition(arg2);
				Toast.makeText(MainActivity.this, c.message, Toast.LENGTH_LONG).show();
				deleteBookmark(c);
				return;
       	}	
        });
        
        //delete = (Button)findViewById(R.id.imageButton1);
        //delete.setOnClickListener(new OnClickListener() {
		//@Override
		//public void onClick(View delete) {
			//	Bookmark b = (Bookmark) ListViewBookmark.getItemAtPosition(delete);
				 //TODO Auto-generated method stub
				//deleteBookmark(b);}
			//});
        
        GetBookmark getEvent = new GetBookmark(){

			public void respon(String respons) {
				// TODO Auto-generated method stub
				System.out.println(respons);
				
				try {
					JSONArray myFiles = new JSONArray(respons);
					//JSONArray arrayEvent = objectEvent.getJSONArray("listEvent");
					
					for (int i = 0; i < myFiles.length(); i++) {
						JSONObject objectBookmark = myFiles.getJSONObject(i);
						String id = objectBookmark.getString("id");
						String update_at = objectBookmark.getString("updated_at");
						String message = objectBookmark.getString("message");
						String commenter_name = objectBookmark.getString("commenter_name");
						
						Bookmark singleBookmark = new Bookmark(message, commenter_name, update_at, id);
						listBookmark.add(singleBookmark);
						
					}
					
					bookmarkAdapter.notifyDataSetChanged(); //update data 
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
        	
        };
        getEvent.execute("https://www.qisc.us/api/v1/mobile/getBookmarks?token="+myToken);
        
    }
    
    public void deleteBookmark(final Bookmark c) 
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Bookmark")
        .setMessage("Want to delete your bookmark?")
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
       	 public void onClick(DialogInterface dialog, int which){
       		 
   			Toast.makeText(MainActivity.this, "Yes button pressed"+ delete(c),
   					
   					
       				Toast.LENGTH_SHORT).show();
   			refresh();
       		 //GoesToNextActivity(b.id);
       		 
       	 }
        })
        .setNegativeButton("No", null).show();
       }
	
	
    public String delete(Bookmark c){
    	ArrayList<NameValuePair> kirimkephp = new ArrayList<NameValuePair>();
		kirimkephp.add(new BasicNameValuePair("id", c.id
				.toString()));
		kirimkephp.add(new BasicNameValuePair("token", myToken
				.toString()));
    	
    	String deleteBookmark = null;
		try{
    	deleteBookmark = ClientToServer.eksekusiHttpPost("https://www.qisc.us/api/v1/mobile/deleteBookmark?", kirimkephp);
		JSONObject objectAuth = new JSONObject(deleteBookmark);
		
		} catch (Exception e){
    	e.printStackTrace();
		}
    	return deleteBookmark;
    	
    }
    
    public void longclick(final Bookmark b)
    {
     AlertDialog.Builder builder = new AlertDialog.Builder(this);
     builder.setTitle("Extend expiry date")
     .setMessage("Want to extend your e xpiry date?")
     .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
    	 public void onClick(DialogInterface dialog, int which){
    		 
			Toast.makeText(MainActivity.this, "Yes button pressed"+ respon(b),
					
					
    				Toast.LENGTH_SHORT).show();
			refresh();
    		 //GoesToNextActivity(b.id);
    		 
    	 }
     })
     .setNegativeButton("No", null).show();
    }
    
    public String respon(final Bookmark b){
    	ArrayList<NameValuePair> kirimkephp = new ArrayList<NameValuePair>();
		kirimkephp.add(new BasicNameValuePair("id", b.id
				.toString()));
		kirimkephp.add(new BasicNameValuePair("token", myToken
				.toString()));
    	String respon = null;
    	try{
    	respon = ClientToServer.eksekusiHttpPost("https://www.qisc.us/api/v1/mobile/addBookmark?", kirimkephp);
		JSONObject objectAuth = new JSONObject(respon);
		
		
		
	    } catch (Exception e){
	    	e.printStackTrace();
	    }
    	return respon;
    	
    	}
    public boolean onOptionsItemSelected(MenuItem item) {
   	 
        switch (item.getItemId()) {
     
            case R.id.refresh:
                showToast("Refresh was clicked.");
                Intent intent = getIntent();
    		    finish();
    		    startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
                
        }
    }
    

	private void showToast(String msg) {
		// TODO Auto-generated method stub
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
    
	private void getOverflowMenu() {
		 
	    try {
	 
	       ViewConfiguration config = ViewConfiguration.get(this);
	       java.lang.reflect.Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
	       if(menuKeyField != null) {
	           menuKeyField.setAccessible(true);
	           menuKeyField.setBoolean(config, false);
	       }
	       
	   } catch (Exception e) {
	       e.printStackTrace();
	   }
	 
	}

    
    public void refresh(){
    	Intent intent = getIntent();
	    finish();
	    startActivity(intent);
    }
    
    public boolean onCreateOptionsMenu(Menu menu){
    	getMenuInflater().inflate(R.menu.main, menu);
    	return true;
    }
    
	
}
