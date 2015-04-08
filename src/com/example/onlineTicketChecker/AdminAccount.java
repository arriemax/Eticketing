package com.example.onlineTicketChecker;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AdminAccount extends Activity
{
	
	Button chngPass,logout;
	TextView username;
	TextView credits,name;
	HashMap<String, String> userDetail;
   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.myaccount);
		username=(TextView)findViewById(R.id.myaccount_usernametxt);
		credits = (TextView)findViewById(R.id.myaccount_creditstxt);
		name = (TextView)findViewById(R.id.myaccount_nametxt);
		AccountCheck a =new AccountCheck(this, username, credits);
		a.execute();
		userDetail =a.getAccountInfo();
		name.setText(userDetail.get("name").toUpperCase());
		credits.setText(userDetail.get("credits").toString());
		SessionManager session = new SessionManager(getApplicationContext());
		HashMap<String, String> user =session.getUserDetails();
		username.setText(user.get("username"));
		
		addListnerOnButton();
	}
	
	
	public void addListnerOnButton(){
	
	 
	chngPass=(Button)findViewById(R.id.myaccount_chnagepassword);
	logout=(Button)findViewById(R.id.myaccount_logout); 

	 
	 //on signupbutton click this method execute

	 
	 //on refreshbutton click this method execute
	 chngPass.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
			
				
			}
		});
	 
	 logout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				 SessionManager session=new SessionManager(getApplicationContext());
				session.logoutUser();
				 
				
			}
		});
	
	
	
}
}
