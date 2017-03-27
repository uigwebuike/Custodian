package com.custodian.InformationCenter;

import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.custodian.Alerts;
import com.custodian.CustodianHomeScreenMain;
import com.custodian.CustodianMainLanding;
import com.custodian.R;
import com.custodian.CONSTANT.CONSTANTS;
import com.custodian.CustodianWebservices.ContactUsWebservice;
import com.custodian.CustodianWebservices.CustodianInterface;
import com.custodian.URLS.WebserviceURLs;

/**
 * Contact us class is used to submit the contact us form using webservice that
 * takes the id of the user as a json paramater.
 */
public class ContactUs extends Activity implements OnClickListener,
        CustodianInterface {
    // Declaration of views.
    TextView txt_subject, txt_desc, mHeading, txt_email, txt_phone;
    EditText edt_desc, edt_subject, edt_email, edt_phone_number;
    String flag;
    boolean value;

    ImageView btnContact, img_email, img_phone;
    ImageButton mHome;
    Intent myIntent;
    JSONObject json;
    ImageButton mback;
    public static final String MyPREFERENCES = "MyPrefs";
    Handler mSplaHandler = null;
    String getContactKey = "";
    String subject, desc, email, phone_number;
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
        setContentView(R.layout.custodian_contact_us);

        mSplaHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                if (msg.what == 1) {
                    Showalerts(Alerts.CONTACT_US_SUBMISSION_FAILURE);
                } else if (msg.what == 2) {
                    CheckStatus(Alerts.CONTACT_US_SUBMISSION_SUCCESS);
                } else if (msg.what == 3) {
                    Showalerts(Alerts.CHECK_INTERNET);

                }
            }
        };

        // initialisations and listeners
        // *******************************************************************************
        Typeface face = Typeface.createFromAsset(getAssets(),
                CONSTANTS.FONT_NAME);
        txt_subject = (TextView) findViewById(R.id.txt_subject);
        txt_subject.setTypeface(face);
        edt_subject = (EditText) findViewById(R.id.edt_subject);
        txt_desc = (TextView) findViewById(R.id.txt_desc);
        txt_desc.setTypeface(face);
        txt_subject.setTypeface(face);
        edt_desc = (EditText) findViewById(R.id.edt_desc);
        btnContact = (ImageView) findViewById(R.id.btnContactUs);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_phone_number = (EditText) findViewById(R.id.edt_phone_number);
        mHeading = (TextView) findViewById(R.id.title);
        mHeading.setTypeface(face);
        txt_email = (TextView) findViewById(R.id.txt_email);
        txt_email.setTypeface(face);
        txt_phone = (TextView) findViewById(R.id.txt_phone_number);
        txt_phone.setTypeface(face);
        img_email = (ImageView) findViewById(R.id.imag_star);
        img_phone = (ImageView) findViewById(R.id.imag_star_phone);
        mback = (ImageButton) findViewById(R.id.imageView1);
        mback.setOnClickListener(this);
        mHome = (ImageButton) findViewById(R.id.home);
        mHome.setOnClickListener(this);
        btnContact.setOnClickListener(this);
        txt_email.setVisibility(View.GONE);
        txt_phone.setVisibility(View.GONE);
        edt_email.setVisibility(View.GONE);
        edt_phone_number.setVisibility(View.GONE);
        img_email.setVisibility(View.GONE);
        img_phone.setVisibility(View.GONE);

        getContactKey = getIntent().getStringExtra("contactKey");
        if (getContactKey != null) {
            mHome.setVisibility(View.GONE);
            txt_email.setVisibility(View.VISIBLE);
            txt_phone.setVisibility(View.VISIBLE);
            edt_email.setVisibility(View.VISIBLE);
            edt_phone_number.setVisibility(View.VISIBLE);
            img_email.setVisibility(View.VISIBLE);
            img_phone.setVisibility(View.VISIBLE);

        } else {

        }
        // *****************************************************************************

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int id = v.getId();
        switch (id) {
            case R.id.imageView1:
                // Back button will navigate the user to previous screen.
                if (getContactKey != null) {
                    myIntent = new Intent(ContactUs.this,
                            CustodianMainLanding.class);
                    startActivity(myIntent);
                } else {
                    myIntent = new Intent(ContactUs.this,
                            InformationCenterMenuScreen.class);
                    startActivity(myIntent);
                }

                break;

            case R.id.home:
                // Home button will navigate the user directly to home screen.
                myIntent = new Intent(ContactUs.this, CustodianHomeScreenMain.class);
                startActivity(myIntent);
                break;

            case R.id.btnContactUs:
                /**
                 * Clicking on Contact us button gets the details from fields filled
                 * by the user,then validate them and hit the webservice to submit
                 * the contact us form. To hit webservice , id of the user will be
                 * used as a json parameter which is procured from the preferences.
                 */
                subject = edt_subject.getText().toString();
                desc = edt_desc.getText().toString();
                email = edt_email.getText().toString();
                phone_number = edt_phone_number.getText().toString();

                if (getContactKey != null) {
                    if (email.equalsIgnoreCase("")) {
                        Showalerts(Alerts.ENTER_EMAIL);
                    } else if (!CheckEmail(email)) {
                        Showalerts(Alerts.INVALID_EMAIL);
                    } else if (phone_number.equalsIgnoreCase("")) {
                        Showalerts(Alerts.ENTER_PHONE);
                    } else if (phone_number.length() > 16) {
                        Showalerts(Alerts.INVALID_PHONE);
                    } else if (!CheckPhone(phone_number)) {
                        Showalerts(Alerts.INVALID_PHONE);
                    } else if (subject.equalsIgnoreCase("")) {
                        Showalerts(Alerts.CONTACT_US_SUBJECT);
                    } else if (desc.equalsIgnoreCase("")) {
                        Showalerts(Alerts.CONTACT_US_DESCRIPTION);
                    } else {
                        goToWebservice();
                    }
                } else if (getContactKey == null) {
                    if (subject.equalsIgnoreCase("")) {
                        Showalerts(Alerts.CONTACT_US_SUBJECT);
                    } else if (desc.equalsIgnoreCase("")) {
                        Showalerts(Alerts.CONTACT_US_DESCRIPTION);
                    } else {
                        goToWebservice();
                    }
                }
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
                String AccountID = sharedPreferences.getString("id", "");

                // Send true as a json parameter by converting it from
                // string to boolean.
                value = Boolean.valueOf("true");
                json = new JSONObject();
                if (getContactKey != null) {
                    json.put("account", "3001");
                    json.put("mobilePhone", phone_number);
                    json.put("emailAddress", email);
                } else {
                    json.put("account", AccountID);
                }

                json.put("active", value);
                json.put("description", desc);
                json.put("label", subject);
                json.put("origin", "MOBILE");
                json.put("priority", "MEDIUM");
                json.put("reason", "FEEDBACK");
                json.put("status", "NEW");
                json.put("subject", subject);
                new ContactUsWebservice(WebserviceURLs.CONTACT_US, "",
                        ContactUs.this, ContactUs.this, true, json,
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
                ContactUs.this);
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
                            myIntent = new Intent(ContactUs.this,
                                    CustodianMainLanding.class);
                            startActivity(myIntent);
                        } else {
                            myIntent = new Intent(ContactUs.this,
                                    InformationCenterMenuScreen.class);
                            startActivity(myIntent);
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
                ContactUs.this);
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
                if (status.equalsIgnoreCase("false")) {
                    // Showalerts(Alerts.CONTACT_US_SUBMISSION_FAILURE);
                    mSplaHandler.sendEmptyMessage(1);
                } else if (status.equalsIgnoreCase("true")) {
                    // CheckStatus(Alerts.CONTACT_US_SUBMISSION_SUCCESS);
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
