[![](https://jitpack.io/v/Topis-Lavalink-Plugins/Topis-Source-Managers-Plugin.svg)](https://jitpack.io/#Topis-Lavalink-Plugins/Topis-Source-Managers-Plugin)

# Topis-Source-Managers-Plugin 

A [Lavalink](https://github.com/freyacodes/Lavalink) plugin to support more audio sources.
* [Spotify](https://www.spotify.com) playlists/albums/songs/artists(top tracks)/search results
* [Apple Music](https://www.apple.com/apple-music/) playlists/albums/songs/artists/search results

`*tracks are played via YouTube or other configurable sources`


## Installation

To install this plugin either download the latest release and place it into your `plugins` folder or add the following into your `application.yml`

```yaml
lavalink:
  plugins:
    - dependency: "com.github.Topis-Lavalink-Plugins:Topis-Source-Managers-Plugin:vx.x.x" # replace vx.x.x with the latest release tag!
      repository: "https://jitpack.io"
```

## Configuration

To get your Spotify clientId & clientSecret go [here](https://developer.spotify.com/dashboard/applications) & create a
new application. Then copy the `Client ID` & `Client Secret` into your `application.yml` like the following

```yaml
plugins:
  topissourcemanagers:
    providers: # Custom providers for track loading. This is the default
      - "ytsearch:\"%ISRC%\"" # Will be ignored if track does not have an ISRC. See https://en.wikipedia.org/wiki/International_Standard_Recording_Code
      - "ytsearch:%QUERY%" # Will be used if track has no ISRC or no track could be found for the ISRC
    # - "scsearch:%QUERY%" you can add multiple other fallback sources here
    sources:
      spotify: true # Enable Spotify source
      applemusic: true # Enable Apple Music source
    spotify:
        clientId: "your client id"
        clientSecret: "your client secret"
        countryCode: "US" # the country code you want to use for filtering the artists top tracks. See https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2
    applemusic:
        countryCode: "US" # the country code you want to use for filtering the artists top tracks and language. See https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2
```

## Usage

Just tell Lavalink to load an url and the plugin does the rest
You can also use `spsearch:<query>` or `amsearch:<query>`(remove the <>) to search for songs on Spotify or Apple Music

### All supported URL types are:

#### Spotify
* spsearch:animals architects
* https://open.spotify.com/track/0eG08cBeKk0mzykKjw4hcQ
* https://open.spotify.com/album/7qemUq4n71awwVPOaX7jw4
* https://open.spotify.com/playlist/37i9dQZF1DXaZvoHOvRg3p
* https://open.spotify.com/artist/3ZztVuWxHzNpl0THurTFCv

#### Apple Music
* amsearch:animals architects
* https://music.apple.com/us/album/animals/1533388849?i=1533388859
* https://music.apple.com/us/album/for-those-that-wish-to-exist/1533388849
* https://music.apple.com/us/playlist/architects-essentials/pl.40e568c609ae4b1eba58b6e89f4cd6a5
* https://music.apple.com/us/artist/architects/182821355

---

In case a requested Spotify song can't be found, you will receive a normal `TrackStartEvent`
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
    "cause": "com.github.topislavalinkplugins.topissourcemanagers.TrackNotFoundException: No matching track found",
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
