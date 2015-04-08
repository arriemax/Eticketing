package com.example.onlineTicketChecker;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 






import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 






import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
 
public class History extends Activity  {
 
	JSONObject tickectjson;
 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  Thread.setDefaultUncaughtExceptionHandler(new ErrorHandler(this));
  setContentView(R.layout.history);
  
  
  
  HistoryCheck c = new HistoryCheck(this);
	c.execute();
tickectjson = c.getTicketJSON();
  
  
  initList();
  final ListView listView = (ListView) findViewById(R.id.historyView);
  SimpleAdapter simpleAdapter = new SimpleAdapter(this, tickectlist, android.R.layout.simple_list_item_1, new String[] {"employees"}, new int[] {android.R.id.text1});
  listView.setAdapter(simpleAdapter);

   listView.setClickable(true);

  listView.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view,
                  int position, long id) {
         
            
            
            String str = listView.getItemAtPosition(position).toString();
            String[] ticket_id = str.split("=");
            String[] ticketid = ticket_id[1].split("-");
            System.out.println(ticketid[0]);
            System.out.println(ticketid[1]);
            System.out.println(ticketid[0]);
            Intent i=new Intent(getApplicationContext(),HistoryDetail.class);
            i.putExtra("ticket_id", ticketid[0].trim());
  			startActivity(i);
          }
          
          });


 
 }
 
 @Override
 public boolean onCreateOptionsMenu(Menu menu) {
  // Inflate the menu; this adds items to the action bar if it is present.
  getMenuInflater().inflate(R.menu.main, menu);
  return true;
 }
  
 List<Map<String,String>> tickectlist = new ArrayList<Map<String,String>>();
 private void initList(){
   
  try{
   
   JSONArray jsonMainNode = tickectjson.optJSONArray("history");
   
  for(int i = 0; i<jsonMainNode.length();i++){
   JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
   String name = jsonChildNode.optString("ticket_detail");
   String outPut = name ;
   tickectlist.add(createEmployee("employees", outPut));
  }
 }
  catch(JSONException e){
   Toast.makeText(getApplicationContext(), "Error"+e.toString(), Toast.LENGTH_SHORT).show();
  }
 }
  
 private HashMap<String, String>createEmployee(String name,String number){
  HashMap<String, String> employeeNameNo = new HashMap<String, String>();
  employeeNameNo.put(name, number);
  return employeeNameNo;
 }


 
}