package com.example.tanamao.ui.activity.recipeDetailActivity;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.tanamao.database.AppDataBase;
import com.example.tanamao.database.FavoritesDAO;
import com.example.tanamao.model.entity.recipe.Recipe;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class RecipeDetailsViewModel extends AndroidViewModel {

    private Recipe recipe;
    private MutableLiveData<Boolean> isRecipeFavorite = new MutableLiveData<>();
    private FavoritesDAO dao;

    public RecipeDetailsViewModel(@NonNull Application application) {
        super(application);
        dao = AppDataBase.getInstance(getApplication()).favoritesDao();
        isRecipeFavorite.setValue(false);
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void getIntentData(Intent intent) {
        recipe = intent.getParcelableExtra(Recipe.RECIPE_KEY);
        checkRecipeFavorite();
    }

    public void favoriteUnfavoriteRecipe() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                if (isRecipeFavorite.getValue()) {
                    dao.deleteFavoriteRecipe(recipe);
                } else {
                    dao.insertRecipe(recipe);
                }
                checkRecipeFavorite();
                return null;
            }

        }.execute();

    }

    public void checkRecipeFavorite() {
        AppDataBase appDataBase = AppDataBase.getInstance(getApplication());

        new AsyncTask<Recipe, Recipe, Boolean>() {
            @Override
            protected Boolean doInBackground(Recipe... recipes) {
                if (appDataBase.favoritesDao().loadFavoriteRecipeById(recipe.getRecipeId()) == null)
                    return false;
                else
                    return true;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                isRecipeFavorite.setValue(result);
            }
        }.execute();
    }

    public MutableLiveData<Boolean> getIsRecipeFavorite() {
        return isRecipeFavorite;
    }
}
