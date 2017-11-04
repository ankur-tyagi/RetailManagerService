package com.db.retailmanager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.retailmanager.api.RetailManagerRequest;
import com.db.retailmanager.repository.RetailManagerRepository;
import com.db.retailmanager.modal.Shop;
import com.db.retailmanager.modal.ShopGeoLocation;

@Service
public class RetailManagerServiceImpl implements RetailManagerService {
	@Autowired
	RetailManagerRepository retailManagerRepository;

	@Override
	public Shop addShop(RetailManagerRequest request) {
		Shop shop = new Shop();
		shop.setShopName(request.getShopName());
		shop.setShopAddress(request.getShopAddress());
		ShopGeoLocation shopGeoLocation = new ShopGeoLocation(1.2, 3.4); // TODO: call Google Geocoding API to get actual coordinates
		if (shopGeoLocation != null) {
			shop.setShopLongitude(shopGeoLocation.getShopLongitude());
			shop.setShopLatitude(shopGeoLocation.getShopLatitude());
		}

		retailManagerRepository.addShop(shop);

		return shop;
	}

	@Override
	public List<Shop> getShops(ShopGeoLocation shopGeoLocation) {
		List<Shop> shops = new ArrayList<Shop>();

		String postalCode = "123456"; // TODO: call Google Geocoding API to get postal code
		if (postalCode != null && !postalCode.isEmpty()) {
			shops = retailManagerRepository.getShop(postalCode);
		}

		return shops;
	}

}
