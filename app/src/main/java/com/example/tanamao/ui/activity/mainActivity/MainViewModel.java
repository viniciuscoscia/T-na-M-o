package com.example.tanamao.ui.activity.mainActivity;

import android.app.Application;

import com.example.tanamao.entity.recipe.Ingredient;
import com.example.tanamao.entity.recipe.Recipe;
import com.example.tanamao.repository.FirebaseRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class MainViewModel extends AndroidViewModel {

    private FirebaseRepo firebaseRepo = FirebaseRepo.getInstance();
    private MutableLiveData<List<Ingredient>> availableIngredients;
    private MutableLiveData<List<Ingredient>> userIngredients;
    private MutableLiveData<List<Recipe>> recipeList;

    public MainViewModel(@NonNull Application application) {
        super(application);
        availableIngredients = new MutableLiveData<>();
        availableIngredients.setValue(new ArrayList<>());
        userIngredients = new MutableLiveData<>();
        userIngredients.setValue(new ArrayList<>());
        recipeList = new MutableLiveData<>();
        recipeList.setValue(new ArrayList<>());
        loadIngredients();
    }

    private void removeListItem(MutableLiveData<List<Ingredient>> mutableList,
                                Ingredient ingredient) {
        List<Ingredient> newList = new ArrayList<>();

        for (Ingredient item : Objects.requireNonNull(mutableList.getValue())) {
            if (item.getId() == ingredient.getId()) {
                continue;
            }
            newList.add(item);
        }
        mutableList.setValue(newList);
    }

    private MutableLiveData<List<Ingredient>> loadIngredients() {
        firebaseRepo
                .getIngredients()
                .addOnCompleteListener(response -> {
                    if (!response.isSuccessful()) {
                        return;
                    }
                    List<Ingredient> ingredients = Objects.requireNonNull(response
                            .getResult())
                            .toObjects(Ingredient.class);
                    availableIngredients.setValue(ingredients);
                })
                .addOnFailureListener(Throwable::printStackTrace);
        return availableIngredients;
    }

    public MutableLiveData<List<Recipe>> loadRecipes() {
        firebaseRepo
                .getRecipes()
                        .addOnCompleteListener(response -> {
                            if (!response.isSuccessful()) {
                                return;
                            }
                            List<Recipe> recipes = Objects
                                    .requireNonNull(response.getResult())
                                    .toObjects(Recipe.class);

                            recipeList.setValue(recipes);
                        })
                .addOnFailureListener(Throwable::printStackTrace);
        return recipeList;
    }

    public void ingredientClickEvent(Ingredient ingredient) {
        if (userIngredients.getValue().contains(ingredient)) {
            executeIngredientExchange(userIngredients, availableIngredients, ingredient);
        } else {
            executeIngredientExchange(availableIngredients, userIngredients, ingredient);
        }
    }

    private void executeIngredientExchange(MutableLiveData<List<Ingredient>> listToRemove,
                                           MutableLiveData<List<Ingredient>> listToAdd,
                                           Ingredient ingredient) {
        removeListItem(listToRemove, ingredient);

        List<Ingredient> list = listToAdd.getValue();
        list.add(ingredient);
        listToAdd.setValue(list);
    }


    public MutableLiveData<List<Ingredient>> getAvailableIngredients() {
        return availableIngredients;
    }

    public MutableLiveData<List<Ingredient>> getUserIngredients() {
        return userIngredients;
    }

}
