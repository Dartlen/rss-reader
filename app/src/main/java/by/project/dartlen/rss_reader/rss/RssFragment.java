package by.project.dartlen.rss_reader.rss;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.project.dartlen.rss_reader.R;
import by.project.dartlen.rss_reader.data.rss.RssItem;
import dagger.android.support.DaggerFragment;


public class RssFragment extends DaggerFragment implements RssContract.View {

    @Inject
    RssContract.Presenter mRssPresenter;

    @Inject
    public RssFragment(){}

    private RssAdapter adapter;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.progress)
    ProgressBar mProgress;

    @BindView(R.id.norss)
    TextView noRss;

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRssPresenter.takeView(this);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rss, container, false);
        ButterKnife.bind(this, v);
        adapter = new RssAdapter();
        mRssPresenter.start();




        GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        return v;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showNoRss() {
        noRss.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoRss() {
        noRss.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showRss(List<RssItem> list) {
        adapter.addRss(list);
        recyclerView.setVisibility(View.VISIBLE);
    }
}
