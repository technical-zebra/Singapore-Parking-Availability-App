package edu.jcu.kezhang.parkingavailability;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/** A helper class to manage carpark database creation and version management.
 * @author Ke Zhang
 * @version 1.0
 * @since 2022-12-1
 */
public class CarparkDatabaseHelper extends SQLiteOpenHelper {

    // Initialise database version.
    private static final int DATABASE_VERSION = 1;

    /** Creates a carpark SQLite database for the first time.
     * @param context A context that database created for.
     */
    public CarparkDatabaseHelper(Context context) {
        super(context, DbConfig.DATABASE_NAME, null, DATABASE_VERSION);
    }

    /** Creates the carpark SQL table in the database.
     * @param sqLiteDatabase The database create table in.
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // SQL Query for create a Table.
        String CREATE_CARPARK_TABLE = "CREATE TABLE " + DbConfig.TABLE_CARPARK + "("
                + DbConfig.COLUMN_CARPARK_ID + " TEXT PRIMARY KEY UNIQUE, "
                + DbConfig.COLUMN_CARPARK_AREA + " TEXT, " // Nullable
                + DbConfig.COLUMN_CARPARK_DEVELOPMENT + " TEXT, " // Nullable
                + DbConfig.COLUMN_CARPARK_LOCATION + " TEXT NOT NULL, "
                + DbConfig.COLUMN_CARPARK_AVAILABLELOTS + " INTEGER, "
                + DbConfig.COLUMN_CARPARK_LOTSTYPE + " TEXT NOT NULL, "
                + DbConfig.COLUMN_CARPARK_AGENCY + " TEXT NOT NULL"
                + ")";

        // SQL execution to create the table.
        sqLiteDatabase.execSQL(CREATE_CARPARK_TABLE);

    }

    /** Database is not upgradable.
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    /** Convert the ArrayList of JSONObject to strings and insert them into the SQL table.
     * @param carparks An ArrayList of JSONObject contain carparks' details.
     * @return A integer count all inserted records.
     */
    public int insertCarparks(ArrayList<JSONObject> carparks) throws JSONException {

        // This variable will count how many records been inserted.
        long insert = 0;

        // Get the writable database.
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete all previous records in table.
        db.execSQL("delete from "+ DbConfig.TABLE_CARPARK);

        // Count how many records remain after deletion.
        System.out.println("Counts: ");
        long count = DatabaseUtils.queryNumEntries(db, DbConfig.TABLE_CARPARK);
        System.out.print(count);

        // Loop all carpark json objects in ArrayList.
        for (JSONObject carpark : carparks){
            // Only insert regular car parking lot records.
            if (carpark.getString("LotType").equals("C")){

                // Initialise a ContentValues that store a set of values to be inserted.
                ContentValues cv = new ContentValues();

                // Insert NA if area is empty.
                String area;
                if (carpark.getString("Area").equals("")){
                    area = "NA";
                } else {
                    area = carpark.getString("Area");
                }

                // A set of values to be inserted.
                cv.put(DbConfig.COLUMN_CARPARK_ID,
                        carpark.getString("CarParkID").toLowerCase());

                cv.put(DbConfig.COLUMN_CARPARK_AREA, area);

                cv.put(DbConfig.COLUMN_CARPARK_DEVELOPMENT,
                        carpark.getString("Development").toLowerCase());

                cv.put(DbConfig.COLUMN_CARPARK_LOCATION, carpark.getString("Location"));

                cv.put(DbConfig.COLUMN_CARPARK_AVAILABLELOTS,
                        carpark.getString("AvailableLots"));

                cv.put(DbConfig.COLUMN_CARPARK_LOTSTYPE, carpark.getString("LotType"));

                cv.put(DbConfig.COLUMN_CARPARK_AGENCY, carpark.getString("Agency"));

                // Execute the SQL insertion and return how many records are inserted.
                insert = insert + db.insert(DbConfig.TABLE_CARPARK,null ,cv);
            }
        }

        // Return number of record inserted.
        return (int) insert;

    }

    /** Query records match given keyword using SQL statements and return data.
     * @param selection A string represent the selection of agency.
     * @param search A string represent the conditions of search keyword.
     * @return An ArrayList of Carpark objects contain information needed to be display.
     */
    public ArrayList<Carpark> populateCarparkListArray(String selection, String search){

        // Initialise search keyword condition part of SQL query.
        String search_query;
        if (!search.equals("")){
            search_query = " AND (development LIKE \"%"+search+"%\" OR id = \""+search+"\") ";
        }else {
            search_query = search;
        }

        // Set up the ArrayList that be return later.
        ArrayList<Carpark> carparks = new ArrayList<>();

        // Get the readable database
        SQLiteDatabase db = this.getReadableDatabase();

        // Add the agency selection and set up complete SQL query.
        String query = String.format("SELECT * FROM %s WHERE agency=\"%s\"",
                DbConfig.TABLE_CARPARK, selection)+ search_query + "LIMIT 40";
        System.out.println(query);

        // Store results in Carpark ArrayList.
        try(Cursor result = db.rawQuery(query,null)){

            // If there is more than one result.
            if (result.getCount() != 0) {

                // A Loop to go through all records.
                while(result.moveToNext()){

                    // Create a Carpark object with one record.
                    String id = result.getString(0);
                    String area = result.getString(1);
                    String development = result.getString(2);
                    String location = result.getString(3);
                    int availableLot = result.getInt(4);
                    String agency = result.getString(6);
                    Carpark carpark = new Carpark(id, area, development,
                            location, availableLot, "", agency);

                    // Add the Carpark objetc to the ArrayList.
                    carparks.add(carpark);
                }
            }

        }

        // return an ArrayList of Carpark objects.
        return carparks;

    }




}
