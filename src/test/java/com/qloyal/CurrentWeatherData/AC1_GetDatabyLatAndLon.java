package com.qloyal.CurrentWeatherData;

import java.io.IOException;

import java.util.HashMap;

import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.testng.annotations.BeforeMethod;

import org.testng.annotations.Test;

import com.qloyal.Utilities.ApiKey;
import com.qloyal.Utilities.PayloadConverter;
import com.qloyal.Utilities.URL;
import com.qloyal.WeatherAPI_Tests.Assertions;
import com.qloyal.WeatherAPI_Tests.RESTCalls;
import com.qloyal.listners.BaseClass;
import com.qloyal.listners.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AC1_GetDatabyLatAndLon extends BaseClass {
	private String RegisteredApikey;
	private static Logger log = LogManager.getLogger(AC1_GetDatabyLatAndLon.class.getName());
	private Map<String, String> QueryParams;
	Response response;

	@BeforeMethod
	public void setUp() throws IOException {
		RegisteredApikey = ApiKey.GetAPIKey();
		QueryParams = new HashMap<String, String>();
		QueryParams.put("lat", "52.516");
		QueryParams.put("lon", "13.3890");
		QueryParams.put("key", RegisteredApikey);
	}

	@Test
	public void StatusCode200_GetDataByLatAndLon() throws IOException {
		log.info("Starting GET current Weather Data by Lattitude and Longitude Test");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Test Case AC1 : ",
				"GET current Weather Data by Lattitude and Longitude Test Returns 200");

		String ExpectedDataResponse = PayloadConverter
				.generatePayloadString("WeatherDataResponse\\WeatherByLatAndLon.json");

		String URI = URL.getWeatherbitSwaggerURL("/current");
		ExtentTestManager.getTest().log(LogStatus.INFO, "URI : ", URI);
		ExtentTestManager.getTest().log(LogStatus.INFO, "QueryParameters : ", QueryParams.toString());

		/*
		 * 1. Verify the status code is 200
		 */

		response = (Response) RESTCalls.GETRequest(URI, QueryParams).then().assertThat().statusCode(200)
				.extract().response();
		String strResponseActual = response.asString();

		ExtentTestManager.getTest().log(LogStatus.INFO, "response is ", strResponseActual);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Status code Expected", "200");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Status code Actual",
				Integer.toString(response.getStatusCode()));

		/*
		 * 2. Verify there are 2 object nodes a. data, b. count
		 */

		Map<String, Object> WeatherDataRootNodeActual = JsonPath.from(strResponseActual).get("");
		Map<String, Object> WeatherDataRootNodesExpected = JsonPath.from(ExpectedDataResponse).get("");

		ExtentTestManager.getTest().log(LogStatus.INFO, "Verifythat there are 2 object Root nodes",
				"" + WeatherDataRootNodeActual.keySet());
		Assertions.verifyResponseRootObjects(WeatherDataRootNodeActual, WeatherDataRootNodesExpected);
		ExtentTestManager.getTest().log(LogStatus.PASS, "Actual:", "" + WeatherDataRootNodesExpected.keySet());

		/*
		 * 3. Verify the data Object has 35 properties from the response
		 */
		ExtentTestManager.getTest().log(LogStatus.INFO, "Verify the data Object has ",
				" 36 properties from the response");
		final int DataObjectHas35properties = new JsonPath(strResponseActual).get("data[0].size()");
		Assertions.verifyResponseJSONObject(DataObjectHas35properties, 36);
		ExtentTestManager.getTest().log(LogStatus.PASS, "Actual ", "" + 36);

		/*
		 * 4.1 Verify the passed QueryParamenter Lat are in the response
		 */
		ExtentTestManager.getTest().log(LogStatus.INFO, " Verify the passed QueryParamenter",
				"Lat Expected: 	2.516 is in the response");
		Assertions.verifyResponsePropeties(JsonPath.with(ExpectedDataResponse).getString("data[0].lat"),
				JsonPath.with(strResponseActual).getString("data[0].lat"));
		ExtentTestManager.getTest().log(LogStatus.PASS, "Actual:",
				"" + JsonPath.with(strResponseActual).getString("data[0].lat"));

		/*
		 * 4.2 Verify the passed QueryParamenter Lon are in the response
		 */
		ExtentTestManager.getTest().log(LogStatus.INFO, " Verify the passed QueryParamenter", "Lon Expected:13.3890 is in the response");
		Assertions.verifyResponsePropeties(JsonPath.with(ExpectedDataResponse).getString("data[0].lon"),
				JsonPath.with(strResponseActual).getString("data[0].lon"));

		ExtentTestManager.getTest().log(LogStatus.PASS, "Actual",
				"" + JsonPath.with(strResponseActual).getString("data[0].lon"));

	}

	@Test
	public void GetDataByLatAndLon_HasLocationProperty() throws IOException {
		log.info(
				"Starting GET current Weather Data with Lattitude and Longitude Test should have a location property under Data object");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Test Case Name : ",
				"Get current WeatherData with Lattitude and Longitude Test should have a location property under Data object");

		String ExpectedDataResponse = PayloadConverter
				.generatePayloadString("WeatherDataResponse\\WeatherByLatAndLon.json");

		String URI = URL.getWeatherbitSwaggerURL("/current");

		ExtentTestManager.getTest().log(LogStatus.INFO, "URI : ", URI);
		ExtentTestManager.getTest().log(LogStatus.INFO, "QueryParameters : ", QueryParams.toString());

		/*
		 * 1. Verify the status code is 200
		 */

		response = (Response) RESTCalls.GETRequest(URI, QueryParams).then().assertThat().statusCode(200)
				.extract().response();
		String strResponseActual = response.asString();

		/*
		 * 2. Verify the data Object has a location property
		 * 
		 */
		JsonPath jsonPath = new JsonPath(strResponseActual);
		Assertions.verifyPropertyIsPresent(jsonPath.getBoolean("data.any { it.containsKey('location') }"));
		ExtentTestManager.getTest().log(LogStatus.PASS, "Expected:location Property is present", " Actual: locationProperty:"
				+ jsonPath.getBoolean("data.any { it.containsKey('location') }"));

	}

	@Test
	public void StatusCode200_GetCurrentWeatherDataByLatAndLonWithMultipleCities() throws IOException {
		log.info(
				"Starting GET current Weather Data with NO Lattitude and Longitude with multiple lattitudes and Longitudes");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Test Case Name : ",
				"Get current WeatherData with Lattitude and Longitude Test for multiple cities by passing multiple lattitude and Longitude parameters returns 200 with multiple locations ");

		String ExpectedDataResponse = PayloadConverter
				.generatePayloadString("WeatherDataResponse\\WeatherByLatAndLon.json");

		String URI = URL.getWeatherbitSwaggerURL("/current");
		Map<String, String> QueryParamsForMultipleCities = new HashMap<String, String>();
		QueryParamsForMultipleCities.put("lat", "52.516, 34.7");
		QueryParamsForMultipleCities.put("lon", "13.3890, 15");
		QueryParamsForMultipleCities.put("key", RegisteredApikey);

		ExtentTestManager.getTest().log(LogStatus.INFO, "URI : ", URI);
		ExtentTestManager.getTest().log(LogStatus.INFO, "QueryParameters for multiple cities : ",
				QueryParamsForMultipleCities.toString());

		/*
		 * 1. Verify the status code is 200 bad request "error":
		 * 
		 */

		 response = (Response) RESTCalls.GETRequest(URI, QueryParamsForMultipleCities).then().assertThat()
				.statusCode(200).extract().response();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Response is : ",  response.toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, "Status code is : ", ""+ response.statusCode());

	}

	@Test
	public void StatusCode400_GetWithNoLatAndLonQparams() throws IOException {
		log.info("Starting GET current Weather Data with NO Lattitude and Longitude Test");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Test Case Name : " + "GetWithNoLatAndLonQparams_Returns400");

		String ExpectedDataResponse = PayloadConverter
				.generatePayloadString("WeatherDataResponse\\WeatherByLatAndLon.json");

		String URI = URL.getWeatherbitSwaggerURL("/current");
		Map<String, String> NOQueryParams = new HashMap<String, String>();
		NOQueryParams.put("key", RegisteredApikey);

		ExtentTestManager.getTest().log(LogStatus.INFO, "Endpoint is" + URI + "With NO Query Parameters  ");
		ExtentTestManager.getTest().log(LogStatus.INFO, "GET ~ " + URI);

		/*
		 * 1. Verify the status code is 400 bad request "error":
		 * "Invalid Parameters supplied."
		 */

		 response = (Response) RESTCalls.GETRequest(URI, NOQueryParams).then().assertThat().statusCode(400)
				.extract().response();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Response is : ", response.toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, "Status code is : " + response.statusCode());

	}

	@Test
	public void StatucCode400_WithInvalid_LatAndLonQparams() throws IOException {
		log.info("Starting GET current Weather Data with invalid  Lattitude and Longitude Test");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Test Case Name : ",
				"Get current WeatherData with invalid  Lattitude and Longitude Test  400");

		String ExpectedDataResponse = PayloadConverter
				.generatePayloadString("WeatherDataResponse\\WeatherByLatAndLon.json");

		String URI = URL.getWeatherbitSwaggerURL("/current");
		Map<String, String> InvalidQueryParams = new HashMap<String, String>();
		InvalidQueryParams.put("lat", "xyz");
		InvalidQueryParams.put("lon", "abc");
		InvalidQueryParams.put("key", RegisteredApikey);

		ExtentTestManager.getTest().log(LogStatus.INFO, "URI : ", URI);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Invalid Query Parameters: ", InvalidQueryParams.toString());

		/*
		 * 1. Verify the status code is 400 bad request "error":
		 * "Invalid lat/lon supplied."
		 */

		 response = (Response) RESTCalls.GETRequest(URI, InvalidQueryParams).then().assertThat().statusCode(400)
				.extract().response();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Response is : " ,response.toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, "Status code is : " + response.statusCode());

	}

	@Test
	public void StatusCode403_GetWithNoAPIKey() throws IOException {
		log.info("Starting GET current Weather Data by Lattitude and Longitude Test with No API Key");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Test Case Name : ",
				"Get current WeatherData withLattitude and Longitude Test and no API Key returns  403 Access denied");

		String ExpectedDataResponse = PayloadConverter
				.generatePayloadString("WeatherDataResponse\\WeatherByLatAndLon.json");

		String URI = URL.getWeatherbitSwaggerURL("/current");
		Map<String, String> QueryParams_NoAPI = new HashMap<String, String>();
		QueryParams_NoAPI.put("lat", "30");
		QueryParams_NoAPI.put("lon", "70");

		/*
		 * 1. Verify the status code is 403 bad request "error": "AccessDenied."
		 */

		response = (Response) RESTCalls.GETRequest(URI, QueryParams_NoAPI).then().assertThat().statusCode(403)
				.extract().response();
		ExtentTestManager.getTest().log(LogStatus.INFO, "Response is : ", response.toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, "Status code is : " + response.statusCode());

	}

}
