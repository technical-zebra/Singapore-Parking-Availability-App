package edu.jcu.kezhang.parkingavailability;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

/** A Activity class provide setting features of ParkingAvailbility App to its users.
 * @author Ke Zhang
 * @version 1.0
 * @since 2022-12-1
 */
public class SettingsActivity extends AppCompatActivity {

    // Declare request code;
    public static int SETTINGS_REQUEST = 1;

    // Declare UI components.
    RadioButton black_and_white, dark_blue_and_white;
    Button apply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // Assign UI components.
        apply = findViewById(R.id.apply_btn);
        black_and_white = findViewById(R.id.black_and_white_rb);
        dark_blue_and_white = findViewById(R.id.dark_blue_and_white_rb);

        /* Button Listener for search carpark button. */
        apply.setOnClickListener(view -> {

            // Declare a string that store the theme;
            String selectedTheme;

            /*Check which radio button is selected, and enable night mode (black_and_white theme)
            if condition is meet. */
            if (black_and_white.isChecked()) {
                selectedTheme = "black_and_white";
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

            } else {
                selectedTheme = "dark_blue_and_white";
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }

            // Return to previous activity and bring theme selection back.
            Intent intent = new Intent();
            intent.putExtra("selectedTheme", selectedTheme);
            setResult(RESULT_OK, intent);
            finish();

        });
    }

}