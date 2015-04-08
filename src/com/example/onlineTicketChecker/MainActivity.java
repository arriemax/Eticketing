package com.example.onlineTicketChecker;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;

import com.example.onlineTicketChecker.Captcha;
import com.example.onlineTicketChecker.TextCaptcha;
import com.example.onlineTicketChecker.TextCaptcha.TextOptions;

import android.R.string;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
    Button signup;
    Button login;
    Button refresh;
    ImageView captchaImage ;
    EditText answer;
    TextView username;
    TextView password;
    public static  String ans="";
    SessionManager session;
    CheckBox adminlog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		if(android.os.Build.VERSION.SDK_INT>9)
//		{
//			StrictMode.ThreadPolicy policy =new StrictMode.ThreadPolicy.Builder().permitAll().build();
//		    StrictMode.setThreadPolicy(policy);
//		    System.out.println("my thread is configured to allow connection ");
//		}
		Thread.setDefaultUncaughtExceptionHandler(new ErrorHandler(this));
		session =new SessionManager(getApplicationContext());
		HashMap<String, String> user =session.getUserDetails();
		System.out.print(user.get("username"));
		
		
		adminlog=(CheckBox)findViewById(R.id.adminloging);
		if(session.isLoggedIn())
		{
			//CheckBox adminlogin=(CheckBox)findViewById(R.id.adminloging);
			if(session.isAdmin())
			{
				 Intent i=new Intent(getApplicationContext(),AdminHome.class);
				 startActivity(i);
			}
			else
			{ 
				Intent i=new Intent(getApplicationContext(),AdminHome.class);
				startActivity(i);
			}
		
			
		}
		else
		{
			
				setContentView(R.layout.activity_main);
			
			
		captchaImage =(ImageView)findViewById(R.id.captchaImage);
		Captcha c = new TextCaptcha(25, 90, 5, TextOptions.NUMBERS_AND_LETTERS);
		captchaImage.setImageBitmap(c.image);
		ans=c.answer;
		//captchaImage.setLayoutParams(new LinearLayout.LayoutParams(c.width *2, c.height *2));
		addListnerOnButton();
		}
		
	}
				
		
	public void addListnerOnButton(){
		
	 final Context context=this;
	 signup=(Button)findViewById(R.id.signupButton);
	 refresh=(Button)findViewById(R.id.refresh);
	 captchaImage =(ImageView)findViewById(R.id.captchaImage);
	 login=(Button)findViewById(R.id.loginButton);
	 answer=(EditText)findViewById(R.id.answer);
	 username=(TextView)findViewById(R.id.username);
	 password=(TextView)findViewById(R.id.password);
	 
	 //on signupbutton click this method execute
	 signup.setOnClickListener( new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent i=new Intent(getApplicationContext(),Signup.class);
			startActivity(i);
		}
	});
	 
	 //on refreshbutton click this method execute
	 refresh.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				//Captcha c = new MathCaptcha(100, 100, MathOptions.PLUS_MINUS_MULTIPLY); 
				Captcha c = new TextCaptcha(25, 90, 5, TextOptions.NUMBERS_AND_LETTERS);
				captchaImage.setImageBitmap(c.image);
				//captchaImage.setLayoutParams(new LinearLayout.LayoutParams(c.width *2, c.height *2));
			//	ans.setText(c.answer);
				ans=c.answer;
				
			}
		});
	 
	 login.setOnClickListener(new OnClickListener() {
			   public void onClick(View v) {
				
				String captcha=(String)answer.getText().toString();
	           			
	           			
				if(captcha.equalsIgnoreCase(ans)){
					CheckBox adminlogin=(CheckBox)findViewById(R.id.adminloging);
					String name=username.getText().toString();
					String pass=password.getText().toString();
					System.out.print(adminlogin.isChecked());
					if(adminlogin.isChecked())
					{
						new LoginCheckAdmin(MainActivity.this,context,username,password).execute(name,pass);
						
					}
					else
					{new LoginCheck(MainActivity.this,context,username,password).execute(name,pass);
						
					}
					

				 Toast.makeText(getApplicationContext(), "Redirecting to main page ",Toast.LENGTH_SHORT).show();
				
		
				}
				else
					
				Toast.makeText(getApplicationContext(), "Keep Calm and Re-enter Captcha",Toast.LENGTH_SHORT).show();
			}
		});
	 
	
	}
	
	
	
	
}
