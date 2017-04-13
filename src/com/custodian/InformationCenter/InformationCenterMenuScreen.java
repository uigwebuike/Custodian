package com.custodian.InformationCenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.custodian.CustodianHomeScreenMain;
import com.custodian.CustodianLoginScreen;
import com.custodian.CustodianMainLanding;
import com.custodian.OnSwipeTouchListener;
import com.custodian.R;
import com.custodian.CONSTANT.CONSTANTS;
import com.custodian.ClaimsCenter.ClaimsCenterMenuScreen;
import com.custodian.ClaimsCenter.CustodianReportClaim;
import com.custodian.ModelView.CustomerDetails;
import com.custodian.MyInfo.CustodianMyInfo;
import com.navdrawer.SimpleSideDrawer;
/**
 * InformationCenterMenuScreen class is menu class for Information centre
 * module that contains three tabs to navigate to their respective screens.
 *
 */

public class InformationCenterMenuScreen extends Activity implements OnClickListener{
	//Declarations of views and Strings used.
	TextView mAboutCustodianProcess,mClaimsProcessMainu,mContactUs,mHeading;
	ImageView mPhone,mEmail,mBranches;
	ImageView mback;
	TextView mTitlebar;
	Intent myIntent;
	SimpleSideDrawer mSlidingMenu;
	public static final String MyPREFERENCES = "MyPrefs" ;
	CustomerDetails details;
	SharedPreferences sharedPreferences;
	TextView  Manage, claimcenter,info_ctr,myInfo,log;
	RelativeLayout rel1,rel2,rel3,rel4,rel5,relLayout1,relLayout2,relLayout3,mainLayout;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custodian_information_center_menu);
        /**
         * Following code is used to slide the screen from left to right.
         */
        mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);
        mainLayout.setOnTouchListener(new OnSwipeTouchListener(InformationCenterMenuScreen.this) {
      	    @Override
      	    public void onSwipeRight() {
      	        // Whatever
      	    	
      	    	mSlidingMenu.toggleLeftDrawer();
      	    }
      	});

        //Initialisations and listeners.
       //********************************************************************************************* 
        mPhone = (ImageView)findViewById(R.id.bPhone);
        mEmail = (ImageView)findViewById(R.id.bEmail);
        mBranches  = (ImageView)findViewById(R.id.bBranches);
        mAboutCustodianProcess=(TextView)findViewById(R.id.about_custodian_direct_menu);
        mClaimsProcessMainu=(TextView)findViewById(R.id.claims_process_menu);
        mContactUs = (TextView)findViewById(R.id.contact_us_menu);
        relLayout1  = (RelativeLayout)findViewById(R.id.layout1);
        relLayout2 = (RelativeLayout)findViewById(R.id.layout2);
        relLayout3 = (RelativeLayout)findViewById(R.id.layout3);
        relLayout1.setOnClickListener(this);
        relLayout2.setOnClickListener(this);
        relLayout3.setOnClickListener(this);
        mPhone.setOnClickListener(this);
        mEmail.setOnClickListener(this);
        mBranches.setOnClickListener(this);
        mSlidingMenu = new SimpleSideDrawer(InformationCenterMenuScreen.this );
        mSlidingMenu.setLeftBehindContentView( R.layout.sidebar );
        rel1 = (RelativeLayout)findViewById(R.id.Menu);
        rel1.setOnClickListener(this);
        Manage = (TextView)findViewById(R.id.txt_Manage_Policy);
        rel2 = (RelativeLayout)findViewById(R.id.Claim_center_menu);
        claimcenter = (TextView)findViewById(R.id.txt_Claim_center);
        rel2.setOnClickListener(this);
        rel3 = (RelativeLayout)findViewById(R.id.Information_menu);
        info_ctr = (TextView)findViewById(R.id.txt_info_center);
        rel3.setOnClickListener(this);
        rel3.setBackgroundColor(Color.GRAY);
        info_ctr.setTextColor(Color.WHITE);
        rel4 = (RelativeLayout)findViewById(R.id.My_Info_menu);
        myInfo = (TextView)findViewById(R.id.txt_my_info);
        rel4.setOnClickListener(this);
        rel5 = (RelativeLayout)findViewById(R.id.Logout_menu); 
        log = (TextView)findViewById(R.id.txt_Log_out);
        rel5.setOnClickListener(this);
        Typeface face = Typeface.createFromAsset(getAssets(),CONSTANTS.FONT_NAME);
        mHeading = (TextView)findViewById(R.id.title);
        mHeading.setTypeface(face);  
        mAboutCustodianProcess.setTypeface(face);
        mClaimsProcessMainu.setTypeface(face);
        mContactUs.setTypeface(face);
       //********************************************************************************************* 
	    
        
        //code to open the slider
        ((ImageButton)findViewById(R.id.imageView1)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mSlidingMenu.toggleLeftDrawer();
			}
		});
     
      
}
/**
 * ClearData() function is used to clear all the data when logout is pressed.
 */
	protected void ClearData() {
		// TODO Auto-generated method stub
	
		  sharedPreferences  = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		  Editor editor = sharedPreferences.edit();
		  if(sharedPreferences.contains("username")||sharedPreferences.contains("password")){
			  editor.remove("username");
			  editor.remove("password");
		  }
		     editor.clear();
		     editor.commit();
	}
	

	//Actions to be done on button click.
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch(id){
		case R.id.layout1:
			//AboutCustodian layout1 used to navigate to AboutCustodianDirect screen.
			myIntent = new Intent(InformationCenterMenuScreen.this,AboutCustodianDirect.class);
            startActivity(myIntent);
            break;
            
		case R.id.layout2:
			//ClaimsProcess layout2 used to navigate to ClaimsProcess screen.
			myIntent = new Intent(InformationCenterMenuScreen.this,ClaimsProcess.class);
            startActivity(myIntent);
            break;
            
		case R.id.layout3:
			//Contact us layout3 used to navigate to ContactUs screen.
			myIntent = new Intent(InformationCenterMenuScreen.this,ContactUs.class);
            startActivity(myIntent);
            break;
            
		case R.id.bPhone:
			//Phone button opens the default dialer.
			myIntent = new Intent(Intent.ACTION_DIAL); 
			myIntent.setData(Uri.parse("tel:" + CONSTANTS.DIAL_NUMBER));
			startActivity(myIntent);
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

		case R.id.bBranches:
			//Branches button will navigate to branches screen.
			myIntent = new Intent(InformationCenterMenuScreen.this,Branches.class);
	        startActivity(myIntent);
	        break;
	        
		case R.id.make_payment_menu:
			//used to navigate to Report Claim screen.
			myIntent = new Intent(InformationCenterMenuScreen.this,CustodianReportClaim.class);
            startActivity(myIntent);
            break;
            
		case R.id.Menu:
			//Menu button placed on left side is used to navigate directly to Home Page.
			myIntent = new Intent(InformationCenterMenuScreen.this,CustodianHomeScreenMain.class);
            startActivity(myIntent);
			break;
			
		case R.id.Claim_center_menu:
			//used to navigate to ClaimsCenterMenuScreen from Menu Button.
			myIntent = new Intent(InformationCenterMenuScreen.this,ClaimsCenterMenuScreen.class);
			startActivity(myIntent);	
			break;

		case R.id.My_Info_menu:
			//used to navigate to MyInfo from Menu Button.
			myIntent = new Intent(InformationCenterMenuScreen.this,CustodianMyInfo.class);
			myIntent.putExtra("details",details);  
			startActivity(myIntent);
			break;
			
		case R.id.Logout_menu:
			//used to logout from the application.
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(InformationCenterMenuScreen.this);
			 alertDialog.setCancelable(false); 
	        // Setting Dialog Title
	        alertDialog.setTitle("Custodian");
	 
	        // Setting Dialog Message
	        alertDialog.setMessage("Are you sure you want to logout from the application?");
	 
	        // Setting Icon to Dialog
	        alertDialog.setIcon(R.drawable.app_icon_48x48);
	 
	        // Setting Positive "Yes" Button
	        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog,int which) {
	            	ClearData();
	            	Intent intent = new Intent(InformationCenterMenuScreen.this,CustodianMainLanding.class);
		 startActivity(intent); 
	            }
	        });
	 
	        // Setting Negative "NO" Button
	        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	            // Write your code here to invoke NO event
	      
	            dialog.cancel();
	            }
	        });
	 
	        // Showing Alert Message
	        alertDialog.show();

			break;
		}   
	        
		}
	}
