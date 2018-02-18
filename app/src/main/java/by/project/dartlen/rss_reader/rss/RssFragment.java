package by.project.dartlen.rss_reader.rss;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import by.project.dartlen.rss_reader.R;
import by.project.dartlen.rss_reader.di.scope.ActivityScope;
import dagger.android.support.DaggerFragment;

@ActivityScope
public class RssFragment extends DaggerFragment implements RssContract.View {

    @Inject
    RssContract.Presenter mRssPresenter;

    @Inject
    public RssFragment(){}

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rss, container, false);
    }
}
