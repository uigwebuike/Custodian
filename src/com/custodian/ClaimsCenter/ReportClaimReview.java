package com.custodian.ClaimsCenter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.custodian.Alerts;
import com.custodian.CustodianHomeScreenMain;
import com.custodian.DisplayMessageAlert;
import com.custodian.R;
import com.custodian.CONSTANT.CONSTANTS;
import com.custodian.CustodianWebservices.ContactUsWebservice;
import com.custodian.CustodianWebservices.CustodianInterface;
import com.custodian.CustodianWebservices.ForgotInterface;
import com.custodian.CustodianWebservices.ForgotWebservice;
import com.custodian.ModelView.CustodianClaims;
import com.custodian.URLS.WebserviceURLs;
/**
 * 
 * ReportClaimReview deals with displaying data saved from previous screens in fields
 * and submitting  the details and uploading pictures if choosen
 * on submit button to create the new  claim .
 *
 */
public class ReportClaimReview  extends Activity implements OnClickListener, CustodianInterface, ForgotInterface{
	/*
	 * Declaration of views,bitmaps and Strings used.
	 */
	 TextView mHeading,txtDateOfIncident,txtTimeofIncident,txtState,txtCity,txtVehicleInvolved,
	 txtCauseOfDamage,txtIncidentDesc,txtContactName,txtContactPhone,
	 txtDateOfIncidentValue,txtTimeofIncidentValue,txtStateValue,txtCityValue,txtVehicleInvolvedValue,
	 txtCauseOfDamageValue,txtIncidentDescValue,txtContactNameValue,txtContactPhoneValue,txtWhoWAsInvolved,
	 txtVehicleModel,txtVehicleUse,txtVehicleUseValue,txtVehicleDamage,txtVehicleDamage1,txtVehicleTowed,txtVehicleAirbags,txtVehiclecolor,
	 txtVehicleNo,txtChassisNo,txtEngineNo,txtVehicleDamageValue,txtVehicleTowedValue,txtVehicleAirbagsValue,
	 txtVehiclecolorValue,txtDriverOfVehicle,txtDriverOfVehicleValue,txtSelectGarage,
	 txtSelectGarageValue,txtPhotographs,txtPoliceReport,txtRepairEstimate,
	 txtVehicleNoValue,txtChassisNoValue,txtEngineNoValue;
	 ImageButton mback,mHome;
	 Intent myIntent;
	 List<String> listDataHeader;
	 HashMap<String, List<String>> listDataChild;
	 ExpandableListView expListView;
	 String StrDateOfIncident,StrTimeOfIncident,StrState,StrCity,StrVehicleInvolved,StrCauseOfDamage,
	 StrIncidentDesc,StrContactNo,StrContactEmail,StrWHOwasInvolved,StrVehicleModel,
	 StrVehiclesInvolvedNo,StrVehicleUse,StrWasVehicleDamaged,StrWasVehicleTowed,StrAirbags,
	 StrVehicleColour,StrChassisNo,StrEngineNo,StrWasVehicleDamagedValue,StrWasVehicleTowedValue,StrAirbagsValue,
	 StrVehicleColourValue,StrChassisNoValue,StrEngineNoValue,StrDriver,StrRepairCentre,StrAddress,
	 StrWhatHAppend,StrPassenger,StrVehiclePolicy,StrVehiclePolicyID,StrNoOfPassengers;
	 ImageButton BtnExpand;
	 RelativeLayout relExpand;
	 boolean visibility_Flag = false;
	 Bitmap pic11,pic22,pic33,pic44,pic55,pic66,pic77;
	 Editor editor;
	 public String my_image = "";
		CustodianClaims cust;	
     String imagePath = null;
     String   imageNamePic1 = null;
     String   imageNamePic2 = null;
     String   imageNamePic3 = null;
     String   imageNamePic4 = null;
     String   imageNamePic5  = null;
     String   imageNamePic6 = null;
     String   imageNamePic7 = null;
     Intent mIntent;

 	String picture_One = "",picture_Two = "",picture_Three = "",picture_Four = "",
 			picture_Five = "",picture_Six = "",picture_Seven= "";

	 String pic1,pic2,pic3,pic4,pic5,pic6,pic7;
	 ImageView uploadedPic1,uploadedPic2,uploadedPic3,uploadedPic4,uploadedPic5,
	 uploadedPic6,uploadedPic7,BtnReviewSubmit;
	 long TimeOfDateOfIncident ;
	 String OWNERID = "";
	 JSONObject json;
	 boolean value;
	 int PolicyInteger = 0,count = 0;
		int PolicyLineInteger = 0;
		String strMessageCode  = "";
		int strMessageCodeInteger  = 0;
		String SelectedImage;
		Boolean IsUploaded = true;
		int NoOfPassengers ;
		ImageButton BtnEdtStart,BtnEdtBasics,BtnEdtYourContactInfo,BtnEdtVehicles,BtnEdtRegistered,BtnUploaded;
		Bitmap  resizedbitmap11 = null, resizedbitmap22=null,resizedbitmap33=null,
				resizedbitmap44=null,resizedbitmap55=null,resizedbitmap66=null,resizedbitmap77=null;
		
	 SharedPreferences sharedPreferences;
	public static final String MyPREFERENCES = "MyPrefs" ;
	
	//OnBackPressed is used to disable the device back button as back button is used in top bar for navigation.
	 @Override
		public void onBackPressed() {
			// TODO Auto-generated method stub
			
		}
	@Override
	   protected void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.custodian_report_claim_review);
