package net.gumcode.webresponsetimer.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by A. Fauzi Harismawan on 11/1/2016.
 */
public class HTTPHelper {

    public static InputStream sendGETRequest(String ur) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(ur);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(10000);
            connection.setUseCaches(false);

            return connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return null;
    }
}
