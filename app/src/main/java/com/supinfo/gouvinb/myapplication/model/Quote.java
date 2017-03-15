package com.supinfo.gouvinb.myapplication.model;

import android.provider.BaseColumns;

import java.util.Calendar;
import java.util.Locale;

public class Quote {

  private String strQuote = "";
  private Integer rating = 0;
  private String dateField = Calendar.getInstance(Locale.getDefault()).getTime().toString();

  public Quote(String strQuote) {
    this.strQuote = strQuote;
  }

  public String getStrQuote() {
    return strQuote;
  }

  public void setStrQuote(String strQuote) {
    this.strQuote = strQuote;
  }

  public Integer getRating() {
    return rating;
  }

  public void setRating(Integer rating) {
    this.rating = rating;
  }

  public String getDateField() {
    return dateField;
  }

  public void setDateField(String dateField) {
    this.dateField = dateField;
  }

  @Override
  public String toString() {
    return strQuote;
  }

  public static class QuoteEntry implements BaseColumns {
    public static final String TABLE_NAME = "quote";
    public static final String COLUMN_NAME_QUOTE = "quote";
    public static final String COLUMN_NAME_RATING = "rating";
    public static final String COLUMN_NAME_DATE = "date";
  }






}
