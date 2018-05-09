package by.project.dartlen.rss_reader.data.remote.callbacks;

import java.util.List;

import by.project.dartlen.rss_reader.data.local.realm.RssItemRealm;
import by.project.dartlen.rss_reader.data.local.realm.RssUrlRealm;
import io.realm.RealmResults;

public interface GetUrls {
    RealmResults<RssItemRealm> onGetUrls(List<RssUrlRealm> result);
    void onFailed(String error);
}
