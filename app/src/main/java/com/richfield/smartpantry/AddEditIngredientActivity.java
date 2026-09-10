package com.richfield.smartpantry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditIngredientActivity extends AppCompatActivity {

    private EditText editName, editQuantity, editUnit, editExpiry;
    private Button btnSave;
    private DatabaseHelper dbHelper;
    private int ingredientId = -1; // Default to -1 to indicate "Add Mode"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        dbHelper = new DatabaseHelper(this);

        editName = findViewById(R.id.editIngredientName);
        editQuantity = findViewById(R.id.editQuantity);
        editUnit = findViewById(R.id.editUnit);
        editExpiry = findViewById(R.id.editExpiry);
        btnSave = findViewById(R.id.btnSaveIngredient);

        // Check if editing an existing item via Intent extras
        Intent intent = getIntent();
        if (intent.hasExtra("ID")) {
            ingredientId = intent.getIntExtra("ID", -1);
            editName.setText(intent.getStringExtra("NAME"));
            editQuantity.setText(String.valueOf(intent.getDoubleExtra("QUANTITY", 0)));
            editUnit.setText(intent.getStringExtra("UNIT"));
            editExpiry.setText(intent.getStringExtra("EXPIRY"));
            btnSave.setText("Update Ingredient");
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveIngredient();
            }
        });
    }

    private void saveIngredient() {
        String name = editName.getText().toString().trim();
        String qtyString = editQuantity.getText().toString().trim();
        String unit = editUnit.getText().toString().trim();
        String expiry = editExpiry.getText().toString().trim();

        // Basic input validation
        if (name.isEmpty() || qtyString.isEmpty() || unit.isEmpty()) {
            Toast.makeText(this, "Please fill out name, quantity, and unit.", Toast.LENGTH_SHORT).show();
            return;
        }

        double quantity = Double.parseDouble(qtyString);
        Ingredient ingredient = new Ingredient(ingredientId, name, quantity, unit, expiry);

        boolean success;
        if (ingredientId == -1) {
            success = dbHelper.addIngredient(ingredient); // Create
        } else {
            success = dbHelper.updateIngredient(ingredient); // Update
        }

        if (success) {
            Toast.makeText(this, "Saved successfully!", Toast.LENGTH_SHORT).show();
            finish(); // Return to previous screen
        } else {
            Toast.makeText(this, "Error saving ingredient.", Toast.LENGTH_SHORT).show();
        }
    }
}