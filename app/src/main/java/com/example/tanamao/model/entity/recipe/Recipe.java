package com.example.tanamao.model.entity.recipe;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.tanamao.database.IngredientListTypeConverter;
import com.example.tanamao.database.IngredientTagsTypeConverter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "favorite_recipes")
public class Recipe implements Parcelable {

    public static final String RECIPE_KEY = "recipe_key";

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String recipeId;
    @ColumnInfo(name = "recipe_name")
    private String recipeName;
    @ColumnInfo(name = "average_rating")
    private float averageRating;
    @ColumnInfo(name = "image_path")
    private String imagePath;
    private int servings;
    @ColumnInfo(name = "recipe_instructions")
    private String recipeInstructions;
    @ColumnInfo(name = "ingredients_tags")
    @TypeConverters(IngredientTagsTypeConverter.class)
    private List<Ingredient> ingredientsTags;
    @TypeConverters(IngredientListTypeConverter.class)
    private List<String> ingredients;
    @Ignore
    private int ingredientsMatch = 0;
    @Ignore
    private double ingredientsPercent = 0.0;

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

    public Recipe() {
    }

    public Recipe(String recipeId, String recipeName, float averageRating,
                  List<Ingredient> ingredientsTags, List<String> ingredients, String imagePath,
                  int servings, String recipeInstructions, int ingredientsMatch,
                  double ingredientsPercent) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.averageRating = averageRating;
        this.ingredientsTags = ingredientsTags;
        this.ingredients = ingredients;
        this.imagePath = imagePath;
        this.servings = servings;
        this.recipeInstructions = recipeInstructions;
        this.ingredientsMatch = ingredientsMatch;
        this.ingredientsPercent = ingredientsPercent;
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

    public int getIngredientsMatch() {
        return ingredientsMatch;
    }

    public void setIngredientsMatch(int ingredientsMatch) {
        this.ingredientsMatch = ingredientsMatch;
    }

    @Override
    public int describeContents() {
        return 0;
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
        ingredientsMatch = in.readInt();
        ingredientsPercent = in.readDouble();
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
        dest.writeInt(ingredientsMatch);
        dest.writeDouble(ingredientsPercent);
    }

    public double getIngredientsPercent() {
        return ingredientsPercent;
    }

    public void setIngredientsPercent(double ingredientsPercent) {
        this.ingredientsPercent = ingredientsPercent;
    }
}
