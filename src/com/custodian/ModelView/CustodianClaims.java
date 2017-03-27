package com.custodian.ModelView;

import java.io.Serializable;
import java.util.Calendar;
/**
 * Custodian claims bean class of Claim Centre module.
 *
 */
public class CustodianClaims implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
String date;
String adjuster;
String description;
String claimNo;
String DateOfIncident;
String vehicleDamage;
String whatHappened;
String vehicleNo;


public String getWhatHappened() {
	return whatHappened;
}
public void setWhatHappened(String whatHappened) {
	this.whatHappened = whatHappened;
}
public String getVehicleNo() {
	return vehicleNo;
}
public void setVehicleNo(String vehicleNo) {
	this.vehicleNo = vehicleNo;
}
public String getVehicleDamage() {
	return vehicleDamage;
}
public void setVehicleDamage(String vehicleDamage) {
	this.vehicleDamage = vehicleDamage;
}


public String getDateOfIncident() {
	return DateOfIncident;
}
public void setDateOfIncident(String dateOfIncident) {
	DateOfIncident = dateOfIncident;
}
String strTimeOfIncident ;
public String getStrTimeOfIncident() {
	return strTimeOfIncident;
}
public void setStrTimeOfIncident(String strTimeOfIncident) {
	this.strTimeOfIncident = strTimeOfIncident;
}
public String getStrState() {
	return strState;
}
public void setStrState(String strState) {
	this.strState = strState;
}
public String getStrCity() {
	return strCity;
}
public void setStrCity(String strCity) {
	this.strCity = strCity;
}
public String getStrChassis() {
	return strChassis;
}
public void setStrChassis(String strChassis) {
	this.strChassis = strChassis;
}
public String getStrContactEmail() {
	return strContactEmail;
}
public void setStrContactEmail(String strContactEmail) {
	this.strContactEmail = strContactEmail;
}
public String getStrContactPhone() {
	return strContactPhone;
}
public void setStrContactPhone(String strContactPhone) {
	this.strContactPhone = strContactPhone;
}
public String getStrdidAirbags() {
	return strdidAirbags;
}
public void setStrdidAirbags(String strdidAirbags) {
	this.strdidAirbags = strdidAirbags;
}
public String getStrEngineNo() {
	return strEngineNo;
}
public void setStrEngineNo(String strEngineNo) {
	this.strEngineNo = strEngineNo;
}
public String getStrVehicleUse() {
	return strVehicleUse;
}
public void setStrVehicleUse(String strVehicleUse) {
	this.strVehicleUse = strVehicleUse;
}
public String getStrVehicleColor() {
	return strVehicleColor;
}
public void setStrVehicleColor(String strVehicleColor) {
	this.strVehicleColor = strVehicleColor;
}
public String getStrVehicleNo() {
	return strVehicleNo;
}
public void setStrVehicleNo(String strVehicleNo) {
	this.strVehicleNo = strVehicleNo;
}
public String getStrWasDamged() {
	return strWasDamged;
}
public void setStrWasDamged(String strWasDamged) {
	this.strWasDamged = strWasDamged;
}
public String getStrVehicleTowed() {
	return strVehicleTowed;
}
public void setStrVehicleTowed(String strVehicleTowed) {
	this.strVehicleTowed = strVehicleTowed;
}
public String getWhoWasDriving() {
	return whoWasDriving;
}
public void setWhoWasDriving(String whoWasDriving) {
	this.whoWasDriving = whoWasDriving;
}
String strState; 
String strCity ;
String strChassis ;
String strContactEmail ;
String strContactPhone;
String strdidAirbags ;
String strEngineNo ;
String strVehicleUse ;
String strVehicleColor ;
String strVehicleNo ;
String strWasDamged ;
String strVehicleTowed ;
String whoWasDriving;

public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public String getAdjuster() {
	return adjuster;
}
public void setAdjuster(String adjuster) {
	this.adjuster = adjuster;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public String getClaimNo() {
	return claimNo;
}
public void setClaimNo(String claimNo) {
	this.claimNo = claimNo;
}


}
