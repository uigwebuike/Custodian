package com.custodian.ManagePolicy;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
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

import com.custodian.Alerts;
import com.custodian.CustodianHomeScreenMain;
import com.custodian.CustodianSplash;
import com.custodian.DisplayMessageAlert;
import com.custodian.R;
import com.custodian.CONSTANT.CONSTANTS;
import com.custodian.CustodianWebservices.CommonWebservice;
import com.custodian.CustodianWebservices.CustodianInterface;
import com.custodian.ModelView.PolicyDetailsValues;
import com.custodian.URLS.WebserviceURLs;
/**
 * View Your Policy detail screen displays the data from webservice and view
 * your policy basic screen. 
 */

public class ViewYourPolicyDetailScreen extends Activity implements
		OnClickListener, CustodianInterface {
	/*
	 * Declarations of views and Strings used.
	 */
	ImageView mHome;
	TextView mHeading, mTitleTextView, mTitlebar, mVehicleUsage, mModelYear,
			mVehicleColor, mVehicleMake, mVehicleMileage, mVehicleNumber,
			mVehicleValue, mEngineNumber, mChassisNumber, mNotes, mPolicyNo,
			mPolicyName, mPolicyType, mVehicleType, mStartDate, mEndDate,
			lbPolicyNo, lbPolicyName, lbPolicyType, lbVehicleType, lbStartDate,
			lbEndDate, lbUage, lbModelYear, lbcolor, lbMake, lbMil, lbValue,
			lbEng, lbChass, lbNotes, lbNo,mVehicleModel;
	Intent myIntent;
	JSONObject json;
	ImageButton mback, mNext,mPrevious;
	ListView listView;
	ViewDetailsAdapter  adapter ;
	Handler mSplaHandler=null;

	String ID;
	int position = 0;
	String vehicleuse = "";
	String modelyear = "";
	String vehicleclr = "";
	String vehiclemake = "";
	String vehiclemil = "";
	String vehicleno = "";
	String vehicleval = "";
	String engineno = "";
	String chassis = "";
	String descr = "";
	String vehicleModel = "";
	PolicyDetailsValues cust;
	Double input = 0.0;
	ArrayList<PolicyDetailsValues> arraylist;

	// OnBackPressed is used to disable the device back button as back button is used in top bar for navigation.
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custodian_view_policy_details_screen);
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
		Typeface face = Typeface.createFromAsset(getAssets(),
				CONSTANTS.FONT_NAME);
		mHeading = (TextView) findViewById(R.id.title);
		mHeading.setTypeface(face);
		
		/* The Previous button is used to show the previous records when tapped
		 * The records for this come from webservice . if the count is more than
		 * one then it will display previous and next buttons for manipulation.  
		 */
		mPrevious = (ImageButton)findViewById(R.id.btnPrevious);
		mPrevious.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub;
				
				if(position>0){
					mNext.setColorFilter(null);
					position = position - 1 ;
					cust = arraylist.get(position);
					mVehicleModel.setText(cust.getmVehicleMake()+" "+" - "+cust.getmVehicleModel());
					mVehicleUsage.setText(cust.getmVehicleUsage());
					mModelYear.setText(cust.getmModelYear());
					mVehicleColor.setText(cust.getmVehicleColor());
					mVehicleMake.setText(cust.getmVehicleMake());
					mVehicleMileage.setText(cust.getmVehicleMileage());
					mVehicleNumber.setText(cust.getmVehicleNumber());
					String valueInInteger = cust.getmVehicleValue();
					input = Double.parseDouble(valueInInteger);
					DecimalFormat myFormatter = new DecimalFormat("############");
					mVehicleValue.setText(myFormatter.format(input));
					Log.e("Vehicle Value",""+myFormatter.format(input));
					mEngineNumber.setText(cust.getmEngineNumber());
					mChassisNumber.setText(cust.getmChassisNumber());
					mNotes.setText(cust.getmNotes());
					  }
				else{
					  mPrevious.setColorFilter(Color.GRAY, Mode.LIGHTEN);
				}	
				}
				
			
		});
		/* The Next button is used to show the previous records when tapped
		 * The records for this come from webservice . if the count is more than
		 * one then it will display previous and next buttons for manipulation.  
		 */
		mNext = (ImageButton) findViewById(R.id.btnNext);
		
		
		mNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if (position < arraylist.size()-1) {
					mNext.setVisibility(View.VISIBLE);
					mPrevious.setVisibility(View.VISIBLE);
					mPrevious.setColorFilter(null);
					position = position + 1;
					cust = arraylist.get(position);
					mVehicleModel.setText(cust.getmVehicleMake()+" "+" - "+cust.getmVehicleModel());
					mVehicleUsage.setText(cust.getmVehicleUsage());
					mModelYear.setText(cust.getmModelYear());
					mVehicleColor.setText(cust.getmVehicleColor());
					mVehicleMake.setText(cust.getmVehicleMake());
					mVehicleMileage.setText(cust.getmVehicleMileage());
					mVehicleNumber.setText(cust.getmVehicleNumber());
					String valueInInteger = cust.getmVehicleValue();
					input = Double.parseDouble(valueInInteger);
					DecimalFormat myFormatter = new DecimalFormat("############");
					mVehicleValue.setText(myFormatter.format(input));
					Log.e("Vehicle Value",""+myFormatter.format(input));
					mEngineNumber.setText(cust.getmEngineNumber());
					mChassisNumber.setText(cust.getmChassisNumber());
					mNotes.setText(cust.getmNotes());
					  }
				else{
					  mNext.setColorFilter(Color.GRAY, Mode.LIGHTEN);
				}
			}
		});

		LayoutInflater mInflater = LayoutInflater.from(this);

		View mCustomView = mInflater.inflate(R.layout.custodian_action_bar,
				null);
		
