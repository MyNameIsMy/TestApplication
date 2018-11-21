package projects.suchushin.org.testapplication.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.suchushin.org.testapplication.R;

public class WebActivity extends Activity {
    @BindView(R.id.web_view)
    WebView webView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_layout);
        ButterKnife.bind(this);

        webView.loadUrl("https://html5test.com/");
        webView.getSettings().setJavaScriptEnabled(true);
    }


}
