package by.project.dartlen.rss_reader.rss;

import javax.inject.Inject;

import by.project.dartlen.rss_reader.data.Repository;

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
}
