package com.hs2069.hs2069.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.hs2069.hs2069.Adapter.ModifyAdapter;
import com.hs2069.hs2069.Adapter.PersonInfoAdapter1;
import com.hs2069.hs2069.Data.ModifyItem1;
import com.hs2069.hs2069.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by edward2414 on 7/11/2016.
 */
public class ModifyFragment extends android.support.v4.app.Fragment  {
    private static final String[] strs = {"头像", "昵称", "身份验证", "支付信息", "收货地址"};
    private static final int[] imgs = {R.drawable.ic_action_head, R.drawable.ic_action_head, R.drawable.ic_action_next_item, R.drawable.ic_action_next_item, R.drawable.ic_action_next_item};
    ArrayList<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
    private ListView lv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify, container, false);
        for(int i = 0; i < 5; i++)
        {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("title", strs[i]);
            item.put("image", imgs[i]);
            if(i == 1) item.put("nickname", "撒比狗");
            dataList.add(item);
            //ModifyItem1 mModifyItem1 = new ModifyItem1(strs[i], imgs[i])
        }
        lv = (ListView)view.findViewById(R.id.fragment_modify_lv);
        lv.setAdapter(new ModifyAdapter(dataList));
        return view;
    }

}
