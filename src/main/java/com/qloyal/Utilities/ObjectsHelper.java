package com.qloyal.Utilities;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class ObjectsHelper {
	private static Logger log = LogManager.getLogger(ObjectsHelper.class.getName());
	
	public static String getJSONResponseString(Response response){
		log.info("Converting response to String");
		String strResponse = response.getBody().asString();
		log.debug(strResponse);
		return strResponse;		
	}
	
	public static JsonPath JSONPaser(String response){
		log.info("Parsing a string response to JSON");
		JsonPath jsonResponse = new JsonPath(response);
		log.debug(jsonResponse);
		return jsonResponse;		
	}
	
	public static XmlPath XMLPaser(String response){
		log.info("Parsing a string response to XML");
		XmlPath XmlResponse = new XmlPath(response);
		log.debug(XmlResponse);
		return XmlResponse;		
	}
	
	public static int getStatusCode(Response response){
		log.info("Getting Response status code");
		int statusCode = response.getStatusCode();
		log.debug(statusCode);
		return statusCode;
		
	}
	
	public static String getStatusMessage(Response response){
		log.info("Converting response to String");
		String strResponse = response.getBody().asString();
		log.debug(strResponse);
		return strResponse;		
		
	}
	
	public static int getJSONObject(Response response){
		log.info("Getting JSON Object from Response body");

		JsonPath jsonResponse = new JsonPath(response.asString());
		String JsonObjectLength = jsonResponse.get("id");
		int idLength = JsonObjectLength.length();
		log.debug(jsonResponse.get("id"));
		log.debug("Id object length is " + idLength);
		return idLength;
		
	}
	
	

}