package by.project.dartlen.rss_reader.rss;

import by.project.dartlen.rss_reader.base.BasePresenter;
import by.project.dartlen.rss_reader.base.BaseView;

public interface RssContract {
    interface View extends BaseView<RssContract.Presenter>{}
    interface Presenter extends BasePresenter<RssContract.View>{}
}
