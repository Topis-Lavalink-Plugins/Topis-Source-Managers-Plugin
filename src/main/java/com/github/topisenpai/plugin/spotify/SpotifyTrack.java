package com.github.topisenpai.plugin.spotify;

import com.sedmelluq.discord.lavaplayer.source.AudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException.Severity;
import com.sedmelluq.discord.lavaplayer.track.AudioItem;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioReference;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import com.sedmelluq.discord.lavaplayer.track.DelegatedAudioTrack;
import com.sedmelluq.discord.lavaplayer.track.InternalAudioTrack;
import com.sedmelluq.discord.lavaplayer.track.playback.LocalAudioTrackExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.michaelthelin.spotify.model_objects.specification.Album;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;

import java.util.List;

import static com.github.topisenpai.plugin.spotify.SpotifySourceManager.ISRC_PATTERN;
import static com.github.topisenpai.plugin.spotify.SpotifySourceManager.QUERY_PATTERN;

public class SpotifyTrack extends DelegatedAudioTrack{

	private static final Logger log = LoggerFactory.getLogger(SpotifyTrack.class);

	private final String isrc;
	private final String artworkURL;
	private final SpotifySourceManager spotifySourceManager;

	public SpotifyTrack(String title, String identifier, String isrc, Image[] images, String uri, ArtistSimplified[] artists, Integer trackDuration, SpotifySourceManager spotifySourceManager){
		this(new AudioTrackInfo(title,
			artists.length == 0 ? "unknown" : artists[0].getName(),
			trackDuration.longValue(),
			identifier,
			false,
			"https://open.spotify.com/track/" + identifier
		), isrc, images.length == 0 ? null : images[0].getUrl(), spotifySourceManager);
	}

	public SpotifyTrack(AudioTrackInfo trackInfo, String isrc, String artworkURL, SpotifySourceManager spotifySourceManager){
		super(trackInfo);
		this.isrc = isrc;
		this.artworkURL = artworkURL;
		this.spotifySourceManager = spotifySourceManager;
	}

	public static SpotifyTrack of(TrackSimplified track, Album album, SpotifySourceManager spotifySourceManager){
		return new SpotifyTrack(track.getName(), track.getId(), null, album.getImages(), track.getUri(), track.getArtists(), track.getDurationMs(), spotifySourceManager);
	}

	public static SpotifyTrack of(Track track, SpotifySourceManager spotifySourceManager){
		return new SpotifyTrack(track.getName(), track.getId(), track.getExternalIds().getExternalIds().getOrDefault("isrc", null), track.getAlbum().getImages(), track.getUri(), track.getArtists(), track.getDurationMs(), spotifySourceManager);
	}

	public String getISRC(){
		return this.isrc;
	}

	public String getArtworkURL(){
		return this.artworkURL;
	}

	private String buildSearchQuery(){
		var query = trackInfo.title;
		if(!trackInfo.author.equals("unknown")){
			query += " " + trackInfo.author;
		}
		return query;
	}

	@Override
	public void process(LocalAudioTrackExecutor executor) throws Exception{
		SpotifyConfig config = this.spotifySourceManager.getConfig();
		AudioItem track = null;

		for(int i = 0; i < config.providers.length; i++){
			String identifier = config.providers[i];

			if(identifier.startsWith(SpotifySourceManager.SEARCH_PREFIX)){
				log.warn("Can not use spsearch as provider!");
				continue;
			}

			if(this.isrc != null){
				identifier = identifier.replace(ISRC_PATTERN, this.isrc);
			}else{
				if(identifier.contains(ISRC_PATTERN)){
					log.debug("Spotify: Ignoring identifier \""+identifier+"\" because this track does not have an ISRC!");
					continue;
				}
			}
			identifier = identifier.replace(QUERY_PATTERN, buildSearchQuery());
			track = loadItem(identifier);
			if (track != null){
				break;
			}
		}

		if(track instanceof AudioPlaylist){
			track = ((AudioPlaylist) track).getTracks().get(0);
		}
		if(track instanceof InternalAudioTrack){
			processDelegate((InternalAudioTrack) track, executor);
			return;
		}
		throw new FriendlyException("No matching Spotify track found", Severity.COMMON, new SpotifyTrackNotFoundException());
	}

	@Override
	public AudioSourceManager getSourceManager(){
		return this.spotifySourceManager;
	}

	public AudioItem loadItem(String query){
		try{
			var apm = this.spotifySourceManager.getAudioPlayerManager();
			var f = apm.getClass().getDeclaredField("sourceManagers");
			f.setAccessible(true);
			var managers = (List<AudioSourceManager>) f.get(apm);
			for(var manager : managers){
				var item = manager.loadItem(null, new AudioReference(query, null));
				if(item != null)
					return item;
			}
			return null;
		}
		catch(NoSuchFieldException | IllegalAccessException e){
			log.error("An error occurred while loading item", e);
		}
		return null;
	}

}
