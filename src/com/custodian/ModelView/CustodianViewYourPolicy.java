package com.custodian.ModelView;

import java.io.Serializable;
/**
 *CustodianViewYourPolicy bean class of View Policy.
 *
 */
public class CustodianViewYourPolicy implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String ID;
	String PolicyNumber;
	String Status;
	String StartDate;
	String EndDate;
	String PolicyType;
	String PolicyName;
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getPolicyName() {
		return PolicyName;
	}
	public void setPolicyName(String policyName) {
		PolicyName = policyName;
	}
	public String getPolicyType() {
		return PolicyType;
	}
	public void setPolicyType(String policyType) {
		PolicyType = policyType;
	}
	public String getVehicleType() {
		return VehicleType;
	}
	public void setVehicleType(String vehicleType) {
		VehicleType = vehicleType;
	}
	String VehicleType;
	public String getPolicyNumber() {
		return PolicyNumber;
	}
	public void setPolicyNumber(String policyNumber) {
		PolicyNumber = policyNumber;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getStartDate() {
		return StartDate;
	}
	public void setStartDate(String startDate) {
		StartDate = startDate;
	}
	public String getEndDate() {
		return EndDate;
	}
	public void setEndDate(String endDate) {
		EndDate = endDate;
	}
	
	

}
