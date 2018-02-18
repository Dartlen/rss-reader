package by.project.dartlen.rss_reader.data.local.mappers;

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

        return rssItemRealm;
    }

    public List<RssItemRealm> mapListToRssItemRealm(List<RssItem> listRssItem){
        RealmList<RssItemRealm> listRealm = new RealmList<>();
        for(RssItem x: listRssItem){
            listRealm.add(mapToRssItemRealm(x));
        }
        return listRealm;
    }
}
