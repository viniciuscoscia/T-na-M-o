package com.example.tanamao.repository;

import android.content.Context;

import com.example.tanamao.model.entity.recipe.Ingredient;
import com.example.tanamao.model.entity.recipe.Recipe;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class FirebaseUtils {
    private FirebaseRepo firebaseRepo = FirebaseRepo.getInstance();
    private Context context;

    private FirebaseUtils(){}

    public FirebaseUtils(Context context) {
        this.context = context;
    }

//    public void insertRecipe(Recipe recipe, List<Ingredient> ingredients, AppCompatActivity activity) {
//        FirebaseRepo.getInstance()
//                .getIngredients()
//                .observe(activity, repoIngredients -> {
//            for (Ingredient ingredient : ingredients) {
//                int ingredientRepoId = hasIngredientInRepo(repoIngredients, ingredient);
//
//                if (ingredientRepoId > 0) { //if 0, didn't found in repo
//                    ingredient.setId(ingredientRepoId);
//                    continue;
//                }
//
//                SharedPreferences sharedPreferences = context.getSharedPreferences("RECIPE_APP", Context.MODE_PRIVATE);
//                int ingredientId = sharedPreferences.getInt("lastIngredientId", -1) + 1;
//
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putInt("lastIngredientId", ingredientId);
//                editor.apply();
//
//                ingredient.setId(ingredientId);
//                insertIngredientToIngredientCollection(ingredient);
//            }
//            recipe.setIngredientsTags(ingredients);
//            executeRecipeInsertion(recipe);
//        });
//    }
//
//    private int hasIngredientInRepo(List<Ingredient> repoIngredients, Ingredient ingredient) {
//        for (Ingredient repoIngredient : repoIngredients) {
//            if(repoIngredient.getName().equals(ingredient.getName())) return repoIngredient.getId();
//        }
//        return 0;
//    }
//
//    private void executeRecipeInsertion(Recipe recipe) {
//        FirebaseRepo.getFirestoreDB().collection(RECIPE_COLLECTION)
//                .document(recipe.getRecipeName())
//                .set(recipe)
//                .addOnSuccessListener(response -> {
//                    Log.d("RECEITA", recipe.getRecipeName() + " inserida com sucesso");
//                })
//                .addOnFailureListener(failure -> Log.d(MainActivity.class.getName(), failure.getMessage()));
//    }
//
//    public void insertIngredientToIngredientCollection(Ingredient ingredient) {
//        FirebaseRepo.getInstance().getIngredients()
//                .document(ingredient.getName())
//                .set(ingredient)
//                .addOnCompleteListener(result -> Log.d("INGREDIENTE", ingredient.getName() + " inserido com sucesso"))
//                .addOnFailureListener(Throwable::printStackTrace);
//    }
//
//    public Task<QuerySnapshot> getRecipe(Recipe recipe) {
//        return FirebaseRepo.getFirestoreDB()
//                .collection(recipe.getRecipeName())
//                .get();
//    }
//
//    public Task<QuerySnapshot> getRecipeById(String id) {
//        return FirebaseRepo
//                .getFirestoreDB()
//                .collection(RECIPE_COLLECTION)
//                .whereEqualTo("recipeId", id)
//                .get();
//    }
//
//    public Task<QuerySnapshot> getRecipes() {
//        return FirebaseRepo
//                .getFirestoreDB()
//                .collection(RECIPE_COLLECTION)
//                .get();
//    }

    private void insertRecipe(AppCompatActivity activity) {
        Recipe recipe = new Recipe();
        recipe.setAverageRating(5F);
        recipe.setImagePath("https://firebasestorage.googleapis.com/v0/b/ta-na-mao-ea4d4.appspot.com/o/recipe%2F284991_original.jpg?alt=media&token=03a7acd9-647d-49bb-bf78-769560add860");

        List<Ingredient> ingredientsTags = new ArrayList<Ingredient>() {{
            add(new Ingredient("alcatra"));
            add(new Ingredient("bacon"));
            add(new Ingredient("óleo"));
            add(new Ingredient("cebola"));
            add(new Ingredient("purê de tomate"));
            add(new Ingredient("manjerona"));
            add(new Ingredient("cheiro verde"));
            add(new Ingredient("sal"));
            add(new Ingredient("pimenta-do-reino"));
        }};
        for (Ingredient ingredient : ingredientsTags) {
            ingredient.setName(ingredient.getName().toLowerCase());
        }

        List<String> ingredients = new ArrayList<String>() {{
            add("6 bifes de alcatra");
            add("6 tiras de bacon");
            add("óleo");
            add("1 cebola picada");
            add("1 lata de purê de tomate");
            add("manjerona");
            add("cheiro verde");
            add("sal");
            add("pimenta do reino");
        }};

        recipe.setIngredients(ingredients);
        recipe.setRecipeId("4");
        recipe.setServings(6);
        recipe.setRecipeName("Bife a Rolê");
        recipe.setRecipeInstructions("1 - Tempere os bifes e coloque a fartia de bacon no meio.\n" +
                "2 - Enrole e prenda com um palito.\n" +
                "3 - Em uma panela de pressão frite os bifes, todos juntos, virando de vez em quando.\n" +
                "4 - Frite em seguida a cebola.\n" +
                "5 - Acrescente o purê de tomate, a manjerona, o sal e a pimenta.\n" +
                "6 - Coloque água e deixe cozinhar por 30 minutos.\n" +
                "7 - Abra a panela, veja se o molho está denso e a carne macia.\n" +
                "8 - Ferva mais um pouco com a panela aberta.\n" +
                "9 - Acrescente o cheiro verde.\n" +
                "10 - Sirva com arroz e legumes.\n");

//        insertRecipe(recipe, ingredientsTags, activity);
    }
}
