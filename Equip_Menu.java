package com.example.sisyphus.leagueapp;

import android.app.DownloadManager;
import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;


public class Equip_Menu extends AppCompatActivity {


    // Create a string for the ImageView label
    final String FIRST_TAG = "drag";
    final String GOAT_TAG = "goat";
    ClipData.Item item;
    ClipData dragData;
    ArrayList<View> items;

    public int getResourceId(String pVariableName, String pResourcename, String pPackageName) {
        try {
            return getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    public void displayEnemyChampion() {
        ImageView profpic = (ImageView) findViewById(R.id.EnemyChamp);
        profpic.setImageResource(R.drawable.champaatrox);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String url = getIntent().getStringExtra("ECJSON");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("To STring", response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("To STring", error.toString());
                        // TODO Auto-generated method stub

                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equip__menu);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        displayEnemyChampion();

        //Set up slots for champion inventory
        setUpChampionInventory();

        GridView gridView = (GridView) findViewById(R.id.gridview);
        ImageAdapter itemGrid = new ImageAdapter(this, item, dragData);
        gridView.setAdapter(itemGrid);
        items = new ArrayList<View>();
    }

    public void decrementLVL(View view){
        TextView textView = (TextView) findViewById(R.id.level);
        int i = Integer.parseInt((String) textView.getText());
        if(i>1) {
            i--;
            textView.setText(i + "");
        }
    }

    public void incrementLVL(View view){
        TextView textView = (TextView) findViewById(R.id.level);
        int i = Integer.parseInt((String) textView.getText());
        if(i<18) {
            i++;
            textView.setText(i + "");
        }
    }
    //Sets ups champion inventory slots
    private void setUpChampionInventory() {
        //set slots as dropable areas
        View slot1 = findViewById(R.id.slot1);
        View slot2 = findViewById(R.id.slot2);
        View slot3 = findViewById(R.id.slot3);
        View slot4 = findViewById(R.id.slot4);
        View slot5 = findViewById(R.id.slot5);
        View slot6 = findViewById(R.id.slot6);

        //sets tags
        slot1.setTag("slot1");
        slot2.setTag("slot2");
        slot3.setTag("slot3");
        slot4.setTag("slot4");
        slot5.setTag("slot5");
        slot6.setTag("slot6");


        MyDragEventListener mDragListener = new MyDragEventListener(slot1);
        slot1.setOnDragListener(mDragListener);
        slot2.setOnDragListener(mDragListener);
        slot3.setOnDragListener(mDragListener);
        slot4.setOnDragListener(mDragListener);
        slot5.setOnDragListener(mDragListener);
        slot6.setOnDragListener(mDragListener);

        makeInventoryDragable(slot1);
        makeInventoryDragable(slot2);
        makeInventoryDragable(slot3);
        makeInventoryDragable(slot4);
        makeInventoryDragable(slot5);
        makeInventoryDragable(slot6);

        TrashDragEventListener tDragedListener = new TrashDragEventListener();
        View trash = findViewById(R.id.trash);
        trash.setOnDragListener(tDragedListener);
    }
    //retu
    private void getCurrentInventory()
    {
        View slot1 = findViewById(R.id.slot1);
        View slot2 = findViewById(R.id.slot2);
        View slot3 = findViewById(R.id.slot3);
        View slot4 = findViewById(R.id.slot4);
        View slot5 = findViewById(R.id.slot5);
        View slot6 = findViewById(R.id.slot6);
    }



    protected class MyDragEventListener implements View.OnDragListener {
        private ImageView draggedView;

        public MyDragEventListener(View view) {
            draggedView = (ImageView) view;
        }

        public boolean onDrag(View v, DragEvent event) {
            final int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DROP:
                    ClipData dd = event.getClipData();
                    ((ImageView) v).setImageResource(getResourceId(dd.getDescription().getLabel() + "", "drawable", getPackageName()));
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    return true;
                case DragEvent.ACTION_DRAG_LOCATION:
                    return true;
                case DragEvent.ACTION_DRAG_STARTED:
                    return true;

            }
            return false;
        }
    }

    protected class TrashDragEventListener implements View.OnDragListener {

        public TrashDragEventListener() {
        }
        public boolean onDrag(View v, DragEvent event) {
        final int action = event.getAction();
        switch (action) {
            case DragEvent.ACTION_DROP:
                ClipData dd = event.getClipData();
                int originId  = getResourceId(dd.getDescription().getLabel()+"", "id", getPackageName());
                ImageView originView = (ImageView) findViewById(originId);
                ((ImageView)originView).setImageResource(R.drawable.goat);
                return true;
            case DragEvent.ACTION_DRAG_ENDED:
                return true;
            case DragEvent.ACTION_DRAG_ENTERED:
                return true;
            case DragEvent.ACTION_DRAG_EXITED:
                return true;
            case DragEvent.ACTION_DRAG_LOCATION:
                return true;
            case DragEvent.ACTION_DRAG_STARTED:
                return true;
        }
            return false;
    }
}


    private boolean makeInventoryDragable(final View view){
        view.setOnLongClickListener(new View.OnLongClickListener() {

            // Defines the one method for the interface, which is called when the View is long-clicked
            public boolean onLongClick(View v) {



                item = new ClipData.Item((CharSequence) v.getTag());


               dragData= new ClipData((java.lang.CharSequence) v.getTag(), new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN}, item);


                // Instantiates the drag shadow builder.
                View.DragShadowBuilder myShadow = new MyDragShadowBuilder(view);


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


    public static class MyDragShadowBuilder extends View.DragShadowBuilder {

        // The drag shadow image, defined as a drawable thing
        private static Drawable shadow;

        // Defines the constructor for myDragShadowBuilder
        public MyDragShadowBuilder(View v) {

            // Stores the View parameter passed to myDragShadowBuilder.
            super(v);

            // Creates a draggable image that will fill the Canvas provided by the system.
            shadow = new ColorDrawable(Color.LTGRAY);
        }

        // Defines a callback that sends the drag shadow dimensions and touch point back to the
        // system.
        @Override
        public void onProvideShadowMetrics (Point size, Point touch) {
            // Defines local variables
             int width, height;

            // Sets the width of the shadow to half the width of the original View
            width = 40;//getView().getWidth() / 2;

            // Sets the height of the shadow to half the height of the original View
            height = 40;//getView().getHeight() / 2;

            // The drag shadow is a ColorDrawable. This sets its dimensions to be the same as the
            // Canvas that the system will provide. As a result, the drag shadow will fill the
            // Canvas.
            shadow.setBounds(0, 0, width, height);

            // Sets the size parameter's width and height values. These get back to the system
            // through the size parameter.
            size.set(width, height);

            // Sets the touch point's position to be in the middle of the drag shadow
            touch.set(width / 2, height / 2);
        }

        // Defines a callback that draws the drag shadow in a Canvas that the system constructs
        // from the dimensions passed in onProvideShadowMetrics().
        @Override
        public void onDrawShadow(Canvas canvas) {

            // Draws the ColorDrawable in the Canvas passed in from the system.
            shadow.draw(canvas);
        }
    }

}
