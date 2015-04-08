package com.example.onlineTicketChecker;

import java.util.List;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import com.example.onlineTicketChecker.BookTicket;
 
public class CustomOnItemSelectedListener  extends BookTicket implements OnItemSelectedListener{

//  public  void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
//	  
//	  
//	  
//	Toast.makeText(parent.getContext(), 
//		"OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
//		Toast.LENGTH_SHORT).show();
//	
//	SourceAndDestination s= new SourceAndDestination(this, parent.getItemAtPosition(pos).toString());
//	s.execute();
//	 List<String>  cities = s.getCities();
//	 
//	dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, cities);
//	// dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//   sourceCmbo.setAdapter(dataAdapter);
//   destinationCmbo.setAdapter(dataAdapter);
//	
//  }
 
  @Override
  public void onNothingSelected(AdapterView<?> arg0) {
	// TODO Auto-generated method stub
  }
 
}
