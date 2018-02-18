package by.project.dartlen.rss_reader.rss;

import by.project.dartlen.rss_reader.di.scope.ActivityScope;
import by.project.dartlen.rss_reader.di.scope.FragmentScope;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class RssModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract RssFragment rssFragment();

    @ActivityScope
    @Binds
    abstract RssContract.Presenter rssPresenter(RssPresenter presenter);
}
