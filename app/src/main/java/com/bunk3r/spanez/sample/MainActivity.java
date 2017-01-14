package com.bunk3r.spanez.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.bunk3r.spanez.SpanEZ;
import com.bunk3r.spanez.locators.Paragraph;
import com.bunk3r.spanez.locators.Range;
import com.bunk3r.spanez.locators.Word;

import static com.bunk3r.spanez.SpanEZ.BOLD;
import static com.bunk3r.spanez.SpanEZ.ITALIC;
import static com.bunk3r.spanez.SpanEZ.STRIKETHROUGH;
import static com.bunk3r.spanez.SpanEZ.SUBSCRIPT;
import static com.bunk3r.spanez.SpanEZ.SUPERSCRIPT;
import static com.bunk3r.spanez.SpanEZ.UNDERLINE;

public class MainActivity extends AppCompatActivity {
    private TextView sample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sample = (TextView) findViewById(R.id.sample_text);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SpanEZ.from(sample)
              .withContent(R.string.lorem_ipsum)
              .backgroundColor(Word.findFirst("ipsum dolor"), R.color.colorAccent)
              .backgroundColor(Range.from(1, 4), R.color.colorPrimaryDark)
              .apply();
    }
}
