package edu.jcu.kezhang.parkingavailability;

import androidx.annotation.NonNull;

/** Represents a carpark and store information related to it.
 * @author Ke Zhang
 * @version 1.0
 * @since 2022-12-1
 */
public class Carpark {

    // Declared Instance Variables.
    private String carParkID;
    private String area;
    private String development;
    private String location;
    private int availableLots;
    private String lotType;
    private String agency;

    /** Creates a carpark with the specified information.
     * @param carParkID A unique code for the carpark.
     * @param area Area of development/building: Orchard; Marina; Harbfront; JurongLakeDistrict; NA.
     * @param development Major landmark or address where the carpark is located.
     * @param location Latitude and Longitude map coordinates of the carpark.
     * @param availableLots Number of lots available at point of data retrieval of the carpark.
     * @param lotType Type of lots of the carpark: C (for Cars); H (for Heavy Vehicles);
     *               Y (for Motorcycles).
     * @param agency Agencies responsible for data of the carpark: HDB; LTA; URA.
     */
    public Carpark(String carParkID, String area, String development, String location,
                   int availableLots, String lotType, String agency){
        this.carParkID = carParkID;
        this.area = area;
        this.development = development;
        this.location = location;
        this.availableLots = availableLots;
        this.lotType = lotType;
        this.agency = agency;
    }

    /** Gets the carpark’s information.
     * @return A string representing all information of the carpark.
     */
    @NonNull
    @Override
    public String toString() {
        return "Carpark{" +
                "carParkID=" + carParkID +
                ", area='" + area + '\'' +
                ", development='" + development + '\'' +
                ", location='" + location + '\'' +
                ", availableLots=" + availableLots +
                ", lotType='" + lotType + '\'' +
                ", agency='" + agency + '\'' +
                '}';
    }

    /** Gets the carpark’s ID.
     * @return A string representing the carpark’s ID.
     */
    public String getCarParkID(){
        return carParkID;
    }

    /** Gets the carpark’s area of development.
     * @return A string representing the carpark’s area of development.
     */
    public String getArea(){
        return area;
    }

    /** Gets the carpark’s landmark/address.
     * @return A string representing the carpark’s landmark/address.
     */
    public String getDevelopment(){
        return development;
    }

    /** Gets the carpark’s latitude and longitude.
     * @return A string representing the carpark’s latitude and longitude.
     */
    public String getLocation(){
        return location;
    }

    /** Gets the number of lots available at the carpark.
     * @return A integer representing lots available at the carpark.
     */
    public int getAvailableLots(){
        return availableLots;
    }

    /** Gets the type of lots of the carpark.
     * @return A string representing the type of lots of the carpark.
     */
    public String getLotType(){
        return lotType;
    }

    /** Gets the agency responsible for the data of the carpark.
     * @return A string representing the agency responsible for the carpark.
     */
    public String getAgency(){
        return  agency;
    }

}