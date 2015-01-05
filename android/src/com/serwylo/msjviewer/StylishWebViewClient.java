package com.serwylo.msjviewer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public abstract class StylishWebViewClient extends WebViewClient {

    private static final String TAG = "com.serwylo.msjviewer.StylishWebViewClient";

    protected final Context context;

    private Animation showAnimation;
    private Animation hideAnimation;

    private JsInterface jsInterface = new JsInterface();

    private boolean shouldHandleFileUrls = false;

    private boolean visible = true;

    public StylishWebViewClient( Context context ) {

        super();

        this.context = context.getApplicationContext();

        showAnimation = new AlphaAnimation( 0.0f, 1.0f );
        showAnimation.setDuration( 1000 );

        hideAnimation = new AlphaAnimation( 1.0f, 0.0f );
        hideAnimation.setDuration( 200 );
    }

    public void setShouldHandleFileUrls( boolean value ) {
        shouldHandleFileUrls = value;
    }

    public JsInterface getJsInterface() {
        return jsInterface;
    }

    @Override
    public boolean shouldOverrideUrlLoading( WebView view, String url ) {
        view.loadUrl(url);
        return true;
    }

    protected void fadeIn( final WebView view ) {
        if (!visible) {
            visible = true;
            showAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    view.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {}

                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
            view.startAnimation(showAnimation);
        }
    }

    protected void fadeOut( final WebView view ) {
        visible = false;
        hideAnimation.setAnimationListener( new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility( View.INVISIBLE );
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        view.startAnimation(hideAnimation);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {

        Log.d(TAG, "onPageStarted( \"" + url + "\"");

        if ( requiresStyling( url ) ) {
            String[] stylesheets = getStylesheetUrls( url );
            if (stylesheets.length > 0) {
                fadeOut(view);
            }
        } else if ( isMailTo( url ) ) {
            Intent intent = new Intent( Intent.ACTION_SENDTO, Uri.parse( url ) );
            intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
            context.startActivity( intent );
        }

    }

    protected static boolean isMailTo( String url ) {
        return url != null && url.startsWith( "mailto:" );
    }

    protected static boolean isFile( String url ) {
        return url != null && url.startsWith( "file:" );
    }

    private boolean requiresStyling( String url ) {
        return !isMailTo( url ) && ( shouldHandleFileUrls || !isFile( url ) );
    }

    @Override
    public void onPageFinished(WebView view, String url)
    {
        Log.d( TAG, "onPageFinished( \"" + url + "\"" );

        if ( !requiresStyling( url ) ) {
            fadeIn( view );
            return;
        }

        String[] stylesheets = getStylesheetUrls( url );
        for ( String stylesheetUrl : stylesheets ) {
            String javascript = "javascript:(function(){" + addCssToDom( stylesheetUrl ) + "})();";
            Log.d( TAG, javascript );
            view.loadUrl(javascript);
        }

        if ( stylesheets.length > 0 ) {
            fadeIn(view);
        }

    }

    protected abstract String[] getStylesheetUrls( String webpageUrl );

    private String addCssToDom( String stylesheetUrl ) {

        return
            "var url = '" + stylesheetUrl + "';" +
            "var headNode = document.getElementsByTagName('head')[0];" +
            "var cssNode = document.createElement('link');" +
            "cssNode.type = 'text/css';" +
            "cssNode.rel = 'stylesheet';" +
            "cssNode.href = url;" +
            "cssNode.media = 'screen';" +
            "headNode.appendChild(cssNode);";

    }

    public class JsInterface {

        private JsInterface() {

        }

    }
}
