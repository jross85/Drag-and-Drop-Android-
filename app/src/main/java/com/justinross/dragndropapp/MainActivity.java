package com.justinross.dragndropapp;

import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView txtView1;
    private TextView txtView2;
    private Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign the touch listener to your view which you want to move
        //BE SURE TO ASSIGN THE VIEWS THAT LISTEN FOR THE DRAG, OR ELSE
        //YOUR TEXTVIEW (OR IMAGEVIEW IF YOU CHOOSE THAT) WILL DISAPPEAR
        //AND YOU WILL BE SAD, VERY SAD
        findViewById(R.id.textView1).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.pinkLayout).setOnDragListener(new MyDragListener());
        findViewById(R.id.yellowLayout).setOnDragListener(new MyDragListener());






    }

    private final class MyTouchListener implements View.OnTouchListener {

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return  false;
            }
        }

    }

     class MyDragListener implements View.OnDragListener {
        Drawable enterShape = getResources().getDrawable(R.drawable.shape_droptarget);
        Drawable normalShape = getResources().getDrawable(R.drawable.shape);


         @Override
         public boolean onDrag(View v, DragEvent event) {
             int action = event.getAction();
             switch (event.getAction()) {
                 case DragEvent.ACTION_DRAG_STARTED:
                     //do nothing
                     v.setBackground(normalShape);
                     v.setVisibility(View.VISIBLE);
                     return true;
                 case DragEvent.ACTION_DRAG_ENTERED:
                     v.setBackground(enterShape);
                     break;
                 case DragEvent.ACTION_DRAG_EXITED:
                     v.setBackground(normalShape);
                     break;
                 case DragEvent.ACTION_DROP:
                     //Dropped, reassign view to ViewGroup
                     Log.v("object: ", "DROPPED");
                     View view = (View) event.getLocalState();
                     ViewGroup owner = (ViewGroup) view.getParent();
                     owner.removeView(view);
                     LinearLayout container = (LinearLayout) v;
                     container.addView(view);
                     view.setVisibility(View.VISIBLE);
                     break;
                 case DragEvent.ACTION_DRAG_ENDED:
                     v.setBackground(normalShape);
                     v.setVisibility(View.VISIBLE);
                 default:
                     break;
             }
             return true;
         }
     }
}
