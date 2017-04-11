package cn.zhiao.develop.desk.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.zhiao.develop.desk.R;
import cn.zhiao.develop.desk.adapter.LeftListAdapter;
import cn.zhiao.develop.desk.adapter.MyDecoration;
import cn.zhiao.develop.desk.adapter.RecyclerListener;
import cn.zhiao.develop.desk.adapter.RightListAdapter;
import cn.zhiao.develop.desk.adapter.TopListAdapter;

/**
 * author：Administrator on 2017/4/7 14:17
 * company: xxxx
 * email：1032324589@qq.com
 */

public class CategoryDialogFragment extends DialogFragment {
    public static final String MY_TAG = "CategoryDialogFragment";
    @Bind(R.id.recycler_left)
    EasyRecyclerView recyclerLeft;
    @Bind(R.id.recycler_right)
    EasyRecyclerView recyclerRight;
    @Bind(R.id.btn_cloce)
    ImageView btnCloce;
    @Bind(R.id.static_text)
    TextView staticText;
    @Bind(R.id.recycler_top)
    EasyRecyclerView recyclerTop;
    @Bind(R.id.content_view)
    LinearLayout contentView;
    private String citys[] = {"不限", "水桶", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州"};
    private LeftListAdapter leftAdapter;
    private RightListAdapter rightAdapter;
    private TopListAdapter topAdapter;
    private onChooseItem listener;
    private ArrayList<String> chooseData = new ArrayList<>();

    private static CategoryDialogFragment getInstance(List<String> chooseData) {
        CategoryDialogFragment mCategoryDialogFragment = new CategoryDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("data", (ArrayList<String>) chooseData);
        mCategoryDialogFragment.setArguments(bundle);
        return mCategoryDialogFragment;
    }

    public static CategoryDialogFragment showDialog(AppCompatActivity activity, List<String> chooseData) {
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag(MY_TAG);
        if (fragment != null) {
            fragmentTransaction.remove(fragment);
        }
        fragmentTransaction.addToBackStack(null);
        // Create and show the dialog.
        CategoryDialogFragment categoryDialogFragment = CategoryDialogFragment.getInstance(chooseData);
        categoryDialogFragment.show(fragmentTransaction, MY_TAG);
        return categoryDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chooseData = getArguments().getStringArrayList("data");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_categroty, container);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
        initEvent();
        return view;
    }

    private void initEvent() {
        leftAdapter.setOnItemClickListener(new RecyclerListener() {
            @Override
            public void setOnItemClickListener(int position) {
                leftAdapter.setCheckItem(position);
                rightAdapter.update(Arrays.asList(citys));
            }
        });
        rightAdapter.setOnItemClickListener(new RecyclerListener() {
            @Override
            public void setOnItemClickListener(int position) {
                rightAdapter.setCheckItem(position);
                topAdapter.add(citys[position]);
            }
        });
        topAdapter.setOnItemClickListener(new RecyclerListener() {
            @Override
            public void setOnItemClickListener(int position) {
                topAdapter.removeData(position);
            }
        });
    }

    private void initData() {

    }

    private void initView(View view) {
        recyclerLeft.addItemDecoration(new MyDecoration(getContext(), MyDecoration.VERTICAL_LIST));
        recyclerLeft.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerRight.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerTop.setLayoutManager(new GridLayoutManager(getContext(), 5));
        leftAdapter = new LeftListAdapter(getContext(), Arrays.asList(citys));
        recyclerLeft.setAdapter(leftAdapter);
        rightAdapter = new RightListAdapter(getContext(), Arrays.asList(citys));
        recyclerRight.setAdapter(rightAdapter);
        topAdapter = new TopListAdapter(getContext(),chooseData);
        recyclerTop.setAdapter(topAdapter);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.dialog_categroty);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消

        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setWindowAnimations(R.style.DialogBottom);
        window.setAttributes(lp);
        return dialog;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.btn_cloce, R.id.submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cloce:
                if (!isHidden())
                    dismiss();
                break;
            case R.id.submit:
                listener.onItem(topAdapter.getListData());
                if (!isHidden())
                    dismiss();
                break;
        }
    }
    public void setOnChooseItem(onChooseItem listener){
        this.listener = listener;
    }
    public interface onChooseItem{
        void onItem(List<String> listData);
    }
}
