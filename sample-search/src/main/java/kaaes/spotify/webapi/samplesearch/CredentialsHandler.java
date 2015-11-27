package kaaes.spotify.webapi.samplesearch;

import android.content.Context;
import android.content.SharedPreferences;

public class CredentialsHandler {

    public static void setToken(Context context, String token, long expiresIn) {
        Context appContext = context.getApplicationContext();

        long now = System.currentTimeMillis();
        long expiresAt = now + expiresIn * 1000;

        SharedPreferences sharedPref = getSharedPreferences(appContext);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(appContext.getString(R.string.access_token), token);
        editor.putLong(appContext.getString(R.string.expires_at), expiresAt);
        editor.apply();
    }

    private static SharedPreferences getSharedPreferences(Context appContext) {
        return appContext.getSharedPreferences(appContext.getString(R.string.access_token_prefs), Context.MODE_PRIVATE);
    }

    public static String getToken(Context context) {
        Context appContext = context.getApplicationContext();
        SharedPreferences sharedPref = getSharedPreferences(appContext);

        String token = sharedPref.getString(appContext.getString(R.string.access_token), null);
        long expiresAt = sharedPref.getLong(appContext.getString(R.string.expires_at), 0l);

        if (token == null || expiresAt < System.currentTimeMillis()) {
            return null;
        }

        return token;
    }
}
