SpanEZ
============
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/yombunker/SpanEZ/blob/master/LICENSE)

Complete abstraction from the Spannable API. Forget about all the boiler plate code and all the
[**Object what**][1] nonsense that you have to deal with when working with Spannable.

 * Eliminate `setSpan` calls by using all the provided methods trough the `StyleEZ` interface.
 * Chain multiple spans with a fluent style
 * By using both the `ContentEZ` and `StyleEZ` interface it removes unnecessary methods from the autocomplete form, and thus it removes clutter.
 * Allows different ways of adding content (String, String Resource and Formatted String)
 * By default it does [INCLUSIVE_INCLUSIVE][2] to all the spans
 * After calling any of the inclusive/exclusive methods, all the next methods will be using that flag until apply is called or another flag is used
 * You can create your own locator by extending the Locator interface

```java
public class MainActivity extends AppCompatActivity {
    private TextView sample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sample = (TextView) findViewById(R.id.sample_text);
        SpanEZ.from(sample)
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
              .link(Word.findFirst("ut ultricies dolor molestie eget. Aliquam scelerisque, elit sit amet faucibus tincidunt"), "http://www.google.com")
              .clickable(Word.findFirst("Mauris in lacus at nulla consectetur dapibus. Sed accumsan sapien nec nisi mattis pulvinar."), new OnSpanClickListener() {
                  @Override
                  public void onSpanClick(String spanContent) {
                      Toast.makeText(MainActivity.this, spanContent, Toast.LENGTH_SHORT).show();
                  }
              })
              .inclusive()
              .quote(Paragraph.containing("Lorem"))
              .scaleX(Range.from(60, 80), 2)
              .apply(); // Calling the apply method is the only one that MUST be done in UIThread
    }
}
```

Download
--------

```groovy
dependencies {
  compile 'com.bunk3r:spanez:2.0.0'
}
```

License
-------

    Copyright 2017 Jorge Aguilar

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[1]: https://developer.android.com/reference/android/text/Spannable.html#setSpan(java.lang.Object, int, int, int)
[2]: https://developer.android.com/reference/android/text/Spanned.html#SPAN_INCLUSIVE_INCLUSIVE