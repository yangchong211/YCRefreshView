package org.yczbj.ycrefreshview.data;



import android.content.res.TypedArray;

import org.yczbj.ycrefreshview.R;
import org.yczbj.ycrefreshview.app.BaseApp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DataProvider {


    private static ArrayList<Integer> getData(){
        ArrayList<Integer> data = new ArrayList<>();
        TypedArray bannerImage = BaseApp.getApp().getResources().obtainTypedArray(R.array.image_girls);
        for (int i = 0; i < 16 ; i++) {
            int image = bannerImage.getResourceId(i, R.drawable.girl2);
            data.add(image);
        }
        bannerImage.recycle();
        return data;
    }

    public static List<PersonData> getPersonList(int size){
        if (size==0){
            size = 16;
        }
        ArrayList<PersonData> arr = new ArrayList<>();
        ArrayList<Integer> data = getData();
        for (int i=0 ; i< size ; i++){
            PersonData person = new PersonData();
            person.setName("小杨逗比"+i);
            person.setImage(data.get(i));
            person.setSign("杨充"+i);
            arr.add(person);
        }
        return arr;
    }

    public static List<AdData> getAdList(){
        ArrayList<AdData> arr = new ArrayList<>();
        ArrayList<Integer> data = getData();
        for (int i=0 ; i<data.size() ; i++){
            AdData ad = new AdData();
            ad.setDrawable(data.get(i));
            arr.add(ad);
        }
        return arr;
    }

    public static List<Object> getPersonWithAds(int page){
        ArrayList<Object> arrAll = new ArrayList<>();
        List<AdData> arrAd = getAdList();
        int index = 0;
        for (PersonData person : getPersonList(page)) {
            arrAll.add(person);
            //按比例混合广告
            if (Math.random()<0.2){
                arrAll.add(arrAd.get(index%arrAd.size()));
                index++;
            }
        }
        return arrAll;
    }

    private static final PictureData[] VIRTUAL_PICTURE = {
            new PictureData(566,800,R.drawable.girl2),
            new PictureData(2126,1181,R.drawable.girl11),
            new PictureData(1142,800,R.drawable.girl12),
            new PictureData(550,778,R.drawable.girl3),
            new PictureData(1085,755,R.drawable.girl4),
            new PictureData(656,550,R.drawable.girl5),
            new PictureData(1920,938,R.drawable.girl6),
            new PictureData(1024,683,R.drawable.girl7),
            new PictureData(723,1000,R.drawable.girl9),
            new PictureData(2000,1667,R.drawable.girl10),
            new PictureData(723,1000,R.drawable.girl13),
            new PictureData(2000,1667,R.drawable.girl14),
    };


    public static ArrayList<PictureData> getPictures(){
        ArrayList<PictureData> arrayList = new ArrayList<>();
        arrayList.addAll(Arrays.asList(VIRTUAL_PICTURE));
        return arrayList;
    }

    public static final int[] NARROW_IMAGE = {
            R.drawable.bg_small_autumn_tree_min,
            R.drawable.bg_small_kites_min,
            R.drawable.bg_small_lake_min,
            R.drawable.bg_small_leaves_min,
            R.drawable.bg_small_magnolia_trees_min,
            R.drawable.bg_small_solda_min,
            R.drawable.bg_small_tree_min,
            R.drawable.bg_small_tulip_min,
            R.drawable.bg_small_kites_min,
    };

    public static ArrayList<Integer> getNarrowImage(int page){
        ArrayList<Integer> arrayList = new ArrayList<>();
        if (page == 4) {
            return arrayList;
        }
        for (int i = 0; i < NARROW_IMAGE.length; i++) {
            arrayList.add(NARROW_IMAGE[i]);
        }
        return arrayList;
    }

    public static ArrayList<String> getTag(){
        ArrayList<String> list = new ArrayList<>();
        list.add("杨充");
        list.add("小样逗比");
        list.add("你过得怎样");
        list.add("996加班");
        list.add("工作太辛苦");
        list.add("潇湘剑雨");
        list.add("充哥");
        list.add("GitHub");
        list.add("你是个傻逼");
        list.add("逗比哈");
        list.add("为什么这样");
        list.add("工作太辛苦");
        list.add("潇湘剑雨");
        list.add("充哥");
        list.add("GitHub");
        list.add("你过得怎样");
        list.add("996加班");
        list.add("工作太辛苦");
        list.add("潇湘剑雨");
        list.add("充哥");
        list.add("GitHub");
        return list;
    }

}