//	       mSplaHandler=new Handler(){
//	        	@Override
//	        	public void handleMessage(Message msg) {
//	        		// TODO Auto-generated method stub
//	        		super.handleMessage(msg);
//	        	if (msg.what==1) {
//	        		Showalerts("Claim has been reported successfully.");
//				}
//				else if (msg.what==3) {
//			    	 Showalerts(Alerts.CHECK_INTERNET);
//
//				}
//	        	}
//	        };

	       /*
			 * Initialisation of views and listeners
			 */
	       //*******************************************************************
	       Typeface face = Typeface.createFromAsset(getAssets(),CONSTANTS.FONT_NAME);
	       mHeading = (TextView)findViewById(R.id.title);
	       mHeading.setTypeface(face);
	       mback = (ImageButton)findViewById(R.id.imageView1);
	        mback.setOnClickListener(this);
	        mHome = (ImageButton)findViewById(R.id.home);
	        mHome.setOnClickListener(this);
	        relExpand = (RelativeLayout)findViewById(R.id.Edit_rel_eighth);
	        
	        BtnExpand = (ImageButton)findViewById(R.id.report_Plus_Button);
	        BtnExpand.setOnClickListener(this);
	        
	        BtnReviewSubmit = (ImageView)findViewById(R.id.review_submit);
	        BtnReviewSubmit.setOnClickListener(this);
	       
			uploadedPic1 = (ImageView)findViewById(R.id.review_upload_photos1);	
			uploadedPic2 = (ImageView)findViewById(R.id.review_upload_photos2);
			uploadedPic3 = (ImageView)findViewById(R.id.review_upload_photos3);
			uploadedPic4 = (ImageView)findViewById(R.id.review_upload_photos4);
			uploadedPic5 = (ImageView)findViewById(R.id.review_upload_photos5);
			uploadedPic6 = (ImageView)findViewById(R.id.review_upload_photos6);
			uploadedPic7 = (ImageView)findViewById(R.id.review_upload_photos7);
			
			BtnEdtStart = (ImageButton)findViewById(R.id.edit_button_first);
			BtnEdtStart.setOnClickListener(this);
			BtnEdtBasics = (ImageButton)findViewById(R.id.edit_button_second);
			BtnEdtBasics.setOnClickListener(this);
			BtnEdtYourContactInfo  = (ImageButton)findViewById(R.id.edit_button_third);
			BtnEdtYourContactInfo.setOnClickListener(this);
			BtnEdtVehicles = (ImageButton)findViewById(R.id.edit_button_fourth);
			BtnEdtVehicles.setOnClickListener(this);
			BtnEdtRegistered = (ImageButton)findViewById(R.id.edit_button_fifth);
			BtnEdtRegistered.setOnClickListener(this);
			BtnUploaded = (ImageButton)findViewById(R.id.edit_button_sixth);
			BtnUploaded.setOnClickListener(this);
			
	        txtDateOfIncident = (TextView)findViewById(R.id.report_dateOfincident);
	        txtDateOfIncident.setTypeface(face);
	        txtDateOfIncidentValue = (TextView)findViewById(R.id.report_dateOfincident_value);
	        txtDateOfIncidentValue.setTypeface(face);
	        txtTimeofIncident = (TextView)findViewById(R.id.report_timeOfincident);
	        txtTimeofIncident.setTypeface(face);
	        txtTimeofIncidentValue = (TextView)findViewById(R.id.report_timeOfincident_value);
	        txtTimeofIncidentValue.setTypeface(face);
	        txtState = (TextView)findViewById(R.id.report_start);
	        txtState.setTypeface(face);
	        txtStateValue  = (TextView)findViewById(R.id.report_start_value);
	        txtStateValue.setTypeface(face);
	        txtCity = (TextView)findViewById(R.id.report_city);
	        txtCity.setTypeface(face);
	        txtCityValue = (TextView)findViewById(R.id.report_city_value);
	        txtCityValue.setTypeface(face);
	        txtVehicleInvolved = (TextView)findViewById(R.id.report_vehicle_involved);
	        txtVehicleInvolved.setTypeface(face);
	        txtVehicleInvolvedValue = (TextView)findViewById(R.id.report_vehicle_involved_value);
	        txtVehicleInvolvedValue.setTypeface(face);
	        txtCauseOfDamage = (TextView)findViewById(R.id.report_cause_damge);
	        txtCauseOfDamage.setTypeface(face);
	        txtCauseOfDamageValue = (TextView)findViewById(R.id.report_cause_damge_value);
	        txtCauseOfDamageValue.setTypeface(face);
	        txtIncidentDesc = (TextView)findViewById(R.id.report_cause_desc);
	        txtIncidentDesc.setTypeface(face);
	        txtIncidentDescValue = (TextView)findViewById(R.id.report_cause_desc_value);
	        txtIncidentDescValue.setTypeface(face);
	        txtContactName = (TextView)findViewById(R.id.report_Contact_number);
	        txtContactName.setTypeface(face);
	        txtContactNameValue = (TextView)findViewById(R.id.report_Contact_number_value);
	        txtContactNameValue.setTypeface(face);
	        txtContactPhone = (TextView)findViewById(R.id.report_Contact_Email);
	        txtContactPhone.setTypeface(face);
	        txtContactPhoneValue = (TextView)findViewById(R.id.report_Contact_Email_value);
	        txtContactPhoneValue.setTypeface(face);
	        txtVehicleModel = (TextView)findViewById(R.id.report_vehicle_Model);
	        txtVehicleModel.setTypeface(face, Typeface.BOLD);
	        txtVehicleUse = (TextView)findViewById(R.id.report_review_Vehicle_Use);
	        txtVehicleUse.setTypeface(face);
	        txtVehicleDamage = (TextView)findViewById(R.id.report_review_was_vehicle_damaged);
	        txtVehicleDamage.setTypeface(face);
	        txtVehicleDamage1 = (TextView)findViewById(R.id.report_review_was_vehicle_damaged1);
	        txtVehicleDamage1.setTypeface(face);
	        txtVehicleTowed = (TextView)findViewById(R.id.report_review_Was_Vehicle_Towed);
	        txtVehicleTowed.setTypeface(face);
	        txtVehicleAirbags = (TextView)findViewById(R.id.report_review_Airbags_inflate);
	        txtVehicleAirbags.setTypeface(face);
	        txtVehiclecolor = (TextView)findViewById(R.id.report_review_Vehicle_Color);
	        txtVehiclecolor.setTypeface(face);
	        txtVehicleNo = (TextView)findViewById(R.id.report_review_Vehicle_No);
	        txtVehicleNo.setTypeface(face);
	        txtChassisNo = (TextView)findViewById(R.id.report_review_Chassis_No);
	        txtChassisNo.setTypeface(face);
	        txtEngineNo = (TextView)findViewById(R.id.report_review_Engine);
	        txtEngineNo.setTypeface(face);
	        txtVehicleUseValue = (TextView)findViewById(R.id.report_review_Vehicle_Use_value);
	        txtVehicleUseValue.setTypeface(face);
	        txtVehicleDamageValue = (TextView)findViewById(R.id.report_review_was_vehicle_damaged_value);
	        txtVehicleDamageValue.setTypeface(face);
	        txtVehicleTowedValue = (TextView)findViewById(R.id.report_review_Was_Vehicle_Towed_value);
	        txtVehicleTowedValue.setTypeface(face);
	        txtVehicleAirbagsValue = (TextView)findViewById(R.id.report_review_Airbags_inflate_value);
	        txtVehicleAirbagsValue.setTypeface(face);
	        txtVehiclecolorValue = (TextView)findViewById(R.id.report_review_Vehicle_Color_value);
	        txtVehiclecolorValue.setTypeface(face);
	        txtVehicleNoValue = (TextView)findViewById(R.id.report_review_Vehicle_No_value);
	        txtVehicleNoValue.setTypeface(face);
	        txtChassisNoValue = (TextView)findViewById(R.id.report_review_Chassis_No_value);
	        txtChassisNoValue.setTypeface(face);
	        txtEngineNoValue = (TextView)findViewById(R.id.report_review_Engine_value);
	        txtEngineNoValue.setTypeface(face);
	        txtDriverOfVehicle = (TextView)findViewById(R.id.report_driver_of_vehicle);
	        txtDriverOfVehicle.setTypeface(face);
	        txtDriverOfVehicleValue = (TextView)findViewById(R.id.report_driver_of_vehicle_value);
	        txtDriverOfVehicleValue.setTypeface(face);
	        txtSelectGarage = (TextView)findViewById(R.id.report_select_garage);
	        txtSelectGarage.setTypeface(face);
	        txtSelectGarageValue = (TextView)findViewById(R.id.report_select_garage_value);
	        txtSelectGarageValue.setTypeface(face);
	        txtPhotographs = (TextView)findViewById(R.id.Heading_Of_Photographs);
	        txtPhotographs.setTypeface(face);
	        txtPoliceReport = (TextView)findViewById(R.id.Heading_Of_PoliceReport);
	        txtPoliceReport.setTypeface(face);
	        txtRepairEstimate= (TextView)findViewById(R.id.Heading_Of_RepairEstimate);
	        txtRepairEstimate.setTypeface(face);
	        Intent a = getIntent();
			cust = (CustodianClaims) a.getSerializableExtra("cust");
	        sharedPreferences  = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
	     
	  	 	 OWNERID =  sharedPreferences.getString("id","");
	  	 	Log.e("id",OWNERID);
	        
