package com.qloyal.Utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class PayloadConverter {
	
	private static Logger log = LogManager.getLogger(PayloadConverter.class.getName());
	public static String generatePayloadString(String filename) throws IOException{
		//log.info("Inside PayloadConverter function");
		String filePath = System.getProperty("user.dir")+"\\Resources\\"+ filename;
		try{
			//System.out.println(new String(Files.readAllBytes(Paths.get(filePath))));
			return new String(Files.readAllBytes(Paths.get(filePath)));
		} catch(IOException e){
			log.error(e);
			return null;
		}
		

}}