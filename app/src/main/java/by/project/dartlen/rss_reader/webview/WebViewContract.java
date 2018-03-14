package by.project.dartlen.rss_reader.webview;

import by.project.dartlen.rss_reader.base.BasePresenter;
import by.project.dartlen.rss_reader.base.BaseView;

public interface WebViewContract {
    interface View extends BaseView<WebViewContract.Presenter>{

    }
    interface Presenter extends BasePresenter<WebViewContract.View> {

    }
}
