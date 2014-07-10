package com.example.phplogin;


import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class phpLogin extends Activity implements OnClickListener, android.view.View.OnClickListener {
	Button loginBtn;
	EditText user, pass;
	EditText pesan;
	private String url = "https://www.qisc.us/users/sign_in.json?";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		user = (EditText) findViewById(R.id.userInput);
		pass = (EditText) findViewById(R.id.passInput);
		pesan = (EditText) findViewById(R.id.pesan);
		loginBtn = (Button) findViewById(R.id.button1);
		loginBtn.setOnClickListener(this);
	} 
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				KirimDataAsync kirimAsync = new KirimDataAsync(){
					
					
					public void respon (String result){
						Toast.makeText(phpLogin.this, result, Toast.LENGTH_SHORT).show();
						
						System.out.println("kirim..");
						pesan.setText(result);
					}
					};
				kirimAsync.execute();
			}
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub

			}

			public abstract class KirimDataAsync extends AsyncTask<String, String, String >{
				public abstract void respon(String result);

				protected String doInBackground(String... arg0){

				
				ArrayList<NameValuePair> kirimkephp = new ArrayList<NameValuePair>();
				kirimkephp.add(new BasicNameValuePair("user[email]", user.getText()
						.toString()));
				kirimkephp.add(new BasicNameValuePair("user[password]", pass.getText()
						.toString()));
				String respon = null;
				System.out.println("kirim");
				try {
					
					respon = ClientToServer.eksekusiHttpPost(url, kirimkephp);
					JSONObject objectAuth = new JSONObject(respon);
					
					String statusAuth = objectAuth.getString("success");
					String tokenAuth = objectAuth.getString("token");
					
					if (statusAuth  == "true" ) {
					GoestoNextActivity(tokenAuth);
					
					}
					if (statusAuth == "false")
					{
						pesan.setText("Failed");
					}
					
				
				} catch (Exception e) {

					e.printStackTrace();
				}
				return respon;
			}
			
			private void GoestoNextActivity(String tokenAuth){
				Intent intent = new Intent(phpLogin.this,MainActivity.class);
				intent.putExtra("token", tokenAuth);
				startActivity(intent);
				finish();
				
			}
			protected void onPostExecute(String result){
				super.onPostExecute(result);
				respon (result);
				}
	}
}