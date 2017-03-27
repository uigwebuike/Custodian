package com.custodian.ClaimsCenter;


import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.custodian.Alerts;
import com.custodian.CustodianHomeScreenMain;
import com.custodian.R;
import com.custodian.CONSTANT.CONSTANTS;
import com.custodian.CustodianWebservices.CommonWebservice;
import com.custodian.CustodianWebservices.CustodianInterface;
import com.custodian.CustodianWebservices.CustomerService;
import com.custodian.CustodianWebservices.CustomerServiceWebService;
import com.custodian.URLS.WebserviceURLs;
 
/**
 * ReportClaimStart class deals with filling data in screen,
 * saving the same in preferences and then navigate to 
 * next screen name ReportClaimBasic.
 */
public class ReportClaimStart extends Activity implements
OnClickListener, CustodianInterface, CustomerService {
	/*
	 * Declarations of views and String used in class
	 */
	ImageButton mback, mHome;
	ImageView btnContinue;
	ImageButton btnCalender;
	Intent myIntent;
	private int year;
	private int month;
	private int day;
	String StateSelected = "";
	String CitySelected = "";
	String StrTimeOfIncident = "";
	TextView mHeading,edtDateOfIncident;
	long delta;
	Date SelectedDate = null;
	Date CurrentDate = null;
	TextView txt_Report_Date_Of_incident,txt_Report_Time_Of_incident,txt_report_address,txt_report_state,txt_report_city;
	EditText  edtAddress;
	AlertDialog.Builder ab;
	AlertDialog alert;
	JSONObject json;
	ArrayList<String> states;
	ArrayList<String> cities1;
	ArrayAdapter<String> dataAdapter ;
	String StrCities, selected = "";
	String address  = "";
	SharedPreferences sharedPreferences;
	public static final String MyPREFERENCES = "MyPrefs" ;
	TextView mSpinnerTimeOfIncident, mSpinnerState, mSpinnerCity;
	static final int DATE_PICKER_ID = 1111;
	//Array for Time Of Incident 
	private String[] TimeOfIncident = { "12am - 1am", "1am - 2am",
			"2am - 3am", "3am - 4am", "4am - 5am", "5am - 6am", "6am - 7am",
			"7am - 8am", "8am - 9am", "9am - 10am", "10am - 11am",
			"11am - 12pm", "12pm - 1pm", "1pm - 2pm", "2pm - 3pm", "3pm - 4pm",
			"4pm - 5pm", "5pm - 6pm", "6pm - 7pm", "7pm - 8pm", "8pm - 9pm",
			"9pm - 10pm", "10pm - 11pm", "11pm - 12pm" };
	String strUrl;
	Editor editor;
	Handler mSplaHandler=null;

	// OnBackPressed is used to disable the device back button as back button is used in top bar for navigation.
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custodian_report_a_claim_start);
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
		edtDateOfIncident = (TextView) findViewById(R.id.edt_Report_Date_Of_incident);
		edtAddress = (EditText) findViewById(R.id.edt_Report_Address);
		states = new ArrayList<String>();

		Typeface face = Typeface.createFromAsset(getAssets(),
				CONSTANTS.FONT_NAME);
		mHeading = (TextView) findViewById(R.id.title);
		mHeading.setTypeface(face);
		txt_Report_Date_Of_incident = (TextView)findViewById(R.id.txt_Report_Date_Of_incident);
		txt_Report_Date_Of_incident.setTypeface(face);
		txt_Report_Time_Of_incident = (TextView)findViewById(R.id.txt_Report_Time_Of_incident);
		txt_Report_Time_Of_incident.setTypeface(face);
		txt_report_address = (TextView)findViewById(R.id.txt_report_address);
		txt_report_address.setTypeface(face);
		txt_report_state = (TextView)findViewById(R.id.txt_report_state);
		txt_report_state.setTypeface(face);
		txt_report_city = (TextView)findViewById(R.id.txt_report_city);
		txt_report_city.setTypeface(face);

		btnCalender = (ImageButton) findViewById(R.id.calender);
		btnContinue = (ImageView) findViewById(R.id.continue_imag);
		btnContinue.setOnClickListener(this);
		btnCalender.setOnClickListener(this);
		mback = (ImageButton) findViewById(R.id.imageView1);
		mback.setOnClickListener(this);
		mHome = (ImageButton) findViewById(R.id.home);
		mHome.setOnClickListener(this);
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		mSpinnerTimeOfIncident = (TextView) findViewById(R.id.spinner_time);
		mSpinnerState = (TextView) findViewById(R.id.spinner_title);
		mSpinnerCity = (TextView) findViewById(R.id.spinner_city);

		mSpinnerTimeOfIncident.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PopUp();
			}
		});

		mSpinnerCity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				

				if(StateSelected.equalsIgnoreCase("")){
					Showalerts(Alerts.SELECT_STATE);

				}else{
					ConnectivityManager connectivityManager 
					= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
					NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
					if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){
						new CustomerServiceWebService(WebserviceURLs.SELECT_CITY + StateSelected,
								ReportClaimStart.this,
								ReportClaimStart.this, true,"Loading cities...").execute();
					}
					else{
						mSplaHandler.sendEmptyMessage(2);
					}
				}

			}
		});
		mSpinnerState.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ConnectivityManager connectivityManager 
				= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
				if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){
					new CommonWebservice(WebserviceURLs.SELECT_STATE,ReportClaimStart.this,
							ReportClaimStart.this, true,"Loading states...").execute();
				}else{
					mSplaHandler.sendEmptyMessage(2);
				}

			}
		});

		
	}

	
	/**
	 * PopUp() used to display list of Time Of Incident using customising
	 * alertdialog.
	 */
	protected void PopUp() {
		// TODO Auto-generated method stub
		dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line);

		ab=new AlertDialog.Builder(ReportClaimStart.this);
		ab.setInverseBackgroundForced(true);
		ab.setTitle("Time of Incident");
		ab.setItems(TimeOfIncident, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				StrTimeOfIncident = TimeOfIncident[item];

				mSpinnerTimeOfIncident.setText(StrTimeOfIncident);
			}
		});
		dataAdapter.notifyDataSetChanged();
		AlertDialog alert = ab.create();
		alert.show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		int id = v.getId();
		switch (id) {
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
			myIntent = new Intent(ReportClaimStart.this,
					CustodianReportClaim.class);
			
			startActivity(myIntent);
			break;

		case R.id.home:
			/**
			 *  Below code is used to generate a unique key for the class
			 *  so that data can be refreashed and new claim can be created.
			 */
			myIntent = new Intent(ReportClaimStart.this,CustodianHomeScreenMain.class);
			sharedPreferences  = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
			editor = sharedPreferences.edit();
			editor.putString("StartKey", "Start");
			editor.commit();

			startActivity(myIntent);
			break;

		case R.id.calender:
			showDialog(DATE_PICKER_ID);
			break;

		case R.id.continue_imag:
			
			address = edtAddress.getText().toString();
			
			String dateOfIncident = edtDateOfIncident.getText().toString();
			if(dateOfIncident.equalsIgnoreCase("")){
				Showalerts(Alerts.REPORT_CLAIM_START_DATE_OF_INCIDENT);

			}
			else if(SelectedDate.after(CurrentDate)){

				Showalerts(Alerts.REPORT_CLAIM_START_CHECK_DATE);

			}
			else if(delta>30){
				Showalerts(Alerts.REPORT_CLAIM_START_CHECK_DATE);
			}
			else if(StrTimeOfIncident.equalsIgnoreCase("")){
				Showalerts(Alerts.REPORT_CLAIM_START_CHECK_TIME);
			}
			else if (address.equalsIgnoreCase("")){

				Showalerts(Alerts.REPORT_CLAIM_START_CHECK_ADDRESS);

			}
			else if( StateSelected.equalsIgnoreCase("")){
				Showalerts(Alerts.STATE_REQUIRED);
			}
			else if(CitySelected.equalsIgnoreCase("")){
				Showalerts(Alerts.CITY_REQUIRED);
			}


			else {
				
				//Save data in preferences to display on Review screen
				sharedPreferences  = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);	
				editor = sharedPreferences.edit();
				editor.putString("dateOfIncident",dateOfIncident);
				editor.putString("timeOfIncident", StrTimeOfIncident);
				editor.putString("address", address);
				editor.putString("state", StateSelected);
				editor.putString("city", CitySelected);
				editor.commit();

				Log.e("dateOfIncident",dateOfIncident);
		     	Log.e("timeOfIncident", StrTimeOfIncident);
				Log.e("address", address);
				Log.e("state", StateSelected);
				Log.e("city", CitySelected);
				 String StartID=  sharedPreferences.getString("StartKey","");
				 String BasicsID=  sharedPreferences.getString("Basics","");
				 String VehicleID = sharedPreferences.getString("vehiclesKEY", "");
				 String BasicsKEY=  sharedPreferences.getString("BasicsKey","");
				 String WhowasKey = sharedPreferences.getString("whowasKEY","");
				 String MainKey = sharedPreferences.getString("MainKey","");

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
					 myIntent = new Intent(ReportClaimStart.this,
								ReportClaimBasic.class);
						
						startActivity(myIntent);
				 }
				 else if(!MainKey.equals("")){
					 myIntent = new Intent(ReportClaimStart.this,
								ReportClaimBasic.class);
						
						startActivity(myIntent); 
				 }
				 else if(!VehicleID.equals("")){
					 myIntent = new Intent(ReportClaimStart.this,
								ReportClaimBasic.class);
						
						startActivity(myIntent); 
				 }
				 else if(!StartID.equals("")){
					 myIntent = new Intent(ReportClaimStart.this,
								ReportClaimBasic.class);
						
						startActivity(myIntent);
				 }
				 
				 else  if(!BasicsKEY.equals("")){
					 myIntent = new Intent(ReportClaimStart.this,
								ReportClaimBasic.class);
						
						startActivity(myIntent);
				 }else{
					 myIntent = new Intent(ReportClaimStart.this,
								ReportClaimBasic.class);
						myIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
						startActivity(myIntent);
				 }
						
				 }
				
			
				
				
				
				break;
			}
		}
	//}	
