package by.project.dartlen.rss_reader.webview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.project.dartlen.rss_reader.R;
import dagger.android.support.DaggerFragment;

public class WebViewFragment extends DaggerFragment implements WebViewContract.View {

    @Inject
    WebViewContract.Presenter mWebViewPresenter;

    @Inject
    public WebViewFragment(){}

    @BindView(R.id.webview)
    WebView webView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mWebViewPresenter.takeView(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_webview, container, false);
        String strtext = getArguments().getString("url");
        ButterKnife.bind(this, root);
        webView.loadUrl(strtext);
        return root;
    }

}
