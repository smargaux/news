package com.example.msmits.helloworld;

/**
 * Created by msmits on 14/03/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class myAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private final ArrayList<News> values;
    private final OnListItemClickListener listener;
    public final int highlight_news = 1;
    public final int simple_news = 0;
    public myAdapter(ArrayList<News> values,OnListItemClickListener listener) {
        this.values = values;
        this.listener = listener;
    }
    public int getItemCount() {
        return values.size();
    }
    public RecyclerView.ViewHolder
    onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==highlight_news){
            LayoutInflater inflater =
                    LayoutInflater.from(parent.getContext());
            return new MyViewHolder(inflater.inflate(
                    R.layout.highlight_news, parent, false
            ),this.listener);
        }else{
            LayoutInflater inflater =
                    LayoutInflater.from(parent.getContext());
            return new MyViewHolder(inflater.inflate(
                    R.layout.simple_news, parent, false
            ),this.listener);
        }

    }
    public int getItemViewType(int position) {

        if (this.values.get(position).getHighlight()) {
            return highlight_news; }
        return simple_news;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView titleView;
        private TextView descriptionView;
        private TextView commentsView;
        private final OnListItemClickListener listener;
        MyViewHolder(View itemView,OnListItemClickListener l) {
            super(itemView);
            titleView = (TextView)itemView.findViewById(R.id.title);
            descriptionView=(TextView)itemView.findViewById(R.id.content);
            commentsView=(TextView)itemView.findViewById(R.id.nb_comments);
            titleView.setOnClickListener(this);
            listener = l;

        }
        public void onClick(View v) {
            Log.i("Position",String.valueOf(getAdapterPosition()));
            listener.onHeaderClicked(getAdapterPosition());
            listener.onItemClicked(getAdapterPosition());

        }
        void bindValue(String title,String description,int comments) {
            titleView.setText(title);
            descriptionView.setText(description);
            commentsView.setText(String.valueOf(comments));


        }

    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder,
                                 int position) {
        MyViewHolder highlightHolder = (MyViewHolder) holder;
        MyViewHolder simpleHolder = (MyViewHolder) holder;

        int viewType = getItemViewType(position);
        if (viewType == highlight_news) {
            highlightHolder.bindValue(values.get(position).getTitle(),values.get(position).getDescription(),values.get(position).getComments());

        } else {
            simpleHolder.bindValue(values.get(position).getTitle(),values.get(position).getDescription(),values.get(position).getComments());

        }
    }



}