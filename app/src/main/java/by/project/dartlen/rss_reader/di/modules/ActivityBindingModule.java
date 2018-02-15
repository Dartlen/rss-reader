package by.project.dartlen.rss_reader.di.modules;

import by.project.dartlen.rss_reader.activity.MainActivity;
import by.project.dartlen.rss_reader.data.Repository;
import by.project.dartlen.rss_reader.data.RepositoryModule;
import by.project.dartlen.rss_reader.di.scope.ActivityScope;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = {RepositoryModule.class})
    abstract MainActivity mainActivity();
}
