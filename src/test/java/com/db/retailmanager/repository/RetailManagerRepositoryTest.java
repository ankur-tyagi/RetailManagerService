package com.db.retailmanager.repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.db.retailmanager.modal.Shop;
import com.db.retailmanager.modal.ShopAddress;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RetailManagerRepositoryTest {

	@Autowired
	private RetailManagerRepository retailManagerRepositoryInMemory;

	@Before
	public void setUp() {
	}

	@Test
	public void getShopTest() {
		String postalCode = "100001";
		List<Shop> shops = retailManagerRepositoryInMemory.getShop(postalCode);
		Assert.assertNotNull(shops);
		Assert.assertEquals("More than 1 shop found", 1, shops.size());
	}

	@Test
	public void getInvalidShopTest() {
		String postalCode = "100002";
		List<Shop> shops = retailManagerRepositoryInMemory.getShop(postalCode);
		Assert.assertNotNull(shops);
		Assert.assertEquals("Shop found", 0, shops.size());
	}

	@Test
	public void getShopByNameTest() {
		String shopName = "Dummy Shop Two";
		Shop shop = retailManagerRepositoryInMemory.getShopByName(shopName);
		Assert.assertNotNull(shop);
		Assert.assertEquals("Shop found", shopName, shop.getShopName());
	}

	@Test
	public void getShopByNameInvalidTest() {
		String shopName = "Dummy Shop Three";
		Shop shop = retailManagerRepositoryInMemory.getShopByName(shopName);
		Assert.assertNull(shop);
	}

	@Test
	public void addShopTest() {
		Shop shop = new Shop();
		shop.setShopName("My Test Shop");
		shop.setShopAddress(new ShopAddress(new BigInteger("1"), "123456"));
		shop.setShopLatitude(1.2);
		shop.setShopLongitude(3.4);
		retailManagerRepositoryInMemory.addShop(shop);

		String postalCode = "123456";
		List<Shop> shops = retailManagerRepositoryInMemory.getShop(postalCode);
		Assert.assertNotNull(shops);
		Assert.assertEquals("More than 1 shop found", 1, shops.size());
		Assert.assertEquals(shop.getShopName(), shops.get(0).getShopName());
	}

	@Test
	public void updateShopTest() {
		Shop shop = new Shop();
		shop.setShopName("My Test Shop");
		shop.setShopAddress(new ShopAddress(new BigInteger("1"), "123456"));
		shop.setShopLatitude(1.2);
		shop.setShopLongitude(3.4);
		retailManagerRepositoryInMemory.addShop(shop);

		Shop shop2 = new Shop();
		shop2.setShopName("My Test Shop"); // same name
		String postalCode2 = "987654";
		shop2.setShopAddress(new ShopAddress(new BigInteger("1"), postalCode2));
		shop2.setShopLatitude(5.6);
		shop2.setShopLongitude(7.8);
		retailManagerRepositoryInMemory.addShop(shop2);

		List<Shop> shops = retailManagerRepositoryInMemory.getShop(postalCode2);
		Assert.assertNotNull(shops);
		Assert.assertEquals("More than 1 shop found", 1, shops.size());
		Assert.assertEquals(shop.getShopName(), shops.get(0).getShopName());
	}

	@Test
	public void addShopConcurrencyTest() throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(10);

		Shop shop1 = new Shop();
		shop1.setShopName("My Concurrent Shop");
		shop1.setShopAddress(new ShopAddress(new BigInteger("1"), "112233"));
		shop1.setShopLatitude(1.2);
		shop1.setShopLongitude(3.4);

		for (int i = 0; i < 10; i++) {
			executor.submit(new Runnable() {
				@Override
				public void run() {
						retailManagerRepositoryInMemory.addShop(shop1);
				}
			});
		}
		executor.awaitTermination(5, TimeUnit.SECONDS);

		Shop shop = retailManagerRepositoryInMemory.getShopByName("My Concurrent Shop");
		Assert.assertNotNull(shop);
		System.out.println("Shop Address in repository is : " + shop.getShopAddress());
	}
}
