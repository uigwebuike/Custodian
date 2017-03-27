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
 * ReportClaimWhoWasInvolved class deals with filling data in screen,
 * saving the same in preferences and then navigate to 
 * next screen name ReportClaimVehicles.
 */
public class ReportClaimWhoWasInvolved  extends Activity implements OnClickListener{
	/*
	 * Declarations of views , preferences and Strings used.   
	 */
	
	TextView mHeading,txtWhoWasDriving,txtNoPassenger;
	   ImageView btnContinue;
	   ImageButton mback,mHome;
	   Intent myIntent;
		ArrayAdapter<String> dataAdapter;
		AlertDialog.Builder ab;
		AlertDialog alert;
		Spinner mSpinnerDriver,mSpinnerPassngr;
		EditText edtEmail,edtMobile;
		 public static final String MyPREFERENCES = "MyPrefs" ;
		 private String[] driver = {"No driver","Policy Owner","Authorized Driver","Other Driver","Unknown"};
		 private String[] passenger = {"0","1","2","3","4","5","6","More than 6"};
		 String StrDriver = "", StrPassenger = "";
		 SharedPreferences sharedPreferences;
		 String StrWhoWasDriving = "",StrNoOfPassengers = "";
		 String email = "", phone="";
		 Editor editor;
		 
