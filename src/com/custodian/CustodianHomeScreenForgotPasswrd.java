package com.custodian;

import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.custodian.CONSTANT.CONSTANTS;
import com.custodian.CustodianWebservices.CommonWebservice;
import com.custodian.CustodianWebservices.CustodianInterface;
import com.custodian.CustodianWebservices.ForgotInterface;
import com.custodian.CustodianWebservices.ForgotWebservice;


public class CustodianHomeScreenForgotPasswrd extends Activity implements CustodianInterface,OnClickListener, ForgotInterface{
	private static final Pattern USERNAME_PATTERN = Pattern
			.compile("[a-zA-Z0-9]{3,30}");
	
	JSONObject json;
	TextView mHeading,mForgotPassword;
	EditText edtForgotPassd;
	String strParseUserName,mUserID ="";
	ImageView mSubmit;
	ImageButton mback;
	Intent myIntent;
	Handler mSplaHandler=null;
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
	}
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custodian_forgot);
        mSplaHandler=new Handler(){
        	@Override
        	public void handleMessage(Message msg) {
        		// TODO Auto-generated method stub
        		super.handleMessage(msg);
        	if (msg.what==1) {
        		Showalerts("The user id that you have entered is not on file. Please try again or call us for assistance.");
			}else if (msg.what==2) {
				ShowDone("Mail has been successfully sent.");
			}else if (msg.what==3) {
		    	 Showalerts(Alerts.CHECK_INTERNET);

			}
        	}
        };

        mForgotPassword = (TextView)findViewById(R.id.forgot_password_email);
        edtForgotPassd = (EditText)findViewById(R.id.edt_forgot_password);
        mSubmit = (ImageView)findViewById(R.id.continue_imag_policy);
        
        Typeface face = Typeface.createFromAsset(getAssets(),CONSTANTS.FONT_NAME);
        
        
        mHeading = (TextView)findViewById(R.id.title);
        mHeading.setTypeface(face);
        edtForgotPassd.setTypeface(face);
        mForgotPassword.setTypeface(face);
        mSubmit.setOnClickListener(this);
        mback = (ImageButton)findViewById(R.id.imageView1);
        mback.setOnClickListener(this);	
			
        
