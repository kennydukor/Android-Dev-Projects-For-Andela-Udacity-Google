package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private final static String TAG = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        try {
            JSONObject mainJsonObject = new JSONObject(json);

            JSONObject name = mainJsonObject.getJSONObject("name");
            String mainName = name.getString("mainName");

            JSONArray JSONArrayAlsoKnownAs = name.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = convertToListFromJsonArray(JSONArrayAlsoKnownAs);

            String placeOfOrigin = mainJsonObject.optString("placeOfOrigin");

            String description = mainJsonObject.getString("description");

            String image = mainJsonObject.getString("image");

            JSONArray JSONArrayIngredients = mainJsonObject.getJSONArray("ingredients");
            List<String> ingredients = convertToListFromJsonArray(JSONArrayIngredients);

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


    private static List<String> convertToListFromJsonArray(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }

        return list;
    }
}

