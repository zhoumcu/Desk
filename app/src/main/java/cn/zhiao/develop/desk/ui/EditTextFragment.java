package cn.zhiao.develop.desk.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.foamtrace.photopicker.ImageCaptureManager;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.PhotoPreviewActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.zhiao.baselib.base.BaseFragment;
import cn.zhiao.baselib.utils.FileUtil;
import cn.zhiao.baselib.utils.ImageUtils;
import cn.zhiao.develop.desk.R;
import cn.zhiao.develop.desk.adapter.PhotoGridAdapter;
import cn.zhiao.develop.desk.bean.Category;
import cn.zhiao.develop.desk.bean.User;

/**
 * author：Administrator on 2017/4/7 14:24
 * company: xxxx
 * email：1032324589@qq.com
 */
public class EditTextFragment extends BaseFragment implements CategoryDialogFragment.onChooseItem {
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
    @Bind(R.id.share_checkbox)
    AppCompatCheckBox shareCheckbox;

    private boolean isChanged;
    private List<Category> options1Items = new ArrayList<>();
    private String options[] = {"不限", "水桶", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州"};
    private String path;
    private int categoryId;
    private User user;
    private ArrayList<String> imagePaths = new ArrayList<>();
    private ArrayList<String> mPaths = new ArrayList<>();
    private PhotoGridAdapter adapter;
    private EditBaseActivity mCallback;
    private List<String> chooseData = new ArrayList<>();

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            mCallback = (EditBaseActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
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
        for (final String path : paths) {
            if (mPaths.size() <= 9) {
                mPaths.add(ImageUtils.createAndSaveWatermark(FileUtil.getInstance().getLocalBitmap(path), "sdjfksdjf"));
            }
        }
        adapter.addAll(mPaths);
    }

    @Override
    public void initView() {
        adapter = new PhotoGridAdapter(getActivity());
        grid.setAdapter(adapter);
        adapter.setOnclickListener(new PhotoGridAdapter.onClickListener() {
            @Override
            public void setOnclickListener(int position) {
                PhotoPickerIntent intent = new PhotoPickerIntent(getActivity());
                intent.setSelectModel(SelectModel.MULTI);
                intent.setShowCarema(true); // 是否显示拍照， 默认false
                intent.setMaxTotal(9); // 最多选择照片数量，默认为9
                intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
                // intent.setImageConfig(config);
                startActivityForResult(intent, REQUEST_CAMERA_CODE);
            }

            @Override
            public void setOnPreviewclickListener(int position) {
                Intent intent = new Intent(getActivity(), PhotoPreviewActivity.class);
                intent.putExtra(PhotoPreviewActivity.EXTRA_PHOTOS, mPaths);
                intent.putExtra(PhotoPreviewActivity.EXTRA_CURRENT_ITEM, position);
                startActivity(intent);
            }

            @Override
            public void setOnCloseclickListener(int position) {
                mPaths.remove(position);
                adapter.upate(mPaths);
            }
        });
        edCost.addTextChangedListener(textWatcher);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public int getLayoutRes() {
        return R.layout.aty_edittext;
    }

    @OnClick({R.id.tv_choces, R.id.share_checkbox})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_choces:
                CategoryDialogFragment fragment = CategoryDialogFragment.showDialog((AppCompatActivity) getActivity(), chooseData);
                fragment.setOnChooseItem(this);
                break;
        }
    }

    public void saveActicle() {
        if (TextUtils.isEmpty(edTitle.getText())) {
            showToast("标题不能为空！");
            return;
        }
        if (TextUtils.isEmpty(edWeight.getText())) {
            showToast("重量不能为空！");
            return;
        }
        if (TextUtils.isEmpty(edSize.getText())) {
            showToast("大小不能为空！");
            return;
        }
        if (TextUtils.isEmpty(edCost.getText())) {
            showToast("成本不能为空！");
            return;
        }
        if (TextUtils.isEmpty(edContact.getText())) {
            showToast("联系方式不能为空！");
            return;
        }
        if (TextUtils.isEmpty(tvChoces.getText())) {
            showToast("类型不能为空！");
            return;
        }
        if (chooseData.size() == 0) {
            showToast("图片不能为空！");
            return;
        }
        if(shareCheckbox.isChecked()){
            shareMultipleImage();
        }
    }

    //分享多张图片
    public void shareMultipleImage() {
        ArrayList<Uri> uriList = new ArrayList<>();
        for (String path : mPaths) {
            uriList.add(Uri.fromFile(new File(path)));
        }
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList);
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

    @Override
    public void onItem(List<String> listData) {
        tvChoces.setText("");
        chooseData = listData;
        for (String str : listData) {
            tvChoces.append(str + ",");
        }
    }
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            edDescriber.append("\n"+s.toString());
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
