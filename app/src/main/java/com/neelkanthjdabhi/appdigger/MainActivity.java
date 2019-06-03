package com.neelkanthjdabhi.appdigger;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    BottomAppBar bottomAppBar;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomAppBar = findViewById(R.id.bar);
        bottomAppBar.replaceMenu(R.menu.bottom_nav_drawer_menu);
        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.info:
                        Intent aboutIntent = new Intent(MainActivity.this,about.class);
                        startActivity(aboutIntent);
                        break;

                    case R.id.analytics:
                        Intent AnalyticsIntent = new Intent(MainActivity.this,Analytics.class);
                        startActivity(AnalyticsIntent);
                        break;
                }
                return false;
            }
        });





        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // Passing the column number 1 to show online one column in each row.
        recyclerViewLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        adapter = new AppsAdapter(new ApkInfoExtractor(this).GetAllInstalledApkInfo());
        recyclerView.setAdapter(adapter);

        bottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFolder();

            }
        });

        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SimpleSearchDialogCompat(MainActivity.this, "Search Apps",
                        "What are you looking for...?", null, createSampleData(),
                        new SearchResultListener<SampleSearchModel>() {
                            @Override
                            public void onSelected(BaseSearchDialogCompat dialog,
                                                   SampleSearchModel item, int position) {

                                Toast.makeText(MainActivity.this, item.getTitle(),
                                        Toast.LENGTH_SHORT).show();
                                recyclerView.scrollToPosition(getPosFromName(item.getTitle()));
                                dialog.dismiss();
                            }
                        }).show();
            }
        });




    }

    private ArrayList<SampleSearchModel> createSampleData(){
        ArrayList<SampleSearchModel> items = new ArrayList<>();

        ApkInfoExtractor apkInfoExtractor = new ApkInfoExtractor(getApplicationContext());

        List<String> packageName = apkInfoExtractor.GetAllInstalledApkInfo();

        for (int i=0;i<packageName.size();i++)
        {
            items.add(new SampleSearchModel(apkInfoExtractor.GetAppName(packageName.get(i))));
        }

        return items;
    }

    private int  getPosFromName(String appName)
    {
        ApkInfoExtractor apkInfoExtractor = new ApkInfoExtractor(getApplicationContext());

        List<String> packageName = apkInfoExtractor.GetAllInstalledApkInfo();

        for (int i=0;i<packageName.size();i++)
        {
            if(appName.equalsIgnoreCase(apkInfoExtractor.GetAppName(packageName.get(i))))
            {
                return i;
            }
        }
        return -1;
    }

    public void openFolder(){
        Uri selectedUri = Uri.parse(Environment.getExternalStorageDirectory() + "/APP_DIGGER/");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(selectedUri, "resource/folder");

        if (intent.resolveActivityInfo(getPackageManager(), 0) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "Goto APP_DIGGER folder on your phone!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bottom_nav_drawer_menu, menu);
        return true;
    }


}
