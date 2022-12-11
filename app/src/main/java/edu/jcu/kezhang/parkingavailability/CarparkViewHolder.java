package edu.jcu.kezhang.parkingavailability;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/** A custom ViewHolder class define an item view and metadata required by the RecyclerView adaptor.
 * @author Ke Zhang
 * @version 1.0
 * @since 2022-12-1
 */
public class CarparkViewHolder extends RecyclerView.ViewHolder {

    // Declared Instance Variables.
    TextView development, area, carparkId, numOfCar;

    ImageView carPic;
    ImageView mapPic;

    View view;

    /** Creates ViewHolder for the RecycleView Adaptor which initialise graphic Layout of one item.
     * @param itemView The view for one item that will generated in RecycleView.
     */
    public CarparkViewHolder(View itemView)
    {
        // Inherit constructor from super class.
        super(itemView);

        // Define components of the item and match them with XML layout.
        development
                = (TextView)itemView
                .findViewById(R.id.development);
        area
                = (TextView)itemView
                .findViewById(R.id.area);
        carparkId
                = (TextView)itemView
                .findViewById(R.id.carpark_id);
        numOfCar
                = (TextView)itemView
                .findViewById(R.id.num_of_lots);
        carPic
                = (ImageView)itemView
                .findViewById(R.id.car_image);
        mapPic
                = (ImageView)itemView
                .findViewById(R.id.map_image);

        view = itemView;
    }
}
