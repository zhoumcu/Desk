package cn.zhiao.develop.desk.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.foamtrace.photopicker.ImageCaptureManager;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.PhotoPreviewActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.zhiao.baselib.base.BaseActivity;
import cn.zhiao.baselib.utils.FileUtil;
import cn.zhiao.baselib.utils.ImageUtils;
import cn.zhiao.baselib.utils.PixelUtil;
import cn.zhiao.develop.desk.R;
import cn.zhiao.develop.desk.adapter.PhotoGridAdapter;
import cn.zhiao.develop.desk.bean.Category;
import cn.zhiao.develop.desk.bean.User;

/**
 * author：Administrator on 2017/4/6 09:31
 * company: xxxx
 * email：1032324589@qq.com
 */
public class EditTextActivity extends BaseActivity {

    private static final int REQUEST_CAMERA_CODE = 1001;
    private static final int REQUEST_PREVIEW_CODE = 1002;
    @Bind(R.id.ed_title)
    TextInputEditText edTitle;
    @Bind(R.id.ed_describer)
    TextInputEditText edDescriber;
    @Bind(R.id.ed_weight)
    TextInputEditText edWeight;
    @Bind(R.id.ed_size)
    TextInputEditText edSize;
    @Bind(R.id.ed_cost)
    TextInputEditText edCost;
    @Bind(R.id.ed_contact)
    TextInputEditText edContact;
    @Bind(R.id.tv_choces)
    AppCompatCheckedTextView tvChoces;
    @Bind(R.id.grid)
    MyGridView grid;
    private boolean isChanged;
    private List<Category> options1Items = new ArrayList<>();
    private String options[] = {"不限", "水桶", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州"};
    private String path;
    private int categoryId;
    private User user;
    private ArrayList<String> imagePaths = new ArrayList<>();
    private ArrayList<String> mPaths = new ArrayList<>();
    private PhotoGridAdapter adapter;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case REQUEST_CAMERA_CODE:
                    refreshAdpater(data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT));
                    break;
                // 拍照
                case ImageCaptureManager.REQUEST_TAKE_PHOTO:
//                    if(captureManager.getCurrentPhotoPath() != null) {
//                        captureManager.galleryAddPic();
//                        // 照片地址
//                        String imagePaht = captureManager.getCurrentPhotoPath();
//                        // ...
//                    }
                    break;
                // 预览
                case REQUEST_PREVIEW_CODE:
                    refreshAdpater(data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT));
                    break;
            }
        }
    }

    private void refreshAdpater(ArrayList<String> paths) {
        // 处理返回照片地址
        for (final String path : paths){
            if(mPaths.size()<=9){
                Bitmap bm = FileUtil.getInstance().getImage(path, PixelUtil.getWindowWidth(), PixelUtil.getWindowHeight()); //获取限定宽高的bitmap，不限定则容易占用内存过大及OOM
                ImageUtils.addWatermarkBitmap(bm,"testsfsdf",PixelUtil.getWindowWidth(),PixelUtil.getWindowHeight());
                //mPaths.add(ImageUtils.drawTextToRightTop(this,path,"test",16,R.color.red_color,10,10));
            }
        }
        adapter.addAll(mPaths);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info_menu, menu);
        return true;
    }

    @Override
    public void initView() {
        adapter = new PhotoGridAdapter(this);
        grid.setAdapter(adapter);
        adapter.setOnclickListener(new PhotoGridAdapter.onClickListener() {
            @Override
            public void setOnclickListener(int position) {
                PhotoPickerIntent intent = new PhotoPickerIntent(EditTextActivity.this);
                intent.setSelectModel(SelectModel.MULTI);
                intent.setShowCarema(true); // 是否显示拍照， 默认false
                intent.setMaxTotal(9); // 最多选择照片数量，默认为9
                intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
                // intent.setImageConfig(config);
                startActivityForResult(intent, REQUEST_CAMERA_CODE);
            }

            @Override
            public void setOnPreviewclickListener(int position) {
                Intent intent = new Intent(EditTextActivity.this,PhotoPreviewActivity.class);
                intent.putExtra(PhotoPreviewActivity.EXTRA_PHOTOS,mPaths);
                startActivity(intent);
            }

            @Override
            public void setOnCloseclickListener(int position) {
                mPaths.remove(position);
                adapter.upate(mPaths);
            }
        });
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.aty_edittext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                shareMultipleImage();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick({R.id.tv_choces, R.id.share_checkbox})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_choces:
                //条件选择器
                OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
//                        String tx = options1Items.get(options1).getCategory();
//                        tvChoces.setText(tx);
//                        categoryId = options1Items.get(options1).getCategoryId();
                    }
                }).build();
                pvOptions.setPicker(Arrays.asList(options));
                pvOptions.show();
                break;
//            case R.id.img_add:
//                PhotoPickerIntent intent = new PhotoPickerIntent(this);
//                intent.setSelectModel(SelectModel.MULTI);
//                intent.setShowCarema(true); // 是否显示拍照， 默认false
//                intent.setMaxTotal(9); // 最多选择照片数量，默认为9
//                intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
//                // intent.setImageConfig(config);
//                startActivityForResult(intent, REQUEST_CAMERA_CODE);
//                break;
        }
    }
    //分享多张图片
    public void shareMultipleImage() {
        ArrayList<Uri> uriList = new ArrayList<>();
        for (String path :mPaths){
            uriList.add(Uri.fromFile(new File(path)));
        }
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList);
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, "分享到"));
    }
}