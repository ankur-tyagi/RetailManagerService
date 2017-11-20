package com.db.retailmanager;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.db.retailmanager.api.RetailManagerRequest;
import com.db.retailmanager.api.RetailManagerResponse;
import com.db.retailmanager.geocoding.GoogleGeoCodingAPI;
import com.db.retailmanager.modal.ShopAddress;
import com.db.retailmanager.modal.ShopGeoLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class RetailmanagerserviceApplicationTests {

	private static final String API_URI = "/api/shops";

	private MockMvc mockMvc;

	@MockBean
	private GoogleGeoCodingAPI googleGeoCodingAPI;

	@Autowired
	protected WebApplicationContext webApplicationContext;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	private String mapRequestToJson(Object obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(obj);
	}

	private <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, clazz);
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void addShopTest() throws Exception {
		System.out.println("TEST - testAddShop - START");

		RetailManagerRequest retailManagerRequest = new RetailManagerRequest();
		retailManagerRequest.setShopName("chennai");
		ShopAddress shopAddress = new ShopAddress();
		shopAddress.setNumber(BigInteger.valueOf(123));
		shopAddress.setPostCode("600001");
		retailManagerRequest.setShopAddress(shopAddress);
		String inputJson = mapRequestToJson(retailManagerRequest);

    	ShopGeoLocation shopGeoLocation = new ShopGeoLocation(13.0963045, 80.2865916);
		when(googleGeoCodingAPI.getShopGeoLocation(retailManagerRequest)).thenReturn(shopGeoLocation);

		MvcResult result = mockMvc.
				perform(MockMvcRequestBuilders
						.post(API_URI)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(inputJson))
				.andReturn();

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		System.out.println("TEST - testAddShop - content = " + content);
		System.out.println("TEST - testAddShop - status = " + status);

		Assert.assertEquals("failure - expected HTTP status 201", HttpStatus.CREATED.value(), status);
		Assert.assertTrue("failure - expected HTTP response body to have avalue", !content.isEmpty());

		RetailManagerResponse shopDetailsResponse = mapFromJson(content, RetailManagerResponse.class);

		Assert.assertNotNull("failure - expected shops not null", shopDetailsResponse);
		Assert.assertEquals("failure - expected shopname match", "chennai", shopDetailsResponse.getShopName());
		System.out.println("TEST - testAddShop - END");
	}

	@Test
	public void getShopsTest() throws Exception {
		when(googleGeoCodingAPI.getPostalCode(any())).thenReturn("600001");

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get(API_URI)
						.param("customerLongitude", "80.2865916")
						.param("customerLatitude", "13.0963045")
						.accept(MediaType.APPLICATION_JSON))
				.andReturn();

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		Assert.assertEquals("failure - expected HTTP status", HttpStatus.OK.value(), status);
		Assert.assertTrue("failure - expected HTTP response body to have a value", !content.isEmpty());

	}
}
