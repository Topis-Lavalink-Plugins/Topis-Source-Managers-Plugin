package com.github.topislavalinkplugins.topissourcemanagersplugin;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import static com.github.topislavalinkplugins.topissourcemanagers.ISRCAudioSourceManager.ISRC_PATTERN;
import static com.github.topislavalinkplugins.topissourcemanagers.ISRCAudioSourceManager.QUERY_PATTERN;

@ConfigurationProperties(prefix = "plugins.topissourcemanagers")
@Component
public class PluginConfig{

	private String[] providers = {
		"ytsearch:\"" + ISRC_PATTERN + "\"",
		"ytsearch:" + QUERY_PATTERN
	};

	public String[] getProviders(){
		return this.providers;
	}

	public void setProviders(String[] providers){
		this.providers = providers;
	}

}
