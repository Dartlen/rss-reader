package by.project.dartlen.rss_reader.rss;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import by.project.dartlen.rss_reader.R;

public class RssAdapter extends RecyclerView.Adapter<RssViesHolder> {

    @Override
    public RssViesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RssViesHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rss, parent, false));
    }

    @Override
    public void onBindViewHolder(RssViesHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
