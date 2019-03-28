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

public class AC2_GetDataByPostCode extends BaseClass {
	private String RegisteredApikey;
	private static Logger log = LogManager.getLogger(AC2_GetDataByPostCode.class.getName());
	private Map<String, String> QueryParams;
	Response response;

	@BeforeMethod
	public void setUp() throws IOException {
		RegisteredApikey = ApiKey.GetAPIKey();
		Map<String, String> QueryParams = new HashMap<String, String>();
		QueryParams.put("postal_code", "38547");
		QueryParams.put("key", RegisteredApikey);
	}

	@Test
	public void StatusCode200_GetByPostcode() throws IOException {
		log.info("Starting GET current Weather Data by Postcode Test");
		ExtentTestManager.getTest().log(LogStatus.INFO, "Test Case AC2 : ",
				"GET current Weather Data by Postalcode Returns 200");

		String ExpectedDataResponse = PayloadConverter
				.generatePayloadString("WeatherDataResponse\\WeatherByPostCode.json");

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
				" 35 properties from the response");
		final int DataObjectHas35properties = new JsonPath(strResponseActual).get("data[0].size()");
		Assertions.verifyResponseJSONObject(DataObjectHas35properties, 35);
		System.out.println(DataObjectHas35properties);
		ExtentTestManager.getTest().log(LogStatus.PASS, "Actual ", "" + 36);
		/*
		 * 4 Verify hat there is a appropriate statecode is in the response
		 * based on the Query param passed
		 */
		ExtentTestManager.getTest().log(LogStatus.INFO,
				" Verify that there is a appropriate statecode  is in the response based on the Query param passed, eg: State_code is TN for the postcode 38547");
		Assertions.verifyResponsePropeties(JsonPath.with(ExpectedDataResponse).getString("data[0].state_code"),
				JsonPath.with(strResponseActual).getString("data[0].state_code"));
		ExtentTestManager.getTest().log(LogStatus.PASS, "Actual:",
				"" + JsonPath.with(strResponseActual).getString("data[0].state_code"));

	}
	
	@Test
	public void StatusCode200_GetByPostalCodeforMultipleCities() throws IOException {
		log.info("Starting GET current Weather Data with no Postal code Test");

		ExtentTestManager.getTest().log(LogStatus.INFO, "Test Case AC2 : ",
				"GET current Weather Data with no  Post code Test Returns 400");

		String ExpectedDataResponse = PayloadConverter
				.generatePayloadString("WeatherDataResponse\\WeatherByPostCode.json");

		String URI = URL.getWeatherbitSwaggerURL("/current");

		Map<String, String> QueryParams_MultiplePostCode = new HashMap<String, String>();
		QueryParams_MultiplePostCode.put("postal_code", "524901,703448,2643743");
		QueryParams_MultiplePostCode.put("key", RegisteredApikey);

		ExtentTestManager.getTest().log(LogStatus.INFO, "URI : ", URI);
		ExtentTestManager.getTest().log(LogStatus.INFO, "QueryParams_NoPostCode : ", QueryParams_MultiplePostCode.toString());
		/*
		 * 1. Verify the status code is 400 bad request "error":
		 * "Invalid Parameters supplied."
		 */

		 response = RESTCalls.GETRequest(URI, QueryParams_MultiplePostCode).then().assertThat().statusCode(400).extract().response();
		ExtentTestManager.getTest().log(LogStatus.INFO, "response is ", response.toString());
		ExtentTestManager.getTest().log(LogStatus.INFO, "Status code Expected", "200");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Status code Actual",
				Integer.toString(response.getStatusCode()));
	}

	@Test
	public void StatusCode400_GetWithNoPostalCode() throws IOException {
		log.info("Starting GET current Weather Data with no Postal code Test");

		ExtentTestManager.getTest().log(LogStatus.INFO, "Test Case AC2 : ",
				"GET current Weather Data with no  Post code Test Returns 400");

		String ExpectedDataResponse = PayloadConverter
				.generatePayloadString("WeatherDataResponse\\WeatherByPostCode.json");

		String URI = URL.getWeatherbitSwaggerURL("/current");

		Map<String, String> QueryParams_NoPostCode = new HashMap<String, String>();
		QueryParams_NoPostCode.put("key", RegisteredApikey);

		ExtentTestManager.getTest().log(LogStatus.INFO, "URI : ", URI);
		ExtentTestManager.getTest().log(LogStatus.INFO, "QueryParams_NoPostCode : ", QueryParams_NoPostCode.toString());
		/*
		 * 1. Verify the status code is 400 bad request "error":
		 * "Invalid Parameters supplied."
		 */

		 response = RESTCalls.GETRequest(URI, QueryParams_NoPostCode).then().assertThat().statusCode(400).extract().response();
		ExtentTestManager.getTest().log(LogStatus.INFO, "response is ", response.toString());
		ExtentTestManager.getTest().log(LogStatus.INFO, "Status code Expected", "400");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Status code Actual",
				Integer.toString(response.getStatusCode()));
	}

	@Test
	public void StatusCode400_GetWithInvalid_PostcalCodee() throws IOException {
		log.info("Starting GET current Weather Data with invalid Postal code Test");

		ExtentTestManager.getTest().log(LogStatus.INFO, "Test Case AC2 : ",
				"GET current Weather Data with invalid  Post code Test Returns 400");

		String ExpectedDataResponse = PayloadConverter
				.generatePayloadString("WeatherDataResponse\\WeatherByPostCode.json");

		String URI = URL.getWeatherbitSwaggerURL("/current");
		Map<String, String> QueryParams_Invalid = new HashMap<String, String>();
		QueryParams_Invalid.put("postal_code", "xysz");
		QueryParams_Invalid.put("key", RegisteredApikey);

		ExtentTestManager.getTest().log(LogStatus.INFO, "URI : ", URI);
		ExtentTestManager.getTest().log(LogStatus.INFO, "QueryParams_NoPostCode : ", QueryParams_Invalid.toString());

		/*
		 * 1. Verify the status code is 400 bad request "error":
		 * "Invalid lat/lon supplied."
		 */

		 response = RESTCalls.GETRequest(URI, QueryParams_Invalid).then().assertThat().statusCode(400).extract().response();

		ExtentTestManager.getTest().log(LogStatus.INFO, "response is ", response.toString());
		ExtentTestManager.getTest().log(LogStatus.INFO, "Status code Expected", "400");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Status code Actual",
				Integer.toString(response.getStatusCode()));

	}

	@Test
	public void StatusCode403_GetWithNoAPIKey() throws IOException {
		log.info("Starting GET current Weather Data with  Postal code and NO API Key Test");

		ExtentTestManager.getTest().log(LogStatus.INFO, "Test Case AC2 : ",
				" GET current Weather Data with  Postal code and NO API Key ");

		String ExpectedDataResponse = PayloadConverter
				.generatePayloadString("WeatherDataResponse\\WeatherByPostCode.json");
		String URI = URL.getWeatherbitSwaggerURL("/current");
		Map<String, String> QueryParams_NoAPIKey = new HashMap<String, String>();
		QueryParams_NoAPIKey.put("postal_code", "2000");

		/*
		 * 1. Verify the status code is 403 bad request "error": "AccessDenied."
		 */

		 response = RESTCalls.GETRequest(URI, QueryParams_NoAPIKey).then().assertThat().statusCode(403)
				.extract().response();

		ExtentTestManager.getTest().log(LogStatus.INFO, "Response is : " + response.toString());
		ExtentTestManager.getTest().log(LogStatus.PASS, "Status code is : " + response.statusCode());

	}

}
