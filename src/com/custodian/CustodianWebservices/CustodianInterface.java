package com.custodian.CustodianWebservices;

import android.view.View;

public interface CustodianInterface {


	void onResponse(String response);
	void onError();


	void onAlreadyExist(String response);
}
