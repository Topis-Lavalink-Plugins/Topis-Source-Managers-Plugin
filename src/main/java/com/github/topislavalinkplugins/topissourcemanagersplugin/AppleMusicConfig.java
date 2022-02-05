package com.github.topislavalinkplugins.topissourcemanagersplugin;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "plugins.topissourcemanagers.applemusic")
@Component
public class AppleMusicConfig{

	private String countryCode = "us";

	public String getCountryCode(){
		return this.countryCode;
	}

	public void setCountryCode(String countryCode){
		this.countryCode = countryCode;
	}

}
