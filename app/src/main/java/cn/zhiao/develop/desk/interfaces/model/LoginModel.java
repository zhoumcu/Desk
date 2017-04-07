package cn.zhiao.develop.desk.interfaces.model;

import android.widget.EditText;

import cn.zhiao.baselib.base.BaseResponse;

/**
* Created by Administrator on 2017/04/06
*/

public interface LoginModel{
    public String verfiyUserName(EditText text);
    public String verfiyPassWord(EditText text);
    public void login(String username, String password, BaseResponse callback);
}