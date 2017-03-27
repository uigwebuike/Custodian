package com.custodian.ClaimsCenter;

import com.custodian.CustodianHomeScreenMain;
import com.custodian.R;
import com.custodian.CONSTANT.CONSTANTS;
import com.custodian.ModelView.CustodianClaims;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * 
 * This class is used to display the detailed data of particular claim.
 *
 */

public class ViewAndManageClaimsDetail extends Activity implements OnClickListener{
	/*
	 * Declaration of views,Strings used.
	 */
	ImageButton mHome;
	TextView mHeading, mTitlebar,txtClaimsHeading,txtViewClaimsHeadingFirst,txtDateOfIncident,
	txtDateOfIncident_Value,txtTimeOfIncident,txtTimeOfIncident_Value,txtState,txtState_Value,
	txtCity,txtCity_Value,txtViewClaimsHeadingSecond,txtVehicleInvolved,txtVehicleInvolved_Value,
	txtCauseOfDamage,txtCauseOfDamage_Value,txtIncidentDesc,txtIncidentDesc_Value,
	txtYourContactInfo,txtContactNumber,txtContactNumber_Value,txtContactEmail,
	txtContactEmail_Value,txtVehicleDetails,txtDdriver,txtDriver_Value,txtvehicleUse,
	txtVehicleUse_Value,txtDamaged,txtDamaged_Value,txtTowed,txtTowed_Value,txtAirbags,
	txtAirbags_Value,txtVehicleColor,txtVehicleColor_Value,txtVehicleNo,txtVehicleNo_Value,
	txtChassisNo,ChassisNo_Value,txtEngineNo,txtEngineNo_Value,txtDamagedNext,txtTowedNext;
	
	Intent myIntent;
	String  strClaimNo ,strDateOfIncident,strStatus ,strDesc,strTimeOfIncident, 
    strState,strCity,strChassis,strContactEmail ,strContactPhone ,strdidAirbags,
    strEngineNo ,strVehicleUse ,strVehicleColor ,strVehicleNo,strWasDamged,whoWasDriving, 
    strVehicleTowed,strVehicleDamge;
	ImageButton mback;
	 public static final String MyPREFERENCES = "MyPrefs" ;
	CustodianClaims cust;
	
