package com.example.tanamao.database;

import com.example.tanamao.model.entity.recipe.Ingredient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import androidx.room.TypeConverter;

public class IngredientTagsTypeConverter {
    private static Gson gson = new Gson();

    @TypeConverter
    public static List<Ingredient> stringToIngredientList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Ingredient>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String ingredientListToString(List<Ingredient> ingredientList) {
        return gson.toJson(ingredientList);
    }
}
