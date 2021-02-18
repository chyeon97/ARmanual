package com.example.tjsdh.armanual;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class getNaverSearch {
    static int start, display;
    //start -> 파싱할 시작값 , display -> 파싱할 갯수

    public ItemData getAPI(String keyword) {
        String ClientID = '';
        String ClientSecret = '';
        String item; //상품명
        String lowprice; //낮은가격
        String mallname; //쇼핑몰이름
        String image; //상품 이미지
        String text; //검색어를 UTF-8 형식으로 변환한 값을 저장
        String link; //상품판매 링크

        boolean startItemTag = false;
        String tag="";
        ItemData itemdata =null ;
        Bitmap bm;
        Log.d("keyword:", keyword);

        try {
            text = URLEncoder.encode(keyword, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/shop.xml?query=" + text + "&display=" + display + "&start=" + start;

          //  Log.d("url",apiURL);

            URL url = new URL(apiURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-Naver-Client-Id", ClientID);
            conn.setRequestProperty("X-Naver-Client-Secret", ClientSecret);

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new InputStreamReader(conn.getInputStream(), "UTF-8"));


            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {

                switch (eventType) {

                    case XmlPullParser.START_DOCUMENT:  itemdata = new ItemData(); break; //생성
                    case XmlPullParser.END_DOCUMENT: break;
                    case XmlPullParser.START_TAG:
                        tag = xpp.getName(); //태그 이름 얻어오기


                       if (tag.equals("item")==true){
                           startItemTag =true;

                       }
                       break;

                       case XmlPullParser.TEXT:
                           if(startItemTag==false) break;

                           if (tag.equals("title")) {
                                item = xpp.getText();

                                Log.d("아이템이름:", item);

                                itemdata.itemName.add(item);


                            } else if (tag.equals("lprice")) {
                                lowprice = xpp.getText();
                                itemdata.lowprice.add(lowprice);

                                Log.d("낮은가격:", lowprice);


                            } else if (tag.equals("mallName")) {
                                mallname = xpp.getText();
                                itemdata.mallname.add(mallname);

                                Log.d("쇼핑몰:", mallname);


                            }
                           else if (tag.equals("link")) {
                               link = xpp.getText();
                               itemdata.link.add(link);

                               Log.d("링크", link);
                           }


                           else if (tag.equals("image")) {
                               image = xpp.getText();

                               URL image_url = new URL(image);
                               HttpURLConnection con = (HttpURLConnection)image_url.openConnection();
                               con.setDoInput(true);
                               con.connect();

                              InputStream is = image_url.openStream();
                               bm = BitmapFactory.decodeStream(is);
                               itemdata.Image.add(bm);
                                Log.d("이미지",image);
                            }

                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName();
                        if(tag.equals("item")==true){
                            startItemTag=false;

                        }
                        break;
                }

                eventType = xpp.next();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    return  itemdata;

    }
}
