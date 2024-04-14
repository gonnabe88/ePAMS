package com.kdb.common.service;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

	/**
	 * Folder location for storing files
	 */
	private String location = "C:/epams";

	public String getLocation() {
		System.out.println("getLocation");
		return location;
	}

	public void setLocation(String location) {
		System.out.println("setLocation");
		this.location = location;
	}

}