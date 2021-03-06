package by.project.dartlen.rss_reader.rss;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.project.dartlen.rss_reader.R;
import by.project.dartlen.rss_reader.data.rss.RssItem;
import by.project.dartlen.rss_reader.di.scope.ActivityScope;
import by.project.dartlen.rss_reader.webview.WebViewFragment;
import dagger.android.support.DaggerFragment;

@ActivityScope
public class RssFragment extends DaggerFragment implements RssContract.View, onClickListener {

    @Inject
    RssContract.Presenter mRssPresenter;

    @Inject
    WebViewFragment mWebViewFragment;

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
        adapter = new RssAdapter(this);
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
        //mProgress.setVisibility(View.VISIBLE);
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

    public void clearData(){
        adapter.clearData();
    }

    @Override
    public void onClick(String url) {

        if(getActivity()!=null) {
            final Bundle bundle = new Bundle();
            bundle.putString("url", url);

            mWebViewFragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, mWebViewFragment).addToBackStack("WebView").commit();
        }
    }
}
