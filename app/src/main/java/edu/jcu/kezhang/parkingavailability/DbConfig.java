package edu.jcu.kezhang.parkingavailability;

/** A helper class define a list of database setup configuration.
 * @author Ke Zhang
 * @version 1.0
 * @since 2022-12-1
 */
public class DbConfig {
    /* Declared Class Variables. */
    // Database configuration.
    public static final String DATABASE_NAME = "carpark_db";

    // Table configuration.
    public static final String TABLE_CARPARK = "carpark";

    // Columns configuration.
    public static final String COLUMN_CARPARK_ID = "id";
    public static final String COLUMN_CARPARK_AREA = "area";
    public static final String COLUMN_CARPARK_DEVELOPMENT = "development";
    public static final String COLUMN_CARPARK_LOCATION = "location";
    public static final String COLUMN_CARPARK_AVAILABLELOTS = "availableLots";
    public static final String COLUMN_CARPARK_LOTSTYPE = "lotType";
    public static final String COLUMN_CARPARK_AGENCY = "agency";
}
