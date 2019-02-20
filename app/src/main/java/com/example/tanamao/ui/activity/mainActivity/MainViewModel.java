package com.example.tanamao.ui.activity.mainActivity;

import android.app.Application;

import com.example.tanamao.entity.recipe.Ingredient;
import com.example.tanamao.repository.FirebaseRepo;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class MainViewModel extends AndroidViewModel {

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    private FirebaseRepo firebaseRepo = FirebaseRepo.getInstance();
    private MutableLiveData<List<Ingredient>> ingredients = new MutableLiveData<>();

    private void loadIngredients() {
        ingredients = firebaseRepo.getIngredients();
    }

    public MutableLiveData<List<Ingredient>> getIngredients() {
        if(ingredients != null && ingredients.getValue().size() <= 0) {
            loadIngredients();
        }
        return ingredients;
    }
}
