package by.project.dartlen.rss_reader.data.remote.callbacks;

public interface RssValidateCallback {
    void onValidate(Boolean result);
    void onFailed(String error);
}
