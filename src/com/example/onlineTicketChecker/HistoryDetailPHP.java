package com.example.onlineTicketChecker;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.content.Context;
public class HistoryDetailPHP extends AsyncTask<String, String, String> {

	JSONParser jParser = new JSONParser();
	// url to get all products list
	private static String url = "history_detail.php";	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	public static final String KEY_USERNAME = "username";
	int i=0;
	private Context parent;
	private TextView username,credits;
	private String ticket_id;
	private ProgressDialog pDialog;
	HashMap<String, String> HistoryDetail;
	JSONArray user = null;
	public HistoryDetailPHP(Context parent, String ticket_id) {
				
	            this.parent=parent;
	            this.ticket_id=ticket_id;		
	}

			/**
			 * Before starting background thread Show Progress Dialog
			 * */
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				pDialog = new ProgressDialog(this.parent);
				pDialog.setMessage("Wait.. Content Loading");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(true);
				pDialog.show();
			}

			/**
			 * getting All products from url
			 * */
			protected String doInBackground(String... args) {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				SessionManager session = new SessionManager(this.parent);
				HashMap<String, String> user=session.getUserDetails();
				params.add(new BasicNameValuePair("name", user.get("username").toString()));
				params.add(new BasicNameValuePair("ticket_id",this.ticket_id));
	          
	          
				// getting JSON string from URL
			
				JSONObject json = jParser.makeHttpRequest(url, "POST", params);
				
				
				Log.d("account: ", json.toString());

				try {
					HistoryDetail = new HashMap<String,String>();
					JSONArray details= json.getJSONArray("history_detail");
			        for(int i = 0; i < details.length(); i++){
			            
			        	 String id = details.getJSONObject(i).getString("ticket_id");
							
				            String source = details.getJSONObject(i).getString("source");
				            
				            String destination = details.getJSONObject(i).getString("destination");
				            
				            String city = details.getJSONObject(i).getString("city");
				         
				            String date = details.getJSONObject(i).getString("journey_date");
				            String noft = details.getJSONObject(i).getString("no_of_passengers");
				            String qrcode = details.getJSONObject(i).getString("qrcode");
				            
				            
				            Log.d("city",city);
				            Log.d("destination",destination);
				            Log.d("source",source);
				            Log.d("journey_date", date);
				            Log.d("noft", noft);
				           
				            
				            HistoryDetail.put("ticket_id", id);				         
				            HistoryDetail.put("Source", source);				           
				            HistoryDetail.put("destination", destination);				           
				            HistoryDetail.put("city", city);				           
				            HistoryDetail.put("date", date);				           
				            HistoryDetail.put("noft", noft);
				            HistoryDetail.put("qrcode", qrcode);
				            
				            
				            
				            
				            Log.d("name",HistoryDetail.get("ticket_id"));
				            
			
			        }
			        i=1;
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
              
				return null;
			}

			/**
			 * After completing background task Dismiss the progress dialog
			 * **/
			protected void onPostExecute(String file_url) {
			pDialog.dismiss();

			}
			
			
			public HashMap<String, String> getInfo()
			{  while(i==0);
			try{
				System.out.println(HistoryDetail.get("city").toString());
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			return HistoryDetail;
			}
	
	

}
