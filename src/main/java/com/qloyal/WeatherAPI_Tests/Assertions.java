package com.qloyal.WeatherAPI_Tests;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;

import com.qloyal.Utilities.ObjectsHelper;
import com.qloyal.listners.BaseClass;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Assertions extends BaseClass {
	private static Logger log = LogManager.getLogger(Assertions.class.getName());

	public static void verifyTrue(boolean flag) {
		Assert.assertTrue(flag);
	}

	public static void verifyFalse(boolean flag) {
		Assert.assertFalse(flag);
	}

	public static void verifyStatusCode(Response response, int status) {
		Assert.assertEquals(ObjectsHelper.getStatusCode(response), status);
	}

	public static void verifyStatusMessage(Response response, String status) {
		Assert.assertEquals(ObjectsHelper.getStatusCode(response), status);
	}

	public static void verifyResponseJSONObject(int IdSizeActual, int idSizeExpected) {
		Assert.assertEquals(IdSizeActual, idSizeExpected);
	}

	public static void verifyResponseJSONObject(String selfLinksActual, String stringExpected) {
		Assert.assertEquals(selfLinksActual, stringExpected);

	}

	public static void verifyResponseRootObjects(Map<String, Object> Actual,
			Map<String, Object> Expected) {
		

		Assert.assertEquals(Actual.keySet(), Expected.keySet());
		

	}
	public static void verifyResponseRootObjectsSorted(Map<String, Object> Actual,
			Map<String, Object> Expected) {
		
		Map<String , Object> ActualSort = new TreeMap<String, Object>(Actual);
		
		ComparatorDescending comp = new ComparatorDescending(ActualSort);

	        Map<String, Object> ActualSorted = new TreeMap(comp);
	        ActualSorted.putAll(ActualSort);
	        System.out.println(ActualSorted);		
		
		
		Assert.assertEquals(ActualSorted.keySet(), Expected.keySet());
		
		
	}

	
	 public static class ComparatorDescending implements Comparator {
	    Map map;

	        public ComparatorDescending(Map map) {
	        this.map = map;
	    }

	    public int compare(Object o1, Object o2) {
	        return (o2.toString()).compareTo(o1.toString());
	    }}




	public static void verifyResponsePropeties(String Actual, String Expected) {
		

			Assert.assertEquals(Actual, Expected);
		}

	public static void verifyPropertyIsPresent(boolean Actual) {
		Assert.assertEquals(Actual, true);
		
	}
		
		
	}
	


