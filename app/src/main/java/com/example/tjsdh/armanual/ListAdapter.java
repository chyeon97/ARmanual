package com.example.tjsdh.armanual;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.DecimalFormat;

public class ListAdapter extends BaseAdapter {
    LayoutInflater inflater =null;
    public ItemData data = new ItemData();
    private  int ListCount;
    ViewHolder viewHolder;
    private  class ViewHolder{
        ImageView imageView;
        TextView item_name;
        TextView low_price;
        TextView mall_name;
    }

    DecimalFormat moneyFormat;
    String itemname_rm;
    String itemname_rm2;

   public ListAdapter(ItemData input) { //생성자

         moneyFormat = new DecimalFormat("###,###");

        for (int name = 0; name < input.itemName.size(); name++) //아이템 이름
        {
            itemname_rm = input.itemName.get(name).replaceAll("<b>", "");
            itemname_rm2 = itemname_rm.replaceAll("</b>", "");
            data.itemName.add(itemname_rm2);
        }

        for (int lprice = 0; lprice < input.lowprice.size(); lprice++)  // 가격
        {
            data.lowprice.add(moneyFormat.format(Double.valueOf(input.lowprice.get(lprice))));
        }

        for (int m_name = 0; m_name < input.mallname.size(); m_name++) // 쇼핑몰 이름
            data.mallname.add(input.mallname.get(m_name));
        for (int image = 0; image < input.Image.size(); image++) //이미지
            data.Image.add(input.Image.get(image));

        Log.d("data내용 아이템이름", data.itemName.toString());
        Log.d("data내용 가격", data.lowprice.toString());
        Log.d("data내용 쇼핑몰", data.mallname.toString());

        ListCount = input.itemName.size();
    }


   @Override
    public int getCount() {
        return ListCount;
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
            if(v ==null){
                final Context context =parent.getContext();
                if(inflater==null){
                    inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                }
                viewHolder = new ViewHolder();
                v=inflater.inflate(R.layout.listview_item2,parent,false);
                viewHolder.item_name =(TextView)v.findViewById(R.id.itemname);
                viewHolder.low_price = (TextView)v.findViewById(R.id.price);
                viewHolder.mall_name = (TextView)v.findViewById(R.id.mallname);
                viewHolder.imageView = (ImageView)v.findViewById(R.id.img);

                v.setTag(viewHolder);
            }else{
                viewHolder=(ViewHolder)v.getTag();
            }
            viewHolder.imageView.setImageBitmap(data.Image.get(position));
            viewHolder.item_name.setText(data.itemName.get(position));  // 아이템이름:
            viewHolder.low_price.setText(data.lowprice.get(position));  // 가격:
            viewHolder.low_price.append("원");
            viewHolder.mall_name.setText(data.mallname.get(position));  // 쇼핑몰:
        return  v;
    }

    public ListAdapter  Refresh(ItemData itemData){ //어댑터 갱신을 위한 메소드
        this.notifyDataSetChanged();
        return this;
    }

}
