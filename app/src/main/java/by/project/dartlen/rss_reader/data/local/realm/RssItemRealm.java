package by.project.dartlen.rss_reader.data.local.realm;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

public class RssItemRealm extends RealmObject {

    private String mTitle;
    private String mLink;
    private String mImage;
    private String mPublishDate;
    private String mDescription;

    @Index
    public long urlId;

    public RssItemRealm(){}

    public String getDescription() {
        return mDescription;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String Title) {
        this.mTitle = Title;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String Link) {
        this.mLink = Link;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String Image) {
        this.mImage = Image;
    }

    public String getPublishDate() {
        return mPublishDate;
    }

    public void setPublishDate(String PublishDate) {
        this.mPublishDate = PublishDate;
    }

    public void setDescription(String Description) {
        this.mDescription = Description;
    }

    public long getUrlId() {
        return urlId;
    }

    public void setUrlId(long urlId) {
        this.urlId = urlId;
    }
}
