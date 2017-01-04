package com.text.dukang.selfview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.text.dukang.selfview.R;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @Author : wdk
 * @Email :a15939582085@126.com
 * Created on : 2017/1/3 10:11
 * @Description :
 */

public class CustomeView extends TextView {

    private String titleText;
    private int titleTextColor;
    private int titleTextSize;
    private int titleBgColor;

    private Paint paint;
    private Rect rect;

    public CustomeView(Context context) {
        this(context, null);
    }

    public CustomeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomeView, defStyleAttr, 0);

        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            /**
             * 获取属性的名称
             */
            int attrName = typedArray.getIndex(i);
            switch (attrName) {
                case R.styleable.CustomeView_titleText:
                    titleText = typedArray.getString(attrName);
                    break;
                case R.styleable.CustomeView_titleTextColor:
                    titleTextColor = typedArray.getColor(attrName, Color.RED);
                    break;
                case R.styleable.CustomeView_titleTextSize:
                    titleTextSize = typedArray.getDimensionPixelSize(attrName, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomeView_titleBgColor:
                    titleBgColor = typedArray.getColor(attrName, Color.WHITE);
                    break;

            }
        }

        typedArray.recycle();

        paint = new Paint();
        paint.setTextSize(titleTextSize);
        rect = new Rect();
        paint.getTextBounds(titleText, 0, titleText.length(), rect);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                titleText = getRandomNum();
                postInvalidate();
            }
        });
    }

    private String getRandomNum() {

        Random random = new Random();
        Set<Integer> set = new HashSet<Integer>();
        for (int i = 0; i < 4; i++) {
            int number = random.nextInt(10);
            set.add(number);
        }

        StringBuffer stringBuffer = new StringBuffer();
        for (Integer i : set) {
            stringBuffer.append(i);
        }

        return stringBuffer.toString();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        /**
         * EXACTLY：一般是设置了明确的值或者是MATCH_PARENT
         * AT_MOST：表示子布局限制在一个最大值内，一般为WARP_CONTENT
         * UNSPECIFIED：表示子布局想要多大就多大，很少使用
         *
         */
        int widthModle = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightModle = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        if (widthModle == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = getPaddingLeft() + rect.width() + getPaddingRight();
        }

        if (heightModle == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = getPaddingTop() + rect.height() + getPaddingBottom();
        }

        setMeasuredDimension(width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);

        paint.setColor(Color.GREEN);
        canvas.drawText(titleText, getWidth() / 2 - rect.width() / 2, getHeight() / 2 + rect.height() / 2, paint);
    }
}