/*
 * Initialisations of views and their typeface set.
 */
		//**********************************************************************************
		mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
		mVehicleModel = (TextView)findViewById(R.id.txt_Vehicle_Name);
		mVehicleModel.setTypeface(face,Typeface.BOLD);
		lbPolicyNo = (TextView) findViewById(R.id.txt_Policy_No);
		lbPolicyNo.setTypeface(face);
		mPolicyNo = (TextView) findViewById(R.id.txt_Policy_No_Value);
		mPolicyNo.setTypeface(face, Typeface.BOLD);
		lbPolicyType = (TextView) findViewById(R.id.txt_Policy_Type);
		lbPolicyType.setTypeface(face);
		mPolicyType = (TextView) findViewById(R.id.txt_Policy_Type_Value);
		mPolicyType.setTypeface(face, Typeface.BOLD);
		lbVehicleType = (TextView) findViewById(R.id.txt_Vehicle_Type);
		lbVehicleType.setTypeface(face);
		mVehicleType = (TextView) findViewById(R.id.txt_Vehicle_Type_Value);
		mVehicleType.setTypeface(face, Typeface.BOLD);
		lbStartDate = (TextView) findViewById(R.id.txt_Start_Date);
		lbStartDate.setTypeface(face);
		mStartDate = (TextView) findViewById(R.id.txt_Start_Date_Value);
		mStartDate.setTypeface(face, Typeface.BOLD);
		lbEndDate = (TextView) findViewById(R.id.txt_End_Date);
		lbEndDate.setTypeface(face);
		mEndDate = (TextView) findViewById(R.id.txt_End_Date_Value);
		mEndDate.setTypeface(face, Typeface.BOLD);

		lbUage = (TextView) findViewById(R.id.txt_Vehicle_Usage);
		lbUage.setTypeface(face);
		mVehicleUsage = (TextView) findViewById(R.id.txt_Vehicle_Usage_Value);
		mVehicleUsage.setTypeface(face, Typeface.BOLD);
		lbModelYear = (TextView) findViewById(R.id.txt_Model_Year);
		lbModelYear.setTypeface(face);
		mModelYear = (TextView) findViewById(R.id.txt_Model_Year_Value);
		mModelYear.setTypeface(face, Typeface.BOLD);
		lbcolor = (TextView) findViewById(R.id.txt_Vehicle_Color);
		lbcolor.setTypeface(face);
		mVehicleColor = (TextView) findViewById(R.id.txt_Vehicle_Color_Value);
		mVehicleColor.setTypeface(face, Typeface.BOLD);
		lbMake = (TextView) findViewById(R.id.txt_Vehicle_Make);
		lbMake.setTypeface(face);
		mVehicleMake = (TextView) findViewById(R.id.txt_Vehicle_Make_Value);
		mVehicleMake.setTypeface(face, Typeface.BOLD);
		lbMil = (TextView) findViewById(R.id.txt_Vehicle_Mileage);
		lbMil.setTypeface(face);
		mVehicleMileage = (TextView) findViewById(R.id.txt_Vehicle_Mileage_Value);
		mVehicleMileage.setTypeface(face, Typeface.BOLD);
		lbNo = (TextView) findViewById(R.id.txt_Vehicle_Number);
		lbNo.setTypeface(face);
		mVehicleNumber = (TextView) findViewById(R.id.txt_Vehicle_Number_Value);
		mVehicleNumber.setTypeface(face, Typeface.BOLD);
		lbValue = (TextView) findViewById(R.id.txt_Vehicle_Value);
		lbValue.setTypeface(face);
		mVehicleValue = (TextView) findViewById(R.id.txt_Vehicle_Value_Value);
		mVehicleValue.setTypeface(face, Typeface.BOLD);
		lbEng = (TextView) findViewById(R.id.txt_Engine_Number);
		lbEng.setTypeface(face);
		mEngineNumber = (TextView) findViewById(R.id.txt_Engine_Number_Value);
		mEngineNumber.setTypeface(face, Typeface.BOLD);

		lbChass = (TextView) findViewById(R.id.txt_Chassis_Number);
		lbChass.setTypeface(face);
		mChassisNumber = (TextView) findViewById(R.id.txt_Chassis_Number_Value);
		mChassisNumber.setTypeface(face, Typeface.BOLD);

		lbNotes = (TextView) findViewById(R.id.txt_Notes);
		lbNotes.setTypeface(face);
		mNotes = (TextView) findViewById(R.id.txt_Notes_Value);
		mNotes.setTypeface(face, Typeface.BOLD);

		mTitleTextView.setText("POLICY DETAIL");
		mback = (ImageButton) findViewById(R.id.imageView1);
		mHome = (ImageView) findViewById(R.id.home);
		mHome.setOnClickListener(this);
		mback.setOnClickListener(this);
