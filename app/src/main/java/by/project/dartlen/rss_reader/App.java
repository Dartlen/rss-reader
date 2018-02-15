package by.project.dartlen.rss_reader;

import javax.inject.Inject;

import by.project.dartlen.rss_reader.data.Repository;
import by.project.dartlen.rss_reader.di.AppComponent;
import by.project.dartlen.rss_reader.di.DaggerAppComponent;
import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class App extends DaggerApplication {

    @Inject
    Repository mRepository;

    @Override
    protected AndroidInjector<? extends  DaggerApplication> applicationInjector(){
        AppComponent appComponets = DaggerAppComponent.builder().application(this).build();
        appComponets.inject(this);
        return appComponets;
    }

}
