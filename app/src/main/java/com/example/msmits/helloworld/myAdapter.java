package com.example.msmits.helloworld;

/**
 * Created by msmits on 14/03/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
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
        private ImageButton addToFaves;
        private ImageButton shareNews;
        private ImageView news_picture;
        private ShareActionProvider shareActionProviderButton;
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
            addToFaves=(ImageButton) itemView.findViewById(R.id.addBookmark);
            shareNews=(ImageButton) itemView.findViewById(R.id.news_share);

            titleView.setOnClickListener(this);

            this.listener = l;

        }
        public void onClick(View v) {
            Log.i("Position",String.valueOf(getAdapterPosition()));
            this.listener.onHeaderClicked(getAdapterPosition());
            this.listener.onItemClicked(getAdapterPosition());

        }
        void bindValue(final String title,  String  description , final int comments, String author, String img_url, final int author_id, final String  url ) {
            final String content;
            titleView.setText(title);
            if(description==null){
                content="";
            }else{
                content=description;
            }
            descriptionView.setText(Html.fromHtml(content).toString());
            commentsView.setText(String.valueOf(comments));
            authorView.setText(author);
            Log.i("image ",img_url);
            Picasso.with(context)
                    .load(img_url)
                    .error(R.drawable.ic_share_variant_black_18dp)
                    .into(news_picture);

            addToFaves.setOnClickListener(new View.OnClickListener()   {
                // Au clic sur le bouton "add to favorites", on met à jour l'article dans la base de données ou on l'ajoute
                public void onClick(View v)  {
                    try {
                        SQLiteDatabase db=DataBaseHelper.getInstance(
                                v.getContext().getApplicationContext()).getWritableDatabase();
                        ContentValues favorite= new ContentValues();
                        favorite.put("FAVORITE", true);
                        Log.i("Selected title",title);
                        Cursor cursor=db.query("posts", null, "title = ?",new String[]{title},null,null,null,null);
                        Log.i("Row count",String.valueOf(cursor.getCount()));
                        int result=db.update("posts", favorite,"title = ?",new String[]{title});
                        // Si le post ne fait pas parti de la base de données, on l'ajoute
                        if(result==0){
                            ContentValues post_values= new ContentValues();
                            post_values.put("TITLE", title);
                            post_values.put("DESCRIPTION",Html.fromHtml(content).toString());
                            post_values.put("COMMENTS_COUNT", comments);
                            post_values.put("FAVORITE", 1);
                            post_values.put("AUTHOR_ID", author_id);
                            db.insert("posts",null,post_values);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            shareNews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("share ",title+" : "+url);
                    // Au clic sur le bouton de partage, on partage le titre de l'article ainsi que son url
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, title+" : "+url);
                    sharingIntent.setType("text/plain");
                    v.getContext().startActivity(Intent.createChooser(sharingIntent, v.getResources().getText(R.string.share_to)));
                }
            });
        }

    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder,
                                 int position) {
        MyViewHolder highlightHolder = (MyViewHolder) holder;
        MyViewHolder simpleHolder = (MyViewHolder) holder;

        int viewType = getItemViewType(position);
        String img_url;
        String author_name;
        String url;
        int author_id;
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
        if(posts.get(position).author!=null) {
             author_name =posts.get(position).author.name;
            author_id=posts.get(position).author.id;
        }  else{
             author_name="";
             author_id=0;
        }
        if(posts.get(position).url!=null) {
            url =posts.get(position).url;
        }  else{
            url ="";
        }
        Log.i("Post title",posts.get(position).title+" titrte");
        simpleHolder.bindValue(posts.get(position).title,posts.get(position).content,posts.get(position).comments_count,author_name,img_url,author_id,url);

    }



}