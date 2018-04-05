package by.project.dartlen.rss_reader.rss;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.project.dartlen.rss_reader.R;

public class RssViesHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.description)
    TextView description;

    @BindView(R.id.image)
    RoundedImageView image;

    public RssViesHolder(View view){
        super(view);
        ButterKnife.bind(this, view);
    }
}
