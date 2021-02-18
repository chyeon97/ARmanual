package com.example.tjsdh.armanual;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class Product_Fragment_two extends Fragment {
    View v,v1;
     static String find_item; //찾을 상품
    ImageButton motherboardBtn,cpuBtn,ramBtn,grapicBtn,oddBtn,ssdBtn,hddBtn,powerBtn,keyboardBtn,caseBtn,coolerBtn,mouseBtn;
    FragmentManager fm;
    FragmentTransaction ft;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.product_fragment_two,container,false);
         final Product_Fragment_three product_fragment_three = new Product_Fragment_three();
        motherboardBtn = v.findViewById(R.id.motherboard);
        motherboardBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                find_item="메인보드";
                fm=getFragmentManager();
                ft=fm.beginTransaction();
                ft.replace(R.id.tablelayout,product_fragment_three).commit();

            }
        });

        cpuBtn=v.findViewById(R.id.cpu);
        cpuBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                find_item="cpu";
                fm=getFragmentManager();
                ft=fm.beginTransaction();
                ft.replace(R.id.tablelayout,product_fragment_three).commit();
                }
        });

        ramBtn=v.findViewById(R.id.ram);
        ramBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                find_item="ram";
                fm=getFragmentManager();
                ft=fm.beginTransaction();
                ft.replace(R.id.tablelayout,product_fragment_three).commit();
            }
        });

        grapicBtn=v.findViewById(R.id.grapiccard);
        grapicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                find_item="그래픽카드";
                fm=getFragmentManager();
                ft=fm.beginTransaction();
                ft.replace(R.id.tablelayout,product_fragment_three).commit();
            }
        });

        oddBtn=v.findViewById(R.id.odd);
        oddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                find_item="odd";
                fm=getFragmentManager();
                ft=fm.beginTransaction();
                ft.replace(R.id.tablelayout,product_fragment_three).commit();
            }
        });

        ssdBtn=v.findViewById(R.id.ssd);
        ssdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                find_item="ssd";
                fm=getFragmentManager();
                ft=fm.beginTransaction();
                ft.replace(R.id.tablelayout,product_fragment_three).commit();
            }
        });

        hddBtn=v.findViewById(R.id.hdd);
        hddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                find_item="hdd";
                fm=getFragmentManager();
                ft=fm.beginTransaction();
                ft.replace(R.id.tablelayout,product_fragment_three).commit();
            }
        });

        powerBtn=v.findViewById(R.id.power);
        powerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                find_item="power";
                fm=getFragmentManager();
                ft=fm.beginTransaction();
                ft.replace(R.id.tablelayout,product_fragment_three).commit();
            }
        });

        keyboardBtn=v.findViewById(R.id.keyboard);
        keyboardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                find_item="keyboard";
                fm=getFragmentManager();
                ft=fm.beginTransaction();
                ft.replace(R.id.tablelayout,product_fragment_three).commit();
            }
        });

        caseBtn=v.findViewById(R.id.case_);
        caseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                find_item="본체";
                fm=getFragmentManager();
                ft=fm.beginTransaction();
                ft.replace(R.id.tablelayout,product_fragment_three).commit();
            }
        });
        coolerBtn=v.findViewById(R.id.cooler);
        coolerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                find_item="cooler";
               fm=getFragmentManager();
                ft=fm.beginTransaction();
                ft.replace(R.id.tablelayout,product_fragment_three).commit();
            }
        });

        mouseBtn=v.findViewById(R.id.mouse);
        mouseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                find_item="mouse";
               fm=getFragmentManager();
                ft=fm.beginTransaction();
                ft.replace(R.id.tablelayout,product_fragment_three).commit();
            }
        });


        return  v;



    }


}
