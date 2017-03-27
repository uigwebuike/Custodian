package com.custodian.ClaimsCenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.custodian.Alerts;
import com.custodian.CustodianHomeScreenMain;
import com.custodian.R;
import com.custodian.CONSTANT.CONSTANTS;
/**
 * ReportClaimBasic class deals with filling data in screen,
 * saving the same in preferences and then navigate to 
 * next screen name ReportClaimWhoWasInvolved.
 */

public class ReportClaimBasic  extends Activity implements OnClickListener{
	/*
	 * Declarations of views and String used in class
	 */
	ImageButton mback,mHome;
	ImageView btnContinue;
	Intent myIntent;
	Spinner  mSpinnerWhatHpnd;
	EditText edtDesc;
	Editor editor;
	ArrayAdapter<String> dataAdapter;
	AlertDialog.Builder ab;
	AlertDialog alert;
	TextView mHeading,mWhatHappened,mDesc,txtWhatHappened;
	
	//Array for What has happened? 
	private String[] WhatHappend = {"My vehicle was hit when it was parked","My vehicle hit a parked vehicle",
			"My vehicle was hit from the back","My vehicle hit another vehicle at the back",
			"My vehicle was in chain-reaction ( hit from the back collision) with two or more others",
			"My vehicle drove over a pot hole","My vehicle drove over debris in the roadway",
			"My vehicle hit a post,ditch,guardrail or any other non-vehicle object",
			"An object fell on my vehicle","My vehicle was stolen at gunpoint",
			"My vehicle was stolen from a parking lot","My vehicle was submerged due to flood",
			"My vehicle caught fire but was not in an accident",
			"Part of my vehicle was stolen","My vehicle tyres were slashed",
			"My vehicle body paint was intentionally scratched","Other"};

	String StrWhatHappened =  "",StrDesc = "";
	SharedPreferences sharedPreferences;
	public static final String MyPREFERENCES = "MyPrefs" ;
	
