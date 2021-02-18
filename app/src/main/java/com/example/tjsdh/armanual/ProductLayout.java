package com.example.tjsdh.armanual;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ProductLayout extends Fragment { //제품설명 레이아웃달고 어뎁터달기
    View v;
    TabLayout tabs;
    productPagerAdapter pagerAdapter;
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.product_layout,container,false); // 레이아웃달기

        tabs=v.findViewById(R.id.tab);
        tabs.addTab(tabs.newTab().setText("카테고리"));
        tabs.addTab(tabs.newTab().setText("상품명검색"));
        tabs.setTabGravity(tabs.GRAVITY_FILL);


         ViewPager viewPager = v.findViewById(R.id.viewpage);
         pagerAdapter = new productPagerAdapter(getFragmentManager(), tabs.getTabCount()); //numoftabs=>탭 수
        viewPager.setAdapter(pagerAdapter);  //어뎁터달기

        tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        return v;
    }



}
