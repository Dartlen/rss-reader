package by.project.dartlen.rss_reader.data.remote.retrofit;

import by.project.dartlen.rss_reader.data.rss.RssConverterFactory;
import by.project.dartlen.rss_reader.di.modules.ApplicationModule;
import by.project.dartlen.rss_reader.di.modules.NetworkModule;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Module(includes = {ApplicationModule.class, NetworkModule.class})
public class ApiInterfaceModule {

    @Provides
    public Retrofit provideRetrofit(OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .addConverterFactory(RssConverterFactory.create())
                .client(okHttpClient)
                .baseUrl("https://github.com")
                .build();
    }

    @Provides
    public RssService provideApiInterface(Retrofit retrofit){
        return retrofit.create(RssService.class);
    }
}

