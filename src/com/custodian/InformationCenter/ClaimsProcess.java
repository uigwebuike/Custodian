package com.custodian.InformationCenter;
import com.custodian.CustodianHomeScreenMain;
import com.custodian.R;
import com.custodian.CONSTANT.CONSTANTS;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/***
 * 	ClaimsProcess class has all static data. 
 *  It contains 3 icons placed at the bottom namely,
 *  Phone - used to call when tapped,
 *  Email - used to send emails,
 *  branches - will take the user to branches screen.
 */


public class ClaimsProcess extends Activity implements OnClickListener{
	
	//Declaration of views used in layout.
	ImageView mPhone,mEmail,mBranches;
	ImageButton mHome;
	TextView mTitlebar,mpara1,mpara2,mpara3,mpara4,mpara5,mpara6,mpara7,mpara8,mHeading;
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
	        setContentView(R.layout.custodian_claims_process); 
	        
	       //intialise views and listeners.
		//********************************************************************       
	        mPhone = (ImageView)findViewById(R.id.bPhone);
	        mEmail = (ImageView)findViewById(R.id.bEmail);
	        mPhone.setOnClickListener(this);
	        mEmail.setOnClickListener(this);
	        mBranches = (ImageView)findViewById(R.id.bBranches);
	        mBranches.setOnClickListener(this);
	        Typeface face = Typeface.createFromAsset(getAssets(),CONSTANTS.FONT_NAME);
	        mpara1 = (TextView)findViewById(R.id.about_paragraph1);
	        mpara1.setTypeface(face);
	        mpara2 = (TextView)findViewById(R.id.about_paragraph2);
	        mpara2.setTypeface(face);
	        mpara3 = (TextView)findViewById(R.id.about_paragraph3);
	        mpara3.setTypeface(face);
	        mpara4 = (TextView)findViewById(R.id.about_paragraph4);
	        mpara4.setTypeface(face);
	        mpara5 = (TextView)findViewById(R.id.about_paragraph5);
	        mpara5.setTypeface(face);
	        mpara6 = (TextView)findViewById(R.id.about_paragraph6);
	        mpara6.setTypeface(face);
	        mpara7 = (TextView)findViewById(R.id.about_paragraph7);
	        mpara7.setTypeface(face);
	        mpara8 = (TextView)findViewById(R.id.about_paragraph8);
	        mpara8.setTypeface(face);
	        mHeading = (TextView)findViewById(R.id.title);
	        mHeading.setTypeface(face);
	        mback = (ImageButton)findViewById(R.id.imageView1);
	        mback.setOnClickListener(this); 
	        mHome = (ImageButton)findViewById(R.id.home);
	        mHome.setOnClickListener(this);
	        //******************************************************************
}
	 
	//Actions to be done on button click.
	 
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
			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Custodian Direct");
			emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");

			/* Send it off to the Activity-Chooser */
			startActivity(Intent.createChooser(emailIntent, "Send mail..."));
			break;
			
		case R.id.imageView1:
			//Back button will navigate back to previous screen from where the user came.
			myIntent = new Intent(ClaimsProcess.this,InformationCenterMenuScreen.class);
	        startActivity(myIntent);
	        break;
	        
		case R.id.home:
			//Home button will navigate back to Home Screen.
			myIntent = new Intent(ClaimsProcess.this,CustodianHomeScreenMain.class);
	        startActivity(myIntent);
	        break;
	        
		case R.id.bBranches:
			//Branches button will navigate to branches screen.
			myIntent = new Intent(ClaimsProcess.this,Branches.class);
	        startActivity(myIntent);
	        break;
		}
	}
	
	
    }
	
	
