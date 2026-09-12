package com.richfield.smartpantry;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView noRecipesText;
    private DatabaseHelper dbHelper;
    private RecipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        recyclerView = findViewById(R.id.recipeRecyclerView);
        noRecipesText = findViewById(R.id.textNoRecipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DatabaseHelper(this);

        List<Recipe> seededRecipes = getSeededRecipes();
        List<Ingredient> currentPantry = dbHelper.getAllPantryItems();

        List<Recipe> matchedRecipes = findStrictMatches(seededRecipes, currentPantry);

        if (matchedRecipes.isEmpty()) {
            noRecipesText.setVisibility(View.VISIBLE);
            noRecipesText.setText("No recipes match your pantry yet - add more ingredients.");
            recyclerView.setVisibility(View.GONE);
        } else {
            noRecipesText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            // Uncommented and connected to your RecipeAdapter
            adapter = new RecipeAdapter(matchedRecipes);
            recyclerView.setAdapter(adapter);
        }
    }

    /**
     * CORE LOGIC: The Strict-Matching Rule.
     * Evaluates if every required ingredient is present in the pantry.
     */
    private List<Recipe> findStrictMatches(List<Recipe> allRecipes, List<Ingredient> pantry) {
        List<Recipe> suggested = new ArrayList<>();

        for (Recipe recipe : allRecipes) {
            boolean canCook = true;

            for (Ingredient required : recipe.getRequiredIngredients()) {
                boolean hasSpecificIngredient = false;

                for (Ingredient stocked : pantry) {
                    if (stocked.getName().trim().equalsIgnoreCase(required.getName().trim()) &&
                            stocked.getQuantity() >= required.getQuantity()) {
                        hasSpecificIngredient = true;
                        break;
                    }
                }

                if (!hasSpecificIngredient) {
                    canCook = false;
                    break;
                }
            }

            if (canCook) {
                suggested.add(recipe);
            }
        }
        return suggested;
    }

    private List<Recipe> getSeededRecipes() {
        List<Recipe> list = new ArrayList<>();

        // Recipe 1: Simple Pasta (Requires Tomatoes & Pasta)
        List<Ingredient> pastaReq = Arrays.asList(
                new Ingredient(0, "Tomatoes", 1.0, "kg", ""),
                new Ingredient(0, "Pasta", 1.0, "pack", "")
        );
        list.add(new Recipe(1, "Simple Pasta", "Boil pasta, chop tomatoes, stir together and serve hot.", pastaReq));

        // Recipe 2: Tomato Soup (Requires Tomatoes & Onion)
        List<Ingredient> soupReq = Arrays.asList(
                new Ingredient(0, "Tomatoes", 1.0, "kg", ""),
                new Ingredient(0, "Onion", 1.0, "pcs", "")
        );
        list.add(new Recipe(2, "Tomato Soup", "Blend tomatoes and onions, then simmer with seasoning.", soupReq));

        return list;
    }
}