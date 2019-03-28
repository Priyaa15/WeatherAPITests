package com.qloyal.Utilities;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ApiKey {
	private static Logger log = LogManager.getLogger(ApiKey.class.getName());

	
	public static String GetAPIKey() throws IOException {
		String RegisteredAPIkey = PayloadConverter.generatePayloadString("ApiKey.txt");
		return RegisteredAPIkey;
	}
		

}
