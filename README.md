[![Build Status](https://travis-ci.org/kaaes/spotify-web-api-android.svg?branch=master)](https://travis-ci.org/kaaes/spotify-web-api-android)

Spotify Web API for Android
===========================

This project is a wrapper for [Spotify Web API](https://developer.spotify.com/web-api/).
It uses [Retrofit](http://square.github.io/retrofit/) to create Java interfaces from API endpoints.

Building the project
--------------------
1. Clone the repository: `git clone https://github.com/kaaes/spotify-web-api-android.git`
2. Build: `./gradlew build`
3. Grab the jar and put it in your project. It can be found in `build/libs/spotify-web-api-android-0.1.0.jar`

Using the wrapper
-----------------

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
````

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
