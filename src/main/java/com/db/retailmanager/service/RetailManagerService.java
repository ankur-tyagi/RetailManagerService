package com.db.retailmanager.service;

import java.util.List;

import com.db.retailmanager.api.RetailManagerRequest;
import com.db.retailmanager.modal.Shop;
import com.db.retailmanager.modal.ShopGeoLocation;

public interface RetailManagerService {

	public Shop addShop(RetailManagerRequest request);

	public List<Shop> getShops(ShopGeoLocation shopGeoLocation);
}
