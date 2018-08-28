package com.example.mbcloud_cuilk.cuilkvedioplayer.datasave.litepal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.mbcloud_cuilk.cuilkvedioplayer.R;
import com.example.mbcloud_cuilk.cuilkvedioplayer.utils.ToastUtils;

import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.SaveCallback;
import org.litepal.crud.callback.UpdateOrDeleteCallback;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LitePalTestActivity extends AppCompatActivity {
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lite_pal_test);
        mContext = LitePalTestActivity.this;
    }

    /**
     * 创建数据库
     *
     * @param view
     */
    public void createDatabase(View view) {
        SQLiteDatabase db = Connector.getDatabase();
    }

    /**
     * 保存单条数据
     *
     * @param view
     */
    public void addSingleObject(View view) {
        News news = new News();
        news.setTitle("这是一条新闻标题");
        news.setContent("这是一条新闻内容");
        news.setPublishDate(new Date());

        news.save();//方式1:在主线程存储 在数据少的情况下没有影响
        news.saveThrows();//方式2:在主线程存储 在数据少的情况下没有影响 存储出错的情况下会抛异常
//        ToastUtils.showShort(mContext, news.toString());

        //方式3：在子线程线程存储 在存储数据量比较大的时候使用
        news.saveAsync().listen(new SaveCallback() {
            @Override
            public void onFinish(boolean success) {
                //返回结果又切回了主线程，可以直接做更新Ui的操作
                if (success) {
                    ToastUtils.showShort(mContext, "单条数据保存成功");
                } else {
                    ToastUtils.showShort(mContext, "单条数据保存失败");
                }

            }
        });
    }

    /**
     * 多对一关系增加对象
     *
     * @param view
     */
    public void addMultiRelation(View view) {
        Comment comment1 = new Comment();
        comment1.setContent("好评！");
        comment1.setPublishDate(new Date());
        comment1.save();
        Comment comment2 = new Comment();
        comment2.setContent("赞一个");
        comment2.setPublishDate(new Date());
        comment2.save();
        News news = new News();
        news.getCommentList().add(comment1);
        news.getCommentList().add(comment2);
        news.setTitle("第二条新闻标题");
        news.setContent("第二条新闻内容");
        news.setPublishDate(new Date());
        news.setCommentCount(news.getCommentList().size());
        news.save();
    }

    /**
     * 存储集合对象
     *
     * @param view
     */
    public void addList(View view) {
        //给集合添加数据
        List<News> newsList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            News news = new News();
            news.setTitle("这是一条新闻标题" + i);
            news.setContent("这是一条新闻内容" + i);
            news.setPublishDate(new Date());
            newsList.add(news);
        }
        //在主线程存储集合对象