		// OnBackPressed is used to disable the device back button as back button is used in top bar for navigation.
		 @Override
			public void onBackPressed() {
				// TODO Auto-generated method stub
				
			}
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custodian_report_claim_whowasinvolved);
        /*
         * Initialisations and listeners
         */
        //*************************************************************************
        Typeface face = Typeface.createFromAsset(getAssets(),CONSTANTS.FONT_NAME);
        mHeading = (TextView)findViewById(R.id.title);
        mHeading.setTypeface(face);
        mback = (ImageButton)findViewById(R.id.imageView1);
        btnContinue = (ImageView)findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(this);
        mback.setOnClickListener(this);
        mHome = (ImageButton)findViewById(R.id.home);
        mHome.setOnClickListener(this);
        txtWhoWasDriving = (TextView)findViewById(R.id.report_whowasdriving1_value);
        txtWhoWasDriving.setOnClickListener(this);
        txtNoPassenger = (TextView)findViewById(R.id.report_passngr2_value);
        txtNoPassenger.setOnClickListener(this);
        edtEmail = (EditText)findViewById(R.id.report_contact_email_value);
        edtMobile = (EditText)findViewById(R.id.report_contact_no_value);
        sharedPreferences  = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
      	 email =  sharedPreferences.getString("emailAddress","");
      	 phone=  sharedPreferences.getString("mobilePhone","");
      	 edtEmail.setText(email);
      	 edtMobile.setText(phone);
   	 //*********************************************************************************
}
	
	private void Showalerts(String message){
		// TODO Auto-generated method stub
	
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(ReportClaimWhoWasInvolved.this);
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
		myIntent = new Intent(ReportClaimWhoWasInvolved.this,ReportClaimBasic.class);
		sharedPreferences  = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
	
		if(sharedPreferences.contains("whowasKEY")||(sharedPreferences.contains("MainKey"))){
			editor = sharedPreferences.edit();
			editor.remove("whowasKEY");
			editor.remove("MainKey");
			editor.commit();
		}
		myIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(myIntent);
		//finish();
        break;
        
	case R.id.home:
		/**
		 *  Below code is used to generate a unique key for the class
		 *  so that data can be refreashed and new claim can be created.
		 */
		myIntent = new Intent(ReportClaimWhoWasInvolved.this,CustodianHomeScreenMain.class);
		sharedPreferences  = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		 editor = sharedPreferences.edit();			
		 editor.putString("whowasKEY", "whowasinvolved");
		 editor.commit();
		myIntent.putExtra("FromReview","NavigateFromReportClaim");
		
		startActivity(myIntent);
        break;
        
	case R.id.report_whowasdriving1_value:
		PopUp();
		break;
		
	case R.id.report_passngr2_value:
		Passengers();
		break;
		
	case R.id.btnContinue:
		if(phone.equalsIgnoreCase("")){
			Showalerts(Alerts.REPORT_CLAIM_WHOWASINVOLVED_CONTACT_NO);
			
		}
		else if (email.equalsIgnoreCase("")){
			Showalerts(Alerts.REPORT_CLAIM_WHOWASINVOLVED_CONTACT_EMAIL);
		}
		else if(StrWhoWasDriving.equalsIgnoreCase("")){
			Showalerts(Alerts.REPORT_CLAIM_WHOWASINVOLVED_DRIVER);
		}
		else if(StrNoOfPassengers.equalsIgnoreCase("")){
			Showalerts(Alerts.REPORT_CLAIM_WHOWASINVOLVED_NO_OF_PASSENGER);
		}
		else{
			//Save data in preferences to display on review screen.
			sharedPreferences  = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		
			Editor editor = sharedPreferences.edit();
			 editor.putString("Driver",StrWhoWasDriving);
			 editor.putString("Passenger",StrNoOfPassengers);
			 Log.e("Phone",phone);
			 Log.e("Email",email);
			 Log.e("Driver",StrWhoWasDriving);
			 Log.e("Passenger",StrNoOfPassengers);
			  editor.commit();
				 String VehicleID = sharedPreferences.getString("vehiclesKEY", "");
				 String MainKey = sharedPreferences.getString("MainKey","");
				 String BasicsKEY=  sharedPreferences.getString("BasicsKey","");
				 String WhowasKey = sharedPreferences.getString("whowasKEY","");
				 String Vehicles = sharedPreferences.getString("vehiclesKEY", "");
				 String StartID=  sharedPreferences.getString("StartKey","");
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
				 
				 if(!WhowasKey.equals("")){
					 myIntent = new Intent(ReportClaimWhoWasInvolved.this,
								ReportClaimVehicles.class);
						
						startActivity(myIntent);
				 }
				 else if(!MainKey.equals("")){
					 myIntent = new Intent(ReportClaimWhoWasInvolved.this,
								ReportClaimVehicles.class);
						
						startActivity(myIntent);
				 }
				 else if(!VehicleID.equals("")){
					 myIntent = new Intent(ReportClaimWhoWasInvolved.this,
								ReportClaimVehicles.class);
						
						startActivity(myIntent);
				 }
				 
				 else  if(!BasicsKEY.equals("")){
					 myIntent = new Intent(ReportClaimWhoWasInvolved.this,
								ReportClaimVehicles.class);
						
						startActivity(myIntent);
				 
				 }else if(!StartID.equals("")){
					 myIntent = new Intent(ReportClaimWhoWasInvolved.this,ReportClaimVehicles.class);
					 
						startActivity(myIntent);
				 }
				 
				 else if(!Vehicles.equals("")){
					 myIntent = new Intent(ReportClaimWhoWasInvolved.this,ReportClaimVehicles.class);
					 
						startActivity(myIntent);  
				 }else{
					 myIntent = new Intent(ReportClaimWhoWasInvolved.this,ReportClaimVehicles.class);
					  myIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
						startActivity(myIntent); 
				 }
		}
		
        break;
		}
	}
	
	/**
	 * Custom List like a default picker for displaying list of
	 * No. Of Passengers
	 */
	private void Passengers() {
		// TODO Auto-generated method stub
		dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line);
        
        ab=new AlertDialog.Builder(ReportClaimWhoWasInvolved.this);
        ab.setInverseBackgroundForced(true);
        ab.setTitle("No. Of Passengers");
        ab.setItems(passenger, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
               // Toast.makeText(getApplicationContext(), TimeOfIncident[item], Toast.LENGTH_SHORT).show();
            	StrNoOfPassengers = passenger[item];
            	
               txtNoPassenger.setText(StrNoOfPassengers);
            }
        });
       dataAdapter.notifyDataSetChanged();
        AlertDialog alert = ab.create();
        alert.show();
	}
	
	
	/**
	 * Custom List like a default picker for displaying list of
	 * Who was driving your vehicle.
	 */

	private void PopUp() {
		// TODO Auto-generated method stub
		dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line);
        
        ab=new AlertDialog.Builder(ReportClaimWhoWasInvolved.this);
        ab.setInverseBackgroundForced(true);
        ab.setTitle("Who was driving your vehicle.");
        ab.setItems(driver, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
               // Toast.makeText(getApplicationContext(), TimeOfIncident[item], Toast.LENGTH_SHORT).show();
            	StrWhoWasDriving = driver[item];
            	
               txtWhoWasDriving.setText(StrWhoWasDriving);
            }
        });
        dataAdapter.notifyDataSetChanged();
        AlertDialog alert = ab.create();
        alert.show();
	}
	}
