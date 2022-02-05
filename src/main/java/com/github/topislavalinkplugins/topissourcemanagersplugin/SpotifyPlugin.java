package com.github.topislavalinkplugins.topissourcemanagersplugin;

import com.github.topislavalinkplugins.topissourcemanagers.applemusic.AppleMusicSourceManager;
import com.github.topislavalinkplugins.topissourcemanagers.spotify.SpotifySourceManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import dev.arbjerg.lavalink.api.AudioPlayerManagerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SpotifyPlugin implements AudioPlayerManagerConfiguration{

	private static final Logger log = LoggerFactory.getLogger(SpotifyPlugin.class);

	private final PluginConfig pluginConfig;
	private final SourcesConfig sourcesConfig;
	private final LavalinkSpotifyConfig lavalinkSpotifyConfig;
	private final AppleMusicConfig appleMusicConfig;

	public SpotifyPlugin(PluginConfig pluginConfig, SourcesConfig sourcesConfig, LavalinkSpotifyConfig lavalinkSpotifyConfig, AppleMusicConfig appleMusicConfig){
		log.info("Loading Topis-Source-Managers-Plugin...");
		this.pluginConfig = pluginConfig;
		this.sourcesConfig = sourcesConfig;
		this.lavalinkSpotifyConfig = lavalinkSpotifyConfig;
		this.appleMusicConfig = appleMusicConfig;
	}

	@Override
	public AudioPlayerManager configure(AudioPlayerManager manager){
		if(this.sourcesConfig.isSpotify()){
			log.info("Loading Spotify-SourceManager...");
			manager.registerSourceManager(new SpotifySourceManager(this.pluginConfig.getProviders(), this.lavalinkSpotifyConfig.toSpotifyConfig(), manager));
		}
		if(this.sourcesConfig.isAppleMusic()){
			log.info("Loading Apple-Music-SourceManager...");
			manager.registerSourceManager(new AppleMusicSourceManager(this.pluginConfig.getProviders(), this.appleMusicConfig.getCountryCode(), manager));
		}
		return manager;
	}

}
