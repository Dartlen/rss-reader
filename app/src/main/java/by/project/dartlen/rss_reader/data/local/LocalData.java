package by.project.dartlen.rss_reader.data.local;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import by.project.dartlen.rss_reader.data.local.mappers.RssItemsDataModelMapper;
import by.project.dartlen.rss_reader.data.local.realm.RssItemRealm;
import by.project.dartlen.rss_reader.data.local.realm.RssUrlRealm;
import by.project.dartlen.rss_reader.data.remote.callbacks.GetUrls;
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
        realm.executeTransactionAsync(realm1 -> realm1.copyToRealmOrUpdate(mapper.mapListToRssItemRealm(listRssItems)));
    }

    public void saveRssUrl(String url){
        realm.executeTransactionAsync(realm1 ->
        {
            RssUrlRealm urlRealm = new RssUrlRealm();
            urlRealm.setUrl(url);
            realm1.copyToRealmOrUpdate(urlRealm);
        });
    }

    public void getUrls(final GetUrls callback){
        RealmResults<RssUrlRealm> realmsUrls = realm.where(RssUrlRealm.class).findAllAsync();
        realmsUrls.load();
        if(realmsUrls.size()>0){
            callback.onGetUrls(realmsUrls);
        }else{
            callback.onFailed("don't searched urls!");
        }
    }
}
