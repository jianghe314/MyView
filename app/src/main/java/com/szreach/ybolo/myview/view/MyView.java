package com.szreach.ybolo.myview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.szreach.ybolo.myview.R;


/**
 * Created by ZX on 2018/9/10
 */
public class MyView extends View {

    private int[] colors={0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000};
    private int mw,mh;
    private int[] angles={0,30,50,70,45,60,50,40,15};
    private int currentAngle=0;
    //钩的当前高度和宽度
    private int currentW=0,currentH=0;
    //钩的实际宽度和高度
    private int rightW,rightH;
    private boolean start=true;

   private Handler handler=new Handler(Looper.getMainLooper()){
       @Override
       public void handleMessage(Message msg) {
            switch (msg.what){
                case 11:
                    if(currentW <= rightW||currentH <= rightH){
                        currentH+=20;
                        currentW+=20;
                        invalidate();
                    }else {
                        start=false;
                    }
                    break;
            }
       }
   };


    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mw=w;
        mh=h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint=new Paint();
        paint.setColor(getResources().getColor(R.color.colorAccent));
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(10f);

        //移动画布
        canvas.translate(200,200);              //相对于原始坐标原点（200，200）
        float r= 100;
        RectF rectF=new RectF(-r,-r,r,r);

        for (int i = 0; i < angles.length; i++) {
            paint.setColor(colors[i]);
            canvas.drawArc(rectF,currentAngle,angles[i],true,paint);
            currentAngle=currentAngle+angles[i];
        }

        //b保存当前内容
       // canvas.save();

        canvas.translate(300,0);                //相对于原始指标原点（500，200）

        Rect rect=new Rect(-100,-100,100,100);
        paint.setStrokeWidth(1f);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        for (int i = 0; i < 25; i++) {
            canvas.drawRect(rect,paint);
            canvas.scale(0.9f,0.9f);
        }

        //canvas.restoreToCount(25);            //画布回滚

        for (int i = 0; i <25 ; i++) {
            canvas.scale(1.1f,1.1f);
        }


        //平移相对坐标
        canvas.translate(400,0);            //相对于原始指标原点（1100，200）
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2f);

        canvas.drawCircle(0,0,120,paint);
        canvas.drawCircle(0,0,100,paint);


        for (int i = 0; i <= 360 ; i+=10) {
            canvas.drawLine(0,-120,0,-100,paint);
            canvas.rotate(10);
        }

        canvas.translate(-700,500);
        canvas.save();

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.YELLOW);
        canvas.drawCircle(0,0,150,paint);

        //画钩
        Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.right);
        rightW=bitmap.getWidth();
        rightH=bitmap.getHeight();
        //指定图片绘制区域
        Rect src=new Rect(0,0,currentW,bitmap.getHeight());
        //指定图片在屏幕上显示的区域
        Rect dst=new Rect(-100,-100,(int)(currentW*0.3),100);
        canvas.drawBitmap(bitmap,src,dst,null);

        //开启动画
        if(start){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    handler.sendEmptyMessageDelayed(11,20);
                }
            }).start();
        }
        canvas.save();

        //移动画布
        canvas.translate(300,-50);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5f);
        paint.setTextSize(50);
        canvas.rotate(-10f);

        canvas.drawText("ABCD",0,0,paint);

        canvas.save();

        canvas.translate(500,0);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2f);

        RectF oval=new RectF(-150,-150,150,150);
        canvas.drawArc(oval,180,180,false,paint);

        paint.setColor(Color.RED);
        paint.setStrokeWidth(10f);
        canvas.drawPoint(0,0,paint);
        canvas.save();

        //绘制刻度
        paint.setStrokeWidth(2f);

        for (int i = 0; i <= 50; i++) {
            //如果能被5整除
            if(i%5==0){
                paint.setColor(Color.RED);
                canvas.drawLine(-150,0,-130,0,paint);
            }else {
                paint.setColor(Color.BLACK);
                canvas.drawLine(-150,0,-140,0,paint);
            }
            //旋转画布
            canvas.rotate(3.6f);

        }
        canvas.save();

        canvas.rotate(-180);

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(2f);
        canvas.translate(-750,500);
        canvas.save();


        paint.setStyle(Paint.Style.FILL);
        Path path1=new Path();
        Path path2=new Path();
        Path path3=new Path();
        Path path4=new Path();
        path1.addCircle(0,0,200, Path.Direction.CW);
        path2.addRect(0,-200,200,200, Path.Direction.CW);
        path3.addCircle(0,-100,100, Path.Direction.CW);
        path4.addCircle(0,100,100, Path.Direction.CW);

        path1.op(path2, Path.Op.DIFFERENCE);        //得到一个大半圆
        path1.op(path3, Path.Op.UNION);             //大半圆加上面的小圆
        path1.op(path4, Path.Op.DIFFERENCE);        //上面的结果减掉一个下面的小半圆

        canvas.drawPath(path1,paint);












    }



}
