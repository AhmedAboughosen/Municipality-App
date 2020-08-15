package com.example.mymunicipalityapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymunicipalityapp.R;
import com.example.mymunicipalityapp.model.NewsModel;
import com.example.mymunicipalityapp.newsFragment.DisplayNewsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>  {



    private Context mContext;
    private List<NewsModel> mNewsList;

    /**
     * Constructs a new {@link NewsAdapter}
     * @param context of the app
     * @param newsList is the list of news, which is the data source of the adapter
     */
    public NewsAdapter(Context context, List<NewsModel> newsList) {
        mContext = context;
        mNewsList = newsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        try {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_news_card, viewGroup, false);
            return   new ViewHolder(v);

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return  null;
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        // Find the current news that was clicked on
        final NewsModel currentNews = mNewsList.get(i);

        holder.titleTextView.setText(currentNews.getTitle());
        holder.sectionTextView.setText(currentNews.getSection());


        // Get
        // time difference between the current date and web publication date and
        // set the time difference on the textView
        holder.dateTextView.setText(currentNews.getDate());


        // Get string of the trailTextHTML and convert Html text to plain text
        // and set the plain text on the textView
        String trailTextHTML = currentNews.getTrailTextHtml();
        holder.trailTextView.setText(Html.fromHtml(Html.fromHtml(trailTextHTML).toString()));

        // Set an OnClickListener to open a website with more information about the selected article
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new intent to view the news URI
                Intent websiteIntent = new Intent(mContext, DisplayNewsActivity.class);

                websiteIntent.putExtra("content" , currentNews.getContent());
                websiteIntent.putExtra("date" , currentNews.getDate());
                websiteIntent.putExtra("image" , currentNews.getThumbnail());
                websiteIntent.putExtra("title" , currentNews.getTitle());
                // Send the intent to launch a new activity
                mContext.startActivity(websiteIntent);
            }
        });


        holder.thumbnailImageView.setImageResource(currentNews.getThumbnail() );

        // Set an OnClickListener to share the data with friends via email or  social networking
        holder.shareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareData(currentNews);
            }
        });

    }

    /**
     * Add  a list of {@link NewsModel}
     * @param newsList is the list of news, which is the data source of the adapter
     */
    public void addAll(List<NewsModel> newsList) {
        mNewsList.clear();
        mNewsList.addAll(newsList);
        notifyDataSetChanged();
    }

    /**
     *  Clear all data (a list of {@link NewsModel} objects)
     */
    public void clearAll() {
        mNewsList.clear();
        notifyDataSetChanged();
    }

    /**
     * Share the article with friends in social network
     * @param news {@link NewsModel} object
     */
    private void shareData(NewsModel news) {
        try {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                    news.getTitle() + " : " + "link of our app");


            mContext.startActivity(Intent.createChooser(sharingIntent, mContext.getString(R.string.share_article)));
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }



    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title_card)  TextView titleTextView;
        @BindView(R.id.section_card)  TextView sectionTextView;
        @BindView(R.id.date_card) TextView dateTextView;
        @BindView(R.id.thumbnail_image_card) ImageView thumbnailImageView;
        @BindView(R.id.share_image_card) ImageView shareImageView;
        @BindView(R.id.details) TextView trailTextView;
        @BindView(R.id.card_view) CardView cardView;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }


}
