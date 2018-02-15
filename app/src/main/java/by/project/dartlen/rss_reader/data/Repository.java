package by.project.dartlen.rss_reader.data;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import by.project.dartlen.rss_reader.data.local.Local;
import by.project.dartlen.rss_reader.data.local.LocalData;
import by.project.dartlen.rss_reader.data.remote.Remote;
import by.project.dartlen.rss_reader.data.remote.RemoteData;
import by.project.dartlen.rss_reader.data.remote.retrofit.RssCallback;
import by.project.dartlen.rss_reader.data.remote.retrofit.RssService;
import by.project.dartlen.rss_reader.data.rss.RssFeed;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class Repository {

    @Inject
    RssService mRssService;

    final private RemoteData mRemoteData;
    final private LocalData mLocalData;

    @Inject
    Repository(@Remote RemoteData remoteDataSource, @Local LocalData localDataSource) {
        mRemoteData = remoteDataSource;
        mLocalData  = localDataSource;
    }

    public void getRssFeed(@NonNull RssCallback callback, String url){
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

        mRssService.getRss(url).enqueue(new Callback<RssFeed>() {
            @Override
            public void onResponse(Call<RssFeed> call, Response<RssFeed> response) {
                callback.onLogined(response.body().getItems());
            }

            @Override
            public void onFailure(Call<RssFeed> call, Throwable t) {
                callback.onFailed(call.toString());
            }
        });
    }
}
