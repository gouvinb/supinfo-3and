package com.supinfo.gouvinb.myapplication;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.supinfo.gouvinb.myapplication.model.Quote;

import java.util.ArrayList;

public class QuoteListActivity extends AppCompatActivity {

  private static final String TAG = "QuoteListActivity";

  private AppCompatButton submitButton;
  private AppCompatEditText quoteEditText;
  private LinearLayoutCompat contentQuoteLinearLayout;

  private ArrayList<Quote> quoteArrayList = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    submitButton = (AppCompatButton) findViewById(R.id.submit_btn);
    quoteEditText = (AppCompatEditText) findViewById(R.id.quote);
    contentQuoteLinearLayout = (LinearLayoutCompat) findViewById(R.id.content_quote);

    String[] stringArray = getResources().getStringArray(R.array.quote_array);
    for (String strQuote : stringArray) {
      addQuote(strQuote);
    }

    addQuote("quote added with java");

    if (BuildConfig.DEBUG) {
      for (Quote quote : quoteArrayList) {
        Log.d(TAG, quote.toString());
      }
    }
  }

  private void addQuote(String strQuote) {
    quoteArrayList.add(new Quote(strQuote));
    TextView quoteView = new TextView(this);
    quoteView.setText(strQuote);
    if (quoteArrayList.size() % 2 == 0) {
      quoteView.setBackgroundColor(Color.LTGRAY);
    }
    contentQuoteLinearLayout.addView(quoteView);
  }










  public void addQuote(View view) {
    String strQuote = quoteEditText.getText().toString();
    if (!strQuote.equals("")) {
      addQuote(strQuote);
    } else {
      Toast.makeText(this, "No text No action", Toast.LENGTH_LONG).show();
    }
  }
}













