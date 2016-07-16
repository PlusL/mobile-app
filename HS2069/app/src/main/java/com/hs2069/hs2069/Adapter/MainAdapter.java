package com.hs2069.hs2069.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hs2069.hs2069.Data.MainItem;
import com.hs2069.hs2069.MainActivity;
import com.hs2069.hs2069.R;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.util.ArrayList;

/**
 * Created by edward2414 on 7/14/2016.
 */
public class MainAdapter extends BaseAdapter {
    private ArrayList<MainItem> mData;

    public MainAdapter(ArrayList<MainItem> mData) {
        super();
        this.mData = mData;
    }

    @Override
    public int getCount(){
        return mData.size();
    }

    @Override
    public Object getItem(int position){
        return mData.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_main, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.iv = (ImageView) convertView.findViewById(R.id.list_main_iv);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.list_main_name);
            viewHolder.tv_content = (TextView) convertView.findViewById(R.id.list_main_content);
            viewHolder.tv_price = (TextView) convertView.findViewById(R.id.list_main_price);
            convertView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder)convertView.getTag();;
        holder.iv.setImageBitmap(mData.get(position).getBitmap());
        holder.tv_name.setText(mData.get(position).getTitle());
        holder.tv_content.setText(mData.get(position).getContent());
        holder.tv_price.setText("CNY " + mData.get(position).getPrice());
        return convertView;
    }

    private static class ViewHolder{
        public ImageView iv;
        public TextView tv_name;
        public TextView tv_content;
        public TextView tv_price;
    }
}
