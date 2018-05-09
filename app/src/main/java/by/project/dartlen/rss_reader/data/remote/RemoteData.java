package by.project.dartlen.rss_reader.data.remote;

import javax.inject.Inject;
import javax.inject.Singleton;

import by.project.dartlen.rss_reader.data.remote.callbacks.RssCallback;
import by.project.dartlen.rss_reader.data.remote.callbacks.RssValidateCallback;
import by.project.dartlen.rss_reader.data.remote.retrofit.RssService;
import by.project.dartlen.rss_reader.data.rss.RssFeed;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class RemoteData {

    @Inject
    RemoteData(){}

    @Inject
    RssService mRssService;

    public void getRssFeedRemote(final RssCallback callback, String url){
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

    public void validateRssFeedRemote(final RssValidateCallback callback, String url){
        mRssService.getRss(url).enqueue(new Callback<RssFeed>() {
            @Override
            public void onResponse(Call<RssFeed> call, Response<RssFeed> response) {
                if(response.body().getItems()==null) {
                    if (response.body().getItems().size() != 0)
                        callback.onValidate(true);
                    else
                        callback.onFailed("Неверная url");
                }else{
                    callback.onFailed("Неверная url");
                }
            }

            @Override
            public void onFailure(Call<RssFeed> call, Throwable t) {
                callback.onFailed(call.toString());
            }
        });
    }
}
