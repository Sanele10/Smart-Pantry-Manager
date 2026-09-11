package com.richfield.smartpantry;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        TextView titleText = findViewById(R.id.textRecipeTitle);
        TextView instructionsText = findViewById(R.id.textInstructions);

        String title = getIntent().getStringExtra("RECIPE_TITLE");
        String instructions = getIntent().getStringExtra("RECIPE_INSTRUCTIONS");

        if (title != null) titleText.setText(title);
        if (instructions != null) instructionsText.setText(instructions);
    }
}