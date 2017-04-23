package net.gumcode.webresponsetimer.utilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.gumcode.webresponsetimer.model.News;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by A. Fauzi Harismawan on 11/1/2016.
 */
public class JSONParser {

    private static final int KEY_ID = 0;
    private static final int KEY_TITLE = 1;
    private static final int KEY_CONTENT = 2;
    private static final int KEY_DATE = 3;

    public static ArrayList<News> parseWebServiceResponse(InputStream inputStream) {
        ArrayList<News> arr = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(inputStream);
            News news = new News();
            for (int i = 0; i < rootNode.size(); i++) {
                news.id = rootNode.get(i).get(KEY_ID).asInt();
                news.title = rootNode.get(i).get(KEY_TITLE).asText();
                news.content = rootNode.get(i).get(KEY_CONTENT).asText();
                news.date = rootNode.get(i).get(KEY_DATE).asLong();
            }
            return arr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
