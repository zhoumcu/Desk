package cn.zhiao.develop.desk.ui;

import cn.zhiao.baselib.base.BaseActivity;
import cn.zhiao.develop.desk.R;

/**
 * author：Administrator on 2017/4/7 14:22
 * company: xxxx
 * email：1032324589@qq.com
 */

public class EditBaseActivity extends BaseActivity{
    @Override
    public void initView() {
        addFragment(R.id.containers,new EditTextFragment());
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.aty_base_edit;
    }
}
