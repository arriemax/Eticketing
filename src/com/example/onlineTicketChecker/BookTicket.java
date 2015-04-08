package com.example.onlineTicketChecker;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


public class BookTicket extends Activity implements OnItemSelectedListener
{
   Spinner cityCmbo,sourceCmbo,destinationCmbo;
   ArrayAdapter<String> dataAdapter;
   List<String> cities;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.bookticket);
		cityCmbo=(Spinner)findViewById(R.id.cityCmbo);
		sourceCmbo=(Spinner)findViewById(R.id.sourceCmbo);
		destinationCmbo=(Spinner)findViewById(R.id.destinationCmbo);
		
		
		super.onCreate(savedInstanceState);		

		Thread.setDefaultUncaughtExceptionHandler(new ErrorHandler(this));
		City c = new City(this,cityCmbo);
		c.execute();
		cities = c.getCities();
		dataAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_dropdown_item_1line, cities);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cityCmbo.setAdapter(dataAdapter);
        addListenerOnSpinnerItemSelection();
        cityCmbo.setOnItemSelectedListener(this);
        addListnerOnButton();
        
	}
	
	
	public void addListnerOnButton(){
		
		
		Button bookTicket;
		
		bookTicket = (Button)findViewById(R.id.ticketsubmit);
	
   bookTicket.setOnClickListener(new OnClickListener() {
			   @SuppressWarnings("deprecation")
			public void onClick(View v) {	 
				   
				   
				    cityCmbo=(Spinner)findViewById(R.id.cityCmbo);
					sourceCmbo=(Spinner)findViewById(R.id.sourceCmbo);
					destinationCmbo=(Spinner)findViewById(R.id.destinationCmbo);
					EditText noftE;
					noftE=(EditText)findViewById(R.id.noft);
			  System.out.println(noftE.getText());
				String	city=cityCmbo.getSelectedItem().toString();
				String  source=sourceCmbo.getSelectedItem().toString();
				String  destination=destinationCmbo.getSelectedItem().toString();
				String noft=noftE.getText().toString();
				System.out.println(noft);
				if(source.equals(destination))
				{
					   Toast.makeText(getApplicationContext(), "Choose Other Destination",Toast.LENGTH_SHORT).show();
				}
				else if(noft.trim().equals("") || (int)Integer.parseInt(noft)<0 || (int)Integer.parseInt(noft)>=3){
					 Toast.makeText(getApplicationContext(), "More than 2 ticket not allowed",Toast.LENGTH_SHORT).show();
				}
				else{
		    BookTicketPHP b = new BookTicketPHP(getApplicationContext(),city,source,destination,noft);
		    b.execute();
		    int success=b.getSuccess();
		    System.out.println(success);
		    if(success==1)
		    {
		    String data=b.getData();
		    String credits=b.getCredits();
		   
		    	
		    
		    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
		    Display display = manager.getDefaultDisplay();
		    Point point = new Point();
		    display.getSize(point);
		    int width = point.x;
		    int height = point.y;
		    int smallerDimension = width < height ? width : height;
		    smallerDimension = smallerDimension * 3/4;
		    
		    QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(data,
		             null,
		             Contents.Type.TEXT, 
		             BarcodeFormat.QR_CODE.toString(),
		             smallerDimension);
		   try {
			   
			   
			   
			 //  AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
			   AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BookTicket.this);
			   alertDialogBuilder.setTitle("A/C Information");
			   
				// set dialog message
				alertDialogBuilder
					.setMessage("Your Main Credit is : "+credits)
					.setCancelable(false)
					.setPositiveButton("OK",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, close
							// current activity
							//MainActivity.this.finish();
						}
					  });
				AlertDialog alertDialog = alertDialogBuilder.create();
				 
				// show it
				alertDialog.show();
       // Showing Alert Message
     //  alertDialog.show();
		    Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
		    ImageView myImage = (ImageView) findViewById(R.id.imageView1);
		    myImage.setImageBitmap(bitmap);
		    QRCodePHP c = new QRCodePHP(getApplicationContext(),bitmap);
			c.execute();
		    
		    // saving the ticket to database 
		    
		    
		    
		  
		    
		 
		   } catch (WriterException e) {
		    e.printStackTrace();
		   }
		}
		    else
		    {
		    	   AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BookTicket.this);
				   alertDialogBuilder.setTitle("A/C Information");
				   
					// set dialog message
					alertDialogBuilder
						.setMessage("No enough balance \n Recharge Now")
						.setCancelable(false)
						.setPositiveButton("OK",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								// if this button is clicked, close
								// current activity
								//MainActivity.this.finish();
							}
						  });
					AlertDialog alertDialog = alertDialogBuilder.create();
					 
					// show it
					alertDialog.show();
		    	
		    }
				}
		
			}
		});
	 
	
	}
		
		
		
    	   



	  public void addListenerOnSpinnerItemSelection() {
		  cityCmbo=(Spinner)findViewById(R.id.cityCmbo);
		  //cityCmbo.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	  }
	  
	  public  void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
		  
		  
		  
		
			
			SourceAndDestination s= new SourceAndDestination(this, parent.getItemAtPosition(pos).toString());
			s.execute();
			 List<String>  cities = s.getCities();
			 
			dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, cities);
			// dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		   sourceCmbo.setAdapter(dataAdapter);
		   destinationCmbo.setAdapter(dataAdapter);
			
		  }


	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}
