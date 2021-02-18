package com.example.tjsdh.armanual;

import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Product_Fragment_three extends Fragment {
    View v;
    Thread thread;
    ItemData itemData2= new ItemData();
    ItemData re = new ItemData();
    RecyclerView recyclerView;
    ListAdapter2 listAdapter2;
    ImageButton img;
    FragmentManager fm;
    FragmentTransaction ft;
    final Product_Fragment_two product_fragment_two = new Product_Fragment_two();
    int i=1;
    int scroll_count=1;
    ListviewHandler listviewHandler = new ListviewHandler();
    private  int overallXScroll=0;
    int lastVisibleItmePosition;
    int itemTotalCount;
    public boolean mLockListview=false;
    public final int Thread_STOP=1;
    ProgressBar progressBar;
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.product_fragment_three,container,false);
        progressBar=v.findViewById(R.id.progressbar2);
        progressBar.setVisibility(View.GONE); //프로세스바 안보이기

        img=(ImageButton)v.findViewById(R.id.backbtn);
        recyclerView=(RecyclerView)v.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listAdapter2=new ListAdapter2(getActivity(),itemData2);
        recyclerView.setAdapter(listAdapter2); //어댑터달기

        getNaverSearch.start=1;
        getNaverSearch.display=20; //한 번 파싱하면 20개 출력
        progressBar.setVisibility(View.VISIBLE);
        performSearch();
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged( RecyclerView recyclerView, int newState) {


                lastVisibleItmePosition=((LinearLayoutManager)recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                itemTotalCount=recyclerView.getAdapter().getItemCount()-1;

                if (lastVisibleItmePosition == itemTotalCount && mLockListview==false) {
                    mLockListview=true;
                    Log.d("스크롤하단","true");
                    scroll_count++;
                    Log.d("스크롤 횟수", String.valueOf(scroll_count));
                    getNaverSearch.start = (i*20) +i;
                    progressBar.setVisibility(View.VISIBLE);
                    performSearch();
                }

            }

            @Override
            public void onScrolled( RecyclerView recyclerView, int dx, int dy) {
                overallXScroll = overallXScroll +dy;
                Log.d("스크롤 위치",String.valueOf(overallXScroll));

            }
        });

        img.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(),"뒤로가기",Toast.LENGTH_LONG).show();
                 fm = getFragmentManager();
                 ft=fm.beginTransaction();
                ft.replace(R.id.framelayout,product_fragment_two).commit();
            }
        });





        return v;
    }

    public void performSearch() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    getNaverSearch getNaverSearch = new getNaverSearch(); //api 가져오는 메소드
                     re = getNaverSearch.getAPI(Product_Fragment_two.find_item);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                                itemData2.itemName.addAll(re.itemName);
                                itemData2.mallname.addAll(re.mallname);
                                itemData2.lowprice.addAll(re.lowprice);
                                itemData2.Image.addAll(re.Image);
                                itemData2.link.addAll(re.link);
                                listAdapter2.Refresh(itemData2);

                               Log.d("어댑터수",String.valueOf(listAdapter2.getItemCount()));
                                Log.d("어댑터 내용", itemData2.itemName.toString());
                                Message msg = listviewHandler.obtainMessage(Thread_STOP); //핸들러에 msg 보내기
                                listviewHandler.sendMessage(msg);

                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        });
        thread.setDaemon(true);
        thread.start();
        i++;
    }

    class ListviewHandler extends Handler {
        /*private  final WeakReference<Product_Fragment_one> weekReference;
        public ListviewHandler(Product_Fragment_one m){
            weekReference=new WeakReference<Product_Fragment_one>(m);
        }*/

       @Override
        public void handleMessage(Message msg) {
            //       fragement = weekReference.get();
            switch (msg.what){
                case Thread_STOP: // 스크롤 갱신
                    this.postDelayed(new Runnable() {
                       @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE); //프로세스바 안보이기
                           if(scroll_count==1){
                                   recyclerView.getLayoutManager().scrollToPosition(0);
                               //scroll_count++;
                           }
                           else{
                               recyclerView.getLayoutManager().scrollToPosition(overallXScroll);
                           }
                       }
                   },1000); //1초간
                mLockListview=false;



                    Log.d("handler","성공");
                    break;


                default:
                    break;
            }
        }

    }

}
