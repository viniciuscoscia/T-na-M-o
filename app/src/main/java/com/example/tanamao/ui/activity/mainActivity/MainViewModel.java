package com.example.tanamao.ui.activity.mainActivity;

import android.app.Application;

import com.example.tanamao.entity.recipe.Ingredient;
import com.example.tanamao.repository.FirebaseRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class MainViewModel extends AndroidViewModel {

    public MainViewModel(@NonNull Application application) {
        super(application);
        loadIngredients();
    }

    private FirebaseRepo firebaseRepo = FirebaseRepo.getInstance();
    private MutableLiveData<List<Ingredient>> availableIngredients = new MutableLiveData<>();
    private MutableLiveData<List<Ingredient>> userIngredients = new MutableLiveData<>();

    public void addToMyUserIngredients(Ingredient ingredient) {
        if(userIngredients.getValue() == null) {
            userIngredients.setValue(new ArrayList<>());
        }

        removeListItem(availableIngredients, ingredient);

        List<Ingredient> list = userIngredients.getValue();
        list.add(ingredient);
        userIngredients.setValue(list);
    }

    private void removeListItem(MutableLiveData<List<Ingredient>> mutableList, Ingredient ingredient) {
        List<Ingredient> newList = new ArrayList<>();

        for(Ingredient item: Objects.requireNonNull(mutableList.getValue())) {
            if(item.getId().equals(ingredient.getId())) {
                continue;
            }
            newList.add(item);
        }
        mutableList.setValue(newList);
    }

    public void removeUserIngredient(Ingredient ingredient) {
        if(availableIngredients.getValue() == null) {
            availableIngredients.setValue(new ArrayList<>());
        }

        removeListItem(userIngredients, ingredient);

        List<Ingredient> list = availableIngredients.getValue();
        list.add(ingredient);
        availableIngredients.setValue(list);
    }

    private void loadIngredients() {
        availableIngredients = firebaseRepo.getIngredients();
    }

    public MutableLiveData<List<Ingredient>> getAvailableIngredients() {
        return availableIngredients;
    }

    public MutableLiveData<List<Ingredient>> getUserIngredients() {
        return userIngredients;
    }

}
