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

public class Home extends Activity
{
	Button history;
	Button bookticket1,myaccount;
	TextView username;
	TextView credits;
	HashMap<String, String> userDetail;
   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new ErrorHandler(this));
		setContentView(R.layout.home);
		username=(TextView)findViewById(R.id.accountname);
		credits = (TextView)findViewById(R.id.credits);
		AccountCheck a =new AccountCheck(this, username, credits);
		a.execute();
		userDetail =a.getAccountInfo();
		username.setText("Welcome " + userDetail.get("name").toUpperCase());
		credits.setText("");
		
		addListnerOnButton();
	}
	
	
	public void addListnerOnButton(){
	
	 
	history=(Button)findViewById(R.id.history);
	bookticket1=(Button)findViewById(R.id.bookticket); 
	myaccount =(Button)findViewById(R.id.myaccountbtn);
	 
	 //on signupbutton click this method execute

	 
	 //on refreshbutton click this method execute
	 history.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				Intent i= new Intent(getApplicationContext(),History.class);
				 startActivity(i);
				 
				
			}
		});
	 
	 bookticket1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				 Intent i= new Intent(getApplicationContext(),BookTicket.class);
				 startActivity(i);
				 
				
			}
		});
	
	
	 myaccount.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				 Intent i= new Intent(getApplicationContext(),MyAccount.class);
				 startActivity(i);
				 
				
			}
		});
}
}
