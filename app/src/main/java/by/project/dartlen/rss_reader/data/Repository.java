package by.project.dartlen.rss_reader.data;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import by.project.dartlen.rss_reader.data.local.Local;
import by.project.dartlen.rss_reader.data.local.LocalData;
import by.project.dartlen.rss_reader.data.local.mappers.RssItemsDataModelMapper;
import by.project.dartlen.rss_reader.data.local.realm.RssItemRealm;
import by.project.dartlen.rss_reader.data.local.realm.RssUrlRealm;
import by.project.dartlen.rss_reader.data.remote.Remote;
import by.project.dartlen.rss_reader.data.remote.RemoteData;
import by.project.dartlen.rss_reader.data.remote.callbacks.GetUrls;
import by.project.dartlen.rss_reader.data.remote.callbacks.RssCallback;
import by.project.dartlen.rss_reader.data.remote.callbacks.RssValidateCallback;
import by.project.dartlen.rss_reader.data.rss.RssItem;

@Singleton
public class Repository {



    final private RemoteData mRemoteData;
    final private LocalData mLocalData;

    @Inject
    Repository(@Remote RemoteData remoteDataSource, @Local LocalData localDataSource) {
        mRemoteData = remoteDataSource;
        mLocalData  = localDataSource;
    }

    public void getRssFeed(@NonNull RssCallback callback){
        getUrls(new GetUrls() {
            @Override
            public void onGetUrls(List<RssUrlRealm> result) {
                if(result.size()>0) {
                    RssItemsDataModelMapper mapper = new RssItemsDataModelMapper();
                    List<RssItemRealm> listRealm = mLocalData.getRssItemsRealm();
                    if (listRealm.size()>0)
                        callback.onLogined(mapper.mapToRssItemList(listRealm));
                    else
                        callback.onFailed("no data");

                    for(RssUrlRealm x: result){
                        mRemoteData.getRssFeedRemote(new RssCallback() {
                            @Override
                            public void onLogined(List<RssItem> result) {
                                mLocalData.saveRssItems(result);
                            }

                            @Override
                            public void onFailed(String error) {

                            }
                        }, x.getUrl());
                    }
                }
            }

            @Override
            public void onFailed(String error) {
                callback.onFailed(error);
            }
        });
        /*retrofit2.Call<RssFeed> call = mRssService.getRss(url);
        call.enqueue(new Callback<RssFeed>() {
            @Override
            public void onResponse(Call<RssFeed> call, Response<RssFeed> response) {
                callback.onLogined(response.body().getItems());
            }

            @Override
            public void onFailure(Call<RssFeed> call, Throwable t) {

            }
        });*/

        /*RealmResults<RssItemRealm> x = mLocalData.getRssItemsRealm();
        Log.d("das","dsa");*/
    }

    public void getRssFeed(@NonNull RssCallback callback, String url){
        getUrls(new GetUrls() {
            @Override
            public void onGetUrls(List<RssUrlRealm> result) {
                if(result.size()>0) {
                    RssItemsDataModelMapper mapper = new RssItemsDataModelMapper();
                    List<RssItemRealm> listRealm = mLocalData.getRssItemsRealm();
                    if (listRealm.size()>0)
                        callback.onLogined(mapper.mapToRssItemList(listRealm));
                    else
                        callback.onFailed("no data");

                    for(RssUrlRealm x: result){
                        if(x.getUrl().equals(url))
                            mRemoteData.getRssFeedRemote(new RssCallback() {
                                @Override
                                public void onLogined(List<RssItem> result) {
                                    mLocalData.saveRssItems(result);
                                }

                                @Override
                                public void onFailed(String error) {

                                }
                            }, x.getUrl());
                    }
                }
            }

            @Override
            public void onFailed(String error) {
                callback.onFailed(error);
            }
        });

    }

    public void saveRssItems(List<RssItem> listRssItems){
        mLocalData.saveRssItems(listRssItems);
    }

    public void saveUrl(String url){
        mLocalData.saveRssUrl(url);
    }

    public void validateRssFeedRemote(final RssValidateCallback callback, String url){
        mRemoteData.validateRssFeedRemote(new RssValidateCallback() {
            @Override
            public void onValidate(Boolean result) {
              callback.onValidate(result);
            }

            @Override
            public void onFailed(String error) {
                callback.onFailed(error);
            }
        }, url);
    }

    public void getUrls(final GetUrls callback){
        mLocalData.getUrls(new GetUrls() {
            @Override
            public void onGetUrls(List<RssUrlRealm> result) {
                callback.onGetUrls(result);
            }

            @Override
            public void onFailed(String error) {
                callback.onFailed(error);
            }
        });
    }

}
