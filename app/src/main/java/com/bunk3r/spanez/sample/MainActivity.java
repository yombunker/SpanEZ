package com.bunk3r.spanez.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.bunk3r.spanez.SpanEZ;
import com.bunk3r.spanez.callbacks.OnSpanClickListener;
import com.bunk3r.spanez.locators.Paragraph;
import com.bunk3r.spanez.locators.Range;
import com.bunk3r.spanez.locators.Word;

import static com.bunk3r.spanez.api.EZ.BOLD;
import static com.bunk3r.spanez.api.EZ.ITALIC;
import static com.bunk3r.spanez.api.EZ.STRIKETHROUGH;
import static com.bunk3r.spanez.api.EZ.UNDERLINE;

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

        SpanEZ.withTextView(sample)
              .withContent(R.string.lorem_ipsum)
              .absoluteSize(Range.from(0, 5), 20)
              .absoluteSizeDP(Range.from(6, 10), 34)
              .exclusive()
              .backgroundColor(Word.findFirst("ipsum dolor"), R.color.colorPrimary)
              .backgroundColor(Range.from(30, 50), R.color.colorPrimaryDark)
              .foregroundColor(Range.from(45, 100), R.color.colorAccent)
              .inclusiveExclusive()
              .style(Word.findAll("ipsum"), BOLD | UNDERLINE)
              .style(Word.findFirst("ipsum"), ITALIC | STRIKETHROUGH)
              .link(Word.findFirst("ut ultricies dolor molestie eget"), "http://www.google.com")
              .clickable(Word.findFirst("Mauris in lacus at nulla"), new OnSpanClickListener() {
                  @Override
                  public void onSpanClick(String spanContent) {
                      Toast.makeText(MainActivity.this, spanContent, Toast.LENGTH_SHORT)
                           .show();
                  }
              })
              .inclusive()
              .quote(Paragraph.containing("Lorem"))
              .scaleX(Range.from(60, 80), 2)
              .apply();
    }
}
