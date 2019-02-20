package com.example.tanamao.repository;

import com.example.tanamao.entity.recipe.Ingredient;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Objects;

import androidx.lifecycle.MutableLiveData;

public class FirebaseRepo {
    public static final String RECIPE_COLLECTION = "recipes";
    public static final String INGREDIENT_COLLECTION = "ingredients";

    private static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private MutableLiveData<List<Ingredient>> ingredientList = new MutableLiveData<>();
    private static FirebaseRepo firebaseRepo;

    private FirebaseRepo(){}

    public static FirebaseRepo getInstance()  {
        if(firebaseRepo == null) {
            firebaseRepo = new FirebaseRepo();
        }
        return firebaseRepo;
    }

    public MutableLiveData<List<Ingredient>> getIngredients() {
            firebaseFirestore.collection(INGREDIENT_COLLECTION)
                    .get()
                    .addOnCompleteListener(response -> {
                        if(!response.isSuccessful()) {
                            return;
                        }
                        List<Ingredient> ingredients = Objects.requireNonNull(response
                                .getResult())
                                .toObjects(Ingredient.class);

                        ingredientList.setValue(ingredients);
                    })
                    .addOnFailureListener(Throwable::printStackTrace);

            return ingredientList;
    }

    public static FirebaseFirestore getFirestoreDB() {
        return firebaseFirestore;
    }
}