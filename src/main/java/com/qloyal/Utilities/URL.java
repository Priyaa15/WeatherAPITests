package com.qloyal.Utilities;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class URL {
	
	public static final String SwaggerURL = "https://api.weatherbit.io/v2.0";
	public static final String WebsiteURL = "https://weatherbit.io";
	
	private static Logger log = LogManager.getLogger(URL.class.getName());

	public static String getWeatherbitURL(){
		log.info("Website URI : " + WebsiteURL);	
		return WebsiteURL;		
	}	


	public static String getWeatherbitURL(String resource){
		log.info("Website URI : " + WebsiteURL);	
		return WebsiteURL +  resource;		
	}	

	public static String getWeatherbitSwaggerURL(String resource){
		log.info("Website URI : " + WebsiteURL);	
		return SwaggerURL +  resource;		
	}
}
