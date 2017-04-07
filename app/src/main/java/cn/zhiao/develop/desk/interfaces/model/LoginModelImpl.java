package cn.zhiao.develop.desk.interfaces.model;


import android.widget.EditText;

import cn.zhiao.baselib.base.BaseResponse;
import cn.zhiao.develop.desk.bean.User;

/**
* Created by Administrator on 2017/04/06
*/

public class LoginModelImpl implements LoginModel{
    @Override
    public String verfiyUserName(EditText text) {
        if(text.getText().toString().isEmpty()){
            return null;
        }

        return text.getText().toString();
    }

    @Override
    public String verfiyPassWord(EditText text) {
        if(text.getText().toString().isEmpty()){
            return null;
        }
        return text.getText().toString();
    }

    @Override
    public void login(final String username, final String password, final BaseResponse callback) {
        final User user = new User();
        user.setPassword(password);
        user.setUsername(username);
    }
}