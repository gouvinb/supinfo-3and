package com.supinfo.gouvinb.myapplication;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

/**
 * Created by gouvinb on 14/03/2017.
 */

public class EditQuoteDialog extends DialogFragment {

  public static final String QUOTE_POSITION = "QUOTE_POSITION";
  public static final String QUOTE_STR = "QUOTE_STR";

  public static EditQuoteDialog newInstance(int pos, String quoteStr) {
    EditQuoteDialog editQuoteDialog = new EditQuoteDialog();
    Bundle bundle = new Bundle();
    bundle.putInt(QUOTE_POSITION, pos);
    bundle.putString(QUOTE_STR, quoteStr);
    editQuoteDialog.setArguments(bundle);
    return editQuoteDialog;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {

    final EditText editText = new EditText(getContext());
    editText.setText(getArguments().getString(QUOTE_STR));

    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext())
        .setTitle("DJ YOLO STYLE")
        .setView(editText)
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            ((QuoteListActivity)getActivity())
                .updateQuote(getArguments().getInt(QUOTE_POSITION), editText.getText().toString());
          }
        });

    return alertDialogBuilder.create();
  }
}
