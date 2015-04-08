package com.example.onlineTicketChecker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.example.onlineTicketChecker.TextCaptcha.TextOptions;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioButton;

public class Signup extends Activity
{
    Button signup;
    Button reset;
    EditText username;
    EditText name;
    EditText password;
    EditText repeatPassword;
    EditText address;
    EditText phone;
    EditText email;
    EditText dob;
    RadioGroup radio;
    String credits;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new ErrorHandler(this));
		setContentView(R.layout.signup);
		addListnerOnButton();
	}
	
	
	public void addListnerOnButton(){
		
	 final Context context=this;
	 signup=(Button)findViewById(R.id.register);
	 reset=(Button)findViewById(R.id.reset);
	 password=(EditText)findViewById(R.id.signingpassword);
	 repeatPassword=(EditText)findViewById(R.id.repeatPassword);
	 username=(EditText)findViewById(R.id.username);
	 name=(EditText)findViewById(R.id.fullname);
	 address=(EditText)findViewById(R.id.address);
	 phone=(EditText)findViewById(R.id.phoneno);
	 dob=(EditText)findViewById(R.id.dateofbirth);
	 email=(EditText)findViewById(R.id.email);
	 
	 radio=(RadioGroup)findViewById(R.id.radioCredits);
	
	  
	 /* POP up OF calender while entering date */
	 final Calendar myCalendar = Calendar.getInstance();

	 final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

	     @Override
	     public void onDateSet(DatePicker view, int year, int monthOfYear,
	             int dayOfMonth) {
	         // TODO Auto-generated method stub
	         myCalendar.set(Calendar.YEAR, year);
	         myCalendar.set(Calendar.MONTH, monthOfYear);
	         myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
	         String myFormat = "yyyy-mm-dd"; //In which you need put here
		     SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

		     dob.setText(sdf.format(myCalendar.getTime()));
	     }

	 };

	    dob.setOnClickListener(new OnClickListener() {

	         @Override
	         public void onClick(View v) {
	             // TODO Auto-generated method stub
	             new DatePickerDialog(Signup.this, date, myCalendar
	                     .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
	                     myCalendar.get(Calendar.DAY_OF_MONTH)).show();
	         }
	     });
	 
	 
	 // validation starts here 
	 
	 

	       
	        // TextWatcher would let us check validation error on the fly
	        name.addTextChangedListener(new TextWatcher() {
	            public void afterTextChanged(Editable s) {
	                Validation.hasText(name);
	            }
	            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	            public void onTextChanged(CharSequence s, int start, int before, int count){}
	        });
	 
	       
	        email.addTextChangedListener(new TextWatcher() {
	            // after every change has been made to this editText, we would like to check validity
	            public void afterTextChanged(Editable s) {
	                Validation.isEmailAddress(email, true);
	            }
	            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	            public void onTextChanged(CharSequence s, int start, int before, int count){}
	        });
	 
	       
	        phone.addTextChangedListener(new TextWatcher() {
	            public void afterTextChanged(Editable s) {
	                Validation.isPhoneNumber(phone, true);
	            }
	            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	            public void onTextChanged(CharSequence s, int start, int before, int count){}
	        });
	 
	      
	        signup.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View view) {
	                /*
	                Validation class will check the error and display the error on respective fields
	                but it won't resist the form submission, so we need to check again before submit
	                 */
	                if ( checkValidation () )
	                  
	                	{
	                	
	                	 if(radio.getCheckedRadioButtonId()!=-1){
	                		    int id= radio.getCheckedRadioButtonId();
	                		    RadioButton btn = (RadioButton)radio.findViewById(id);
	                		     credits = (String) btn.getText().toString();
	                		    System.out.print(credits);
	                		  
	                		    submitForm();
	                		}
	                	 else
	                	 {
	                		 Toast.makeText(getApplicationContext(), "Please Select your credits", Toast.LENGTH_LONG).show();
	                	 }
	                	
	                	}
	                else
	                    Toast.makeText(Signup.this, "Form contains error", Toast.LENGTH_LONG).show();
	            }
	        });
	        
	        
	   	 //on refreshbutton click this method execute
	   	 reset.setOnClickListener(new OnClickListener() {
	   			public void onClick(View v) {
	   				
	   				 username.setText(null);
	   				 username.setHint("Username");
	   				 name.setHint("Full Name");
	   				 password.setHint("Password");
	   				 repeatPassword.setHint("Confirm Password");
	   				 dob.setHint("Date of Birth");
	   				 address.setHint("Address");
	   				 email.setHint("Email");
	   				 phone.setHint("Phone Number");
	   				 
	   				 
	   				
	   			}
	   		});
	    
	}
	    private void submitForm() {
	        // Submit your form here. your form is valid
	       
	        new SignupCheck(Signup.this,this,username,password,name,dob,address,email,phone,credits).execute();			   
		    Toast.makeText(getApplicationContext(), "Redirecting to main page ",Toast.LENGTH_SHORT).show();
		    Intent i=new Intent(getBaseContext(),Home.class);
			startActivity(i);
	    }
	 
	    private boolean checkValidation() {
	        boolean ret = true;
	 
	        if (!Validation.hasText(name)) ret = false;
	        if (!Validation.hasText(username)) ret = false;
	        if (!Validation.hasText(address)) ret = false;
	        if (!Validation.hasText(dob)) ret = false;
	        if (!Validation.hasText(password)) ret = false;
	        if (!Validation.hasText(repeatPassword)) ret = false;
	        if (!Validation.isSelected(radio)) ret = false;
	        if (!Validation.isEmailAddress(email, true)) ret = false;
	        if (!Validation.isPhoneNumber(phone, true)) ret = false;
	 
	        return ret;
	    }
	 
	 
	 // end
	 
//	 if( name.getText().toString().trim().length() == 0 )
//		 name.setError( "Full name is required!" );
//	 if( email.getText().toString().trim().length() != 0)
//	 {
//	 if( !android.util.Patterns.EMAIL_ADDRESS.matcher((CharSequence) email).matches())
//		email.setError( "Enter vaild EmailID " );
//	
//	 }
//	 else
//	 {
//		 email.setError( "this Field is required!" );
//	 } //on signupbutton click this method execute
//	 if( phone.getText().toString().trim().length() != 0)
//	 {
//	 if( ! android.util.Patterns.PHONE.matcher((CharSequence) phone).matches())
//		phone.setError( "Enter vaild Mobile No " );
//	
//	 }
//	 else
//	 {
//		 phone.setError( "this Field is required!" );
//	 }
	 
	 

	    
	
	       
	     /*end*/  
	 
	 
	 
	 

	 
	
	 
	
	}
	


