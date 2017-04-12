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
import org.w3c.dom.Text;

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
public class BuyAPolicyVehicledetailsVerify extends Activity implements OnClickListener,
        CustodianInterface {
    // Declaration of views.
    TextView disclaimer,amount_to_pay_value,chasis_number_value,engine_number_value,label,vehicle_no,vehicle_make,vehicle_use,premium,insurance_start_date,insurance_end_date,cover_period,cover_type,payment_plan,transaction_number;

    ImageButton mHome;
    Intent myIntent;
    Editor editor;
    ImageButton mback;
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


    public Editor getEditor() {
        return editor;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
    }


    public TextView getVehicle_make() {
        return vehicle_make;
    }

    public void setVehicle_make(TextView vehicle_make) {
        this.vehicle_make = vehicle_make;
    }

    public TextView getVehicle_use() {
        return vehicle_use;
    }

    public void setVehicle_use(TextView vehicle_use) {
        this.vehicle_use = vehicle_use;
    }

    public TextView getInsurance_start_date() {
        return insurance_start_date;
    }

    public void setInsurance_start_date(TextView insurance_start_date) {
        this.insurance_start_date = insurance_start_date;
    }

    public TextView getChasis_number_value() {
        return chasis_number_value;
    }

    public void setChasis_number_value(TextView chasis_number_value) {
        this.chasis_number_value = chasis_number_value;
    }

    public TextView getEngine_number_value() {
        return engine_number_value;
    }

    public void setEngine_number_value(TextView engine_number_value) {
        this.engine_number_value = engine_number_value;
    }

    public TextView getPremium() {
        return premium;
    }

    public void setPremium(TextView premium) {
        this.premium = premium;
    }

    public TextView getInsurance_end_date() {
        return insurance_end_date;
    }

    public void setInsurance_end_date(TextView insurance_end_date) {
        this.insurance_end_date = insurance_end_date;
    }

    public TextView getCover_period() {
        return cover_period;
    }

    public void setCover_period(TextView cover_period) {
        this.cover_period = cover_period;
    }

    public TextView getPayment_plan() {
        return payment_plan;
    }

    public void setPayment_plan(TextView payment_plan) {
        this.payment_plan = payment_plan;
    }

    public TextView getTransaction_number() {
        return transaction_number;
    }

    public void setTransaction_number(TextView transaction_number) {
        this.transaction_number = transaction_number;
    }

    public TextView getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(TextView disclaimer) {
        this.disclaimer = disclaimer;
    }

    public TextView getCover_type() {
        return cover_type;
    }

    public void setCover_type(TextView cover_type) {
        this.cover_type = cover_type;
    }

    public TextView getLabel() {
        return label;
    }

    public void setLabel(TextView label) {
        this.label = label;
    }

    public TextView getVehicle_no() {
        return vehicle_no;
    }

    public void setVehicle_no(TextView vehicle_no) {
        this.vehicle_no = vehicle_no;
    }

    public TextView getAmount_to_pay_value() {
        return amount_to_pay_value;
    }

    public void setAmount_to_pay_value(TextView amount_to_pay_value) {
        this.amount_to_pay_value = amount_to_pay_value;
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custodian_policy_create_vehicle_details_verify);

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
        this.setLabel((TextView) findViewById(R.id.label_value));
        this.setVehicle_no((TextView) findViewById(R.id.vehicle_number_value));
        this.setVehicle_make((TextView) findViewById(R.id.vehicle_make_values));
        this.setVehicle_use((TextView) findViewById(R.id.vehicle_use_value));
        this.setPremium((TextView) findViewById(R.id.premium_value));
        this.setInsurance_start_date((TextView) findViewById(R.id.insurance_startdate_value));
        this.setInsurance_end_date((TextView) findViewById(R.id.insurance_enddate_value));
        this.setCover_period((TextView) findViewById(R.id.coverperiod_value));
        this.setCover_type((TextView) findViewById(R.id.covertype_value));
        this.setPayment_plan((TextView) findViewById(R.id.payment_plan_value));
        this.setChasis_number_value((TextView) findViewById(R.id.chasis_number_value));
        this.setEngine_number_value((TextView) findViewById(R.id.engine_number_value));
        this.setAmount_to_pay_value((TextView) findViewById(R.id.amount_to_pay_value));
        this.setDisclaimer((TextView) findViewById(R.id.disclaimer));




        //Store the value in the shared settings.
        this.setSharedPreferences(this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE));
        this.setEditor(sharedPreferences.edit());

        this.getLabel().setText(this.getSharedPreferences().getString("title_spinner", "")+" "+this.getSharedPreferences().getString("othername", "")+" "+this.getSharedPreferences().getString("surname", ""));
        this.getVehicle_no().setText(this.getSharedPreferences().getString("reg_no", ""));
        this.getVehicle_make().setText(this.getSharedPreferences().getString("vehicle_make", ""));
        this.getVehicle_use().setText("PRIVATE");
        this.getPremium().setText(this.getSharedPreferences().getString("premium", ""));
        this.getInsurance_start_date().setText(this.getSharedPreferences().getString("insurance_startdate", ""));
        this.getInsurance_end_date().setText(this.getSharedPreferences().getString("insurance_enddate", ""));
        this.getCover_period().setText(this.getSharedPreferences().getString("cover_period", ""));
        this.getCover_type().setText(this.getSharedPreferences().getString("cover_spinner", ""));
        this.getPayment_plan().setText(this.getSharedPreferences().getString("paymentOptionLabel", ""));
        this.getChasis_number_value().setText(this.getSharedPreferences().getString("chassis_number", ""));
        this.getEngine_number_value().setText(this.getSharedPreferences().getString("engine_number", ""));
        this.getAmount_to_pay_value().setText(this.getSharedPreferences().getString("amount_to_pay", ""));
        if(this.getSharedPreferences().getString("paymentOptionLabel", "").contains("Annually")){
            this.getDisclaimer().setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int id = v.getId();
        switch (id) {
            case R.id.imageView1:
                // Back button will navigate the user to previous screen.
                if (getContactKey != null) {
                    myIntent = new Intent(BuyAPolicyVehicledetailsVerify.this,
                            BuyAPolicyVehicledetails.class);
                    startActivity(myIntent);
                } else {
                    myIntent = new Intent(BuyAPolicyVehicledetailsVerify.this,
                            InformationCenterMenuScreen.class);
                    startActivity(myIntent);
                }

                break;

            case R.id.home:
                // Home button will navigate the user directly to home screen.
                myIntent = new Intent(BuyAPolicyVehicledetailsVerify.this,CustodianMainLanding.class);
                startActivity(myIntent);
                break;

            case R.id.continue_imag_vehicleDetails:

                myIntent = new Intent(BuyAPolicyVehicledetailsVerify.this,
                        BuyAPolicyPayment.class);
                startActivity(myIntent);

                break;
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
                BuyAPolicyVehicledetailsVerify.this);
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
                BuyAPolicyVehicledetailsVerify.this);
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
