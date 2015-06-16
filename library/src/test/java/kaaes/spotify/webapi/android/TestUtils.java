package kaaes.spotify.webapi.android;

import com.google.gson.Gson;

import org.robolectric.Robolectric;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;

import retrofit.client.Header;
import retrofit.client.Response;
import retrofit.mime.TypedInput;

public class TestUtils {
    private static final String TEST_DATA_DIR = "/fixtures/";
    private static final int MAX_TEST_DATA_FILE_SIZE = 131072;
    private static final Gson gson = new Gson();

    private static class ResponseBody implements TypedInput {

        private final String mJson;

        private ResponseBody(String json) {
            mJson = json;
        }

        @Override
        public String mimeType() {
            return "application/json";
        }

        @Override
        public long length() {
            return mJson.length();
        }

        @Override
        public InputStream in() throws IOException {
            return new ByteArrayInputStream(mJson.getBytes(Charset.forName("UTF-8")));
        }
    }

    public static <T> Response getResponseFromModel(int statusCode, T model, Class<T> modelClass) {
        ResponseBody responseBody = new ResponseBody(gson.toJson(model, modelClass));
        return createResponse(statusCode, responseBody);
    }

    public static <T> Response getResponseFromModel(T model, Class<T> modelClass) {
        ResponseBody responseBody = new ResponseBody(gson.toJson(model, modelClass));
        return createResponse(200, responseBody);
    }

    public static <T> Response getResponseFromModel(T model, Type modelType) {
        ResponseBody responseBody = new ResponseBody(gson.toJson(model, modelType));
        return createResponse(200, responseBody);
    }

    private static Response createResponse(int statusCode, ResponseBody responseBody) {
        return new Response("", statusCode, "", new ArrayList<Header>(), responseBody);
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
