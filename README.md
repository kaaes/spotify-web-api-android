[![Build Status](https://travis-ci.org/kaaes/spotify-web-api-android.svg?branch=master)](https://travis-ci.org/kaaes/spotify-web-api-android)
[![Release](https://img.shields.io/github/release/kaaes/spotify-web-api-android.svg?label=maven)](https://jitpack.io/#kaaes/spotify-web-api-android)

# Spotify Web API for Android

This project is a wrapper for the [Spotify Web API](https://developer.spotify.com/web-api/).
It uses [Retrofit](http://square.github.io/retrofit/) to create Java interfaces from API endpoints.

This library supports both Retrofit 1.9 and Retrofit 2.0 (experimental).

## Integrating into your project

This library is available in [JitPack.io](https://jitpack.io/) repository.
To use it make sure that repository's url is added to the `build.gradle` file in your app:

```groovy

repositories {
    mavenCentral()
    maven { url "https://jitpack.io" }
}

dependencies {
    // To import for Retrofit 1.9
    compile 'com.github.kaaes.spotify-web-api-android:api-retrofit:0.4.1'

    // To import for Retrofit 2.0 (experimantal)
    compile 'com.github.kaaes.spotify-web-api-android:api-retrofit2:0.4.1'

    // Other dependencies your app might use
}
```

## Using with Retrofit 2.0

Basic usage

```java
SpotifyService spotifyService = Spotify.createAuthenticatedService(accessToken);

// Access token is strongly advised but optional for certain endpoints
// so if you know you'll only use the ones that don't require authorisation
// you can use unauthenticated service instead:

SpotifyService spotifyService = Spotify.createNotAuthenticatedService()

Call<Album> call = spotifyService.getAlbum("2dIGnmEIy1WZIcZCFSj6i8");
Response<Album> response = call.execute();
Album album = response.body();
```

If default configuration doesn't work for you, you can create your own instance:

```java
Retrofit retrofit = new Retrofit.Builder()
        .client(customHttpClient)
        .addConverterFactory(customConverterFactory)
        .baseUrl(Config.API_URL)
        .build();

SpotifyService spotifyService = retrofit.create(SpotifyService.class);
```

## Using with Retrofit 1.9

Basic usage

```java
SpotifyService spotifyService = Spotify.createAuthenticatedService(accessToken);

// Access token is strongly advised but optional for certain endpoints
// so if you know you'll only use the ones that don't require authorisation
// you can use unauthenticated service instead:

SpotifyService spotifyService = Spotify.createNotAuthenticatedService()

Album album = spotifyService.getAlbum("2dIGnmEIy1WZIcZCFSj6i8");
```

If default configuration doesn't work for you, you can create your own instance:

```java
RestAdapter adapter = new RestAdapter.Builder()
        .setEndpoint(Config.API_URL)
        .setRequestInterceptor(customRequestInterceptor)
        .setExecutors(customHttpExecutor, customCallbackExecutor)
        .build();

SpotifyService spotifyService = adapter.create(SpotifyService.class);
```

## Obtaining Access Tokens

The most straightforward way to get the access token is to use the Authentication Library from the [Spotify Android SDK](https://github.com/spotify/android-sdk).
Detailed information how to use it can be found in the [Spotify Android SDK Authentication Guide](https://developer.spotify.com/technologies/spotify-android-sdk/android-sdk-authentication-guide/).

Feeling adventurous? You can implement the auth flow yourself, following the [Spotify Authorization Guide](https://developer.spotify.com/web-api/authorization-guide/).


## Error Handling

### With Retrofit 1.9

When using Retrofit, errors are returned as [`RetrofitError`](http://square.github.io/retrofit/javadoc/retrofit/RetrofitError.html)
objects. These objects, among others, contain HTTP status codes and their descriptions,
for example `400 - Bad Request`.
In many cases this will work well enough but in some cases Spotify Web API returns more detailed information,
for example `400 - No search query`.

To use the data returned in the response from the Web API `SpotifyCallback` object should be passed to the
request method instead of regular Retrofit's `Callback`:
```java
spotifyService.getMySavedTracks(new SpotifyCallback<Pager<SavedTrack>>() {
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
    Pager<SavedTrack> mySavedTracks = spotifyService.getMySavedTracks();
} catch (RetrofitError error) {
    SpotifyError spotifyError = SpotifyError.fromRetrofitError(error);
    // handle error
}
```

### With Retrofit 2.0

To use the data returned in the response from the Web API `SpotifyCallback` object should be passed to the
request method instead of regular Retrofit's `Callback`:

```java
Call<TracksPager> call = spotifyService.searchTracks(query, options);

call.enqueue(new SpotifyCallback<TracksPager>() {
     @Override
    public void onResponse(Call<TracksPager> call, Response<TracksPager> response, TracksPager payload) {
        // handle successful response
    }

    @Override
    public void onFailure(Call<TracksPager> call, SpotifyError error) {
        // handle error response
    }
});
```

For synchronous callse `RetrofitError` can be converted to `SpotifyError` if needed:

```java
Call<TracksPager> call = spotifyService.searchTracks(query, options);
Response<TracksPager> response = call.execute();
SpotifyError.fromResponse(response);
```

## Help

#### Versioning policy
We use [Semantic Versioning 2.0.0](http://semver.org/) as our versioning policy.

#### Bugs, Feature requests
Found a bug? Something that's missing? Feedback is an important part of improving the project, so please [open an issue](https://github.com/kaaes/spotify-web-api-android/issues).

#### Code
Fork this project and start working on your own feature branch. When you're done, send a Pull Request to have your suggested changes merged into the master branch by the project's collaborators. Read more about the [GitHub flow](https://guides.github.com/introduction/flow/).
