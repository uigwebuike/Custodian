package com.custodian.ClaimsCenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.custodian.CustodianHomeScreenMain;
import com.custodian.R;

public class CustodianReportClaim extends Activity implements
		OnClickListener {

	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;
	DisplayMetrics metrics;
	int width;
	ImageButton startClaim, mback,mHome;
	TextView mHeading;
	Intent myIntent;
	SharedPreferences sharedPreferences;
	public static final String MyPREFERENCES = "MyPrefs" ;
	Editor editor;

	private static int prev = -1;
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custodian_report_claim_weather);
		metrics = new DisplayMetrics();
//		ActionBar mActionBar = getSupportActionBar();
//		mActionBar.hide();
		Typeface face = Typeface.createFromAsset(getAssets(),
				"fonts/helvetica.otf");
		mHeading = (TextView) findViewById(R.id.title);
		mHeading.setTypeface(face);

		LayoutInflater mInflater = LayoutInflater.from(this);

		View mCustomView = mInflater.inflate(R.layout.custodian_action_bar,
				null);
		TextView mTitleTextView = (TextView) mCustomView
				.findViewById(R.id.title_text);
		
		mHome = (ImageButton)findViewById(R.id.home);
		mHome.setOnClickListener(this);
		mTitleTextView.setText("INFORMATION CENTER");
		mback = (ImageButton) findViewById(R.id.imageView1);
		mback.setOnClickListener(this);
		startClaim = (ImageButton) findViewById(R.id.btnClaim);
		startClaim.setOnClickListener(this);
		startClaim.setVisibility(View.INVISIBLE);
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		width = metrics.widthPixels;
		// get the listview
		expListView = (ExpandableListView) findViewById(R.id.lvExp);
		
		// preparing list data
		prepareListData();

		listAdapter = new ExpandableListAdapter(this, listDataHeader,
				listDataChild, startClaim);
		expListView.setIndicatorBounds(width - GetDipsFromPixel(50), width
				- GetDipsFromPixel(50));
		// setting list adapter
		
		// and OnGroupExpandListener implement

		expListView.setCacheColorHint(0);
		expListView.setAdapter(listAdapter);
		//expListView.setOverScrollMode(View.OVER_SCROLL_NEVER);
		
		 