//******************************************************************
	}
  @Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	//Get saved data from preferences in onResume() to reflect the change if edited.
	StrDateOfIncident =  sharedPreferences.getString("dateOfIncident","");
  	StrTimeOfIncident=  sharedPreferences.getString("timeOfIncident","");
  	StrState=  sharedPreferences.getString("state","");
  	StrCity=  sharedPreferences.getString("city","");
 	StrVehicleInvolved=  sharedPreferences.getString("WHOWASINVOLVED","");
  	StrCauseOfDamage=  sharedPreferences.getString("VehicleDamage","");
 	StrIncidentDesc=  sharedPreferences.getString("Description","");
  	StrContactNo=  sharedPreferences.getString("mobilePhone","");
 	StrContactEmail=  sharedPreferences.getString("emailAddress","");
 	StrVehiclesInvolvedNo = sharedPreferences.getString("VehiclesInvolved", "");
 	StrVehicleUse = sharedPreferences.getString("VehicleBeingUsed", "");
 	StrWasVehicleDamaged = sharedPreferences.getString("VehicleDamage", "");
 	StrWasVehicleTowed = sharedPreferences.getString("VehicleTowed","");
 	StrAirbags = sharedPreferences.getString("Airbags","");
 	StrVehicleColour = sharedPreferences.getString("VehicleColour","");
 	StrChassisNo = sharedPreferences.getString("ChassisNo", "");
 	StrEngineNo = sharedPreferences.getString("EngineNo","");
 	StrDriver = sharedPreferences.getString("Driver","");
 	StrRepairCentre = sharedPreferences.getString("RepairCentre", "");
 	
  	
 	
  	StrCauseOfDamage=  sharedPreferences.getString("WhatHappenedSelected","");
 	StrIncidentDesc=  sharedPreferences.getString("Description","");
  	StrContactNo=  sharedPreferences.getString("mobilePhone","");
 	StrContactEmail=  sharedPreferences.getString("emailAddress","");
 	StrWHOwasInvolved= sharedPreferences.getString("WHOWASINVOLVED","");
 	StrAddress = sharedPreferences.getString("address", "");
 	StrDriver = sharedPreferences.getString("Driver","");
 	StrWhatHAppend = sharedPreferences.getString("WhatHAppend", "");
 	StrPassenger = sharedPreferences.getString("Passenger", "");
 	StrVehiclePolicy  = sharedPreferences.getString("StrPolicy","");
 	StrVehiclePolicyID = sharedPreferences.getString("Strid", "");
 	StrWHOwasInvolved= sharedPreferences.getString("WHOWASINVOLVED","");
 	
 	
 	/*
 	 * Get the path of image and convert it into bitmap as 
 	 * bitmap is required to convert it directly into base64
 	 */
 	picture_One = sharedPreferences.getString("picture1","");
 	Log.e("pictureOne",picture_One);
 	resizedbitmap11  =  decodeSampledBitmapFromResource(picture_One,80, 80);
 	uploadedPic1.setImageBitmap(resizedbitmap11);
 	
 	
 	picture_Two= sharedPreferences.getString("picture2","");
 	Log.e("pictureTwo",picture_Two);
 	resizedbitmap22  =  decodeSampledBitmapFromResource(picture_Two,80, 80);
 	uploadedPic2.setImageBitmap(resizedbitmap22);
 	
 	
 	picture_Three = sharedPreferences.getString("picture3","");
 	Log.e("pictureThree",picture_Three);
 	resizedbitmap33  =  decodeSampledBitmapFromResource(picture_Three,80, 80);
 	uploadedPic3.setImageBitmap(resizedbitmap33);
 	
 	
 	picture_Four = sharedPreferences.getString("picture4","");
 	Log.e("pictureFour",picture_Four);

	resizedbitmap44  =  decodeSampledBitmapFromResource(picture_Four,80, 80);
 	uploadedPic4.setImageBitmap(resizedbitmap44);
 	
 	
 	picture_Five= sharedPreferences.getString("picture5","");
 	resizedbitmap55  =  decodeSampledBitmapFromResource(picture_Five,80, 80);
 	uploadedPic5.setImageBitmap(resizedbitmap55);
 	
 	
 	picture_Six = sharedPreferences.getString("picture6","");
 	resizedbitmap66  =  decodeSampledBitmapFromResource(picture_Six,80, 80);
 	uploadedPic6.setImageBitmap(resizedbitmap66);
 	
 	
 	picture_Seven = sharedPreferences.getString("picture7","");
 	resizedbitmap77  =  decodeSampledBitmapFromResource(picture_Seven,80, 80);
 	uploadedPic7.setImageBitmap(resizedbitmap77);
	
 	Log.e("1",StrDateOfIncident);
 	Log.e("2",StrTimeOfIncident);
 	Log.e("3",StrState);
 	Log.e("4",StrCity);
 	Log.e("5",StrVehicleInvolved);
 	Log.e("6",StrCauseOfDamage);
 	Log.e("7",StrIncidentDesc);
 	Log.e("8",StrContactNo);
 	Log.e("9",StrContactEmail);
 
 	try {
 		String startTime = StrDateOfIncident;
     	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
		Date date1 = dateFormat.parse(startTime);
		 TimeOfDateOfIncident = date1.getTime();
		Log.e("Date ",""+date1.getTime());
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 	
 	if(StrPassenger.equals("More than 6")){
 		StrNoOfPassengers = StrPassenger.replace("More than 6", "0");
 		Log.e("10",""+NoOfPassengers);
 	}
 	else{
 		StrNoOfPassengers = StrPassenger;
 		Log.e("10",""+NoOfPassengers);
 	}
 	
 	
 	//Set the data into fields.
 	txtDateOfIncidentValue.setText(StrDateOfIncident);
 	txtTimeofIncidentValue.setText(StrTimeOfIncident);
 	txtStateValue.setText(StrState);
 	txtCityValue.setText(StrCity);
 	txtVehicleInvolvedValue.setText(StrWHOwasInvolved);
 	txtCauseOfDamageValue.setText(StrCauseOfDamage);
 	txtIncidentDescValue.setText(StrIncidentDesc);
 	txtContactNameValue.setText(StrContactNo);
 	txtContactPhoneValue.setText(StrContactEmail);
 	txtVehicleModel.setText(StrVehicleInvolved);
 	txtVehicleUseValue.setText(StrVehicleUse);
 	txtVehicleDamageValue.setText(StrWasVehicleDamaged);
 	txtVehicleTowedValue.setText(StrWasVehicleTowed);
 	txtVehicleAirbagsValue.setText(StrAirbags);
 	txtVehiclecolorValue.setText(StrVehicleColour);
 	txtVehicleNoValue.setText(StrVehiclesInvolvedNo);
 	txtChassisNoValue.setText(StrChassisNo);
 	txtEngineNoValue.setText(StrEngineNo);
 	txtDriverOfVehicleValue.setText(StrDriver);
 	txtSelectGarageValue.setText(StrRepairCentre);
 
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
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch(id){
		case R.id.imageView1:
			//Navigate to previous screen
			finish();
        break;
        
		case R.id.home:
			//Navigate to home screen.
			myIntent = new Intent(ReportClaimReview.this,CustodianHomeScreenMain.class);
	        startActivity(myIntent);
				finish();
	        break;
        
		case R.id.edit_button_first:
			editor = sharedPreferences.edit();
			editor.putString("Start","1");
			if(sharedPreferences.contains("BasicsKey")||sharedPreferences.contains("whowasKEY")
					||sharedPreferences.contains("vehiclesKEY")||
					sharedPreferences.contains("StartKey")||sharedPreferences.contains("MainKey")){
				editor = sharedPreferences.edit();
				editor.remove("BasicsKey");
				editor.remove("whowasKEY");
				editor.remove("vehiclesKEY");
				editor.remove("StartKey");
				editor.remove("MainKey");
				editor.commit();
			}
			editor.commit();
			mIntent = new Intent(ReportClaimReview.this,ReportClaimStart.class);
			
			mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(mIntent);
			
			break;
		case R.id.edit_button_second:
			
			sharedPreferences  = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
			editor = sharedPreferences.edit();
			editor.putString("Basics","2");
			if(sharedPreferences.contains("BasicsKey")||sharedPreferences.contains("whowasKEY")
					||sharedPreferences.contains("vehiclesKEY")||
					sharedPreferences.contains("StartKey")||sharedPreferences.contains("MainKey")){
				editor = sharedPreferences.edit();
				editor.remove("BasicsKey");
				editor.remove("whowasKEY");
				editor.remove("vehiclesKEY");
				editor.remove("StartKey");
				editor.remove("MainKey");
				editor.commit();
			}
			editor.commit();
			
			mIntent = new Intent(ReportClaimReview.this,ReportClaimBasic.class);
			mIntent.putExtra("ReviewBasics","Basics");
		
			mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(mIntent);
			break;
			
		case R.id.edit_button_third:
			sharedPreferences  = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
			editor = sharedPreferences.edit();
			editor.putString("WhoWasInvolvedID","3");
			if(sharedPreferences.contains("BasicsKey")||sharedPreferences.contains("whowasKEY")
					||sharedPreferences.contains("vehiclesKEY")||
					sharedPreferences.contains("StartKey")||sharedPreferences.contains("MainKey")){
				editor = sharedPreferences.edit();
				editor.remove("BasicsKey");
				editor.remove("whowasKEY");
				editor.remove("vehiclesKEY");
				editor.remove("StartKey");
				editor.remove("MainKey");
				editor.commit();
			}
			editor.commit();
			mIntent = new Intent(ReportClaimReview.this,ReportClaimWhoWasInvolved.class);
			mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(mIntent);
			break;
		case R.id.edit_button_fourth:
			sharedPreferences  = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
			editor = sharedPreferences.edit();
			editor.putString("Vehicles","4");
			if(sharedPreferences.contains("BasicsKey")||sharedPreferences.contains("whowasKEY")
					||sharedPreferences.contains("vehiclesKEY")||
					sharedPreferences.contains("StartKey")||sharedPreferences.contains("MainKey")){
				editor = sharedPreferences.edit();
				editor.remove("BasicsKey");
				editor.remove("whowasKEY");
				editor.remove("vehiclesKEY");
				editor.remove("StartKey");
				editor.remove("MainKey");
				editor.commit();
			}
			editor.commit();
			mIntent = new Intent(ReportClaimReview.this,ReportClaimVehicles.class);
			mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(mIntent);
			break;
		case R.id.edit_button_fifth:
			sharedPreferences  = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
			if(sharedPreferences.contains("BasicsKey")||sharedPreferences.contains("whowasKEY")
					||sharedPreferences.contains("vehiclesKEY")||
					sharedPreferences.contains("StartKey")||sharedPreferences.contains("MainKey")){
				editor = sharedPreferences.edit();
				editor.remove("BasicsKey");
				editor.remove("whowasKEY");
				editor.remove("vehiclesKEY");
				editor.remove("StartKey");
				editor.remove("MainKey");
				editor.commit();
			}
			mIntent = new Intent(ReportClaimReview.this,ReportClaimVehicles.class);
			mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(mIntent);
			break;
		case R.id.edit_button_sixth:
			if(sharedPreferences.contains("BasicsKey")||sharedPreferences.contains("whowasKEY")
					||sharedPreferences.contains("vehiclesKEY")||
					sharedPreferences.contains("StartKey")||sharedPreferences.contains("MainKey")){
				editor = sharedPreferences.edit();
				editor.remove("BasicsKey");
				editor.remove("whowasKEY");
				editor.remove("vehiclesKEY");
				editor.remove("StartKey");
				editor.remove("MainKey");
				editor.commit();
			}
			
			mIntent = new Intent(ReportClaimReview.this,ReportClaimVehicles.class);
			mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(mIntent);
			break;
	
			
        
		case R.id.report_Plus_Button:

			
			if(visibility_Flag == false){
				
				BtnExpand.setImageResource(R.drawable.minus);
				relExpand.setVisibility(View.VISIBLE);
		            visibility_Flag = true;
			}
			else if(visibility_Flag == true){
				BtnExpand.setImageResource(R.drawable.add);
				relExpand.setVisibility(View.GONE);
				
				
		            visibility_Flag = false;
			}
			break;
      
		case R.id.review_submit:
			
			GenerateIDForPicUpload();
		
			break;
	}
	}			
   

	private void Showalerts(String message) {
		// TODO Auto-generated method stub
		//AlertDialog.Builder alertDialog = new AlertDialog.Builder(CustodianHomeScreen.this);
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(ReportClaimReview.this);
        // Setting Dialog Title
		 alertDialog.setCancelable(false); 
        alertDialog.setTitle("Custodian");
 
        // Setting Dialog Message
        alertDialog.setMessage(message);
 
        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.app_icon_48x48);
 
        // Setting Positive "Yes" Button
        alertDialog.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
            	myIntent = new Intent(ReportClaimReview.this,CustodianReportClaim.class);
    	        startActivity(myIntent);
            
            }
        });
        alertDialog.show();
		}	
	
	private void GenerateIDForPicUpload() {
		// TODO Auto-generated method stub
     	PolicyInteger  = Integer.parseInt(StrVehiclePolicy);                                                                                                                                  
     	PolicyLineInteger = Integer.parseInt(StrVehiclePolicyID);                                                                                                                                                                                                 
                                                                                                                                                
	json = new JSONObject();                                                                                                                                                           
	try {
		Log.e("TimeOfDateOfIncident",""+TimeOfDateOfIncident);
		value = Boolean.valueOf("true");    
		json.put("active",value);
		json.put("address",StrAddress); //from interview                                                                                                                                   
		json.put("chassisNo",StrChassisNo); //                                                                                                                                             
		json.put("city",StrCity); //from interview                                                                                                                                         
		json.put("contactEmail",StrContactEmail); //from interview                                                                                                                         
		json.put("contactPhone",StrContactNo); //from interview                                                                                                                            
		json.put("customer",OWNERID); //from the session object after he had logged in @see https://apps-demo.zanibal.com/hermes/rest/api/v1/partner/customer/username/[username]          
		json.put("dateOfIncident",TimeOfDateOfIncident ); //from interview                                                                                                                 
		json.put("didAirbagInflate","YES"); //from interview                                                                                                                               
		json.put("engineNo",StrEngineNo);                                                                                                                                                   
		json.put("howManyPassengers",StrNoOfPassengers); //from interview                                                                                                                       
		json.put("howWasVehicleUsed", StrVehicleUse);//from interview                                                                                                                      
		json.put("policy",PolicyInteger);                                                                                                                                                  
		json.put("policyLine",PolicyLineInteger); //From the selected policy line @see response from https://apps-demo.zanibal.com/hermes/rest/api/v1/partner/policy/list/line?policyId=xxx
		json.put("state",StrState) ;//from interview                                                                                                                                       
		json.put("status","NEW"); //hard coded                                                                                                                                             
		json.put("timeOfIncident",StrTimeOfIncident);//from interview                                                                                                                      
		json.put("vehicleColor",StrVehicleColour); //From the selected policy line @see response from https://apps-demo.zanibal.com/hermes/rest/api/v1/partner/policy/list/line?policyId=xxx
		json.put("vehicleDamage",StrCauseOfDamage); //from interview                                                                                                                       
		json.put("vehicleNo",StrVehiclesInvolvedNo); //From the selected policy line @see response from https://apps-demo.zanibal.com/hermes/rest/api/v1/partner/policy/list/line?policyId=xxx  
		json.put("wasVehicleDamaged",StrWasVehicleDamaged); //from interview                                                                                                                    
		json.put("wasVehicleTowed",StrWasVehicleTowed);//from interview                                                                                                                       
		json.put("whatHappened",StrWhatHAppend); //from interview                                                                                                                          
	                                                                                                                                                                                     
		json.put("whoWasDriving",StrDriver);//from interview                                                                                                                               
	                                                                                                                                                                                     
		json.put("autoShopEstimate",0);                                                                                                                                                    
	                                                                                                                                                                                     
		json.put("adjusterEstimate",0);                                                                                                                                                    
		                                                                                                                                                                                   
		json.put("approvedEstimate",0);   
		
		ConnectivityManager connectivityManager 
        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
  NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
   if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){
		new ForgotWebservice(WebserviceURLs.REPORT_CLAIM_REVIEW_CLAIM_ID, "", ReportClaimReview.this, ReportClaimReview.this, true, json,"Creating claims...").execute();                                                                        
   }
   else{
	   Showalerts(Alerts.CHECK_INTERNET);
   }
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}                                                                                                                                                          
	                                                                                                                                                                                   
	                                   
		
		
		
	}
	@Override
	public void onResponse(String response) {
		// TODO Auto-generated method stub
		try {
			json = new JSONObject(response);
			String status  = json.optString("success");
			if(status.equalsIgnoreCase("true")){
				
				if(resizedbitmap11!=null||resizedbitmap22!=null||resizedbitmap33!=null||resizedbitmap44!=null||resizedbitmap55!=null||resizedbitmap66!=null||resizedbitmap77!=null){
					GotoNextWebserviceOfUploadPics();
				}
				else{
					sharedPreferences  = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
					editor = sharedPreferences.edit();
					editor.putString("Basics","2");
					if(sharedPreferences.contains("BasicsKey")||sharedPreferences.contains("whowasKEY")||
							sharedPreferences.contains("vehiclesKEY")||sharedPreferences.contains("StartKey")){
						editor = sharedPreferences.edit();
						editor.remove("BasicsKey");
						editor.remove("whowasKEY");
						editor.remove("vehiclesKEY");
						editor.remove("StartKey");
						editor.commit();
					}
					editor.commit();
					Showalerts("Claim has been reported successfully.");
				}
			}
			else{
				//Showalerts("Unsuccessfull");
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
	public void Response(String response) {
		// TODO Auto-generated method stub
		
		try {
			json = new JSONObject(response);
			String status  = json.optString("success");
			 strMessageCode = json.optString("msgCode");
			 strMessageCodeInteger = Integer.parseInt(strMessageCode);
			Log.e("message code",""+strMessageCode);
			if(status.equalsIgnoreCase("true")){
				
				if(resizedbitmap11!=null||resizedbitmap22!=null||resizedbitmap33!=null||resizedbitmap44!=null||resizedbitmap55!=null||resizedbitmap66!=null||resizedbitmap77!=null){
					GotoNextWebserviceOfUploadPics();
				}
				else{
					Showalerts("Claim has been reported successfully.");
				}
				
				
				}
			else{
				
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
		


	private void GotoNextWebserviceOfUploadPics() {
		// TODO Auto-generated method stub
		

		if(resizedbitmap11!=null){
			//Bitmap b  = BitmapFactory.decodeFile(pic1);
	        ByteArrayOutputStream bad = new ByteArrayOutputStream();
	        resizedbitmap11.compress(Bitmap.CompressFormat.JPEG, 100, bad);
	        File imageFile = new File(picture_One);
	        Log.d("pic1 path",""+imageFile);
          if(imageFile.exists())
          {
        	 imageNamePic1 = imageFile.getName();
        	  Log.e("image name",""+imageNamePic1);
//        	  imageSize = imageFile.getUsableSpace();
//      	  Log.e("image size",""+imageSize);
        	  imagePath = imageFile.getAbsolutePath();
        
        	  
          }
	     
	        byte[] ba = bad.toByteArray();
	        
//	        data1 = selectedImagePath.getBytes(); 
	        int size = ba.length;
	        Log.e("Picture Size in bytes",""+size);
	        if(size>1048576){
	       
	        	//mSplaHandler.sendEmptyMessage(2);
	        	Showalerts("Image Size cannot exceed more than 1MB.Please crop your image");
	        }else{
	        	   my_image = Base64.encodeToString(ba, Base64.DEFAULT);
	   	        String image = my_image.replace("\n", "%20");
	   	     
	   			IsUploaded = true;
	   			resizedbitmap11 = null;
	   			
	   	
	   	        Log.e("StringOfImageBase64first",image);
	   	
	   			try {
	   				json = new JSONObject();
	   				json.put("active",value);
	   				json.put("category", "CLAIM_PICTURE");
	   				json.put("label", "PICTURE # 1 FOR CLAIM" +  strMessageCodeInteger);
	   				json.put("relatedToId", strMessageCodeInteger);
	   				json.put("relatedToType","CLAIM");
	   				json.put("fileMimeType","image/png");
	   				json.put("fileName", imageNamePic1);
	   				json.put("fileSize",size);
	   				json.put("type","RECORD");
	   				json.put("base64Attachment",image);
	   				ConnectivityManager connectivityManager 
	   		        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	   		  NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	   		   if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){
	   				new ContactUsWebservice(WebserviceURLs.REPORT_CLAIM_REVIEW_PIC_UPLOAD, "", ReportClaimReview.this, ReportClaimReview.this, true,json,"Creating claims...").execute();
	   		   }else{
	   			 Showalerts(Alerts.CHECK_INTERNET);
	   		   }
	   		   } catch (JSONException e) {
	   				// TODO Auto-generated catch block
	   				e.printStackTrace();
	   			}}
	        }

	     
		
		
		else  if(resizedbitmap22!=null){
			//Bitmap b  = BitmapFactory.decodeFile(pic2);
	        ByteArrayOutputStream bad = new ByteArrayOutputStream();
	        resizedbitmap22.compress(Bitmap.CompressFormat.JPEG, 100, bad);
	        File imageFile = new File(picture_Two);
	        Log.d("pic2 path",""+imageFile);
          if(imageFile.exists())
          {
        	  imageNamePic2 = imageFile.getName();
        	  Log.e("image name",""+imageNamePic2);
    	 
        	  imagePath = imageFile.getAbsolutePath();
        
        	  
          }
          byte[] ba = bad.toByteArray();
	        
//	        data1 = selectedImagePath.getBytes(); 
	        int size = ba.length;
	        Log.e("Picture Size in bytes",""+size);
	        if(size>1048576){
	        	
	      	Showalerts("Image Size cannot exceed more than 1MB.Please crop your image");
	        }else{

	        my_image = Base64.encodeToString(ba, Base64.DEFAULT);
	        String image = my_image.replace("\n", "%20");
	     
			IsUploaded = true;
			resizedbitmap22= null;
			
	
	        Log.e("StringOfImageBase64first",image);
			try {
				json = new JSONObject();
				json.put("active",value);
				json.put("category", "CLAIM_PICTURE");
				json.put("label", "PICTURE # 2 FOR CLAIM" +  strMessageCodeInteger);
				json.put("relatedToId", strMessageCodeInteger);
				json.put("relatedToType","CLAIM");
				json.put("fileMimeType","image/png");
				json.put("fileName", imageNamePic2);
				json.put("fileSize",size);
				json.put("type","RECORD");
				json.put("base64Attachment",image);
				ConnectivityManager connectivityManager 
		        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		  NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		   if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){
				new ContactUsWebservice(WebserviceURLs.REPORT_CLAIM_REVIEW_PIC_UPLOAD, "", ReportClaimReview.this, ReportClaimReview.this,true,json,"Creating claims...").execute();
		   }else{
			   Showalerts(Alerts.CHECK_INTERNET);
		   }
		   } catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
		}
			
		else  if(resizedbitmap33!=null){
				//Bitmap b  = BitmapFactory.decodeFile(pic3);
		        ByteArrayOutputStream bad = new ByteArrayOutputStream();
		        resizedbitmap33.compress(Bitmap.CompressFormat.JPEG, 100, bad);
		        File imageFile = new File(picture_Three);
	          if(imageFile.exists())
	          {
	        	  imageNamePic3 = imageFile.getName();
	        	  Log.e("image name",""+imageNamePic3);
//	        	  imageSize = imageFile.getUsableSpace();
//	        	  Log.e("image size",""+imageSize);
	        	  imagePath = imageFile.getAbsolutePath();
	        
	        	  
	          }
		     
		        byte[] ba = bad.toByteArray();
		        
//		        data1 = selectedImagePath.getBytes(); 
		        int size = ba.length;
		        Log.e("Picture Size in bytes",""+size);
		        if(size>1048576){
		        	
		        Showalerts("Image Size cannot exceed more than  1MB.Please crop your image");
		        }else{

		        my_image = Base64.encodeToString(ba, Base64.DEFAULT);
		        String image = my_image.replace("\n", "%20");
		     
				IsUploaded = true;
				resizedbitmap33 = null;
				
		        Log.e("StringOfImageBase64Second",image);
				try {
					json = new JSONObject();
					json.put("active",value);
					json.put("category", "CLAIM_PICTURE");
					json.put("label", "PICTURE #3  FOR CLAIM" +  strMessageCodeInteger);
					json.put("relatedToId", strMessageCodeInteger);
					json.put("relatedToType","CLAIM");
					json.put("fileMimeType","image/png");
					json.put("fileName", imageNamePic3);
					json.put("fileSize",size);
					json.put("type","RECORD");
					json.put("base64Attachment",image);
					ConnectivityManager connectivityManager 
			        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			  NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
			   if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){
					new ContactUsWebservice(WebserviceURLs.REPORT_CLAIM_REVIEW_PIC_UPLOAD, "", ReportClaimReview.this, ReportClaimReview.this, true,json,"Creating claims...").execute();
			   }else{
				   Showalerts(Alerts.CHECK_INTERNET);
			   }
			   } catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}
		}
		 
		 
		 
		else  if(resizedbitmap44!=null){
				//Bitmap b  = BitmapFactory.decodeFile(pic4);
		        ByteArrayOutputStream bad = new ByteArrayOutputStream();
		        resizedbitmap44.compress(Bitmap.CompressFormat.JPEG, 100, bad);
		        File imageFile = new File(picture_Four);
	          if(imageFile.exists())
	          {
	        	 imageNamePic4= imageFile.getName();
	        	  Log.e("image name",""+imageNamePic4);
//	        	  imageSize = imageFile.getUsableSpace();
//	        	  Log.e("image size",""+imageSize);
	        	  imagePath = imageFile.getAbsolutePath();
	        
	        	  
	          }
		     
		        byte[] ba = bad.toByteArray();
		        
//		        data1 = selectedImagePath.getBytes(); 
		        int size = ba.length;
		        Log.e("Picture Size in bytes",""+size);
		        if(size>1048576){
		        	
		        Showalerts("Image Size cannot exceed more than 1MB.Please crop your image");
		        }else{

		        my_image = Base64.encodeToString(ba, Base64.DEFAULT);
		        String image = my_image.replace("\n", "%20");
		     
				IsUploaded = true;
				resizedbitmap44 = null;
				
		        Log.e("StringOfImageBase64Second",image);
				try {
					json = new JSONObject();
					json.put("active",value);
					json.put("category", "CLAIM_PICTURE");
					json.put("label", "PICTURE #4  FOR CLAIM" +  strMessageCodeInteger);
					json.put("relatedToId", strMessageCodeInteger);
					json.put("relatedToType","CLAIM");
					json.put("fileMimeType","image/png");
					json.put("fileName", imageNamePic4);
					json.put("fileSize",size);
					json.put("type","RECORD");
					json.put("base64Attachment",image);
					ConnectivityManager connectivityManager 
			        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			  NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
			   if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){
					new ContactUsWebservice(WebserviceURLs.REPORT_CLAIM_REVIEW_PIC_UPLOAD, "", ReportClaimReview.this, ReportClaimReview.this,true,json,"Creating claims...").execute();
			   }else{
				   Showalerts(Alerts.CHECK_INTERNET);
			   }
			   } catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}
		}
		 
		else if(resizedbitmap55!=null){
				//Bitmap b  = BitmapFactory.decodeFile(pic5);
		        ByteArrayOutputStream bad = new ByteArrayOutputStream();
		        resizedbitmap55.compress(Bitmap.CompressFormat.JPEG, 100, bad);
		        File imageFile = new File(picture_Five);
	          if(imageFile.exists())
	          {
	        	  imageNamePic5 = imageFile.getName();
	        	  Log.e("image name",""+imageNamePic5);
//	        	  imageSize = imageFile.getUsableSpace();
//	        	  Log.e("image size",""+imageSize);
	        	  imagePath = imageFile.getAbsolutePath();
	        
	        	  
	          }
		     
		        byte[] ba = bad.toByteArray();
		        
//		        data1 = selectedImagePath.getBytes(); 
		        int size = ba.length;
		        Log.e("Picture Size in bytes",""+size);
		        if(size>1048576){
		        	//mSplaHandler.sendEmptyMessage(2);
		        Showalerts("Image Size cannot exceed more than 1MB.Please crop your image");
		        }else{

		        my_image = Base64.encodeToString(ba, Base64.DEFAULT);
		        String image = my_image.replace("\n", "%20");
		     
				IsUploaded = true;
				resizedbitmap55= null;
				
		        Log.e("StringOfImageBase64Second",image);
				try {
					json = new JSONObject();
					json.put("active",value);
					json.put("category", "CLAIM_PICTURE");
					json.put("label", "PICTURE #5 FOR CLAIM" +  strMessageCodeInteger);
					json.put("relatedToId", strMessageCodeInteger);
					json.put("relatedToType","CLAIM");
					json.put("fileMimeType","image/png");
					json.put("fileName", imageNamePic5);
					json.put("fileSize",size);
					json.put("type","RECORD");
					json.put("base64Attachment",image);
					ConnectivityManager connectivityManager 
			        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			  NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
			   if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){
					new ContactUsWebservice(WebserviceURLs.REPORT_CLAIM_REVIEW_PIC_UPLOAD, "", ReportClaimReview.this, ReportClaimReview.this,true,json,"Creating claims...").execute();
			   }else{
				
				   Showalerts(Alerts.CHECK_INTERNET);
			   }
			   } catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}
		}
		 
		 
		else if(resizedbitmap66!=null){
				//Bitmap b  = BitmapFactory.decodeFile(pic6);
		        ByteArrayOutputStream bad = new ByteArrayOutputStream();
		        resizedbitmap66.compress(Bitmap.CompressFormat.JPEG, 100, bad);
		        File imageFile = new File(picture_Six);
	          if(imageFile.exists())
	          {
	        	  imageNamePic6 = imageFile.getName();
	        	  Log.e("image name",""+imageNamePic6);
//	        	  imageSize = imageFile.getUsableSpace();
//	        	  Log.e("image size",""+imageSize);
	        	  imagePath = imageFile.getAbsolutePath();
	        
	        	  
	          }
		     
		        byte[] ba = bad.toByteArray();
		        
//		        data1 = selectedImagePath.getBytes(); 
		        int size = ba.length;
		        Log.e("Picture Size in bytes",""+size);
		        if(size>1048576){
		        	//mSplaHandler.sendEmptyMessage(2);
		        Showalerts("Image Size cannot exceed more than 1MB.Please crop your image");
		        }else{

		        my_image = Base64.encodeToString(ba, Base64.DEFAULT);
		        String image = my_image.replace("\n", "%20");
		     
				IsUploaded = true;
				resizedbitmap66 = null;
				
		        Log.e("StringOfImageBase64Second",image);
				try {
					json = new JSONObject();
					json.put("active",value);
					json.put("category", "POLICE_REPORT");
					json.put("label", "POLICE REPORT  FOR CLAIM " +  strMessageCodeInteger);
					json.put("relatedToId", strMessageCodeInteger);
					json.put("relatedToType","CLAIM");
					json.put("fileMimeType","image/png");
					json.put("fileName", imageNamePic6);
					json.put("fileSize",size);
					json.put("type","RECORD");
					json.put("base64Attachment",image);
					ConnectivityManager connectivityManager 
			        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			  NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
			   if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){
					new ContactUsWebservice(WebserviceURLs.REPORT_CLAIM_REVIEW_PIC_UPLOAD, "", ReportClaimReview.this, ReportClaimReview.this, true,json,"Creating claims...").execute();
			   }else{
				   Showalerts(Alerts.CHECK_INTERNET);
			   }
			   } catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}
		}
		
		
		
		else if(resizedbitmap77!=null){
				//Bitmap b  = BitmapFactory.decodeFile(pic7);
		        ByteArrayOutputStream bad = new ByteArrayOutputStream();
		        resizedbitmap77.compress(Bitmap.CompressFormat.JPEG, 100, bad);
		        File imageFile = new File(picture_Seven);
	          if(imageFile.exists())
	          {
	        	  imageNamePic7 = imageFile.getName();
	        	  Log.e("image name",""+imageNamePic7);
//	        	  imageSize = imageFile.getUsableSpace();
//	        	  Log.e("image size",""+imageSize);
	        	  imagePath = imageFile.getAbsolutePath();
	        
	        	  
	          }
		     
		        byte[] ba = bad.toByteArray();
		        
//		        data1 = selectedImagePath.getBytes(); 
		        int size = ba.length;
		        Log.e("Picture Size in bytes",""+size);
		        if(size>1048576){
		        	
		    Showalerts("Image Size cannot exceed more than 1MB.Please crop your image");
		        }else{

		        my_image = Base64.encodeToString(ba, Base64.DEFAULT);
		        String image = my_image.replace("\n", "%20");
		     
				IsUploaded = true;
				resizedbitmap77 = null;
				
		        Log.e("StringOfImageBase64Second",image);
				try {
					json = new JSONObject();
					json.put("active",value);
					json.put("category", "REPAIR_ESTIMATE");
					json.put("label", "REPAIR ESTIMATE  FOR CLAIM" +  strMessageCodeInteger);
					json.put("relatedToId", strMessageCodeInteger);
					json.put("relatedToType","CLAIM");
					json.put("fileMimeType","image/png");
					json.put("fileName", imageNamePic7);
					json.put("fileSize",size);
					json.put("type","RECORD");
					json.put("base64Attachment",image);
					ConnectivityManager connectivityManager 
			        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			  NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
			   if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){
					new ContactUsWebservice(WebserviceURLs.REPORT_CLAIM_REVIEW_PIC_UPLOAD, "", ReportClaimReview.this, ReportClaimReview.this, true,json,"Creating claims...").execute();
			   }else{
				  
				  Showalerts(Alerts.CHECK_INTERNET);
			   }
			   } catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}
		
		}
		
		
		
		
		
		
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