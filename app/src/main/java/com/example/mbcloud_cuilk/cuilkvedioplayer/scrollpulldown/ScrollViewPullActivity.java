package com.example.mbcloud_cuilk.cuilkvedioplayer.scrollpulldown;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mbcloud_cuilk.cuilkvedioplayer.R;

public class ScrollViewPullActivity extends AppCompatActivity {
    ElasticScrollView elasticScrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_view_pull);
        elasticScrollView = (ElasticScrollView)findViewById(R.id.scrollview1);
//        View mScrollChildView = View.inflate(this, R.layout.scroll_child_view, null);
////        for(int i=1;i<=50;i++){
////            TextView tempTextView = new TextView(this);
////            tempTextView.setText("Text:" + i);
////            elasticScrollView.addChild(tempTextView,1);
////        }
//        elasticScrollView.addChild(mScrollChildView,1);

        final Handler handler = new Handler() {
            public void handleMessage(Message message) {
                String str = (String)message.obj;
                OnReceiveData(str);
            }
        };
        elasticScrollView.setonRefreshListener(new ElasticScrollView.OnRefreshListener() {

            @Override
            public void onRefresh() {
                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Message message = handler.obtainMessage(0, "new Text");
                        handler.sendMessage(message);
                    }
                });
                thread.start();
            }
        });
    }
    protected void OnReceiveData(String str) {
        TextView textView =  new TextView(this);
        textView.setText(str);
        elasticScrollView.addChild(textView, 1);
        elasticScrollView.onRefreshComplete();
    }
}
