package by.project.dartlen.rss_reader.rss;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import by.project.dartlen.rss_reader.R;
import by.project.dartlen.rss_reader.data.rss.RssItem;

public class RssAdapter extends RecyclerView.Adapter<RssViesHolder> {

    private List<RssItem> list = new ArrayList<>(0);

    @Override
    public RssViesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RssViesHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rss, parent, false));
    }

    @Override
    public void onBindViewHolder(RssViesHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.description.setText(list.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        if(list.size()==0)
            return 0;
        return list.size();
    }

    public void addRss(List<RssItem> list){
        for(RssItem x: list){
            add(x);
        }
        //notifyDataSetChanged();
    }

    public void add(RssItem item){
        list.add(item);
        notifyDataSetChanged();
    }

}
