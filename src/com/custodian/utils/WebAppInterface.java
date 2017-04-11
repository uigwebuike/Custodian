package com.custodian.utils;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.custodian.InformationCenter.BuyAPolicyInterswitch;

public class WebAppInterface {
    public Context mContext;

    WebAppInterface(Context c) {
        mContext = c;
    }

    @JavascriptInterface
    public void openActivity(String activityName) {
        String packageName = "com.custodian.InformationCenter";
        try {
            Class activityClass = Class.forName(packageName + "." + activityName);
            //mContext.startActivity(new Intent(BuyAPolicyInterswitch.this, activityClass));
        } catch(Exception ex) {
            Toast.makeText(mContext, "invalid activity name: " + activityName, Toast.LENGTH_SHORT).show();
        }
    }

}