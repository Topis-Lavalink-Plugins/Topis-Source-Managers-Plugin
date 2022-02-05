package com.github.topislavalinkplugins.topissourcemanagersplugin;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@ConfigurationProperties(prefix = "plugins.topissourcemanagers.sources")
@Component
public class SourcesConfig{

	private boolean spotify = true;
	private boolean appleMusic = true;

	public boolean isSpotify(){
		return this.spotify;
	}

	public void setSpotify(boolean spotify){
		this.spotify = spotify;
	}

	public boolean isAppleMusic(){
		return this.appleMusic;
	}

	public void setAppleMusic(boolean appleMusic){
		this.appleMusic = appleMusic;
	}

}
