package com.example.tanamao.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import com.example.tanamao.entity.recipe.Ingredient;
import com.example.tanamao.entity.recipe.Recipe;
import com.example.tanamao.entity.recipe.Video;
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

    public void insertIngredients(List<Ingredient> ingredientes) {
//        List<Ingredient> ingredientes = new ArrayList<Ingredient>(){{
//            add(new Ingredient("0","alho"));
//            add(new Ingredient("1","carne"));
//            add(new Ingredient("2","mandioca"));
//            add(new Ingredient("3","arroz"));
//            add(new Ingredient("4","feijão"));
//            add(new Ingredient("5","cebola"));
//            add(new Ingredient("6","coxa de frango"));
//            add(new Ingredient("7","bisteca"));
//            add(new Ingredient("8","costela suína"));
//            add(new Ingredient("9","salsicha"));
//            add(new Ingredient("10","batata"));
//            add(new Ingredient("11","asa de frango"));
//            add(new Ingredient("12","peito de frango"));
//            add(new Ingredient("13","frango inteiro"));
//            add(new Ingredient("14","tomate"));
//            add(new Ingredient("15","vinho"));
//            add(new Ingredient("16","azeitona"));
//            add(new Ingredient("17","palmito"));
//            add(new Ingredient("18","camarão"));
//            add(new Ingredient("19","lula"));
//            add(new Ingredient("20","polvo"));
//            add(new Ingredient("21","cachaça"));
//            add(new Ingredient("22","vodka"));
//            add(new Ingredient("23","rum"));
//            add(new Ingredient("24","vodka"));
//            add(new Ingredient("25","maracujá"));
//            add(new Ingredient("26","uva"));
//            add(new Ingredient("27","morango"));
//            add(new Ingredient("28","abacaxi"));
//            add(new Ingredient("29","chocolate"));
//            add(new Ingredient("30","chocolate branco"));
//            add(new Ingredient("31","amarula"));
//            add(new Ingredient("32","leite condensado"));
//            add(new Ingredient("33","farinha de trigo"));
//            add(new Ingredient("34","farinha de rosca"));
//            add(new Ingredient("35","farinha de mandioca"));
//            add(new Ingredient("36","fermento"));
//            add(new Ingredient("37","leite"));
//            add(new Ingredient("38","limão"));
//            add(new Ingredient("39","kiwi"));
//            add(new Ingredient("40","whisky"));
//            add(new Ingredient("41","cereja"));
//            add(new Ingredient("42","chuchu"));
//            add(new Ingredient("43","abóbora"));
//            add(new Ingredient("44","abobrinha"));
//            add(new Ingredient("45","mandioquinha"));
//            add(new Ingredient("46","cenoura"));
//            add(new Ingredient("47","óleo"));
//            add(new Ingredient("48","açúcar"));
//            add(new Ingredient("49","sal"));
//            add(new Ingredient("50","manteiga"));
//            add(new Ingredient("51","chocolate em pó"));
//            add(new Ingredient("52","achocolatado"));
//            add(new Ingredient("53","presunto"));
//            add(new Ingredient("54","queijo prato"));
//            add(new Ingredient("55","queijo cheddar"));
//            add(new Ingredient("56","queijo mussarela"));
//            add(new Ingredient("57","manjericão"));
//            add(new Ingredient("58","cheiro verde"));
//            add(new Ingredient("59","pimenta-do-reino"));
//            add(new Ingredient("60","canela em pó"));
//            add(new Ingredient("61","fermento biológico"));
//            add(new Ingredient("62","leite em pó"));
//            add(new Ingredient("63","café"));
//            add(new Ingredient("64","azeite"));
//            add(new Ingredient("65","carne moída"));
//            add(new Ingredient("66","maçã"));
//            add(new Ingredient("67","fubá"));
//            add(new Ingredient("68","goiabada "));
//            add(new Ingredient("69","erva-doce"));
//            add(new Ingredient("70","margarina"));
//            add(new Ingredient("71","maisena"));
//            add(new Ingredient("72","queijo ralado"));
//            add(new Ingredient("73","lentilha"));
//            add(new Ingredient("74","caldo de carne"));
//            add(new Ingredient("75","linguiça calabresa"));
//            add(new Ingredient("76","salsinha"));
//            add(new Ingredient("77","milho"));
//            add(new Ingredient("78","caldo de legumes"));
//            add(new Ingredient("79","espinafre"));
//            add(new Ingredient("80","cebolinha"));
//            add(new Ingredient("81","espaguete"));
//            add(new Ingredient("82","brócolis"));
//            add(new Ingredient("83","couve-flor"));
//            add(new Ingredient("84","molho shoyu"));
//            add(new Ingredient("85","champignon"));
//            add(new Ingredient("86","acelga"));
//            add(new Ingredient("87","requeijão"));
//            add(new Ingredient("88","moranga"));
//            add(new Ingredient("89","ketchup"));
//            add(new Ingredient("90","mostarda"));
//            add(new Ingredient("91","maionese"));
//            add(new Ingredient("92","leite de coco"));
//            add(new Ingredient("93","bacalhau"));
//            add(new Ingredient("94","alfavaca"));
//            add(new Ingredient("95","pimenta branca"));
//            add(new Ingredient("96","pimentão vermelho"));
//            add(new Ingredient("97","pimentão verde"));
//            add(new Ingredient("98","pimentão amarelo"));
//            add(new Ingredient("99","orégano"));
//            add(new Ingredient("100","catupiry"));
//            add(new Ingredient("101","creme de leite"));
//        }};

        for (Ingredient ingredient : ingredientes) {

            checkIfIngredientExists(ingredient);

            FirebaseRepo.getFirestoreDB().collection(INGREDIENT_COLLECTION)
                    .document(ingredient.getName())
                    .set(ingredient)
                    .addOnCompleteListener(result -> Log.d("INGREDIENTE", ingredient.getName() + " inserido com sucesso"))
                    .addOnFailureListener(Throwable::printStackTrace);
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

                        ingredient.setId(ingredientId);
                        ingredient.setName(ingredient.getName().toLowerCase());
                        insertIngredient(ingredient);
                    }
                })
                .addOnFailureListener(error -> error.getMessage())
                .addOnFailureListener(error -> error.getMessage());
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
        recipe.setIngredients(new ArrayList<Ingredient>(){{
            add(new Ingredient( "feijão preto"));
            add(new Ingredient( "carne seca"));
            add(new Ingredient( "orelha de porco"));
            add(new Ingredient( "rabo de porco"));
            add(new Ingredient( "pé de porco"));
            add(new Ingredient( "costelinha de porco"));
            add(new Ingredient( "lombo de porco"));
            add(new Ingredient( "paio"));
            add(new Ingredient( "lingüiça portuguesa"));
            add(new Ingredient( "cebola"));
            add(new Ingredient( "cebolinha verde"));
            add(new Ingredient( "louro"));
            add(new Ingredient( "alho"));
            add(new Ingredient( "pimenta do reino"));
            add(new Ingredient( "laranja"));
            add(new Ingredient( "pinga"));
            add(new Ingredient( "sal"));
        }});
        recipe.setRecipeId("0");
        recipe.setVideos(new ArrayList<Video>(){{
            add(new Video("www.exemplo.com/idVideo0", "exemplo.com"));
        }});
        recipe.setServings(2);
        recipe.setRecipeName("RecipeName0");
        recipe.setTags(new ArrayList<String>(){{
            add("Japonesa");
        }});
        recipe.setSteps(new ArrayList<String>(){{
            add("Passo 0");
        }});

        FirebaseRepo.getFirestoreDB().collection(RECIPE_COLLECTION)
                .document(recipe.getRecipeName())
                .set(recipe)
                .addOnSuccessListener(response -> Log.d(MainActivity.class.getName(), recipe.getRecipeName()))
                .addOnFailureListener(failure -> Log.d(MainActivity.class.getName(), failure.getMessage()));

    }
}
