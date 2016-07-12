package com.hs2069.hs2069.Data;

import com.hs2069.hs2069.Adapter.ModifyAdapter;

/**
 * Created by edward2414 on 7/11/2016.
 */
public class ModifyItem1 {

    private int img;
    private String title;

    public ModifyItem1(String title, int img){
        this.img = img;
        this.title = title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

    public void setImg(int img){
        this.img = img;
    }

    public int getImg(){
        return this.img;
    }

}
