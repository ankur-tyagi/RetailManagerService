package com.db.retailmanager.geocoding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.db.retailmanager.api.RetailManagerRequest;
import com.db.retailmanager.api.error.RetailManagerException;
import com.db.retailmanager.modal.ShopGeoLocation;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.ComponentFilter;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

@Component
public class GoogleGeoCodingAPI {

	@Value("${Google.GeoCodingAPI.Key}")
	private String GOOGLE_GEOCODING_KEY;

	private static final Logger logger = LoggerFactory.getLogger(GoogleGeoCodingAPI.class);

	public ShopGeoLocation getShopGeoLocation(RetailManagerRequest request) throws RetailManagerException {
		logger.debug("GOOGLE_GEOCODING_KEY = " + GOOGLE_GEOCODING_KEY);
		GeoApiContext context = new GeoApiContext().setApiKey(GOOGLE_GEOCODING_KEY);
		try {
			ComponentFilter componentFilter = ComponentFilter.postalCode(request.getShopAddress().getPostCode());
			GeocodingApiRequest geocodingApiRequest = GeocodingApi.newRequest(context)
					.address(request.getShopAddress().getNumber().toString() + " " + request.getShopName())
					.components(componentFilter);
			GeocodingResult[] geocodingResults = geocodingApiRequest.await();
			if (geocodingResults.length <= 0) {
				logger.info("GoogleGeoCodingAPI : No results found");
				return null;
			}

			for (GeocodingResult geocodingResult : geocodingResults) {
				for (AddressComponent address : geocodingResult.addressComponents) {
					logger.debug(
							"GoogleGeoCodingAPI : address " + address.longName + " (" + address.types.toString() + ")");
					for (AddressComponentType type : address.types) {
						if (type == AddressComponentType.POSTAL_CODE
								&& address.longName.equalsIgnoreCase(request.getShopAddress().getPostCode())) {
							return new ShopGeoLocation(geocodingResult.geometry.location.lat,
									geocodingResult.geometry.location.lng);
						}
					}
				}
			}
		} catch (Exception exception) {
			logger.error("GoogleGeoCodingAPI : Excpetion=" + exception.getMessage());
			throw new RetailManagerException(RetailManagerException.RETURN_CODE_INVALID_REQUEST, exception.getMessage());
		}
		return null;
	}

	public String getPostalCode(ShopGeoLocation shopGeoLocation) throws RetailManagerException {
		GeoApiContext context = new GeoApiContext().setApiKey(GOOGLE_GEOCODING_KEY);

		try {
			LatLng latLng = new LatLng(shopGeoLocation.getShopLatitude(), shopGeoLocation.getShopLongitude());
			GeocodingResult[] geocodingResults = GeocodingApi.reverseGeocode(context, latLng).await();
			if (geocodingResults == null || geocodingResults.length < 1) {
				logger.info("GoogleGeoCodingAPI : No results found");
				return null;
			}
			for (GeocodingResult geocodingResult : geocodingResults) {
				logger.debug("GoogleGeoCodingAPI : " + geocodingResult.formattedAddress);
				for (AddressComponent addressComponet : geocodingResult.addressComponents) {
					for (AddressComponentType type : addressComponet.types) {
						if (type == AddressComponentType.POSTAL_CODE) {
							return addressComponet.longName;
						}
					}
				}
			}
		} catch (Exception exception) {
			logger.error("GoogleGeoCodingAPI : Excpetion=" + exception.getMessage());
			throw new RetailManagerException(RetailManagerException.RETURN_CODE_INVALID_REQUEST, exception.getMessage());
		}
		return null;
	}
}
