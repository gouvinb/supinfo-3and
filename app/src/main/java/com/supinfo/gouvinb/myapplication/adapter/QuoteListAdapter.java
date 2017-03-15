package com.supinfo.gouvinb.myapplication.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.supinfo.gouvinb.myapplication.sqlite.QuoteDbHelper;

import java.util.Calendar;
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
    SQLiteDatabase sqLiteDatabase = new QuoteDbHelper(context).getWritableDatabase();

    if (!checkConflict(quote) && !quoteList.contains(quote)) {
      quoteList.add(quote);

      ContentValues contentValues = new ContentValues();
      contentValues.put(Quote.QuoteEntry.COLUMN_NAME_QUOTE, quote.getStrQuote());
      contentValues.put(Quote.QuoteEntry.COLUMN_NAME_RATING, quote.getRating());
      contentValues.put(Quote.QuoteEntry.COLUMN_NAME_DATE, quote.getDateField());
      sqLiteDatabase.insertWithOnConflict(
          Quote.QuoteEntry.TABLE_NAME,
          null,
          contentValues,
          SQLiteDatabase.CONFLICT_IGNORE
      );
      sqLiteDatabase.close();

      notifyDataSetChanged();
    }
  }

  public void update(int pos, int rating) {
    quoteList.get(pos).setRating(rating);
    quoteList.get(pos).setDateField(Calendar.getInstance().getTime().toString());

    String whereClause = Quote.QuoteEntry.COLUMN_NAME_QUOTE + "=?";
    String[] whereArgs = {quoteList.get(pos).getStrQuote()};

    SQLiteDatabase sqLiteDatabase = new QuoteDbHelper(context).getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put(Quote.QuoteEntry.COLUMN_NAME_QUOTE, quoteList.get(pos).getStrQuote());
    contentValues.put(Quote.QuoteEntry.COLUMN_NAME_RATING, quoteList.get(pos).getRating());
    contentValues.put(Quote.QuoteEntry.COLUMN_NAME_DATE, quoteList.get(pos).getDateField());
    sqLiteDatabase.update(Quote.QuoteEntry.TABLE_NAME, contentValues, whereClause, whereArgs);
    sqLiteDatabase.close();

    notifyDataSetChanged();
  }

  public void update(int pos, String quoteStr) {
    String oldQuote = quoteList.get(pos).getStrQuote();

    quoteList.get(pos).setStrQuote(quoteStr);
    quoteList.get(pos).setDateField(Calendar.getInstance().getTime().toString());

    String whereClause = Quote.QuoteEntry._ID + "=?";
    String[] whereArgs = {"" + (pos + 1)};

    SQLiteDatabase sqLiteDatabase = new QuoteDbHelper(context).getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put(Quote.QuoteEntry.COLUMN_NAME_QUOTE, quoteList.get(pos).getStrQuote());
    contentValues.put(Quote.QuoteEntry.COLUMN_NAME_DATE, quoteList.get(pos).getDateField());
    sqLiteDatabase.update(Quote.QuoteEntry.TABLE_NAME, contentValues, whereClause, whereArgs);
    sqLiteDatabase.close();

    notifyDataSetChanged();
  }

  private boolean checkConflict(Quote quote) {
    SQLiteDatabase sqLiteDatabase = new QuoteDbHelper(context).getWritableDatabase();

    String[] quoteField = {
        Quote.QuoteEntry.COLUMN_NAME_QUOTE,
        Quote.QuoteEntry.COLUMN_NAME_RATING,
        Quote.QuoteEntry.COLUMN_NAME_DATE,
    };

    String whereClause = quoteField[0] + "=?";
    String[] whereArgs = {quote.getStrQuote()};

    Cursor quoteEntry = sqLiteDatabase.query(
        Quote.QuoteEntry.TABLE_NAME,
        quoteField,
        whereClause,
        whereArgs,
        null,
        null,
        null
    );

    boolean haveConflict = false;
    if (quoteEntry.getCount() > 0) {
      haveConflict = true;
    }
    quoteEntry.close();
    sqLiteDatabase.close();

    return haveConflict;
  }
}









