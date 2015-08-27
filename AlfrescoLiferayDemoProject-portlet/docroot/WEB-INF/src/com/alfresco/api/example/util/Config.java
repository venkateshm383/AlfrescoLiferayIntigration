package com.alfresco.api.example.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class Config {

	private static Properties config;
	
	public static void main(String[] args) {
System.out.println("datata"+getConfig());	getConfig();
	}

	public static Properties getConfig() {
		if (config == null) {
			config = new Properties();
			try {
			
		   		config.load(Config.class.getClassLoader()
						.getResourceAsStream("config.properties"));
		   	} catch (IOException ioe) {
		   		ioe.printStackTrace();
		    }
			
		}
		return config;
	}	

}
