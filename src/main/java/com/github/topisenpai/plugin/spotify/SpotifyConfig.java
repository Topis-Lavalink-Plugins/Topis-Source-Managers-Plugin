package com.github.topisenpai.plugin.spotify;

import com.neovisionaries.i18n.CountryCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import static com.github.topisenpai.plugin.spotify.SpotifySourceManager.ISRC_PATTERN;
import static com.github.topisenpai.plugin.spotify.SpotifySourceManager.QUERY_PATTERN;

@ConfigurationProperties(prefix = "plugins.spotify")
@Component
public class SpotifyConfig{

	public String clientId;
	public String clientSecret;
	public CountryCode countryCode = CountryCode.US;
	public String[] providers = {
		"ytsearch:\"" + ISRC_PATTERN + "\"",
		"ytsearch:" + QUERY_PATTERN
	};

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

	public String[] getProviders(){
		return providers;
	}

	public void setProviders(String[] providers){
		this.providers = providers;
	}

}
