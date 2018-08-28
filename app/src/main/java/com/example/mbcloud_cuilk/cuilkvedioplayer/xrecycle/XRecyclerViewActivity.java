package com.example.mbcloud_cuilk.cuilkvedioplayer.xrecycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.mbcloud_cuilk.cuilkvedioplayer.R;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

public class XRecyclerViewActivity extends AppCompatActivity implements XRecyclerView.LoadingListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xrecycler_view);
        XRecyclerView listview = (XRecyclerView) findViewById(R.id.listview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(XRecyclerViewActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listview.setLayoutManager(layoutManager);

        listview.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        listview.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        listview.setArrowImageView(R.drawable.iconfont_downgrey);
        listview.setLoadingListener(this);
        listview.setLoadingMoreEnabled(true);

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