//**************************************************************************************
		
		/*
		 *  Get data from View your policy basic screen 
		 */
		Intent i = getIntent();
		ID = (String) i.getSerializableExtra("id");
		Log.e("POLICYID", ID);
		String policyno = (String) i.getSerializableExtra("policyNo");
		String policyname = (String) i.getSerializableExtra("policyName");
		String policytype = (String) i.getSerializableExtra("coverType");
		String vehicletype = (String) i.getSerializableExtra("vehicleType");
		String startdate = (String) i.getSerializableExtra("startDate");
		String enddate = (String) i.getSerializableExtra("endDate");
		String policyType = (String) i.getSerializableExtra("policyType");

		/*
		 * Set the data into fields.
		 */
		mPolicyNo.setText(policyno);
		mPolicyType.setText(policyType);
		mVehicleType.setText(vehicletype);
		mStartDate.setText(startdate);
		mEndDate.setText(enddate);
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
			new CommonWebservice(WebserviceURLs.VIEW_POLICY_DETAIL+ID, ViewYourPolicyDetailScreen.this,
					ViewYourPolicyDetailScreen.this, true,"Loading policy details...").execute();

		} else {
//		new DisplayMessageAlert(ViewYourPolicyDetailScreen.this,
//		Alerts.CHECK_INTERNET, "Ok");
			mSplaHandler.sendEmptyMessage(1);
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.imageView1:
		//Navigate back to previous screen.
			finish();
			break;

		case R.id.home:
			//Navigate back to home screen.
			myIntent = new Intent(ViewYourPolicyDetailScreen.this,
					CustodianHomeScreenMain.class);
			startActivity(myIntent);
			break;
		}
	}

