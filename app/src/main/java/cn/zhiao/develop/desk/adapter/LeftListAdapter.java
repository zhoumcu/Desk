package cn.zhiao.develop.desk.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.zhiao.develop.desk.R;

/**
 * author：Administrator on 2017/4/6 15:44
 * company: xxxx
 * email：1032324589@qq.com
 */

public class LeftListAdapter extends RecyclerView.Adapter<LeftListAdapter.ViewHolder> {


    private Context context;
    private LayoutInflater mLayoutInflater;
    private List<String> mOrder = new ArrayList<>();
    private int checkItemPosition;
    private RecyclerListener listener;

    public LeftListAdapter(Context context, List<String> mOrder) {
        this.mOrder = mOrder;
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setCheckItem(int position) {
        checkItemPosition = position;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_options_left, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        fillValue(position, holder);
        bindEvent(position, holder);
    }

    private void bindEvent(final int position, ViewHolder holder) {
        holder.tvMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.setOnItemClickListener(position);
            }
        });
    }

    private void fillValue(int position, ViewHolder viewHolder) {
        viewHolder.tvMain.setText(mOrder.get(position));
        if (checkItemPosition != -1) {
            if (checkItemPosition == position) {
                viewHolder.tvMain.setTextColor(context.getResources().getColor(R.color.drop_down_selected));
                viewHolder.tvMain.setBackgroundResource(R.color.check_bg);
            } else {
                viewHolder.tvMain.setTextColor(context.getResources().getColor(R.color.drop_down_unselected));
                viewHolder.tvMain.setBackgroundResource(R.color.white);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mOrder == null ? 0 : mOrder.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {

        @Bind(R.id.tv_main)
        TextView tvMain;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public void setOnItemClickListener(RecyclerListener listener){
        this.listener = listener;
    }
}
