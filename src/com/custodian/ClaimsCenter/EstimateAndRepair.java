package com.custodian.ClaimsCenter;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.custodian.Alerts;
import com.custodian.CustodianHomeScreenMain;
import com.custodian.DisplayMessageAlert;
import com.custodian.R;
import com.custodian.CONSTANT.CONSTANTS;
import com.custodian.CustodianWebservices.CommonWebservice;
import com.custodian.CustodianWebservices.CustodianInterface;
import com.custodian.CustodianWebservices.CustomerService;
import com.custodian.CustodianWebservices.CustomerServiceWebService;
import com.custodian.CustodianWebservices.EstimateInterface;
import com.custodian.CustodianWebservices.EstimateWebservice;
import com.custodian.ModelView.LatLongMOdel;
import com.custodian.URLS.WebserviceURLs;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
/**
 * EstimateAndRepair class is used to show the location on map.
 * Based on which city and state is selected ,location pinpoint will appear
 * on map. City is dependent on State . City can only be selected when
 * state is selected.
 */
public class EstimateAndRepair extends FragmentActivity implements
		android.widget.AdapterView.OnItemSelectedListener, LocationListener,
		CustodianInterface, CustomerService, OnClickListener, EstimateInterface {
	//Declaration of views used in layout
	ImageView  mFindLocations;
	ImageButton mHome;
	String StateSelected = "";
	String CitySelected = "";
	ImageButton mback;
	GoogleMap googleMap;
	TextView mHeading;
	Spinner mSpinnerState, mSpinnerCity;
	String StrCities, selected = "", selectedCity = "";

	ArrayList<String> states;
	ArrayList<String> cities1, cities2;
	JSONObject json;
	LatLongMOdel latlong;
	ArrayList<LatLongMOdel> arraylist;
	TextView txtStates,txtCities;
	ArrayAdapter<String> dataAdapter ;
	AlertDialog.Builder ab;
	AlertDialog alert;
	String Strstates = "";
	Handler mSplaHandler=null;
	// OnBackPressed is used to disable the device back button as back button is used in top bar for navigation.
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

	}

			@Override
		protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		
		
		setContentView(R.layout.custodian_estimate_and_repair_locations);
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
		 
		Typeface face = Typeface.createFromAsset(getAssets(),
				CONSTANTS.FONT_NAME);
		mHeading = (TextView) findViewById(R.id.title);
		mHeading.setTypeface(face);
		mback = (ImageButton) findViewById(R.id.imageView1);
		mHome = (ImageButton) findViewById(R.id.home);
		mFindLocations = (ImageView) findViewById(R.id.find_locations);
		mFindLocations.setOnClickListener(this);
		states = new ArrayList<String>();
		cities1 = new ArrayList<String>();
		cities2 = new ArrayList<String>();
		txtCities = (TextView)findViewById(R.id.spinner_city);
		txtCities.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if(StateSelected.equalsIgnoreCase("")){
					Showalerts(Alerts.SELECT_STATE);
					
		}else{
			txtCities.setTextColor(Color.parseColor("#888888")); 
			txtCities.setText("City");
			
			
			ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	     if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){
			new CustomerServiceWebService(WebserviceURLs.SELECT_CITY+StateSelected,
					EstimateAndRepair.this,
					EstimateAndRepair.this, true,"Loading cities...").execute();
		}
	     else{
	    	 mSplaHandler.sendEmptyMessage(2);
	     }
		}
				
			}
		});
		txtStates = (TextView)findViewById(R.id.spinner_state);
		txtStates.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ConnectivityManager connectivityManager 
		          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		     if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){
				new CommonWebservice(WebserviceURLs.SELECT_STATE, EstimateAndRepair.this,
					EstimateAndRepair.this, true,"Loading states...").execute();	
		     }
		     else{
		    	 mSplaHandler.sendEmptyMessage(2);
		     }
			}
		});

		mHome.setOnClickListener(new OnClickListener() {
//Navigate to home screen
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(EstimateAndRepair.this,
						CustodianHomeScreenMain.class);
				startActivity(myIntent);
			}
		});

		mback.setOnClickListener(new OnClickListener() {
//Navigate to previous screen
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(EstimateAndRepair.this,
						ClaimsCenterMenuScreen.class);
				startActivity(myIntent);
			}
		});

		// Getting Google Play availability status
		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getBaseContext());

		// Showing status
		if (status != ConnectionResult.SUCCESS) { // Google Play Services are
													// not available

			int requestCode = 10;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this,
					requestCode);
			dialog.show();

		} else { // Google Play Services are available

			// Getting reference to the SupportMapFragment of activity_main.xml
			SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map);

			// Getting GoogleMap object from the fragment
			googleMap = fm.getMap();

			// Enabling MyLocation Layer of Google Map
			googleMap.setMyLocationEnabled(true);

			// Getting LocationManager object from System Service
			// LOCATION_SERVICE
			LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

			// Creating a criteria object to retrieve provider
			Criteria criteria = new Criteria();

			// Getting the name of the best provider
			String provider = locationManager.getBestProvider(criteria, true);

			// Getting Current Location
			Location location = locationManager.getLastKnownLocation(provider);

			if (location != null) {
				onLocationChanged(location);
			}
			locationManager.requestLocationUpdates(provider, 20000, 0, this);
		}
	}

	@Override
	public void onLocationChanged(Location location) {

		// TextView tvLocation = (TextView) findViewById(R.id.tv_location);

		// Getting latitude of the current location
		double latitude = location.getLatitude();

		// Getting longitude of the current location
		double longitude = location.getLongitude();

		// Creating a LatLng object for the current location
		LatLng latLng = new LatLng(latitude, longitude);

		
		googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude,
				longitude)));
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

		mSpinnerState.setSelection(position);
		String selState = (String) mSpinnerState.getSelectedItem();
		Toast.makeText(EstimateAndRepair.this, selState, Toast.LENGTH_LONG)
				.show();
		if (selState.equals("")) {
			new DisplayMessageAlert(EstimateAndRepair.this, Alerts.SELECT_STATE, "Ok");
		}
		mSpinnerCity.setSelection(position);
		String selCity = (String) mSpinnerCity.getSelectedItem();
		if (selCity.equals("")) {
			new DisplayMessageAlert(EstimateAndRepair.this,Alerts.SELECT_CITY, "Ok");
		}
		Toast.makeText(EstimateAndRepair.this, selCity, Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResponse(final String response) {
		// TODO Auto-generated method stub
		Log.e("Estimnate and Repair Respnse ", "" + response);
		runOnUiThread(new  Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					/*
					 * dataAdapter is used to display list on button click .
					 * Its a customised picker.
					 */
					dataAdapter = new ArrayAdapter<String>(EstimateAndRepair.this, android.R.layout.simple_dropdown_item_1line);
		            
		            ab=new AlertDialog.Builder(EstimateAndRepair.this);
		            ab.setInverseBackgroundForced(true);
		            ab.setTitle("States");
		            
		            //Json Parsed
					json = new JSONObject(response);
					String status = json.optString("success");
					if (status.equalsIgnoreCase("false")) {
						mSplaHandler.sendEmptyMessage(1);
					}else{
						
				
					JSONArray jsonArray = json.getJSONArray("listItems");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject c = jsonArray.getJSONObject(i);
						 Strstates = c.getString("name");

						// Populate spinner with country names
						 dataAdapter.add(Strstates);
			             ab.setAdapter(dataAdapter,new DialogInterface.OnClickListener() {
			                 public void onClick(DialogInterface dialog, int item) {
			              
			                StateSelected = dataAdapter.getItem(item);
			                txtStates.setTextColor(Color.BLACK);
			                txtStates.setText(StateSelected);
			                txtCities.setTextColor(Color.parseColor("#888888"));
			                txtCities.setText("City");
			                 	dialog.cancel();

			                 }
			             });
					}

		alert = ab.create();
		alert.setInverseBackgroundForced(true);
		            alert.show();


					}
				} catch (JSONException e) {
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

	private void Showalerts(String message) {
		// TODO Auto-generated method stub
	
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				EstimateAndRepair.this);
		// Setting Dialog Title
		alertDialog.setTitle("Custodian Direct");

		// Setting Dialog Message
		alertDialog.setMessage(message);

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
	public void onResponsePayment(final String response) {
		// TODO Auto-generated method stub
		
		/*
		 * dataAdapter is used to display list on button click .
		 * Its a customised picker.
		 */
		runOnUiThread( new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					json = new JSONObject(response);
					String status = json.optString("success");
					if (status.equalsIgnoreCase("false")) {
						mSplaHandler.sendEmptyMessage(1);

						//Showalerts(Alerts.NO_RECORDS_FOUND);


					} else {
						dataAdapter = new ArrayAdapter<String>(EstimateAndRepair.this, android.R.layout.simple_dropdown_item_1line);
				           if (ab!=null) {
				        	    ab.setTitle("Cities");
				    				
						}
			            
			            
						JSONArray jsonArray = json.getJSONArray("listItems");

						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject c = jsonArray.getJSONObject(i);
							StrCities = c.getString("name");
							dataAdapter.add(StrCities);
						}
					
						 ab.setAdapter(dataAdapter,new DialogInterface.OnClickListener() {
			                 public void onClick(DialogInterface dialog, int item) {
			                CitySelected = dataAdapter.getItem(item);
			                txtCities.setTextColor(Color.BLACK);
			            txtCities.setText(CitySelected);
			            
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.find_locations:
			//clear the pinpoint when webservice is hit again to show the updated pinpoints
			googleMap.clear();

			if(StateSelected.equals("")){
				Showalerts(Alerts.STATE_REQUIRED);
			}
			else if(CitySelected.equals("")){
				Showalerts(Alerts.CITY_REQUIRED);
			}
			
			/***
			 *  on selecting city if the city contains space eg. CITY A
			 *  then below code is used to remove the space to make it as
			 *  CITYA as it gives error on CITY A
			 */
			//*********************************************************************************
			else if(CitySelected.contains(" ")){
				String CitySelectedWithoutSPace = CitySelected.replace(" ", "");
				Log.e("CitySelectedWithoutSPace",CitySelectedWithoutSPace);
				
				
				ConnectivityManager connectivityManager 
		          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		     if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){
				new EstimateWebservice(WebserviceURLs.ESTIMATE_REPAIR_FIND_LOCATIONS+ StateSelected+ "&city=" +  CitySelectedWithoutSPace, EstimateAndRepair.this,
						EstimateAndRepair.this, true).execute();
		     }
		     else{
		    	 mSplaHandler.sendEmptyMessage(2);
		    	// Showalerts(Alerts.CHECK_INTERNET);
		     }
			}
			else{
		
				ConnectivityManager connectivityManager 
		          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		     if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){
				new EstimateWebservice(WebserviceURLs.ESTIMATE_REPAIR_FIND_LOCATIONS+ StateSelected+ "&city=" +  CitySelected, EstimateAndRepair.this,
						EstimateAndRepair.this, true).execute();
		     }else{
		    	 mSplaHandler.sendEmptyMessage(2);
		    	// Showalerts(Alerts.CHECK_INTERNET);
		     }
			}
			//********************************************************************************************
			
			

		}
	}

	@Override
	public void onGetResult(String response) {
		// TODO Auto-generated method stub
		try {
			arraylist = new ArrayList<LatLongMOdel>();

		
			json = new JSONObject(response);
			String count = json.getString("count");
			Log.e("count", count);
			if (count.equals("0")) {
				 mSplaHandler.sendEmptyMessage(1);
			//	Showalerts(Alerts.NO_RECORDS_FOUND);
			}

			else {
				JSONArray jsonArray = json.getJSONArray("result");
				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject oneObject = jsonArray.getJSONObject(i);
					latlong = new LatLongMOdel();
					String strLatitude = oneObject.getString("latitude");
					latlong.setLatitude(strLatitude);
					String strLongitude = oneObject.getString("longitude");
					latlong.setLongitude(strLongitude);
					String strLabel = oneObject.getString("label");
					latlong.setLabel(strLabel);
					String strCity = oneObject.getString("city");
					latlong.setCity(strCity);
					String strCountry = oneObject.getString("country");
					latlong.setCountry(strCountry);

					Log.e("Lat", strLatitude);
					Log.e("Long", strLongitude);
					Log.e("city", strCity);
					Log.e("country", strCountry);
					arraylist.add(latlong);

					ShowMarkers();

				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
/*
 * ShowMarks() is used to show the pinpoints on map using arraylist
 */
	private void ShowMarkers() {
		// TODO Auto-generated method stub

		for (int list = 0; list < arraylist.size(); list++) {
			double lati = Double.parseDouble(latlong.getLatitude());
			double longLat = Double.parseDouble(latlong.getLongitude());
			Log.e("DLat", "" + lati);
			Log.e("DLong", "" + longLat);

			googleMap.animateCamera(CameraUpdateFactory.zoomTo(5));
			googleMap.addMarker(new MarkerOptions()
					.position(new LatLng(lati, longLat))
					.title(latlong.getLabel())
					.snippet(
							latlong.getCity() + " " + "," + " "
									+ latlong.getCountry()));
		}

	}

}
