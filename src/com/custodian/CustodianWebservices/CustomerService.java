package com.custodian.CustodianWebservices;

public interface CustomerService {
	void onResponsePayment(String response);
	void onError();


	void onAlreadyExist(String response);

}
