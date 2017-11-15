package net.giniguru.githubdemo.blog;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import net.giniguru.githubdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by KK on 11/14/2017.
 */

public class BlogActivity extends AppCompatActivity {
    private final String TAG = getClass().getName();
    @BindView(R.id.webView)
    WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);

        ButterKnife.bind(this);
        setActionBarTitle();
        initWebViewClient();
    }
    /**
     * set title in action bar
     */
    private void setActionBarTitle(){
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(getString(R.string.blog));
    }

    /**
     * Initialize WebView
     */
    private void initWebViewClient() {
        webView.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

            //If you will not use this method url links are open in new browser not in WebView
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //show loader progress during page loading
                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(view.getContext());
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }

            }
            public void onPageFinished(WebView view, String url) {
                try{
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }

        });
        Log.i(TAG,"blog:"+getIntent().getStringExtra("blog"));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.loadUrl(getIntent().getStringExtra("blog"));
        webView.setHorizontalScrollBarEnabled(false);
    }


}
