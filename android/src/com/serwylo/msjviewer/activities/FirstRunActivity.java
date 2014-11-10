package com.serwylo.msjviewer.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import com.serwylo.msjviewer.R;

public class FirstRunActivity extends Activity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.first_run );

        WebViewClient client = new WebViewClient();
        WebView view = (WebView)findViewById( R.id.web_view );
        view.setWebViewClient( client );
        view.getSettings().setJavaScriptEnabled( true );
        view.loadUrl( "file:///android_asset/about.html" );

        findViewById( R.id.btn_cancel ).setOnClickListener( new Button.OnClickListener() {
            @Override
            public void onClick( View v ) {
                setResult( Activity.RESULT_CANCELED );
                finish();
            }
        });

        findViewById( R.id.btn_understand ).setOnClickListener( new Button.OnClickListener() {
            @Override
            public void onClick( View v ) {
                setResult( Activity.RESULT_OK );
                finish();
            }
        });
    }
}
