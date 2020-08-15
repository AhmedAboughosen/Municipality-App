package com.example.mymunicipalityapp.model;

public class SetupScreenItem {

    private String Title ;
    private String description;
    private int image;


    public SetupScreenItem(String Title  , String description , int image ){
        this.Title = Title;
        this.description = description;
        this.image = image;
    }
    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
