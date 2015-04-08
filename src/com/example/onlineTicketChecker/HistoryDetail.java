package com.example.onlineTicketChecker;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
 






import com.google.zxing.WriterException;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.Display;



import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 




import com.google.zxing.BarcodeFormat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
 

public class HistoryDetail extends Activity {
 
    // Progress Dialog
    private ProgressDialog pDialog;
    TextView ticketcity,date,noft,source,destination,id;
    ImageView qrcode;
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
    HashMap<String, String> HistoryDetail;
  
 
    // url to get all products list
    private static String url = "history_detail.php";	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	public static final String KEY_USERNAME = "username";
 
    // JSON Node names
 
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_PID = "pid";
    private static final String TAG_NAME = "name";
 
    // products JSONArray
    JSONArray products = null;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tickect_history);
        Thread.setDefaultUncaughtExceptionHandler(new ErrorHandler(this));
        ticketcity=(TextView)findViewById(R.id.ticket_city);
      
        date=(TextView)findViewById(R.id.ticket_date);
        noft=(TextView)findViewById(R.id.ticket_noft);
        source=(TextView)findViewById(R.id.ticket_source);
        id=(TextView)findViewById(R.id.ticketid);
        destination=(TextView)findViewById(R.id.ticket_destination);
        
        qrcode=(ImageView)findViewById(R.id.ticketqrcode);
        // Hashmap for ListView
       
        // on seleting single product
        // launching Edit Product Screen
        new LoadData().execute();
 
    }
 
    // Response from Edit Product Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if result code 100
        if (resultCode == 100) {
            // if result code 100 is received
            // means user edited/deleted product
            // reload this screen again
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
 
    }
 
    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class LoadData extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(HistoryDetail.this);
            pDialog.setMessage("Loading  Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
        	List<NameValuePair> params = new ArrayList<NameValuePair>();
			SessionManager session = new SessionManager(HistoryDetail.this);
			HashMap<String, String> user=session.getUserDetails();
			params.add(new BasicNameValuePair("name", user.get("username").toString()));
			params.add(new BasicNameValuePair("ticket_id",getIntent().getStringExtra("ticket_id")));
          
          
			// getting JSON string from URL
		
			JSONObject json = jParser.makeHttpRequest(url, "POST", params);
			
			
			Log.d("account: ", json.toString());
            try {
                // Checking for SUCCESS TAG
//                int success = json.getInt(TAG_SUCCESS);
// 
//                if (success == 1) {
//                    // products found
//                    // Getting Array of Products
//                    products = json.getJSONArray(TAG_PRODUCTS);
// 
//                    // looping through All Products
//                    for (int i = 0; i < products.length(); i++) {
//                        JSONObject c = products.getJSONObject(i);
// 
//                        // Storing each json item in variable
//                        String id = c.getString(TAG_PID);
//                        String name = c.getString(TAG_NAME);
// 
//                        // creating new HashMap
//                        HashMap<String, String> map = new HashMap<String, String>();
// 
//                        // adding each child node to HashMap key => value
//                        map.put(TAG_PID, id);
//                        map.put(TAG_NAME, name);
// 
//                        // adding HashList to ArrayList
//                        productsList.add(map);
//                    }
//                } else {
//                    // no products found
//                    // Launch Add New product Activity
//                   
//                }
            	
            	
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
			            HistoryDetail.put("source", source);				           
			            HistoryDetail.put("destination", destination);				           
			            HistoryDetail.put("city", city);				           
			            HistoryDetail.put("date", date);				           
			            HistoryDetail.put("noft", noft);
			            HistoryDetail.put("qrcode", qrcode);
			            
			            
			            
			            
			            Log.d("name",HistoryDetail.get("ticket_id"));
		        }
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                	ticketcity.setText(HistoryDetail.get("city").toString());
                	date.setText(HistoryDetail.get("date").toString());
                	noft.setText(HistoryDetail.get("noft").toString());
                	source.setText(HistoryDetail.get("source").toString());
                	destination.setText(HistoryDetail.get("destination").toString());
                	id.setText(HistoryDetail.get("ticket_id").toString());
                	try
                	{   WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        		        Display display = manager.getDefaultDisplay();
        		        Point point = new Point();
        		    display.getSize(point);
        		    int width = point.x;
        		    int height = point.y;
        		    int smallerDimension = width < height ? width : height;
        		    smallerDimension = smallerDimension * 3/4;
        		    String data=HistoryDetail.get("ticket_id").toString();
        		    QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(data,
        		             null,
        		             Contents.Type.TEXT, 
        		             BarcodeFormat.QR_CODE.toString(),
        		             smallerDimension);
                    Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
                    qrcode.setImageBitmap(bitmap);
                	}
                	catch(Exception e)
                	{
                		e.printStackTrace();
                	}
                	
                }
            });
 
        }
 
    }
}