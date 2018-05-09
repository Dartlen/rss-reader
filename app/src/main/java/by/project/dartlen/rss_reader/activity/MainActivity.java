package by.project.dartlen.rss_reader.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.rustamg.filedialogs.FileDialog;
import com.rustamg.filedialogs.OpenFileDialog;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import butterknife.BindView;
import by.project.dartlen.rss_reader.R;
import by.project.dartlen.rss_reader.data.Repository;
import by.project.dartlen.rss_reader.data.local.realm.RssItemRealm;
import by.project.dartlen.rss_reader.data.local.realm.RssUrlRealm;
import by.project.dartlen.rss_reader.data.remote.callbacks.GetUrls;
import by.project.dartlen.rss_reader.data.remote.callbacks.RssCallback;
import by.project.dartlen.rss_reader.data.remote.callbacks.RssValidateCallback;
import by.project.dartlen.rss_reader.data.rss.RssFeed;
import by.project.dartlen.rss_reader.data.rss.RssItem;
import by.project.dartlen.rss_reader.data.rss.XMLParser;
import by.project.dartlen.rss_reader.di.scope.ActivityScope;
import by.project.dartlen.rss_reader.rss.RssFragment;
import by.project.dartlen.rss_reader.webview.WebViewFragment;
import dagger.android.support.DaggerAppCompatActivity;
import io.realm.RealmResults;

@ActivityScope
public class MainActivity extends DaggerAppCompatActivity implements FileDialog.OnFileSelectedListener {

    @Inject
    Repository mRepository;

    @Inject
    RssFragment mRssFragment;

    @BindView(R.id.fragment)
    ContentFrameLayout frameLayout;

    private NavigationView navigationView;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());*/

        drawer = findViewById(R.id.drawer_layout);
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
                                                mRepository.getRssFeed(new RssCallback() {
                                                    @Override
                                                    public void onLogined(List<RssItem> result) {
                                                        mRssFragment.showRss(result);
                                                        mRssFragment.hideNoRss();
                                                    }

                                                    @Override
                                                    public void onFailed(String error) {

                                                    }
                                                });
                                                recreate();
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
                            showFileDialog(new OpenFileDialog(), OpenFileDialog.class.getName());
                            break;
                        case R.id.nav_all:
                            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
                            if(fragment != null && fragment instanceof WebViewFragment){
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, mRssFragment).commit();
                            }
                            mRepository.getRssFeed(new RssCallback() {
                                @Override
                                public void onLogined(List<RssItem> result) {
                                    mRssFragment.clearData();
                                    mRssFragment.showRss(result);
                                    mRssFragment.hideNoRss();
                                }

                                @Override
                                public void onFailed(String error) {

                                }
                            });

                         default:{
                             DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                             drawer.closeDrawer(GravityCompat.START);
                             return super.onOptionsItemSelected(menuItem);
                         }
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

        new RxPermissions(this)
                .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    //showFileDialog(new OpenFileDialog(), OpenFileDialog.class.getName());
                    //mStoragePermissionGranted = granted;
                });



    }
    private void showFileDialog(FileDialog dialog, String tag) {

        Bundle args = new Bundle();

        dialog.setArguments(args);
        dialog.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme);
        dialog.show(getSupportFragmentManager(), tag);
    }

    @Override
    public void recreate()
    {
        if (android.os.Build.VERSION.SDK_INT >= 11)
        {
            super.recreate();
        }
        else
        {
            startActivity(getIntent());
            finish();
        }
    }

    public void createNavigationView(){
        mRepository.getUrls(new GetUrls() {
            @Override
            public RealmResults<RssItemRealm> onGetUrls(List<RssUrlRealm> result) {
                for(RssUrlRealm x: result)
                    navigationView.getMenu().add(x.getUrl()).setIcon(R.drawable.ic_menu_gallery).setOnMenuItemClickListener(
                            item -> {
                                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
                                if(fragment != null && fragment instanceof WebViewFragment){
                                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, mRssFragment).commit();
                                }
                                mRepository.getRssFeed(new RssCallback() {
                                    @Override
                                    public void onLogined(List<RssItem> result) {
                                        mRssFragment.clearData();
                                        mRssFragment.showRss(result);
                                        mRssFragment.hideNoRss();
                                    }

                                    @Override
                                    public void onFailed(String error) {

                                    }
                                }, item.getTitle().toString());
                                drawer.closeDrawer(Gravity.START);
                                return true;
                            }
                    );
                return null;
            }

            @Override
            public void onFailed(String error) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (fragment instanceof WebViewFragment
                ) {
            super.onBackPressed();
        } else {

        }

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

    @Override
    public void onFileSelected(FileDialog dialog, File file) {
        Toast.makeText(this, file.toString(), Toast.LENGTH_LONG).show();

        FileInputStream in = null;
        FileOutputStream out = null;

        try {
            in = new FileInputStream(file);
            Log.d(in.toString(),"dsa");
        }catch (Exception e){
            Log.d("dsafasf","dsa");
        }

        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }

        Log.d("text",text.toString());

        RssFeed rssFeed = new RssFeed();

            XMLParser parser = new XMLParser();
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = parserFactory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            xmlReader.setContentHandler(parser);



            InputSource inputSource = new InputSource(in);

            xmlReader.parse(inputSource);
            ArrayList<RssItem> items = parser.getItems();
            rssFeed.setItems(items);
            mRssFragment.clearData();
            mRssFragment.showRss(items);
            //mRepository.saveRssItems(items);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
//TODO:Задание 2. «Rss-reader»
//Требуется разработать приложение с графическим интерфейсом поддерживающее просмотр любой rss-ленты. Необходимо реализовать парсинг xml и отображение списка новостей и так же детальное отображение новости используя UIWebView(то есть открываться страница будет в приложении, а не браузере)
//Можно загружать новостную ленту как из файла так и из интернета.