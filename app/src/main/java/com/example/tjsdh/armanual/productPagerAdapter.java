package com.example.tjsdh.armanual;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class productPagerAdapter extends FragmentPagerAdapter {
    int mNumOfTabs;

    public productPagerAdapter(FragmentManager fm, int numoftabs) {
        super(fm);
        this.mNumOfTabs=numoftabs;
    }

    @Override
    public Fragment getItem(int i) { //제품설명 탭에 해당한 페이지 설정
       switch (i){
           case 0:
               Product_Fragment_two tab1 = new Product_Fragment_two();
               return tab1;
           case 1:
                Product_Fragment_one tab2 = new Product_Fragment_one();
                return tab2;
               default:
                   return null;
       }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


    @Override
    public int getCount() {
        return mNumOfTabs;
    }




}
