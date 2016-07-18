package com.hs2069.hs2069.Adapter;

import android.media.Image;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.hs2069.hs2069.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 * Created by edward2414 on 7/11/2016.
 */
public class ModifyAdapter extends BaseAdapter {
    private ArrayList<Map<String, Object>> mData;
    public static final int TYPE_EDITTEXT = 0;
    public static final int TYPE_IMAGE = 1;

    public ModifyAdapter(ArrayList<Map<String, Object>> mData) {
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
    public int getItemViewType(int position){
        if(position == 1) return TYPE_EDITTEXT;
        else return TYPE_IMAGE;
    }

    @Override
    public int getViewTypeCount(){ return  2; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        int type = getItemViewType(position);
        if(convertView == null){
            switch(type) {
                case TYPE_IMAGE:
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_modify1, parent, false);
                    ViewHolder1 viewHolder1 = new ViewHolder1();
                    viewHolder1.tv = (TextView) convertView.findViewById(R.id.list_modify1_tv);
                    viewHolder1.iv = (ImageView) convertView.findViewById(R.id.list_modify1_iv);
                    convertView.setTag(viewHolder1);
                    break;
                case TYPE_EDITTEXT:
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_modify2, parent, false);
                    ViewHolder2 viewHolder2 = new ViewHolder2();
                    viewHolder2.tv = (TextView) convertView.findViewById(R.id.list_modify2_tv);
                    viewHolder2.et = (EditText) convertView.findViewById(R.id.list_modify2_et);
                    convertView.setTag(viewHolder2);
                    break;
            }
        }
        switch(type) {
            case TYPE_IMAGE:
                ViewHolder1 holder = (ViewHolder1) convertView.getTag();
                Map<String, Object> map = mData.get(position);
                holder.tv.setText(map.get("title").toString());
                holder.iv.setImageResource(Integer.parseInt(String.valueOf(map.get("image").toString())));
                break;
            case TYPE_EDITTEXT:
                ViewHolder2 holder2 = (ViewHolder2) convertView.getTag();
                Map<String, Object> map2 = mData.get(position);
                holder2.tv.setText(map2.get("title").toString());
                //TODO:保存修改的数据
                holder2.et.setText(map2.get("nickname").toString());
                holder2.et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        EditText et = (EditText) v.findViewById(R.id.list_modify2_et);
                        et.setText(et.getText());
                        return false;
                    }
                });
                break;
        }
        return convertView;
    }

    private static class ViewHolder1{
        public TextView tv;
        public ImageView iv;
    }

    private static class ViewHolder2{
        public TextView tv;
        public EditText et;
    }

}
