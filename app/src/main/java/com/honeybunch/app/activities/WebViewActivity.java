package com.honeybunch.app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.honeybunch.app.R;

public class WebViewActivity extends AppCompatActivity {

    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webview = findViewById(R.id.webview);

//    getSupportActionBar().hide();
        Intent intent = getIntent();
        if (intent != null) {
            String url = intent.getStringExtra("url");
            String title = intent.getStringExtra("title");

           // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setDisplayShowHomeEnabled(true);

           /* getSupportActionBar().setTitle(Html.fromHtml("<font color='#000'>"+title+"</font>"));
            TextView textView = new TextView(this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            textView.setGravity(Gravity.CENTER);*/


           /* final Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_24);
            upArrow.setColorFilter(getResources().getColor(R.color.unselect_color), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);*/
            WebSettings webSetting = webview.getSettings();
//            webSetting.setBuiltInZoomControls(true);
            webSetting.setJavaScriptEnabled(true);

            webview.setWebViewClient(new WebViewClient());
            webview.loadUrl(url);
        }
    }
   /* @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }*/
    private class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

}