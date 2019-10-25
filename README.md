## NeCustomView Android自定义View之自绘控件与组合控件（自定义toolbar）
### 一、自定义控件类型
![image](https://github.com/tianyalu/NeCustomView/blob/master/show/component_type.png)   
### 二、自绘控件  
```android 
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        //移动canvas坐标系到中心
        canvas.translate(width/2, height/2);
        carMatrix.reset();
        distanceRatio += 0.006f;
        if(distanceRatio >= 1) {
            distanceRatio = 0;
        }

        float[] pos = new float[2]; //记录当前点位置坐标
        float[] tan = new float[2]; //记录切点值xy   tan[0]=sinA(x坐标) tan[1]=cosA(y坐标)
        float distance = pathMeasure.getLength() * distanceRatio;
        pathMeasure.getPosTan(distance, pos, tan);
        //tan[0]=sinA(x坐标) tan[1]=cosA(y坐标)
        /**
         * tan(θ) = y / x:
         * θ = ATan(y, x)求出的θ取值范围是[-PI/2, PI/2]。
         * θ = ATan2(y, x)求出的θ取值范围是[-PI, PI]。
         * 当 (x, y) 在第一象限, 0 < θ < PI/2.
         * 当 (x, y) 在第二象限 PI/2 < θ≤PI.
         * 当 (x, y) 在第三象限, -PI < θ < -PI/2.
         * 当 (x, y) 在第四象限, -PI/2 < θ < 0.
         * 当点(x, y)在象限的边界也就是坐标轴上时:
         * 当 y 是 0，x 为非负值, θ = 0.
         * 当 y 是 0， x 是 负值, θ = PI.
         * 当 y 是 正值， x 是 0, θ = PI/2.
         * 当 y 是 负值， x 是 0, θ = -PI/2.
         */
        float degree = (float) ((Math.atan2(tan[1], tan[0])) * 180 / Math.PI);
        //设置旋转角度
        carMatrix.postRotate(degree, carBitmap.getWidth()/2, carBitmap.getHeight()/2);
        //这里要将设置到小车的中心点
        carMatrix.postTranslate(pos[0] - carBitmap.getWidth()/2, pos[1] - carBitmap.getHeight()/2);
        canvas.drawPath(path, circlePaint);
        canvas.drawBitmap(carBitmap, carMatrix, carPaint);
        invalidate();
    }
```
![image](https://github.com/tianyalu/NeCustomView/blob/master/show/get_postan.png)  
   
![image](https://github.com/tianyalu/NeCustomView/blob/master/show/analyse.png)  
   
![image](https://github.com/tianyalu/NeCustomView/blob/master/show/get_segment.png)  
   
### 二、组合控件 
```android 
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
```

### 三、演示示例
![image](https://github.com/tianyalu/NeCustomView/blob/master/show/show.gif)  


