package by.project.dartlen.rss_reader.data.remote.retrofit;

import java.util.List;

import by.project.dartlen.rss_reader.data.rss.RssFeed;
import by.project.dartlen.rss_reader.data.rss.RssItem;

public interface RssCallback {
    void onLogined(List<RssItem> result);
    void onFailed(String error);

}
