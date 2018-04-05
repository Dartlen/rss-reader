package by.project.dartlen.rss_reader.data.local.mappers;

import java.util.ArrayList;
import java.util.List;

import by.project.dartlen.rss_reader.data.local.realm.RssItemRealm;
import by.project.dartlen.rss_reader.data.rss.RssItem;
import io.realm.Realm;
import io.realm.RealmList;

public class RssItemsDataModelMapper {

    public RssItemsDataModelMapper(){}

    public RssItemRealm mapToRssItemRealm(RssItem rssItem){
        RssItemRealm rssItemRealm = Realm.getDefaultInstance().createObject(RssItemRealm.class);

        rssItemRealm.setDescription(rssItem.getDescription());
        rssItemRealm.setImage(rssItem.getImage());
        rssItemRealm.setLink(rssItem.getLink());
        rssItemRealm.setPublishDate(rssItem.getPublishDate());
        rssItemRealm.setTitle(rssItem.getTitle());
        rssItemRealm.setUrlId(1);

        return rssItemRealm;
    }

    public List<RssItemRealm> mapListToRssItemRealm(List<RssItem> listRssItem){
        RealmList<RssItemRealm> listRealm = new RealmList<>();
        for(RssItem x: listRssItem){
            listRealm.add(mapToRssItemRealm(x));
        }
        return listRealm;
    }

    public List<RssItem> mapToRssItemList(List<RssItemRealm> listRealm){
        List<RssItem> listRss = new ArrayList<>();
        for(RssItemRealm x: listRealm){
            listRss.add(mapToRssItem(x));
        }
        return listRss;
    }

    public RssItem mapToRssItem(RssItemRealm realmItem){
        RssItem item = new RssItem();
        item.setDescription(realmItem.getDescription());
        item.setImage(realmItem.getImage());
        item.setLink(realmItem.getLink());
        item.setPublishDate(realmItem.getPublishDate());
        item.setTitle(realmItem.getTitle());
        return item;
    }
}
