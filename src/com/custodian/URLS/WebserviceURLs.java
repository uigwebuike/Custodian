package com.custodian.URLS;

public class WebserviceURLs {
	
	//public static String BASE_URL ="https://custodian.zanibal.com/hermes/rest/api/v1/";
	public static String BASE_URL ="http://apps.demo.zanibal.com/hermes/rest/api/v1/";
	public static String  HOME_SCREEN = BASE_URL + "partner/customer/username/";
	public static String  FORGOT_ID =	BASE_URL + "partner/customer/email-address/";
	public static String LOGIN_SCREEN = BASE_URL +"security/login/customer?username=";
	public static String CONTACT_US = BASE_URL +"partner/case/create";
	public static String LEAD_CAPTURE = BASE_URL +"partner/lead/create";
	public static String LEAD_QUOTE_CAPTURE = BASE_URL +"partner/lead-quote/create";
	public static String LEAD_QUOTE_LINE = BASE_URL +"partner/lead-quote-line/create";
	public static String LEAD_OUOTE_TO_POLICY = BASE_URL +"partner/lead-quote/convert";
	public static String GET_LEAD_QUOTE_WITH_ID = BASE_URL +"/partner/lead-quote/id";
	public static String VIEW_POLICY_DETAIL = BASE_URL + "partner/policy/list/line?policyId=";
	public static String VIEW_POLICY_MAIN_PART1 =BASE_URL + "partner/customer/list/policy?customerId=";
	public static String VIEW_POLICY_MAIN_PART2_APPEND = "&orderBy=name&orderDr=ASC&start=0&limit=1000";
	public static String VIEW_MANAGE_CLAIMS_MAIN=BASE_URL +"partner/customer/list/claim?customerId=";
	public static String VIEW_MANAGE_CLAIMS_MAIN_APPEND = "&orderBy=name&orderDr=DESC&start=0&limit=1000";
	public static String CUSTOMER_SERVICE_PAYMENT= BASE_URL +"partner/customer/list/payment?customerId=";
	public static String CUSTOMER_SERVICE_INVOICE= BASE_URL + "partner/customer/list/invoice?customerId=";
	public static String SELECT_CITY = BASE_URL + "common/list/picklist/name/STATE_";
	public static String SELECT_STATE= BASE_URL + "common/list/picklist/name/COUNTRY_STATES";
	public static String ESTIMATE_REPAIR_FIND_LOCATIONS= BASE_URL +"partner/vendor/nld-search/location-type?type=AUTOSHOP&orderBy=label&orderDr=ASC&start=0&limit=1000&state=";
	public static String REPORT_CLAIM_VEHICLES_REPAIR_CENTRE =BASE_URL + "partner/vendor/nld-list/type?type=AUTOSHOP&orderBy=label&orderDr=ASC&start=0&limit=1000";
	public static String REPORT_CLAIM_VEHICLES_WHOWASINVOLVED =BASE_URL +"partner/customer/list/all-policy-lines?customerId=";
	public static String REPORT_CLAIM_VEHICLES_WHOWASINVOLVED_APPEND = "&orderBy=vehicleMake&orderDr=ASC&start=0&limit=100";
	public static String REPORT_CLAIM_REVIEW_CLAIM_ID = BASE_URL+"partner/claim/create";
	public static String REPORT_CLAIM_REVIEW_PIC_UPLOAD = BASE_URL +"common/note/create";
	public static String FORGOT_EMAIL_SEND = BASE_URL +"partner/email/send";
	public static String INTERSWITCH_REQUERY_URL = "https://webpay.interswitchng.com/paydirect/api/v1/gettransaction.json";
	//public static String INTERSWITCH_URL = "http://192.168.1.174:85/interswitch/index.php";
	public static String INTERSWITCH_URL = "http://www.custodiandirect.com/interswitch/index.php";

}
