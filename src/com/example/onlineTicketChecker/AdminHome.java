package com.example.onlineTicketChecker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.integration.android.IntentIntegrator;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentResult;
public class AdminHome extends Activity
{
	Button scanTicket;
	Button searchTicket,myaccount;
	TextView username;
	TextView credits;
	HashMap<String, String> userDetail;
	JSONParser jParser = new JSONParser();
    HashMap<String, String> HistoryDetail;
    String scanContent;
 
    // url to get all products list
    private static String url = "ticketdetail.php";	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new ErrorHandler(this));
		setContentView(R.layout.adminhome);
		addListnerOnButton();
	}
	
	
	
	

	
	
	
	// Event on buttons click
	
	public void addListnerOnButton(){
	
	 
		scanTicket=(Button)findViewById(R.id.admin_ticketscan);
		
	myaccount =(Button)findViewById(R.id.admin_myaccountbtn);
	 
	
	scanTicket.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				IntentIntegrator scanIntegrator = new IntentIntegrator(AdminHome.this);
				scanIntegrator.initiateScan();
				 
				
			}
		});

	
	myaccount.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				 SessionManager session=new SessionManager(getApplicationContext());
				session.logoutUser();
				 
				
			}
		});
	
}
	
	
	
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		//retrieve result of scanning - instantiate ZXing object
	    IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
	    //check we have a valid result
	    if (scanningResult != null) {
	        //get content from Intent Result
	      scanContent = scanningResult.getContents();
	        //get format name of data scanned
	        String scanFormat = scanningResult.getFormatName();
	        Log.v("SCAN", "content: "+scanContent+" - format: "+scanFormat);
	    	Intent i=new Intent(getApplicationContext(),QRScan.class);
	    	i.putExtra("scancontent", scanContent);
			startActivity(i);
         
	    }
	    else{
	        //invalid scan data or scan canceled
	        Toast toast = Toast.makeText(getApplicationContext(),
	                "No  scan data received!", Toast.LENGTH_SHORT);
	        toast.show();
		}
		}
	
	
	
	
	
	
	// for deatil of ticket
	
	
	   
}
