package com.serwylo.msjviewer.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.serwylo.msjviewer.R;

public class AboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView( R.layout.basic_web_view );

        WebViewClient client = new WebViewClient();
        WebView view = (WebView)findViewById( R.id.web_view );
        view.setWebViewClient( client );
        view.loadUrl( "file:///android_asset/about.html" );

        setupActionBar();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {

        if ( Build.VERSION.SDK_INT > 11 && getActionBar() != null ) {
            getActionBar().setDisplayHomeAsUpEnabled( true );
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if ( item.getItemId() == android.R.id.home ) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
}
