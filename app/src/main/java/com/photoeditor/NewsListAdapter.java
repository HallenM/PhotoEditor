package com.photoeditor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.BlurTransformation;
import jp.wasabeef.picasso.transformations.GrayscaleTransformation;
import jp.wasabeef.picasso.transformations.gpu.SepiaFilterTransformation;

public class NewsListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<NewsList> newsArrayList;
    private Integer stateIMG = 0;

    public NewsListAdapter(Context context, ArrayList<NewsList> newsArrayList, Integer stateIMG) {

        this.context = context;
        this.newsArrayList = newsArrayList;
        this.stateIMG = stateIMG;
    }

    public int getStateIMG() {
        return stateIMG;
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
        if (stateIMG == 0) {
            Picasso.get().load(newsArrayList.get(position).getImageLink()).into(holder.image);
        } else if (stateIMG == 1) {
            Picasso.get().load(newsArrayList.get(position).getImageLink()).transform(new GrayscaleTransformation()).into(holder.image);
        } else if (stateIMG == 2) {
            Picasso.get().load(newsArrayList.get(position).getImageLink()).transform(new BlurTransformation(context)).into(holder.image);
        }

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
