package by.project.dartlen.rss_reader.di.modules;

import android.content.Context;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@Module(includes = ApplicationModule.class)
public class NetworkModule {

    @Provides
    public File provideCacheFile(Context context){
        return new File(context.getCacheDir(), "okhttp_cache");
    }

    @Provides
    public Cache provideCache(File cacheFile){
        return new Cache(cacheFile, 10*1000*1000);
    }

    @Provides
    public HttpLoggingInterceptor loggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message -> {});
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return interceptor;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkhttpClient(HttpLoggingInterceptor loggingInterceptor, Cache cache) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.cache(cache).addInterceptor(loggingInterceptor);
        return client.build();
    }

}

