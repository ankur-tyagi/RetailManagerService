package com.db.retailmanager.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.db.retailmanager.api.error.RetailManagerException;
import com.db.retailmanager.modal.Shop;
import com.db.retailmanager.modal.ShopGeoLocation;
import com.db.retailmanager.service.RetailManagerService;

@RestController
@RequestMapping(value="/")
public class RetailManagerController {
	@Autowired
	private RetailManagerService retailManagerService;

	@RequestMapping(value="/", method=RequestMethod.GET)
	public String getInfo() {
		return "This is Retail Manager Application";
	}

	@RequestMapping(value="api/shops", method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<RetailManagerResponse>> getShops(@RequestParam(value="customerLongitude") Float customerLongitude,
			@RequestParam(value="customerLatitude") Float customerLatitude) {
		List<Shop> shops = null;
		try {
			shops = retailManagerService.getShops(new ShopGeoLocation(customerLatitude, customerLongitude));
		} catch (RetailManagerException retailManagerxception) {
			return new ResponseEntity<Collection<RetailManagerResponse>>(new ArrayList<RetailManagerResponse>(), HttpStatus.BAD_REQUEST);
		} catch (Exception exception) {
			return new ResponseEntity<Collection<RetailManagerResponse>>(new ArrayList<RetailManagerResponse>(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		List<RetailManagerResponse> retailManagerResponses = new ArrayList<RetailManagerResponse>();
		if (!CollectionUtils.isEmpty(shops)) {
			for(Shop shop : shops) {
				RetailManagerResponse retailManagerResponse = mapResponse(shop);
				retailManagerResponses.add(retailManagerResponse);
			}
		}
		HttpStatus httpStatus = HttpStatus.OK;
		if (CollectionUtils.isEmpty(retailManagerResponses)) {
			httpStatus = HttpStatus.NO_CONTENT;
		}
		return new ResponseEntity<Collection<RetailManagerResponse>>(retailManagerResponses, httpStatus);
	}

	@RequestMapping(value="api/shops", method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RetailManagerResponse> addShop(@RequestBody RetailManagerRequest request) {
		Shop shopAdded = null;
		try {
			shopAdded = retailManagerService.addShop(request);
		} catch (RetailManagerException retailManagerxception) {
		    return new ResponseEntity<RetailManagerResponse>(new RetailManagerResponse(), HttpStatus.BAD_REQUEST);
		} catch (Exception exception) {
		    return new ResponseEntity<RetailManagerResponse>(new RetailManagerResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		RetailManagerResponse retailManagerResponse = mapResponse(shopAdded);
	    return new ResponseEntity<RetailManagerResponse>(retailManagerResponse, HttpStatus.CREATED);
	}

	private RetailManagerResponse mapResponse(Shop shopAdded) {
		RetailManagerResponse retailManagerResponse = new RetailManagerResponse();
		retailManagerResponse.setShopName(shopAdded.getShopName());
		retailManagerResponse.setShopAddress(shopAdded.getShopAddress());
		retailManagerResponse.setShopLatitude(shopAdded.getShopLatitude());
		retailManagerResponse.setShopLongitude(shopAdded.getShopLongitude());

		return retailManagerResponse;
	}

}
