package com.custodian;

import org.json.JSONException;
import org.json.JSONObject;

import com.custodian.CONSTANT.CONSTANTS;
import com.custodian.ClaimsCenter.ClaimsCenterMenuScreen;
import com.custodian.CustodianWebservices.CommonWebservice;
import com.custodian.CustodianWebservices.CustodianInterface;
import com.custodian.InformationCenter.InformationCenterMenuScreen;
import com.custodian.ManagePolicy.CustomerServiceMain;
import com.custodian.ManagePolicy.ViewYourDocuments;
import com.custodian.ManagePolicy.ViewYourPolicyMainScreen;
import com.custodian.ModelView.CustomerDetails;
import com.custodian.MyInfo.CustodianMyInfo;
import com.custodian.URLS.WebserviceURLs;
import com.navdrawer.SimpleSideDrawer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;

import android.os.Bundle;

import android.util.Log;

import android.view.View;
import android.view.View.OnClickListener;

import android.widget.ImageButton;

import android.widget.RelativeLayout;

import android.widget.TextView;

public class CustodianHomeScreenMain extends Activity implements
		OnClickListener, CustodianInterface {
	/**
	 * CustodianHomeScreenMain class is the class which comes after the user
	 * successfully logs in into his account.This class includes Main landing
	 * page that contains all the modules so that user can navigate to any
	 * module from this screen.The default module is Manage Policy.
	 */

	// OnBackPressed is used to disable the device back button as back button is
	// used in top bar for navigation.
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

	}

	// Declaration of views used in layout
	// ********************************************************************************
	TextView Manage, claimcenter, info_ctr, myInfo, log;
	RelativeLayout rel1, rel2, rel3, rel4, rel5, relLyout1, relLayout2,
			relLayout3, mainLayout;
	SimpleSideDrawer mSlidingMenu;
	CustomerDetails details;
	Intent myIntent;
	TextView mManagePoliciesMenu, mViewYourDocuments, mPaymentHistory;
	TextView txtMangePolicy, txtClaimCenter, txtInfoCenter, txtMyInfo, txtLog,
			mHeading;
	public static final String MyPREFERENCES = "MyPrefs";
	SharedPreferences sharedPreferences;
	JSONObject json;
	Editor editor;
	String username = "", password = "", userName = "", email = "",
			firstName = "", lastName = "", mobile = "", add1 = "", add2 = "",
			state = "", city = "", customerId = "", ownerId = "";

	// ********************************************************************************

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custodian_home_main);
		Typeface face = Typeface.createFromAsset(getAssets(),
				CONSTANTS.FONT_NAME);
		mHeading = (TextView) findViewById(R.id.title);
		mHeading.setTypeface(face);

		// slide the activity from left to right with a finger

		mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
		mainLayout.setOnTouchListener(new OnSwipeTouchListener(
				CustodianHomeScreenMain.this) {
			@Override
			public void onSwipeRight() {
				mSlidingMenu.toggleLeftDrawer();
			}
		});

		// get username and password from sharedpreferences as they are needed
		// in webservice as a parameter

		sharedPreferences = this.getSharedPreferences(MyPREFERENCES,
				Context.MODE_PRIVATE);
		username = sharedPreferences.getString("username", "");
		password = sharedPreferences.getString("password", "");
		Log.e("", "" + username);
		Log.e("", "" + password);

		// Webservice

		new CommonWebservice(WebserviceURLs.HOME_SCREEN + username,
				CustodianHomeScreenMain.this, CustodianHomeScreenMain.this,
				true, "Loading Customer Info...").execute();

		mSlidingMenu = new SimpleSideDrawer(CustodianHomeScreenMain.this);
		mSlidingMenu.setLeftBehindContentView(R.layout.sidebar);

		rel1 = (RelativeLayout) findViewById(R.id.Menu);
		rel1.setOnClickListener(this);
		rel1.setBackgroundColor(Color.GRAY);
		Manage = (TextView) findViewById(R.id.txt_Manage_Policy);
		Manage.setTextColor(Color.WHITE);

		rel2 = (RelativeLayout) findViewById(R.id.Claim_center_menu);
		claimcenter = (TextView) findViewById(R.id.txt_Claim_center);
		rel2.setOnClickListener(this);

		rel3 = (RelativeLayout) findViewById(R.id.Information_menu);
		info_ctr = (TextView) findViewById(R.id.txt_info_center);
		rel3.setOnClickListener(this);

		rel4 = (RelativeLayout) findViewById(R.id.My_Info_menu);
		myInfo = (TextView) findViewById(R.id.txt_my_info);
		rel4.setOnClickListener(this);
		rel5 = (RelativeLayout) findViewById(R.id.Logout_menu);

		log = (TextView) findViewById(R.id.txt_Log_out);

		rel5.setOnClickListener(this);

		((ImageButton) findViewById(R.id.imageView1))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						mSlidingMenu.toggleLeftDrawer();
					}
				});

		/**
		 * MANAGE POLICY MENU The following used particularly has been used for
		 * default page to be shown on right side with main landing page. The
		 * default page from main landing page is Manage Policy
		 */
		// *****************************************************************************************
		mManagePoliciesMenu = (TextView) findViewById(R.id.manage_policies_menu);
		mManagePoliciesMenu.setTypeface(face);
		relLyout1 = (RelativeLayout) findViewById(R.id.layout1);
		relLyout1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Navigate from Custodian Home Page to View Your Policy Screen.
				myIntent = new Intent(CustodianHomeScreenMain.this,
						ViewYourPolicyMainScreen.class);

				startActivity(myIntent);

			}
		});
		mPaymentHistory = (TextView) findViewById(R.id.make_payment_menu);
		mPaymentHistory.setTypeface(face);
		relLayout2 = (RelativeLayout) findViewById(R.id.layout2);
		relLayout2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Navigate from Custodian Home Page to Make Payment Screen.
				myIntent = new Intent(CustodianHomeScreenMain.this,
						CustomerServiceMain.class);
				startActivity(myIntent);
			}
		});
		mViewYourDocuments = (TextView) findViewById(R.id.view_your_document);
		mViewYourDocuments.setTypeface(face);
		relLayout3 = (RelativeLayout) findViewById(R.id.layout3);
		relLayout3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Navigate from Custodian Home Page to View Your Documents.
				myIntent = new Intent(CustodianHomeScreenMain.this,
						ViewYourDocuments.class);
				startActivity(myIntent);
			}
		});
		// **********************************************************************************************

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.Claim_center_menu:
			// Claim centre menu layout from main lanading page.
			myIntent = new Intent(CustodianHomeScreenMain.this,
					ClaimsCenterMenuScreen.class);
			myIntent.putExtra("details", details);
			startActivity(myIntent);
			break;

		case R.id.Information_menu:
			// Information centre menu layout from main landing page.
			myIntent = new Intent(CustodianHomeScreenMain.this,
					InformationCenterMenuScreen.class);
			myIntent.putExtra("details", details);
			startActivity(myIntent);
			break;

		case R.id.My_Info_menu:
			// My Info menu layout from main landing page.
			myIntent = new Intent(CustodianHomeScreenMain.this,
					CustodianMyInfo.class);
			myIntent.putExtra("details", details);
			startActivity(myIntent);
			break;

		case R.id.Logout_menu:
			// Logout menu layout from main landing page.
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(
					CustodianHomeScreenMain.this);
			alertDialog.setCancelable(false);
			// Setting Dialog Title
			alertDialog.setTitle("Custodian");

			// Setting Dialog Message
			alertDialog.setMessage(Alerts.HOME_SCREEN_LOGOUT);

			// Setting Icon to Dialog
			alertDialog.setIcon(R.drawable.app_icon_48x48);

			// Setting Positive "Yes" Button
			alertDialog.setPositiveButton("YES",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							ClearData();

							myIntent = new Intent(CustodianHomeScreenMain.this,
									CustodianMainLanding.class);
							startActivity(myIntent);
						}
					});

			// Setting Negative "NO" Button
			alertDialog.setNegativeButton("NO",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// Write your code here to invoke NO event
							dialog.cancel();
						}
					});

			// Showing Alert Message
			alertDialog.show();

			break;
		}
	}

	// ClearData() function is used to clear all the data when logout is
	// pressed.
	// *****************************************************************************************
	protected void ClearData() {
		// TODO Auto-generated method stub

		sharedPreferences = this.getSharedPreferences(MyPREFERENCES,
				Context.MODE_PRIVATE);

		Editor editor = sharedPreferences.edit();
		if (sharedPreferences.contains("username")
				|| sharedPreferences.contains("password")) {
			editor.remove("username");
			editor.remove("password");
		}
		editor.clear();
		editor.commit();
	}

	// *************************************************************************************************

	// Json Response parsing from webservice
	@Override
	public void onResponse(String response) {
		// TODO Auto-generated method stub

		try {
			json = new JSONObject(response);
			details = new CustomerDetails();
			ownerId = json.optString("id");
			details.setOwnerId(ownerId);
			editor = sharedPreferences.edit();
			editor.putString("id", ownerId);
			editor.commit();
			userName = json.getString("portalUserName");
			details.setUsername(userName);
			editor = sharedPreferences.edit();
			editor.putString("portalUserName", userName);
			editor.commit();
			Log.e("Name", userName);
			JSONObject jsonArray = json.getJSONObject("personOwner");
			email = jsonArray.getString("emailAddress");
			editor = sharedPreferences.edit();
			editor.putString("emailAddress", email);
			editor.commit();
			Log.e("1", email);
			firstName = jsonArray.getString("firstName");
			editor = sharedPreferences.edit();
			editor.putString("firstName", firstName);
			editor.commit();
			Log.e("2", firstName);
			lastName = jsonArray.getString("lastName");
			editor = sharedPreferences.edit();
			editor.putString("lastName", lastName);
			editor.commit();
			Log.e("3", lastName);
			mobile = jsonArray.getString("mobilePhone");
			editor.putString("mobilePhone", mobile);
			editor.commit();
			Log.e("4", mobile);
			add1 = jsonArray.getString("primaryStreet");
			editor = sharedPreferences.edit();
			editor.putString("primaryStreet", add1);
			editor.commit();
			Log.e("5", add1);
			add2 = jsonArray.getString("primaryStreet1");
			editor = sharedPreferences.edit();
			editor.putString("primaryStreet1", add2);
			editor.commit();
			Log.e("6", add2);
			state = jsonArray.getString("primaryState");
			editor = sharedPreferences.edit();
			editor.putString("primaryState", state);
			editor.commit();
			Log.e("7", state);
			city = jsonArray.getString("primaryCity");
			editor = sharedPreferences.edit();
			editor.putString("secondaryCity", city);
			editor.commit();
			Log.e("8", city);

			JSONObject jsonArray1 = json.getJSONObject("custom");
			customerId = jsonArray1.getString("id");

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

}
