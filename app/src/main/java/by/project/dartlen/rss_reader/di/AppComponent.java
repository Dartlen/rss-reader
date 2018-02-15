package by.project.dartlen.rss_reader.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import by.project.dartlen.rss_reader.data.Repository;
import by.project.dartlen.rss_reader.data.RepositoryModule;
import by.project.dartlen.rss_reader.data.local.LocalModule;
import by.project.dartlen.rss_reader.data.remote.retrofit.ApiInterfaceModule;
import by.project.dartlen.rss_reader.data.remote.retrofit.RssService;
import by.project.dartlen.rss_reader.di.modules.ActivityBindingModule;
import by.project.dartlen.rss_reader.di.modules.ApplicationModule;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dagger.android.support.DaggerApplication;

@Singleton
@Component(modules = {ApplicationModule.class,
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class,
        ApiInterfaceModule.class,
        RepositoryModule.class,
        LocalModule.class
        })
public interface AppComponent extends AndroidInjector<DaggerApplication> {

    Context bindContext();
    Repository getRepository();
    RssService getApiInterface();

    @Component.Builder
    interface Builder{
        @BindsInstance
        AppComponent.Builder application(Application application);
        AppComponent build();
    }
}
