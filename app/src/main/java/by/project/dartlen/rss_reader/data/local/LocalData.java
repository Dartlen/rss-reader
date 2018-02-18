package by.project.dartlen.rss_reader.data.local;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import by.project.dartlen.rss_reader.data.local.mappers.RssItemsDataModelMapper;
import by.project.dartlen.rss_reader.data.local.realm.RssItemRealm;
import by.project.dartlen.rss_reader.data.rss.RssItem;
import io.realm.Realm;
import io.realm.RealmResults;

@Singleton
public class LocalData {

    @Inject
    Realm realm;

    private RssItemsDataModelMapper mapper;

    @Inject
    public LocalData(){
        mapper = new RssItemsDataModelMapper();
    }

    public RealmResults<RssItemRealm> getRssItemsRealm(){
        return realm.where(RssItemRealm.class).findAll();
    }

    public void saveRssItems(List<RssItem> listRssItems){
        realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(mapper.mapListToRssItemRealm(listRssItems)));
    }
}
