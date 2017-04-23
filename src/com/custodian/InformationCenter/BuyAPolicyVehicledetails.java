package com.custodian.InformationCenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.icu.util.GregorianCalendar;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Pattern;


/**
 * Contact us class is used to submit the contact us form using webservice that
 * takes the id of the user as a json paramater.
 */
public class BuyAPolicyVehicledetails extends Activity implements OnClickListener,
        CustodianInterface {


    double premium,amountTopay;
    String insurance_enddate_display,insurance_startdate_display;


    public String getInsurance_enddate_display() {
        return insurance_enddate_display;
    }

    public void setInsurance_enddate_display(String insurance_enddate_display) {
        this.insurance_enddate_display = insurance_enddate_display;
    }

    public String getInsurance_startdate_display() {
        return insurance_startdate_display;
    }

    public void setInsurance_startdate_display(String insurance_startdate_display) {
        this.insurance_startdate_display = insurance_startdate_display;
    }

    public class CoverTypeEventLisner implements AdapterView.OnItemSelectedListener {

        public CoverTypeEventLisner(){
            /*if(getVehicle_value().getText().toString() !="" && getVehicle_value().getText().toString().length() > 1) {
                setPremium(0.03d * Double.valueOf(getVehicle_value().getText().toString()));
                getEditor().putString("premium", String.valueOf(getPremium()));
                getEditor().putString("amount_to_pay", String.valueOf(getAmountTopay()));
                Log.e("premium",String.valueOf(getPremium()));
                Log.e("amount to pay",String.valueOf(getPremium()));
            }*/
        }

        public void onItemSelected(AdapterView<?> parent,
                                   View view, int pos, long id) {
            if(getVehicle_value().getText().toString() != "" && getVehicle_value().getText().toString().length() > 1){
                setPremium(0.03d * Double.valueOf(getVehicle_value().getText().toString()));


                setSharedPreferences(getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE));
                setEditor(sharedPreferences.edit());

                    if(parent.getItemAtPosition(pos).toString().contains("Comprehensive")){
                        if (getPaymentOption_spinner().getSelectedItem().toString().equalsIgnoreCase("Annually")) {
                            setAmountTopay(0.03d * Double.valueOf(getVehicle_value().getText().toString()));  //hard coded to 3% of the value - todo review this

                        } else if (getPaymentOption_spinner().getSelectedItem().toString().equalsIgnoreCase("Bi-Annually")) {
                            setAmountTopay((0.03d * Double.valueOf(getVehicle_value().getText().toString()))/1.95);  //hard coded to 3% of the value - todo review this


                        } else if (getPaymentOption_spinner().getSelectedItem().toString().equalsIgnoreCase("Quarterly")) {
                            setAmountTopay((0.03d * Double.valueOf(getVehicle_value().getText().toString()))/3.75);  //hard coded to 3% of the value - todo review this
                        }
                    }else if(parent.getItemAtPosition(pos).toString().contains("Third Party")){
                        setPremium(5000d);
                        setAmountTopay(5000d);
                    }
                getEditor().putString("premium", String.valueOf(getPremium()));
                getEditor().putString("amount_to_pay", String.valueOf(getAmountTopay()));

                Log.e("premium",String.valueOf(getPremium()));
                Log.e("amount to pay",String.valueOf(getAmountTopay()));
            }
        }

        public void onNothingSelected(AdapterView parent) {
            setSharedPreferences(getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE));
            setEditor(sharedPreferences.edit());
            if(getVehicle_value().getText().toString() !="" && getVehicle_value().getText().toString().length() > 1) {
                setPremium(0.03d * Double.valueOf(getVehicle_value().getText().toString()));
                getEditor().putString("premium", String.valueOf(getPremium()));
                getEditor().putString("amount_to_pay", String.valueOf(getPremium()));
                Log.e("premium",String.valueOf(getPremium()));
                Log.e("amount to pay",String.valueOf(getPremium()));
            }

        }
    }

    public class ShowHideOthersTextView implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent,
                                   View view, int pos, long id) {
            String selected =  parent.getItemAtPosition(pos).toString();
            if(selected.contains("Others")){
                getOthers_vehicle().setVisibility(View.VISIBLE);

            }else{
                getOthers_vehicle().setVisibility(View.GONE);

            }
      }

        public void onNothingSelected(AdapterView parent) {

        }
    }

    public class CalculateAmountTopay implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent,
                                   View view, int pos, long id) {

            setSharedPreferences(getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE));
            setEditor(sharedPreferences.edit());
            SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");

            String datePickerValue = (getInsurance_startdate().getMonth()+1)   + "/" +  getInsurance_startdate().getDayOfMonth() + "/" + getInsurance_startdate().getYear();


            Date date = null;
            try {
                date = dt.parse(datePickerValue);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Log.e("start Date", datePickerValue);
            int month,year;
            String item =  parent.getItemAtPosition(pos).toString();
            Log.e("Item-selected",item);

            if(item.contains("Annually")) {
                year = getInsurance_startdate().getYear() + 1;

                setInsurance_enddate((getInsurance_startdate().getMonth()+1)  + "/" +  getInsurance_startdate().getDayOfMonth() + "/" + year);
                setInsurance_enddate_display( getInsurance_startdate().getDayOfMonth() + "/" +(getInsurance_startdate().getMonth()+1)  + "/" +  year);
                setCoverperiod("1 Year");

                Log.e("EndDate", getInsurance_enddate());
                    /*Toast.makeText(parent.getContext(),
                            "On Item Select : \n" + parent.getItemAtPosition(pos).toString(),
                            Toast.LENGTH_LONG).show();*/
                Log.e("EndDate1year", getInsurance_enddate() + "    " + (getInsurance_startdate().getYear() + 1));


            }
            if(item.contains("Bi-Annually")){
                if((getInsurance_startdate().getMonth()+1) + 6 > 12){
                    year =  getInsurance_startdate().getYear() + 1;
                    month = ((getInsurance_startdate().getMonth()+1) + 6) - 12;

                }else{
                    month = (getInsurance_startdate().getMonth()+1) + 6;
                    year =getInsurance_startdate().getYear();
                }
                setCoverperiod("6 Months");
                setInsurance_enddate(month   + "/" +getInsurance_startdate().getDayOfMonth()+ "/" + year);
                setInsurance_enddate_display(getInsurance_startdate().getDayOfMonth()+ "/" +month   + "/" + year);
                    /*Toast.makeText(parent.getContext(),
                            "On Item Select : \n" + parent.getItemAtPosition(pos).toString(),
                            Toast.LENGTH_LONG).show();*/
                Log.e("EndDate", getInsurance_enddate());
                Log.e("EndDate6month", getInsurance_enddate()+"    "+(getInsurance_startdate().getYear() + 1));
            }

            if(item.contains("Quarterly")){
                if((getInsurance_startdate().getMonth()+1) + 3 > 12){
                    year =  getInsurance_startdate().getYear() + 1;
                    month = ((getInsurance_startdate().getMonth()+1) + 3) - 12;

                }else{
                    month = (getInsurance_startdate().getMonth()+1) + 3;
                    year =getInsurance_startdate().getYear();
                }
                setCoverperiod("3 Months");
                setInsurance_enddate(month + "/" + getInsurance_startdate().getDayOfMonth()+ "/" + year);
                setInsurance_enddate_display(getInsurance_startdate().getDayOfMonth()+ "/" +month   + "/" + year);
                   /* Toast.makeText(parent.getContext(),
                            "On Item Select : \n" + parent.getItemAtPosition(pos).toString(),
                            Toast.LENGTH_LONG).show();*/
                Log.e("EndDate", getInsurance_enddate());
                Log.e("EndDate3Months", getInsurance_enddate()+"    "+(getInsurance_startdate().getYear() + 1));
            }

            if(item.length()<1){
                setInsurance_enddate( (getInsurance_startdate().getMonth()+1) + "/" +      getInsurance_startdate().getDayOfMonth()  + "/" + (getInsurance_startdate().getYear() + 1));
                Log.e("EndDate", getInsurance_enddate()+"    "+(getInsurance_startdate().getYear() + 1));
                   /* Toast.makeText(parent.getContext(),
                            "On Item Select : \n" + parent.getItemAtPosition(pos).toString(),
                            Toast.LENGTH_LONG).show();*/
            }
            Log.e("Cover Period", "Index changed");


           /* Toast.makeText(parent.getContext(), "The planet is " +
                    parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();*/

            String selected =  parent.getItemAtPosition(pos).toString();
            if(getCover_spinner().getSelectedItem().toString().contains("Third Party")){
                 setAmountTopay(5000d);
                 setPremium(5000d);
            }else{
                if(selected.equalsIgnoreCase("Annually")){
                    //do nothing
                    setPremium(getAmountTopay());
                }else if(selected.equalsIgnoreCase("Bi-Annually")) {
                    setAmountTopay(getAmountTopay() / 1.95);
                    setPremium(getAmountTopay() / 1.95);
              }
                else if(selected.equalsIgnoreCase("Quarterly")){
                    setAmountTopay(getAmountTopay()/3.75);
                    setPremium(getAmountTopay()/3.75);
                }
            }

                Log.e("amount to pay",String.valueOf(getAmountTopay()));
                getEditor().putString("amount_to_pay", String.valueOf(getAmountTopay()));
                getEditor().putString("premium", String.valueOf(getPremium()));
                getEditor().commit();



        }
        public void onNothingSelected(AdapterView parent) {

        }
    }

    public EditText getOthers_vehicle() {
        return others_vehicle;
    }

    public void setOthers_vehicle(EditText others_vehicle) {
        this.others_vehicle = others_vehicle;
    }

    public EditText getEngine_number() {
        return engine_number;
    }

    public void setEngine_number(EditText engine_number) {
        this.engine_number = engine_number;
    }

    // Declaration of views.
    EditText reg_no, chassis_number, vehicle_value,engine_number,others_vehicle;

    DatePicker insurance_startdate;

    String insurance_enddate;

    Spinner /*coverPeriod,*/ cover, vehicle_make,paymentOption_spinner;


    public double getPremium() {
        return premium;
    }

    public void setPremium(double premium) {
        this.premium = premium;
    }

    public double getAmountTopay() {
        return amountTopay;
    }

    public void setAmountTopay(double amountTopay) {
        this.amountTopay = amountTopay;
    }

    public Spinner getCover_spinner() {
        return cover;
    }

    public void setCover_spinner(Spinner cover_spinner) {
        this.cover = cover_spinner;
    }


    public Spinner getPaymentOption_spinner() {
        return this.paymentOption_spinner;
    }

    public void setPaymentOption_spinner(Spinner paymentOption_spinner) {
        this.paymentOption_spinner = paymentOption_spinner;
    }

    public String getInsurance_enddate() {
        return insurance_enddate;
    }

    public void setInsurance_enddate(String insurance_enddate) {
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

    public Spinner getVehicle_make() {
        return vehicle_make;
    }

    public void setVehicle_make(Spinner vehicle_make) {
        this.vehicle_make = vehicle_make;
    }

    public EditText getReg_no() {
        return reg_no;
    }

    public void setReg_no(EditText reg_no) {
        this.reg_no = reg_no;
    }

/*
    public Spinner getCoverPeriod() {
        return coverPeriod;
    }

    public void setCoverPeriod(Spinner coverPeriod) {
        this.coverPeriod = coverPeriod;
    }
*/

    boolean value;

    ImageButton mHome;
    Intent myIntent;
    JSONObject json;
    Editor editor;
    ImageButton mback;
    ImageView btnContinue;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    Handler mSplaHandler = null;
    String getContactKey,coverperiod = "";


    public String getCoverperiod() {
        return coverperiod;
    }

    public void setCoverperiod(String coverperiod) {
        this.coverperiod = coverperiod;
    }

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

    private static final Pattern PHONE_NO_PATTERN = Pattern.compile("^\\\\+?[0-9. ()-]{11,13}$");

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
        this.setVehicle_make((Spinner) findViewById(R.id.vehicle_make));
        this.setChassis_number((EditText) findViewById(R.id.chassis_number));
        this.getChassis_number().setTypeface(face);
        this.setVehicle_value((EditText) findViewById(R.id.vehicle_value));
        this.getVehicle_value().setTypeface(face);
        this.setInsurance_startdate((DatePicker) findViewById(R.id.insurance_startdate));
        this.setCover_spinner((Spinner) findViewById(R.id.cover_spinner));
        this.setPaymentOption_spinner((Spinner) findViewById(R.id.paymentOption_spinner));
        this.setEngine_number((EditText) findViewById(R.id.engine_number));
        this.setOthers_vehicle((EditText) findViewById(R.id.others_vehicle));
        this.setSharedPreferences(this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE));

        this.getVehicle_value().setText(this.getSharedPreferences().getString("vehicle_value", "").length()>1?this.getSharedPreferences().getString("vehicle_value", ""):"");
        this.getEngine_number().setText(this.getSharedPreferences().getString("engine_number", "").length()>1?this.getSharedPreferences().getString("engine_number", ""):"");
        this.getVehicle_value().setText(this.getSharedPreferences().getString("vehicle_value", "").length()>1?this.getSharedPreferences().getString("vehicle_value", ""):"");
        this.getChassis_number().setText(this.getSharedPreferences().getString("chassis_number", "").length()>1?this.getSharedPreferences().getString("chassis_number", ""):"");
    this.getReg_no().setText(this.getSharedPreferences().getString("reg_no", "").length()>1?this.getSharedPreferences().getString("reg_no", ""):"");


        SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
        String datePickerValue = (getInsurance_startdate().getMonth()+1)   + "/" +  getInsurance_startdate().getDayOfMonth() + "/" + getInsurance_startdate().getYear();

        this.setInsurance_startdate_display(getInsurance_startdate().getDayOfMonth() + "/" +(getInsurance_startdate().getMonth()+1)   + "/" +   getInsurance_startdate().getYear());


        Date date = null;
        try {
            date = dt.parse(datePickerValue);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.getCover_spinner().setOnItemSelectedListener(new CoverTypeEventLisner());
        this.getVehicle_make().setOnItemSelectedListener(new ShowHideOthersTextView());
        this.getPaymentOption_spinner().setOnItemSelectedListener(new CalculateAmountTopay());


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
                            CustodianMainLanding.class);
                    startActivity(myIntent);
                }
                break;

            case R.id.home:
                // Home button will navigate the user directly to home screen.
                myIntent = new Intent(BuyAPolicyVehicledetails.this, CustodianMainLanding.class);
                startActivity(myIntent);
                break;

            case R.id.continue_imag_vehicleDetails:


                Long paymentOption = null;
                String datePickerValue = (this.getInsurance_startdate().getMonth() + 1) + "/" + this.getInsurance_startdate().getDayOfMonth()  + "/" + this.getInsurance_startdate().getYear();
                this.setInsurance_startdate_display(getInsurance_startdate().getDayOfMonth() + "/" +(getInsurance_startdate().getMonth()+1)   + "/" +   getInsurance_startdate().getYear());

                SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");


                Log.e("SelectedMonth",String.valueOf(this.getInsurance_startdate().getMonth()));

                //todo please port over the logic for calculating the premium from the php script into this and save it


                //Store the value in the shared settings.
                this.setSharedPreferences(this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE));
                this.setEditor(sharedPreferences.edit());
                this.getEditor().putString("BasicsKey", "Basics");


                this.getEditor().putString("reg_no", this.getReg_no().getText().toString());

                if(getOthers_vehicle().getVisibility() == View.VISIBLE && getOthers_vehicle().getText().length() > 1){
                    this.getEditor().putString("vehicle_make", this.getOthers_vehicle().getText().toString());
                }else{
                    this.getEditor().putString("vehicle_make", this.getVehicle_make().getSelectedItem().toString());
                }


                this.getEditor().putString("chassis_number", this.getChassis_number().getText().toString());

                this.getEditor().putString("engine_number", this.getEngine_number().getText().toString());

                this.getEditor().putString("vehicle_value", this.getVehicle_value().getText().toString());

                this.getEditor().putString("insurance_startdate", datePickerValue);
                this.getEditor().putString("insurance_startdate_display", getInsurance_startdate_display());

               this.getEditor().putString("insurance_enddate", this.getInsurance_enddate().toString());

               this.getEditor().putString("insurance_enddate_display", this.getInsurance_enddate_display());


                this.getEditor().putString("cover_spinner", this.getCover_spinner().getSelectedItem().toString().toUpperCase());

                this.getEditor().putString("cover_period", this.getCoverperiod().toString());


                this.getEditor().putString("paymentOptionLabel", this.getPaymentOption_spinner().getSelectedItem().toString());
                this.getEditor().putString("amount_to_pay", Double.toString(amountTopay));
                this.getEditor().putString("premium", String.valueOf(this.getPremium()));

                if(this.getPaymentOption_spinner().getSelectedItem().toString().equalsIgnoreCase("Annually")){
                    paymentOption =  1001l;
                    Log.e("Payment-option", String.valueOf(paymentOption));
                }else if(this.getPaymentOption_spinner().getSelectedItem().toString().equalsIgnoreCase("Bi-Annually")){
                    //paymentOption = 1000l;
                    paymentOption = 1001l;
                    Log.e("Payment-option", String.valueOf(paymentOption));
                }else if(this.getPaymentOption_spinner().getSelectedItem().toString().equalsIgnoreCase("Quarterly")){
                    //paymentOption = 1003l;
                    paymentOption = 1001l;
                    Log.e("Payment-option", String.valueOf(paymentOption));
                }else{
                    paymentOption = 1001l;
                    Log.e("Payment-option", String.valueOf(paymentOption));
                }

                this.getEditor().putLong("paymentOption", paymentOption);
                this.getEditor().commit();
                Log.e("title_spinner", this.getSharedPreferences().getString("title_spinner", ""));
                Log.e("occupation_spinner", this.getSharedPreferences().getString("occupation_spinner", ""));
                Log.e("surname", this.getSharedPreferences().getString("surname", ""));
                Log.e("othername", this.getSharedPreferences().getString("othername", ""));
                Log.e("address", this.getSharedPreferences().getString("address", ""));
                Log.e("username", this.getSharedPreferences().getString("username", ""));
                Log.e("password", this.getSharedPreferences().getString("password", ""));
                Log.e("dateofbirth", this.getSharedPreferences().getString("dateofbirth", ""));
                Log.e("cover_spinner", this.getSharedPreferences().getString("cover_spinner", ""));
                Log.e("insurance_enddate", this.getSharedPreferences().getString("insurance_enddate", ""));
                Log.e("insurance_startdate", this.getSharedPreferences().getString("insurance_startdate", ""));
                Log.e("vehicle_value", this.getSharedPreferences().getString("vehicle_value", ""));
                Log.e("chassis_number", this.getSharedPreferences().getString("chassis_number", ""));
                Log.e("engine_number", this.getSharedPreferences().getString("engine_number", ""));
                Log.e("vehicle_make", this.getSharedPreferences().getString("vehicle_make", ""));
                Log.e("reg_no", this.getSharedPreferences().getString("reg_no", ""));
                Log.e("premium", this.getSharedPreferences().getString("premium", ""));
                Log.e("amount_to_pay", this.getSharedPreferences().getString("amount_to_pay", ""));
                Log.e("payment_Option_Label", this.getSharedPreferences().getString("paymentOptionLabel", ""));
                Log.e("payment_Option", String.valueOf(this.getSharedPreferences().getLong("paymentOption", 0L)));

                Date convertToDate = null;
                try {
                    convertToDate = dt.parse(datePickerValue);
                    String newDateString = dt.format(convertToDate);
                    Log.e("insuranceDate",newDateString);
                    Log.e("insuranceDateDate",convertToDate.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (getCover_spinner().getSelectedItem().toString().equalsIgnoreCase("")) {
                    Showalerts(Alerts.ENTER_COVER);
                }
                else if (getVehicle_make().getSelectedItem().toString().equalsIgnoreCase("")) {
                    Showalerts(Alerts.ENTER_VEHICLE_MAKE);
                }

                else if (getVehicle_make().getSelectedItem().toString().contains("Others") && getOthers_vehicle().getText().toString().equalsIgnoreCase("")) {
                    Showalerts(Alerts.ENTER_VEHICLE_MAKE);
                }
                else if(getChassis_number().getText().toString().equalsIgnoreCase("")){
                    Showalerts(Alerts.ENTER_CHASSIS_NUMBER);
                }
                else if(getEngine_number().getText().toString().equalsIgnoreCase("")){
                    Showalerts(Alerts.ENTER_ENGINE_NUMBER);
                }
                else if(getReg_no().getText().toString().equalsIgnoreCase("")){
                    Showalerts(Alerts.ENTER_VEHICLE_REGISTATION_NUMBER);
                }
                else if(getVehicle_value().getText().toString().equalsIgnoreCase("")){
                    Showalerts(Alerts.ENTER_VEHICLE_VALUE);
                }
                else if(Double.valueOf(getVehicle_value().getText().toString()) < 1500000d){
                    Showalerts(Alerts.INVALID_VEHICLE_VALUE);
                }
                else if(this.isBackDated(convertToDate)){
                    Showalerts(Alerts.INVALID_INSURANCE_START_DATE);
                }
                /*else if(getCoverPeriod().getSelectedItem().toString().equalsIgnoreCase("")){
                    Showalerts(Alerts.ENTER_PAMENT_PERIOD);
                }*/
                else if(getPaymentOption_spinner().getSelectedItem().toString().equalsIgnoreCase("")){
                    Showalerts(Alerts.ENTER_PAMENT_OPTION);
                }
                //todo validate insurance date not to be futer dated
                else {
                    this.goToWebservice();
                    myIntent = new Intent(BuyAPolicyVehicledetails.this,
                            BuyAPolicyVehicledetailsVerify.class);
                    startActivity(myIntent);
                }
                break;

        }
    }

    private boolean isBackDated(Date startDate) {
        Date today = StringDateUtils.getTodaysDate();
        return !(startDate != null && /*startDate.after(today) ||*/ startDate.equals(today));
    }


    private void goToWebservice() {
        // TODO Auto-generated method stub

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            try {

                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                Date date = new Date();
                value = Boolean.valueOf("true");


                json = new JSONObject();
                json.put("active", value);
                json.put("description", this.getCover_spinner().getSelectedItem().toString() + " policy for  - " + this.getSharedPreferences().getString("othername", "") + " " + this.getSharedPreferences().getString("surname", "").toString());
                json.put("label", this.getCover_spinner().getSelectedItem() + " policy for  - " + this.getSharedPreferences().getString("othername", "").toString() + " " + this.getSharedPreferences().getString("surname", "").toString());
                json.put("lead", Long.parseLong(this.getSharedPreferences().getString("leadID", ""), 10));
                json.put("quoteDate", dateFormat.format(date));
                json.put("requestStartDttm", dateFormat.format(date));
                json.put("startDate", this.getSharedPreferences().getString("insurance_startdate", ""));
                json.put("requestEndDttm", getInsurance_enddate());
                json.put("validUntil", getInsurance_enddate());
                json.put("paymentTermLabel", this.getSharedPreferences().getString("paymentOptionLabel", ""));
                json.put("paymentTerm", this.getSharedPreferences().getLong("paymentOption", 0l));
                json.put("endDate", getInsurance_enddate());
                json.put("quoteType", this.getCover_spinner().getSelectedItem().toString().toUpperCase());
                json.put("quoteReference", this.getVehicle_make().getSelectedItem() + " private");
                json.put("quoteAmount", this.getSharedPreferences().getString("premium", ""));
                json.put("category", "SERVICE");
                json.put("status", "NEW");
                json.put("origin", "MOBILE");
                json.put("currency", "NGN");
                json.put("origin", "MOBILE");
                json.put("salesOffice", 1600);
                json.put("salesChannel", 1200);

                Log.e("JSONMAP:",json.toString());

                //Clear the cached quote id and make the call

                new LeadCaptureWebservice(WebserviceURLs.LEAD_QUOTE_CAPTURE, "",
                        BuyAPolicyVehicledetails.this, BuyAPolicyVehicledetails.this, true, json,
                        "Submitting...").execute();

                editor = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).edit();
                editor.remove("leadQuoteID");
                editor.remove("leadQuoteName");
                // editor.remove("textMessage");
                editor.commit();


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
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date date = new Date();
            SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
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
                json.put("label", this.getVehicle_make().getSelectedItem().toString().toUpperCase() + " -    # " + this.getChassis_number().getText().toString());
                json.put("modelYear", "NA");
                json.put("vehicleMileage", "NA");
                json.put("vehicleMake", this.getVehicle_make().getSelectedItem().toString().toUpperCase());
                json.put("vehicleColor", "NA");
                json.put("vehicleModel", "NA");
                json.put("vehicleNo", this.getReg_no().getText().toString());
                json.put("chassisNo", this.getChassis_number().getText().toString());
                json.put("engineNo", this.getEngine_number().getText().toString());
                json.put("vehicleUse", "PRIVATE");
                json.put("description", "NA");
                json.put("vehicleValue", this.getVehicle_value().getText().toString());
                json.put("premium", this.getSharedPreferences().getString("premium", ""));
                json.put("validUntil", getInsurance_enddate()); //todo calculate the correct end date
                json.put("requestedItem", 1200);
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
                        editor.putString("textMessage", "Complete your order by paying N" + this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).getString("premium", "")
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