//        ActionBar actionBar  = getSupportActionBar();
//        actionBar.hide();
//        actionBar.setIcon(R.drawable.btn_menu);
//        actionBar.getNavigationMode();
//        actionBar.setHomeButtonEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(false);
        
}

	protected boolean CheckUserID(String mUserID) {
		// TODO Auto-generated method stub
		return USERNAME_PATTERN.matcher(mUserID).matches();
	}
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
          //  NavUtils.navigateUpFromSameTask(this);
        	onBackPressed();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	@Override
	public void onResponse(String response) {
		// TODO Auto-generated method stub
		try {

			json = new JSONObject(response);
			if(json.has("error")){
				String loginError = json.getString("error");

				//new Alerts(CustodianHomeScreenForgotPasswrd.this,
						//loginError, "ok");
				//Showalerts(loginError);
			}
			else if(json.optString("success").equalsIgnoreCase("false"))
				{
				//new Alerts(CustodianHomeScreenForgotPasswrd.this,"The user id that you have entered is not on file. Please try again or call us for assistance.","Ok");	
				//Showalerts("The user id that you have entered is not on file. Please try again or call us for assistance.");
				 mSplaHandler.sendEmptyMessage(1);
				}
			 
			else{
				String strUrl = "https://custodian.zanibal.com/hermes/rest/api/v1/partner/email/send";
				String	ownerId = json.optString("id");
				JSONObject jsonArray = json.getJSONObject("personOwner");
				String email = jsonArray.getString("emailAddress");
				int AccountID = Integer.parseInt(ownerId);
//				strParseUserName = json.optString("portalUserName");
//				Log.e("portalUserName",""+strParseUserName);
//				Log.e("entered user id",""+mUserID);
//				if(mUserID.equalsIgnoreCase(strParseUserName)){
//				Showalerts("Mail has been successfully sent");
				//new Alerts(CustodianHomeScreenForgotPasswrd.this,"We have sent your password to your email address: uchenna@zanibal.com"+"\n" +"Thank you for using the portal. ","Ok");		
				//}
				boolean value = Boolean.valueOf("true");
				json = new JSONObject();
			
				json.put("active",value);
				json.put("from","enquiries@custodianinsurance.com");
				json.put("template",1612);
				json.put("mimeType","text/html");
				json.put("partner",AccountID);
				json.put("relatedToId",AccountID);
				json.put("relatedToType","CUSTOMER");
				json.put("to",email);
			
				new ForgotWebservice(strUrl, "", CustodianHomeScreenForgotPasswrd.this,CustodianHomeScreenForgotPasswrd.this,false, json,"Loading..").execute();		
				
			}
				
			
		} catch (JSONException e) {
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
	
	
	private void Showalerts(String message2) {
		// TODO Auto-generated method stub
		//AlertDialog.Builder alertDialog = new AlertDialog.Builder(CustodianHomeScreen.this);
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(CustodianHomeScreenForgotPasswrd.this);
        // Setting Dialog Title
        alertDialog.setTitle("Custodian Direct");
 
        // Setting Dialog Message
        alertDialog.setMessage(message2);
 
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
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch(id){
		case R.id.imageView1 :
//			myIntent = new Intent(CustodianHomeScreenForgotPasswrd.this,CustodianLoginScreen.class);
//	        startActivity(myIntent);
			finish();
	        break;
		case R.id.continue_imag_policy:
			 mUserID= edtForgotPassd.getText().toString();
				if(mUserID.equalsIgnoreCase("")){
					new DisplayMessageAlert(CustodianHomeScreenForgotPasswrd.this,"Please enter User ID","Ok");
				}
				else if (!CheckUserID(mUserID)) {
					//new Alerts(CustodianHomeScreenForgotPasswrd.this,"Please enter email in the following pattern :"+"\n" +"abc@gmail.com","Ok");	
				}
				
				else {
					String url = "https://apps-demo.zanibal.com/hermes/rest/api/v1/partner/customer/username/"+mUserID;
					ConnectivityManager connectivityManager 
			          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
			     if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){
			    	 new CommonWebservice(url, CustodianHomeScreenForgotPasswrd.this,
								CustodianHomeScreenForgotPasswrd.this, true,"Submitting...").execute();
								
			     }else{
			    	 mSplaHandler.sendEmptyMessage(3);
			    	 //new DisplayMessageAlert(CustodianHomeScreenForgotPasswrd.this,"Please Check Internet Connection","Ok");
			     }
					
				}
			}
		}
	
	
	private void ShowDone(String message2) {
		// TODO Auto-generated method stub
		//AlertDialog.Builder alertDialog = new AlertDialog.Builder(CustodianHomeScreen.this);
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(CustodianHomeScreenForgotPasswrd.this);
        // Setting Dialog Title
        alertDialog.setTitle("Custodian Direct");
 
        // Setting Dialog Message
        alertDialog.setMessage(message2);
 
        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.app_icon_48x48);
 
        // Setting Positive "Yes" Button
        alertDialog.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
 
            	finish();
            }
        });
        alertDialog.show();
		}	
	
	@Override
	public void Response(String response) {
		// TODO Auto-generated method stub
		try {
			json = new JSONObject(response);
			if(json.has("error")){
				String loginError = json.getString("error");

				//Showalerts(loginError);
			}else{
			String status  = json.optString("success");
			if(status.equalsIgnoreCase("false")){
				Showalerts("Invalid Email");
				}
		 
		else if(status.equalsIgnoreCase("true")){
			 mSplaHandler.sendEmptyMessage(2);
			//Showalerts("Mail has been successfully sent");
		}
		}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void Error() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void AlreadyExist(String response) {
		// TODO Auto-generated method stub
		
	}

	
	}

