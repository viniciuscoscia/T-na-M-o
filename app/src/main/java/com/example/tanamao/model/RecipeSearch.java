package com.example.tanamao.model;

import com.example.tanamao.model.entity.recipe.Ingredient;
import com.example.tanamao.model.entity.recipe.Recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecipeSearch {

    public List<Recipe> recipeFilter(List<Recipe> recipeList, List<Ingredient> ingredients) {
        for (Recipe recipe : recipeList) {
            setIngredientsMatchRecipe(ingredients, recipe);
        }

        recipeList = removeZeroesMatch(recipeList);

        calculatePercentageIngredientMatch(recipeList);

        Collections.sort(recipeList, (o1, o2) -> {
            if (o1.getIngredientsPercent() == o2.getIngredientsPercent()) {
                return 0;
            }
            return o1.getIngredientsPercent() < o2.getIngredientsPercent() ? 1 : -1;
        });

        return recipeList;
    }

    private ArrayList<Recipe> removeZeroesMatch(List<Recipe> recipes) {
        ArrayList<Recipe> cleanList = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (recipe.getIngredientsMatch() > 0) {
                cleanList.add(recipe);
            }
        }

        return cleanList;
    }

    private void calculatePercentageIngredientMatch(List<Recipe> recipeList) {
        for (Recipe recipe : recipeList) {
            float matchIngredients = recipe.getIngredientsMatch();
            float size = recipe.getIngredientsTags().size();
            recipe.setIngredientsPercent((matchIngredients * 100) / size);
        }
    }

    private void setIngredientsMatchRecipe(List<Ingredient> ingredients, Recipe recipe) {
        for (Ingredient ingredientsTag : recipe.getIngredientsTags()) {
            for (Ingredient ingredient : ingredients) {
                if (ingredient.getId() == ingredientsTag.getId()) {
                    recipe.setIngredientsMatch(recipe.getIngredientsMatch() + 1);
                }
            }
        }
    }

}
