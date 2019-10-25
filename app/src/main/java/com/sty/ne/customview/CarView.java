package com.sty.ne.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by tian on 2019/10/25.
 */

public class CarView extends View {
    private Bitmap carBitmap;

    private Path path;
    private PathMeasure pathMeasure; //路径计算
    private float distanceRatio = 0;
    private Paint circlePaint; //画圆圈的画笔
    private Paint carPaint; //画小车的画笔
    private Matrix carMatrix; //针对 car bitmap 图片操作的矩阵

    public CarView(Context context) {
        this(context, null);
    }

    public CarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        carBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_car);
        path = new Path();
        path.addCircle(0, 0, 200, Path.Direction.CW);
        pathMeasure = new PathMeasure(path, false);

        circlePaint = new Paint();
        circlePaint.setStrokeWidth(4);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.BLACK);

        carPaint = new Paint();
        carPaint.setColor(Color.DKGRAY);
        carPaint.setStrokeWidth(2);
        carPaint.setStyle(Paint.Style.STROKE);

        carMatrix = new Matrix();
    }

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
}
