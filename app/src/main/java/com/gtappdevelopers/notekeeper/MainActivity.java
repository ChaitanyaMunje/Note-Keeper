package com.gtappdevelopers.notekeeper;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    StickyNote note = new StickyNote();
    float currentSize;
    private EditText notesEdt;
    private Button italicBtn, boldBtn, underLineBtn;
    private TextView tvSize,addTVSizeBtn,reduceTVSizeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notesEdt = findViewById(R.id.idEdt);
        notesEdt.setText(note.getStick(this));
        tvSize = findViewById(R.id.idTVSize);
        addTVSizeBtn = findViewById(R.id.idBtnAdd);
        reduceTVSizeBtn = findViewById(R.id.idBtnSub);
        italicBtn = findViewById(R.id.idbtnItalic);
        boldBtn = findViewById(R.id.idBtnBold);
        underLineBtn = findViewById(R.id.idBtnUnderLine);
        currentSize = notesEdt.getTextSize();

        tvSize.setText("" + currentSize);
        addTVSizeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSize.setText("" + currentSize);
                currentSize++;
                notesEdt.setTextSize(currentSize);
            }
        });
        reduceTVSizeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSize.setText("" + currentSize);
                currentSize--;
                notesEdt.setTextSize(currentSize);
            }
        });
        italicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boldBtn.setTextColor(getResources().getColor(R.color.white));
                boldBtn.setBackgroundColor(getResources().getColor(R.color.purple_200));
                if (notesEdt.getTypeface().isItalic()) {
                    notesEdt.setTypeface(Typeface.DEFAULT);
                    italicBtn.setTextColor(getResources().getColor(R.color.white));
                    italicBtn.setBackgroundColor(getResources().getColor(R.color.purple_200));
                } else {
                    italicBtn.setTextColor(getResources().getColor(R.color.purple_200));
                    italicBtn.setBackgroundColor(getResources().getColor(R.color.white));
                    notesEdt.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                }
            }
        });
        boldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                italicBtn.setTextColor(getResources().getColor(R.color.white));
                italicBtn.setBackgroundColor(getResources().getColor(R.color.purple_200));
                if (notesEdt.getTypeface().isBold()) {
                    notesEdt.setTypeface(Typeface.DEFAULT);
                    boldBtn.setTextColor(getResources().getColor(R.color.white));
                    boldBtn.setBackgroundColor(getResources().getColor(R.color.purple_200));
                } else {
                    boldBtn.setTextColor(getResources().getColor(R.color.purple_200));
                    boldBtn.setBackgroundColor(getResources().getColor(R.color.white));
                    notesEdt.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                }
            }
        });
        underLineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notesEdt.getPaintFlags() == 8) {
                    underLineBtn.setTextColor(getResources().getColor(R.color.white));
                    underLineBtn.setBackgroundColor(getResources().getColor(R.color.purple_200));
                    notesEdt.setPaintFlags(notesEdt.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
                } else {
                    underLineBtn.setTextColor(getResources().getColor(R.color.purple_200));
                    underLineBtn.setBackgroundColor(getResources().getColor(R.color.white));
                    notesEdt.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                }
            }
        });
    }

    public void saveButton(View view) {
        note.setStick(notesEdt.getText().toString(), this);
        updateWidget();
        Toast.makeText(this, "Updated Successfully!!", Toast.LENGTH_SHORT).show();
    }

    private void updateWidget() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        RemoteViews remoteViews = new RemoteViews(this.getPackageName(), R.layout.widget_layout);
        ComponentName thisWidget = new ComponentName(this, AppWidget.class);
        remoteViews.setTextViewText(R.id.appWidgetTV, notesEdt.getText().toString());
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);

    }
}