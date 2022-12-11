package edu.jcu.kezhang.parkingavailability;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/** A Activity class provide key features of ParkingAvailbility App to its users.
 * @author Ke Zhang
 * @version 1.0
 * @since 2022-12-1
 */
public class MainActivity extends AppCompatActivity {

    // Declared Instance Variables.
    Button displayCarparks, searchCarpark, setting;
    EditText carparkInput;
    RecyclerView recyclerView;
    RadioButton lta, hdb, ura;
    CarparkDatabaseHelper databaseHelper = new CarparkDatabaseHelper(MainActivity.this);
    String selectedAgency = "";
    String search = "";
    String selectedTheme = "dark_blue_and_white";
    private RequestQueue queue;

    /** Check the state of network connectivity.
     * @return A boolean representing the network connection.
     */
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    /** Check which radio button is selected.
     * @return A string representing the selection of agency.
     */
    private String getRadioButton(){
        String selectedAgency = "LTA";

        if (lta.isChecked()) {
            selectedAgency = lta.getText().toString();
        } else if (hdb.isChecked()) {
            selectedAgency = hdb.getText().toString();
        } else if (ura.isChecked()) {
            selectedAgency = ura.getText().toString();
        }
        return selectedAgency;
    }

    /** Update the view using search keyword and agency selection.
     * @param search A string representing search keyword for carpark address or ID.
     */
    private void queryDataFromSQLite(String search){
        // Detect which agency (radio button) is selected.
        selectedAgency = getRadioButton();

        // Query data filter using search and agency keyword.
        ArrayList<Carpark> carparks = databaseHelper
                .populateCarparkListArray(selectedAgency, search);

        // Set CustomAdapter as the adapter for RecyclerView.
        CustomAdapter adapter = new CustomAdapter(carparks, MainActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(MainActivity.this));

    }

    /** Initialise this activity, create the the default UI and their interactive functions,
     *  also restore view after screen rotation.
     * @param savedInstanceState A bundle able to read information in key-value pair.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check network connection.
        Toast.makeText(MainActivity.this, "Network connection: "
                        + isNetworkConnected(),
                Toast.LENGTH_SHORT).show();

        displayCarparks = findViewById(R.id.display_carparks_btn);
        searchCarpark = findViewById(R.id.search_carpark_btn);
        setting = findViewById(R.id.setting_btn);

        carparkInput = findViewById(R.id.carpark_input_et);
        recyclerView = findViewById(R.id.carparks_report_rv);

        lta = findViewById(R.id.lta_rb);
        hdb = findViewById(R.id.hdb_rb);
        ura = findViewById(R.id.ura_rb);

        // Instantiate a RequestQueue.
        queue = Volley.newRequestQueue(MainActivity.this);

        /* Button Listener for display all carparks button. */
        displayCarparks.setOnClickListener(view -> {

            Toast.makeText(MainActivity.this, "Loading... Please wait.",
                    Toast.LENGTH_SHORT).show();

            // Setup API url to get JSON data.
            String url = "http://datamall2.mytransport.sg/ltaodataservice/CarParkAvailabilityv2";

            // Request a Json Object response from the provided URL.
            JsonObjectRequest request = new JsonObjectRequest
                    (Request.Method.GET, url,null, response -> {
                        // response.

                        try {
                            // Get the JSONArray contains all carparks' information.
                            JSONArray resultJson = response.getJSONArray("value");

                            // Convert JSONArray to an ArrayList contain JSONObjects.
                            ArrayList<JSONObject> carparks = new ArrayList<>();
                            for (int n =0; n < resultJson.length();n++) {
                                carparks.add(resultJson.getJSONObject(n));
                            }

                            /* Insert an ArrayList of JSONObjects to SQLite database,
                            return count of inserted record. */
                            int inserted_record = databaseHelper.insertCarparks(carparks);
                            System.out.println("Number of record inserted: "+inserted_record);

                        } catch (JSONException e){
                            // Exception Handling for invalid JSON conversion.
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error, " +
                                            "not able to convert JSON!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }, error -> {
                        // Exception Handling for invalid request.
                        error.printStackTrace();
                        Toast.makeText(MainActivity.this,
                                "Error, not able to download data, please change to " +
                                        "wifi connection mode.",
                                Toast.LENGTH_SHORT).show();
                    }
                    ) {
                // Header configuration.
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String>  params = new HashMap<>();
                    params.put("AccountKey", "BIYCkkcYT/eTc9whKEPSEQ==");
                    params.put("accept", "application/json");
                    params.put("Accept-Encoding", "gzip, deflate, br");
                    return params;
                }
            };

            // Add a request to the RequestQueue.
            queue.add(request);

            // Query result from SQLite database and fill the RecycleView with no search keyword.
            search = "";
            queryDataFromSQLite(search);


        });

        /* Button Listener for search carpark button. */
        searchCarpark.setOnClickListener(view -> {

            // Get search keyword as a lowercase string.
            search = carparkInput.getText().toString().toLowerCase();

            // Query data and filter with search and agency keyword.
            queryDataFromSQLite(search);

        });

        /* Button Listener for setting button. */
        setting.setOnClickListener(view -> {

            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

        });

        /* Restore view after screen rotation. */
        if(savedInstanceState == null){
            ; // Do nothing.
        } else {
            // Get the strings from bundle
            search = savedInstanceState.getString("search");
            selectedAgency = savedInstanceState.getString("selectedAgency");

            // Set the radio button selection according to the string.
            if (selectedAgency.equals("LTA")){
                lta.setChecked(true);
                hdb.setChecked(false);
                ura.setChecked(false);
            } else if(selectedAgency.equals("HDB")){
                lta.setChecked(false);
                hdb.setChecked(true);
                ura.setChecked(false);
            } else{
                lta.setChecked(false);
                hdb.setChecked(false);
                ura.setChecked(true);
            }

            // Query result from SQLite database and fill the RecycleView.
            queryDataFromSQLite(search);
        }

    }

    /** Save the search keyword and agency selection for screen rotation.
     * @param outState A bundle able to store information in key-value pair.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("search", search);
        selectedAgency = getRadioButton();
        outState.putString("selectedAgency", selectedAgency);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SettingsActivity.SETTINGS_REQUEST){
            if (resultCode == RESULT_OK) {
                if(data != null){
                    selectedTheme = data.getStringExtra("selectedTheme");
                }
            }
        }
    }
}