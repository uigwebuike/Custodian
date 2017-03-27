package com.custodian.ClaimsCenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.custodian.InformationCenter.InformationCenterMenuScreen;
import com.custodian.ModelView.CustomerDetails;
import com.custodian.MyInfo.CustodianMyInfo;
import com.navdrawer.SimpleSideDrawer;

/**
 * ClaimsCenterMenuScreen class is the module containing three submodules namely,
 * View and Manage Claims, estimate and Repair Locations and Report a Claim.
 * This class deals with navigation to their submodules.
 */
public class ClaimsCenterMenuScreen extends Activity implements OnClickListener{
	/*
	 * Declarations of views , preferences and Strings used.
	 */
	TextView mClaimsCentter,mTitlebar,mEstimateAndRepair,mPayment,mHeading;
	Intent myIntent;
	ImageView mback;
	CustomerDetails details;
	String OWNERID ="";
	 public static final String MyPREFERENCES = "MyPrefs" ;
	 SimpleSideDrawer mSlidingMenu;
	 RelativeLayout rel1,rel2,rel3,rel4,rel5,relLayout1,relLayout2,relLayout3,mainLayout;
	 SharedPreferences sharedPreferences;
	 TextView  Manage, claimcenter,info_ctr,myInfo,log; 
	 
	// OnBackPressed is used to disable the device back button as back button is used in top bar for navigation.
	 @Override
		public void onBackPressed() {
			// TODO Auto-generated method stub
			
		}

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custodian_claims_center_menu);
        mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);
        /*
         * code to slide from left to right with a finger.
         */
        //**********************************************************
        mainLayout.setOnTouchListener(new OnSwipeTouchListener(ClaimsCenterMenuScreen.this) {
      	    @Override
      	    public void onSwipeRight() {
      	        // Whatever
      	    	
      	    	mSlidingMenu.toggleLeftDrawer();
      	    }
      	});
        //***********************************************************
        
	 /*
	  *  Initailisations of views and typeface of textviews
	  */
        //*************************************************************************
        sharedPreferences  = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Typeface face = Typeface.createFromAsset(getAssets(),CONSTANTS.FONT_NAME);
        mHeading = (TextView)findViewById(R.id.title);
        mHeading.setTypeface(face);
        details = new CustomerDetails();
        mSlidingMenu = new SimpleSideDrawer(ClaimsCenterMenuScreen.this );
        
      
	    mSlidingMenu.setLeftBehindContentView( R.layout.sidebar );
	    
	    relLayout1 = (RelativeLayout)findViewById(R.id.layout1);
	    relLayout2 = (RelativeLayout)findViewById(R.id.layout2);
	    relLayout3 = (RelativeLayout)findViewById(R.id.layout3);
	    relLayout1.setOnClickListener(this);
	    relLayout2.setOnClickListener(this);
	    relLayout3.setOnClickListener(this);
	    rel1 = (RelativeLayout)findViewById(R.id.Menu);
        rel1.setOnClickListener(this);
       
        Manage = (TextView)findViewById(R.id.txt_Manage_Policy);
        rel2 = (RelativeLayout)findViewById(R.id.Claim_center_menu);
        claimcenter = (TextView)findViewById(R.id.txt_Claim_center);
        rel2.setBackgroundColor(Color.GRAY);
        rel2.setOnClickListener(this);
        claimcenter.setTextColor(Color.WHITE);   
        rel3 = (RelativeLayout)findViewById(R.id.Information_menu);
        info_ctr = (TextView)findViewById(R.id.txt_info_center);
        rel3.setOnClickListener(this);
        rel4 = (RelativeLayout)findViewById(R.id.My_Info_menu);
        myInfo = (TextView)findViewById(R.id.txt_my_info);
        rel4.setOnClickListener(this);
        rel5 = (RelativeLayout)findViewById(R.id.Logout_menu);
        rel5.setOnClickListener(this);
        
        log = (TextView)findViewById(R.id.txt_Log_out);
        mEstimateAndRepair=(TextView)findViewById(R.id.view_your_document);
        mEstimateAndRepair.setTypeface(face);
        mEstimateAndRepair.setText("ESTIMATE & REPAIR LOCATIONS");
        mPayment = (TextView)findViewById(R.id.make_payment_menu);
        mPayment.setTypeface(face);
        mClaimsCentter=(TextView)findViewById(R.id.manage_policies_menu);
        mClaimsCentter.setTypeface(face);
        mClaimsCentter.setText("VIEW & MANAGE CLAIMS");
		Log.e("OWNERID",OWNERID);
	    //*******************************************************************
		
		
		//Opens the slider
        ((ImageButton)findViewById(R.id.imageView1)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mSlidingMenu.toggleLeftDrawer();
			}
		});
        
        
}
	
	// ClearData() function is used to clear all the data when logout is pressed.
	//*********************************************************************************
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
	//******************************************************************************
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch(id){
		case R.id.layout1:
			//Navigate to View and Manage Claims basic screen
			myIntent = new Intent(ClaimsCenterMenuScreen.this,ViewAndManageClaimMain.class);
            startActivity(myIntent);
            break;
            
		case R.id.layout3:
			//Navigate to EstimateLocations 
			myIntent = new Intent(ClaimsCenterMenuScreen.this,EstimateAndRepair.class);
            startActivity(myIntent);
            break;
            
		case R.id.layout2:
			//Navigate to Report Claim
			myIntent = new Intent(ClaimsCenterMenuScreen.this,CustodianReportClaim.class);
            startActivity(myIntent);
            break;
            
		case R.id.Menu:
			//opens Slider to show all modules 
			myIntent = new Intent(ClaimsCenterMenuScreen.this,CustodianHomeScreenMain.class);
			 startActivity(myIntent);
			break;

		case R.id.Information_menu:
			//Navigate to Information centre 
			myIntent = new Intent(ClaimsCenterMenuScreen.this,InformationCenterMenuScreen.class);
			startActivity(myIntent);	
			break;
			
		case R.id.My_Info_menu:
			//Navigate to Myfo 
			myIntent = new Intent(ClaimsCenterMenuScreen.this,CustodianMyInfo.class);
			myIntent.putExtra("details",details);  
			startActivity(myIntent);
			break;
		case R.id.Logout_menu:
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(ClaimsCenterMenuScreen.this);
			 alertDialog.setCancelable(false); 
	        // Setting Dialog Title
	        alertDialog.setTitle("Custodian Direct");
	 
	        // Setting Dialog Message
	        alertDialog.setMessage("Are you sure you want to logout from the application?");
	 
	        // Setting Icon to Dialog
	        alertDialog.setIcon(R.drawable.app_icon_48x48);
	 
	        // Setting Positive "Yes" Button
	        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog,int which) {
	            	ClearData();
	            	Intent intent = new Intent(ClaimsCenterMenuScreen.this,CustodianMainLanding.class);
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
