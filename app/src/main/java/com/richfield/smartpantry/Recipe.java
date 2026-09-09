package com.richfield.smartpantry;

import java.util.List;

public class Recipe {
    private int id;
    private String title;
    private String instructions;
    private List<Ingredient> requiredIngredients;

    public Recipe(int id, String title, String instructions, List<Ingredient> requiredIngredients) {
        this.id = id;
        this.title = title;
        this.instructions = instructions;
        this.requiredIngredients = requiredIngredients;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getInstructions() { return instructions; }
    public List<Ingredient> getRequiredIngredients() { return requiredIngredients; }
}