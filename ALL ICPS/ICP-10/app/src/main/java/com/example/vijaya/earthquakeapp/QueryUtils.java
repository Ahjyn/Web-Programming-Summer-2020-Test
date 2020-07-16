package com.example.vijaya.earthquakeapp;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the USGS dataset and return a list of {@link Earthquake} objects.
     */
    public static List<Earthquake> fetchEarthquakeData2(String requestUrl) {
        // An empty ArrayList that we can start adding earthquakes to
        List<Earthquake> earthquakes = new ArrayList<>();
        //  URL object to store the url for a given string
        URL url = null;
        // A string to store the response obtained from rest call in the form of string
        String jsonResponse = "";
        StringBuilder string1 = new StringBuilder();
        try {
            //TODO: 1. Create a URL from the requestUrl string and make a GET request to it
            url = new URL(requestUrl);

            //TODO: 2. Read from the Url Connection and store it as a string(jsonResponse)
            HttpURLConnection linker = (HttpURLConnection) url.openConnection();
            InputStream inputStream = new BufferedInputStream(linker.getInputStream()); //create input stream to read in
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); //read in values
            String into;
            while ((into = bufferedReader.readLine()) != null) {  //append values to string
                string1.append(into);
            }
            jsonResponse = string1.toString();

            /*TODO: 3. Parse the jsonResponse string obtained in step 2 above into JSONObject to extract the values of
                        "mag","place","time","url" for every earth quake and create corresponding Earthquake objects with them
                        Add each earthquake object to the list(earthquakes) and return it.
            */

            try {
                JSONObject value = new JSONObject(jsonResponse);
                JSONArray denom = value.getJSONArray("features");
                JSONObject anotherValue;
                JSONObject finalValue = new JSONObject(denom.getString(0));

                for (int i = 0; i < denom.length(); i++) {
                    finalValue = new JSONObject(denom.getString(i));
                    anotherValue = new JSONObject(finalValue.getString("properties"));
                    Earthquake e = new Earthquake(Double.parseDouble(anotherValue.getString("mag")), anotherValue.getString("place"), Long.parseLong(anotherValue.getString("time")), anotherValue.getString("url"));
                    earthquakes.add(e);
                    Log.d("App", anotherValue.getString("mag"));
                }

            } catch (Throwable t) {
                Log.e("App", "parse JSON problem: \"" + jsonResponse + "\"");
            }

            // Return the list of earthquakes

        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception:  ", e);
        }
        // Return the list of earthquakes
        return earthquakes;
    }
}
