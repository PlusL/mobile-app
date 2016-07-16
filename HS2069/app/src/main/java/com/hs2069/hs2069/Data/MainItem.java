package com.hs2069.hs2069.Data;

import android.graphics.Bitmap;

/**
 * Created by edward2414 on 7/15/2016.
 */
public class MainItem {
    private String title;
    private String content;
    private String price;
    private Bitmap bitmap;

    public MainItem(){

    }

    public MainItem(String title, String content, String price, Bitmap bitmap){
        this.title = title;
        this.content = content;
        this.price = price;
        this.bitmap = bitmap;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getContent(){
        return this.content;
    }

    public void setPrice(String price){
        this.price = price;
    }

    public String getPrice(){
        return this.price;
    }

    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap(){
        return this.bitmap;
    }
}
