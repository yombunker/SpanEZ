package com.bunk3r.spanez.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.bunk3r.spanez.Font;
import com.bunk3r.spanez.SpanEZ;

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
              .subscript(0, 10)
              .superscript(20, 40)
              .scaleX(50, 80, 2.f)
              .relativeSize(100, 120, 3f)
              .absoluteSize(130, 150, 20)
              .absoluteSizeDP(130, 150, 20)
              .font(Font.MONOSPACE, "ut ultricies dolor molestie eget")
              .font(Font.SANS_SERIF_CONDENSED, "Sed accumsan sapien nec nisi mattis pulvinar.")
              .apply();
    }
}
