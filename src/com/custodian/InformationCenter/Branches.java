package com.custodian.InformationCenter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.custodian.CustodianHomeScreenMain;
import com.custodian.CustodianMainLanding;
import com.custodian.R;

/***
 * This class opens when the user taps on the Branches icon used in followings
 * screens. InformationCenterMenuScreen class i.e Information Centre Main menu
 * screen , AboutCustodianDirect class, ClaimsProcess class.
 * 
 */
public class Branches extends Activity implements OnClickListener {

	// Declarations of views used in layout.
	ImageView mAddressTab, mPhoneTab, mHome;
	Intent myIntent;
	TextView mHeading;
	View view1, view2;
	RelativeLayout LayoutOfAddress, LayoutOfPhone;
	private RelativeLayout rel1, rel2;
	ImageButton mback;

	// OnBackPressed is used to disable the device back button as back button is
	// used in top bar for navigation.
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custodian_branches_main);

		// Initialisations and Declarations
		rel1 = (RelativeLayout) findViewById(R.id.rel_1);
		rel2 = (RelativeLayout) findViewById(R.id.rel_2);
		LayoutOfAddress = (RelativeLayout) this
				.findViewById(R.id.layout_address);
		
		Typeface face = Typeface.createFromAsset(getAssets(),
				"fonts/helvetica.otf");
		mHeading = (TextView) findViewById(R.id.title);
		mHeading.setTypeface(face);
		LayoutInflater mInflater = LayoutInflater.from(this);
		View mCustomView = mInflater.inflate(R.layout.custodian_action_bar,
				null);
		TextView mTitleTextView = (TextView) mCustomView
				.findViewById(R.id.title_text);
		mTitleTextView.setText("INFORMATION CENTER");
		mback = (ImageButton) findViewById(R.id.imageView1);
		mHome = (ImageView) findViewById(R.id.home);
		mHome.setOnClickListener(this);
		mAddressTab = (ImageView) findViewById(R.id.address_imag);
		mPhoneTab = (ImageView) findViewById(R.id.phone_imag);
		mback.setOnClickListener(this);
		mAddressTab.setOnClickListener(this);
		mPhoneTab.setOnClickListener(this);
		String getAboutKey = getIntent().getStringExtra("aboutKey");
		if (getAboutKey != null) {
			mHome.setVisibility(View.GONE);
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.imageView1:
			// Back button will navigate the user to previous screen.
			String getAboutKey = getIntent().getStringExtra("aboutKey");
			if (getAboutKey != null) {
				myIntent = new Intent(Branches.this,
						CustodianMainLanding.class);
				startActivity(myIntent);
			} else {
				myIntent = new Intent(Branches.this,
						InformationCenterMenuScreen.class);
				startActivity(myIntent);
			}

			break;

		case R.id.home:
			// Home button will navigate the user directly to home screen.
			myIntent = new Intent(Branches.this, CustodianHomeScreenMain.class);
			startActivity(myIntent);
			break;

		case R.id.address_imag:
			// Address tab when pressed will make the address layout visible.
			mAddressTab.setBackgroundResource(R.drawable.tab_address_selected);
			mPhoneTab.setBackgroundResource(R.drawable.tab_phone_number);
			rel1.setVisibility(View.VISIBLE);
			rel2.setVisibility(View.GONE);

			break;

		case R.id.phone_imag:
			// Phone tab when pressed will make the Phone layout visible.
			/**
			 * phone tab displays the phone numbers which have been made
			 * tappable so that when the user taps on any particular phone
			 * number,he would be able to make a call on the same number. Phone
			 * numbers have been made tappable using property android:autoLink
			 * (used in layout).
			 * 
			 */
			mAddressTab.setBackgroundResource(R.drawable.tab_address);
			mPhoneTab
					.setBackgroundResource(R.drawable.tab_phone_number_selected);
			rel2.setVisibility(View.VISIBLE);
			rel1.setVisibility(View.GONE);

			break;
		}
	}
}