	// OnBackPressed is used to disable the device back button as back button is used in top bar for navigation.
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custodian_report_claim_basic);
        
        /*
         * Initialisations and listeners
         */
        //***********************************************************************************
        Typeface face = Typeface.createFromAsset(getAssets(),CONSTANTS.FONT_NAME);
      txtWhatHappened = (TextView)findViewById(R.id.spinner_hapnd);
      txtWhatHappened.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			PopUp();
		}
	});
      mHome = (ImageButton)findViewById(R.id.home);
      mHome.setOnClickListener(this);
        mWhatHappened= (TextView)findViewById(R.id.txt_what_hppnd);
        mWhatHappened.setTypeface(face);
        mDesc = (TextView)findViewById(R.id.txt_desc);
        mDesc.setTypeface(face);
        edtDesc = (EditText)findViewById(R.id.edt_desc);
        mback = (ImageButton)findViewById(R.id.imageView1);
        mback.setOnClickListener(this);
        mHeading = (TextView)findViewById(R.id.title);
        mHeading.setTypeface(face);
        btnContinue = (ImageView)findViewById(R.id.continue_imag);
        btnContinue.setOnClickListener(this);
        //***************************************************************************
          		}
	
	
            		

	/**
	 * PopUp() used to display list of What has happened using customising
	 * alertdialog.
	 */
	protected void PopUp() {
		// TODO Auto-generated method stub
		dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line);
        
        ab=new AlertDialog.Builder(ReportClaimBasic.this);
        ab.setInverseBackgroundForced(true);
        ab.setTitle("What Happened?");
        ab.setItems(WhatHappend, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
               
            	StrWhatHappened  = WhatHappend[item];
            	
               txtWhatHappened.setText(StrWhatHappened);
            }
        });
       dataAdapter.notifyDataSetChanged();
        AlertDialog alert = ab.create();
        alert.show();
	}

	/**
	 * Showalerts (String message) is used to show alerts.
	 * @param message message is the parameter that reflects the message 
	 * to be shown to user .
	 */
	private void Showalerts(String message2) {
		// TODO Auto-generated method stub
		
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(ReportClaimBasic.this);
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
	

	//Actions To be done on Clicks
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch(id){
		case R.id.imageView1:
			/**
			 * Clicking on Back Button will navigate to previus screen.
			 * At the same time it will remove the key procured from preferences
			 * saved in custodianReportClaim Screen on ACCEPT button and other key 
			 * this screen when pressed home button. 
			 * This has been done just to refreash the data so that whenever the user
			 * come again via ACCEPT button ,there will be nothing in preferences
			 * and a new claim can be created.
			 */
			sharedPreferences  = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
			editor = sharedPreferences.edit();
		
			if(sharedPreferences.contains("BasicsKey")||(sharedPreferences.contains("MainKey"))){
				editor = sharedPreferences.edit();
				editor.remove("BasicsKey");
				editor.remove("MainKey");
				editor.commit();
			}
		myIntent = new Intent(ReportClaimBasic.this,ReportClaimStart.class);
		
		myIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(myIntent);
	
        break;	
        
		case R.id.home:
			/**
			 *  Below code is used to generate a unique key for the class
			 *  so that data can be refreashed and new claim can be created.
			 */
			myIntent = new Intent(ReportClaimBasic.this,CustodianHomeScreenMain.class);
			sharedPreferences  = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
				 editor = sharedPreferences.edit();
				 editor.putString("BasicsKey", "Basics");
				 editor.commit();
			startActivity(myIntent);
	        break;
        
        
		case R.id.continue_imag:
		StrDesc = edtDesc.getText().toString();

		if(StrWhatHappened.equalsIgnoreCase("")){
			Showalerts(Alerts.REPORT_CLAIM_BASIC);
		}else{
			Log.e("WhatHAppend",StrWhatHappened);	
			Log.e("Description",StrDesc);
				    sharedPreferences  = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);	
			 editor = sharedPreferences.edit();
			 editor.putString("WhatHappenedSelected",StrWhatHappened);
			 editor.putString("Description",StrDesc);
			 editor.commit();	
			 String StartID=  sharedPreferences.getString("StartKey","");
			 String MainKey = sharedPreferences.getString("MainKey","");
			 String VehicleID = sharedPreferences.getString("vehiclesKEY", "");
			 String WhoWasInvolved = sharedPreferences.getString("whowasKEY", "");
			 String BAsicsKey = sharedPreferences.getString("BasicsKey", "");
			
			 /***
			  * Below are the unique keys of all the screens of report claim
			  * These keys will be checked if there is any data. If any of these
			  * keys contains data then on navigating to next screen, there will
			  * be no data written in the fields.No data written in fields is 
			  * an indication of creating a new claim.
			  * But whenever the user is in report claim and whenever he
			  * navigating from and to different screen, data should be written
			  * in the fields.For that below code is used to maintain the data:	 
			  * myIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			  */
			 
			 if(!BAsicsKey.equals("")){
				 myIntent = new Intent(ReportClaimBasic.this,ReportClaimWhoWasInvolved.class);
		         	
	         		startActivity(myIntent);	 	
			}
			else if(!MainKey.equals("")){
				myIntent = new Intent(ReportClaimBasic.this,ReportClaimWhoWasInvolved.class);
	         	
         		startActivity(myIntent);	 	
			}
			else if(!VehicleID.equals("")){
				myIntent = new Intent(ReportClaimBasic.this,ReportClaimWhoWasInvolved.class);
	         	
         		startActivity(myIntent);
			}
			else if(!StartID.equals("")){
				 myIntent = new Intent(ReportClaimBasic.this,ReportClaimWhoWasInvolved.class);
		         	
	         		startActivity(myIntent);
			}
			else if(!WhoWasInvolved.equals("")){
				 myIntent = new Intent(ReportClaimBasic.this,ReportClaimWhoWasInvolved.class);
         	
         		startActivity(myIntent);	  
			 }else{
				 myIntent = new Intent(ReportClaimBasic.this,ReportClaimWhoWasInvolved.class);
         		 myIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
         		startActivity(myIntent);	 
			 }
		}
      	break; 
		 		
	}
}
}
