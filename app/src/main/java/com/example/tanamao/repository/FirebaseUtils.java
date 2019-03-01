package com.example.tanamao.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.tanamao.entity.recipe.Ingredient;
import com.example.tanamao.entity.recipe.Recipe;
import com.example.tanamao.ui.activity.mainActivity.MainActivity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.tanamao.repository.FirebaseRepo.INGREDIENT_COLLECTION;
import static com.example.tanamao.repository.FirebaseRepo.RECIPE_COLLECTION;

public class FirebaseUtils {
    private FirebaseRepo firebaseRepo = FirebaseRepo.getInstance();
    private Context context;

    private FirebaseUtils(){}

    public FirebaseUtils(Context context) {
        this.context = context;
    }

    public void insertRecipe(Recipe recipe, List<Ingredient> ingredients, AppCompatActivity activity) {
        FirebaseRepo.getInstance().getIngredients().observe(activity, repoIngredients -> {
            for (Ingredient ingredient : ingredients) {
                if (repoIngredients.contains(ingredient)) {
                    Ingredient repoIngredient = repoIngredients.get(repoIngredients.indexOf(ingredient));
                    ingredient.setId(repoIngredient.getId());
                    return;
                }
                SharedPreferences sharedPreferences = context.getSharedPreferences("RECIPE_APP", Context.MODE_PRIVATE);
                int ingredientId = sharedPreferences.getInt("lastIngredientId", -1) + 1;

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("lastIngredientId", ingredientId);
                editor.apply();

                ingredient.setId(ingredientId);
                insertIngredientToIngredientCollection(ingredient);
            }
            recipe.setIngredientsTags(ingredients);
            executeRecipeInsertion(recipe);
        });
    }

    private void executeRecipeInsertion(Recipe recipe) {
        FirebaseRepo.getFirestoreDB().collection(RECIPE_COLLECTION)
                .document(recipe.getRecipeName())
                .set(recipe)
                .addOnSuccessListener(response -> {
                    Log.d("RECEITA", recipe.getRecipeName() + " inserida com sucesso");
                })
                .addOnFailureListener(failure -> Log.d(MainActivity.class.getName(), failure.getMessage()));
    }

    public void insertIngredientToIngredientCollection(Ingredient ingredient) {
        FirebaseRepo.getFirestoreDB()
                .collection(INGREDIENT_COLLECTION)
                .document(ingredient.getName())
                .set(ingredient)
                .addOnCompleteListener(result -> Log.d("INGREDIENTE", ingredient.getName() + " inserido com sucesso"))
                .addOnFailureListener(Throwable::printStackTrace);
    }

    public Task<QuerySnapshot> getRecipe(Recipe recipe) {
        return FirebaseRepo.getFirestoreDB()
                .collection(recipe.getRecipeName())
                .get();
    }

}
