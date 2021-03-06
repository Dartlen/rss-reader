package by.project.dartlen.rss_reader.di.modules;

import by.project.dartlen.rss_reader.activity.MainActivity;
import by.project.dartlen.rss_reader.data.RepositoryModule;
import by.project.dartlen.rss_reader.di.scope.ActivityScope;
import by.project.dartlen.rss_reader.rss.RssModule;
import by.project.dartlen.rss_reader.webview.WebViewModule;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = {RepositoryModule.class, RssModule.class, WebViewModule.class})
    abstract MainActivity mainActivity();
}
