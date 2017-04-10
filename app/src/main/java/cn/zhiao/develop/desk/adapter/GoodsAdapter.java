package cn.zhiao.develop.desk.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.zhiao.develop.desk.R;
import cn.zhiao.develop.desk.bean.Goods;

/**
 * author：Administrator on 2017/4/6 15:44
 * company: xxxx
 * email：1032324589@qq.com
 */

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater mLayoutInflater;
    private List<Goods> mOrder = new ArrayList<>();
    private boolean isAll = true;

    public GoodsAdapter(Context context, List<Goods> mOrder) {
        this.mOrder = mOrder;
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_goods, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (!isAll) {
            holder.ckChooseShoppingCart.setChecked(true);
        }else {
            holder.ckChooseShoppingCart.setChecked(false);
        }
    }
    public void setAll(boolean isAll){
        this.isAll = isAll;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return mOrder == null ? 0 : mOrder.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_goods_picture_shopping_cart)
        ImageView ivGoodsPictureShoppingCart;
        @Bind(R.id.tv_goods_name_shopping_cart)
        TextView tvGoodsNameShoppingCart;
        @Bind(R.id.tv_goods_info_shopping_cart)
        TextView tvGoodsInfoShoppingCart;
        @Bind(R.id.tv_remove_goods)
        TextView tvRemoveGoods;
        @Bind(R.id.ck_choose_shopping_cart)
        CheckBox ckChooseShoppingCart;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
