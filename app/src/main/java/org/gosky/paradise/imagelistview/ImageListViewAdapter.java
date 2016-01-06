package org.gosky.paradise.imagelistview;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.gosky.paradise.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author galaxy captain
 * @date 2016/1/4
 */
public class ImageListViewAdapter extends RecyclerView.Adapter<ImageListViewAdapter.ViewHolder> {

    private Context mContext = null;

    private List<String> mUrls = new ArrayList<>();
    private List<String> mList = new ArrayList<>();

    private int mWidth = 0;
    private int margin = 0;

    /**
     * auto layout param
     */
    private FrameLayout.LayoutParams params = null;

    /**
     * max length in line
     */
    private int maxLength = 0;

    /**
     * show more text
     */
    private boolean isMore = false;

    /**
     * show add button
     */
    private boolean isAdd = false;

    /**
     * add button 's image
     */
    private int imgID = View.NO_ID;

    public ImageListViewAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ImageListViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_imagelistview, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ImageListViewAdapter.ViewHolder holder, int position) {

        String path = mList.get(position);

        holder.moreTv.setLayoutParams(params);
        holder.img.setLayoutParams(params);

        if (isMore && !isAdd && mUrls.size() > maxLength && position == mList.size() - 1) {
            holder.moreTv.setVisibility(View.VISIBLE);
        } else {
            holder.moreTv.setVisibility(View.GONE);
        }

        if (isAdd && position == mUrls.size() - 1) {
            holder.img.setImageResource(imgID);//添加按钮
        } else if (path.startsWith("http")) {
            Picasso.with(mContext).load(path).into(holder.img);//网络图片
        } else {
            Uri uri = Uri.fromFile(new File(path));
            Picasso.with(mContext).load(uri).into(holder.img);//SD卡图片
        }

    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    /**
     * 刷新全部图片
     *
     * @param list
     */
    public void refresh(List<String> list) {
        this.mUrls = list;
        this.mList = isMore ? list.size() > maxLength ? list.subList(0, maxLength) : list : list;
        //如果显示添加按钮，在末尾加一个item
        if (isAdd) {
            if (mList.size() == 0) {
                mList.add("");
            } else if (mList.get(mList.size() - 1).length() != 0) {
                mList.add("");
            }
        }
        notifyDataSetChanged();
    }

    public void getPath(int position) {
        this.mUrls.get(position);
    }

    public List<String> getAllPath() {
        return this.mUrls;
    }

    /**
     * 添加或修改图片
     *
     * @param position 添加或修改图片的位置，-1表示在末尾添加
     * @param path     图片路径，例：url，本地路径
     */
    public void addItem(int position, String path) {
        if (this.mUrls.size() == 0) this.mUrls.add(path);
        else if (isAdd && position == -1) this.mUrls.add(mUrls.size() - 1, path);
        else if (position == -1) this.mUrls.add(path);
        else this.mUrls.add(position, path);

        refresh(this.mUrls);
    }

    public void addItem(String path) {
        addItem(-1, path);
    }

    /**
     * 添加或插入图片列表
     *
     * @param position
     * @param list
     */
    public void addAllItem(int position, List<String> list) {
        if (this.mUrls.size() == 0) this.mUrls.addAll(list);
        else if (isAdd && position == -1) this.mUrls.addAll(mUrls.size() - 1, list);
        else if (position == -1) this.mUrls.addAll(list);
        else this.mUrls.addAll(position, list);

        refresh(this.mUrls);
    }

    public void addAllItem(List<String> list) {
        addAllItem(-1, list);
    }

    /**
     * @param width     宽度
     * @param margin    间距
     * @param maxLength 一行的最大数量
     */
    public void setSize(int width, int margin, int maxLength) {
        this.mWidth = width;
        this.margin = margin;
        this.maxLength = maxLength;
        params = new FrameLayout.LayoutParams(mWidth, mWidth);
        params.setMargins(margin, margin, margin, margin);
    }

    public void isAdd(boolean isAdd, int imgID) {
        this.isAdd = isAdd;
        this.imgID = imgID;
    }

    /**
     * 是否可以显示更多
     *
     * @param isMore
     */
    public void setMore(boolean isMore) {
        this.isMore = isMore;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        FrameLayout layout;
        ImageView img;
        TextView moreTv;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = (FrameLayout) itemView.findViewById(R.id.item_imagelistview_layout);
            img = (ImageView) itemView.findViewById(R.id.item_imagelistview_img);
            moreTv = (TextView) itemView.findViewById(R.id.item_imagelistview_more);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();

                    if (position == mUrls.size() - 1 && isAdd) {
                        if (onImageAddClick != null) onImageAddClick.onAddClick();
                    } else {
                        if (onImageItemClick != null) onImageItemClick.onImageItemClick(position);
                    }

                }
            });

        }

    }

    private OnImageItemClick onImageItemClick;

    public void setOnImageItemClick(OnImageItemClick onImageItemClick) {
        this.onImageItemClick = onImageItemClick;
    }

    private OnImageAddClick onImageAddClick;

    public void setOnImageAddClick(OnImageAddClick onImageAddClick) {
        this.onImageAddClick = onImageAddClick;
    }
}
