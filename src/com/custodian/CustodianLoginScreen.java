package com.custodian;

import java.util.regex.Pattern;


import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.custodian.CONSTANT.CONSTANTS;
import com.custodian.CustodianWebservices.CommonWebservice;
import com.custodian.CustodianWebservices.CustodianInterface;
import com.custodian.URLS.WebserviceURLs;

/***
 *  Login Screen class is used to let the user log into his account if
 *  he is registered.
 * 	Login Screen contains Registeration that takes the user to website
 * 	to proceed the registeration process,Forgot Password Button for navigation
 *	and Contact Us that allows the user to call on the number that opens 
 *	with default dialer.
 */


public class CustodianLoginScreen extends Activity implements OnClickListener, CustodianInterface {

// OnBackPressed is used to disable the device back button as back button is used in top bar for navigation.
@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
	}

//Declarations of views used in layout,formats for validations,and other string used.
SharedPreferences sharedPreferences;
String mUsername , mPassword;
Button  mBuyOneNow,mRegister,mForgotID,mForgotPassword;
ImageView mButtonLogin,btContactUs,btBack;
TextView mDontHavePolicy,mBuyNow;
EditText edtUserName, edtPassword;
public static final String MyPREFERENCES = "MyPrefs" ;
Intent mIntent;
JSONObject json;
private static final Pattern PASSWORD_PATTERN = Pattern
.compile("[a-zA-Z0-9+_.]{3,15}");
private static final Pattern USERNAME_PATTERN = Pattern
.compile("[a-zA-Z0-9]{3,30}");
Handler mSplaHandler=null;
String loginError ;
	 
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.custodian_login_screen);
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
	        Typeface face = Typeface.createFromAsset(getAssets(),CONSTANTS.FONT_NAME);
	        
	        //Views Initialisations and their listeners.
	        
	        btContactUs = (ImageView)findViewById(R.id.Contact_us);
	        btContactUs.setOnClickListener(this);
	        mDontHavePolicy = (TextView)findViewById(R.id.dont_have_policy);
	        mDontHavePolicy.setTypeface(face);
	        edtUserName = (EditText)findViewById(R.id.login_screen_username);
	        edtPassword = (EditText)findViewById(R.id.login_screen_password);
	        mRegister = (Button)findViewById(R.id.login_screen_register);
	        mRegister.setTypeface(face);
	        mButtonLogin = (ImageView)findViewById(R.id.login_screen_loginButton);
	        mBuyOneNow=(Button)findViewById(R.id.buy_one_now);
	        mBuyOneNow.setOnClickListener(this);
	        mBuyOneNow.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
	        mBuyOneNow.setTypeface(face);
	        mForgotID = (Button)findViewById(R.id.login_screen_forgot_user_id);
	        mForgotID.setTypeface(face);
	        mForgotPassword=(Button)findViewById(R.id.login_screen_forgot_password);
	        mForgotPassword.setTypeface(face);
	        mButtonLogin.setOnClickListener(this);
	        mForgotID.setOnClickListener(this);
	        mForgotPassword.setOnClickListener(this);
	        mRegister.setOnClickListener(this);
	        btBack = (ImageView)findViewById(R.id.back);
	        btBack.setOnClickListener(this);
}

	   //Actions to be done on clicking on views
	    
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int id = v.getId();
			switch(id){
			
		/***
		 * Login button used to get the details from the user, 
		 * then validate the same and save the details enter 
		 * in preferences as they will be used in Home Page Screen
		 * to send username as a parameter in Home Screen Webservice.
		 */
			case R.id.login_screen_loginButton :
				mUsername = edtUserName.getText().toString();
				mPassword = edtPassword.getText().toString();
				sharedPreferences  = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
				 Editor editor = sharedPreferences.edit();
				 editor.putString("username",mUsername);
				 editor.putString("password",mPassword);
				  editor.commit();
				  
				  if(mUsername.equalsIgnoreCase("")){
					  Showalerts(Alerts.LOGIN_USERNAME_EMPTY);  
				  }
				  else if(mPassword.equalsIgnoreCase("")){
					  Showalerts(Alerts.LOGIN_PASSWORD_EMPTY);
				  }		
				else{
					
					
				ConnectivityManager connectivityManager 
			          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
			     if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){
			    	 new CommonWebservice(WebserviceURLs.LOGIN_SCREEN+mUsername+"&password="+mPassword, CustodianLoginScreen.this,
								CustodianLoginScreen.this, true,"Logging in...").execute();
								
			     }else{
			    	 mSplaHandler.sendEmptyMessage(3);
			     }
					
				}
				
			
				
				break;
				
				
			/***
			 * Register button will navigate the user to website link 
			 * so that user can register from the website. 
			 * Below link mentioned is the link of website for registeration.	
			 */
			case R.id.login_screen_register :
				 mIntent = new Intent(android.content.Intent.ACTION_VIEW);     
	             mIntent.setData(Uri.parse("http://www.custodiandirect.com/index.php?page=guest.Register"));
	             startActivity(mIntent);
	             break;
	             
	        //Forgot ID button used to navigate to forgot ID screen.     
			case R.id.login_screen_forgot_user_id :
				mIntent  = new Intent(CustodianLoginScreen.this,CustodianHomeScreenForgotID.class);
				startActivity(mIntent);
				break;
				
			case R.id.back:
				mIntent  = new Intent(CustodianLoginScreen.this,CustodianMainLanding.class);
				startActivity(mIntent);
				break;
				
				
			//Forgot Password button used to navigate to forgot password screen.	
			case R.id.login_screen_forgot_password :
				mIntent  = new Intent(CustodianLoginScreen.this,CustodianHomeScreenForgotPasswrd.class);
				startActivity(mIntent);
				break;
			
			//This button is placed on top of login screen to open the default dialer with phone number.
			case R.id.Contact_us :
				Intent intent = new Intent(Intent.ACTION_DIAL);
				intent.setData(Uri.parse("tel:" + CONSTANTS.DIAL_NUMBER));
				startActivity(intent);
				break;
			
			//Buy one now button is used to take the user to the below mentioned link.
			case R.id.buy_one_now:
				mIntent = new Intent(android.content.Intent.ACTION_VIEW);
	            
	             mIntent.setData(Uri.parse("http://www.custodiandirect.com/index.php?page=guest.GetAQuote&reset=1"));
	             startActivity(mIntent);
			}
		}

		
		/**
		 * Showalerts (String message) is used to show alerts.
		 * @param message message is the parameter that reflects the message 
		 * to be shown to user when he fill any wrong data or leave fields blank.
		 */
		private void Showalerts(String message) {
			// TODO Auto-generated method stub
			//AlertDialog.Builder alertDialog = new AlertDialog.Builder(CustodianHomeScreen.this);
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(CustodianLoginScreen.this);
	        // Setting Dialog Title
	        alertDialog.setTitle("Custodian");
	 
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


		//Json Response
		@Override
		public void onResponse(String response) {
			// TODO Auto-generated method stub
			try {
				json = new JSONObject(response);
				
				if(json.has("error")){
					String loginError = json.getString("error");

					mSplaHandler.sendEmptyMessage(1);
				}else{
				String status  = json.optString("success");
				if(status.equalsIgnoreCase("false")){
					mSplaHandler.sendEmptyMessage(2);
					}
			 
			else if(status.equalsIgnoreCase("true")){
				mIntent  = new Intent(CustodianLoginScreen.this,CustodianHomeScreenMain.class);
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