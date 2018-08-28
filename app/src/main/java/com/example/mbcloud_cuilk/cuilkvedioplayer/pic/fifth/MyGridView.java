package com.example.mbcloud_cuilk.cuilkvedioplayer.pic.fifth;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by wbxmz19 on 2017/11/2.
 * 避免在SrollView里面嵌套的GridView或者ListView或者ExpandableListView无法全部展示数据
 */
public class MyGridView extends GridView {
    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridView(Context context) {
        super(context);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
