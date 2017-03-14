package com.supinfo.gouvinb.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.supinfo.gouvinb.myapplication.adapter.QuoteListAdapter;
import com.supinfo.gouvinb.myapplication.model.Quote;

import java.util.ArrayList;

public class QuoteListActivity extends AppCompatActivity {

  private static final String TAG = "QuoteListActivity";
  private static final int RATE_QUOTE = 1;

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
        Intent intent = new Intent(QuoteListActivity.this, QuoteActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(QuoteActivity.QUOTE_POSITION, position);
        bundle.putString(QuoteActivity.QUOTE_STR, quoteArrayList.get(position).getStrQuote());
        bundle.putInt(QuoteActivity.QUOTE_RATING, quoteArrayList.get(position).getRating());
        intent.putExtras(bundle);
        startActivityForResult(intent, RATE_QUOTE);
      }
    });
    maListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
      @Override
      public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        EditQuoteDialog
            .newInstance(position, quoteArrayList.get(position).getStrQuote())
            .show(getSupportFragmentManager(), "Edit Quote");
        return true;
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

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == RATE_QUOTE) {
      if (resultCode == RESULT_OK) {
        quoteListAdapter.update(
            data.getExtras().getInt(QuoteActivity.QUOTE_POSITION),
            data.getExtras().getInt(QuoteActivity.QUOTE_RATING));
        maListView.invalidate();
      }
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.retour) {
      finish();
    }
    return super.onOptionsItemSelected(item);
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

  public void updateQuote(int pos, String quoteStr) {
    quoteListAdapter.update(pos, quoteStr);
    maListView.invalidate();
  }
}













