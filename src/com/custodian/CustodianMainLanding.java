package com.custodian;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.custodian.CONSTANT.CONSTANTS;
import com.custodian.InformationCenter.AboutCustodianDirect;
import com.custodian.InformationCenter.BuyAPolicy;
import com.custodian.InformationCenter.ContactUs;

public class CustodianMainLanding extends Activity implements OnClickListener {
	RelativeLayout relLogin, relBuyAPolicy, relBuyAboutCustodian, relContactUs;
	Intent mIntent;
	Button mBuyOneNow, mRegister, mForgotID, mForgotPassword;
	TextView txtLogin,txtBuyPolicy,txtAboutCustodian,txtContactUs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custodian_main_landing_screen);
		Typeface face = Typeface.createFromAsset(getAssets(),
				CONSTANTS.FONT_NAME);
		relLogin = (RelativeLayout) findViewById(R.id.landing_page_login_layout);
		relBuyAPolicy = (RelativeLayout) findViewById(R.id.landing_page_buy_one_policy_layout);
		relBuyAboutCustodian = (RelativeLayout) findViewById(R.id.landing_page_about_policy_layout);
		relContactUs = (RelativeLayout) findViewById(R.id.landing_page_contact_policy_layout);
		txtLogin = (TextView)findViewById(R.id.landing_page_login_layout_txt);
		txtLogin.setTypeface(face);
		txtBuyPolicy = (TextView)findViewById(R.id.landing_page_buy_layout_txt);
		txtBuyPolicy.setTypeface(face);
		txtAboutCustodian = (TextView)findViewById(R.id.landing_page_about_layout_txt);
		txtAboutCustodian.setTypeface(face);
		txtContactUs = (TextView)findViewById(R.id.landing_page_contact_layout_txt);
		txtContactUs.setTypeface(face);
//		mRegister = (Button) findViewById(R.id.login_screen_register);
//		mRegister.setTypeface(face);
//		mForgotID = (Button) findViewById(R.id.login_screen_forgot_user_id);
//		mForgotID.setTypeface(face);
//		mForgotPassword = (Button) findViewById(R.id.login_screen_forgot_password);
//		mForgotPassword.setTypeface(face);
		relLogin.setOnClickListener(this);
		relBuyAPolicy.setOnClickListener(this);
		relBuyAboutCustodian.setOnClickListener(this);
		relContactUs.setOnClickListener(this);
		//mRegister.setOnClickListener(this);
//		mForgotID.setOnClickListener(this);
//		mForgotPassword.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.landing_page_login_layout:
			mIntent = new Intent(CustodianMainLanding.this,
					CustodianLoginScreen.class);
			startActivity(mIntent);
			break;

		case R.id.landing_page_buy_one_policy_layout:
//			mIntent = new Intent(android.content.Intent.ACTION_VIEW);
//			mIntent.setData(Uri
//					.parse("http://www.custodiandirect.com/index.php?page=guest.GetAQuote&reset=1"));
//			startActivity(mIntent);
//			break;
			mIntent = new Intent(CustodianMainLanding.this,
					BuyAPolicy.class);
			mIntent.putExtra("aboutKey","aboutKey");
			startActivity(mIntent);
			break;

		case R.id.landing_page_about_policy_layout:
			mIntent = new Intent(CustodianMainLanding.this,
					AboutCustodianDirect.class);
			mIntent.putExtra("aboutKey","aboutKey");
			startActivity(mIntent);
			break;

		case R.id.landing_page_contact_policy_layout:
			mIntent = new Intent(CustodianMainLanding.this, ContactUs.class);
			mIntent.putExtra("contactKey","contactKey");
			startActivity(mIntent);
			break;

//		case R.id.login_screen_register:
//			mIntent = new Intent(android.content.Intent.ACTION_VIEW);
//			mIntent.setData(Uri
//					.parse("http://www.custodiandirect.com/index.php?page=guest.Register"));
//			startActivity(mIntent);
//			break;

//		// Forgot ID button used to navigate to forgot ID screen.
//		case R.id.login_screen_forgot_user_id:
//			mIntent = new Intent(CustodianMainLanding.this,
//					CustodianHomeScreenForgotID.class);
//			startActivity(mIntent);
//			break;
//
//		// Forgot Password button used to navigate to forgot password screen.
//		case R.id.login_screen_forgot_password:
//			mIntent = new Intent(CustodianMainLanding.this,
//					CustodianHomeScreenForgotPasswrd.class);
//			
//			startActivity(mIntent);
//			break;
		}
	}
}