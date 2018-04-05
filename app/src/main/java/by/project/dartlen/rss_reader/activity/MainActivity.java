package by.project.dartlen.rss_reader.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import by.project.dartlen.rss_reader.R;
import by.project.dartlen.rss_reader.data.Repository;
import by.project.dartlen.rss_reader.data.local.realm.RssUrlRealm;
import by.project.dartlen.rss_reader.data.remote.callbacks.GetUrls;
import by.project.dartlen.rss_reader.data.remote.callbacks.RssCallback;
import by.project.dartlen.rss_reader.data.remote.callbacks.RssValidateCallback;
import by.project.dartlen.rss_reader.data.rss.RssItem;
import by.project.dartlen.rss_reader.di.scope.ActivityScope;
import by.project.dartlen.rss_reader.rss.RssFragment;
import dagger.android.support.DaggerAppCompatActivity;

@ActivityScope
public class MainActivity extends DaggerAppCompatActivity{

    @Inject
    Repository mRepository;

    @Inject
    RssFragment mRssFragment;

    @BindView(R.id.fragment)
    ContentFrameLayout frameLayout;

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);

        createNavigationView();

        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    // set item as selected to persist highlight
                    menuItem.setChecked(true);
                    // close drawer when item is tapped
                    drawer.closeDrawers();

                    switch (menuItem.getItemId()){
                        case R.id.nav_add_channel: {
                            final EditText txtUrl = new EditText(this);
                            new AlertDialog.Builder(this)
                                    .setTitle("Введите ссылку ресурса")
                                    .setMessage("Ссылка должна ссылаться на rss ленту")
                                    .setView(txtUrl)
                                    .setPositiveButton("Добавить", (dialog, which) -> {
                                        String url = txtUrl.getText().toString();

                                        mRepository.validateRssFeedRemote(new RssValidateCallback() {
                                            @Override
                                            public void onValidate(Boolean result) {
                                                mRepository.saveUrl(url);

                                            }

                                            @Override
                                            public void onFailed(String error) {
                                                Toast.makeText(getApplication(), url, Toast.LENGTH_LONG).show();
                                            }
                                        }, url);

                                        dialog.dismiss();
                                    })
                                    .setNegativeButton("Отмена", (dialog, which) -> {
                                        dialog.dismiss();
                                    }).show();
                            break;
                        }
                        case R.id.nav_load_from_file:
                            break;
                        case R.id.nav_all:
                            mRepository.getRssFeed(new RssCallback() {
                                @Override
                                public void onLogined(List<RssItem> result) {
                                    mRssFragment.showRss(result);
                                }

                                @Override
                                public void onFailed(String error) {

                                }
                            });
                    }
                    // Add code here to update the UI based on the item selected
                    // For example, swap UI fragments here

                    return true;
                });



        getSupportFragmentManager().beginTransaction().add(R.id.fragment, mRssFragment).addToBackStack("notefragment").commit();

        /*mRepository.getRssFeed(new RssCallback() {
            @Override
            public void onLogined(List<RssItem> result) {
                mRepository.saveRssItems(result);
            }

            @Override
            public void onFailed(String error) {

            }
        },"https://news.tut.by/rss/index.rss");*/



    }

    public void createNavigationView(){
        mRepository.getUrls(new GetUrls() {
            @Override
            public void onGetUrls(List<RssUrlRealm> result) {
                for(RssUrlRealm x: result)
                    navigationView.getMenu().add(x.getUrl()).setIcon(R.drawable.ic_menu_gallery).setOnMenuItemClickListener(
                            item -> {
                                mRepository.getRssFeed(new RssCallback() {
                                    @Override
                                    public void onLogined(List<RssItem> result) {
                                        mRssFragment.showRss(result);

                                    }

                                    @Override
                                    public void onFailed(String error) {

                                    }
                                }, item.getTitle().toString());
                                return true;
                            }
                    );
            }

            @Override
            public void onFailed(String error) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*@SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add_channel) {
            final EditText txtUrl = new EditText(this);
            new AlertDialog.Builder(this)
                    .setTitle("Введите ссылку ресурса")
                    .setMessage("Ссылка должна ссылаться на rss ленту")
                    .setView(txtUrl)
                    .setPositiveButton("Добавить", (dialog, which) -> {
                       String url = txtUrl.getText().toString();
                        Toast.makeText(this,url,Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    })
                    .setNegativeButton("Отмена", (dialog, which) -> {
                        dialog.dismiss();
                    }).show();

        } else if (id == R.id.nav_load_from_file) {

        }/* else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } *//*else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/


}
//TODO:Задание 2. «Rss-reader»
//Требуется разработать приложение с графическим интерфейсом поддерживающее просмотр любой rss-ленты. Необходимо реализовать парсинг xml и отображение списка новостей и так же детальное отображение новости используя UIWebView(то есть открываться страница будет в приложении, а не браузере)
//Можно загружать новостную ленту как из файла так и из интернета.