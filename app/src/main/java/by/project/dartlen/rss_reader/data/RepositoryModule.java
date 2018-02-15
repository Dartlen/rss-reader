package by.project.dartlen.rss_reader.data;

import javax.inject.Singleton;

import by.project.dartlen.rss_reader.data.local.Local;
import by.project.dartlen.rss_reader.data.local.LocalData;
import by.project.dartlen.rss_reader.data.remote.Remote;
import by.project.dartlen.rss_reader.data.remote.RemoteData;
import by.project.dartlen.rss_reader.di.modules.ApplicationModule;
import dagger.Binds;
import dagger.Module;

@Module
public abstract class RepositoryModule {

    @Singleton
    @Binds
    @Remote
    abstract RemoteData providerRemoteDataSource(RemoteData remoteDataSource);

    @Singleton
    @Binds
    @Local
    abstract LocalData providerLocalDataSource(LocalData localDataSource);
}
