package edu.jcu.kezhang.parkingavailability;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/** A helper class to avoid multiple clicked added repeated instances to the queue
 *  and cause memory overload.
 * @author Ke Zhang
 * @version 1.0
 * @since 2022-12-1
 */
public class VolleySingleton {

    // Declare class variables.
    private static VolleySingleton instance;

    // Declare Instance Variables.
    private RequestQueue requestQueue;

    /** Create a request queue for whole application and not just life time of one activity.
     * @param ctx Context to be implement.
     */
    private VolleySingleton(Context ctx) {
        requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
    }

    /** Create a new instance and return it only if there are no existing instances,
     *  else return the existing instance. Synchronized mean only one thread can exist at one time.
     * @param ctx Context to be implement.
     * @return A VolleySingleton instance, can be newly created or existing one.
     */
    public static synchronized VolleySingleton getInstance(Context ctx) {
        if (instance == null) {
            instance = new VolleySingleton(ctx);
        }
        return instance;
    }

    /** Provide the request queue.
     * @return A request queue expected to be returned.
     */
    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

}