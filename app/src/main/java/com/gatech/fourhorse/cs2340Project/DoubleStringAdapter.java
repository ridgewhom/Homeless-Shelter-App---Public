package com.gatech.fourhorse.cs2340Project;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


class DoubleStringAdapter extends ArrayAdapter<GenericDoubleString> {

    private final Context mContext;
    private final int mResource;


    public DoubleStringAdapter(@NonNull Context context,
                               @NonNull List<GenericDoubleString> objects) {
        super(context, R.layout.adapter_view_layout, objects);
        mContext = context;
        mResource = R.layout.adapter_view_layout;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View convertView1 = convertView;
        String str1= "";

        String str2= "";
        try {
            if((position < getCount()) && (position >= 0)) {

                if (getItem(position) != null) {
                    GenericDoubleString s = getItem(position);
                    if (s != null) {
                        str1 = s.getStr1();
                        str2 = s.getStr2();
                    }
                }
            }
        } catch (Exception ignored){

        }
        //GenericDoubleString doubleSTR = new GenericDoubleString(str1, str2);
        if (convertView1 == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView1 = inflater.inflate(mResource, parent, false);
        }
        TextView tv1 = convertView1.findViewById(R.id.textView1);
        TextView tv2 = convertView1.findViewById(R.id.textView2);

        tv1.setText(str1);
        tv2.setText(str2);

        return convertView1;
    }
}
