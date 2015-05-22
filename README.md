[![Build Status](https://travis-ci.org/kaaes/spotify-web-api-android.svg?branch=master)](https://travis-ci.org/kaaes/spotify-web-api-android)

# Spotify Web API for Android

This project is a wrapper for the [Spotify Web API](https://developer.spotify.com/web-api/).
It uses [Retrofit](http://square.github.io/retrofit/) to create Java interfaces from API endpoints.

## Building
This project is built using [Gradle](https://gradle.org/):

1. Clone the repository: `git clone https://github.com/kaaes/spotify-web-api-android.git`
2. Build: `./gradlew jar`
3. Grab the jar and put it in your project. It can be found in `build/libs/spotify-web-api-android-0.1.0.jar`

#### Dependencies

This project depends on `Retrofit 1.9.0` and `OkHttp 2.2.0`. When you build it using
the command above it creates a lean jar that doesn't contain Retrofit and OkHttp files.
To make your app work you'll need to include these dependencies in your app:

```java
compile 'com.squareup.retrofit:retrofit:1.9.0'
compile 'com.squareup.okhttp:okhttp:2.2.0'
```

It is also possible to build the project as a fat jar that contains all dependencies. To do this run:

`./gradlew jarAll`

the jar will be located in `build/libs/spotify-web-api-android-all-0.1.0.jar`


## Usage

Out of the box it uses [OkHttp](http://square.github.io/okhttp/) HTTP client and a [single thread executor](https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/Executors.html#newSingleThreadExecutor()).

```java
SpotifyApi api = new SpotifyApi();

// Most (but not all) of the Spotify Web API endpoints require authorisation.
// If you know you'll only use the ones that don't require authorisation you can skip this step
api.setAccessToken("myAccessToken");

SpotifyService spotify = api.getService();

spotify.getAlbum("2dIGnmEIy1WZIcZCFSj6i8", new Callback<Album>() {
    @Override
    public void success(Album album, Response response) {
        Log.d("Album success", album.name);
    }

    @Override
    public void failure(RetrofitError error) {
        Log.d("Album failure", error.toString());
    }
});
```

It is also possible to construct the adapter with custom parameters.

```java
final String accessToken = "myAccessToken";

RestAdapter restAdapter = new RestAdapter.Builder()
        .setEndpoint(SpotifyApi.SPOTIFY_WEB_API_ENDPOINT)
        .setRequestInterceptor(new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Authorization", "Bearer " + accessToken);
            }
        })
        .build();

SpotifyService spotify = restAdapter.create(SpotifyService.class);
```

## Obtaining Access Tokens

The most straightforward way to get the access token is to use the Authentication Library from the [Spotify Android SDK](https://github.com/spotify/android-sdk).
Detailed information how to use it can be found in the [Spotify Android SDK Authentication Guide](https://developer.spotify.com/technologies/spotify-android-sdk/android-sdk-authentication-guide/).

Feeling adventurous? You can implement the auth flow yourself, following the [Spotify Authorization Guide](https://developer.spotify.com/web-api/authorization-guide/).


## Error Handling

When using Retrofit, errors are returned as [`RetrofitError`](http://square.github.io/retrofit/javadoc/retrofit/RetrofitError.html)
objects. These objects, among others, contain HTTP status codes and their descriptions,
for example `400 - Bad Request`.
In many cases this will work well enough but in some cases Spotify Web API returns more detailed information,
for example `400 - No search query`.

To use the data returned in the response from the Web API `SpotifyCallback` object should be passed to the
request method instead of regular Retrofit's `Callback`:
```java
spotify.getMySavedTracks(new SpotifyCallback<Pager<SavedTrack>>;() {
    @Override
    public void success(Pager<SavedTrack> savedTrackPager, Response response) {
        // handle successful response
    }

    @Override
    public void failure(SpotifyError error) {
        // handle error
    }
});
```
For synchronous requests `RetrofitError` can be converted to `SpotifyError` if needed:
```java
try {
    Pager<SavedTrack> mySavedTracks = spotify.getMySavedTracks();
} catch (RetrofitError error) {
    SpotifyError spotifyError = SpotifyError.fromRetrofitError(error);
    // handle error
}
```

## Help

#### Versioning policy
We use [Semantic Versioning 2.0.0](http://semver.org/) as our versioning policy.

#### Bugs, Feature requests
Found a bug? Something that's missing? Feedback is an important part of improving the project, so please [open an issue](https://github.com/kaaes/spotify-web-api-android/issues).

#### Code
Fork this project and start working on your own feature branch. When you're done, send a Pull Request to have your suggested changes merged into the master branch by the project's collaborators. Read more about the [GitHub flow](https://guides.github.com/introduction/flow/).
