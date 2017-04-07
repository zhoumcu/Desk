package cn.zhiao.develop.desk.bean;

/**
 * author：Administrator on 2017/3/17 15:30
 * company: xxxx
 * email：1032324589@qq.com
 */

public class User {

    private String username;
    private String password;
    private String phone;
    private String imageUrl;
    private boolean edit;

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
