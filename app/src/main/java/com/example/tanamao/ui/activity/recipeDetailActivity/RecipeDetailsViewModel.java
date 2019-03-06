package com.example.tanamao.ui.activity.recipeDetailActivity;

import android.app.Application;
import android.content.Intent;

import com.example.tanamao.entity.recipe.Recipe;

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
}
