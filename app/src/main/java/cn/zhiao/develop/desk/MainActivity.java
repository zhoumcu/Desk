package cn.zhiao.develop.desk;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.zhiao.baselib.base.BaseActivity;
import cn.zhiao.develop.desk.adapter.ConstellationAdapter;
import cn.zhiao.develop.desk.adapter.GoodsAdapter;
import cn.zhiao.develop.desk.adapter.LeftListAdapter;
import cn.zhiao.develop.desk.adapter.ListDropDownAdapter;
import cn.zhiao.develop.desk.adapter.MyDecoration;
import cn.zhiao.develop.desk.adapter.RecyclerListener;
import cn.zhiao.develop.desk.adapter.RightListAdapter;
import cn.zhiao.develop.desk.bean.Goods;
import cn.zhiao.develop.desk.ui.EditBaseActivity;

public class MainActivity extends BaseActivity {

    @Bind(R.id.dropDownMenu)
    DropDownMenu mDropDownMenu;
    @Bind(R.id.ck_check_all)
    CheckBox ckCheckAll;
    //    private GirdDropDownAdapter cityAdapter;
    private ListDropDownAdapter ageAdapter;
    private ListDropDownAdapter sexAdapter;
    private ConstellationAdapter constellationAdapter;

    private String headers[] = {"分类", "价格", "操作员", "重量", "规格"};
    private List<View> popupViews = new ArrayList<>();
    private String citys[] = {"不限", "水桶", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州"};
    private String ages[] = {"不限", "18以下", "18-22", "23-26", "27-35", "35以上"};
    private String sexs[] = {"不限", "张三", "李四"};
    private String constellations[] = {"不限", "1kg以下", "1-3kg", "3-5kg", "5-7kg", "7-10kg", "10kg以上"};
    private String size[] = {"不限", "1mL以下", "1-3mL", "3-5mL", "5-7mL", "7-10mL", "10mL以上"};

    private int constellationPosition = 0;
    private ListDropDownAdapter sizeAdapter;
    private List<Goods> goods = new ArrayList<>();
    private LeftListAdapter leftAdapter;
    private GoodsAdapter adapter;
    private RightListAdapter rightAdapter;

    @OnClick(R.id.fab)
    public void onClick() {
        gt(EditBaseActivity.class);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public void initView() {
        //init city menu
        View categrotyView = getLayoutInflater().inflate(R.layout.item_options, null, false);
        EasyRecyclerView recyclerLeftView = (EasyRecyclerView) categrotyView.findViewById(R.id.recycler_left);
        EasyRecyclerView recyclerRightView = (EasyRecyclerView) categrotyView.findViewById(R.id.recycler_right);
        recyclerLeftView.addItemDecoration(new MyDecoration(getContext(), MyDecoration.VERTICAL_LIST));
        recyclerLeftView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerRightView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        leftAdapter = new LeftListAdapter(getContext(), Arrays.asList(citys));
        recyclerLeftView.setAdapter(leftAdapter);
        rightAdapter = new RightListAdapter(getContext(), Arrays.asList(citys));
        recyclerRightView.setAdapter(rightAdapter);
        //init age menu
        final ListView ageView = new ListView(this);
        ageView.setDividerHeight(0);
        ageAdapter = new ListDropDownAdapter(this, Arrays.asList(ages));
        ageView.setAdapter(ageAdapter);

        //init sex menu
        final ListView sexView = new ListView(this);
        sexView.setDividerHeight(0);
        sexAdapter = new ListDropDownAdapter(this, Arrays.asList(sexs));
        sexView.setAdapter(sexAdapter);

        //init constellation
        final View constellationView = getLayoutInflater().inflate(R.layout.custom_layout, null);
        GridView constellation = ButterKnife.findById(constellationView, R.id.constellation);
        constellationAdapter = new ConstellationAdapter(this, Arrays.asList(constellations));
        constellation.setAdapter(constellationAdapter);
        TextView ok = ButterKnife.findById(constellationView, R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDropDownMenu.setTabText(constellationPosition == 0 ? headers[3] : constellations[constellationPosition]);
                mDropDownMenu.closeMenu();
            }
        });
        //init size menu
        final ListView sizeView = new ListView(this);
        sizeView.setDividerHeight(0);
        sizeAdapter = new ListDropDownAdapter(this, Arrays.asList(size));
        sizeView.setAdapter(sizeAdapter);
        //init popupViews
        popupViews.add(categrotyView);
        popupViews.add(ageView);
        popupViews.add(sexView);
        popupViews.add(sizeView);
        popupViews.add(constellationView);

        //add item click event
//        cityView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                cityAdapter.setCheckItem(position);
//                mDropDownMenu.setTabText(position == 0 ? headers[0] : citys[position]);
//                mDropDownMenu.closeMenu();
//            }
//        });
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
                mDropDownMenu.setTabText(position == 0 ? headers[0] : citys[position]);
                mDropDownMenu.closeMenu();
            }
        });
        ageView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ageAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : ages[position]);
                mDropDownMenu.closeMenu();
            }
        });

        sexView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sexAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[2] : sexs[position]);
                mDropDownMenu.closeMenu();
            }
        });
        sizeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sizeAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[2] : size[position]);
                mDropDownMenu.closeMenu();
            }
        });
        constellation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                constellationAdapter.setCheckItem(position);
                constellationPosition = position;
            }
        });

        //init context view
        EasyRecyclerView contentView = new EasyRecyclerView(this);
        contentView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        goods.add(new Goods());
        goods.add(new Goods());
        goods.add(new Goods());
        goods.add(new Goods());
        adapter = new GoodsAdapter(getContext(), goods);
        contentView.setAdapter(adapter);
        //init dropdownview
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                gt(EditBaseActivity.class);
                //switchLanguage(Locale.SIMPLIFIED_CHINESE,MainActivity.class);
                break;
            case android.R.id.home:
                //toast("home");
                //startActivityWithData(MenuActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //退出activity前关闭菜单
        if (mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu();
        } else {
            super.onBackPressed();
        }
    }

    @OnClick({R.id.ck_check_all, R.id.tv_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ck_check_all:
                if (ckCheckAll.isChecked()){
                    adapter.setAll(false);
                }else {
                    adapter.setAll(true);
                }
                break;
            case R.id.tv_pay:
                break;
        }
    }
}
