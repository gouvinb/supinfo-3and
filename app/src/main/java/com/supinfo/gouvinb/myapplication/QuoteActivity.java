package com.supinfo.gouvinb.myapplication;

import android.content.Intent;
import android.icu.math.BigDecimal;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class QuoteActivity extends AppCompatActivity {

  private static final String TAG = "QuoteActivity";

  public static final String QUOTE_POSITION = "QUOTE_POSITION";
  public static final String QUOTE_STR = "QUOTE_STR";
  public static final String QUOTE_RATING = "QUOTE_RATING";

  private TextView textView;
  private RatingBar ratingBar;
  private Button button;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quote);

    textView = (TextView) findViewById(R.id.quote_content_details);
    ratingBar = (RatingBar) findViewById(R.id.ratingBar);
    button = (Button) findViewById(R.id.submit_rate);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Bundle bundle = getIntent().getExtras();
        bundle.putInt(QUOTE_RATING, (int) ratingBar.getRating());
        getIntent().putExtras(bundle);
        setResult(RESULT_OK, getIntent());
        finish();
      }
    });

    if (getIntent() == null) {
      setResult(RESULT_CANCELED);
      finish();
    } else {
      textView.setText(getIntent().getExtras().getString(QUOTE_STR));
      ratingBar.setRating(getIntent().getExtras().getInt(QUOTE_RATING));
    }
  }

}




