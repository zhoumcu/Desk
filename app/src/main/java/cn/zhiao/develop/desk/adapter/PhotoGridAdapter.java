package cn.zhiao.develop.desk.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import cn.zhiao.baselib.custom.imageload.GlideImageLoader;
import cn.zhiao.develop.desk.R;

/**
 * author：Administrator on 2017/4/6 14:07
 * company: xxxx
 * email：1032324589@qq.com
 */

public class PhotoGridAdapter extends BaseAdapter{

    private Context context;
    private LayoutInflater inflater;
    private int selectedPosition = -1;
    private boolean shape;
    private ArrayList<String> paths = new ArrayList<>();
    private onClickListener listener;

    public boolean isShape() {
        return shape;
    }

    public void setShape(boolean shape) {
        this.shape = shape;
    }

    public PhotoGridAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    public void addAll(ArrayList<String> paths) {
        this.paths = paths;
        notifyDataSetChanged();
    }
    public int getCount() {
        if(paths.size() == 9){
            return 9;
        }
        return (paths.size() + 1);
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int arg0) {
        return 0;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_published_grida, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.item_grida_image);
            holder.imageClose = (ImageView) convertView.findViewById(R.id.item_close);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position ==paths.size()) {
            holder.imageClose.setVisibility(View.GONE);
            holder.image.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.add_pic));
            if (position == 9) {
                holder.image.setVisibility(View.GONE);
            }
        } else {
            holder.imageClose.setVisibility(View.VISIBLE);
            new GlideImageLoader().loadGridItemView(holder.image, paths.get(position), R.id.item_grida_image, 50, 50);
        }
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position==paths.size()){
                    listener.setOnclickListener(position);
                }else {
                    listener.setOnPreviewclickListener(position);
                }
            }
        });
        holder.imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.setOnCloseclickListener(position);
            }
        });
        return convertView;
    }

    public void upate(ArrayList<String> mPaths) {
        this.paths = mPaths;
        notifyDataSetChanged();
    }

    public class ViewHolder {
        public ImageView image;
        public ImageView imageClose;
    }
    public void setOnclickListener(onClickListener listener){
        this.listener = listener;
    }
    public interface onClickListener{
        public void setOnclickListener(int position);
        public void setOnPreviewclickListener(int position);
        public void setOnCloseclickListener(int position);
    }
}
