package com.custodian.InformationCenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.custodian.Alerts;
import com.custodian.CONSTANT.CONSTANTS;
import com.custodian.CustodianHomeScreenMain;
import com.custodian.CustodianLoginScreen;
import com.custodian.CustodianMainLanding;
import com.custodian.CustodianWebservices.CustodianInterface;
import com.custodian.CustodianWebservices.MySSLSocketFactory;
import com.custodian.R;
import com.custodian.URLS.WebserviceURLs;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyStore;
import java.security.MessageDigest;


/**
 * Contact us class is used to submit the contact us form using webservice that
 * takes the id of the user as a json paramater.
 */
public class BuyAPolicyPaymentMessage extends Activity implements OnClickListener,
        CustodianInterface {

    boolean paymentSuccessful;
    TextView payment_status_message;
    ImageView payment_status_image;


    ImageButton mHome;
    Intent myIntent;
    Editor editor;
    ImageView btnContinue;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    Handler mSplaHandler = null;
    String getContactKey = "";

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }


    public TextView getPayment_status_message() {
        return payment_status_message;
    }

    public void setPayment_status_message(TextView payment_status_message) {
        this.payment_status_message = payment_status_message;
    }

    public ImageView getPayment_status_image() {
        return payment_status_image;
    }

    public void setPayment_status_image(ImageView payment_status_image) {
        this.payment_status_image = payment_status_image;
    }

    public Editor getEditor() {
        return editor;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
    }


    public boolean isPaymentSuccessful() {
        return paymentSuccessful;
    }

    public void setPaymentSuccessful(boolean paymentSuccessful) {
        this.paymentSuccessful = paymentSuccessful;
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.setSharedPreferences(this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE));
        this.setEditor(sharedPreferences.edit());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custodian_policy_create_vehicle_details_payment_message);

        mSplaHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                if (msg.what == 1) {
                    Showalerts(Alerts.LEADQUOTE_SUBMISSION_FAILURE);
                } else if (msg.what == 2) {
                    CheckStatus(Alerts.LEADQUOTE_SUBMISSION_SUCCESS);
                } else if (msg.what == 3) {
                    Showalerts(Alerts.CHECK_INTERNET);

                }
            }
        };

        mHome = (ImageButton) findViewById(R.id.home);
        mHome.setOnClickListener(this);
        btnContinue = (ImageView) findViewById(R.id.continue_imag_vehicleDetails);
        btnContinue.setOnClickListener(this);

        this.setPayment_status_message((TextView) findViewById(R.id.payment_status_message));
        this.setPayment_status_image((ImageView) findViewById(R.id.payment_status_image));


        try {
           // String interswitchRequeryResponse = this.requeryInterswitch(this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).getString("leadQuoteName", ""),this.getSharedPreferences().getString("premium", ""),this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).getString("leadQuoteID", ""),this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).getString("leadID", ""));
            String interswitchRequeryResponse = this.requeryInterswitch(this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).getString("leadQuoteName", ""),"200",this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).getString("leadQuoteID", ""),this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).getString("leadID", ""));

            Log.e("InterswitchResponse",interswitchRequeryResponse);

            JSONObject jsonObject = new JSONObject(interswitchRequeryResponse);
            String responseFromInterswitch = jsonObject.optString("ResponseDescription");

            if(jsonObject.optString("ResponseCode") == "00"){

                this.setPaymentSuccessful(true);

                //todo convert Lead to customer and convert insurance Lead quote to policy

                String leadToPolicyConversion = this.leadToPolicyConversion(this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).getString("leadQuoteID", ""),this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).getString("leadQuoteName", ""));

                Log.e("InterswitchResponse",leadToPolicyConversion);

                jsonObject = new JSONObject(leadToPolicyConversion);

                this.setPaymentSuccessful(true);


            }
            else{

                this.setPaymentSuccessful(false);
            }
            String uriToImage = null;
            Drawable res = null;
            if(this.isPaymentSuccessful()){
                this.getPayment_status_message().setText("Thank you, your transaction was successful \n"+responseFromInterswitch);
                uriToImage = "@drawable/confirmacao";  // where myresource (without the extension) is the file
                int imageResource = getResources().getIdentifier(uriToImage, null, getPackageName());
                res = getResources().getDrawable(imageResource);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    this.getPayment_status_image().setBackground(res);
                }
            }else {
                this.getPayment_status_message().setText("Sorry! your transaction was not successful \n "+responseFromInterswitch);
                uriToImage = "@drawable/signerroricon";  // where myresource (without the extension) is the file
                int imageResource = getResources().getIdentifier(uriToImage, null, getPackageName());
                res = getResources().getDrawable(imageResource);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    this.getPayment_status_image().setBackground(res);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int id = v.getId();
        switch (id) {
            case R.id.home:
                // Home button will navigate the user directly to home screen.
                myIntent = new Intent(BuyAPolicyPaymentMessage.this, InformationCenterMenuScreen.class);
                startActivity(myIntent);
                break;

            case R.id.continue_imag_vehicleDetails:

                editor = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).edit();
                editor.remove("title_spinner");
                editor.remove("occupation_spinner");
                editor.remove("surname");
                editor.remove("othername");
                editor.remove("address");
                editor.remove("username");
                editor.remove("password");
                editor.remove("dateofbirth");
                editor.remove("cover_spinner");
                editor.remove("insurance_enddate");
                editor.remove("insurance_startdate");
                editor.remove("vehicle_value");
                editor.remove("chassis_number");
                editor.remove("vehicle_make");
                editor.remove("reg_no");
                editor.remove("premium");
                editor.remove("payment_Option_Label");
                editor.remove("payment_Option");
                editor.commit();

                myIntent = new Intent(BuyAPolicyPaymentMessage.this,
                        CustodianMainLanding.class);
                startActivity(myIntent);

                break;
        }
    }


    private String requeryInterswitch(String leadQuoteName,String amount,String leadQuoteId, String leadid) throws Exception {
        // TODO Auto-generated method stub
        SharedPreferences sharedPreferences = this
                .getSharedPreferences(MyPREFERENCES,
                        Context.MODE_PRIVATE);
        HttpClient httpClient;
        httpClient = getNewHttpClient();

        HttpGet httpGet = new HttpGet(WebserviceURLs.INTERSWITCH_REQUERY_URL + "?productid=5528&transactionreference="+leadQuoteName+"&amount="+amount);
        UsernamePasswordCredentials credentials =
                new UsernamePasswordCredentials("root", "Admin$1234");
        BasicScheme scheme = new BasicScheme();
        Header authorizationHeader = null;
        try {
            authorizationHeader = scheme.authenticate(credentials, httpGet);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }

        String hash = "5528"+leadQuoteName+"4F9D8064BA433AD4859DEAD51AAB7684049AC7EF4765F56FFDD05692C80DAC2996435FDAD6850F65DE1327F53F8F4E614C72348E5CACDD340D44DEA474626A8E";

        String hashedtext = hashText(hash);

        httpGet.addHeader(authorizationHeader);
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Content-type", "application/json");
        httpGet.setHeader("Hash", hashedtext);
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpEntity httpEntity = httpResponse.getEntity();

        String response = null;
        try {
            response = EntityUtils.toString(httpEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;

    }




    private String leadToPolicyConversion(String leadQuoteId, String leadQuoteName) {
        // TODO Auto-generated method stub
        SharedPreferences sharedPreferences = this
                .getSharedPreferences(MyPREFERENCES,
                        Context.MODE_PRIVATE);
        HttpClient httpClient;
        httpClient = getNewHttpClient();

        HttpGet httpGet = new HttpGet(WebserviceURLs.LEAD_OUOTE_TO_POLICY + "?quoteId="+leadQuoteId+"&paymentMethod=Debit Card&paymentReference=" +leadQuoteName);
        UsernamePasswordCredentials credentials =
                new UsernamePasswordCredentials("root", "Admin$1234");
        BasicScheme scheme = new BasicScheme();
        Header authorizationHeader = null;
        try {
            authorizationHeader = scheme.authenticate(credentials, httpGet);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        httpGet.addHeader(authorizationHeader);
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Content-type", "application/json");
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpEntity httpEntity = httpResponse.getEntity();

        String response = null;
        try {
            response = EntityUtils.toString(httpEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;

    }








    public static String hashText(String textToHash) throws Exception {
        final MessageDigest sha512 = MessageDigest.getInstance("SHA-512");
        byte[] dataBytes = sha512.digest(textToHash.getBytes());

        return convertByteToHex(dataBytes);
    }

    public static String convertByteToHex(byte data[]) {
        StringBuffer hexData = new StringBuffer();
        for (int byteIndex = 0; byteIndex < data.length; byteIndex++)
            hexData.append(Integer.toString((data[byteIndex] & 0xff) + 0x100,
                    16).substring(1));

        return hexData.toString();
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


    /**
     * CheckStatus(String message) is used to show alerts.
     *
     * @param message message is the parameter that reflects the message to be shown
     *                to user when the status response from webservice is true and
     *                this alert on pressing OK button will navigate back to
     *                previous screen from where he came.
     */
    private void CheckStatus(String message) {
        // TODO Auto-generated method stub
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                BuyAPolicyPaymentMessage.this);
        // Setting Dialog Title
        alertDialog.setTitle("Custodian Direct");
        alertDialog.setCancelable(false);
        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.app_icon_48x48);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        if (getContactKey != null) {
                            // myIntent = new Intent(BuyAPolicyVehicledetails.this,
                            //       BuyAPolicyPayment.class);
                            //startActivity(myIntent);
                        } else {
                            //myIntent = new Intent(BuyAPolicyVehicledetails.this,
                            //      BuyAPolicyPayment.class);
                            //startActivity(myIntent);
                        }

                    }
                });
        alertDialog.show();
    }

    /**
     * Showalerts (String message) is used to show alerts.
     *
     * @param message message is the parameter that reflects the message to be shown
     *                to user when he fill any wrong data or leave fields blank.
     */
    private void Showalerts(String message) {
        // TODO Auto-generated method stub
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                BuyAPolicyPaymentMessage.this);
        // Setting Dialog Title
        alertDialog.setTitle("Custodian Direct");

        // Setting Dialog Message
        alertDialog.setMessage(message);

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
    public void onResponse(String response) {

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
