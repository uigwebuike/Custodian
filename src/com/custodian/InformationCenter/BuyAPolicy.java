package com.custodian.InformationCenter;

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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import com.custodian.Alerts;
import com.custodian.CONSTANT.CONSTANTS;
import com.custodian.CustodianHomeScreenMain;
import com.custodian.CustodianMainLanding;
import com.custodian.CustodianWebservices.CustodianInterface;
import com.custodian.CustodianWebservices.LeadCaptureWebservice;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyStore;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

/**
 * Contact us class is used to submit the contact us form using webservice that
 * takes the id of the user as a json paramater.
 */
public class BuyAPolicy extends Activity implements OnClickListener,
        CustodianInterface {
    // Declaration of views.
    EditText surname, othername, address, username,password, emailaddress,phonenumber,otherOccupation;
    DatePicker dateofbirth;
    Spinner title_spinner, occupation_spinner;
    String flag;

    boolean value;

    ImageView btnContact, img_email, img_phone;
    ImageButton mHome;
    Intent myIntent;
    JSONObject json;
    Editor editor;
    ImageButton mback;
    ImageView btnContinue;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    Handler mSplaHandler = null;
    String getContactKey = "";
    String subject, desc, email, phone_number;
    private static final Pattern LoginEmail_PATTERN = Pattern
            .compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    private static final Pattern PHONE_NO_PATTERN = Pattern.compile("^[0-9]*$");

    // OnBackPressed is used to disable the device back button as back button is
    // used in top bar for navigation.

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub

    }


    public class ShowHideOthersTextView implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent,
                                   View view, int pos, long id) {
            String selected =  parent.getItemAtPosition(pos).toString();
            if(selected.contains("Others")){
                otherOccupation.setVisibility(View.VISIBLE);

            }else{
                otherOccupation.setVisibility(View.GONE);

            }
        }

        public void onNothingSelected(AdapterView parent) {

        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custodian_policy_create_start);

        mSplaHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                if (msg.what == 1) {
                    Showalerts(Alerts.LEAD_SUBMISSION_FAILURE);
                } else if (msg.what == 2) {
                    CheckStatus(Alerts.LEAD_SUBMISSION_SUCCESS);
                } else if (msg.what == 3) {
                    Showalerts(Alerts.CHECK_INTERNET);

                }
            }
        };
        sharedPreferences  = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mback = (ImageButton) findViewById(R.id.imageView1);
        mback.setOnClickListener(this);
        mHome = (ImageButton) findViewById(R.id.home);
        mHome.setOnClickListener(this);
        btnContinue = (ImageView)findViewById(R.id.continue_imag_policy);
        btnContinue.setOnClickListener(this);
        Typeface face = Typeface.createFromAsset(getAssets(),
                CONSTANTS.FONT_NAME);
        title_spinner = (Spinner) findViewById(R.id.title_spinner) ;
        occupation_spinner = (Spinner) findViewById(R.id.occupation_spinner);
        surname = (EditText) findViewById(R.id.surname) ;
        surname.setTypeface(face);
        othername = (EditText) findViewById(R.id.othernames);
        othername.setTypeface(face);
        address = (EditText) findViewById(R.id.address);
        address.setTypeface(face);
        username  = (EditText) findViewById(R.id.username);
        username.setTypeface(face);
        dateofbirth = (DatePicker) findViewById(R.id.dateofbirth);
        emailaddress = (EditText) findViewById(R.id.emailaddress);
        emailaddress.setTypeface(face);

        emailaddress.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    if(username.getText().toString().length()<1){
                        username.setText(emailaddress.getText().toString());
                    }
                }
            }
        });
        phonenumber = (EditText) findViewById(R.id.phonenumber);
        phonenumber.setTypeface(face);
        otherOccupation =  (EditText) findViewById(R.id.otherOccupation);
        occupation_spinner.setOnItemSelectedListener(new BuyAPolicy.ShowHideOthersTextView());
        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(!hasFocus){
                    try {
                        String response = checkUsername(username.getText().toString());
                        JSONObject jsonObject = new JSONObject(response);
                        if(jsonObject.optString("msgCode") != "DOCUMENT_NOT_FOUND"){
                            Showalerts(Alerts.USERNAME_EXSIST);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //purpulate Values
/*
        surname.setText(sharedPreferences.getString("surname","").length()>1?sharedPreferences.getString("surname",""):"");

        othername.setText(sharedPreferences.getString("othername","").length()>1?sharedPreferences.getString("othername",""):"");

        address.setText(sharedPreferences.getString("address","").length()>1?sharedPreferences.getString("address",""):"");

        emailaddress.setText(sharedPreferences.getString("emailaddress","").length()>1?sharedPreferences.getString("emailaddress",""):"");

        phonenumber.setText(sharedPreferences.getString("phonenumber","").length()>1?sharedPreferences.getString("phonenumber",""):"");

**/

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int id = v.getId();
        switch (id) {
            case R.id.imageView1:
                // Back button will navigate the user to previous screen.
                if (getContactKey != null) {
                    myIntent = new Intent(BuyAPolicy.this,
                            CustodianMainLanding.class);
                    startActivity(myIntent);
                } else {
                    myIntent = new Intent(BuyAPolicy.this,
                            InformationCenterMenuScreen.class);
                    startActivity(myIntent);
                }

                break;

            case R.id.home:
                // Home button will navigate the user directly to home screen.
                myIntent = new Intent(BuyAPolicy.this, CustodianMainLanding.class);
                startActivity(myIntent);
                break;

            case R.id.continue_imag_policy:





                int age;
                String datePickerValue = this.dateofbirth.getMonth() +"-" +this.dateofbirth.getDayOfMonth() +  "-" + this.dateofbirth.getYear();
                age = getAge(this.dateofbirth.getYear(),this.dateofbirth.getMonth(), this.dateofbirth.getDayOfMonth());

                sharedPreferences  = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putString("BasicsKey", "Basics");
                editor.putString("title_spinner",title_spinner.getSelectedItem().toString());
                Log.e("title_spinner",sharedPreferences.getString("title_spinner",""));


                if(otherOccupation.getVisibility() == View.VISIBLE && otherOccupation.getText().length() > 1){
                    editor.putString("occupation_spinner",otherOccupation.getText().toString());

                }else{
                    editor.putString("occupation_spinner",occupation_spinner.getSelectedItem().toString());
                }
                Log.e("occupation_spinner",sharedPreferences.getString("occupation_spinner",""));
                editor.putString("surname",surname.getText().toString());
                Log.e("surname",sharedPreferences.getString("surname",""));
                editor.putString("othername",othername.getText().toString());
                Log.e("othername",sharedPreferences.getString("othername",""));
                editor.putString("address",address.getText().toString());
                Log.e("address",sharedPreferences.getString("address",""));
                editor.putString("username",username.getText().toString());
                Log.e("username",sharedPreferences.getString("username",""));
              //  editor.putString("password",password.getText().toString());
               // Log.e("password",sharedPreferences.getString("password",""));
                editor.putString("dateofbirth",datePickerValue.toString());
                Log.e("dateofbirth",sharedPreferences.getString("dateofbirth",""));
                Log.e("age",String.valueOf(age));
                editor.putString("emailaddress",emailaddress.getText().toString());
                Log.e("emailaddress",sharedPreferences.getString("emailaddress",""));
                editor.putString("phonenumber",phonenumber.getText().toString());
                Log.e("phonenumber",sharedPreferences.getString("phonenumber",""));

                editor.commit();


                if (emailaddress.getText().toString().equalsIgnoreCase("")) {
                    Showalerts(Alerts.ENTER_EMAIL);
                } else if (!CheckEmail(emailaddress.getText().toString())) {
                    Showalerts(Alerts.INVALID_EMAIL);
                } else if (phonenumber.getText().toString().equalsIgnoreCase("")) {
                    Showalerts(Alerts.ENTER_PHONE);
                } else if (!countLengthoOfMobileNumber(phonenumber.getText().toString())) {
                    Showalerts(Alerts.INVALID_PHONE);
                } else if (!CheckPhone(phonenumber.getText().toString())) {
                    Showalerts(Alerts.INVALID_PHONE);
                }
                else if (surname.getText().toString().equalsIgnoreCase("")) {
                    Showalerts(Alerts.ENTER_FIRSTNAME);
                }
                else if (othername.getText().toString().equalsIgnoreCase("")) {
                    Showalerts(Alerts.ENTER_OTHERNAMES);
                }
                else if (occupation_spinner.getSelectedItem().toString().equalsIgnoreCase("")) {
                    Showalerts(Alerts.ENTER_OCCUPATION);
                }
                else if (title_spinner.getSelectedItem().toString().equalsIgnoreCase("")) {
                    Showalerts(Alerts.ENTER_TITLE);
                }
                else if (username.getText().toString().equalsIgnoreCase("")) {
                    Showalerts(Alerts.ENTER_USERNAME);
                }
                /*else if (password.getText().toString().equalsIgnoreCase("")) {
                    Showalerts(Alerts.ENTER_PASSWORD);
                }
                else if (password.getText().toString().length() < 6) {
                    Showalerts(Alerts.INVALID_PASSWORD);
                }
                */
                else if (address.getText().toString().equalsIgnoreCase("")) {
                    Showalerts(Alerts.ENTER_ADDRESS);
                }
                else if (age<18) {
                    Showalerts(Alerts.UNSUPORRTED_AGE);
                }
                else {
                    goToWebservice();
                }
                break;
        }
    }


    private boolean countLengthoOfMobileNumber(String number) {
        return (number.length() >= 11 && number.length() <=13)?true:false;
    }


    public int getAge(int year,int month, int day){


        String datetext = month+"-"+day+"-"+year; // Date in text
        try {
            Calendar birth = new GregorianCalendar();
            Calendar today = new GregorianCalendar();
            int age = 0;
            int factor = 0; //to correctly calculate age when birthday has not been yet celebrated
            Date birthDate = new SimpleDateFormat("MM-dd-yyyy").parse(datetext);
            Date currentDate = new Date(); //current date

            birth.setTime(birthDate);
            today.setTime(currentDate);

            // check if birthday has been celebrated this year
            if(today.get(Calendar.DAY_OF_YEAR) < birth.get(Calendar.DAY_OF_YEAR)) {
                factor = -1; //birthday not celebrated
            }
            age = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR) + factor;
            //System.out.println("AGE (years): "+age);
            System.out.println("age: "+age);

            return age;
        } catch (ParseException e) {
            System.out.println("Given date: "+datetext+ " not in expected format (Please enter a MM-DD-YYYY date)");
        }
        return 0;


    }



    private void goToWebservice() {
        // TODO Auto-generated method stub

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            try {

                sharedPreferences  = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();

                value = Boolean.valueOf("true");
                json = new JSONObject();

                json.put("active", value);
                json.put("leadType","INDIVIDUAL");
                json.put("description", "Started insurance quote from custodian andriod mobile application");
                json.put("label", othername.getText()  + " " + surname.getText());
                json.put("leadSource", "CUSTODIAN_DIRECT_MOBILE_ANDRIOD");
                json.put("organization", othername.getText()  + " " + surname.getText());

                if(otherOccupation.getVisibility() == View.VISIBLE && otherOccupation.getText().length() > 1){
                    json.put("profession",otherOccupation.getText().toString());

                }else{
                    //UFASHHB
                    json.put("profession",occupation_spinner.getSelectedItem().toString());
                }
                //json.put("profession", occupation_spinner.getSelectedItem().toString());
                json.put("emailAddress", CheckEmail(emailaddress.getText().toString())?emailaddress.getText().toString():"");
                json.put("mobilePhone", phonenumber.getText());
                json.put("dateOfBirth",sharedPreferences.getString("dateofbirth","N/A"));
                json.put("portalUserName",username.getText().toString());
                json.put("identifier",username.getText().toString());
              ///  json.put("portalPassword",password.getText().toString());
                json.put("firstName", othername.getText());
                json.put("lastName", surname.getText());
                json.put("primaryStreet", address.getText());
                json.put("title", title_spinner.getSelectedItem().toString());

                new LeadCaptureWebservice(WebserviceURLs.LEAD_CAPTURE, "",
                        BuyAPolicy.this, BuyAPolicy.this, true, json,
                        "Submitting...").execute();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else {
            mSplaHandler.sendEmptyMessage(3);
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
                BuyAPolicy.this);
        // Setting Dialog Title
        alertDialog.setTitle("Custodian");
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
                            myIntent = new Intent(BuyAPolicy.this,
                                    BuyAPolicyVehicledetails.class);
                            startActivity(myIntent);
                        } else {
                            myIntent = new Intent(BuyAPolicy.this,
                                    BuyAPolicyVehicledetails.class);
                            startActivity(myIntent);
                        }

                    }
                });
        alertDialog.show();
    }






    private String checkUsername(String username) {
        // TODO Auto-generated method stub
        SharedPreferences sharedPreferences = this
                .getSharedPreferences(MyPREFERENCES,
                        Context.MODE_PRIVATE);
        HttpClient httpClient;
        httpClient = getNewHttpClient();
        HttpGet httpGet = new HttpGet(WebserviceURLs.VERIFY_USERNAME  + username);
        UsernamePasswordCredentials credentials =
                new UsernamePasswordCredentials("custodian", "Welcome123");
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
     * Showalerts (String message) is used to show alerts.
     *
     * @param message message is the parameter that reflects the message to be shown
     *                to user when he fill any wrong data or leave fields blank.
     */
    private void Showalerts(String message) {
        // TODO Auto-generated method stub
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                BuyAPolicy.this);
        // Setting Dialog Title
        alertDialog.setTitle("Custodian");

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

    // Method to get the Email Validated
    protected boolean CheckEmail(String Subject) {
        // TODO Auto-generated method stub
        return LoginEmail_PATTERN.matcher(Subject).matches();
    }

    // Method to get the Phone Validated
    protected boolean CheckPhone(String Phone) {
        // TODO Auto-generated method stub
        return PHONE_NO_PATTERN.matcher(Phone).matches();
    }

    // json response parsed.
    @Override
    public void onResponse(String response) {
        // TODO Auto-generated method stub

        try {
            json = new JSONObject(response);
            if (json.has("error")) {
                String error = json.getString("error");
                Showalerts(error);
            } else {
                String status = json.optString("success");
                String id = json.optString("msgCode");
                if (status.equalsIgnoreCase("false")) {
                    mSplaHandler.sendEmptyMessage(1);
                } else if (status.equalsIgnoreCase("true")) {
                    sharedPreferences  = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putString("leadID", id);
                    editor.commit();
                    Log.e("leadID",sharedPreferences.getString("leadID",""));
                    mSplaHandler.sendEmptyMessage(2);

                }

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

}
