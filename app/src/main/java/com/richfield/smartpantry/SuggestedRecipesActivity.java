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
        noRecipesText = findViewById(R.id.textNoRecipes); // Feedback for zero matches
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
            // adapter = new RecipeAdapter(matchedRecipes, this);
            // recyclerView.setAdapter(adapter);
        }
    }

    /**
     * CORE LOGIC: The Strict-Matching Rule.
     * Evaluates if every required ingredient is present in the pantry in the required quantity.
     */
    private List<Recipe> findStrictMatches(List<Recipe> allRecipes, List<Ingredient> pantry) {
        List<Recipe> suggested = new ArrayList<>();

        for (Recipe recipe : allRecipes) {
            boolean canCook = true;

            for (Ingredient required : recipe.getRequiredIngredients()) {
                boolean hasSpecificIngredient = false;

                for (Ingredient stocked : pantry) {
                    // Simple string match ignoring case to handle trivial differences
                    if (stocked.getName().trim().equalsIgnoreCase(required.getName().trim()) &&
                            stocked.getQuantity() >= required.getQuantity()) {
                        hasSpecificIngredient = true;
                        break; // Found the ingredient, move to next requirement
                    }
                }

                if (!hasSpecificIngredient) {
                    canCook = false; // Missing an ingredient or insufficient quantity
                    break; // Fails strict matching, skip this recipe
                }
            }

            if (canCook) {
                suggested.add(recipe);
            }
        }
        return suggested;
    }

    // Seeded database of recipes
    private List<Recipe> getSeededRecipes() {
        List<Recipe> list = new ArrayList<>();
        // Example Recipe 1
        List<Ingredient> omeletteReq = Arrays.asList(
                new Ingredient(0, "Eggs", 3.0, "pcs", ""),
                new Ingredient(0, "Milk", 50.0, "ml", "")
        );
        list.add(new Recipe(1, "Basic Omelette", "Whisk eggs and milk. Fry until golden.", omeletteReq));

        // Add 14-19 more to meet the 15-20 recipe requirement
        return list;
    }
}