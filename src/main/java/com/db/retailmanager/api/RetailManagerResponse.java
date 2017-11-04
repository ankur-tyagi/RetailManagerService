package com.db.retailmanager.api;

import com.db.retailmanager.modal.ShopAddress;

public class RetailManagerResponse {
	private String shopName;
	private ShopAddress shopAddress;
	private double shopLongitude;
	private double shopLatitude;

	/**
	 * @return the shopName
	 */
	public String getShopName() {
		return shopName;
	}

	/**
	 * @param shopName
	 *            the shopName to set
	 */
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	/**
	 * @return the shopAddress
	 */
	public ShopAddress getShopAddress() {
		return shopAddress;
	}

	/**
	 * @param shopAddress
	 *            the shopAddress to set
	 */
	public void setShopAddress(ShopAddress shopAddress) {
		this.shopAddress = shopAddress;
	}

	/**
	 * @return the shopLongitude
	 */
	public double getShopLongitude() {
		return shopLongitude;
	}

	/**
	 * @param shopLongitude
	 *            the shopLongitude to set
	 */
	public void setShopLongitude(double shopLongitude) {
		this.shopLongitude = shopLongitude;
	}

	/**
	 * @return the shopLatitude
	 */
	public double getShopLatitude() {
		return shopLatitude;
	}

	/**
	 * @param shopLatitude
	 *            the shopLatitude to set
	 */
	public void setShopLatitude(double shopLatitude) {
		this.shopLatitude = shopLatitude;
	}

}
