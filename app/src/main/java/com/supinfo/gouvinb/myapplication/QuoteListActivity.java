package com.supinfo.gouvinb.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.supinfo.gouvinb.myapplication.adapter.QuoteListAdapter;
import com.supinfo.gouvinb.myapplication.model.Quote;

import java.util.ArrayList;

public class QuoteListActivity extends AppCompatActivity {

  private static final String TAG = "QuoteListActivity";

  private AppCompatEditText quoteEditText;
  private ListView maListView;

  private QuoteListAdapter<Quote> quoteListAdapter;

  private ArrayList<Quote> quoteArrayList = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    quoteEditText = (AppCompatEditText) findViewById(R.id.quote);
    maListView = (ListView) findViewById(R.id.ma_list_view);
    maListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getBaseContext(), "Quote a la position " + position, Toast.LENGTH_SHORT).show();
      }
    });

    quoteListAdapter = new QuoteListAdapter<>(
        this,
        R.layout.simple_list_item,
        quoteArrayList
    );

    maListView.setAdapter(quoteListAdapter);

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
    quoteListAdapter.add(new Quote(strQuote));
    maListView.invalidate();

  }


  public void addQuote(View view) {
    String strQuote = quoteEditText.getText().toString();
    if (!strQuote.equals("")) {
      addQuote(strQuote);
    } else {
      Toast.makeText(this, "No text No action", Toast.LENGTH_SHORT).show();
    }
  }
}













