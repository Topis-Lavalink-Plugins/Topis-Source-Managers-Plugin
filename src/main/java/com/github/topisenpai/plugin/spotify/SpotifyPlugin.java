package com.github.topisenpai.plugin.spotify;

import com.github.lavalinkplugins.spotify.SpotifySourceManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import dev.arbjerg.lavalink.api.AudioPlayerManagerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SpotifyPlugin implements AudioPlayerManagerConfiguration{

	private static final Logger log = LoggerFactory.getLogger(SpotifyPlugin.class);

	private final LavalinkSpotifyConfig config;

	public SpotifyPlugin(LavalinkSpotifyConfig config){
		log.info("Loading Spotify Plugin...");
		this.config = config;
	}

	@Override
	public AudioPlayerManager configure(AudioPlayerManager manager){
		if(config.getClientId() == null || config.getClientId().isEmpty()){
			log.error("No spotify client id found in configuration. Not registering spotify source manager. Config key is 'plugins.spotify.clientId");
			return manager;
		}
		if(config.getClientSecret() == null || config.getClientSecret().isEmpty()){
			log.error("No spotify client secret found in configuration. Not registering spotify source manager. Config key is 'plugins.spotify.clientSecret");
			return manager;
		}
		manager.registerSourceManager(new SpotifySourceManager(this.config.toSpotifyConfig(), manager));
		return manager;
	}

}
