package cn.zhiao.develop.desk.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import cn.zhiao.baselib.base.BaseActivity;
import cn.zhiao.develop.desk.R;

/**
 * author：Administrator on 2017/4/7 14:22
 * company: xxxx
 * email：1032324589@qq.com
 */

public class EditBaseActivity extends BaseActivity {

    private EditTextFragment editFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public void initView() {
        editFragment = new EditTextFragment();
        addFragment(R.id.containers, editFragment);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.aty_base_edit;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                editFragment.shareMultipleImage();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
