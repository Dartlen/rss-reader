package by.project.dartlen.rss_reader.base;

public interface BasePresenter<T> {
    void takeView(T view);
    void dropView();
}
