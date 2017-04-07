package cn.zhiao.develop.desk.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * author：Administrator on 2017/3/13 11:38
 * company: xxxx
 * email：1032324589@qq.com
 */

public class Category {

    /**
     * category : 美食鉴赏
     * categoryId : 0
     */

    private String category;
    private int categoryId;
    private int uiType;

    public static List<Category> arrayCategoryFromData(String str) {

        Type listType = new TypeToken<ArrayList<Category>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getUiType() {
        return uiType;
    }

    public void setUiType(int uiType) {
        this.uiType = uiType;
    }
}
