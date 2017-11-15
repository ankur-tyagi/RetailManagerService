package com.db.retailmanager.repository;

import java.util.List;

import com.db.retailmanager.modal.Shop;

public interface RetailManagerRepository {

	public Shop getShopByName(String shopName);
	public List<Shop> getShop(String postalCode);

	public void addShop(Shop shop);
}
