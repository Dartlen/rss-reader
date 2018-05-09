package by.project.dartlen.rss_reader.rss;

import java.util.List;

import by.project.dartlen.rss_reader.base.BasePresenter;
import by.project.dartlen.rss_reader.base.BaseView;
import by.project.dartlen.rss_reader.data.rss.RssItem;

public interface RssContract {
    interface View extends BaseView<RssContract.Presenter>{
        void showProgress();
        void hideProgress();
        void showNoRss();
        void hideNoRss();
        void showRss(List<RssItem> list);
    }
    interface Presenter extends BasePresenter<RssContract.View>{
        void start();
        void onClick(String url);
    }

}
