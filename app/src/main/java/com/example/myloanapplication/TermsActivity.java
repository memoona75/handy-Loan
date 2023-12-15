package com.example.myloanapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.net.http.SslError;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.material.snackbar.Snackbar;

public class TermsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
SwipeRefreshLayout swipeRefreshLayout;
WebView webView;
String url= "https://www.investopedia.com/terms/l/loan.asp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
    }
    public void loadweb()
    {
    swipeRefreshLayout= findViewById(R.id.swipe_layout);
    webView= findViewById(R.id.web_view);
    swipeRefreshLayout.setOnRefreshListener(this);
    swipeRefreshLayout.setRefreshing(true);
    webView.loadUrl(url);
    webView.canGoBack();
    webView.setOnKeyListener(new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int i, KeyEvent KeyEvent) {
            if (i == KeyEvent.KEYCODE_BACK && KeyEvent.getAction() == MotionEvent.ACTION_UP
                    && webView.canGoBack())
            {
                webView.canGoBack();
                return true;
            }

                return false;
        }
    });
    webView.setWebViewClient(new WebViewClient() {


        @Override
        public void onPageFinished(WebView view, String urll) {
            swipeRefreshLayout.setRefreshing(false);
            url= urll;
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            Snackbar.make(view,"Oops!Somthing went wrong.",Snackbar.LENGTH_INDEFINITE).show();

        }
    });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadweb();
    }

    @Override
    public void onRefresh() {
        loadweb();
    }
}