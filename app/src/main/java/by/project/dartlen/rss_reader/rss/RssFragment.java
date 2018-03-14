package by.project.dartlen.rss_reader.rss;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.project.dartlen.rss_reader.R;
import dagger.android.support.DaggerFragment;


public class RssFragment extends DaggerFragment implements RssContract.View {

    @Inject
    RssContract.Presenter mRssPresenter;

    @Inject
    public RssFragment(){}

    private RssAdapter adapter;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

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
        View v = inflater.inflate(R.layout.fragment_rss, container, false);
        ButterKnife.bind(this, v);

        adapter = new RssAdapter();
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);



        return v;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