@Override
protected void onRestart() {
	// TODO Auto-generated method stub
	super.onRestart();
	Log.e("---------------","RESTART");
	
}

	private void Showalerts(String message2) {
		// TODO Auto-generated method stub
		// AlertDialog.Builder alertDialog = new
		// AlertDialog.Builder(CustodianHomeScreen.this);
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				ReportClaimStart.this);
		// Setting Dialog Title
		alertDialog.setTitle("Custodian Direct");

		// Setting Dialog Message
		alertDialog.setMessage(message2);

		// Setting Icon to Dialog
		alertDialog.setIcon(R.drawable.app_icon_48x48);

		// Setting Positive "Yes" Button
		alertDialog.setNeutralButton("Ok",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				dialog.cancel();
			}
		});
		alertDialog.show();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_PICKER_ID:

			// open datepicker dialog.
			// set date picker for current date
			// add pickerListener listner to date picker
			return new DatePickerDialog(this, pickerListener, year, month, day);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {



		// when dialog box is closed, below method will be called.
		@Override
		public void onDateSet(DatePicker view, int year,
				int month, int day) {
	
			/**
			 * In this the date selected is converted into Date Format using Calender
			 * instance() and take the current system milliseconds to find out the
			 * current date. Both the dates are compared with number of days.
			 * The Difference should not be greater than 30 days for previous months
			 * and should not allow future dates.
			 */

			Calendar c = Calendar.getInstance();
			c.set(year, month, day);

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String formattedDate = sdf.format(c.getTime());
			edtDateOfIncident.setText(formattedDate);
			long   timeTwo = 0;
			long timeOne = 0;


			Log.e("datecreated",formattedDate);
			try {


				SelectedDate = sdf.parse(formattedDate);
				Log.e("StringInDate",""+SelectedDate.getTime());
				timeTwo = SelectedDate.getTime();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			Calendar c9 = Calendar.getInstance();	
			c9.setTimeInMillis(System.currentTimeMillis());

			SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
			String formattedDate1 = sdf1.format(c9.getTime());
			Log.e("current date",formattedDate1);

			try {

				CurrentDate = sdf1.parse(formattedDate1);
				Log.e("StringInDate1",""+CurrentDate.getTime());
				timeOne = CurrentDate.getTime();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//One day calculated
			long oneDay = 1000 * 60 * 60 * 24;
			//Milliseconds of both dates are divided by one day to calculate no of days.
			delta= (timeOne - timeTwo)/oneDay;
			Log.e("no of days",""+delta);



		}
	};

	//Json Parsed data of States.
	@Override
	public void onResponse(final String response) {
		// TODO Auto-generated method stub
					runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					dataAdapter = new ArrayAdapter<String>(ReportClaimStart.this, android.R.layout.simple_dropdown_item_1line);

					ab=new AlertDialog.Builder(ReportClaimStart.this);
					ab.setInverseBackgroundForced(true);
					ab.setTitle("States");

					try {
						json = new JSONObject(response);
						String status = json.optString("success");
						if (status.equalsIgnoreCase("false")) {
							mSplaHandler.sendEmptyMessage(1);
							mSpinnerCity.setText("");

						} else{
					
					JSONArray jsonArray = json.getJSONArray("listItems");

					states.add("");

					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject c = jsonArray.getJSONObject(i);
						String Strstates = c.getString("name");

						states.add(Strstates);
						// Log.e("",""+states);

						// Populate spinner with country names
						dataAdapter.add(Strstates);
						ab.setAdapter(dataAdapter,new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								StateSelected = dataAdapter.getItem(item);
								mSpinnerState.setText(StateSelected);
								mSpinnerCity.setText("");
								dialog.cancel();

							}
						});
					}


					alert = ab.create();
					alert.setInverseBackgroundForced(true);
					alert.show();
					}
					}catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
			
		
	}

	@Override
	public void onError() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAlreadyExist(String response) {
		// TODO Auto-generated method stub

	}

	//Json Parsed data of Cities
	@Override
	public void onResponsePayment(final String response) {
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				cities1 = new ArrayList<String>();

				try {
					json = new JSONObject(response);
					String status = json.optString("success");
					if (status.equalsIgnoreCase("false")) {
						mSplaHandler.sendEmptyMessage(1);
						mSpinnerCity.setText("");

					} else {
						dataAdapter = new ArrayAdapter<String>(ReportClaimStart.this, android.R.layout.simple_dropdown_item_1line);
						if (ab!=null) {
							ab.setTitle("Cities");

						}
						JSONArray jsonArray = json.getJSONArray("listItems");
						//				 cities.add("");

						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject c = jsonArray.getJSONObject(i);
							StrCities = c.getString("name");

							dataAdapter.add(StrCities);

						}

						ab.setAdapter(dataAdapter,new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								CitySelected = dataAdapter.getItem(item);
								mSpinnerCity.setText(CitySelected);
								dialog.dismiss();

							}
						});

						alert = ab.create();
						alert.show();

					}
				}

				catch (Exception e) {
					// TODO: handle exception
				}

			}
		});
		
	}
}
