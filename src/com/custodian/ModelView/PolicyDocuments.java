package com.custodian.ModelView;

import java.io.Serializable;
/**
 * Policy Documents bean class.
 *
 */
public class PolicyDocuments implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
String ID;
String PolicyNo;
String startDate;
String endDate;
String coverType;
public String getCoverType() {
	return coverType;
}
public void setCoverType(String coverType) {
	this.coverType = coverType;
}
public String getID() {
	return ID;
}
public void setID(String iD) {
	ID = iD;
}
public String getPolicyNo() {
	return PolicyNo;
}
public void setPolicyNo(String policyNo) {
	PolicyNo = policyNo;
}
public String getStartDate() {
	return startDate;
}
public void setStartDate(String startDate) {
	this.startDate = startDate;
}
public String getEndDate() {
	return endDate;
}
public void setEndDate(String endDate) {
	this.endDate = endDate;
}

}
