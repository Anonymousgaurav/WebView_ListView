package com.example.textureview_listview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.example.textureview_listview.Adapter.RvAdapter;
import com.example.textureview_listview.Model.DataSetList;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    RecyclerView recyclerView;
    List<DataSetList>listData= new ArrayList<>();
    RvAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.Recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        initData();

        adapter = new RvAdapter(listData,getApplicationContext());
        recyclerView.setAdapter(adapter);

    }

    private void initData()
    {
        DataSetList dataSetList = new DataSetList("http://d34tgsvaeg86ru.cloudfront.net/transcodedvideos/130800655237_1574233199740.mp4");
        listData.add(dataSetList);
        dataSetList = new DataSetList("http://d34tgsvaeg86ru.cloudfront.net/transcodedvideos/d7b5aa097ed9b985_1573904133657.mp4");
        listData.add(dataSetList);
        dataSetList = new DataSetList("http://d34tgsvaeg86ru.cloudfront.net/transcodedvideos/111740682045_1573192729888.mp4");
        listData.add(dataSetList);
        dataSetList = new DataSetList("http://d34tgsvaeg86ru.cloudfront.net/transcodedvideos/14ad2a326d2e2246_1573055574824.mp4");
        listData.add(dataSetList);

        Log.d("abc", String.valueOf(listData));
    }
}
