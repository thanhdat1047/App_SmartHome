package com.example.pbl5demo1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MenuAdapter extends BaseAdapter {
    public MenuAdapter(Context context, int layout, List<itemMenu> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    private Context context;
    private  int layout;
    private List<itemMenu> list;
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class ViewHolder{
        TextView tv;
        ImageView imgv;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null ){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            viewHolder = new ViewHolder();

            viewHolder.tv = (TextView) view.findViewById(R.id.nameIcon);
            viewHolder.imgv = (ImageView) view.findViewById(R.id.imgIcon);

            view.setTag(viewHolder);

        }
        else {
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.tv.setText(list.get(i).nameIcon);
        viewHolder.imgv.setImageResource(list.get(i).icon);
        return view;
    }
}
