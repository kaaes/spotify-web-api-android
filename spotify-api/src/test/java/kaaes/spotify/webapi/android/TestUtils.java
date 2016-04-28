package kaaes.spotify.webapi.android;

import com.google.gson.Gson;

import org.robolectric.Robolectric;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestUtils {
    private static final String TEST_DATA_DIR = "/fixtures/";
    private static final int MAX_TEST_DATA_FILE_SIZE = 131072;
    private static final Gson gson = new Gson();

    public static <T> Response getResponseFromModel(int statusCode, T model, Class<T> modelClass) {
        ResponseBody responseBody = createResponseBody(gson.toJson(model, modelClass));
        return createResponse(statusCode, responseBody);
    }

    public static <T> Response getResponseFromModel(T model, Class<T> modelClass) {
        ResponseBody responseBody = createResponseBody(gson.toJson(model, modelClass));
        return createResponse(200, responseBody);
    }

    public static <T> Response getResponseFromModel(T model, Type modelType) {
        ResponseBody responseBody = createResponseBody(gson.toJson(model, modelType));
        return createResponse(200, responseBody);
    }

    private static Response createResponse(int statusCode, ResponseBody responseBody) {
        return new Response.Builder()
                .request(new Request.Builder()
                        .url(SpotifyApi.SPOTIFY_WEB_API_ENDPOINT)
                        .build())
                .protocol(Protocol.HTTP_1_1)
                .code(statusCode)
                .body(responseBody)
                .build();
    }

    private static ResponseBody createResponseBody(String json) {
        return ResponseBody.create(MediaType.parse("application/json"), json);
    }

    public static Call createdMockedCall(Response response) {
        Call mockCall = mock(Call.class);
        try {
            when(mockCall.execute()).thenReturn(response);
            return mockCall;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String requestBodyToString(RequestBody requestBody) throws IOException {
        if (null != requestBody) {
            final BufferedSink sink = new Buffer();
            requestBody.writeTo(sink);
            return sink.buffer().readString(Charset.defaultCharset());
        }
        return null;
    }

    public static String readTestData(String fileName) {
        try {
            String path = Robolectric.class.getResource("/fixtures/" + fileName).toURI().getPath();
            return readFromFile(new File(path));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String readFromFile(File file) throws IOException {
        Reader reader = new FileReader(file);
        CharBuffer charBuffer = CharBuffer.allocate(MAX_TEST_DATA_FILE_SIZE);
        reader.read(charBuffer);
        charBuffer.position(0);
        return charBuffer.toString().trim();
    }
}