	// OnBackPressed is used to disable the device back button as back button is used in top bar for navigation.
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
	}
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custodian_view_and_manage_details_next);
        /*
         * Initialisations , typeface of textviews and listeners
         */
        //******************************************************************
        mback = (ImageButton)findViewById(R.id.imageView1);
        mback.setOnClickListener(this);
        mHome = (ImageButton)findViewById(R.id.home);
        mHome.setOnClickListener(this);
        Typeface face = Typeface.createFromAsset(getAssets(),CONSTANTS.FONT_NAME);
        mHeading = (TextView)findViewById(R.id.title);
        mHeading.setTypeface(face);
        txtClaimsHeading = (TextView)findViewById(R.id.viewClaimsHeading);
        txtClaimsHeading.setTypeface(face);
        txtViewClaimsHeadingFirst= (TextView)findViewById(R.id.viewClaimsHeadingFirst);
        txtViewClaimsHeadingFirst.setTypeface(face);
      
        txtDateOfIncident= (TextView)findViewById(R.id.dateOfIncident);
        txtDateOfIncident.setTypeface(face);
    	txtDateOfIncident_Value= (TextView)findViewById(R.id.dateOfIncident_Value);
    	txtDateOfIncident_Value.setTypeface(face,Typeface.BOLD);
    	txtTimeOfIncident= (TextView)findViewById(R.id.timeOfIncident);
    	txtTimeOfIncident.setTypeface(face);
    	txtTimeOfIncident_Value= (TextView)findViewById(R.id.timeOfIncident_Value);
    	txtTimeOfIncident_Value.setTypeface(face,Typeface.BOLD);
    	txtState=(TextView)findViewById(R.id.state);
    	txtState.setTypeface(face);
    	txtState_Value= (TextView)findViewById(R.id.state_Value);
    	txtState_Value.setTypeface(face,Typeface.BOLD);
    	txtCity=(TextView)findViewById(R.id.city);
    	txtCity.setTypeface(face);
    	txtCity_Value= (TextView)findViewById(R.id.city_Value);
    	txtCity_Value.setTypeface(face,Typeface.BOLD);
    	txtViewClaimsHeadingSecond= (TextView)findViewById(R.id.viewClaimsHeadingSecond);
    	txtViewClaimsHeadingSecond.setTypeface(face);
    	txtViewClaimsHeadingSecond.setText("Who was involved & what happened");
    	txtVehicleInvolved= (TextView)findViewById(R.id.vehicleInvolved);
    	txtVehicleInvolved.setTypeface(face);
    	txtVehicleInvolved_Value= (TextView)findViewById(R.id.vehicleInvolved_Value);
    	txtVehicleInvolved_Value.setTypeface(face,Typeface.BOLD);
    	txtCauseOfDamage= (TextView)findViewById(R.id.causeOfDamage);
    	txtCauseOfDamage.setTypeface(face);
    	txtCauseOfDamage_Value= (TextView)findViewById(R.id.causeOfDamage_Value);
    	txtCauseOfDamage_Value.setTypeface(face,Typeface.BOLD);
    	txtIncidentDesc= (TextView)findViewById(R.id.incidentDesc);
    	txtIncidentDesc.setTypeface(face);
    	txtIncidentDesc_Value= (TextView)findViewById(R.id.incidentDesc_Value);
    	txtIncidentDesc_Value.setTypeface(face,Typeface.BOLD);
    	txtYourContactInfo= (TextView)findViewById(R.id.yourContactInfo);
    	txtYourContactInfo.setTypeface(face);
    	txtContactNumber= (TextView)findViewById(R.id.contactNumber);
    	txtContactNumber.setTypeface(face);
    	txtContactNumber_Value= (TextView)findViewById(R.id.contactNumber_Value);
    	txtContactNumber_Value.setTypeface(face,Typeface.BOLD);
    	txtContactEmail= (TextView)findViewById(R.id.contactEmail);
    	txtContactEmail.setTypeface(face);
    	txtContactEmail_Value= (TextView)findViewById(R.id.contactEmail_Value);
    	txtContactEmail_Value.setTypeface(face,Typeface.BOLD);
    	txtVehicleDetails= (TextView)findViewById(R.id.VehicleDetails);
    	txtVehicleDetails.setTypeface(face);
    	txtDdriver= (TextView)findViewById(R.id.driver);
    	txtDdriver.setTypeface(face);
    	txtDriver_Value= (TextView)findViewById(R.id.driver_Value);
    	txtDriver_Value.setTypeface(face,Typeface.BOLD);
    	txtvehicleUse= (TextView)findViewById(R.id.vehicleUse);
    	txtvehicleUse.setTypeface(face);
    	txtVehicleUse_Value= (TextView)findViewById(R.id.vehicleUse_Value);
    	txtVehicleUse_Value.setTypeface(face,Typeface.BOLD);
    	txtDamaged= (TextView)findViewById(R.id.damaged);
    	txtDamaged.setTypeface(face);
    	txtDamagedNext= (TextView)findViewById(R.id.damaged_next);
    	txtDamagedNext.setTypeface(face);
    	txtDamaged_Value= (TextView)findViewById(R.id.damaged_Value);
    	txtDamaged_Value.setTypeface(face,Typeface.BOLD);
    	txtTowed= (TextView)findViewById(R.id.towed);
    	txtTowed.setTypeface(face);
    	txtTowedNext= (TextView)findViewById(R.id.towed_next);
    	txtTowedNext.setTypeface(face);
    	txtTowed_Value = (TextView)findViewById(R.id.towed_Value);
    	txtTowed_Value.setTypeface(face,Typeface.BOLD);
    	txtAirbags= (TextView)findViewById(R.id.Airbags);
    	txtAirbags.setTypeface(face);
    	txtAirbags_Value= (TextView)findViewById(R.id.Airbags_Value);
    	txtAirbags_Value.setTypeface(face,Typeface.BOLD);
    	txtVehicleColor= (TextView)findViewById(R.id.vehicleColor);
    	txtVehicleColor.setTypeface(face);
    	txtVehicleColor_Value= (TextView)findViewById(R.id.vehicleColor_Value);
    	txtVehicleColor_Value.setTypeface(face,Typeface.BOLD);
    	txtVehicleNo= (TextView)findViewById(R.id.vehicleNo);
    	txtVehicleNo.setTypeface(face);
    	txtVehicleNo_Value= (TextView)findViewById(R.id.vehicleNo_Value);
    	txtVehicleNo_Value.setTypeface(face,Typeface.BOLD);
    	txtChassisNo= (TextView)findViewById(R.id.ChassisNo);
    	txtChassisNo.setTypeface(face);
    	ChassisNo_Value= (TextView)findViewById(R.id.ChassisNo_Value);
    	ChassisNo_Value.setTypeface(face,Typeface.BOLD);
    	txtEngineNo= (TextView)findViewById(R.id.EngineNo);
    	txtEngineNo.setTypeface(face);
    	txtEngineNo_Value= (TextView)findViewById(R.id.EngineNo_Value);
    	txtEngineNo_Value.setTypeface(face,Typeface.BOLD);
	
		//*****************************************************************************   
		   	/*
		   	 * Get data from Bean class and displays them in fields.
		   	 */
    		Intent i = getIntent();
			cust = (CustodianClaims) i.getSerializableExtra("cust");
			txtClaimsHeading.setText("CLAIM NO.:"+cust.getClaimNo());
	        txtDateOfIncident_Value.setText(cust.getDateOfIncident());
	        txtTimeOfIncident_Value.setText(cust.getStrTimeOfIncident());
	        txtState_Value.setText(cust.getStrState());
	        txtCity_Value.setText(cust.getStrCity());
	        txtContactNumber_Value.setText(cust.getStrContactPhone());
	        txtContactEmail_Value.setText(cust.getStrContactEmail());
	        txtEngineNo_Value.setText(cust.getStrEngineNo());
	        ChassisNo_Value.setText(cust.getStrChassis());
	        txtVehicleNo_Value.setText(cust.getVehicleNo());
	        txtVehicleColor_Value.setText(cust.getStrVehicleColor());
	        txtAirbags_Value.setText(cust.getStrdidAirbags());
	        txtTowed_Value.setText(cust.getStrVehicleTowed());
	        txtDamaged_Value.setText(cust.getStrWasDamged());
	        txtVehicleUse_Value.setText(cust.getStrVehicleUse());
	        txtDriver_Value.setText(cust.getWhoWasDriving());
	        txtIncidentDesc_Value.setText(cust.getVehicleDamage());
	        txtVehicleInvolved_Value.setText(cust.getVehicleNo());
	        txtCauseOfDamage_Value.setText(cust.getWhatHappened());
}
	
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch(id){
	case R.id.imageView1:
		//Navigate back to previous screen
		finish();
        break;
        
	case R.id.home:
		//Navigate back to home screen.
		Intent	myIntent1 = new Intent(ViewAndManageClaimsDetail.this,CustodianHomeScreenMain.class);
	        startActivity(myIntent1);
	        break;
		}
	}
}