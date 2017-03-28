package com.custodian;

import org.json.JSONException;
import org.json.JSONObject;

import com.custodian.CONSTANT.CONSTANTS;
import com.custodian.CustodianWebservices.CommonWebservice;
import com.custodian.CustodianWebservices.CustodianInterface;
import com.custodian.CustodianWebservices.ForgotInterface;
import com.custodian.CustodianWebservices.ForgotWebservice;

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


public class CustodianHomeScreenForgotID extends Activity implements CustodianInterface,OnClickListener, ForgotInterface{
//	private static final Pattern EMAIL_PATTERN = Pattern
//			.compile("[a-zA-Z0-9+._%-+]{1,100}" + "@"
//					+ "[a-zA-Z0-9][a-zA-Z0-9-]{0,10}" + "(" + ".");
	public static final String MyPREFERENCES = "MyPrefs" ;
	JSONObject json ;
	TextView mHeading,mForgotPassword;
	EditText edtForgotPassd;
	String email,mGetEmail="";
	ImageView mSubmit;
	ImageButton mback;
	Intent myIntent;
	Handler mSplaHandler=null;
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stubdf
		
	}
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custodian_forgot_password);
        mSplaHandler=new Handler(){
        	@Override
        	public void handleMessage(Message msg) {
        		// TODO Auto-generated method stub
        		super.handleMessage(msg);
        	if (msg.what==1) {
        		Showalerts("The email address that you have entered is not on file. Please try again or call us for assistance. ");
			}else if (msg.what==2) {
				ShowDone("Mail has been successfully sent.");
			}else if (msg.what==3) {
		    	 Showalerts(Alerts.CHECK_INTERNET);

			}
        	}
        };
        mSubmit = (ImageView)findViewById(R.id.continue_imag_policy);
        mForgotPassword = (TextView)findViewById(R.id.forgot_password_email);
        edtForgotPassd = (EditText)findViewById(R.id.edt_forgot_password);
        
        
        Typeface face = Typeface.createFromAsset(getAssets(),CONSTANTS.FONT_NAME);
        
        
        mHeading = (TextView)findViewById(R.id.title);
        mHeading.setTypeface(face);
        edtForgotPassd.setTypeface(face);
        mForgotPassword.setTypeface(face);
        mSubmit.setOnClickListener(this);
			
		
//        
//        ActionBar actionBar  = getSupportActionBar();
//        actionBar.hide();
//        actionBar.setIcon(R.drawable.btn_menu);
//        actionBar.getNavigationMode();
//        actionBar.setHomeButtonEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(false);
        
        
        mback = (ImageButton)findViewById(R.id.imageView1);
        mback.setOnClickListener(this);
}
//	protected boolean CheckEmail(String mGetEmail) {
//		// TODO Auto-generated method stub
//		return EMAIL_PATTERN.matcher(mGetEmail).matches();
//	}
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
				
				//Showalerts(loginError);
				//new Alerts(CustodianHomeScreenForgotID.this,
						//loginError, "ok");
			}
			else if(json.optString("success").equalsIgnoreCase("false"))
			{
				mSplaHandler.sendEmptyMessage(1);
			//	Showalerts("The email address that you have entered is not on file. Please try again or call us for assistance. ");
			//new Alerts(CustodianHomeScreenForgotID.this,"The email address that you have entered is not on file. Please try again or call us for assistance. ","Ok");		
			}
			 
			else {
				String	ownerId = json.optString("id");
				JSONObject jsonArray = json.getJSONObject("personOwner");
				email = jsonArray.getString("emailAddress");
			
				
				if(mGetEmail.equalsIgnoreCase(email)){
					String strUrl = "https://custodian.zanibal.com/hermes/rest/api/v1/partner/email/send";
					try {
						
						int AccountID = Integer.parseInt(ownerId);
//						SharedPreferences sharedPreferences  = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//						String AccountID= sharedPreferences.getString("id","");
						//int account = Integer.parseInt(AccountID);
//						Log.e("AccountID",""+AccountID);
//						int accountId = Integer.parseInt(AccountID);
//						Log.e("ID of account",""+accountId);
//						String EmailID = sharedPreferences.getString("emailAddress","");
						boolean value = Boolean.valueOf("true");
						json = new JSONObject();
						
						
						json.put("active",value);
						json.put("from","enquiries@custodianinsurance.com");
						json.put("template",1611);
						json.put("mimeType","text/html");
						json.put("partner",AccountID);
						json.put("relatedToId",AccountID);
						json.put("relatedToType","CUSTOMER");
						json.put("to",mGetEmail);
					
					
					
					new ForgotWebservice(strUrl, "", CustodianHomeScreenForgotID.this,CustodianHomeScreenForgotID.this, false, json,"").execute();
						
						//Showalerts("Mail has been successfully sent");
					//Showalerts("We have sent your User ID to your email address: uchenna@zanibal.com"+"\n" +"Thank you for using the portal. ");
					//new Alerts(CustodianHomeScreenForgotID.this,"We have sent your User ID to your email address: uchenna@zanibal.com"+"\n" +"Thank you for using the portal. ","Ok");		
				}
					catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
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
	
	private void Showalerts(String message2) {
		// TODO Auto-generated method stub
		//AlertDialog.Builder alertDialog = new AlertDialog.Builder(CustodianHomeScreen.this);
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(CustodianHomeScreenForgotID.this);
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
	
	
	private void ShowDone(String message2) {
		// TODO Auto-generated method stub
		//AlertDialog.Builder alertDialog = new AlertDialog.Builder(CustodianHomeScreen.this);
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(CustodianHomeScreenForgotID.this);
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch(id){
		case R.id.imageView1 :
//			myIntent = new Intent(CustodianHomeScreenForgotID.this,CustodianLoginScreen.class);
//	        startActivity(myIntent);
			finish();
	        break;
	    
		case R.id.continue_imag_policy:
			mGetEmail = edtForgotPassd.getText().toString();
			if(mGetEmail.equalsIgnoreCase("")){
				new DisplayMessageAlert(CustodianHomeScreenForgotID.this,"Email address is required","Ok");
			}
//			else if (!CheckEmail(mGetEmail)) {
//				new Alerts(CustodianHomeScreenForgotID.this,"Please enter email in the following pattern :"+"\n" +"abc@example.com","Ok");	
//			}
			
			else {
				String url = "https://apps-demo.zanibal.com/hermes/rest/api/v1/partner/customer/email-address/"+mGetEmail;
				ConnectivityManager connectivityManager 
		          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		     if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){
		    	 new CommonWebservice(url, CustodianHomeScreenForgotID.this,
							CustodianHomeScreenForgotID.this, true,"Submitting...").execute();
							
		     }else{
		    	 mSplaHandler.sendEmptyMessage(3);
		    	 //Showalerts("Check Internet Connection");
		    	 //new Alerts(CustodianHomeScreenForgotID.this,"Check Internet Connection","Ok");
		     }
				
			}
		}
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
				Showalerts("Unable to send the mail.");
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
	