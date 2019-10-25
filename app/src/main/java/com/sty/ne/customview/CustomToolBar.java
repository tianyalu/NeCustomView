package com.sty.ne.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by tian on 2019/10/25.
 */

public class CustomToolBar extends RelativeLayout{
    private ImageView ivTitleBarLeft;
    private ImageView ivTitleBarRight;
    private TextView tvTitle;
    private int mTextColor = Color.WHITE;
    private String titleName;

    public CustomToolBar(Context context) {
        this(context, null);
    }

    public CustomToolBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomToolBar);
        mTextColor = mTypedArray.getColor(R.styleable.CustomToolBar_title_text_color, Color.WHITE);
        titleName = mTypedArray.getString(R.styleable.CustomToolBar_title_text);
        //获取资源后要及时回收
        mTypedArray.recycle();
        LayoutInflater.from(context).inflate(R.layout.titlebar, this, true);
        ivTitleBarLeft = findViewById(R.id.iv_titlebar_left);
        ivTitleBarRight = findViewById(R.id.iv_titlebar_right);
        tvTitle = findViewById(R.id.tv_titlebar_title);
        //设置标题文字颜色
        tvTitle.setTextColor(mTextColor);
        setTitle(titleName);
    }

    public void setLeftListener(OnClickListener onClickListener) {
        ivTitleBarLeft.setOnClickListener(onClickListener);
    }

    public void setRightListener(OnClickListener onClickListener) {
        ivTitleBarRight.setOnClickListener(onClickListener);
    }

    public void setTitle(String titleName) {
        if(!TextUtils.isEmpty(titleName)) {
            tvTitle.setText(titleName);
        }
    }
}
