package by.project.dartlen.rss_reader.data.local;

import android.app.Application;

import javax.inject.Singleton;

import by.project.dartlen.rss_reader.di.modules.ApplicationModule;
import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

@Module(includes = ApplicationModule.class)
public class LocalModule {

    @Provides
    @Singleton
    RealmConfiguration provideRealmConfiguration(Application application)
    {
        return new RealmConfiguration.Builder()
                .name("rss.realm")
                .build();
    }

    @Provides
    @Singleton
    Realm provideRealm(RealmConfiguration realmConfiguration)
    {
        return Realm.getInstance(realmConfiguration);
    }
}
