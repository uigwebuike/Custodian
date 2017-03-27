package com.custodian.ManagePolicy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.custodian.Alerts;
import com.custodian.CustodianHomeScreenMain;
import com.custodian.CustodianLoginScreen;
import com.custodian.R;
import com.custodian.CONSTANT.CONSTANTS;
import com.custodian.ClaimsCenter.ClaimsCenterMenuScreen;
import com.custodian.ClaimsCenter.PolicyDocumentsAdapter;
import com.custodian.ClaimsCenter.ViewAndManageClaimAdapter;
import com.custodian.ClaimsCenter.ViewAndManageClaimMain;
import com.custodian.CustodianWebservices.CommonWebservice;
import com.custodian.CustodianWebservices.CustodianInterface;
import com.custodian.CustodianWebservices.CustomerService;
import com.custodian.CustodianWebservices.CustomerServiceWebService;
import com.custodian.ModelView.CertificateOfDocuments;
import com.custodian.ModelView.CustodianClaims;
import com.custodian.ModelView.PolicyDocuments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ViewYourDocuments extends Activity implements OnClickListener, CustodianInterface, CustomerService{
	Button mButtonViewFirst,mButtonViewSecond;
	ImageView PolicyDocuments,CertificateDocuments;
	ImageButton mback,mHome;
	TextView mTitlebar,mHeading;
	Intent myIntent;
	JSONObject json;
	ArrayList<CertificateOfDocuments> arraylist;
	public static final String MyPREFERENCES = "MyPrefs" ;
	SharedPreferences sharedPreferences;
	CertificateOfDocumentsAdapter adapter ;
	PolicyDocumentsAdapter adapter1;
	ListView listView;
	Handler mSplaHandler=null;
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
	}
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custodian_view_your_documents);
        mSplaHandler=new Handler(){
        	@Override
        	public void handleMessage(Message msg) {
        		// TODO Auto-generated method stub
        		super.handleMessage(msg);
        	if (msg.what==1) {
        		Showalerts(Alerts.NO_RECORDS_FOUND);	
			}else if (msg.what==2) {
		    	 Showalerts(Alerts.CHECK_INTERNET);

			}
        	}
        };
        
        sharedPreferences  = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
   	 	final String OWNERID =  sharedPreferences.getString("id","");
   	 	Log.e("id",OWNERID);
        Typeface face = Typeface.createFromAsset(getAssets(),CONSTANTS.FONT_NAME);
        mHeading = (TextView)findViewById(R.id.title);
        mHeading.setTypeface(face);
        CertificateDocuments = (ImageView)findViewById(R.id.invoice_imag);
        CertificateDocuments.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CertificateDocuments.setBackgroundResource(R.drawable.tab_certificate_of_insurance_hover);
				PolicyDocuments.setBackgroundResource(R.drawable.tab_policy_document);
				String url = "https://custodian.zanibal.com/hermes/rest/api/v1/partner/customer/list/all-policy-lines?customerId="+OWNERID+"&orderBy=vehicleMake&orderDr=ASC&start=0&limit=100";
				ConnectivityManager connectivityManager 
		        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		  NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		   if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){
			   new CommonWebservice(url,ViewYourDocuments.this,
						ViewYourDocuments.this, true,"Loading Documents...").execute();		
							
		   }
		   else{
			   mSplaHandler.sendEmptyMessage(2);
		     }
			}
		});
        PolicyDocuments = (ImageView)findViewById(R.id.payment_imag);
        PolicyDocuments.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CertificateDocuments.setBackgroundResource(R.drawable.tab_certificate_of_insurance);
				PolicyDocuments.setBackgroundResource(R.drawable.tab_policy_document_hover);
				String geturl = "https://custodian.zanibal.com/hermes/rest/api/v1/partner/customer/list/policy?customerId="+OWNERID+"&orderBy=name&orderDr=ASC&start=0&limit=1000";
				ConnectivityManager connectivityManager 
		        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		  NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		   if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){
			   new CustomerServiceWebService(geturl, ViewYourDocuments.this,
						ViewYourDocuments.this, true,"Loading Policies...").execute();		
							
		   }
		   else{
			   mSplaHandler.sendEmptyMessage(2);
		     }
		   
			}
		});
   
        
//        ActionBar mActionBar = getSupportActionBar();
//        mActionBar.hide();
        
        
        
        LayoutInflater mInflater = LayoutInflater.from(this);
 
        View mCustomView = mInflater.inflate(R.layout.custodian_action_bar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
        mTitleTextView.setText("YOUR DOCUMENTS");
        mback = (ImageButton)findViewById(R.id.imageView1);
        mback.setOnClickListener(this);
        mHome = (ImageButton)findViewById(R.id.home);
		mHome.setOnClickListener(this);
		String url = "https://custodian.zanibal.com/hermes/rest/api/v1/partner/customer/list/all-policy-lines?customerId="+OWNERID+"&orderBy=vehicleMake&orderDr=ASC&start=0&limit=100";
		ConnectivityManager connectivityManager 
        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
  NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
   if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){
	   new CommonWebservice(url,ViewYourDocuments.this,
				ViewYourDocuments.this, true,"Loading Documents...").execute();		
					
   }
   else{
	   mSplaHandler.sendEmptyMessage(2);
   }
	
