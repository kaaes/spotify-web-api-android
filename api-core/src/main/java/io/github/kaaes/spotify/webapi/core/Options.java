package io.github.kaaes.spotify.webapi.core;

public class Options {

    /**
     * The maximum number of objects to return.
     */
    public static final String LIMIT = "limit";

    /**
     * The index of the first playlist to return. Default: 0 (the first object).
     * Use with limit to get the next set of objects (albums, playlists, etc).
     */
    public static final String OFFSET = "offset";

    /**
     * A comma-separated list of keywords that will be used to filter the response.
     * Valid values are: {@code album}, {@code single}, {@code appears_on}, {@code compilation}
     */
    public static final String ALBUM_TYPE = "album_type";

    /**
     * The country: an ISO 3166-1 alpha-2 country code.
     * Limit the response to one particular geographical market.
     * Synonym to {@link #COUNTRY}
     */
    public static final String MARKET = "market";

    /**
     * Same as {@link #MARKET}
     */
    public static final String COUNTRY = "country";

    /**
     * The desired language, consisting of a lowercase ISO 639 language code
     * and an uppercase ISO 3166-1 alpha-2 country code, joined by an underscore.
     * For example: es_MX, meaning "Spanish (Mexico)".
     */
    public static final String LOCALE = "locale";

    /**
     * Filters for the query: a comma-separated list of the fields to return.
     * If omitted, all fields are returned.
     */
    public static final String FIELDS = "fields";

    /**
     * A timestamp in ISO 8601 format: yyyy-MM-ddTHH:mm:ss. Use this parameter to
     * specify the user's local time to get results tailored for that specific date
     * and time in the day. If not provided, the response defaults to the current UTC time
     */
    public static final String TIMESTAMP = "timestamp";
}
