package com.db.retailmanager.repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.db.retailmanager.modal.Shop;
import com.db.retailmanager.modal.ShopAddress;

@Repository
public class RetailManagerRepositoryInMemory implements RetailManagerRepository {

	private static final Logger logger = LoggerFactory.getLogger(RetailManagerRepositoryInMemory.class);
	private Map<String, Shop> shopList;

	public RetailManagerRepositoryInMemory() {
		this.shopList = new ConcurrentHashMap<String, Shop>();

		// dummy data
		Shop shop1 = new Shop();
		shop1.setShopName("Dummy Shop One");
		shop1.setShopAddress(new ShopAddress(BigInteger.valueOf(1), "600001"));
		shop1.setShopLatitude(13.0963045);
		shop1.setShopLongitude(80.2865916);
		addShop(shop1);
		Shop shop2 = new Shop();
		shop2.setShopName("Dummy Shop Two");
		shop2.setShopAddress(new ShopAddress(BigInteger.valueOf(1), "100001"));
		shop2.setShopLatitude(39.9077718);
		shop1.setShopLongitude(116.4254997);
		addShop(shop2);
	}

	@Override
	public void addShop(Shop newShop) {
		logger.info("RetailManagerRepositoryInMemory : adding " + newShop.toString());
		synchronized (shopList) {
			Shop oldShop = shopList.get(newShop.getShopName());
			if (oldShop == null) {
				// just add shop
				logger.info("Adding new shop");
				shopList.put(newShop.getShopName(), newShop);
			} else {
				// update shop
				logger.info("Updating existing shop");
				shopList.replace(oldShop.getShopName(), newShop);
			}
		}
	}

	@Override
	public List<Shop> getShop(String postalCode) {
		List<Shop> shops = new ArrayList<Shop>();

		if (postalCode == null || shopList.isEmpty()) {
			logger.warn("RetailManagerRepositoryInMemory : postal code is invalid or repository is empty");
			return shops;
		}

		for (Shop shop : shopList.values()) {
			if (postalCode.equals(shop.getShopAddress().getPostCode())) {
				logger.info("RetailManagerRepositoryInMemory : found " + shop.toString());
				shops.add(shop);
			}
		}
		return shops;
	}

	@Override
	public Shop getShopByName(String shopName) {
		return shopList.get(shopName);
	}

}
