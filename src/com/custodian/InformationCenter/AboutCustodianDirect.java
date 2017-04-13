package com.custodian.InformationCenter;

import com.custodian.CustodianHomeScreenMain;
import com.custodian.CustodianMainLanding;
import com.custodian.R;
import com.custodian.CONSTANT.CONSTANTS;
import com.custodian.ClaimsCenter.ViewAndManageClaimMain;
import com.custodian.ManagePolicy.CustomerServiceMain;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
/**
 *  AboutCustodianDirect class has all static data. 
 *  It contains 3 icons placed at the bottom namely,
 *  Phone - used to call when tapped,
 *  Email - used to send emails,
 *  branches - will take the user to branches screen.
 */


public class AboutCustodianDirect extends Activity implements OnClickListener{
	
	//Declarations of Views used in layout
	ImageView mPhone,mEmail,mBranches;
	ImageView mHome;
	TextView mTitlebar,mpara1,mpara2,mpara3,mpara4,mHeading;
	Intent myIntent;
	ImageButton mback;
	
	// OnBackPressed is used to disable the device back button as back button is used in top bar for navigation.
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
	}
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	       //initialisations and listeners
	        setContentView(R.layout.custodian_about_custodian_direct); 
	        mPhone = (ImageView)findViewById(R.id.bPhone);
	        mEmail = (ImageView)findViewById(R.id.bEmail);
	        mBranches = (ImageView)findViewById(R.id.bBranches);
	        Typeface face = Typeface.createFromAsset(getAssets(),CONSTANTS.FONT_NAME);
	        mHeading = (TextView)findViewById(R.id.title);
	        mHeading.setTypeface(face);
	        mpara1 = (TextView)findViewById(R.id.about_paragraph1);
	        mpara1.setTypeface(face);
	        
	        mpara2 = (TextView)findViewById(R.id.about_paragraph2);
	        mpara2.setTypeface(face);
	        mpara3 = (TextView)findViewById(R.id.about_paragraph3);
	        mpara3.setTypeface(face);
	        mpara4 = (TextView)findViewById(R.id.about_paragraph4);
	        mpara4.setTypeface(face);
	       
	        mBranches.setOnClickListener(this);
	        mPhone.setOnClickListener(this);
	        mEmail.setOnClickListener(this);
	        mback = (ImageButton)findViewById(R.id.imageView1);
	        mback.setOnClickListener(this);
	        
	        mHome = (ImageView)findViewById(R.id.home);
	        mHome.setOnClickListener(this);
				
	        String getAboutKey = getIntent().getStringExtra("aboutKey");
			if(getAboutKey!=null){
			mHome.setVisibility(View.GONE);	
			}
   
}

	


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch(id){
		case R.id.bPhone:
			//Phone button opens the default dialer.
			Intent intent = new Intent(Intent.ACTION_DIAL);
			intent.setData(Uri.parse("tel:" + CONSTANTS.DIAL_NUMBER));
			startActivity(intent);
			break;
			
		case R.id.bEmail:
			//Email button to send email.
			final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

			/* Fill it with Data */
			emailIntent.setType("plain/text");
			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"enquiries@custodianinsurance.com"});
			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Custodian");
			emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");

			/* Send it off to the Activity-Chooser */
			startActivity(Intent.createChooser(emailIntent, "Send mail..."));
			break;
			
		case R.id.imageView1:
			//Back button will navigate back to previous screen from where the user came.
			String getAboutKey = getIntent().getStringExtra("aboutKey");
			if(getAboutKey!=null){
				myIntent = new Intent(AboutCustodianDirect.this,CustodianMainLanding.class);
		        startActivity(myIntent);	
			}
			else{
				myIntent = new Intent(AboutCustodianDirect.this,InformationCenterMenuScreen.class);
		        startActivity(myIntent);
			}
		
	        break;
	        
		case R.id.bBranches:
			//Branches button will navigate to branches screen.
			myIntent = new Intent(AboutCustodianDirect.this,Branches.class);
			myIntent.putExtra("aboutKey", "aboutKey");
	        startActivity(myIntent);
	        break;
	        
		case R.id.home:
			//Home button will navigate back to Home Screen.
			Intent	myIntent1 = new Intent(AboutCustodianDirect.this,CustodianHomeScreenMain.class);
		    startActivity(myIntent1);
		        break;
		}
		
		
		
		
	}
}
	 