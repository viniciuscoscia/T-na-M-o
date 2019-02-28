package com.example.tanamao.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import com.example.tanamao.entity.recipe.Ingredient;
import com.example.tanamao.entity.recipe.Recipe;
import com.example.tanamao.ui.activity.mainActivity.MainActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.tanamao.repository.FirebaseRepo.INGREDIENT_COLLECTION;
import static com.example.tanamao.repository.FirebaseRepo.RECIPE_COLLECTION;

public class FirebaseUtils {

    private FirebaseRepo firebaseRepo = FirebaseRepo.getInstance();

    private Context context;

    private FirebaseUtils(){}

    public FirebaseUtils(Context context) {
        this.context = context;
    }

    public void insertIngredients(final List<Ingredient> ingredientes) {
        for (Ingredient ingredient : ingredientes) {

            FirebaseRepo.getFirestoreDB()
                    .collection(INGREDIENT_COLLECTION)
                    .whereEqualTo("name", ingredient.getName())
                    .get()
                    .addOnCompleteListener(result -> {
                        List<Ingredient> ingredients = result.getResult().toObjects(Ingredient.class);
                        if(ingredients.size() == 0) {
                            SharedPreferences sharedPreferences = context.getSharedPreferences("RECIPE_APP", Context.MODE_PRIVATE);
                            int ingredientId = sharedPreferences.getInt("lastIngredientId", -1) + 1;

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("lastIngredientId", ingredientId);
                            editor.apply();

                            ingredient.setId(ingredientId);
                            ingredient.setName(ingredient.getName().toLowerCase());
                            insertIngredient(ingredient);
                            return;
                        }
                        ingredient.setId(ingredients.get(0).getId());
                    })
                    .addOnFailureListener(error -> error.getMessage())
                    .addOnFailureListener(error -> error.getMessage());
        }
    }

    public void insertIngredient(Ingredient ingredient) {
        FirebaseRepo.getFirestoreDB().collection(INGREDIENT_COLLECTION)
                .document(ingredient.getName())
                .set(ingredient)
                .addOnCompleteListener(result -> Log.d("INGREDIENTE", ingredient.getName() + " inserido com sucesso"))
                .addOnFailureListener(Throwable::printStackTrace);
    }

    public void checkIfIngredientExists(Ingredient ingredient) {

    }

    public void retrieveDataFromFirebase() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FirebaseRepo.getFirestoreDB().collection(RECIPE_COLLECTION)
                    .whereEqualTo("category.categoryName", "Comida Asiática")
                    .get()
                    .addOnCompleteListener(result -> result.getResult()
                            .forEach(action -> Log.d("RESULTADOS", action.getData().toString())));
        }
    }

    public void insertRecipe() {
        Recipe recipe = new Recipe();
        recipe.setAverageRating(3.5F);
        recipe.setImagePath("https://firebasestorage.googleapis.com/v0/b/ta-na-mao-ea4d4.appspot.com/o/recipe%2Ffeijoada.jpg?alt=media&token=9854088c-0439-4364-8c95-1f1dc12b2961");

        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>() {{
            add(new Ingredient("feijão preto"));
            add(new Ingredient("carne seca"));
            add(new Ingredient("orelha de porco"));
            add(new Ingredient("rabo de porco"));
            add(new Ingredient("pé de porco"));
            add(new Ingredient("costelinha de porco"));
            add(new Ingredient("lombo de porco"));
            add(new Ingredient("paio"));
            add(new Ingredient("lingüiça portuguesa"));
            add(new Ingredient("cebola"));
            add(new Ingredient("cebolinha verde"));
            add(new Ingredient("louro"));
            add(new Ingredient("alho"));
            add(new Ingredient("pimenta do reino"));
            add(new Ingredient("laranja"));
            add(new Ingredient("pinga"));
            add(new Ingredient("sal"));
        }};
        insertIngredients(ingredients);
        recipe.setIngredients(ingredients);

        recipe.setRecipeId("0");
        recipe.setServings(4);
        recipe.setRecipeName("Feijoada");
        recipe.setRecipeInstructions("1 - Coloque as carnes de molho por 36 horas ou mais, vá trocando a água várias vezes, " +
                "se for ambiente quente ou verão, coloque gelo por cima ou em camadas frias.\n" +
                "2 - Coloque para cozinhar passo a passo: as carnes duras, em seguida as carnes moles.\n" +
                "3 - Quando estiver mole coloque o feijão, e retire as carnes.\n" +
                "4 - Finalmente tempere o feijão.");

        FirebaseRepo.getFirestoreDB().collection(RECIPE_COLLECTION)
                .document(recipe.getRecipeName())
                .set(recipe)
                .addOnSuccessListener(response -> Log.d(MainActivity.class.getName(), recipe.getRecipeName()))
                .addOnFailureListener(failure -> Log.d(MainActivity.class.getName(), failure.getMessage()));

    }
}
