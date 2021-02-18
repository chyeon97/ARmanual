package com.example.tjsdh.armanual;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class HomeLayout  extends Fragment {

    View v;
    ViewFlipper imgFlipper;
    float down_x;
    float up_x;
    private GestureDetector detecture;

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
       v=inflater.inflate(R.layout.content_navigation,container,false);
        //return super.onCreateView(inflater, container, savedInstanceState);

    int[] sliders={
            R.drawable.img_home1,
            R.drawable.img_home2,
            R.drawable.img_home3
    };

        imgFlipper=v.findViewById(R.id.imgFlipper);

        for(int slide: sliders){
            sliderFlipper(slide);
        }
        return  v;
    }

    public void sliderFlipper(int image){
        ImageView imageView = new ImageView(getActivity().getApplicationContext());
        imageView.setBackgroundResource(image);

        imgFlipper.addView(imageView);
        imgFlipper.setFlipInterval(4000);
        imgFlipper.setAutoStart(true);
        imgFlipper.setInAnimation(getActivity().getApplicationContext(), android.R.anim.fade_in);
        imgFlipper.setOutAnimation(getActivity().getApplicationContext(), android.R.anim.fade_out);

        imgFlipper.setOnTouchListener(new View.OnTouchListener()
         {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if(v != imgFlipper)
                    return false;
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    down_x = event.getX();
                }
                else if (event.getAction()==MotionEvent.ACTION_UP) {
                    up_x = event.getX();

                    if(up_x < down_x){ //오->왼
                        imgFlipper.setInAnimation(AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.push_left_in));
                        imgFlipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.push_left_out));

                        imgFlipper.showNext();
                    } else if(up_x > down_x){ //왼->오
                        imgFlipper.setInAnimation(AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.push_right_in));
                        imgFlipper.setOutAnimation(AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.push_right_out));

                        imgFlipper.showPrevious();

                    }

                }
                return true;
            }

         });
    }
}
