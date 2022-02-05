package com.github.topislavalinkplugins.topissourcemanagersplugin;

import com.github.topislavalinkplugins.topissourcemanagers.spotify.SpotifyConfig;
import com.neovisionaries.i18n.CountryCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "plugins.topissourcemanagers.spotify")
@Component
public class LavalinkSpotifyConfig{

	private String clientId;
	private String clientSecret;
	private CountryCode countryCode = CountryCode.US;

	public String getClientId(){
		return this.clientId;
	}

	public void setClientId(String clientId){
		this.clientId = clientId;
	}

	public String getClientSecret(){
		return this.clientSecret;
	}

	public void setClientSecret(String clientSecret){
		this.clientSecret = clientSecret;
	}

	public CountryCode getCountryCode(){
		return this.countryCode;
	}

	public void setCountryCode(String countryCode){
		this.countryCode = CountryCode.getByCode(countryCode);
	}

	public SpotifyConfig toSpotifyConfig(){
		return new SpotifyConfig(this.clientId, this.clientSecret, this.countryCode);
	}


}
