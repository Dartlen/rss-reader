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

    public RealmResults<RssItemRealm> getRssItemsRealm(String url){
        getUrls(new GetUrls() {
            @Override
            public RealmResults<RssItemRealm> onGetUrls(List<RssUrlRealm> result) {
                for(RssUrlRealm x: result)
                    if(x.equals(result))
                        return realm.where(RssItemRealm.class).equalTo("urlId", x.getId()).findAll();
                return null;
            }

            @Override
            public void onFailed(String error) {
                return;
            }
        });
        return null;
    }

    public void saveRssItems(List<RssItem> listRssItems){
        realm.executeTransactionAsync(realm -> {

            for(RssItem item: listRssItems){
                RssItemRealm search = realm.where(RssItemRealm.class).equalTo("mLink", item.getLink()).findFirst();
                if(search==null)
                    mapper.mapToRssItemRealm(item);
            }

        }, () -> {

        });

    }

    public void saveRssUrl(String url){
        realm.executeTransaction(realm1 ->
        {
            RssUrlRealm urlRealm = new RssUrlRealm();
            urlRealm.setUrl(url);
            RssUrlRealm search = realm.where(RssUrlRealm.class).equalTo("url", url).findFirst();
            Number searchId = realm.where(RssUrlRealm.class).max("id");
            int nextId;
            if(searchId==null) {
                nextId = 1;
            }else {
                nextId = searchId.intValue()+1;
            }
            if(search==null) {
                urlRealm.setId(nextId);
                realm1.insertOrUpdate(urlRealm);
            }
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
