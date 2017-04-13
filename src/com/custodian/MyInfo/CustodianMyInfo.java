package com.custodian.MyInfo;
import org.json.JSONException;
import org.json.JSONObject;
import com.custodian.Alerts;
import com.custodian.CustodianHomeScreenMain;
import com.custodian.CustodianLoginScreen;
import com.custodian.CustodianMainLanding;
import com.custodian.OnSwipeTouchListener;
import com.custodian.R;
import com.custodian.CONSTANT.CONSTANTS;
import com.custodian.ClaimsCenter.ClaimsCenterMenuScreen;
import com.custodian.CustodianWebservices.CommonWebservice;
import com.custodian.CustodianWebservices.CustodianInterface;
import com.custodian.InformationCenter.InformationCenterMenuScreen;
import com.custodian.ModelView.CustomerDetails;
import com.custodian.URLS.WebserviceURLs;
import com.navdrawer.SimpleSideDrawer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
/***
 * CustodianMyInfo class is used to display the information of the
 * user using webservice. For webservice username will
 * be required to as parameters in url. They are procured from the 
 * preferences of Login Screen.
 *
 */
public class CustodianMyInfo extends Activity implements OnClickListener, CustodianInterface {
	
	/*
	 * Declarations of views and Strings used in class.
	 */
	ImageView mback,btnManagePolicy;
	TextView  Manage, claimcenter,info_ctr,myInfo,log;
	TextView mTitlebar,txtUserName,txtEmail,txtFirstName,txtLastName,
	txtMobile,txtAdd,txtAdd1,txtState,txtCity,lbUserName,mHeading,lbFirstName,lbEmail,
	lbLastName,lbMobile,lbAdd,lbAdd1,lbState,lbCity;
	Intent myIntent;
	public static final String MyPREFERENCES = "MyPrefs" ;
	RelativeLayout rel1,rel2,rel3,rel4,rel5,mainLayout;
	CustomerDetails details;
	SimpleSideDrawer mSlidingMenu;
	JSONObject json;
	SharedPreferences sharedPreferences ;
	String username ="";
	String password = "",userName= "",email = "",firstName = "",lastName ="",
	mobile ="",add1 = "",add2 = "",state = "", city = "",customerId = "",
	ownerId = "";
	Handler mSplaHandler=null;
	
	
	/*
	 *  OnBackPressed is used to disable the device back button as back button is used in top bar for navigation.(non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custodian_my_info);
        mSplaHandler=new Handler(){
        	@Override
        	public void handleMessage(Message msg) {
        		// TODO Auto-generated method stub
        		super.handleMessage(msg);
        	if (msg.what==1) {
		    	 Showalerts(Alerts.CHECK_INTERNET);

			}
        	}
        };
        
       /*
        * To Toggle the sliding menu 
        */
       
        mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);
        mainLayout.setOnTouchListener(new OnSwipeTouchListener(CustodianMyInfo.this) {
      	    @Override
      	    public void onSwipeRight() {
      	        // Whatever
      	    	
      	    	mSlidingMenu.toggleLeftDrawer();
      	    }
      	});

        /*
         * Hit Webservice to get data 
         */
  
         sharedPreferences  = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
      	 username =  sharedPreferences.getString("username","");
      	 password=  sharedPreferences.getString("password","");
      	 Log.e("",""+username);
      	 Log.e("",""+password);
        
       
        ConnectivityManager connectivityManager 
        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){
        new CommonWebservice(WebserviceURLs.HOME_SCREEN+ username, CustodianMyInfo.this,
   				CustodianMyInfo.this, true,"Loading Customer Info...").execute();
        
		
	     }else{
	    	 mSplaHandler.sendEmptyMessage(1);
	     }
 
        /*
         * Initializations and Changing the text with Helvetica
         */
        //************************************************************************************       
        Typeface face = Typeface.createFromAsset(getAssets(),CONSTANTS.FONT_NAME);
        mHeading = (TextView)findViewById(R.id.title);
        mHeading.setTypeface(face);
        btnManagePolicy = (ImageView)findViewById(R.id.my_info_buttonOfManage);
        btnManagePolicy.setOnClickListener(this);
        lbUserName = (TextView)findViewById(R.id.txt_my_info_username);
        lbUserName.setTypeface(face);
        lbEmail =  (TextView)findViewById(R.id.txt_my_info_email);
        lbEmail.setTypeface(face);
        lbFirstName = (TextView)findViewById(R.id.txt_my_info_first_name);
        lbFirstName.setTypeface(face);
        lbLastName = (TextView)findViewById(R.id.txt_my_info_last_name);
        lbLastName.setTypeface(face);
        lbMobile = (TextView)findViewById(R.id.txt_my_info_mobile);
        lbMobile.setTypeface(face);
        lbAdd = (TextView)findViewById(R.id.txt_my_info_address1);
        lbAdd.setTypeface(face);
        lbAdd1 = (TextView)findViewById(R.id.txt_my_info_address2);
        lbAdd1.setTypeface(face);
        lbState = (TextView)findViewById(R.id.txt_my_info_state);
        lbState.setTypeface(face);
        lbCity = (TextView)findViewById(R.id.txt_my_info_city);
        lbCity.setTypeface(face);
        txtUserName=(TextView)findViewById(R.id.edt_my_info_username);
        txtUserName.setTypeface(face);
        txtEmail = (TextView)findViewById(R.id.edt_my_info_email);
        txtEmail.setTypeface(face);
        txtFirstName = (TextView)findViewById(R.id.edt_my_info_first_name);
        txtFirstName.setTypeface(face);
        txtLastName =(TextView)findViewById(R.id.edt_my_info_last_name);
        txtLastName.setTypeface(face);
        txtMobile =(TextView)findViewById(R.id.edt_my_info_mobile);
        txtMobile.setTypeface(face);
        txtAdd = (TextView)findViewById(R.id.edt_my_info_address1);
        txtAdd.setTypeface(face);
        txtAdd1 = (TextView)findViewById(R.id.edt_my_info_address2);
        txtAdd1.setTypeface(face);
        txtState = (TextView)findViewById(R.id.edt_my_info_state);
        txtState.setTypeface(face);
        txtCity =(TextView)findViewById(R.id.edt_my_info_city);
        txtCity.setTypeface(face);
        
        mSlidingMenu = new SimpleSideDrawer(CustodianMyInfo.this );
        
        // set the slider to behind of layout
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
        rel4 = (RelativeLayout)findViewById(R.id.My_Info_menu);
        myInfo = (TextView)findViewById(R.id.txt_my_info);
        rel4.setBackgroundColor(Color.GRAY);
        myInfo.setTextColor(Color.WHITE);
        rel4.setOnClickListener(this);
        rel5 = (RelativeLayout)findViewById(R.id.Logout_menu);
        log = (TextView)findViewById(R.id.txt_Log_out);
        rel5.setOnClickListener(this);
        //****************************************************************************
        
        /*
         *  To slide the screen from left to right with finger
         */
        
        ((ImageView)findViewById(R.id.imageView1)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mSlidingMenu.toggleLeftDrawer();
			}
		});
        
        sharedPreferences  = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
 

}
 /*
  *  ClearData() function is used to clear all the data when logout is pressed.
  */
    //********************************************************************************************************
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
    //*********************************************************************************************************
    
    /**
     * Showalerts (String message) is used to show alerts.
     * @param message message is the parameter that reflects the message 
     * to be shown to user.
     */
    
	private void Showalerts(String message) {
		// TODO Auto-generated method stub
		//AlertDialog.Builder alertDialog = new AlertDialog.Builder(CustodianHomeScreen.this);
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(CustodianMyInfo.this);
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

    

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch(id){

	case R.id.my_info_buttonOfManage:
		/*
		 * Manage Policy Button used to navigate back to Manage Policy screen i.e. Home Screen.
		 */
		myIntent = new Intent(CustodianMyInfo.this,CustodianHomeScreenMain.class);
        startActivity(myIntent);
        break;
        
	case R.id.Menu:
		/*
		 * Menu button placed on left side is used to navigate directly to Home Page.
		 */
		myIntent = new Intent(CustodianMyInfo.this,CustodianHomeScreenMain.class);
		 startActivity(myIntent);
		break;
		
	case R.id.Claim_center_menu:
		/*
		 * used to navigate to ClaimsCenterMenuScreen from Menu Button.
		 */
		myIntent = new Intent(CustodianMyInfo.this,ClaimsCenterMenuScreen.class);
		startActivity(myIntent);	
		break;
		
	case R.id.Information_menu:
		/*
		 * used to navigate to InformationCenterMenuScreen from Menu Button.
		 */
		myIntent = new Intent(CustodianMyInfo.this,InformationCenterMenuScreen.class);
		startActivity(myIntent);	
		break;
		
	case R.id.Logout_menu:
		/*
		 * used to logout from the application.
		 */
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(CustodianMyInfo.this);
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
            	Intent intent = new Intent(CustodianMyInfo.this,CustodianMainLanding.class);
	 startActivity(intent); 
            }
        });
 
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            // Write your code here to invoke NO event
           // Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
            dialog.cancel();
            }
        });
 
        // Showing Alert Message
        alertDialog.show();

		break;
	}   
        
	}


/*
 * Json response parsed(non-Javadoc)
 * @see com.custodian.CustodianWebservices.CustodianInterface#onResponse(java.lang.String)
 */
	
	@Override
	public void onResponse(String response) {
		// TODO Auto-generated method stub
		try {
			json = new JSONObject(response);
			 ownerId = json.optString("id");
			 Log.e("ID",ownerId);
			 userName =  json.getString("portalUserName");
			 txtUserName.setText(userName);
			 Log.e("Name",userName);
			 JSONObject jsonArray = json.getJSONObject("personOwner"); 
			 email = jsonArray.getString("emailAddress");
			 txtEmail.setText(email);
			 Log.e("1",email);
			 firstName = jsonArray.getString("firstName");
			 txtFirstName.setText(firstName);
			 Log.e("2",firstName);
			 lastName =jsonArray.getString("lastName");
			 txtLastName.setText(lastName);
			 Log.e("3",lastName);
			 mobile = jsonArray.getString("mobilePhone");
			 txtMobile.setText(mobile);
			 Log.e("4",mobile);
			 add1 = jsonArray.getString("primaryStreet");
			 txtAdd.setText(add1);
			 txtAdd.setMovementMethod(new ScrollingMovementMethod());
			 Log.e("5",add1);
			 add2 = jsonArray.getString("primaryStreet1");
			 txtAdd1.setText(add2);
			 txtAdd1.setMovementMethod(new ScrollingMovementMethod());
			 Log.e("6",add2);
			 state = jsonArray.getString("primaryState");
			 txtState.setText(state);
			 Log.e("7",state);
			 city = jsonArray.getString("primaryCity");
			 txtCity.setText(city);
			 Log.e("8",city);
	
			 
			 JSONObject jsonArray1 = json.getJSONObject("custom"); 
			 customerId = jsonArray1.getString("id");
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

	}
