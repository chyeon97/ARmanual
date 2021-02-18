package com.example.tjsdh.armanual;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.DecimalFormat;
public class ListAdapter2 extends RecyclerView.Adapter<ListAdapter2.ProductViewHolder> {
    private Context mCtx;
    private ItemData itemdata;
    DecimalFormat price_format;
  //  ArrayList<String> itemname_rm= new ArrayList<>();
    String itemname_rm2 ,itemname_rm ;
    public ListAdapter2(Context mCtx,ItemData itemData){
        price_format= new DecimalFormat("###,###");
        this.mCtx=mCtx;
        this.itemdata = itemData;
    }

    @Override
    public ProductViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view= inflater.inflate(R.layout.listview_item2,null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ProductViewHolder productViewHolder, int i) {
        for (int name = 0; name < itemdata.itemName.size(); name++) //아이템 이름
        {
            itemname_rm=itemdata.itemName.get(i).replaceAll("<b>","");
            itemname_rm2 = itemname_rm.replaceAll("</b>","");
            productViewHolder.itemname.setText(itemname_rm2);
        }
        productViewHolder.lowprice.setText(price_format.format(Double.valueOf(itemdata.lowprice.get(i)))+"원");
        productViewHolder.mallname.setText(itemdata.mallname.get(i));
        productViewHolder.img.setImageBitmap(itemdata.Image.get(i));

    }

    @Override
    public int getItemCount() {
        return itemdata.itemName.size();
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder  {
        TextView itemname, lowprice, mallname;
        ImageView img;

        public ProductViewHolder(final View itemview){
            super(itemview);

            itemview.setOnClickListener(v -> {
                Log.d("아이템클릭",String.valueOf(getPosition()));
              //  itemdata.link.get(getPosition());
              //  Log.d("uri",itemdata.link.toString());
                   String uri=itemdata.link.get(getPosition());
                Intent intent =  new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                itemview.getContext().startActivity(intent);


            });
            itemname=itemview.findViewById(R.id.itemname);
            lowprice=itemview.findViewById(R.id.price);
            mallname=itemview.findViewById(R.id.mallname);
            img=itemview.findViewById(R.id.img);

        }



    }


    public void Refresh(ItemData itemData){
        this.notifyDataSetChanged();
    }
}
