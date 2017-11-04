package com.db.retailmanager.modal;

public class ShopGeoLocation {
	private double shopLatitude;
	private double shopLongitude;

	public ShopGeoLocation(double shopLatitude, double shopLongitude) {
		this.shopLatitude = shopLatitude;
		this.shopLongitude = shopLongitude;
	}

	@Override
	public String toString() {
		return "ShopGeoLocation [latitude=" + shopLatitude + ", longitude=" + shopLongitude + "]";
	}

	public double getShopLatitude() {
		return shopLatitude;
	}

	public void setShopLatitude(double shopLatitude) {
		this.shopLatitude = shopLatitude;
	}

	public double getShopLongitude() {
		return shopLongitude;
	}

	public void setShopLongitude(double shopLongitude) {
		this.shopLongitude = shopLongitude;
	}

}
