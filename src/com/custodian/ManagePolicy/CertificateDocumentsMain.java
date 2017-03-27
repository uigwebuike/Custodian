package com.custodian.ManagePolicy;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.custodian.CustodianHomeScreenMain;
import com.custodian.R;
import com.custodian.CONSTANT.CONSTANTS;
import com.custodian.ModelView.PolicyDocuments;
/**
 *  Class to load the certificate documents in webview.
 */
public class CertificateDocumentsMain extends Activity{
	/*
	 * Declarations of views used in layout, preferences.
	 */
	WebView webView;
	TextView mHeading;
	ProgressDialog m_progressDialog;
	PolicyDocuments cust;
	public static final String MyPREFERENCES = "MyPrefs" ;
	SharedPreferences sharedPreferences;
	ImageButton mback,mHome;
	Intent intent;
	StringBuilder url1;
	/*
	 *  OnBackPressed is used to disable the device back button as back button is used in top bar for navigation.(non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pdfdocuments);
		mHome = (ImageButton)findViewById(R.id.home);
		mHome.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			//navigate to Home screen.
				intent = new Intent(CertificateDocumentsMain.this,
						CustodianHomeScreenMain.class);
				startActivity(intent);	
			}
		});
		mback = (ImageButton)findViewById(R.id.imageView1);
		mback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			//	navigate back to previous screen.
				finish();
			}
		});
		
		/*
		 * Preferences used to get firstname and lastname to send them
		 * as  a parameter in url.
		 */
		  	sharedPreferences  = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
	   	 	 String firstName=  sharedPreferences.getString("firstName","");
	   	 	 Log.e("firstName",firstName);
	   	 	String lastName=  sharedPreferences.getString("lastName","");
	   	 	Log.e("lastName",lastName);
	   	 	 
	   	 
		 Intent i = getIntent();
		String policyNumber = (String)i.getSerializableExtra("policyno");
		Log.e("policy",policyNumber);
		Typeface face = Typeface.createFromAsset(getAssets(),
				CONSTANTS.FONT_NAME);
		mHeading = (TextView) findViewById(R.id.title);
		mHeading.setText("CERTIFICATE OF INSURANCE");
		mHeading.setTypeface(face);
		m_progressDialog = new ProgressDialog(this);

	
		
		
		webView = (WebView) findViewById(R.id.webView1);
		webView.setContentDescription("application/pdf");
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setDomStorageEnabled(true);
		webView.getSettings().setAppCacheEnabled(true);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setBuiltInZoomControls(true);
		 url1 = new StringBuilder();
		String MyURL = "http://docs.google.com/viewer?url=http://www.custodiandirect.com/pdf/makepdf.php?template=certificate&docNo="+policyNumber+"&customer=";
		Log.e("checkurl",MyURL);
		url1.append("http://docs.google.com/viewer?url=http://www.custodiandirect.com/pdf/makepdf.php?template=certificate&docNo="+policyNumber+"&customer=");
		url1.append(firstName);
		url1.append(lastName);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl(url1.toString());
		/*
		 * Used to load the page in webview and show loader till
		 * the page is loaded completely.
		 */
	
		webView.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
				try {

					m_progressDialog.setMessage("Loading Document...");
					m_progressDialog.show();
				} catch (Exception e) {
					e.printStackTrace();

				}
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				try {
					m_progressDialog.dismiss();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
	
	
}
