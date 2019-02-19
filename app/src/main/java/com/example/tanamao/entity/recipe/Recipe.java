package com.example.tanamao.entity.recipe;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable {

    public static final String RECIPE_KEY = "recipe_key";

    private String recipeId;
    private String recipeName;
    private float averageRating;
    private List<Ingredient> ingredients;
    private String imagePath;
    private int servings;
    private List<String> steps;
    private List<Video> videos;
    private List<String> tags;

    public Recipe() {
    }

    public Recipe(String recipeId, String recipeName, float mediumAvaliation,
                  List<Ingredient> ingredient, String imagePath, int servings, List<String> steps,
                  List<Video> videos, List<String> tags) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.averageRating = mediumAvaliation;
        this.ingredients = ingredient;
        this.imagePath = imagePath;
        this.servings = servings;
        this.steps = steps;
        this.videos = videos;
        this.tags = tags;
    }

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

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
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

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    protected Recipe(Parcel in) {
        recipeId = in.readString();
        recipeName = in.readString();
        averageRating = in.readFloat();
        imagePath = in.readString();
        servings = in.readInt();
        steps = in.createStringArrayList();
        tags = in.createStringArrayList();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(recipeId);
        dest.writeString(recipeName);
        dest.writeFloat(averageRating);
        dest.writeString(imagePath);
        dest.writeInt(servings);
        dest.writeStringList(steps);
        dest.writeStringList(tags);
    }
}
