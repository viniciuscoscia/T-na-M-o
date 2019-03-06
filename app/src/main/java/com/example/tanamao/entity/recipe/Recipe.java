package com.example.tanamao.entity.recipe;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Recipe implements Parcelable {

    public static final String RECIPE_KEY = "recipe_key";

    private String recipeId;
    private String recipeName;
    private float averageRating;
    private String imagePath;
    private int servings;
    private String recipeInstructions;
    private List<Ingredient> ingredientsTags;
    private List<String> ingredients;

    public Recipe() {
    }

    public Recipe(String recipeId, String recipeName, float averageRating,
                  List<Ingredient> ingredientsTags, List<String> ingredients, String imagePath,
                  int servings, String recipeInstructions) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.averageRating = averageRating;
        this.ingredientsTags = ingredientsTags;
        this.ingredients = ingredients;
        this.imagePath = imagePath;
        this.servings = servings;
        this.recipeInstructions = recipeInstructions;
    }

    protected Recipe(Parcel in) {
        recipeId = in.readString();
        recipeName = in.readString();
        averageRating = in.readFloat();
        ingredientsTags = in.createTypedArrayList(Ingredient.CREATOR);
        ingredients = in.createStringArrayList();
        imagePath = in.readString();
        servings = in.readInt();
        recipeInstructions = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    public List<Ingredient> getIngredientsTags() {
        return ingredientsTags;
    }

    public void setIngredientsTags(List<Ingredient> ingredientsTags) {
        this.ingredientsTags = ingredientsTags;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getRecipeInstructions() {
        return recipeInstructions;
    }

    public void setRecipeInstructions(String recipeInstructions) {
        this.recipeInstructions = recipeInstructions;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(recipeId);
        dest.writeString(recipeName);
        dest.writeFloat(averageRating);
        dest.writeTypedList(ingredientsTags);
        dest.writeStringList(ingredients);
        dest.writeString(imagePath);
        dest.writeInt(servings);
        dest.writeString(recipeInstructions);
    }
}
