package by.project.dartlen.rss_reader.webview;

import by.project.dartlen.rss_reader.di.scope.ActivityScope;
import by.project.dartlen.rss_reader.di.scope.FragmentScope;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class WebViewModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract WebViewFragment webViewFragment();

    @ActivityScope
    @Binds
    abstract WebViewContract.Presenter webViewPresenter(WebViewPresenter presenter);
}
