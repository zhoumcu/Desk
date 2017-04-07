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

public class TopListAdapter extends RecyclerView.Adapter<TopListAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater mLayoutInflater;
    private List<String> mOrder = new ArrayList<>();
    private RecyclerListener listener;
    private int checkItemPosition;

    public TopListAdapter(Context context) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public TopListAdapter(Context context, List<String> mOrder) {
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
        View view = mLayoutInflater.inflate(R.layout.item_options_top, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        fillValue(position, holder);
        bindEvent(position, holder);
    }
    private void bindEvent(final int position, TopListAdapter.ViewHolder holder) {
        holder.tvMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.setOnItemClickListener(position);
            }
        });
    }
    private void fillValue(int position, TopListAdapter.ViewHolder viewHolder) {
        viewHolder.tvMain.setText(mOrder.get(position));
        viewHolder.tvMain.setTextColor(context.getResources().getColor(R.color.drop_down_selected));
        viewHolder.tvMain.setBackgroundResource(R.color.check_bg);
    }

    @Override
    public int getItemCount() {
        return mOrder == null ? 0 : mOrder.size();
    }

    public void add(String city) {
        if(!isExit(city)){
            mOrder.add(city);
            notifyDataSetChanged();
        }
    }

    private boolean isExit(String city) {
        for (String str : mOrder){
            if(city.equals(str)){
                return true;
            }
        }
        return false;
    }

    public void removeData(int position) {
        mOrder.remove(position);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_main)
        TextView tvMain;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemClickListener(RecyclerListener listener) {
        this.listener = listener;
    }
}
