package by.project.dartlen.rss_reader.data.local.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RssUrlRealm extends RealmObject {

    @PrimaryKey
    public long id;

    private String url;

    public RssUrlRealm() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
