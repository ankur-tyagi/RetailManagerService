package com.db.retailmanager.repository;

import java.util.List;

import com.db.retailmanager.modal.Shop;

public interface RetailManagerRepository {

	public static final int INITIAL_VERSION = 0;

	public Shop getShopByName(String shopName);
	public List<Shop> getShop(String postalCode);

	public Shop addShop(Shop shop);
}
