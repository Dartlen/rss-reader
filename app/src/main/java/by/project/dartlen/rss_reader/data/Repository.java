package by.project.dartlen.rss_reader.data;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import by.project.dartlen.rss_reader.data.local.Local;
import by.project.dartlen.rss_reader.data.local.LocalData;
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
