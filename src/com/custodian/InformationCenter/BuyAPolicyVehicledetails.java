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
import android.widget.TextView;

import com.custodian.Alerts;
import com.custodian.CONSTANT.CONSTANTS;
import com.custodian.CustodianHomeScreenMain;
import com.custodian.CustodianMainLanding;
import com.custodian.CustodianWebservices.ContactUsWebservice;
import com.custodian.CustodianWebservices.CustodianInterface;
import com.custodian.CustodianWebservices.LeadCaptureWebservice;
import com.custodian.CustodianWebservices.MySSLSocketFactory;
import com.custodian.R;
import com.custodian.URLS.WebserviceURLs;
import com.custodian.utils.StringDateUtils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyStore;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;


/**
 * Contact us class is used to submit the contact us form using webservice that
 * takes the id of the user as a json paramater.
 */
public class BuyAPolicyVehicledetails extends Activity implements OnClickListener,
        CustodianInterface {
    // Declaration of views.
    EditText reg_no, vehicle_make, chassis_number, vehicle_value;

    DatePicker insurance_startdate;

    TextView insurance_enddate;

    Spinner coverPeriod;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Spinner getCover_spinner() {
        return cover_spinner;
    }


    public void setCover_spinner(Spinner cover_spinner) {
        this.cover_spinner = cover_spinner;
    }

    public TextView getInsurance_enddate() {
        return insurance_enddate;
    }

    public void setInsurance_enddate(TextView insurance_enddate) {
        this.insurance_enddate = insurance_enddate;
    }

    public DatePicker getInsurance_startdate() {
        return insurance_startdate;
    }

    public void setInsurance_startdate(DatePicker insurance_startdate) {
        this.insurance_startdate = insurance_startdate;
    }

    public EditText getVehicle_value() {
        return vehicle_value;
    }

    public void setVehicle_value(EditText vehicle_value) {
        this.vehicle_value = vehicle_value;
    }

    public EditText getChassis_number() {
        return chassis_number;
    }

    public void setChassis_number(EditText chassis_number) {
        this.chassis_number = chassis_number;
    }

    public EditText getVehicle_make() {
        return vehicle_make;
    }

    public void setVehicle_make(EditText vehicle_make) {
        this.vehicle_make = vehicle_make;
    }

    public EditText getReg_no() {
        return reg_no;
    }

    public void setReg_no(EditText reg_no) {
        this.reg_no = reg_no;
    }


    public Spinner getCoverPeriod() {
        return coverPeriod;
    }

    public void setCoverPeriod(Spinner coverPeriod) {
        this.coverPeriod = coverPeriod;
    }

    Spinner cover_spinner;
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

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }


    public Editor getEditor() {
        return editor;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    private static final Pattern LoginEmail_PATTERN = Pattern
            .compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    private static final Pattern PHONE_NO_PATTERN = Pattern.compile("^[0-9+]*$");

    // OnBackPressed is used to disable the device back button as back button is
    // used in top bar for navigation.

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custodian_policy_create_vehicle_details);

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

        mback = (ImageButton) findViewById(R.id.imageView1);
        mback.setOnClickListener(this);
        mHome = (ImageButton) findViewById(R.id.home);
        mHome.setOnClickListener(this);
        btnContinue = (ImageView) findViewById(R.id.continue_imag_vehicleDetails);
        btnContinue.setOnClickListener(this);
        Typeface face = Typeface.createFromAsset(getAssets(),
                CONSTANTS.FONT_NAME);
        this.setReg_no((EditText) findViewById(R.id.reg_no));
        this.getReg_no().setTypeface(face);
        this.setVehicle_make((EditText) findViewById(R.id.vehicle_make));
        this.getVehicle_make().setTypeface(face);
        this.setChassis_number((EditText) findViewById(R.id.chassis_number));
        this.getChassis_number().setTypeface(face);
        this.setVehicle_value((EditText) findViewById(R.id.vehicle_value));
        this.getVehicle_value().setTypeface(face);
        this.setInsurance_startdate((DatePicker) findViewById(R.id.insurance_startdate));
        this.setInsurance_enddate((TextView) findViewById(R.id.insuranceEnddate));
        this.getInsurance_enddate().setTypeface(face);
        this.setCover_spinner((Spinner) findViewById(R.id.cover_spinner));
        this.setCoverPeriod((Spinner) findViewById(R.id.coverPeriod_spinner));


        this.getCover_spinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {


                SimpleDateFormat dt = new SimpleDateFormat("dd/mm/yyyy");

                String datePickerValue = getInsurance_startdate().getDayOfMonth() + "/" + getInsurance_startdate().getMonth() + "/" + getInsurance_startdate().getYear();


                Date date = null;
                try {
                    date = dt.parse(datePickerValue);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                if (getCover_spinner().getSelectedItem() == "1 year") {

                    getInsurance_enddate().setText(dt.format(StringDateUtils.addYearsToDate(date, 1)).toString());

                } else if (getCover_spinner().getSelectedItem().toString() == "6 months") {
                    getInsurance_enddate().setText(dt.format(StringDateUtils.addMonthsToDate(date, 6)).toString());

                } else if (getCover_spinner().getSelectedItem().toString() == "3 months") {

                    getInsurance_enddate().setText(dt.format(StringDateUtils.addMonthsToDate(date, 3)).toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int id = v.getId();
        switch (id) {
            case R.id.imageView1:
                // Back button will navigate the user to previous screen.
                if (getContactKey != null) {
                    myIntent = new Intent(BuyAPolicyVehicledetails.this,
                            BuyAPolicy.class);
                    startActivity(myIntent);
                } else {
                    myIntent = new Intent(BuyAPolicyVehicledetails.this,
                            InformationCenterMenuScreen.class);
                    startActivity(myIntent);
                }

                break;

            case R.id.home:
                // Home button will navigate the user directly to home screen.
                myIntent = new Intent(BuyAPolicyVehicledetails.this, CustodianHomeScreenMain.class);
                startActivity(myIntent);
                break;

            case R.id.continue_imag_vehicleDetails:


                String datePickerValue = this.getInsurance_startdate().getDayOfMonth() + "/" + this.getInsurance_startdate().getMonth() + "/" + this.getInsurance_startdate().getYear();


                this.setSharedPreferences(this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE));
                this.setEditor(sharedPreferences.edit());
                this.getEditor().putString("BasicsKey", "Basics");


                this.getEditor().putString("reg_no", this.getReg_no().getText().toString());

                this.getEditor().putString("vehicle_make", this.getVehicle_make().getText().toString());

                this.getEditor().putString("chassis_number", this.getChassis_number().getText().toString());

                this.getEditor().putString("vehicle_value", this.getVehicle_value().getText().toString());

                this.getEditor().putString("insurance_startdate", datePickerValue);

                this.getEditor().putString("insurance_enddate", this.getInsurance_enddate().getText().toString());

                this.getEditor().putString("cover_spinner", this.getCover_spinner().getSelectedItem().toString());

                //todo please port over the logic for calculating the premium from the php script into this and save it
                double yearlyPremium = 0.03d * Double.valueOf(this.getVehicle_value().getText().toString());  //hard coded to 3% of the value - todo review this
                double policyPremium = 0d;

                //todo finish up this logic - remember that you need to look at the policy type e.g third party = 5K flat
                if (getCover_spinner().getSelectedItem() == "1 year") {
                    policyPremium = yearlyPremium;
                } else if (getCover_spinner().getSelectedItem().toString() == "6 months") {
                    policyPremium = yearlyPremium / 2;
                } else if (getCover_spinner().getSelectedItem().toString() == "3 months") {
                    policyPremium = yearlyPremium / 4;
                }

                //Store the value in the shared settings.
                this.getEditor().putString("premium", String.valueOf(policyPremium));

                this.getEditor().commit();

                // editor.putString("title_spinner",title_spinner.getSelectedItem().toString());
                Log.e("title_spinner", this.getSharedPreferences().getString("title_spinner", ""));
                //editor.putString("occupation_spinner",occupation_spinner.getSelectedItem().toString());
                Log.e("occupation_spinner", this.getSharedPreferences().getString("occupation_spinner", ""));
                // editor.putString("surname",surname.getText().toString());
                Log.e("surname", this.getSharedPreferences().getString("surname", ""));
                // editor.putString("othername",othername.getText().toString());
                Log.e("othername", this.getSharedPreferences().getString("othername", ""));
                // editor.putString("address",address.getText().toString());
                Log.e("address", this.getSharedPreferences().getString("address", ""));
                //  editor.putString("username",username.getText().toString());
                Log.e("username", this.getSharedPreferences().getString("username", ""));
                //  editor.putString("password",password.getText().toString());
                Log.e("password", this.getSharedPreferences().getString("password", ""));
                // editor.putString("dateofbirth",dateofbirth.getText().toString());
                Log.e("dateofbirth", this.getSharedPreferences().getString("dateofbirth", ""));
                Log.e("cover_spinner", this.getSharedPreferences().getString("cover_spinner", ""));
                Log.e("insurance_enddate", this.getSharedPreferences().getString("insurance_enddate", ""));
                Log.e("insurance_startdate", this.getSharedPreferences().getString("insurance_startdate", ""));
                Log.e("vehicle_value", this.getSharedPreferences().getString("vehicle_value", ""));
                Log.e("chassis_number", this.getSharedPreferences().getString("chassis_number", ""));
                Log.e("vehicle_make", this.getSharedPreferences().getString("vehicle_make", ""));
                Log.e("reg_no", this.getSharedPreferences().getString("reg_no", ""));

                this.goToWebservice();

                myIntent = new Intent(BuyAPolicyVehicledetails.this,
                        BuyAPolicyPayment.class);
                startActivity(myIntent);


                break;
        }
    }

    private void goToWebservice() {
        // TODO Auto-generated method stub

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            try {
                SharedPreferences sharedPreferences = this
                        .getSharedPreferences(MyPREFERENCES,
                                Context.MODE_PRIVATE);
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                Date date = new Date();
                value = Boolean.valueOf("true");

                String endDateLabel = null;

                String endDate = this.getInsurance_enddate().getText().toString();

                if (endDate == "1 Year") {
                    endDateLabel = "Pay_Anually";

                } else if (endDate == "6 Months") {
                    endDateLabel = "Bi_Anually";

                } else if (endDate == "3 Months") {
                    endDateLabel = "Pay_Quaterly";

                }
                SimpleDateFormat dt = new SimpleDateFormat("dd/mm/yyyy");
                Date dates = null;
                try {
                    dates = dt.parse(dateFormat.format(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                json = new JSONObject();
                json.put("active", value);
                json.put("description", this.getCover_spinner().getSelectedItem().toString() + " policy for  - " + this.getSharedPreferences().getString("othername", "") + " " + this.getSharedPreferences().getString("surname", "").toString());
                json.put("label", this.getCover_spinner().getSelectedItem() + " policy for  - " + this.getSharedPreferences().getString("othername", "").toString() + " " + this.getSharedPreferences().getString("surname", "").toString());
                json.put("lead", Long.parseLong(this.getSharedPreferences().getString("leadID", ""), 10));
                json.put("quoteDate", dateFormat.format(date));
                json.put("requestStartDttm", dateFormat.format(date));
                json.put("startDate", this.getSharedPreferences().getString("insurance_startdate", ""));
                json.put("requestEndDttm", StringDateUtils.addYearsToDate(dates, 2));
                json.put("validUntil", StringDateUtils.addYearsToDate(dates, 2));
                json.put("paymentTermLabel", endDateLabel);
                json.put("paymentTerm", 1200);
                json.put("endDate", StringDateUtils.addYearsToDate(dates, 2));
                //json.put("endDate", this.getInsurance_enddate().getText());
                json.put("quoteType", this.getCover_spinner().getSelectedItem().toString().toUpperCase());
                json.put("quoteReference", this.getVehicle_make().getText() + "private");
                json.put("quoteAmount", this.getVehicle_value().getText());
                json.put("category", "SERVICE");
                json.put("status", "NEW");
                json.put("origin", "MOBILE");
                json.put("currency", "NGN");
                json.put("origin", "MOBILE");
                json.put("salesOffice", 1600);
                json.put("salesChannel", 1200);

                //Clear the cached quote id and make the call
                editor = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).edit();
                editor.remove("leadQuoteID");
                editor.remove("leadQuoteName");
                editor.remove("textMessage");
                editor.commit();

                new LeadCaptureWebservice(WebserviceURLs.LEAD_QUOTE_CAPTURE, "",
                        BuyAPolicyVehicledetails.this, BuyAPolicyVehicledetails.this, true, json,
                        "Submitting...").execute();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else {
            mSplaHandler.sendEmptyMessage(3);
        }
    }


    private void callPolicyLineBlock() {
        // TODO Auto-generated method stub

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            SimpleDateFormat dt = new SimpleDateFormat("dd/mm/yyyy");
            Date dates = null;
            try {
                dates = dt.parse(dateFormat.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                value = Boolean.valueOf("true");
                json = new JSONObject();
                json.put("active", value);
                json.put("leadQuote", Long.parseLong(this.getSharedPreferences(MyPREFERENCES,
                        Context.MODE_PRIVATE).getString("leadQuoteID", ""), 10));
                json.put("lead", Long.parseLong(this.getSharedPreferences(MyPREFERENCES,
                        Context.MODE_PRIVATE).getString("leadID", ""), 10));
                json.put("label", this.getVehicle_make().getText().toString() + " -    # " + this.getChassis_number().getText().toString());
                json.put("modelYear", "NA");
                json.put("vehicleMileage", "NA");
                json.put("vehicleMake", this.getVehicle_make().getText().toString());
                json.put("vehicleColor", "NA");
                json.put("vehicleModel", "NA");
                json.put("vehicleNo", this.getReg_no().getText().toString());
                json.put("chassisNo", this.getChassis_number().getText().toString());
                json.put("engineNo", "NA");
                json.put("vehicleUse", "PRIVATE");
                json.put("description", "NA");
                json.put("vehicleValue", this.getVehicle_value().getText().toString());
                json.put("premium", this.getVehicle_value().getText().toString());
                json.put("validUntil", StringDateUtils.addYearsToDate(dates, 2));
                json.put("requestedItem", 2000);
                json.put("origin", "MOBILE");
                json.put("status", "NEW");
                json.put("salesOffice", 1600);
                json.put("salesChannel", 1200);
                new LeadCaptureWebservice(WebserviceURLs.LEAD_QUOTE_LINE, "",
                        BuyAPolicyVehicledetails.this, BuyAPolicyVehicledetails.this, true, json,
                        "Submitting...").execute();


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else {
            mSplaHandler.sendEmptyMessage(3);
        }
    }


    private String getLeadquote(String leadQuoteId) {
        // TODO Auto-generated method stub
        SharedPreferences sharedPreferences = this
                .getSharedPreferences(MyPREFERENCES,
                        Context.MODE_PRIVATE);
        HttpClient httpClient;
        httpClient = getNewHttpClient();

        HttpGet httpGet = new HttpGet(WebserviceURLs.GET_LEAD_QUOTE_WITH_ID + "/" + leadQuoteId);
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
                BuyAPolicyVehicledetails.this);
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
                BuyAPolicyVehicledetails.this);
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
                String loginError = json.getString("error");

                Showalerts(loginError);
            } else {
                String status = json.optString("success");
                String id = json.optString("msgCode");

                if (status.equalsIgnoreCase("false")) {
                    mSplaHandler.sendEmptyMessage(1);
                } else if (status.equalsIgnoreCase("true")) {

                    //First determine what you are responding to ie leadquote or a lead quote line.
                    String leadQuoteId = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).getString("leadQuoteID", null);

                    //If nothing is found then we are dealing with a lead quote response
                    editor = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).edit();
                    if (leadQuoteId == null) {
                        editor.putString("leadQuoteID", id);

                        //Now call back to get the details of the generated lead quote eg name etc.
                        String res = this.getLeadquote(id);
                        JSONObject jsonObject = new JSONObject(res);

                        //Save the details from that response
                        editor.putString("leadQuoteName", jsonObject.optString("name"));
                        editor.putString("textMessage", "Complete your order by paying N" + this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).getString("vehicle_value", "")
                                + " into any of the bank accounts below. Please remember to provide your reference no " + jsonObject.optString("name") + " when making the payment.");
                        editor.commit();
                        Log.e("leadQuoteID", this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).getString("leadQuoteID", ""));
                        Log.e("leadQuoteName", this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).getString("leadQuoteName", ""));

                        callPolicyLineBlock(); // continue with the policy line after the lead quote line has succeeded
                    } else {
                        // we are now dealing with a quote line reponse
                        editor.putString("policyLineID", id);
                        editor.commit();
                        Log.e("policyLineID", this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).getString("policyLineID", ""));
                    }

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
