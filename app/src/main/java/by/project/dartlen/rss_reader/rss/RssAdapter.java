package by.project.dartlen.rss_reader.rss;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import by.project.dartlen.rss_reader.R;
import by.project.dartlen.rss_reader.data.rss.RssItem;

public class RssAdapter extends RecyclerView.Adapter<RssViesHolder> {

    private List<RssItem> list = new ArrayList<>(0);

    private onClickListener onClick;

    public RssAdapter(onClickListener onClick){
        this.onClick = onClick;
    }

    @Override
    public RssViesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RssViesHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rss, parent, false));
    }

    @Override
    public void onBindViewHolder(RssViesHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        Uri uri = Uri.parse(list.get(position).getImage());

        holder.image.setImageURI(uri);

        holder.itemView.setOnClickListener(v -> {
            onClick.onClick(list.get(position).getLink());
        });
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

    public void clearData(){
        list.clear();
        notifyDataSetChanged();
    }

}
