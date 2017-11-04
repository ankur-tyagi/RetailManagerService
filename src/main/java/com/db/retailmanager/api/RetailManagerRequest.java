package com.db.retailmanager.api;

import com.db.retailmanager.modal.ShopAddress;

public class RetailManagerRequest {
	private String shopName;
	private ShopAddress shopAddress;

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

	@Override
	public String toString() {
		return "RetailManagerServiceRequest [shopName=" + shopName + ", shopAddress=" + shopAddress + "]";
	}

}
