package com.richfield.smartpantry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PantryAdapter adapter;
    private DatabaseHelper dbHelper;
    private TextView textEmptyPantry;
    private Button btnViewSuggestions;
    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.pantryRecyclerView);
        textEmptyPantry = findViewById(R.id.textEmptyPantry);
        btnViewSuggestions = findViewById(R.id.btnViewSuggestions);
        fabAdd = findViewById(R.id.fabAddIngredient);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Open Add Ingredient form
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditIngredientActivity.class);
                startActivity(intent);
            }
        });

        // Open Recipe Matching screen
        btnViewSuggestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SuggestedRecipesActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Re-queries SQLite every time the user navigates back to this Activity.
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadPantryData();
    }

    private void loadPantryData() {
        List<Ingredient> pantryList = dbHelper.getAllPantryItems();

        if (pantryList.isEmpty()) {
            textEmptyPantry.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            textEmptyPantry.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter = new PantryAdapter(pantryList);
            recyclerView.setAdapter(adapter);
        }
    }
}