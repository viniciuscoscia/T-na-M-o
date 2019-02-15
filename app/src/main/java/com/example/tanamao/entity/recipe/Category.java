package com.example.tanamao.entity.recipe;

import android.os.Parcel;
import android.os.Parcelable;

public class Category implements Parcelable {
    private String id;
    private String categoryName;
    private Category parentCategory;

    public Category() {
    }

    public Category(String id, String categoryName, Category parentCategory) {
        this.id = id;
        this.categoryName = categoryName;
        this.parentCategory = parentCategory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    protected Category(Parcel in) {
        id = in.readString();
        categoryName = in.readString();
        parentCategory = in.readParcelable(Category.class.getClassLoader());
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(categoryName);
        dest.writeParcelable(parentCategory, flags);
    }
}
