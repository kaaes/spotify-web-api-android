package kaaes.spotify.webapi.android.models;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#track-object-full">Track object model</a>
 */
public class Track {
    public Album album;
    public List<Artist> artists;
    public List<String> available_markets;
    public int disc_number;
    public long duration_ms;
    public boolean explicit;
    public Map<String,String> external_ids;
    public Map<String,String> external_urls;
    public String href;
	public String id;
	public String name;
	public int popularity;
    public String preview_url;
    public int track_number;
    public String type;
    public String uri;

	/**
	 * This class handles conversion when tracks are contained in a Playlist.
	 */
	public static class TrackTypeAdapter implements JsonDeserializer<Track> {

		@Override
		public Track deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			try {
				JsonObject jsonObject = json.getAsJsonObject();
				if(jsonObject.has("track")) {
					return new Gson().fromJson(json.getAsJsonObject().get("track"), typeOfT);
				} else {
					return new Gson().fromJson(json, typeOfT);
				}
			} catch (JsonSyntaxException e) {
				return new Gson().fromJson(json, typeOfT);
			}
		}
	}
}
