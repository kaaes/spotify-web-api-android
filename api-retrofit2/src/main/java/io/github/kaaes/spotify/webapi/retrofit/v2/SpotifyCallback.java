package io.github.kaaes.spotify.webapi.retrofit.v2;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class SpotifyCallback<T> implements Callback<T> {

    public abstract void onResponse(Call<T> call, Response<T> response, T payload);

    public abstract void onFailure(Call<T> call, SpotifyError error);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            onResponse(call, response, response.body());
        } else {
            onFailure(call, SpotifyError.fromResponse(response));
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onFailure(call, new SpotifyError(t));
    }
}
