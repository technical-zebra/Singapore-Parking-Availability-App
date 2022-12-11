package edu.jcu.kezhang.parkingavailability;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SettingsActivity extends AppCompatActivity {

    // Declare request code;
    public static int SETTINGS_REQUEST = 1;

    RadioButton black_and_white, dark_blue_and_white;
    Button apply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        apply = findViewById(R.id.apply_btn);
        black_and_white = findViewById(R.id.black_and_white_rb);
        dark_blue_and_white = findViewById(R.id.dark_blue_and_white_rb);

        /* Button Listener for search carpark button. */
        apply.setOnClickListener(view -> {

            String selectedTheme;

            //Check which radio button is selected.
            if (black_and_white.isChecked()) {
                selectedTheme = "black_and_white";
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

            } else {
                selectedTheme = "dark_blue_and_white";
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }

            Intent intent = new Intent();
            intent.putExtra("selectedTheme", selectedTheme);
            setResult(RESULT_OK, intent);
            finish();

        });
    }

}