package com.example.tanamao.database;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import androidx.room.TypeConverter;

public class IngredientListTypeConverter {
    private static Gson gson = new Gson();

    @TypeConverter
    public static List<String> stringToIngredientList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<String>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String ingredientListToString(List<String> ingredientList) {
        return gson.toJson(ingredientList);
    }
}
