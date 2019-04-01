package org.yczbj.ycrefreshview.five;

import android.content.Context;
import android.view.ViewGroup;


import org.yczbj.ycrefreshview.data.AdData;
import org.yczbj.ycrefreshview.data.PersonData;
import org.yczbj.ycrefreshview.first.PersonViewHolder;
import org.yczbj.ycrefreshview.seven.AdViewHolder;
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.holder.BaseViewHolder;

import java.security.InvalidParameterException;


public class PersonWithAdAdapter extends RecyclerArrayAdapter<Object> {

    public static final int TYPE_INVALID = 0;
    public static final int TYPE_AD = 1;
    public static final int TYPE_PERSON = 2;
    public PersonWithAdAdapter(Context context) {
        super(context);
    }

    @Override
    public int getViewType(int position) {
        if(getItem(position) instanceof AdData){
            return TYPE_AD;
        }else if (getItem(position) instanceof PersonData){
            return TYPE_PERSON;
        }
        return TYPE_INVALID;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_PERSON:
                return new PersonViewHolder(parent);
            case TYPE_AD:
                return new AdViewHolder(parent);
            default:
                throw new InvalidParameterException();
        }
    }
}
