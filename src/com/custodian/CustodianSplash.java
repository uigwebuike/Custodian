package com.custodian;

import org.json.JSONException;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.crittercism.app.Crittercism;
import com.custodian.CustodianWebservices.CommonWebservice;
import com.custodian.CustodianWebservices.CustodianInterface;
import com.custodian.URLS.WebserviceURLs;
import com.navdrawer.SimpleSideDrawer;

/***
 *  Splash screen class is the first screen to indicate that the application
 * 	is just about to start in few seconds.In this,Login Webservice is hit to check
 *  if the user was logged in last time. If the user was logged in last time
 *  and response is procured as true it will navigate the user directly to Home
 *  Screen else navigate to login screen.
 */

public class CustodianSplash extends Activity implements CustodianInterface {

	//Declarations of views , preferences and other Strings used in this class.
	 private static int SPLASH_TIME_OUT = 5000;
	 public static final String MyPREFERENCES = "MyPrefs" ;
	 SharedPreferences sharedPreferences;
	 String mUsername,mPassword;
	 Intent mIntent;
	 JSONObject json;
	 SimpleSideDrawer mSlidingMenu;
	 ProgressDialog mDialog;
	String loginError;
	Handler mSplaHandler=null;
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        Crittercism.init(getApplicationContext(), "537f4234b573f11c3a000003");
	      
	        setContentView(R.layout.activity_custodian_splash);

	       /**
	        * get data from preferences to check whether there is any data in username and password
	        * so that can be checked that the user was logged in last time or not.
	        */
	        mSplaHandler=new Handler(){
	        	@Override
	        	public void handleMessage(Message msg) {
	        		// TODO Auto-generated method stub
	        		super.handleMessage(msg);
	        	if (msg.what==1) {
	        		Showalerts(loginError);	
				}else if (msg.what==2) {
					Showalerts(Alerts.LOGIN_FAILED);
				}else if (msg.what==3) {
			    	 Showalerts(Alerts.CHECK_INTERNET);

				}
	        	}
	        };
	        sharedPreferences  = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
	        mUsername =  sharedPreferences.getString("username","");
	   	 	mPassword=  sharedPreferences.getString("password","");
	   	 	if(!mUsername.equals("") && !mPassword.equals("")){
	   	 	
			ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    
	     if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){
	    	 new CommonWebservice(WebserviceURLs.LOGIN_SCREEN+mUsername+"&password="+mPassword, CustodianSplash.this,
						CustodianSplash.this,false ,"Loading...").execute();
						
	     }else{
	    	 mSplaHandler.sendEmptyMessage(3);
	     }	

	   	 	}
	   	 	else{
	   	     new Handler().postDelayed(new Runnable() {
	   	 	 
		            /*
		             * Showing splash screen with a timer. This will be useful when you
		             * want to show case your app logo / company
		             */
		 
		            @Override
		            public void run() {
		                // This method will be executed once the timer is over
		                // Start your app main activity
		                Intent i = new Intent(CustodianSplash.this, CustodianMainLanding.class);
		                startActivity(i);
		 
		                // close this activity
		                finish();
		            }
		        }, SPLASH_TIME_OUT);	
	   	 	}
	    
	    }
	    
	    
		
		/**
		 * Showalerts (String message) is used to show alerts.
		 * @param message message is the parameter that reflects the message 
		 * to be shown to user.
		 */
		private void Showalerts(String message) {
			// TODO Auto-generated method stub
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(CustodianSplash.this);
	        // Setting Dialog Title
	        alertDialog.setTitle("Custodian Direct");
	 
	        // Setting Dialog Message
	        alertDialog.setMessage(message);
	 
	        // Setting Icon to Dialog
	        alertDialog.setIcon(R.drawable.app_icon_48x48);
	 
	        // Setting Positive "Yes" Button
	        alertDialog.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog,int which) {
	 
	            dialog.cancel();
	            }
	        });
	        alertDialog.show();
			}	
		
		
		//Json Response	to check the status. If the status is true ,it will navigate directly to Home Screen.	
		@Override
		public void onResponse(String response) {
			// TODO Auto-generated method stub
			
		try {
			json = new JSONObject(response);
			if(json.has("error")){
			loginError = json.getString("error");

				
				mSplaHandler.sendEmptyMessage(1);			
			
			
			}else{
			String status  = json.optString("success");
			if(status.equalsIgnoreCase("false")){
				mIntent  = new Intent(CustodianSplash.this,CustodianMainLanding.class);
				startActivity(mIntent);
				}
		 
		else if(status.equalsIgnoreCase("true")){
			mIntent  = new Intent(CustodianSplash.this,CustodianHomeScreenMain.class);
			startActivity(mIntent);
		}
			}
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		@Override
		public void onError() {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onAlreadyExist(String response) {
			// TODO Auto-generated method stub
			
		}
	 

}
