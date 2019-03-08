package com.example.tanamao.ui.activity.recipeDetailActivity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.tanamao.model.entity.recipe.Recipe;
import com.example.tanamao.utils.Constants;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class RecipeDetailsViewModel extends AndroidViewModel {

    private Recipe recipe;

    public RecipeDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void getIntentData(Intent intent) {
        recipe = intent.getParcelableExtra(Recipe.RECIPE_KEY);
    }

    public void favoriteRecipe() {
        SharedPreferences sharedPreferences = getApplication()
                .getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

        String recipesString = sharedPreferences.getString(Constants.SHARED_PREFERENCES_FAV_RECIPES, "");

        if (recipesString.equals("")) {
            return;
        }

        List<Recipe> recipeList = new Gson().fromJson(recipesString, new TypeToken<List<Recipe>>() {
        }.getType());
        dealWithFavorite(recipeList);
    }

    private void dealWithFavorite(List<Recipe> recipeList) {
        boolean found = false;
        List<Recipe> cleanList = new ArrayList<>();
        for (Recipe favRecipe : recipeList) {
            if (favRecipe.getRecipeId().equals(recipe.getRecipeId())) {
                found = true;
                continue;
            }
            cleanList.add(favRecipe);
        }
    }
}
