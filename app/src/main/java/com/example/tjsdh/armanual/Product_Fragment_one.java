package com.example.tjsdh.armanual;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;

import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

public class Product_Fragment_one extends Fragment  {
    View v;
    ItemData str =new ItemData() ;
    ItemData re =new ItemData();
    ItemData renew;
    String findvalue; //사용자가 찾는 값
    ImageButton button;
    ListView myListview ;
    ListAdapter myAdapter;
    Thread thread;
    EditText editText;
    InputMethodManager imm; //키보드
    FragmentTransaction transaction , transaction2;
    boolean lastItemVisiblFlag = false; // 리스트 스크롤이 마지막 셀로 이동했는지 체크할 변수
    boolean mLockListview = false; //데이터 갱신 시 중복안되게 하는 변수
    boolean buttonLock = false; // 검색버튼 중복 방지 변수
    boolean reset_res=false; // edit 재검색 시 res 초기화를 위한 변수
    int search_count ; // 검색한 수
    ProgressBar progressBar;
    int i = 1; // 파싱할때 start 변수를 동적으로 바꾸기위한 변수
    int scroll_count = 1; //출력된 리스트뷰를 스크롤한 횟수

    public  final int Thread_STOP = 1;

    final ListviewHandler handler = new ListviewHandler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.product_fragment_one, container, false);
        editText = v.findViewById(R.id.et); //유저가 입력한 내용
        button= v.findViewById(R.id.button_);

        myListview = v.findViewById(R.id.list_view);
        myAdapter = new ListAdapter(re); // 어댑터 생성
        Log.d("초기어댑터", myAdapter.data.itemName.toString());
        myListview.setAdapter(myAdapter);

         progressBar=v.findViewById(R.id.progressbar);
         progressBar.setVisibility(View.GONE);

        //////////////////검색어 처리//////////////////////////////////////////////////

       // editText.setInputType(EditorInfo.TYPE_NULL); // 초기 editText 커서깜빡이 제거

        editText.setOnTouchListener(new View.OnTouchListener() { //editText 클릭 이벤트
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                buttonLock=true; //검색버튼을 클릭 할 수 있도록 함.
                Log.d("버튼불린값:",String.valueOf(buttonLock));

                //    ((EditText) v).setInputType(EditorInfo.TYPE_CLASS_TEXT); //editText클릭 시 커서 깜빡임

                return false;
            }

        });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(buttonLock==true){
                        scroll_count=1; //검색 버튼을 누르면 스크롤카운트는 1로 초기화
                        Log.d("스크롤 횟수",String.valueOf(scroll_count));
                        search_count++; //검색 버튼 클릭마다 카운트 증가(재검색을 위함)
                        Log.d("검색버튼 카운트",String.valueOf(search_count));
                        if(search_count>1)  reset_res=true;

                        imm=(InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);   //입력받는 방법을 관리하는 Manager객체를 요청해 InputMethodmanager에 반환
                        imm.hideSoftInputFromWindow(editText.getWindowToken(),0);  // 첫번째 매개변수에 해당하는 곳에 키보드가 생기면 키보드를 숨긴다.
                        getNaverSearch.start=1;
                        getNaverSearch.display=20; //한 번 파싱하면 20개 출력
                        progressBar.setVisibility(View.VISIBLE);
                        performSearch();
                        buttonLock=false; //검색버튼 막음
                }
            }
        });

        myListview.setOnScrollListener(new AbsListView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) { //리스트뷰의 스크롤이 하단에 닿았을 경우 실행
                if(scrollState==AbsListView.OnScrollListener.SCROLL_STATE_IDLE&& lastItemVisiblFlag&&mLockListview==false ){
                    Log.d("데이터 갱신","실행");
                    getNaverSearch.start = (i*20) +i;
                    scroll_count++; //스크롤카운트 증가
                    mLockListview=true;
                   progressBar.setVisibility(View.VISIBLE); //로딩중을 알리는 프로그레스바 보이기

                    performSearch();
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    lastItemVisiblFlag=(totalItemCount>0) && (firstVisibleItem+visibleItemCount>=totalItemCount);
                }

        });
        myListview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("클릭",re.link.get(position).toString());
                String  uri=re.link.get(position);
                Intent intent =  new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });
        return v;
    }

    class ListviewHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Thread_STOP: // 스크롤 갱신
                    this.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE); //프로세스바 안보이기
                            str.Image.removeAll(str.Image);
                            str.itemName.removeAll(str.itemName);
                            str.mallname.removeAll(str.mallname);
                            str.lowprice.removeAll(str.lowprice);
                            str.link.removeAll(str.link);
                        }
                    },1000); //1초간
                    if(scroll_count==1){
                        myListview.setSelection(0);
                        scroll_count++;
                    }
                    else{
                        myListview.setAdapter(myAdapter);
                        myListview.setSelection((20*scroll_count)-1);
                    }
                    transaction =getFragmentManager().beginTransaction();
                    transaction.detach(Product_Fragment_one.this).attach(Product_Fragment_one.this).commit();
                    mLockListview=false;
                    Log.d("handler","성공");
                    break;


                default:
                    break;
            }
        }

    }

    public void performSearch() {
        findvalue = editText.getText().toString();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    getNaverSearch getNaverSearch = new getNaverSearch(); //api 가져오는 메소드
                    str = getNaverSearch.getAPI(findvalue);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(search_count==1){ //처음 검색한 값
                                re.itemName.addAll(str.itemName);
                                re.mallname.addAll(str.mallname);
                                re.lowprice.addAll(str.lowprice);
                                re.Image.addAll(str.Image);
                                re.link.addAll(str.link);
                                myAdapter.Refresh(re);
                                Message msg = handler.obtainMessage(Thread_STOP); //핸들러에 msg 보내기
                                handler.sendMessage(msg);
                            }
                            if(search_count>1 && reset_res) { //재검색 시 res 초기화 할 경우
                                re.Image.removeAll(re.Image);
                                re.itemName.removeAll(re.itemName);
                                re.mallname.removeAll(re.mallname);
                                re.lowprice.removeAll(re.lowprice);
                                re.link.removeAll(re.link);
                                myAdapter.Refresh(re);
                                reset_res=false; //초기화함
                            }
                            if(search_count>1 && !reset_res) //재검색한 값 스크롤 갱신
                            {
                                re.itemName.addAll(str.itemName);
                                re.mallname.addAll(str.mallname);
                                re.lowprice.addAll(str.lowprice);
                                re.Image.addAll(str.Image);
                                re.link.addAll(str.link);
                                myAdapter.Refresh(re);

                                Message msg3 = handler.obtainMessage(Thread_STOP);
                                handler.sendMessage(msg3);
                            }



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


}