//        mActionBar.setCustomView(mCustomView);
//        mActionBar.setDisplayShowCustomEnabled(true);
}
	
	
	private void Showalerts(String message2) {
		// TODO Auto-generated method stub
		//AlertDialog.Builder alertDialog = new AlertDialog.Builder(CustodianHomeScreen.this);
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(ViewYourDocuments.this);
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch(id){
//		case R.id.view_first:
//			 myIntent = new Intent(android.content.Intent.ACTION_VIEW);
//            
//             myIntent.setData(Uri.parse("http://google.com"));
//             startActivity(myIntent);
//			break;
//		case R.id.view_second:
//			myIntent = new Intent(android.content.Intent.ACTION_VIEW);
//            
//            myIntent.setData(Uri.parse("http://google.com"));
//            startActivity(myIntent);
//			break;
		case R.id.imageView1:
//			myIntent = new Intent(ViewYourDocuments.this,CustodianHomeScreenMain.class);
//	        startActivity(myIntent);
			finish();
	        break;
	        
		case R.id.home:
			Intent	myIntent1 = new Intent(ViewYourDocuments.this,CustodianHomeScreenMain.class);
		        startActivity(myIntent1);
		        break;
			}
		}
	@Override
	public void onResponse(String response) {
		// TODO Auto-generated method stub
		CertificateOfDocuments cust  ;
		
		try {
			json = new JSONObject(response);
			arraylist = new ArrayList<CertificateOfDocuments>();
			String count  = json.getString("count");
			Log.e("count",count);
			if(count.equals("0")){
				mSplaHandler.sendEmptyMessage(1);
				
			}else{
				JSONArray jsonArray = json.getJSONArray("result"); 
				
				 for (int i=0; i < jsonArray.length(); i++)
				    {
					 JSONObject oneObject = jsonArray.getJSONObject(i);
					 cust = new CertificateOfDocuments();
					 String Id = oneObject.getString("id");
					 cust.setID(Id);
					 String strPolicyNo = oneObject.getString("policyNo");
					 cust.setPolicyNo(strPolicyNo);
					 String strVehicleNumber = oneObject.getString("vehicleNo");
					 cust.setVehicleNumber(strVehicleNumber);
					 String strVehicleMake = oneObject.getString("vehicleMake");
					 cust.setVehicleMake(strVehicleMake);
					 String strVehicleModel = oneObject.getString("vehicleModel");
					 cust.setVehicleModel(strVehicleModel);
					 arraylist.add(cust);
				    }
				 
				 listView = (ListView)findViewById(R.id.listOfCustodian_claim_center);
				 adapter = new CertificateOfDocumentsAdapter(ViewYourDocuments.this, arraylist);
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
	@Override
	public void onResponsePayment(String response) {
		// TODO Auto-generated method stub
		try {
	
			PolicyDocuments cust;
			json = new JSONObject(response);
			ArrayList<PolicyDocuments> arraylist = new ArrayList<PolicyDocuments>();
			String count  = json.getString("count");
			Log.e("count",count);
			if(count.equals("0")){
			//Showalerts("No records found");	
				mSplaHandler.sendEmptyMessage(1);
			}
			else{
				JSONArray jsonArray = json.getJSONArray("result"); 
				
				 for (int i=0; i < jsonArray.length(); i++)
				    {
					 JSONObject oneObject = jsonArray.getJSONObject(i);
					 cust= new PolicyDocuments();
					 String id = oneObject.getString("id");
					 String strPolicyno = oneObject.getString("policyNo");
					 cust.setPolicyNo(strPolicyno);
					 Log.e("strPolicy",strPolicyno);
					 String strStart = oneObject.getString("startDate");
					 String x= strStart;
					  SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
			
					    long milliSecondsstart= Long.parseLong(x);
					    System.out.println(milliSecondsstart);
			
					    Calendar calendarStart = Calendar.getInstance();
					    calendarStart .setTimeInMillis(milliSecondsstart);
					   final String parsed = (dateFormat.format(calendarStart .getTime())); 
					 cust.setStartDate(parsed);
					 String strEnd = oneObject.getString("endDate");
					 
					 String y= strEnd;
					  SimpleDateFormat dateFormat1=new SimpleDateFormat("dd/MM/yyyy");
			
					    long milliSecondsstart1= Long.parseLong(y);
					    System.out.println(milliSecondsstart1);
			
					    Calendar calendarStart1 = Calendar.getInstance();
					    calendarStart1.setTimeInMillis(milliSecondsstart1);
					   final String parsed1 = (dateFormat1.format(calendarStart1.getTime())); 
					cust.setEndDate(parsed1);
					 
					 String coverType = oneObject.getString("coverType");
					 cust.setCoverType(coverType);
					 arraylist.add(cust);
				    }
				 
				 listView = (ListView)findViewById(R.id.listOfCustodian_claim_center);
				 adapter1 = new PolicyDocumentsAdapter(ViewYourDocuments.this, arraylist);
				 listView.setAdapter(adapter1);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	}