//        DataSupport.saveAll(newsList);
        //在子线程线程存储 在存储数据量比较大的时候使用
        DataSupport.saveAllAsync(newsList).listen(new SaveCallback() {
            @Override
            public void onFinish(boolean success) {
                //返回结果又切回了主线程，可以直接做更新Ui的操作
                if (success) {
                    ToastUtils.showShort(mContext, "存储集合对象成功");
                } else {
                    ToastUtils.showShort(mContext, "存储集合对象失败");
                }
            }
        });
    }

    /**
     * 5,修改单个对象某个字段(把news表中id为2的记录的标题改成“今日iPhone6发布”)
     *
     * @param view
     */
    public void updateSingle(View view) {
         //方式1：
        ContentValues values = new ContentValues();
        values.put("title", "今日iPhone6发布");
        DataSupport.update(News.class, values, 2);
//        DataSupport.updateAsync(News.class, values, 2).listen(new UpdateOrDeleteCallback() {
//            @Override
//            public void onFinish(int rowsAffected) {
//
//            }
//        });
    }

    /**
     * 把news表中标题为“今日iPhone6发布”的所有新闻的标题改成“今日iPhone6 Plus发布”
     * @param view
     */
    public void updateSingleSecond(View view) {
        ContentValues values = new ContentValues();
        values.put("title", "今日iPhone6 Plus发布");
        DataSupport.updateAll(News.class, values, "title = ?", "今日iPhone6发布");
//        DataSupport.updateAllAsync(News.class, values, "title = ?", "今日iPhone6发布").listen(new UpdateOrDeleteCallback() {
//            @Override
//            public void onFinish(int rowsAffected) {
//
//            }
//        });
    }

    /**
     * 把news表中标题为“今日iPhone6发布”且评论数量大于0的所有新闻的标题改成“今日iPhone6 Plus发布”
     * @param view
     */
    public void updateSingleThird(View view) {
        ContentValues values = new ContentValues();
        values.put("title", "今日iPhone6 Plus发布");
        DataSupport.updateAll(News.class, values, "title = ? and commentcount > ?", "今日iPhone6发布", "0");
    }

    /**
     * 把news表中所有新闻的标题都改成“今日iPhone6发布”
     * @param view
     */
    public void updateAll(View view) {
        ContentValues values = new ContentValues();
        values.put("title", "今日iPhone6 Plus发布");
        DataSupport.updateAll(News.class, values);
    }

    /**
     * 把news表中id为2的记录的标题改成“今日iPhone6发布”
     * @param view
     */
    public void updateByCondition(View view) {
        News updateNews = new News();
        updateNews.setTitle("今日iPhone6发布");
        updateNews.update(2);
    }

    /**
     * 把news表中标题为“今日iPhone6发布”且评论数量大于0的所有新闻的标题改成“今日iPhone6 Plus发布”
     * @param view
     */
    public void updateByCondit(View view) {
        News updateNews = new News();
        updateNews.setTitle("今日iPhone6发布");
        updateNews.updateAll("title = ? and commentcount > ?", "今日iPhone6发布", "0");
    }

    /**
     * 把news表中所有新闻的评论数清零
     * @param view
     */
    public void updateLast(View view) {
        News updateNews = new News();
        updateNews.setToDefault("commentCount");
        updateNews.updateAll();
    }

    /**
     * 删除news表中id为2的记录
     * 需要注意的是，这不仅仅会将news表中id为2的记录删除，
     * 同时还会将其它表中以news id为2的这条记录作为外键的数据一起删除掉，
     * 因为外键既然不存在了，那么这么数据也就没有保留的意义了。
     * @param view
     */
    public void deleteFirst(View view) {
        DataSupport.delete(News.class, 2);
    }

    /**
     * 把news表中标题为“今日iPhone6发布”且评论数等于0的所有新闻都删除掉
     * @param view
     */
    public void deleteSecond(View view) {
        DataSupport.deleteAll(News.class, "title = ? and commentcount = ?", "今日iPhone6发布", "0");
    }

    /**
     * 把news表中所有的数据全部删除掉
     * @param view
     */
    public void deleteThird(View view) {
        DataSupport.deleteAll(News.class);
    }

    /**
     * 查询news表中id为1的这条记录
     * @param view
     */
    public void queryFirst(View view) {
        News news = DataSupport.find(News.class, 1);
    }

    /**
     * 取出表中的第一条数据
     * @param view
     */
    public void queryFirstItem(View view) {
        News firstNews = DataSupport.findFirst(News.class);
    }

    /**
     * 取出表中的最后一条数据
     * @param view
     */
    public void queryLastItem(View view) {
        News lastNews = DataSupport.findLast(News.class);
    }

    /**
     * 把news表中id为1、3、5、7的数据都查出来
     * @param view
     */
    public void queryItem(View view) {
        //方式1
        List<News> newsList = DataSupport.findAll(News.class, 1, 3, 5, 7);
        //方式2
//        long[] ids = new long[] { 1, 3, 5, 7 };
//        List<News> newsList = DataSupport.findAll(News.class, ids);
    }

    /**
     * 查询news表中的所有数据
     * @param view
     */
    public void queryAllItem(View view) {
        List<News> allNews = DataSupport.findAll(News.class);

    }

    /**
     * 查询news表中所有评论数大于零的新闻
     * @param view
     */
    public void queryLian1(View view) {
        List<News> newsList = DataSupport.where("commentcount > ?", "0").find(News.class);
    }

    /**
     * 查询news表中所有评论数大于零的新闻,只要title和content这两列数据
     * @param view
     */
    public void queryLian2(View view) {
        List<News> newsList = DataSupport.select("title", "content")
                .where("commentcount > ?", "0").find(News.class);
    }
    /**
     * 查询news表中所有评论数大于零的新闻,只要title和content这两列数据  将查询出的新闻按照发布的时间倒序排列
     * @param view
     */
    public void queryLian3(View view) {
        List<News> newsList = DataSupport.select("title", "content")
                .where("commentcount > ?", "0")
                .order("publishdate desc").find(News.class);
    }
    /**
     * 查询news表中所有评论数大于零的新闻,只要title和content这两列数据  将查询出的新闻按照发布的时间倒序排列 只要前10条数据
     * @param view
     */
    public void queryLian4(View view) {
        List<News> newsList = DataSupport.select("title", "content")
                .where("commentcount > ?", "0")
                .order("publishdate desc").limit(10).find(News.class);
    }

    /**
     * 查询news表中所有评论数大于零的新闻,只要title和content这两列数据  将查询出的新闻按照发布的时间倒序排列,对新闻进行分页展示，翻到第二页时，展示第11到第20条新闻"
     * @param view
     */
    public void queryLian5(View view) {
        List<News> newsList = DataSupport.select("title", "content")
                .where("commentcount > ?", "0")
                .order("publishdate desc").limit(10).offset(10)
                .find(News.class);
    }

    /**
     * 根据sql语句查询
     * @param view
     */
    public void queryBySql(View view) {
        Cursor cursor = DataSupport.findBySQL("select * from news where commentcount>?", "0");
    }

    /**
     * 聚合函数count() count()方法主要是用于统计行数的 统计news表中一共有多少行
     * @param view
     */
    public void jhCount(View view) {
        int result = DataSupport.count(News.class);
    }
    /**
     * 聚合函数count() count()方法主要是用于统计行数的 统计一共有多少条新闻是零评论的
     * @param view
     */
    public void jhCount2(View view) {
        int result = DataSupport.where("commentcount = ?", "0").count(News.class);
    }

    /**
     * 统计news表中评论的总数量
     * @param view
     */
    public void jhSum(View view) {
        int result = DataSupport.sum(News.class, "commentcount", int.class);
    }

    /**
     * 统计news表中平均每条新闻有多少评论
     * @param view
     */
    public void jhAverage(View view) {
        double result = DataSupport.average(News.class, "commentcount");
    }

    /**
     * 查询news表中所有新闻里面最高的评论数是多少
     * @param view
     */
    public void jhMax(View view) {
        int result = DataSupport.max(News.class, "commentcount", int.class);
    }
    /**
     * 查询news表中所有新闻里面最少的评论数是多少
     * @param view
     */
    public void jhMin(View view) {
        int result = DataSupport.min(News.class, "commentcount", int.class);
    }
}