//		
//		expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
//
//			@Override
//			public void onGroupExpand(int groupPosition) {
//
//				startClaim.setVisibility(View.VISIBLE);
//
//			}
//		});
		


		expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {

				startClaim.setVisibility(View.INVISIBLE);

			}
		});
		
		expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
	        int previousGroup = -1;

	        @Override
	        public void onGroupExpand(int groupPosition) {
	        
	            if(groupPosition != previousGroup)
	                expListView.collapseGroup(previousGroup);
	            previousGroup = groupPosition;
	        	startClaim.setVisibility(View.VISIBLE);
	        }
	    });
	}
	


	/*
	 * Preparing the list data
	 */
	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

		// Adding child data
		listDataHeader.add("WEATHER");
		listDataHeader.add("THEFT/VANDALISM");
		listDataHeader.add("AUTO OTHER");

		// Adding child data
		List<String> Weather = new ArrayList<String>();
		Weather.add("You can start here to report damage from things like hail storms and flooding."
				+ "\n"
				+ "\n"
				+ "We will ask you about when and where it happened, and what the damage is like. You don't have to have every detail to get started now, we can help you fill in the blanks later."
				+ "\n"
				+ "\n"
				+ "In many cases you will be able to select a repair shop online or upload a repair estimate."
				+ "\n"
				+ "\n"
				+ "Once you submit the report you will see more information about your next steps and your coverage.");
		// top250.add("The Godfather");OI U DFWQJHIO
		// top250.add("The Godfather: Part II");
		// top250.add("Pulp Fiction");
		// top250.add("The Good, the Bad and the Ugly");
		// top250.add("The Dark Knight");
		// top250.add("12 Angry Men");

		List<String> Theft = new ArrayList<String>();
		Theft.add("You can start here to report things like:"
				+ "\n"
				+ "\n"
				+ "Theft of your entire vehicle."
				+ "\n"
				+ "\n"
				+ "Theft of a part of your vehicle."
				+ "\n"
				+ "\n"
				+ "Slashed tires, intentionally-scratched paint."
				+ "\n"
				+ "\n"
				+ "Any other kind of vandalism, theft, or malicious mischief"
				+ "\n"
				+ "\n"
				+ "We will ask you about when and where it happened, and what the damage is like. You don't have to have every detail to get started now-we can help you fill in the blanks later."
				+ "\n"
				+ "\n"
				+ "In many cases you will be able to select a repair shop online or upload a repair estimate."
				+ "\n"
				+ "\n"
				+ "Once you submit the report you will see more information about your next steps and your coverage.");
		// nowShowing.add("Despicable Me 2");
		// nowShowing.add("Turbo");
		// nowShowing.add("Grown Ups 2");
		// nowShowing.add("Red 2");
		// nowShowing.add("The Wolverine");

		List<String> Auto = new ArrayList<String>();
		Auto.add("You can start here to report damage from things like:"
				+ "\n"
				+ "\n"
				+ "A collision with another vehicle."
				+ "\n"
				+ "\n"
				+ "A collision with an object-like posts, guard rails, fences, trees, even a ditch or snow bank."
				+ "\n"
				+ "\n"
				+ "Objects falling on your vehicle."
				+ "\n"
				+ "\n"
				+ "Anything that doesn't fit in the categories of weather, theft/vandalism, or animal."
				+ "\n"
				+ "\n"
				+ "We will ask you about when and where it happened, and what the damage is like. You don't have to have every detail to get started now-we can help you fill in the blanks later."
				+ "\n"
				+ "\n"
				+ "In many cases you will be able to select a repair shop online or upload a repair estimate." 
				+ "\n"
				+ "\n"
				+ "Once you submit the report you will see more information about your next steps and your coverage.");
		// comingSoon.add("The Smurfs 2");
		// comingSoon.add("The Spectacular Now");
		// comingSoon.add("The Canyons");
		// comingSoon.add("Europa Report");

		listDataChild.put(listDataHeader.get(0), Weather); // Header, Child data
		listDataChild.put(listDataHeader.get(1), Theft);
		listDataChild.put(listDataHeader.get(2), Auto);
	}

	public int GetDipsFromPixel(float pixels) {
		// Get the screen's density scale
		final float scale = getResources().getDisplayMetrics().density;
		// Convert the dps to pixels, based on density scale
		return (int) (pixels * scale + 0.5f);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		int id = v.getId();
		switch (id) {
		case R.id.imageView1:
			myIntent = new Intent(CustodianReportClaim.this,
					ClaimsCenterMenuScreen.class);
			startActivity(myIntent);
			break;
			
			
		case R.id.home:
			myIntent = new Intent(CustodianReportClaim.this,
					CustodianHomeScreenMain.class);
			startActivity(myIntent);
			break;
			
		case R.id.btnClaim:
		AlertBoxToAcceptOrDontAccept();	
			
			break;
		}
	}

	private void AlertBoxToAcceptOrDontAccept() {
		// TODO Auto-generated method stub
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(CustodianReportClaim.this);
		 alertDialog.setCancelable(false); 
       // Setting Dialog Title
       alertDialog.setTitle("Custodian Direct");

       // Setting Dialog Message
       alertDialog.setMessage("Any person who knowingly presents a false or fraudulent claim for the payment of a loss is guilty of a crime and may be subject to fine and/or conviction.");

       // Setting Icon to Dialog
       alertDialog.setIcon(R.drawable.app_icon_48x48);

       // Setting Positive "Yes" Button
       alertDialog.setPositiveButton("I ACCEPT", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog,int which) {
           	
           	
        	   ClenAllPreferencesOfREportClaim();
           	
        	   myIntent = new Intent(CustodianReportClaim.this,
   					ReportClaimStart.class);
        	   
   			startActivity(myIntent); 
           }
       });

       // Setting Negative "NO" Button
       alertDialog.setNegativeButton("I DO NOT ACCEPT", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int which) {
           // Write your code here to invoke NO event
          // Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
           dialog.cancel();
           }
       });

       // Showing Alert Message
       alertDialog.show();

	}

	protected void ClenAllPreferencesOfREportClaim() {
		// TODO Auto-generated method stub
		   sharedPreferences  = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
			 editor = sharedPreferences.edit();
		   editor.putString("MainKey","Report");
//  			if(sharedPreferences.contains("Basics")||sharedPreferences.contains("WhoWasInvolvedID")
//  					||sharedPreferences.contains("Vehicles"))
//
//  					{
//  			
//  				editor.remove("Basics");
//  				editor.remove("WhoWasInvolvedID");
//  				editor.remove("Start");
//  				editor.remove("Vehicles");
////  				editor.remove("Registered");
////  				editor.remove("Uploaded");
////  				
 				editor.commit();
  				
  			}
	}
