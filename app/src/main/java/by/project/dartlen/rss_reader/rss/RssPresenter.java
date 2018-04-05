package by.project.dartlen.rss_reader.rss;

import java.util.List;

import javax.inject.Inject;

import by.project.dartlen.rss_reader.data.Repository;
import by.project.dartlen.rss_reader.data.local.realm.RssUrlRealm;
import by.project.dartlen.rss_reader.data.remote.callbacks.GetUrls;
import by.project.dartlen.rss_reader.data.remote.callbacks.RssCallback;
import by.project.dartlen.rss_reader.data.rss.RssItem;

public class RssPresenter implements RssContract.Presenter {

    @Inject
    public RssPresenter(){}

    @Inject
    Repository mRepository;

    private RssContract.View mRssFragment;

    @Override
    public void takeView(RssContract.View view) {
        mRssFragment = view;
    }

    @Override
    public void dropView() {
        mRssFragment = null;
    }

    @Override
    public void start() {
        mRssFragment.showProgress();
        mRepository.getRssFeed(new RssCallback() {
            @Override
            public void onLogined(List<RssItem> result) {
                mRssFragment.hideProgress();
                mRssFragment.showRss(result);
            }

            @Override
            public void onFailed(String error) {
                mRssFragment.hideProgress();
                mRssFragment.showNoRss();
            }
        });
    }
}
