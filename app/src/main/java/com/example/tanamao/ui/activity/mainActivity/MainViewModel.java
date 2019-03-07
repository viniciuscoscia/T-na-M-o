package com.example.tanamao.ui.activity.mainActivity;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.tanamao.model.entity.recipe.Ingredient;
import com.example.tanamao.model.entity.recipe.Recipe;
import com.example.tanamao.repository.FirebaseRepo;
import com.example.tanamao.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import static android.content.Context.MODE_PRIVATE;

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
                    List<Ingredient> repoIngredients = Objects.requireNonNull(response
                            .getResult())
                            .toObjects(Ingredient.class);
                    List<Ingredient> persistedUserIngredients = getPersistedUserIngredients(getApplication().getBaseContext());
                    repoIngredients = removeSavedIngredients(repoIngredients, persistedUserIngredients);
                    availableIngredients.setValue(repoIngredients);
                    userIngredients.setValue(persistedUserIngredients);
                })
                .addOnFailureListener(Throwable::printStackTrace);
        return availableIngredients;
    }

    private List<Ingredient> removeSavedIngredients(List<Ingredient> repoIngredients, List<Ingredient> persistedUserIngredients) {
        ArrayList<Ingredient> cleanList = new ArrayList<>();
        for (Ingredient ingredient : repoIngredients) {
            boolean found = false;
            for (Ingredient persistedUserIngredient : persistedUserIngredients) {
                if (ingredient.getId() == persistedUserIngredient.getId()) {
                    found = true;
                }
            }

            if (!found) {
                cleanList.add(ingredient);
            }
        }
        return cleanList;
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

        saveUserIngredients();
    }


    public MutableLiveData<List<Ingredient>> getAvailableIngredients() {
        return availableIngredients;
    }

    public MutableLiveData<List<Ingredient>> getUserIngredients() {
        return userIngredients;
    }

    public List<Ingredient> getPersistedUserIngredients(Context context) {
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        String json = sharedPreferences.getString(Constants.SHARED_PREFERENCES_INGREDIENTS_JSON, "");
        ArrayList<Ingredient> ingredients = new Gson().fromJson(json, new TypeToken<List<Ingredient>>() {
        }.getType());

        if (ingredients != null) {
            return ingredients;
        }

        return new ArrayList<>();
    }

    private void saveUserIngredients() {
        SharedPreferences sharedPreferences = this.getApplication()
                .getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, MODE_PRIVATE);

        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(Constants.SHARED_PREFERENCES_INGREDIENTS_JSON,
                new Gson().toJson(userIngredients.getValue()));
        edit.apply();
    }
}
