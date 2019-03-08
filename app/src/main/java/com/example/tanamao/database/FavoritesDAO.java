package com.example.tanamao.database;


import com.example.tanamao.model.entity.recipe.Recipe;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface FavoritesDAO {
    @Query("SELECT * FROM favorite_recipes ORDER BY id")
    List<Recipe> loadFavoriteRecipes();

    @Query("SELECT * FROM favorite_recipes ORDER BY id")
    LiveData<List<Recipe>> loadFavoriteRecipesLiveData();

    @Insert
    void insertRecipe(Recipe recipe);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavoriteRecipe(Recipe recipe);

    @Delete
    void deleteFavoriteRecipe(Recipe recipe);

    @Query("SELECT * FROM favorite_recipes WHERE id = :id")
    Recipe loadFavoriteRecipeById(String id);
}
