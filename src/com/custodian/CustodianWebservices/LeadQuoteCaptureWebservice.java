package com.custodian.CustodianWebservices;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.security.KeyStore;


public class LeadQuoteCaptureWebservice extends AsyncTask<Void, Void, Void> {

	String strUrl ="";
	String strEntity;
	CustodianInterface gListener;
	String response = "";
	ProgressDialog mDialog;
	Context gContext;
	boolean isDialog;
	JSONObject nameValuePairs;
	String Message;

	public LeadQuoteCaptureWebservice(String strUrl, String strEntity, CustodianInterface mListener,
									  Context gContext, boolean isDialog, JSONObject nameValuePairs1, String Message) {
		// TODO Auto-generated constructor stub
		this.strEntity = strEntity;
		this.gListener = mListener;
		this.strUrl = strUrl;
		this.gContext = gContext;
		this.isDialog = isDialog;
		this.Message = Message;
		this.nameValuePairs=nameValuePairs1;
	}


	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub

		super.onPreExecute();
		if (isDialog == true) {
			mDialog = ProgressDialog.show(gContext, Message, "");
		}

	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if (!response.equalsIgnoreCase("") && response != null) {
			gListener.onResponse(response);
			
			
			if (isDialog==true) {
				mDialog.dismiss();
				
				
			}
		
		} else {
			Log.e("-----", "Error");
			gListener.onResponse(response);
			if (isDialog==true) {
				mDialog.dismiss();
			}
		}
	
	
	}

	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		try {
			
			HttpClient httpClient;
			httpClient = 	getNewHttpClient();
			
			//HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost= new HttpPost(strUrl);
			UsernamePasswordCredentials credentials =
	                new UsernamePasswordCredentials("custodian", "Welcome123");
	            BasicScheme scheme = new BasicScheme();
	            Header authorizationHeader = scheme.authenticate(credentials, httpPost);
	            httpPost.addHeader(authorizationHeader);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			StringEntity se = new StringEntity(nameValuePairs.toString());  
			Log.e("nameValuePairs",""+nameValuePairs);
			httpPost.setEntity(se);
		

			HttpResponse httpResponse = httpClient.execute(httpPost);
			
			HttpEntity httpEntity = httpResponse.getEntity();
			 int status = httpResponse.getStatusLine().getStatusCode();
			String Response = EntityUtils.toString(httpEntity);
			response = Response;
			Log.e("Response_of_ImageUpload",response);
			 Log.e("status",""+status);
			 
			
		
		} catch (Exception e) {
			e.printStackTrace();

		}

		return null;


	
	}
	
	
	public HttpClient getNewHttpClient() {
	    try {
	        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
	        trustStore.load(null, null);

	        SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
	        sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

	        HttpParams params = new BasicHttpParams();
	        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

	        SchemeRegistry registry = new SchemeRegistry();
	        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
	        registry.register(new Scheme("https", sf, 443));

	        ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

	        return new DefaultHttpClient(ccm, params);
	    } catch (Exception e) {
	        return new DefaultHttpClient();
	    }
	
	}	
}