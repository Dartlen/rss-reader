package by.project.dartlen.rss_reader.data.remote.callbacks;

import java.util.List;

import by.project.dartlen.rss_reader.data.local.realm.RssUrlRealm;

public interface GetUrls {
    void onGetUrls(List<RssUrlRealm> result);
    void onFailed(String error);
}
