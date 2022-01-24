package com.github.topisenpai.plugin.spotify;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import dev.arbjerg.lavalink.api.AudioPlayerManagerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SpotifyPlugin implements AudioPlayerManagerConfiguration{

	private static final Logger log = LoggerFactory.getLogger(SpotifyPlugin.class);

	private final SpotifyConfig config;

	public SpotifyPlugin(SpotifyConfig config){
		log.info("Loading Spotify Plugin...");
		this.config = config;
	}

	@Override
	public AudioPlayerManager configure(AudioPlayerManager manager){
		if(config.clientId == null || config.clientId.isEmpty()){
			log.error("No spotify client id found in configuration. Not registering spotify source manager. Config key is 'plugins.spotify.clientId");
			return manager;
		}
		if(config.clientSecret == null || config.clientSecret.isEmpty()){
			log.error("No spotify client secret found in configuration. Not registering spotify source manager. Config key is 'plugins.spotify.clientSecret");
			return manager;
		}
		manager.registerSourceManager(new SpotifySourceManager(this.config, manager));
		return manager;
	}

}
