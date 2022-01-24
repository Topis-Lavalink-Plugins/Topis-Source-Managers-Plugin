[![](https://jitpack.io/v/TopiSenpai/spotify-plugin.svg)](https://jitpack.io/#TopiSenpai/spotify-plugin)

# Spotify-Plugin

A [Lavalink](https://github.com/freyacodes/Lavalink) plugin to lazy load [Spotify](https://www.spotify.com) playlists/albums/songs/artists(top tracks)/search results from [YouTube](https://youtube.com)

## Installation

To install this plugin either download the latest release and place it into your `plugins` folder or add the following into your `application.yml`

```yaml
lavalink:
  plugins:
    - dependency: "com.github.topisenpai:spotify-plugin:vx.x.x" # replace vx.x.x with the latest release tag!
      repository: "https://jitpack.io"
```

## Configuration

To get your Spotify clientId & clientSecret go [here](https://developer.spotify.com/dashboard/applications) & create a
new application. Then copy the `Client ID` & `Client Secret` into your `application.yml` like the following

```yaml
plugins:
  spotify:
    clientId: "your client id"
    clientSecret: "your client secret"
    countryCode: "US" # the country code you want to use for filtering the artists top tracks. See https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2
    providers: # Custom providers for track loading. This is the default
        - "ytsearch:\"%ISRC%\"" # Will be ignored if track does not have an ISRC. See https://en.wikipedia.org/wiki/International_Standard_Recording_Code
        - "ytsearch:%QUERY%" # Will be used if track has no ISRC or no track could be found for the ISRC
      # - "scsearch:%QUERY%" you can add multiple other fallback sources here
```

## Usage

Just tell Lavalink to load a Spotify url and the plugin does the rest
You can also use `spsearch:<query>`(remove the <>) to search for songs on Spotify 

#### All supported Spotify URL types are:

* spsearch:animals architects
* https://open.spotify.com/track/0eG08cBeKk0mzykKjw4hcQ
* https://open.spotify.com/album/7qemUq4n71awwVPOaX7jw4
* https://open.spotify.com/playlist/37i9dQZF1DXaZvoHOvRg3p
* https://open.spotify.com/artist/3ZztVuWxHzNpl0THurTFCv

---

In some cases a requested Spotify songs can't be found on YouTube then you will receive a normal `TrackStartEvent`
followed by a `TrackExceptionEvent` and later `TrackEndEvent`

<details>
<summary>TrackStartEvent Example</summary>

```json
{
  "op": "event",
  "type": "TrackStartEvent",
  "guildId": "730879265956167740",
  "track": "QAAAdwIADTMyNTM0NmI0NTZiNTYAEDc0NXY5NjQ4OTY3dmI0ODkAAAAAAAO9CAALamRXaEpjcnJqUXMAAQAraHR0cHM6Ly93d3cueW91dHViZS5jb20vd2F0Y2g/dj1qZFdoSmNycmpRcwAHc3BvdGlmeQAAAAAAA7ok"
}
```

</details>

<details>
<summary>TrackExceptionEvent Example</summary>

```json
{
  "op": "event",
  "type": "TrackExceptionEvent",
  "guildId": "730879265956167740",
  "track": "QAAAdwIADTMyNTM0NmI0NTZiNTYAEDc0NXY5NjQ4OTY3dmI0ODkAAAAAAAO9CAALamRXaEpjcnJqUXMAAQAraHR0cHM6Ly93d3cueW91dHViZS5jb20vd2F0Y2g/dj1qZFdoSmNycmpRcwAHc3BvdGlmeQAAAAAAA7ok",
  "error": "Something broke when playing the track.",
  "exception": {
    "severity": "COMMON",
    "cause": "com.github.topisenpai.plugin.spotify.SpotifyTrackNotFoundException: No matching Spotify track found",
    "message": "Something broke when playing the track."
  }
}
```

</details>

<details>
<summary>TrackEndEvent Example</summary>

```json
{
  "op": "event",
  "reason": "CLEANUP",
  "type": "TrackEndEvent",
  "guildId": "730879265956167740",
  "track": "QAAAdwIADTMyNTM0NmI0NTZiNTYAEDc0NXY5NjQ4OTY3dmI0ODkAAAAAAAO9CAALamRXaEpjcnJqUXMAAQAraHR0cHM6Ly93d3cueW91dHViZS5jb20vd2F0Y2g/dj1qZFdoSmNycmpRcwAHc3BvdGlmeQAAAAAAA7ok"
}
```

</details>
