package com.custodian.ClaimsCenter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.SyncStateContract.Constants;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.custodian.Alerts;
import com.custodian.CustodianHomeScreenMain;
import com.custodian.R;
import com.custodian.CONSTANT.CONSTANTS;
import com.custodian.CustodianWebservices.CommonWebservice;
import com.custodian.CustodianWebservices.ContactUsWebservice;
import com.custodian.CustodianWebservices.CustodianInterface;
import com.custodian.CustodianWebservices.CustomerService;
import com.custodian.CustodianWebservices.CustomerServiceWebService;
import com.custodian.CustodianWebservices.ForgotInterface;
import com.custodian.ModelView.CustodianClaims;
import com.custodian.URLS.WebserviceURLs;
import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ImageChooserListener;
import com.kbeanie.imagechooser.api.ImageChooserManager;
import com.squareup.picasso.Picasso;
/***
 * class used to select the pictures either from gallery or camera and set the bitmap in 
 * imageview . Image Chooser Library has been used for this. In this class
 * one webservice is hit to get the detail of type of vehicle Model and another
 * webservice is hit to get the data for select a repair centre. 
 *  
 *
 */
public class ReportClaimVehicles extends Activity implements
	 OnClickListener, CustomerService, ForgotInterface,
		ImageChooserListener, CustodianInterface {

/*
 * Declaration of views,bitmaps and Strings used.
 */
	//****************************************************************************
	String path;
	Uri imageUri;
	Bitmap imageBitmap;
	ByteArrayOutputStream baos;
	int imagePosition;
	ImageChooserManager imageChooserManager;
	TextView mSpinnerVeh, mSpinnerTow, mSpinnerDmg, mSpinnerAirbags,
			mSpinnerVehicle;
	TextView mHeading, mGarages, txtWhoWAsInvolved, txtUploadPic1,
			txtUploadPic2, txtUploadPic3, txtUploadPic4, txtUploadPic5,
			txtUploadPic6, txtUploadPic7;
	ImageButton mback, mHome;
	ImageView btnContinue, UploadPhoto1, UploadPhoto2, UploadPhoto3,
			UploadPhoto4, UploadPhoto5, UploadPhoto6, UploadPhoto7;

	ArrayList<String> garages;
	private String[] Veh = { "Work", "Business", "Personal" };
	private String[] Tow = { "Yes", "No" };
	JSONObject json;
	Intent myIntent;
	CustodianClaims cust;
	String imageName = null;
	String imagePath = null;
	long imageSize;
	Uri outputFileUri;
	Bitmap bitMap1, bitMap2, bitMap3, bitMap4, bitMap5, bitMap6, bitMap7;
	String selectedImagePath = "";
	public String my_image = "";
	ArrayAdapter<String> dataAdapter;
	SharedPreferences sharedPreferences;
	public static final String MyPREFERENCES = "MyPrefs";
	private static final String filePath = null;
	AlertDialog.Builder ab;
	AlertDialog alert;
	String label = "";
	String SelectedGarages = "";
	Editor editor;
	String WhoWASInvolved = "";
	String strMessageCode = "";
	boolean value;
	String image = "";
	String OWNERID = "";
	File file;
	byte[] data1;
	String ConcatenatedResult = "";
	String StrVehicleBeingUsed = "", StrVehicleTowed = "",
			StrVehicleDamge = "", StrAirbagInflate = "";
	String StrDateOfIncident, StrTimeOfIncident, StrState, StrCity,
			StrVehicleInvolved, StrCauseOfDamage, StrIncidentDesc,
			StrContactNo, StrContactEmail, StrWHOwasInvolved, StrAddress,
			StrDriver, StrWhatHAppend, StrPassenger;
	String Id = "", strPolicyNo = "", strVehicleNumber = "",
			strVehicleMake = "", strVehicleModel = "", StrchassisNo = "",
			StrEnginNo = "", Strid = "", StrPolicy = "", StrvehicleColor = "",
			StrvehicleUse = "", StrVehiclePolicy = "";
	int PolicyInteger = 0, count = 0;
	public static int counter = 0;
	int PolicyLineInteger = 0;
	int strMessageCodeInteger = 0;
	long TimeOfDateOfIncident;
	String picturePath = "", picturePath1 = "", picturePath2 = "",
			picturePath3 = "", picturePath4 = "", picturePath5 = "",
			picturePath6 = "", picturePath7 = "";
	String StrVehiclePolicyID;

	Bitmap resizedbitmap11, resizedbitmap22, resizedbitmap33, resizedbitmap44,
			resizedbitmap55, resizedbitmap66, resizedbitmap77;

	int height, width;
	Handler mSplaHandler=null;
//**************************************************************************************************
	
	//OnBackPressed is used to disable the device back button as back button is used in top bar for navigation.
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custodian_report_claim_vehicles);
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		height = metrics.heightPixels;
		width = metrics.widthPixels;
		garages = new ArrayList<String>();
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
		
		/*
		 * Initialisation of views and listeners
		 */
		//************************************************************************
		Typeface face = Typeface.createFromAsset(getAssets(),
				CONSTANTS.FONT_NAME);
		mSpinnerVeh = (TextView) findViewById(R.id.spinner_ques2);
		mSpinnerVeh.setOnClickListener(this);
		mSpinnerTow = (TextView) findViewById(R.id.spinner_ques3);
		mSpinnerTow.setOnClickListener(this);
		mSpinnerDmg = (TextView) findViewById(R.id.spinner_ques4);
		mSpinnerDmg.setOnClickListener(this);
		btnContinue = (ImageView) findViewById(R.id.btnContinue);
		btnContinue.setOnClickListener(this);
		mSpinnerAirbags = (TextView) findViewById(R.id.spinner_ques5);
		mSpinnerAirbags.setOnClickListener(this);
		mback = (ImageButton) findViewById(R.id.imageView1);
		mback.setOnClickListener(this);
		mHome = (ImageButton) findViewById(R.id.home);
		mHome.setOnClickListener(this);
		mHeading = (TextView) findViewById(R.id.title);
		mHeading.setTypeface(face);
		UploadPhoto1 = (ImageView) findViewById(R.id.upload_photos1);
		UploadPhoto1.setOnClickListener(this);
		UploadPhoto2 = (ImageView) findViewById(R.id.upload_photos2);
		UploadPhoto2.setOnClickListener(this);
		UploadPhoto3 = (ImageView) findViewById(R.id.upload_photos3);
		UploadPhoto3.setOnClickListener(this);
		UploadPhoto4 = (ImageView) findViewById(R.id.upload_photos4);
		UploadPhoto4.setOnClickListener(this);
		UploadPhoto5 = (ImageView) findViewById(R.id.upload_photos5);
		UploadPhoto5.setOnClickListener(this);
		UploadPhoto6 = (ImageView) findViewById(R.id.upload_photos6);
		UploadPhoto6.setOnClickListener(this);
		UploadPhoto7 = (ImageView) findViewById(R.id.upload_photos7);
		UploadPhoto7.setOnClickListener(this);

		txtUploadPic1 = (TextView) findViewById(R.id.myImageViewText1);
		txtUploadPic2 = (TextView) findViewById(R.id.myImageViewText2);
		txtUploadPic3 = (TextView) findViewById(R.id.myImageViewText3);
		txtUploadPic4 = (TextView) findViewById(R.id.myImageViewText4);
		txtUploadPic5 = (TextView) findViewById(R.id.myImageViewText5);
		txtUploadPic6 = (TextView) findViewById(R.id.myImageViewText6);
		txtUploadPic7 = (TextView) findViewById(R.id.myImageViewText7);
		
		//****************************************************************************************
		
		sharedPreferences = this.getSharedPreferences(MyPREFERENCES,
				Context.MODE_PRIVATE);
		OWNERID = sharedPreferences.getString("id", "");
		Log.e("id", OWNERID);
		txtWhoWAsInvolved = (TextView) findViewById(R.id.spinner_vehicleInvolved);
		txtWhoWAsInvolved.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo activeNetworkInfo = connectivityManager
						.getActiveNetworkInfo();
				if (activeNetworkInfo != null
						&& activeNetworkInfo.isConnected()) {
					new CustomerServiceWebService(WebserviceURLs.REPORT_CLAIM_VEHICLES_WHOWASINVOLVED+OWNERID+WebserviceURLs.REPORT_CLAIM_VEHICLES_WHOWASINVOLVED_APPEND,
							ReportClaimVehicles.this, ReportClaimVehicles.this,
							true, "Loading Policy Details...").execute();

				} else {
					 mSplaHandler.sendEmptyMessage(3);
				}

			}
		});

		mGarages = (TextView) findViewById(R.id.spinner_registered);
		mGarages.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo activeNetworkInfo = connectivityManager
						.getActiveNetworkInfo();
				if (activeNetworkInfo != null
						&& activeNetworkInfo.isConnected()) {
				new CommonWebservice(WebserviceURLs.REPORT_CLAIM_VEHICLES_REPAIR_CENTRE, ReportClaimVehicles.this,
						ReportClaimVehicles.this, true,
						"Loading Repair Centres...").execute();
			}
				else {
					 mSplaHandler.sendEmptyMessage(3);
				}
			}
		});
	
	

	}

	private void Showalerts(String message2) {
		// TODO Auto-generated method stub
		// AlertDialog.Builder alertDialog = new
		// AlertDialog.Builder(CustodianHomeScreen.this);
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				ReportClaimVehicles.this);
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
	public void onResponse(final String response) {
		// TODO Auto-generated method stub
runOnUiThread(new Runnable() {
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			json = new JSONObject(response);

			String count = json.getString("count");
			Log.e("count", count);
			if (count.equals("0")) {
				 mSplaHandler.sendEmptyMessage(1);

			} else {
				
				/*
				 * Display customised list on button click using dataAdpter
				 */
				dataAdapter = new ArrayAdapter<String>(ReportClaimVehicles.this,
						android.R.layout.simple_dropdown_item_1line);

				ab = new AlertDialog.Builder(ReportClaimVehicles.this);
				ab.setInverseBackgroundForced(true);
				ab.setTitle("Repair centre");
				
				//json response of repair centre parsed
				JSONArray jsonArray = json.getJSONArray("result");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject oneObject = jsonArray.getJSONObject(i);
					label = oneObject.getString("label");

					dataAdapter.add(label);
					ab.setAdapter(dataAdapter,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int item) {
									SelectedGarages = dataAdapter.getItem(item);
									mGarages.setText(SelectedGarages);
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

	/* get the data from either gallery or camera in OnActivityResult() and display the data (Bitmap)
	 * in imageviews if the data is not null
	 * (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
			if (resultCode == RESULT_OK	&& (requestCode == ChooserType.REQUEST_PICK_PICTURE )) {
				imageChooserManager.submit(requestCode, data);
		}else if(resultCode == RESULT_OK	&& requestCode == ChooserType.REQUEST_CAPTURE_PICTURE ){

			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 3;
			imageBitmap = BitmapFactory.decodeFile(path, options);
			baos = null;
			baos = new ByteArrayOutputStream();
			imageBitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos); // bm

			Uri mImageCaptureUri = getImageUri(ReportClaimVehicles.this,
					imageBitmap);

			
			if(imagePosition==1){
				
			Picasso.with(ReportClaimVehicles.this).load(mImageCaptureUri)
					.resize(width / 5, height / 8).into(UploadPhoto1);
			txtUploadPic1.setText("");

			picturePath1 = path;
			}
			else if(imagePosition==2){
				Picasso.with(ReportClaimVehicles.this).load(mImageCaptureUri)
				.resize(width / 5, height / 8).into(UploadPhoto2);
				txtUploadPic2.setText("");
				picturePath2 = path;
			}
			else if(imagePosition==3){
				Picasso.with(ReportClaimVehicles.this).load(mImageCaptureUri)
				.resize(width / 5, height / 8).into(UploadPhoto3);
				txtUploadPic3.setText("");
				picturePath3 = path;
				
			}
			else if(imagePosition==4){
				Picasso.with(ReportClaimVehicles.this).load(mImageCaptureUri)
				.resize(width / 5, height / 8).into(UploadPhoto4);
				txtUploadPic4.setText("");
				picturePath4 = path;
				
			}
			else if(imagePosition==5){
				Picasso.with(ReportClaimVehicles.this).load(mImageCaptureUri)
				.resize(width / 5, height / 8).into(UploadPhoto5);
				txtUploadPic5.setText("");
				picturePath5 = path;
			
			}
			else if(imagePosition==6){
				Picasso.with(ReportClaimVehicles.this).load(mImageCaptureUri)
				.resize(width / 5, height / 8).into(UploadPhoto6);
				txtUploadPic6.setText("");
				picturePath6 = path;
			
			}
			else if(imagePosition==7){
				Picasso.with(ReportClaimVehicles.this).load(mImageCaptureUri)
				.resize(width / 5, height / 8).into(UploadPhoto7);
				txtUploadPic7.setText("");
				picturePath7 = path;
				
			}
			
			Log.i("PAth====","path"+ path);
			
			
			try {
				baos.flush();
				baos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		}

		}



	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}

	public static Bitmap decodeSampledBitmapFromResource(String path,
			int reqWidth, int reqHeight) {
		Log.d("path", path);
		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path, options);
	}

	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = getContentResolver().query(uri, projection, null, null,
				null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	/**
	 * Custom List like a default picker for displaying list of
	 * How was this vehicle being used at the time of the incident?
	 */
	protected void PopUp() {
		// TODO Auto-generated method stub
		dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line);

		ab = new AlertDialog.Builder(ReportClaimVehicles.this);
		ab.setInverseBackgroundForced(true);
		ab.setTitle("How was this vehicle being used at the time of the incident?");
		ab.setItems(Veh, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				
				StrVehicleBeingUsed = Veh[item];

				mSpinnerVeh.setText(StrVehicleBeingUsed);
			}
		});
		dataAdapter.notifyDataSetChanged();
		AlertDialog alert = ab.create();
		alert.show();
	}

	/**
	 * Custom List like a default picker for displaying list of
	 * Is there damage to your vehicle?
	 */
	protected void PopForVehicleDamage() {
		// TODO Auto-generated method stub
		dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line);

		ab = new AlertDialog.Builder(ReportClaimVehicles.this);
		ab.setInverseBackgroundForced(true);
		ab.setTitle("Is there damage to your vehicle?");
		ab.setItems(Tow, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				
				StrVehicleDamge = Tow[item];

				mSpinnerDmg.setText(StrVehicleDamge);
			}
		});
		dataAdapter.notifyDataSetChanged();
		AlertDialog alert = ab.create();
		alert.show();
	}

	/**
	 * Custom List like a default picker for displaying list of
	 * Was your vehicle towed?
	 */
	protected void PopForVehicleTowed() {
		// TODO Auto-generated method stub
		dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line);

		ab = new AlertDialog.Builder(ReportClaimVehicles.this);
		ab.setInverseBackgroundForced(true);
		ab.setTitle("Was your vehicle towed?");
		ab.setItems(Tow, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				// Toast.makeText(getApplicationContext(), TimeOfIncident[item],
				// Toast.LENGTH_SHORT).show();
				StrVehicleTowed = Tow[item];

				mSpinnerTow.setText(StrVehicleTowed);
			}
		});
		dataAdapter.notifyDataSetChanged();
		AlertDialog alert = ab.create();
		alert.show();
	}

	/**
	 * Custom List like a default picker for displaying list of
	 * "Did any airbags inflate?"
	 */
	protected void Airbags() {
		// TODO Auto-generated method stub
		dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line);

		ab = new AlertDialog.Builder(ReportClaimVehicles.this);
		ab.setInverseBackgroundForced(true);
		ab.setTitle("Did any airbags inflate?");
		ab.setItems(Tow, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
			StrAirbagInflate = Tow[item];
			mSpinnerAirbags.setText(StrAirbagInflate);
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
			myIntent = new Intent(ReportClaimVehicles.this,
					ReportClaimWhoWasInvolved.class);
			sharedPreferences  = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
			editor = sharedPreferences.edit();
		
			if(sharedPreferences.contains("vehiclesKEY")||(sharedPreferences.contains("MainKey"))){
				editor = sharedPreferences.edit();
				editor.remove("vehiclesKEY");
				editor.remove("MainKey");
				editor.commit();
			}
			myIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(myIntent);

			break;

			
			/**
			 *  Below code is used to generate a unique key for the class
			 *  so that data can be refreashed and new claim can be created.
			 */
		case R.id.home:
			myIntent = new Intent(ReportClaimVehicles.this,
					CustodianHomeScreenMain.class);
			sharedPreferences  = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
			 editor = sharedPreferences.edit();	
			 editor.putString("vehiclesKEY", "vehicles");
			 editor.commit();
			 myIntent.putExtra("FromReview", "NavigateFromReportClaim");
			 startActivity(myIntent);
			break;

		case R.id.spinner_ques2:
			PopUp();
			break;

		case R.id.spinner_ques3:
			PopForVehicleTowed();
			break;
		case R.id.spinner_ques4:
			PopForVehicleDamage();
			break;
		case R.id.spinner_ques5:
			Airbags();
			break;

		case R.id.btnContinue:
			if (WhoWASInvolved.equalsIgnoreCase("")) {
				Showalerts(Alerts.REPORT_CLAIM_VEHICLES_WHOWASINVOLVED);
			} else if (StrVehicleBeingUsed.equalsIgnoreCase("")) {
				Showalerts(Alerts.REPORT_CLAIM_VEHICLES_HOW_VEHICLE_WAS_USED);
			} else if (StrVehicleTowed.equalsIgnoreCase("")) {
				Showalerts(Alerts.REPORT_CLAIM_VEHICLES_TOWED);
			} else if (StrVehicleDamge.equalsIgnoreCase("")) {
				Showalerts(Alerts.REPORT_CLAIM_VEHICLES_DAMAGE);
			} else if (StrAirbagInflate.equalsIgnoreCase("")) {
				Showalerts(Alerts.REPORT_CLAIM_VEHICLES_AIRBAGS);
			}

			else {
				//Save data in preferences to show on Review Screen.
				sharedPreferences = this.getSharedPreferences(MyPREFERENCES,
						Context.MODE_PRIVATE);
				editor = sharedPreferences.edit();
				editor.putString("WHOWASINVOLVED", WhoWASInvolved);
				editor.putString("VehicleBeingUsed", StrVehicleBeingUsed);
				editor.putString("VehicleTowed", StrVehicleTowed);
				editor.putString("VehicleDamage", StrVehicleDamge);
				editor.putString("Airbags", StrAirbagInflate);
				editor.putString("RepairCentre", SelectedGarages);
				editor.putString("VehiclesInvolved", strVehicleNumber);
				editor.putString("VehicleColour", StrvehicleColor);
				editor.putString("ChassisNo", StrchassisNo);
				editor.putString("EngineNo", StrEnginNo);
				editor.putString("StrPolicy", StrPolicy);
				editor.putString("picture1", picturePath1);
				editor.putString("picture2", picturePath2);
				editor.putString("picture3", picturePath3);
				editor.putString("picture4", picturePath4);
				editor.putString("picture5", picturePath5);
				editor.putString("picture6", picturePath6);
				editor.putString("picture7", picturePath7);

				editor.putString("Strid", Strid);

				editor.commit();

				Log.e("VehicleBeingUsed", StrVehicleBeingUsed);
				Log.e("VehicleTowed", StrVehicleTowed);
				Log.e("VehicleDamage", StrVehicleDamge);
				Log.e("Airbags", StrAirbagInflate);
				Log.e("RepairCentre", SelectedGarages);
				Log.e("VehicleInvolved", WhoWASInvolved);

				Intent i = getIntent();

				cust = (CustodianClaims) i.getSerializableExtra("cust");
				SharedPreferences sharedPreferences = this
						.getSharedPreferences(MyPREFERENCES,
								Context.MODE_PRIVATE);
				String AccountID = sharedPreferences.getString("id", "");
				StrDateOfIncident = sharedPreferences.getString(
						"dateOfIncident", "");
				StrTimeOfIncident = sharedPreferences.getString(
						"timeOfIncident", "");
				StrState = sharedPreferences.getString("state", "");
				StrCity = sharedPreferences.getString("city", "");
				StrVehicleInvolved = sharedPreferences.getString("state", "");
				StrCauseOfDamage = sharedPreferences.getString("VehicleDamage",
						"");
				StrIncidentDesc = sharedPreferences
						.getString("Description", "");
				StrContactNo = sharedPreferences.getString("mobilePhone", "");
				StrContactEmail = sharedPreferences.getString("emailAddress",
						"");
				StrWHOwasInvolved = sharedPreferences.getString(
						"WHOWASINVOLVED", "");
				StrAddress = sharedPreferences.getString("address", "");
				StrDriver = sharedPreferences.getString("Driver", "");
				StrWhatHAppend = sharedPreferences.getString("WhatHAppend", "");
				StrPassenger = sharedPreferences.getString("Passenger", "");

				try {
					String startTime = StrDateOfIncident;
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"dd-MM-yyyy");
					Date date1 = dateFormat.parse(startTime);
					TimeOfDateOfIncident = date1.getTime();
					Log.e("Date ", "" + date1.getTime());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

					myIntent = new Intent(ReportClaimVehicles.this,
							ReportClaimReview.class);
					myIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					startActivity(myIntent);
		
			}
			break;

		case R.id.upload_photos1:
			resizedbitmap11 = null;
			GalleryORCamera(1);
			break;

		case R.id.upload_photos2:
			resizedbitmap22 = null;
			GalleryORCamera(2);
			break;

		case R.id.upload_photos3:
			resizedbitmap33 = null;
			GalleryORCamera(3);
			break;

		case R.id.upload_photos4:
			resizedbitmap44 = null;
			GalleryORCamera(4);
			break;

		case R.id.upload_photos5:
			resizedbitmap55 = null;
			GalleryORCamera(5);
			break;

		case R.id.upload_photos6:
			resizedbitmap66 = null;
			GalleryORCamera(6);
			break;

		case R.id.upload_photos7:
			resizedbitmap77 = null;
			GalleryORCamera(7);
			break;
		}
	}

	private void GalleryORCamera(final int imagePosition) {
		// TODO Auto-generated method stub

		
		this.imagePosition=imagePosition;
		
		final CharSequence[] items = { "Gallery", "Camera" };

		AlertDialog.Builder builder = new AlertDialog.Builder(
				ReportClaimVehicles.this);
		builder.setTitle("Choose image");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (items[item].equals("Camera")) {
//*************************************
					takePhoto();
					//*********************************
			
				} else if (items[item].equals("Gallery")) {
					
					//open gallery of phone
					imageChooserManager = new ImageChooserManager(
							ReportClaimVehicles.this,
							ChooserType.REQUEST_PICK_PICTURE);

					imageChooserManager
							.setImageChooserListener(ReportClaimVehicles.this);
					try {
						imageChooserManager.choose();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (items[item].equals("")) {
					dialog.dismiss();
				}
			}
		});
		builder.show();

	}
	
	
	//Open Default camera
	public void takePhoto() {
		path = Environment.getExternalStorageDirectory() + "/photo1"+System.currentTimeMillis()+".jpg";

		File file = new File(path);
		imageUri = Uri.fromFile(file);
		Intent intent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(intent, ChooserType.REQUEST_CAPTURE_PICTURE);

	}


	
	@Override
	public void onResponsePayment(final String response) {
		// TODO Auto-generated method stub
runOnUiThread(new Runnable() {
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			json = new JSONObject(response);
			// arraylist = new ArrayList<CertificateOfDocuments>();
			String count = json.getString("count");
			Log.e("count", count);
			if (count.equals("0")) {
				 mSplaHandler.sendEmptyMessage(1);
				// Showalerts("No records found");

			} else {
				
				dataAdapter = new ArrayAdapter<String>(ReportClaimVehicles.this,
						android.R.layout.simple_dropdown_item_1line);

				ab = new AlertDialog.Builder(ReportClaimVehicles.this);
				ab.setInverseBackgroundForced(true);
				ab.setTitle("Which of your vehicle was involved?");
				JSONArray jsonArray = json.getJSONArray("result");

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject oneObject = jsonArray.getJSONObject(i);
					// cust = new CertificateOfDocuments();
					Id = oneObject.getString("id");
					// cust.setID(Id);
					strPolicyNo = oneObject.getString("policyNo");
					// cust.setPolicyNo(strPolicyNo);
					strVehicleNumber = oneObject.getString("vehicleNo");
					// cust.setVehicleNumber(strVehicleNumber);
					strVehicleMake = oneObject.getString("vehicleMake");
					// cust.setVehicleMake(strVehicleMake);
					strVehicleModel = oneObject.getString("vehicleModel");
					// cust.setVehicleModel(strVehicleModel);
					// arraylist.add(cust);
					StrchassisNo = oneObject.getString("chassisNo");
					StrEnginNo = oneObject.getString("engineNo");
					Strid = oneObject.getString("id");
					StrPolicy = oneObject.getString("policy");
					StrvehicleColor = oneObject.getString("vehicleColor");
					StrvehicleUse = oneObject.getString("vehicleUse");

					ConcatenatedResult = strVehicleNumber + "-"
							+ strVehicleMake + "-" + strVehicleModel;
					Log.e("Concatenated", "" + ConcatenatedResult);

					dataAdapter.add(ConcatenatedResult);
					//
					ab.setAdapter(dataAdapter,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int item) {
									WhoWASInvolved = dataAdapter.getItem(item);
									txtWhoWAsInvolved.setText(WhoWASInvolved);
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
	public void onImageChosen(final ChosenImage arg0) {
		// TODO Auto-generated method stub

		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
if(arg0 != null){
	

				int rotation = 0;

				try {
					ExifInterface exifInterface = new ExifInterface(
							arg0.getFilePathOriginal());
					int exifRotation = exifInterface.getAttributeInt(
							ExifInterface.TAG_ORIENTATION,
							ExifInterface.ORIENTATION_UNDEFINED);

					if (exifRotation != ExifInterface.ORIENTATION_UNDEFINED) {
						switch (exifRotation) {
						case ExifInterface.ORIENTATION_ROTATE_180:
							rotation = 180;
							break;
						case ExifInterface.ORIENTATION_ROTATE_270:
							rotation = 270;
							break;
						case ExifInterface.ORIENTATION_ROTATE_90:
							rotation = 90;
							break;
						}
					}
				} catch (IOException e) {
				}
				Matrix matrix = null;
				if (rotation != 0) {
					matrix = new Matrix();
					matrix.setRotate(rotation);

				}
				Bitmap mBitmap = decodeFileNew(new File(
						arg0.getFilePathOriginal()));
				Bitmap bitmap = Bitmap.createBitmap(mBitmap, 0, 0,
						mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);

				Uri mImageCaptureUri = getImageUri(ReportClaimVehicles.this,
						bitmap);

				
				if(imagePosition==1){
					
				Picasso.with(ReportClaimVehicles.this).load(mImageCaptureUri)
						.resize(width / 5, height / 8).into(UploadPhoto1);
				txtUploadPic1.setText("");
				File mFile = new File(arg0.getFileThumbnail());

				picturePath1 = arg0.getFilePathOriginal();
				//Toast.makeText(ReportClaimVehicles.this,picturePath1, Toast.LENGTH_LONG).show();
				}
				else if(imagePosition==2){
					Picasso.with(ReportClaimVehicles.this).load(mImageCaptureUri)
					.resize(width / 5, height / 8).into(UploadPhoto2);
					txtUploadPic2.setText("");
					picturePath2 = arg0.getFilePathOriginal();
					//Toast.makeText(ReportClaimVehicles.this,picturePath2, Toast.LENGTH_LONG).show();
				}
				else if(imagePosition==3){
					Picasso.with(ReportClaimVehicles.this).load(mImageCaptureUri)
					.resize(width / 5, height / 8).into(UploadPhoto3);
					txtUploadPic3.setText("");
					picturePath3 = arg0.getFilePathOriginal();
					//Toast.makeText(ReportClaimVehicles.this,picturePath3, Toast.LENGTH_LONG).show();
				}
				else if(imagePosition==4){
					Picasso.with(ReportClaimVehicles.this).load(mImageCaptureUri)
					.resize(width / 5, height / 8).into(UploadPhoto4);
					txtUploadPic4.setText("");
					picturePath4 = arg0.getFilePathOriginal();
					//Toast.makeText(ReportClaimVehicles.this,picturePath4, Toast.LENGTH_LONG).show();
				}
				else if(imagePosition==5){
					Picasso.with(ReportClaimVehicles.this).load(mImageCaptureUri)
					.resize(width / 5, height / 8).into(UploadPhoto5);
					txtUploadPic5.setText("");
					picturePath5 = arg0.getFilePathOriginal();
				//	Toast.makeText(ReportClaimVehicles.this,picturePath5, Toast.LENGTH_LONG).show();
				}
				else if(imagePosition==6){
					Picasso.with(ReportClaimVehicles.this).load(mImageCaptureUri)
					.resize(width / 5, height / 8).into(UploadPhoto6);
					txtUploadPic6.setText("");
					picturePath6 = arg0.getFilePathOriginal();
				//	Toast.makeText(ReportClaimVehicles.this,picturePath6, Toast.LENGTH_LONG).show();
				}
				else if(imagePosition==7){
					Picasso.with(ReportClaimVehicles.this).load(mImageCaptureUri)
					.resize(width / 5, height / 8).into(UploadPhoto7);
					txtUploadPic7.setText("");
					picturePath7 = arg0.getFilePathOriginal();
					//Toast.makeText(ReportClaimVehicles.this,picturePath7, Toast.LENGTH_LONG).show();
				}
			
				Log.i("PAth====", arg0.getFilePathOriginal());
			
				
}
			}
		});
	

	}

	private Bitmap decodeFileNew(File f) {
		try {
			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// The new size we want to scale to
			final int REQUIRED_SIZE = 200;

			// Find the correct scale value. It should be the power of 2.
			int scale = 1;
			while (o.outWidth / scale / 2 >= REQUIRED_SIZE
					&& o.outHeight / scale / 2 >= REQUIRED_SIZE)
				scale *= 2;

			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
	}

	public static Uri getImageUri(Context inContext, Bitmap inImage) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
		String path = Images.Media.insertImage(inContext.getContentResolver(),
				inImage, "Title", null);
		return Uri.parse(path);
	}

	@Override
	public void onError(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Response(String response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Error() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void AlreadyExist(String response) {
		// TODO Auto-generated method stub
		
	}
}