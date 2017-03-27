package com.custodian.ModelView;

import java.io.Serializable;

public class CustomerDetails implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
String Username;
String Email;
String FirstName;
String LastName;
String Mobile;
String Address1;
String Address2;
String City;
String State;
String OwnerId;
public String getUsername() {
	return Username;
}
public void setUsername(String username) {
	Username = username;
}
public String getEmail() {
	return Email;
}
public void setEmail(String email) {
	Email = email;
}
public String getFirstName() {
	return FirstName;
}
public void setFirstName(String firstName) {
	FirstName = firstName;
}
public String getLastName() {
	return LastName;
}
public void setLastName(String lastName) {
	LastName = lastName;
}
public String getMobile() {
	return Mobile;
}
public void setMobile(String mobile) {
	Mobile = mobile;
}
public String getAddress1() {
	return Address1;
}
public void setAddress1(String address1) {
	Address1 = address1;
}
public String getAddress2() {
	return Address2;
}
public void setAddress2(String address2) {
	Address2 = address2;
}
public String getCity() {
	return City;
}
public void setCity(String city) {
	City = city;
}
public String getState() {
	return State;
}
public void setState(String state) {
	State = state;
}
public String getOwnerId() {
	return OwnerId;
}
public void setOwnerId(String ownerId) {
	OwnerId = ownerId;
}

}
