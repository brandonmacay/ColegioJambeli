package com.vluver.cbj.colegio;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mancj.materialsearchbar.MaterialSearchBar;

public class SearchInEducarPlus extends AppCompatActivity implements  MaterialSearchBar.OnSearchActionListener{

    MaterialSearchBar searchBar;
    String url = "https://educarplus.com/?s=";
    WebView miVisorWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchBar = findViewById(R.id.searchBarMain);
        searchBar.setOnSearchActionListener(this);
        searchBar.enableSearch();

        miVisorWeb = (WebView) findViewById(R.id.visorWeb);

        final WebSettings ajustesVisorWeb = miVisorWeb.getSettings();
        ajustesVisorWeb.setJavaScriptEnabled(true);
//improve webView performance
        miVisorWeb.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        miVisorWeb.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        miVisorWeb.getSettings().setAppCacheEnabled(true);
        miVisorWeb.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        ajustesVisorWeb.setDomStorageEnabled(true);
        ajustesVisorWeb.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        ajustesVisorWeb.setUseWideViewPort(true);
        ajustesVisorWeb.setLoadWithOverviewMode(true);
        ajustesVisorWeb.setAllowFileAccess(true);

        if (Build.VERSION.SDK_INT >= 19) {
            miVisorWeb.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else if(Build.VERSION.SDK_INT >=15 && Build.VERSION.SDK_INT < 19) {
            miVisorWeb.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        ajustesVisorWeb.setSavePassword(true);
        ajustesVisorWeb.setSaveFormData(true);
        ajustesVisorWeb.setEnableSmoothTransition(true);
        //force links open in webview only
        miVisorWeb.setWebViewClient(new MyWebviewClient());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);*/
        return true;
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {
        if (!enabled){
            finish();
            overridePendingTransition(0,0);
        }
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        miVisorWeb.loadUrl(url+text.toString().replaceAll("\\s+","+"));
    }

    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode) {
            case MaterialSearchBar.BUTTON_SPEECH:
                break;
            case MaterialSearchBar.BUTTON_BACK:
                finish();
                break;
        }
    }
    private class MyWebviewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals("https://educarplus.com")) {
                //open url contents in webview
                miVisorWeb.loadUrl(url);
                return false;
            } else {
                //here open external links in external browser or app
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                return true;
            }

        }
        //ProgressDialogue
        ProgressDialog pd = null;

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            pd=new ProgressDialog(SearchInEducarPlus.this);
            pd.setMessage("Cargando...");
            pd.show();
            pd.setCancelable(false);
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            pd.dismiss();

            super.onPageFinished(view, url);
        }

    }
}
