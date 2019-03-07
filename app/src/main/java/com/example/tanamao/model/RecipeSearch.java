package com.example.tanamao.model;

import com.example.tanamao.model.entity.recipe.Ingredient;
import com.example.tanamao.model.entity.recipe.Recipe;

import java.util.Collections;
import java.util.List;

public class RecipeSearch {

    public void recipeFilter(List<Recipe> recipeList, List<Ingredient> ingredients) {
        for (Recipe recipe : recipeList) {
            setIngredientsMatchRecipe(ingredients, recipe);
        }

        Collections.sort(recipeList, (o1, o2) -> {
            if (o1.getIngredientsMatch() == o2.getIngredientsMatch()) {
                return 0;
            }
            return o1.getIngredientsMatch() < o2.getIngredientsMatch() ? 1 : -1;
        });
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
