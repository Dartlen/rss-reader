package by.project.dartlen.rss_reader.webview;

import javax.inject.Inject;

public class WebViewPresenter implements WebViewContract.Presenter{

    @Inject
    public WebViewPresenter(){}

    private WebViewContract.View mWebWievFragment;

    @Override
    public void takeView(WebViewContract.View view) {
        mWebWievFragment = view;
    }

    @Override
    public void dropView() {
        mWebWievFragment = null;
    }
}
