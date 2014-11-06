package com.serwylo.msjviewer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TabHost;

public class WebViewActivity extends Activity {

    private static final int FIRST_RUN = 1;

    @Override
    public void onCreate( Bundle savedInstanceState ) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        WebViewClient client = new MsjWebViewClient( this );

        WebView view = getWebView();
        view.getSettings().setJavaScriptEnabled( true );
        view.setWebViewClient( client );
        view.loadUrl( MsjConstants.URL_ROSTER );

        setupTabs();

        getTabHost().setCurrentTab( 0 );

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkForFirstRun();
    }

    private void checkForFirstRun() {
        if ( Preferences.get( this ).isFirstRun() ) {
            showFirstRunMessage();
        }
    }

    private void showFirstRunMessage() {
        Intent intent = new Intent( this, FirstRunActivity.class );
        startActivityForResult(intent, FIRST_RUN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode == FIRST_RUN ) {
            if ( resultCode == Activity.RESULT_CANCELED ) {
                finish();
            } else if ( resultCode == Activity.RESULT_OK ) {
                Preferences.get( this ).setIsFirstRun( false );
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate( R.menu.main_menu, menu );
        return true;

    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        if ( item.getItemId() == R.id.menu_logout ) {
            getWebView().loadUrl( MsjConstants.URL_LOGOUT );
            return true;
        }

        return super.onMenuItemSelected( featureId, item );

    }

    private void setupTabs() {

        TabHost tabs = getTabHost();

        tabs.setup();

        TabHost.TabContentFactory factory = new TabHost.TabContentFactory() {
            @Override
            public View createTabContent(String tag) {
                return getWebView();
            }
        };

        TabHost.TabSpec rosterTab = tabs.newTabSpec( "Roster" );
        TabHost.TabSpec eoiTab    = tabs.newTabSpec( "EOI" );
        TabHost.TabSpec hoursTab  = tabs.newTabSpec("Hours");

        rosterTab.setContent( factory ).setIndicator("Roster");
        eoiTab.setContent( factory ).setIndicator( "EOI" );
        hoursTab.setContent( factory ).setIndicator( "Hours" );

        tabs.addTab(rosterTab);
        tabs.addTab( eoiTab );
        tabs.addTab( hoursTab );

        tabs.setOnTabChangedListener( new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged( String tabId ) {

                if ( "Roster".equals( tabId ) ) {
                    showRoster();
                } else if ( "EOI".equals( tabId ) ) {
                    showEoi();
                } else if ( "Hours".equals( tabId ) ) {
                    showHours();
                }

            }
        });

    }

    protected void showRoster() {
        showUrl( MsjConstants.URL_ROSTER );
    }

    protected void showEoi() {
        showUrl( MsjConstants.URL_EOI );
    }

    protected void showHours() {
        showUrl( MsjConstants.URL_HOURS );
    }

    protected void showUrl( String url ) {
        getWebView().loadUrl(url);
    }

    private WebView getWebView() {
        return (WebView)findViewById( R.id.web_view );
    }

    private TabHost getTabHost() {
        return (TabHost)findViewById( android.R.id.tabhost );
    }
}
