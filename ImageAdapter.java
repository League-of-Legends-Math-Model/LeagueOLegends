package com.example.sisyphus.leagueapp;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.sisyphus.leagueapp.R;

/**
 * Created by sisyphus on 1/17/17.
 */

public class ImageAdapter extends BaseAdapter{
    private Context mContext;
    private ClipData.Item item;
    private ClipData dragData;

    public ImageAdapter(Context c,    ClipData.Item it,
            ClipData drD) {
        mContext = c;
        item = it;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

         //Set tag to correct picture
            Log.d("Position",""+position);
            String itemName = resourceAsItemString(mThumbIds[position]);
            imageView.setTag(itemName);
           // imageView.setTag("item1001");
            imageView.setPadding(8, 8, 8, 8);
            this.makeDragable(imageView);
        } else {
            Log.d("reached","omo");
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    private String resourceAsItemString(int i)
    {
        String itemName = mContext.getResources().getString(i);
        return itemName.substring(13,21);
    }

    private String[] itemPictureIndex = {
            "1001","1004","1006","1011","1018","1026",
            "1027", "1028", "1029", "1031", "1036",
            "1037", "1038", "1039", "1041", "1042",
            "1043", "1051", "1052", "1053", "1054",
            "1055", "1056", "1057", "1058", "1082",
            "1083", "1400", "1401", "1402", "1408",
            "1409", "1410", "1412"
    };
    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.item1001, R.drawable.item1004,
            R.drawable.item1006, R.drawable.item1011,
            R.drawable.item1018, R.drawable.item1026,
            R.drawable.item1027, R.drawable.item1028,
            R.drawable.item1029, R.drawable.item1031,
            R.drawable.item1036, R.drawable.item1037,
            R.drawable.item1038, R.drawable.item1039,
            R.drawable.item1041, R.drawable.item1042,
            R.drawable.item1043, R.drawable.item1051,
            R.drawable.item1052, R.drawable.item1053,
            R.drawable.item1054, R.drawable.item1055,
            R.drawable.item1056, R.drawable.item1057,
            R.drawable.item1058, R.drawable.item1082,
            R.drawable.item1083, R.drawable.item1400,
            R.drawable.item1401, R.drawable.item1402,
            R.drawable.item1408, R.drawable.item1409,
            R.drawable.item1410, R.drawable.item1412,

    };


    private boolean makeDragable(final View view){
        view.setOnLongClickListener(new View.OnLongClickListener() {

            // Defines the one method for the interface, which is called when the View is long-clicked
            public boolean onLongClick(View v) {

                // Create a new ClipData.
                // This is done in two steps to provide clarity. The convenience method
                // ClipData.newPlainText() can create a plain text ClipData in one step.

                // Create a new ClipData.Item from the ImageView object's tag
                item = new ClipData.Item((CharSequence) v.getTag());

                // Create a new ClipData using the tag as a label, the plain text MIME type, and
                // the already-created item. This will create a new ClipDescription object within the
                // ClipData, and set its MIME type entry to "text/plain"
                dragData = new ClipData((java.lang.CharSequence) v.getTag(), new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN}, item);

                // Instantiates the drag shadow builder.
                View.DragShadowBuilder myShadow = new Equip_Menu.MyDragShadowBuilder(view);

                // Starts the drag based on version
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(dragData,  // the data to be dragged
                            myShadow,  // the drag shadow builder
                            null,      // no need to use local data
                            0          // flags (not currently used, set to 0)
                    );
                } else{
                    v.startDrag(dragData,  // the data to be dragged
                            myShadow,  // the drag shadow builder
                            null,      // no need to use local data
                            0          // flags (not currently used, set to 0)
                    );
                }

                return true;
            }
        });
        return true;
    }




}
