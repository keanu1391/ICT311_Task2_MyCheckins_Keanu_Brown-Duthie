package com.keanu1094859.mycheckins;

import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CheckinHelpActivity extends AppCompatActivity {
    private static final String CHECKIN_HELP_LINK = "https://www.wikihow.com/Check-In-on-Facebook";

    private WebView mWebView;
    private TextView mLinkView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_checkin_help);

        mWebView = findViewById(R.id.checkin_help);
        mWebView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                return false;
            }

        });
        mWebView.loadUrl(CHECKIN_HELP_LINK);

        mLinkView = findViewById(R.id.checkin_help_link);
        mLinkView.setText(CHECKIN_HELP_LINK);
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}