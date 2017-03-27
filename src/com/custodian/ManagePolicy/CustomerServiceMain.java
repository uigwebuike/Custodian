package com.custodian.ManagePolicy;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.custodian.Alerts;
import com.custodian.CustodianHomeScreenMain;
import com.custodian.DisplayMessageAlert;
import com.custodian.R;
import com.custodian.CONSTANT.CONSTANTS;
import com.custodian.CustodianWebservices.CommonWebservice;
import com.custodian.CustodianWebservices.CustodianInterface;
import com.custodian.CustodianWebservices.CustomerService;
import com.custodian.CustodianWebservices.CustomerServiceWebService;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CustomerServiceMain extends Activity implements CustomerService,OnClickListener,CustodianInterface{
	/*
	 * Declarations of views and String used.
	 */
	JSONObject json;
	ListView listView;
	ImageView imgInvoice,imgPayment;
	ImageButton mHome;
	ImageButton mback;
	String OWNERID;
	TextView mHeading;
	public static final String MyPREFERENCES = "MyPrefs" ;
	SharedPreferences sharedPreferences;
	Handler mSplaHandler=null;
	// OnBackPressed is used to disable the device back button as back button is used in top bar for navigation.
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
	}
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custodian_service_center_invoice_main);
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
     
        Typeface face = Typeface.createFromAsset(getAssets(),CONSTANTS.FONT_NAME);
        mHeading = (TextView)findViewById(R.id.title);
        mHeading.setTypeface(face);
        imgInvoice=(ImageView)findViewById(R.id.invoice_imag);
        imgPayment=(ImageView)findViewById(R.id.payment_imag);
        imgInvoice.setOnClickListener(this);
        sharedPreferences  = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
   	 	final String OWNERID =  sharedPreferences.getString("id","");
   	 	Log.e("id",OWNERID);
        imgPayment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				imgInvoice.setBackgroundResource(R.drawable.tab_invoice_history);
				imgPayment.setBackgroundResource(R.drawable.tab_payment_history_selected);
				// TODO Auto-generated method stub

		        ConnectivityManager connectivityManager 
		        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		  NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		   if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){
			   new CustomerServiceWebService(WebserviceURLs.CUSTOMER_SERVICE_PAYMENT+OWNERID+WebserviceURLs.VIEW_POLICY_MAIN_PART2_APPEND, CustomerServiceMain.this,
						CustomerServiceMain.this, true,"Loading Payment Details...").execute();		
							
		   }else{
			   mSplaHandler.sendEmptyMessage(2);
		  	// new DisplayMessageAlert(CustomerServiceMain.this,Alerts.CHECK_INTERNET,"Ok");
		   }
				
			}
		});
        json = new JSONObject();
        
     
        ConnectivityManager connectivityManager 
        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
  NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
   if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){
	   new CommonWebservice(WebserviceURLs.CUSTOMER_SERVICE_INVOICE+OWNERID+WebserviceURLs.VIEW_POLICY_MAIN_PART2_APPEND, CustomerServiceMain.this,
				CustomerServiceMain.this, true,"Loading Invoice Details...").execute();		
					
   }else{
	   mSplaHandler.sendEmptyMessage(2);
 //new DisplayMessageAlert(CustomerServiceMain.this,Alerts.CHECK_INTERNET,"Ok");
   }

	        
	        LayoutInflater mInflater = LayoutInflater.from(this);
	 
	        View mCustomView = mInflater.inflate(R.layout.custodian_action_bar, null);
	        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
	        mTitleTextView.setText("VIEW YOUR POLICY");
	        mback = (ImageButton)findViewById(R.id.imageView1);
	        mback.setOnClickListener(this);
			mHome = (ImageButton)findViewById(R.id.home);
			mHome.setOnClickListener(this);
	       
}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch(id){
	case R.id.invoice_imag:
			imgInvoice.setBackgroundResource(R.drawable.tab_invoice_history_selected);
			imgPayment.setBackgroundResource(R.drawable.tab_payment_history);
			sharedPreferences  = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
	   	 	final String OWNERID =  sharedPreferences.getString("id","");
	   	 	Log.e("id",OWNERID);
			 
	        ConnectivityManager connectivityManager 
	        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	  NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	   if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){
		   new CommonWebservice(WebserviceURLs.CUSTOMER_SERVICE_INVOICE+OWNERID+WebserviceURLs.VIEW_POLICY_MAIN_PART2_APPEND, CustomerServiceMain.this,
					CustomerServiceMain.this, true,"Loading Invoice Details...").execute();		
						
	   }else{
		   mSplaHandler.sendEmptyMessage(2);
	  	//new DisplayMessageAlert(CustomerServiceMain.this,Alerts.CHECK_INTERNET,"Ok");
	   }	
			break;
		
	case R.id.imageView1:
		//Back button will navigate the user to previous screen.
		finish();
        break;

	case R.id.home:
		//Home button will navigate the user directly to home screen.
		Intent	myIntent1 = new Intent(CustomerServiceMain.this,CustodianHomeScreenMain.class);
	        startActivity(myIntent1);
	        break;
		}
	}

	//Json Response of Invoice  parsed
	@Override
	public void onResponse(String response) {
		// TODO Auto-generated method stub

		try {
			json = new JSONObject(response);
			ArrayList<CustodianServiceInvoice> arraylist = new ArrayList<CustodianServiceInvoice>();
			CustodianServiceInvoice invoiceObject ;
			
			String count  = json.getString("count");
			Log.e("count",count);
			if(count.equals("0")){
			//Showalerts(Alerts.NO_RECORDS_FOUND);	
				mSplaHandler.sendEmptyMessage(1);
				
			}else{
				JSONArray jsonArray = json.getJSONArray("result");
				 for (int i=0; i < jsonArray.length(); i++){
					 JSONObject oneObject = jsonArray.getJSONObject(i);
					 invoiceObject =  new CustodianServiceInvoice();
					 
					 String StrInvoiceDate = oneObject.optString("invoiceDate");
					 invoiceObject.setInvoiceDate(StrInvoiceDate);
					 
			 
					 String strInvoice = oneObject.optString("name");
					 invoiceObject.setInvoice(strInvoice);
			 
					 String  strAmount = oneObject.optString("invoiceTotalAmount");
					 invoiceObject.setAmount(strAmount);
					 
					 String strDesc = oneObject.optString("label");
					 invoiceObject.setDescription(strDesc);
					 
					 String strDueDate = oneObject.optString("dueDate");
					 invoiceObject.setDate(strDueDate);
					 
					 String StrStatus = oneObject.optString("status");
					 invoiceObject.setStatus(StrStatus);
					 
					 arraylist.add(invoiceObject);
					 listView = (ListView)findViewById(R.id.listOfCustodian_claim_center);
					CustomerServiceInvoiceAdapter adapter = new CustomerServiceInvoiceAdapter(CustomerServiceMain.this, arraylist);
					 listView.setAdapter(adapter);
				 }
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//Json Response of Payment parsed

	@Override
	public void onResponsePayment(String response) {
		// TODO Auto-generated method stub

		try {
			json = new JSONObject(response);
			ArrayList<CustodianServicePayment> arraylist = new ArrayList<CustodianServicePayment>();
			CustodianServicePayment invoiceObject = new CustodianServicePayment();
			String count  = json.getString("count");
			Log.e("count",count);
			if(count.equals("0")){
				//Showalerts(Alerts.NO_RECORDS_FOUND);	
				mSplaHandler.sendEmptyMessage(1);
			}
			
			else{
				JSONArray jsonArray = json.getJSONArray("result");
				 for (int i=0; i < jsonArray.length(); i++){
					 JSONObject oneObject = jsonArray.getJSONObject(i);
					 String postingDate = oneObject.optString("paymentDate");
					 invoiceObject.setPostingDate(postingDate);
			 
					 String  strAmount = oneObject.optString("paymentAmount");
					 invoiceObject.setAmount(strAmount);
					 
					 
					 String strName = oneObject.optString("name");
					 invoiceObject.setInvoiceNo(strName);
					 
					 String strDesc = oneObject.optString("label");
					 invoiceObject.setDesc(strDesc);
					 

					 
					 arraylist.add(invoiceObject);
					 listView = (ListView)findViewById(R.id.listOfCustodian_claim_center);
					 CustodianServicePaymentAdapter adapter= new CustodianServicePaymentAdapter(CustomerServiceMain.this, arraylist);
					 listView.setAdapter(adapter);
				 }
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
	
	/**
	 * Showalerts (String message) is used to show alerts.
	 * @param message message is the parameter that reflects the message 
	 * to be shown to user.
	 */
	
	private void Showalerts(String message) {
		// TODO Auto-generated method stub
		//AlertDialog.Builder alertDialog = new AlertDialog.Builder(CustodianHomeScreen.this);
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(CustomerServiceMain.this);
        // Setting Dialog Title
		
		 alertDialog.setCancelable(false);
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
}
	