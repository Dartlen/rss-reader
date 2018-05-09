package by.project.dartlen.rss_reader.data.rss;

import java.util.List;

/**
 * RSS Feed response model
 */

public class RssFeed {

    /**
     * List of parsed {@link RssItem} objects
     */
    private List<RssItem> mItems;

    public List<RssItem> getItems() {
        return mItems;
    }

    public void setItems(List<RssItem> items) {
        mItems = items;
    }
}
