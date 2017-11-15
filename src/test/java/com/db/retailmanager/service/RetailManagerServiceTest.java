package com.db.retailmanager.service;

import static org.mockito.Mockito.when;

import java.math.BigInteger;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.db.retailmanager.api.RetailManagerRequest;
import com.db.retailmanager.api.error.RetailManagerException;
import com.db.retailmanager.geocoding.GoogleGeoCodingAPI;
import com.db.retailmanager.modal.Shop;
import com.db.retailmanager.modal.ShopAddress;
import com.db.retailmanager.modal.ShopGeoLocation;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RetailManagerServiceTest {
    @Autowired
	private RetailManagerServiceImpl retailManagerService;

    @MockBean
    private GoogleGeoCodingAPI googleGeoCodingAPI;

    @Before
	public void setUp() {
	}

	@Test
	public void addShopTest() throws RetailManagerException {
    	RetailManagerRequest retailManagerRequest = new RetailManagerRequest();
    	retailManagerRequest.setShopName("Test Shop");
    	retailManagerRequest.setShopAddress(new ShopAddress(BigInteger.ONE, "123456"));

    	ShopGeoLocation shopGeoLocation = new ShopGeoLocation(1.2, 3.4);
		when(googleGeoCodingAPI.getShopGeoLocation(retailManagerRequest)).thenReturn(shopGeoLocation);

		Shop shop = retailManagerService.addShop(retailManagerRequest);
		Assert.assertNotNull(shop);
		Assert.assertEquals(retailManagerRequest.getShopName(), shop.getShopName());
	}

	@Test
	public void getShopTest() throws RetailManagerException {
		ShopGeoLocation shopGeoLocation = new ShopGeoLocation(39.9077718, 116.4254997);

		when(googleGeoCodingAPI.getPostalCode(shopGeoLocation)).thenReturn("100001");

		List<Shop> shops = retailManagerService.getShops(shopGeoLocation);
		Assert.assertNotNull(shops);
		Assert.assertEquals("Dummy Shop Two", shops.get(0).getShopName());
	}

	@Test
	public void getShopExceptionTest() throws RetailManagerException {
		ShopGeoLocation shopGeoLocation = new ShopGeoLocation(39.9077718, 116.4254997);

		when(googleGeoCodingAPI.getPostalCode(shopGeoLocation)).thenThrow(
				new RetailManagerException(RetailManagerException.RETURN_CODE_INVALID_REQUEST, "Test Exception"));

		List<Shop> shops = null;
		try {
			shops = retailManagerService.getShops(shopGeoLocation);
		} catch (RetailManagerException rme) {
			// got exception
			Assert.assertEquals(RetailManagerException.RETURN_CODE_INVALID_REQUEST, rme.getReturnCode());
		}
		Assert.assertNull(shops);
	}

	@Test
	public void addShopConcurrencyTest() throws InterruptedException, RetailManagerException {
		ExecutorService executor = Executors.newFixedThreadPool(10);

    	RetailManagerRequest retailManagerRequest = new RetailManagerRequest();
    	retailManagerRequest.setShopName("My Concurrent Shop");
    	retailManagerRequest.setShopAddress(new ShopAddress(BigInteger.ONE, "112233"));

    	ShopGeoLocation shopGeoLocation = new ShopGeoLocation(1.23, 4.56);
		when(googleGeoCodingAPI.getShopGeoLocation(retailManagerRequest)).thenReturn(shopGeoLocation);

		for (int i = 0; i < 10; i++) {
			executor.submit(new Runnable() {
				@Override
				public void run() {
						try {
							retailManagerService.addShop(retailManagerRequest);
						} catch (RetailManagerException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			});
		}
		executor.awaitTermination(5, TimeUnit.SECONDS);

		ShopGeoLocation shopGeoLocation2 = new ShopGeoLocation(39.9077718, 116.4254997);
		when(googleGeoCodingAPI.getPostalCode(shopGeoLocation2)).thenReturn("100001");
		List<Shop> shops = retailManagerService.getShops(shopGeoLocation2);
		Assert.assertNotNull(shops);
		Assert.assertEquals(1, shops.size());
	}
}
