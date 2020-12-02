package com.photoeditor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

public class NewsListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<NewsList> newsArrayList;

    public NewsListAdapter(Context context, ArrayList<NewsList> newsArrayList) {

        this.context = context;
        this.newsArrayList = newsArrayList;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return newsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            // Раздуваем макет от родителя,(parent) хотим получить его контекст
            // Вне этого будем печатать(inflate) и раздувать макет элемента, который мы создали (R.layout.item_list)
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_list, null, true);

            // Передача родительского эл-та, чтобы завершить передачу параметров(установка информации к новому ViewHolder, который мы недавно создали)
            holder.title = convertView.findViewById(R.id.textListItem);
            holder.image = convertView.findViewById(R.id.imageListItem);
            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.title.setText(newsArrayList.get(position).getHeader());
        Picasso.get().load(newsArrayList.get(position).getImageLink()).into(holder.image);

        return convertView;
    }

    public void clearData() {
        // clear the data
        newsArrayList.clear();
    }

    class ViewHolder {

        protected TextView title;
        protected ImageView image;

    }
}
