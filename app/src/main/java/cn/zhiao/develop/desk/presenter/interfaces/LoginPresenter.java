package cn.zhiao.develop.desk.presenter.interfaces;

import android.widget.EditText;

import cn.zhiao.develop.desk.interfaces.view.LoginView;

/**
 * author：Administrator on 2017/3/24 10:09
 * company: xxxx
 * email：1032324589@qq.com
 */

public interface LoginPresenter {
    /**
     * 登录
     * @param username
     * @param password
     */
    public void login(EditText username, EditText password, LoginView loginView);
}
