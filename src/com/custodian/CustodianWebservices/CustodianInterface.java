package com.custodian.CustodianWebservices;

public interface CustodianInterface {
	void onResponse(String response);
	void onError();


	void onAlreadyExist(String response);
}
