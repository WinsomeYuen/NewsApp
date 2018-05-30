package com.example.winsome.newsapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NewsAdapter extends ArrayAdapter<News> {
    private static final String LOG_TAG = NewsAdapter.class.getSimpleName();

    public NewsAdapter(Activity context, ArrayList<News> news) {
        super(context, 0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        final News currentNews = getItem(position);

        // Set image
        ImageView articleThumbnail = (ImageView) listItemView.findViewById(R.id.thumbnail_image_view);
        articleThumbnail.setImageBitmap(formatImageFromBitmap(currentNews.getImage()));

        // Set section
        TextView section = (TextView) listItemView.findViewById(R.id.section_text_view);
        section.setText(currentNews.getSection());

        // Set title
        TextView title = (TextView) listItemView.findViewById(R.id.title_text_view);
        title.setText(currentNews.getTitle());

        // Set author
        TextView author = (TextView) listItemView.findViewById(R.id.author_text_view);
        author.setText(currentNews.getAuthor());

        // Set publish time
        TextView publishTime = (TextView) listItemView.findViewById(R.id.date_text_view);
        publishTime.setText(formatPublishTime(currentNews.getDate()));

        Button btn = (Button) listItemView.findViewById(R.id.more_button);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
                myWebLink.setData(Uri.parse(currentNews.getUrl()));
                getContext().startActivity(myWebLink);
            }
        });

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }

    // Format publish date
    private String formatPublishTime(final String time) {
        // If not the correct base format
        String rTime = "N.A.";
        // Check time validation
        if ((time != null) && (!time.isEmpty())) {
            try {
                // Create current format
                SimpleDateFormat currentSDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                // Create new format
                SimpleDateFormat newSDF = new SimpleDateFormat("yyyy.MM.dd / HH:mm");
                // Parse time
                rTime = newSDF.format(currentSDF.parse(time));
            } catch (ParseException parseEx) {
                // If an error occurs don't stop the app
                rTime = "N.A.";
                // But log the error
                Log.e(LOG_TAG, "Error while parsing the published date", parseEx);
            }
        }

        return rTime;
    }

    // Get the thumbnail image
    private Bitmap formatImageFromBitmap(Bitmap articleThumbnail) {
        // Bitmap for image
        Bitmap returnBitmap;
        // Check thumbnail valid
        if (articleThumbnail == null) {
            // If not valid return default image
            returnBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.no_image_available);
        } else {
            // If valid return image
            returnBitmap = articleThumbnail;
        }
        // Return bitmap
        return returnBitmap;
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
}
