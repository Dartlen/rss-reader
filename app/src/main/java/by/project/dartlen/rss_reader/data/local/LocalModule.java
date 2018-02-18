package by.project.dartlen.rss_reader.data.local;

import android.app.Application;

import javax.inject.Singleton;

import by.project.dartlen.rss_reader.di.modules.ApplicationModule;
import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module(includes = ApplicationModule.class)
public class LocalModule {

    @Provides
    @Singleton
    Realm provideRealm(Application application)
    {
        Realm.init(application);
        return Realm.getDefaultInstance();
    }
}
