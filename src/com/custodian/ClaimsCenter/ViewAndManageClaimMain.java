package com.custodian.ClaimsCenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.custodian.Alerts;

import com.custodian.CustodianHomeScreenMain;
import com.custodian.R;
import com.custodian.CONSTANT.CONSTANTS;
import com.custodian.CustodianWebservices.CommonWebservice;
import com.custodian.CustodianWebservices.CustodianInterface;
import com.custodian.ModelView.CustodianClaims;
import com.custodian.ModelView.CustomerDetails;
import com.custodian.URLS.WebserviceURLs;
/**
 * Class to display records of all claims.If any claim is created then it would be
 * reflected in this screen. Newly created claim will be seen as  the 
 * first record.That is how the claims have been arranged in descending order.
 *
 */
public class ViewAndManageClaimMain extends Activity implements CustodianInterface,OnClickListener{
	
	/**
	 * Declarations of views and Strings used.
	 */
	JSONObject json;
	ImageView mHome;
	TextView mTitlebar,mHeading;
	Intent myIntent;
	ListView listView;
	ViewAndManageClaimAdapter adapter ;
	ArrayList<CustodianClaims> arraylist;
	public static final String MyPREFERENCES = "MyPrefs" ;
	CustomerDetails details;
	String OWNERID;
	ImageButton mback;
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
        
		
        setContentView(R.layout.custodian_view_and_manage_main); 
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
         * Initialisations , typeface of textviews and listeners
         */
        json = new JSONObject();
        sharedPreferences  = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
   	 	String OWNERID =  sharedPreferences.getString("id","");
   	 	Log.e("id",OWNERID);
        Typeface face = Typeface.createFromAsset(getAssets(),CONSTANTS.FONT_NAME);
        mHeading = (TextView)findViewById(R.id.title);
        mHeading.setGravity(Gravity.CENTER);
        mHeading.setText("VIEW & MANAGE CLAIMS");  
        mHeading.setTypeface(face);
        mback = (ImageButton)findViewById(R.id.imageView1);
        mback.setOnClickListener(this);
        mHome = (ImageView)findViewById(R.id.home);
        mHome.setOnClickListener(this);
        ConnectivityManager connectivityManager 
        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
  NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
   if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){
	   new CommonWebservice(WebserviceURLs.VIEW_MANAGE_CLAIMS_MAIN+OWNERID+WebserviceURLs.VIEW_MANAGE_CLAIMS_MAIN_APPEND,  ViewAndManageClaimMain.this,
				ViewAndManageClaimMain.this, true,"Loading Claims...").execute();
					
   }else{
	   mSplaHandler.sendEmptyMessage(2);
   }
      
}
	//Json response parsed
	@Override
	public void onResponse(String response) {
		// TODO Auto-generated method stub
	//	,"claimNo":"000001201","contactEmail":"chinemelum.ozigb@zanibal.com","contactPhone":"08130639935","createdBy":"2200","createdDttm":1387378973000,"currency":1400,"customer":10002,"dateOfIncident":1387324800000,"didAirbagInflate":"NO","engineNo":"66588","howManyPassengers":2,"howWasVehicleUsed":"Work","id":1201,"label":" CLAIM FOR femi  dayo - 664884","lastModifiedBy":"2200","lastModifiedDttm":1387380614000,"lastViewedBy":"2200","lastViewedByDttm":1387380628000,"name":"000001201","policy":1201,"policyLine":1201,"state":"AN","status":"ADJUSTER","timeOfIncident":"12am - 1am","vehicleColor":"black","vehicleDamage":"I was hit at the back of my car","vehicleNo":"664884","wasVehicleDamaged":"No","wasVehicleTowed":"Yes","whatHappened":"MY VEHICLE WAS HIT WHILE IT WAS PARKED","whoWasDriving":"POLICY OWNER"},{"active":true,"address":"123 phone street","adjusterEstimate":0.00,"approvedEstimate":0.00,"autoShop":3608,"autoShopEstimate":0.00,"chassisNo":"66739","city":"ABOH","claimNo":"000001600","contactEmail":"chinemelum.ozigb@zanibal.com","contactPhone":"08130639935","createdBy":"2200","createdDttm":1394281675000,"customer":10002,"dateOfIncident":1393632000000,"didAirbagInflate":"NO","engineNo":"66588","howManyPassengers":1,"howWasVehicleUsed":"Work","id":1600,"label":" CLAIM FOR femi dayo - 664884","lastModifiedBy":"2200","lastModifiedDttm":1394281675000,"lastViewedBy":"2200","lastViewedByDttm":1394281675000,"name":"000001600","policy":1201,"policyLine":1201,"state":"DT","status":"NEW","timeOfIncident":"12am - 1am","vehicleColor":"black","vehicleDamage":"hello","vehicleNo":"664884","wasVehicleDamaged":"Yes","wasVehicleTowed":"No","whatHappened":"MY VEHICLE WAS HIT WHILE IT WAS PARKED","whoWasDriving":"POLICY OWNER"},{"active":true,"address":"ruyrtu","adjusterEstimate":0.00,"approvedEstimate":0.00,"autoShop":3608,"autoShopEstimate":0.00,"chassisNo":"66739","city":"FUFORE","claimNo":"000001800","contactEmail":"chinemelum.ozigb@zanibal.com","contactPhone":"08130639935","createdBy":"2200","createdDttm":1395647134000,"customer":10002,"dateOfIncident":1394582400000,"didAirbagInflate":"YES","engineNo":"66588","howManyPassengers":1,"howWasVehicleUsed":"Work","id":1800,"label":" CLAIM FOR femi dayo - 664884","lastModifiedBy":"2200","lastModifiedDttm":1395647134000,"lastViewedBy":"2200","lastViewedByDttm":1395647134000,"name":"000001800","policy":1201,"policyLine":1201,"state":"AD","status":"NEW","timeOfIncident":"12am - 1am","vehicleColor":"black","vehicleDamage":"httytyyyt tytytytyt yt ytytytytty yyty yyy","vehicleNo":"664884","wasVehicleDamaged":"Yes","wasVehicleTowed":"Yes","whatHappened":"MY VEHICLE HIT A PARKED VEHICLE","whoWasDriving":"POLICY OWNER"},{"active":true,"address":"ty45ew6","adjusterEstimate":0.00,"approvedEstimate":0.00,"autoShop":3608,"autoShopEstimate":0.00,"chassisNo":"66739","city":"APOMU","claimNo":"000002000","contactEmail":"chinemelum.ozigb@zanibal.com","contactPhone":"08130639935","createdBy":"2200","createdDttm":1396524627000,"customer":10002,"dateOfIncident":1396310400000,"didAirbagInflate":"YES","engineNo":"66588","howManyPassengers":1,"howWasVehicleUsed":"Work","id":2000,"label":" CLAIM FOR femi dayo - 664884","lastModifiedBy":"2200","lastModifiedDttm":1396524627000,"lastViewedBy":"2200","lastViewedByDttm":1396524627000,"name":"000002000","policy":1201,"policyLine":1201,"state":"OS","status":"NEW","timeOfIncident":"12am - 1am","vehicleColor":"black","vehicleDamage":"hbfth","vehicleNo":"664884","wasVehicleDamaged":"Yes","wasVehicleTowed":"Yes","whatHappened":"MY VEHICLE HIT A PARKED VEHICLE","whoWasDriving":"NO DRIVER"},{"active":true,"address":"fgd","adjusterEstimate":0.00,"approvedEstimate":0.00,"autoShop":3608,"autoShopEstimate":0.00,"chassisNo":"66739","city":"ABAK","claimNo":"000002001","contactEmail":"chinemelum.ozigb@zanibal.com","contactPhone":"08130639935","createdBy":"2200","createdDttm":1396524770000,"customer":10002,"dateOfIncident":1396396800000,"didAirbagInflate":"YES","engineNo":"66588","howManyPassengers":2,"howWasVehicleUsed":"Work","id":2001,"label":" CLAIM FOR femi dayo - 664884","lastModifiedBy":"2200","lastModifiedDttm":1396524770000,"lastViewedBy":"2200","lastViewedByDttm":1396524770000,"name":"000002001","policy":1201,"policyLine":1201,"state":"AK","status":"NEW","timeOfIncident":"2am - 3am","vehicleColor":"black","vehicleDamage":"fth","vehicleNo":"664884","wasVehicleDamaged":"Yes","wasVehicleTowed":"Yes","whatHappened":"MY VEHICLE WAS HIT WHILE IT WAS PARKED","whoWasDriving":"NO DRIVER"}]}{"count":5,"result":[{"active":true,"address":"Lekki","adjuster":3609,"adjusterEstimate":0.00,"approvedEstimate":0.00,"assignedTo":5313,"autoShop":3608,"autoShopEstimate":0.00,"chassisNo":"66739","city":"AGUATA","claimNo":"000001201","contactEmail":"chinemelum.ozigb@zanibal.com","contactPhone":"08130639935","createdBy":"2200","createdDttm":1387378973000,"currency":1400,"customer":10002,"dateOfIncident":1387324800000,"didAirbagInflate":"NO","engineNo":"66588","howManyPassengers":2,"howWasVehicleUsed":"Work","id":1201,"label":" CLAIM FOR femi  dayo - 664884","lastModifiedBy":"2200","lastModifiedDttm":1387380614000,"lastViewedBy":"2200","lastViewedByDttm":1387380628000,"name":"000001201","policy":1201,"policyLine":1201,"state":"AN","status":"ADJUSTER","timeOfIncident":"12am - 1am","vehicleColor":"black","vehicleDamage":"I was hit at the back of my car","vehicleNo":"664884","wasVehicleDamaged":"No","wasVehicleTowed":"Yes","whatHappened":"MY VEHICLE WAS HIT WHILE IT WAS PARKED","whoWasDriving":"POLICY OWNER"},{"active":true,"address":"123 phone street","adjusterEstimate":0.00,"approvedEstimate":0.00,"autoShop":3608,"autoShopEstimate":0.00,"chassisNo":"66739","city":"ABOH","claimNo":"000001600","contactEmail":"chinemelum.ozigb@zanibal.com","contactPhone":"08130639935","createdBy":"2200","createdDttm":1394281675000,"customer":10002,"dateOfIncident":1393632000000,"didAirbagInflate":"NO","engineNo":"66588","howManyPassengers":1,"howWasVehicleUsed":"Work","id":1600,"label":" CLAIM FOR femi dayo - 664884","lastModifiedBy":"2200","lastModifiedDttm":1394281675000,"lastViewedBy":"2200","lastViewedByDttm":1394281675000,"name":"000001600","policy":1201,"policyLine":1201,"state":"DT","status":"NEW","timeOfIncident":"12am - 1am","vehicleColor":"black","vehicleDamage":"hello","vehicleNo":"664884","wasVehicleDamaged":"Yes","wasVehicleTowed":"No","whatHappened":"MY VEHICLE WAS HIT WHILE IT WAS PARKED","whoWasDriving":"POLICY OWNER"},{"active":true,"address":"ruyrtu","adjusterEstimate":0.00,"approvedEstimate":0.00,"autoShop":3608,"autoShopEstimate":0.00,"chassisNo":"66739","city":"FUFORE","claimNo":"000001800","contactEmail":"chinemelum.ozigb@zanibal.com","contactPhone":"08130639935","createdBy":"2200","createdDttm":1395647134000,"customer":10002,"dateOfIncident":1394582400000,"didAirbagInflate":"YES","engineNo":"66588","howManyPassengers":1,"howWasVehicleUsed":"Work","id":1800,"label":" CLAIM FOR femi dayo - 664884","lastModifiedBy":"2200","lastModifiedDttm":1395647134000,"lastViewedBy":"2200","lastViewedByDttm":1395647134000,"name":"000001800","policy":1201,"policyLine":1201,"state":"AD","status":"NEW","timeOfIncident":"12am - 1am","vehicleColor":"black","vehicleDamage":"httytyyyt tytytytyt yt ytytytytty yyty yyy","vehicleNo":"664884","wasVehicleDamaged":"Yes","wasVehicleTowed":"Yes","whatHappened":"MY VEHICLE HIT A PARKED VEHICLE","whoWasDriving":"POLICY OWNER"},{"active":true,"address":"ty45ew6","adjusterEstimate":0.00,"approvedEstimate":0.00,"autoShop":3608,"autoShopEstimate":0.00,"chassisNo":"66739","city":"APOMU","claimNo":"000002000","contactEmail":"chinemelum.ozigb@zanibal.com","contactPhone":"08130639935","createdBy":"2200","createdDttm":1396524627000,"customer":10002,"dateOfIncident":1396310400000,"didAirbagInflate":"YES","engineNo":"66588","howManyPassengers":1,"howWasVehicleUsed":"Work","id":2000,"label":" CLAIM FOR femi dayo - 664884","lastModifiedBy":"2200","lastModifiedDttm":1396524627000,"lastViewedBy":"2200","lastViewedByDttm":1396524627000,"name":"000002000","policy":1201,"policyLine":1201,"state":"OS","status":"NEW","timeOfIncident":"12am - 1am","vehicleColor":"black","vehicleDamage":"hbfth","vehicleNo":"664884","wasVehicleDamaged":"Yes","wasVehicleTowed":"Yes","whatHappened":"MY VEHICLE HIT A PARKED VEHICLE","whoWasDriving":"NO DRIVER"},{"active":true,"address":"fgd","adjusterEstimate":0.00,"approvedEstimate":0.00,"autoShop":3608,"autoShopEstimate":0.00,"chassisNo":"66739","city":"ABAK","claimNo":"000002001","contactEmail":"chinemelum.ozigb@zanibal.com","contactPhone":"08130639935","createdBy":"2200","createdDttm":1396524770000,"customer":10002,"dateOfIncident":1396396800000,"didAirbagInflate":"YES","engineNo":"66588","howManyPassengers":2,"howWasVehicleUsed":"Work","id":2001,"label":" CLAIM FOR femi dayo - 664884","lastModifiedBy":"2200","lastModifiedDttm":1396524770000,"lastViewedBy":"2200","lastViewedByDttm":1396524770000,"name":"000002001","policy":1201,"policyLine":1201,"state":"AK","status":"NEW","timeOfIncident":"2am - 3am","vehicleColor":"black","vehicleDamage":"fth","vehicleNo":"664884","wasVehicleDamaged":"Yes","wasVehicleTowed":"Yes","whatHappened":"MY VEHICLE WAS HIT WHILE IT WAS PARKED","whoWasDriving":"NO DRIVER"}]}
		CustodianClaims cust = new CustodianClaims();
		try {
			json = new JSONObject(response);
			arraylist = new ArrayList<CustodianClaims>();
			String count  = json.getString("count");
			Log.e("count",count);
			if(count.equals("0")){
		//	Showalerts(Alerts.NO_RECORDS_FOUND);	
			mSplaHandler.sendEmptyMessage(1);
				
			}
			else{
				JSONArray jsonArray = json.getJSONArray("result"); 
				 for (int i=0; i < jsonArray.length(); i++)
				    {
					 JSONObject oneObject = jsonArray.getJSONObject(i);
					 String strClaimNo = oneObject.getString("claimNo");
					 cust=new CustodianClaims();
					 cust.setClaimNo(strClaimNo);
					 Log.e("11",strClaimNo);
					 String strDateOfIncident = oneObject.getString("dateOfIncident").trim();
					
				        
					 String x= strDateOfIncident;
					  SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
			
					    long milliSecondsstart= Long.parseLong(x);
					    System.out.println(milliSecondsstart);
			
					    Calendar calendarStart = Calendar.getInstance();
					    calendarStart .setTimeInMillis(milliSecondsstart);
					   final String parsed = (dateFormat.format(calendarStart .getTime())); 
					 cust.setDateOfIncident(parsed);
					 Log.e("22",strDateOfIncident);
					 String strStatus = oneObject.getString("status");
					 cust.setAdjuster(strStatus);
					 Log.e("33",strStatus);
					 String strDesc = oneObject.getString("whatHappened");
					 cust.setDescription(strDesc);
					 Log.e("44",strDesc);
					 
					 arraylist.add(cust);
					
					
					 String strTimeOfIncident = oneObject.getString("timeOfIncident");
					 cust.setStrTimeOfIncident(strTimeOfIncident);
					 String strState = oneObject.getString("state");
					 cust.setStrState(strState);
					
					 String strCity = oneObject.getString("city");
					 cust.setStrCity(strCity);
					 String strChassis = oneObject.getString("chassisNo");
					 cust.setStrChassis(strChassis);
					 String strContactEmail = oneObject.getString("contactEmail");
					 cust.setStrContactEmail(strContactEmail);
					 String strContactPhone = oneObject.getString("contactPhone");
					 cust.setStrContactPhone(strContactPhone);
					 String strdidAirbags = oneObject.getString("didAirbagInflate");
					 cust.setStrdidAirbags(strdidAirbags);
					 String strEngineNo = oneObject.getString("engineNo");
					 cust.setStrEngineNo(strEngineNo);
					 String strVehicleUse = oneObject.getString("howWasVehicleUsed");
					 cust.setStrVehicleUse(strVehicleUse);
					 String strVehicleColor = oneObject.getString("vehicleColor");
					 cust.setStrVehicleColor(strVehicleColor);
					 String strVehicleNo = oneObject.getString("vehicleNo");
					 cust.setVehicleNo(strVehicleNo);
					 Log.e("vehicle no",""+strVehicleNo);
					 String strVehicleDamage = oneObject.getString("vehicleDamage");
					 cust.setVehicleDamage(strVehicleDamage);
					 String strWasDamged = oneObject.getString("wasVehicleDamaged");
					 cust.setStrWasDamged(strWasDamged);
					 String strVehicleTowed = oneObject.getString("wasVehicleTowed");
					 cust.setStrVehicleTowed(strVehicleTowed);
					 String whoWasDriving  = oneObject.getString("whoWasDriving");
					 cust.setWhoWasDriving(whoWasDriving);
					 
					 String strWhatHappened = oneObject.getString("whatHappened");
					 cust.setWhatHappened(strWhatHappened);
					 SharedPreferences sharedPreferences  = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
					 Editor editor = sharedPreferences.edit();
					 editor.putString("vehicles", strWhatHappened);
				    }
				 
				  //Collections.reverse(arraylist);
				 listView = (ListView)findViewById(R.id.listOfCustodian_claim_center);
				 adapter = new ViewAndManageClaimAdapter(ViewAndManageClaimMain.this, arraylist);
				 listView.setAdapter(adapter);
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void Showalerts(String message) {
		// TODO Auto-generated method stub
		//AlertDialog.Builder alertDialog = new AlertDialog.Builder(CustodianHomeScreen.this);
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(ViewAndManageClaimMain.this);
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
 
            dialog.cancel();
            }
        });
        alertDialog.show();
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
		Intent	myIntent1 = new Intent(ViewAndManageClaimMain.this,CustodianHomeScreenMain.class);
	        startActivity(myIntent1);
	        break;
		}
		}
	}
	
