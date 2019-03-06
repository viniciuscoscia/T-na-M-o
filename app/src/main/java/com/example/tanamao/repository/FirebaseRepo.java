package com.example.tanamao.repository;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class FirebaseRepo {
    public static final String RECIPE_COLLECTION = "recipes";
    public static final String INGREDIENT_COLLECTION = "ingredients";

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private static FirebaseRepo firebaseRepo;

    private FirebaseRepo(){}

    public static FirebaseRepo getInstance()  {
        if(firebaseRepo == null) {
            firebaseRepo = new FirebaseRepo();
        }
        return firebaseRepo;
    }

    public Task<QuerySnapshot> getIngredients() {
            return firebaseFirestore
                    .collection(INGREDIENT_COLLECTION)
                    .get();
    }

    public Task<QuerySnapshot> getRecipes() {
        return firebaseFirestore
                .collection(RECIPE_COLLECTION)
                .get();
    }

}