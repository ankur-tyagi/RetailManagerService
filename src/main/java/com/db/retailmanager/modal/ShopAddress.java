package com.db.retailmanager.modal;

import java.math.BigInteger;

public class ShopAddress {
	private BigInteger number;
	private String postCode;
	private ShopGeoLocation shopGeoLocation;

	public ShopAddress() {
	}

	public ShopAddress(BigInteger number, String postCode) {
		this.number = number;
		this.postCode = postCode;
	}

	@Override
	public String toString() {
		return "ShopAddress [number=" + number + ", postCode=" + postCode + "]";
	}

	public BigInteger getNumber() {
		return number;
	}

	public void setNumber(BigInteger number) {
		this.number = number;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public ShopGeoLocation getShopGeoLocation() {
		return shopGeoLocation;
	}

	public void setShopGeoLocation(ShopGeoLocation shopGeoLocation) {
		this.shopGeoLocation = shopGeoLocation;
	}

}
