package com.custodian.ManagePolicy;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.custodian.Alerts;

import com.custodian.CustodianHomeScreenMain;
import com.custodian.CustodianSplash;
import com.custodian.DisplayMessageAlert;
import com.custodian.R;
import com.custodian.CONSTANT.CONSTANTS;
import com.custodian.CustodianWebservices.CommonWebservice;
import com.custodian.CustodianWebservices.CustodianInterface;
import com.custodian.ModelView.CustodianViewYourPolicy;
import com.custodian.ModelView.CustomerDetails;
import com.custodian.URLS.WebserviceURLs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
/**
 * View Policy Basic screebn to get the records from ViewYourPolicyAdapter
 * using webservice.Tapping on records will take the user to its detail screen
 * named ViewYourPolicyDetailScreen.
 */
public class ViewYourPolicyMainScreen extends Activity implements CustodianInterface,OnClickListener{
	/*
	 * Declarations of views and Strings used.
	 */
	String url ;
	JSONObject json;
	ImageButton mHome;
	TextView mTitlebar,mHeading;
	Intent myIntent;
	ListView listView;
	ViewYourPolicyAdapter adapter ;
	ArrayList<CustodianViewYourPolicy> arraylist;
	 public static final String MyPREFERENCES = "MyPrefs" ;
	CustomerDetails details;
	SharedPreferences sharedPreferences;
	ImageButton mback;
	Handler mSplaHandler=null;

	// OnBackPressed is used to disable the device back button as back button is used in top bar for navigation.
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
	}
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custodian_view_policy_main_screen); 
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
      
        json = new JSONObject();
        // get user id from preferences as it is required as a parameter to hit webservice.
        sharedPreferences  = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
   	 	String OWNERID =  sharedPreferences.getString("id","");
        Typeface face = Typeface.createFromAsset(getAssets(),CONSTANTS.FONT_NAME);
        mHeading = (TextView)findViewById(R.id.title);
        mHeading.setTypeface(face);
        LayoutInflater mInflater = LayoutInflater.from(this);
 
        View mCustomView = mInflater.inflate(R.layout.custodian_action_bar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
        mTitleTextView.setText("VIEW YOUR POLICY");
        mback = (ImageButton)findViewById(R.id.imageView1);
        mHome = (ImageButton)findViewById(R.id.home);
        mHome.setOnClickListener(this);
        mback.setOnClickListener(this);
       
   
        ConnectivityManager connectivityManager 
        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
  NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
   if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){
	   new CommonWebservice(WebserviceURLs.VIEW_POLICY_MAIN_PART1+OWNERID+WebserviceURLs.VIEW_POLICY_MAIN_PART2_APPEND, ViewYourPolicyMainScreen.this,
				ViewYourPolicyMainScreen.this, true,"Loading Policies...").execute();
					
   }else{
	   mSplaHandler.sendEmptyMessage(1);
 //  new DisplayMessageAlert(ViewYourPolicyMainScreen.this,Alerts.CHECK_INTERNET,"Ok");
   }
}
	
	
	//Json response parsed,store the result in arraylist and send the list in adapter.
	@Override
	public void onResponse(String response) {
		// TODO Auto-generated method stub
		arraylist = new ArrayList<CustodianViewYourPolicy>();
		try {
			CustodianViewYourPolicy policies ;
			json = new JSONObject(response);
			 JSONArray jsonArray = json.getJSONArray("result"); 
			 for (int i=0; i < jsonArray.length(); i++)
			    {
				 JSONObject oneObject = jsonArray.getJSONObject(i);
			policies = new CustodianViewYourPolicy();
			 String id = oneObject.getString("id");
			 policies.setID(id);
			 Log.e("id",id);
			 String startDate = oneObject.getString("startDate");
			
			 policies.setStartDate(startDate);
			 String lastDate = oneObject.getString("endDate");
			 policies.setEndDate(lastDate);
			 String policyNo = oneObject.getString("policyNo");
			 policies.setPolicyNumber(policyNo);
			 String status = oneObject.getString("status");
			 policies.setStatus(status);
			 String coverType = oneObject.getString("coverType");
			 policies.setPolicyType(coverType);
			 Log.e("policyType",coverType);
			 String vehicleType = oneObject.getString("vehicleType");
			 policies.setVehicleType(vehicleType);
			 String policyName = oneObject.getString("name");
			 policies.setPolicyName(policyName);
			 arraylist.add(policies);
			 listView = (ListView)findViewById(R.id.listOfCustodian_claim_center);
			 adapter = new ViewYourPolicyAdapter(ViewYourPolicyMainScreen.this, arraylist);
			 listView.setAdapter(adapter);
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
	private void Showalerts(String message) {
		// TODO Auto-generated method stub
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(ViewYourPolicyMainScreen.this);
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
		//Navigate to previous screen.
		finish();
        break;
        
        
	case R.id.home:
		//Navigate to home screen.
		myIntent = new Intent(ViewYourPolicyMainScreen.this,CustodianHomeScreenMain.class);
        startActivity(myIntent);
        break;
	}
}
}