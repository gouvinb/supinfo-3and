package com.supinfo.gouvinb.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.supinfo.gouvinb.myapplication.R;
import com.supinfo.gouvinb.myapplication.model.Quote;

import java.util.List;

/**
 * Created by gouvinb on 13/03/2017.
 */

public class QuoteListAdapter<T> extends BaseAdapter {

  private final int simpleListItem;
  private final Context context;
  private final List<Quote> quoteList;

  public QuoteListAdapter(Context context, int simpleListItem, List<Quote> quoteList) {
    this.context = context;
    this.simpleListItem = simpleListItem;
    this.quoteList = quoteList;
  }

  @Override
  public int getCount() {
    return quoteList.size();
  }

  @Override
  public Quote getItem(int position) {
    return quoteList.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = LayoutInflater.from(context).inflate(simpleListItem, parent, false);
    }

    TextView quoteView = (TextView) convertView.findViewById(R.id.content_quote);
    quoteView.setText(getItem(position).toString());

    if (position % 2 == 0) {
      convertView.setBackgroundColor(Color.parseColor("#44AA0000"));
    } else {
      convertView.setBackgroundColor(Color.WHITE);
    }

    RatingBar rateQuoteView = (RatingBar) convertView.findViewById(R.id.rating_quote);
    rateQuoteView.setRating(quoteList.get(position).getRating());

    if (position % 2 == 0) {
      convertView.setBackgroundColor(Color.parseColor("#44AA0000"));
    } else {
      convertView.setBackgroundColor(Color.WHITE);
    }

    return convertView;
  }

  public void add(Quote quote) {
    quoteList.add(quote);
    notifyDataSetChanged();
  }

  public void update(int pos, int rating) {
    quoteList.get(pos).setRating(rating);
    Log.i("TAG", "update: " + rating);
    notifyDataSetChanged();
  }
}