//Json response parsed


	@Override
	public void onResponse(String response) {
		// TODO Auto-generated method stub

		arraylist = new ArrayList<PolicyDetailsValues>();
		try {

			json = new JSONObject(response);
			String count  = json.getString("count");
			int counter = Integer.parseInt(count);
			Log.e("count",count);
			if(counter>1){
				mNext.setVisibility(View.VISIBLE);
				JSONArray jsonArray = json.getJSONArray("result");

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject oneObject = jsonArray.getJSONObject(i);
					cust = new PolicyDetailsValues();
					String active = oneObject.optString("active");
					chassis = oneObject.optString("chassisNo");
					cust.setmChassisNumber(chassis);
					String createdDt = oneObject.optString("createdDttm");
					descr = oneObject.optString("description");
					cust.setmNotes(descr);
					engineno = oneObject.optString("engineNo");
					cust.setmEngineNumber(engineno);
					String id = oneObject.optString("id");
					modelyear = oneObject.optString("modelYear");
					cust.setmModelYear(modelyear);

					String name = oneObject.optString("name");
					String premium = oneObject.optString("premiumAmount");
					vehicleclr = oneObject.optString("vehicleColor");
					cust.setmVehicleColor(vehicleclr);
					vehiclemake = oneObject.optString("vehicleMake");
					cust.setmVehicleMake(vehiclemake);
					vehiclemil = oneObject.optString("vehicleMileage");
					cust.setmVehicleMileage(vehiclemil);
					vehicleno = oneObject.optString("vehicleNo");
					cust.setmVehicleNumber(vehicleno);
					vehicleModel= oneObject.optString("vehicleModel");
					cust.setmVehicleModel(vehicleModel);
					vehicleuse = oneObject.optString("vehicleUse");
					cust.setmVehicleUsage(vehicleuse);

					vehicleval = oneObject.optString("vehicleValue");
					
					cust.setmVehicleValue(vehicleval);
					arraylist.add(cust);
					settingValues(position);

					Log.e("", "" + vehicleuse);
					Log.e("", "" + modelyear);
					Log.e("", "" + modelyear);
					Log.e("", "" + vehiclemil);
					Log.e("", "" + vehicleno);
					Log.e("", "" + vehicleval);
					Log.e("", "" + engineno);
					Log.e("", "" + chassis);
					Log.e("", "" + descr);
				}
			}else{
				JSONArray jsonArray = json.getJSONArray("result");

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject oneObject = jsonArray.getJSONObject(i);
					cust = new PolicyDetailsValues();
					String active = oneObject.optString("active");
					chassis = oneObject.optString("chassisNo");
					cust.setmChassisNumber(chassis);
					String createdDt = oneObject.optString("createdDttm");
					descr = oneObject.optString("description");
					cust.setmNotes(descr);
					engineno = oneObject.optString("engineNo");
					cust.setmEngineNumber(engineno);
					String id = oneObject.optString("id");
					modelyear = oneObject.optString("modelYear");
					cust.setmModelYear(modelyear);

					String name = oneObject.optString("name");
					String premium = oneObject.optString("premiumAmount");
					vehicleclr = oneObject.optString("vehicleColor");
					cust.setmVehicleColor(vehicleclr);
					vehiclemake = oneObject.optString("vehicleMake");
					cust.setmVehicleMake(vehiclemake);
					vehiclemil = oneObject.optString("vehicleMileage");
					cust.setmVehicleMileage(vehiclemil);
					vehicleno = oneObject.optString("vehicleNo");
					cust.setmVehicleNumber(vehicleno);
					vehicleModel= oneObject.optString("vehicleModel");
					cust.setmVehicleModel(vehicleModel);
					vehicleuse = oneObject.optString("vehicleUse");
					cust.setmVehicleUsage(vehicleuse);

					vehicleval = oneObject.optString("vehicleValue");
					cust.setmVehicleValue(vehicleval);
					arraylist.add(cust);
					settingValues(position);

					Log.e("", "" + vehicleuse);
					Log.e("", "" + modelyear);
					Log.e("", "" + modelyear);
					Log.e("", "" + vehiclemil);
					Log.e("", "" + vehicleno);
					Log.e("", "" + vehicleval);
					Log.e("", "" + engineno);
					Log.e("", "" + chassis);
					Log.e("", "" + descr);

				}
				
			}


		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void DisplayData() {
		// TODO Auto-generated method stub
		
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
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(ViewYourPolicyDetailScreen.this);
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
	

	/**
	 *  settingValues function   
	 *  @param position  is used to get the position of 
	 *  count number so that it can be manipulated back and forth using
	 *  next and previous button 
	 */
	private void settingValues(int position) {
	
		cust = arraylist.get(position);
		mVehicleModel.setText(cust.getmVehicleMake()+" "+" - "+cust.getmVehicleModel());
		mVehicleUsage.setText(cust.getmVehicleUsage());
		mModelYear.setText(cust.getmModelYear());
		mVehicleColor.setText(cust.getmVehicleColor());
		mVehicleMake.setText(cust.getmVehicleMake());
		mVehicleMileage.setText(cust.getmVehicleMileage());
		mVehicleNumber.setText(cust.getmVehicleNumber());
	
		  String value = null;
		  String valueInInteger = cust.getmVehicleValue();
			
			  input = Double.parseDouble(valueInInteger);
			  DecimalFormat myFormatter = new DecimalFormat("############");
			  mVehicleValue.setText(myFormatter.format(input));
			  Log.e("Vehicle Value",""+myFormatter.format(input));
		
		mEngineNumber.setText(cust.getmEngineNumber());
		mChassisNumber.setText(cust.getmChassisNumber());

		mNotes.setText(cust.getmNotes());

		
	}
}
