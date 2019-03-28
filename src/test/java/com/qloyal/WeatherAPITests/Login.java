package com.qloyal.WeatherAPITests;

import java.io.IOException;

import org.testng.annotations.Test;

import com.qloyal.Utilities.PayloadConverter;
import com.qloyal.Utilities.URL;
import com.qloyal.WeatherAPI_Tests.RESTCalls;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.path.xml.XmlPath.CompatibilityMode;
import io.restassured.response.Response;

public class Login {
	Response postresponse;
	Response getresponse;
	
	@Test
	public void doLogin() throws IOException{
		String loginPayload = PayloadConverter.generatePayloadString("WeatherbitLogin.json");
		String endpointURI = URL.getWeatherbitURL("/account/login");
		String dashEndpoint = URL.getWeatherbitURL("/account/dashboard");
		
		postresponse = RESTCalls.POSTRequest(endpointURI);
		
		
		
		String strResponse = postresponse.getBody().asString();
		//System.out.println(strResponse);
		
		getresponse= RESTCalls.GETRequest(dashEndpoint);
		
		 XmlPath doc = new XmlPath(
	                CompatibilityMode.HTML,
	                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">"
	                        + "<html xmlns=\"http://www.w3.org/1999/xhtml\">"
	                        + "<head><title>Hello world</title></head>"
	                        + "<body>some body"
	                        + "<div class=\"content\">wrapped</div>"
	                        + "<div class=\"content\">wrapped2</div>"
	                        + "</body></html>");
		 
		 String content = doc.getString("html.body.div");
				 //doc.getString("html/body/div/div/find{it.@class =='ccol-md-9 well'}");
		//String strResponse2  = getresponse.getBody().asString();
		System.out.println(content);
		//JsonPath jsonRes = new JsonPath(strResponse);
		//String key = jsonRes.getString("session.value");
		//System.out.println(key);
		
		
		
		
	}

}
