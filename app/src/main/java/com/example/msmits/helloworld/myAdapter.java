package com.example.msmits.helloworld;

/**
 * Created by msmits on 14/03/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class myAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private final ArrayList<Post> posts;
    private final OnListItemClickListener listener;
    public final int highlight_news = 1;
    public final int simple_news = 0;
    public myAdapter(ArrayList<Post> posts,OnListItemClickListener listener) {
        this.posts= posts;
        this.listener = listener;
    }
    public int getItemCount() {
        return posts.size();
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

        /*if (this.posts.get(position).getHighlight()) {
            return highlight_news; }*/
        return simple_news;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView titleView;
        private TextView descriptionView;
        private TextView commentsView;
        private TextView authorView;

        private ImageView news_picture;
        private final OnListItemClickListener listener;
        private Context context;
        MyViewHolder(View itemView,OnListItemClickListener l) {
            super(itemView);
            context=itemView.getContext();
            titleView = (TextView)itemView.findViewById(R.id.title);
            descriptionView=(TextView)itemView.findViewById(R.id.content);
            commentsView=(TextView)itemView.findViewById(R.id.nb_comments);
            news_picture=(ImageView) itemView.findViewById(R.id.image_news);
            authorView=(TextView) itemView.findViewById(R.id.news_author);
            titleView.setOnClickListener(this);
            this.listener = l;

        }
        public void onClick(View v) {
            Log.i("Position",String.valueOf(getAdapterPosition()));
            this.listener.onHeaderClicked(getAdapterPosition());
            this.listener.onItemClicked(getAdapterPosition());

        }
        void bindValue(String title,String description,int comments, String author,String img_url ) {
            titleView.setText(title);
            descriptionView.setText(Html.fromHtml(description).toString());
            commentsView.setText(String.valueOf(comments));
            authorView.setText(author);
            Picasso.with(context)
                    .load(img_url)
                    .error(R.drawable.ic_share_variant_black_18dp)
                    .into(news_picture);


        }

    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder,
                                 int position) {
        MyViewHolder highlightHolder = (MyViewHolder) holder;
        MyViewHolder simpleHolder = (MyViewHolder) holder;

        int viewType = getItemViewType(position);
        String img_url;
       /* if (viewType == highlight_news) {
            highlightHolder.bindValue(posts.get(position).title,posts.get(position).content,posts.get(position).comments_count,posts.get(position).thumbnail);

        } else {
            simpleHolder.bindValue(posts.get(position).title,posts.get(position).content,posts.get(position).comments_count,posts.get(position).thumbnail);

        }*/
        if(posts.get(position).thumbnail==null){
            img_url="http://i.imgur.com/RRUe0Mo.png";
        }else{
            img_url=posts.get(position).thumbnail;
        }
        simpleHolder.bindValue(posts.get(position).title,posts.get(position).content,posts.get(position).comments_count,posts.get(position).author.name,img_url);

    }



}