package kaaes.spotify.webapi.android;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;

import retrofit.client.Header;
import retrofit.client.Response;
import retrofit.mime.TypedInput;

public class TestUtils {
    private static final String TEST_DATA_DIR = "src/test/fixtures/";
    private static final int MAX_TEST_DATA_FILE_SIZE = 65536;
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

    public static <T> Response getResponseFromModel(T model) throws IOException {
        ResponseBody responseBody = new ResponseBody(gson.toJson(model, new TypeToken<T>(){}.getType()));
        return new Response("", 200, "", new ArrayList<Header>(), responseBody);
    }

    public static String readTestData(String fileName) throws IOException {
        return readFromFile(new File(TEST_DATA_DIR, fileName));
    }

    private static String readFromFile(File file) throws IOException {
        Reader reader = new FileReader(file);
        CharBuffer charBuffer = CharBuffer.allocate(MAX_TEST_DATA_FILE_SIZE);
        reader.read(charBuffer);
        charBuffer.position(0);
        return charBuffer.toString().trim();
    }
}
