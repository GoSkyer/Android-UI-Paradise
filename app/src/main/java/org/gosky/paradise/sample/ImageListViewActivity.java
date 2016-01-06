package org.gosky.paradise.sample;

import org.gosky.paradise.R;
import org.gosky.paradise.base.BaseActivity;
import org.gosky.paradise.imagelistview.ImageListView;

import java.util.ArrayList;

/**
 * 多图片显示控件
 *
 * @author galaxy captain
 * @date 2016/1/6
 */
public class ImageListViewActivity extends BaseActivity {

    private ImageListView imageListView;

    private ArrayList<String> mList = new ArrayList<>();

    @Override
    protected int setRootView() {
        return R.layout.activity_imagelistview;
    }

    @Override
    protected void findView() {
        imageListView = id(R.id.imagelistview);
    }

    @Override
    protected void initView() {

        imageListView.addItem("http://7xobcr.com2.z0.glb.qiniucdn.com/78496sad789sdfa.jpg");
        imageListView.addItem("http://7xobcr.com2.z0.glb.qiniucdn.com/asdfasdfasdqw.jpg");
        imageListView.addItem("http://7xobcr.com2.z0.glb.qiniucdn.com/fsdafasdfsadfsdwqe.jpg");
        imageListView.addItem("http://7xobcr.com2.z0.glb.qiniucdn.com/897wqe789erwsacdf.jpg");

        //设置是否显示添加按钮
        imageListView.isAdd(R.mipmap.tianjia_bg, true);
        //初始化控件
        imageListView.init(3, false);
        //向列表中添加图片，应该在ImageListView$init()之后使用
        imageListView.addAllItem(mList);
    }

    @Override
    protected void initFirst() {
        super.initFirst();
    }
}
