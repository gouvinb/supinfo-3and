package com.supinfo.gouvinb.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.supinfo.gouvinb.myapplication.adapter.QuoteListAdapter;
import com.supinfo.gouvinb.myapplication.model.Quote;
import com.supinfo.gouvinb.myapplication.model.Quote.QuoteEntry;
import com.supinfo.gouvinb.myapplication.sqlite.QuoteDbHelper;

import java.util.ArrayList;

public class QuoteListActivity extends AppCompatActivity {

  private static final String TAG = "QuoteListActivity";
  private static final int RATE_QUOTE = 1;

  private AppCompatEditText quoteEditText;
  private ListView maListView;

  private QuoteListAdapter<Quote> quoteListAdapter;

  private ArrayList<Quote> quoteArrayList = new ArrayList<>();
  private int NOTIFICATION = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    sendNotification();

    getPersistedData();

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


  }

  private void getPersistedData() {
    SQLiteDatabase sqLiteDatabase =
        new QuoteDbHelper(getApplicationContext()).getReadableDatabase();

    String[] quoteField = {
        QuoteEntry.COLUMN_NAME_QUOTE,
        QuoteEntry.COLUMN_NAME_RATING,
        QuoteEntry.COLUMN_NAME_DATE,
    };

    Cursor quoteEntry = sqLiteDatabase.query(
        QuoteEntry.TABLE_NAME,
        quoteField,
        null,
        null,
        null,
        null,
        null
    );

    while (quoteEntry.moveToNext()) {
      Quote quote = new Quote(quoteEntry.getString(quoteEntry.getColumnIndex(quoteField[0])));
      quote.setRating(quoteEntry.getInt(quoteEntry.getColumnIndex(quoteField[1])));
      quote.setDateField(quoteEntry.getString(quoteEntry.getColumnIndex(quoteField[2])));
      quoteArrayList.add(quote);
    }

    quoteEntry.close();
    sqLiteDatabase.close();
  }

  private void sendNotification() {
    int icon = android.R.drawable.ic_dialog_alert;
    String title = "Coucou je suis un titre";
    String content = "lorem";

    Intent notificationIntent = new Intent(getApplicationContext(), QuoteListActivity.class);
    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
        NOTIFICATION, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

    Notification.Builder builder = new Notification.Builder(getApplicationContext());
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      builder.setContentTitle(title)
          .setSmallIcon(icon)
          .setContentIntent(pendingIntent)
          .setContentText(content)
          .setColor(Color.RED);
    } else {
      builder.setContentTitle(title)
          .setSmallIcon(icon)
          .setContentIntent(pendingIntent)
          .setContentText(content);
    }
    Notification notification = builder.build();
    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    notificationManager.notify(NOTIFICATION, notification);
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

  public void addQuote(View view) {
    String strQuote = quoteEditText.getText().toString();
    if (!strQuote.equals("")) {
      addQuote(strQuote);
    } else {
      Toast.makeText(this, "No text No action", Toast.LENGTH_SHORT).show();
    }
  }

  private void addQuote(String strQuote) {
    quoteListAdapter.add(new Quote(strQuote));
    maListView.invalidate();

  }


  public void updateQuote(int pos, String quoteStr) {
    quoteListAdapter.update(pos, quoteStr);
    maListView.invalidate();
  }
}













