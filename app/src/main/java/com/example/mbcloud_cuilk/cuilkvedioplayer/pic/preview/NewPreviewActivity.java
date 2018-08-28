package com.example.mbcloud_cuilk.cuilkvedioplayer.pic.preview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mbcloud_cuilk.cuilkvedioplayer.R;
import com.example.mbcloud_cuilk.cuilkvedioplayer.custom.ViewPagerFixed;
import com.example.mbcloud_cuilk.cuilkvedioplayer.custom.photoview.PhotoView;
import com.example.mbcloud_cuilk.cuilkvedioplayer.datasave.ListDataSave;
import com.example.mbcloud_cuilk.cuilkvedioplayer.utils.BitmapUtils;
import com.example.mbcloud_cuilk.cuilkvedioplayer.vedio.MediaPlaySVActivity;
import com.example.mbcloud_cuilk.cuilkvedioplayer.vedio.cameraview.MediaDbBean;
import com.githang.statusbar.StatusBarCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wbxmz19 on 2017/12/21. 预览页面
 */
public class NewPreviewActivity extends Activity {
    private IntruderViewPagerAdapter mAdapter;
    private TextView mTitle;
    private TabLayout mTabLayout;
    private ViewPagerFixed mViewPager;
    private ImageView mLeftBtn;
    ListDataSave carmedataSave;
    ListDataSave gallarydataSave;
    private List<String> mPathList = new ArrayList<>();
    private LayoutInflater inflater = null;
    private boolean isFromLp = false;
    private List<MediaDbBean> mediaDbSerBeanList;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_preview);
        //设置状态栏的颜色
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.bar_red));
        initView();
        initLocalData();

    }

    private void initView() {
        mTitle = (TextView) findViewById(R.id.title);
        mTabLayout = (TabLayout) findViewById(R.id.tab_main);
        mViewPager = (ViewPagerFixed) findViewById(R.id.vp_main);
        mLeftBtn = (ImageView) findViewById(R.id.left_btn);
    }

    private void initLocalData() {
        mediaDbSerBeanList = (List<MediaDbBean>) getIntent().getSerializableExtra("bean");
        if (null != mediaDbSerBeanList && mediaDbSerBeanList.size() > 0) {
            isFromLp = true;
        }

        carmedataSave = new ListDataSave(NewPreviewActivity.this, "carme");
        gallarydataSave = new ListDataSave(NewPreviewActivity.this, "gallary");
        List<String> carmephotoList = carmedataSave.getDataList("carmephotoList");
        List<String> gallaryList = gallarydataSave.getDataList("gallaryphotoList");
        if (mPathList.size() > 0) {
            mPathList.clear();
        }
        if (null != carmephotoList && carmephotoList.size() > 0) {
            mPathList.addAll(carmephotoList);
        }
        if (null != gallaryList && gallaryList.size() > 0) {
            for (int i = 0; i < gallaryList.size(); i++) {
                mPathList.add(gallaryList.get(i));
            }
        }

        mTitle.setText("预览");
        mAdapter = new IntruderViewPagerAdapter(NewPreviewActivity.this);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(6);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        //将TabLayout和ViewPager绑定在一起，使双方各自的改变都能直接影响另一方，解放了开发人员对双方变动事件的监听
        mTabLayout.setupWithViewPager(mViewPager);
        setupTabIcons();
        //指定Tab的位置
        if (mTabLayout.getChildCount() > 0) {
            if (mTabLayout.getTabAt(0) != null) {
                mTabLayout.getTabAt(0).select();
            }
        }
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }


    private void setupTabIcons() {
        if (isFromLp) {
            for (int i = 0; i < mediaDbSerBeanList.size(); i++) {
                mTabLayout.getTabAt(i).setCustomView(getTabView(i));
            }
        } else {
            for (int i = 0; i < mPathList.size(); i++) {
                mTabLayout.getTabAt(i).setCustomView(getTabView(i));
            }
        }

    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_tab, null);
        if (isFromLp) {
            imageUrl = mediaDbSerBeanList.get(position).fileUrl;
        } else {
            imageUrl = mPathList.get(position);
        }
        ImageView img_title = (ImageView) view.findViewById(R.id.iv_tab_view);
        img_title.setImageBitmap(BitmapUtils.centerSquareScaleBitmap(BitmapFactory.decodeFile(imageUrl), 120));
        return view;
    }


    public class IntruderViewPagerAdapter extends PagerAdapter {
        private Context context;

        public IntruderViewPagerAdapter(Context context) {
            super();
            context = context;
            //   mDrawableResIdList = resIdList;
        }

        @Override
        public int getCount() {
            if (isFromLp) {
                if (mediaDbSerBeanList != null) {
                    return mediaDbSerBeanList.size();
                }
            } else {
                if (mPathList != null) {
                    return mPathList.size();
                }
            }

            return 0;
        }

        @Override
        public int getItemPosition(Object object) {
            if (isFromLp) {
                if (object != null && mediaDbSerBeanList != null) {
                    String resId = (String) ((View) object).getTag();
                    if (resId != null) {
                        for (int i = 0; i < mediaDbSerBeanList.size(); i++) {
                            if (resId.equals(mediaDbSerBeanList.get(i))) {
                                return i;
                            }
                        }
                    }
                }
            } else {
                if (object != null && mPathList != null) {
                    String resId = (String) ((View) object).getTag();
                    if (resId != null) {
                        for (int i = 0; i < mPathList.size(); i++) {
                            if (resId.equals(mPathList.get(i))) {
                                return i;
                            }
                        }
                    }
                }
            }
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public Object instantiateItem(View container, int position) {
            if (isFromLp) {
                if (mediaDbSerBeanList != null && position < mediaDbSerBeanList.size()) {
                    final MediaDbBean bean = mediaDbSerBeanList.get(position);
                    final String imageUrl = bean.fileUrl;
                    String resId = imageUrl;
                    if (resId != null) {
                        ImageView itemView = null;
                        ImageView mAudioIcon = null;
                        PhotoView photoView = null;
                        View convertView = LayoutInflater.from(NewPreviewActivity.this).inflate(
                                R.layout.activity_index_gallery_item2, null);
                        itemView = (ImageView) convertView.findViewById(R.id.img);
                        mAudioIcon = (ImageView) convertView.findViewById(R.id.audio_icon);
                        photoView = (PhotoView) convertView.findViewById(R.id.photoview);
                        itemView.setVisibility(View.GONE);
                        mAudioIcon.setVisibility(View.GONE);
                        photoView.setVisibility(View.VISIBLE);
                        if (bean.type.equals("image")) {
                            itemView.setVisibility(View.GONE);
                            mAudioIcon.setVisibility(View.GONE);
                            photoView.setVisibility(View.VISIBLE);
                            Glide.with(NewPreviewActivity.this).load(bean.fileUrl).into(photoView);
                        } else if (bean.type.equals("vedio")) {
                            mAudioIcon.setVisibility(View.GONE);
                            itemView.setVisibility(View.VISIBLE);
                            photoView.setVisibility(View.GONE);
                            itemView.setImageBitmap(null);
                            Glide.with(NewPreviewActivity.this).load(bean.fileUrl).into(itemView);
                        } else if (bean.type.equals("audio")) {
                            mAudioIcon.setVisibility(View.VISIBLE);
                            itemView.setVisibility(View.GONE);
                            photoView.setVisibility(View.GONE);
                        }
//                        Glide.with(NewPreviewActivity.this).load(imageUrl).into(photoView);

                        //此处假设所有的照片都不同，用resId唯一标识一个itemView；也可用其它Object来标识，只要保证唯一即可
                        convertView.setTag(resId);

                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (bean.type.equals("vedio")) {
//                                    Intent intent=new Intent(NewPreviewActivity.this,TestNetVedioActivity.class);
                                    Intent intent = new Intent(NewPreviewActivity.this, MediaPlaySVActivity.class);
                                    String mNewFileUrl = bean.vedioPath;
                                    intent.putExtra("url", "file://" + mNewFileUrl);
                                    startActivity(intent);
                                }
// else if (detailBean.type.equals("audio")) {
//                                Intent intent2 = new Intent(NewPreviewActivity.this, AudioPlayActivity.class);
//                                intent2.putExtra("url", detailBean.fileUrl);
//                                startActivity(intent2);
//                            }
                            }
                        });
                        ((ViewPager) container).addView(convertView);
                        return convertView;
                    }
                }
            } else {
                if (mPathList != null && position < mPathList.size()) {
                    final String imageUrl = mPathList.get(position);
                    String resId = imageUrl;
                    if (resId != null) {
                        ImageView itemView = null;
                        ImageView mAudioIcon = null;
                        PhotoView photoView = null;
                        View convertView = LayoutInflater.from(NewPreviewActivity.this).inflate(
                                R.layout.activity_index_gallery_item2, null);
                        itemView = (ImageView) convertView
                                .findViewById(R.id.img);
                        mAudioIcon = (ImageView) convertView
                                .findViewById(R.id.audio_icon);
                        photoView = (PhotoView) convertView
                                .findViewById(R.id.photoview);
                        itemView.setVisibility(View.GONE);
                        mAudioIcon.setVisibility(View.GONE);
                        photoView.setVisibility(View.VISIBLE);
                        Glide.with(NewPreviewActivity.this).load(imageUrl).into(photoView);

                        //此处假设所有的照片都不同，用resId唯一标识一个itemView；也可用其它Object来标识，只要保证唯一即可
                        convertView.setTag(resId);

                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                            if (detailBean.type.equals("video")) {
////                                    Intent intent=new Intent(NewPreviewActivity.this,TestNetVedioActivity.class);
//                                Intent intent = new Intent(NewPreviewActivity.this, ThirdVedioPlayActivity.class);
//                                String mNewFileUrl = detailBean.fileUrl.replace("jpg", "mp4");
//                                intent.putExtra("url", "file://" + mNewFileUrl);
//                                startActivity(intent);
//                            } else if (detailBean.type.equals("audio")) {
//                                Intent intent2 = new Intent(NewPreviewActivity.this, AudioPlayActivity.class);
//                                intent2.putExtra("url", detailBean.fileUrl);
//                                startActivity(intent2);
//                            }
                            }
                        });
                        ((ViewPager) container).addView(convertView);
                        return convertView;
                    }
                }
            }

            return null;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            //注意：此处position是ViewPager中所有要显示的页面的position，与Adapter mDrawableResIdList并不是一一对应的。
            //因为mDrawableResIdList有可能被修改删除某一个item，在调用notifyDataSetChanged()的时候，ViewPager中的页面
            //数量并没有改变，只有当ViewPager遍历完自己所有的页面，并将不存在的页面删除后，二者才能对应起来
            if (object != null) {
                ViewGroup viewPager = ((ViewGroup) container);
                int count = viewPager.getChildCount();
                for (int i = 0; i < count; i++) {
                    View childView = viewPager.getChildAt(i);
                    if (childView == object) {
                        viewPager.removeView(childView);
                        break;
                    }
                }
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

    }


}
