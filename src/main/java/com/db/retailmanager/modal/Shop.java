package com.db.retailmanager.modal;

public class Shop {
	private String shopName;
	private ShopAddress shopAddress;
	private double shopLongitude;
	private double shopLatitude;
	private int version;

	@Override
	public String toString() {
		return "Shop [shopName=" + shopName + ", shopAddress=" + shopAddress + ", shopLongitude="
				+ shopLongitude + ", shopLatitude=" + shopLatitude + ", version=" + version + "]";
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public ShopAddress getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(ShopAddress shopAddress) {
		this.shopAddress = shopAddress;
	}

	public double getShopLongitude() {
		return shopLongitude;
	}

	public void setShopLongitude(double shopLongitude) {
		this.shopLongitude = shopLongitude;
	}

	public double getShopLatitude() {
		return shopLatitude;
	}

	public void setShopLatitude(double shopLatitude) {
		this.shopLatitude = shopLatitude;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

}
