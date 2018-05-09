package by.project.dartlen.rss_reader.data.remote.retrofit;

import by.project.dartlen.rss_reader.data.rss.RssFeed;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RssService {

    /**
     * No baseUrl defined. Each RSS Feed will be consumed by it's Url
     * @param url RSS Feed Url
     * @return Retrofit Call
     */
    @GET
    Call<RssFeed> getRss(@Url String url);

}
