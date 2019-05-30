package com.jersey.tools.view;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CustomVerImageView extends AppCompatImageView {
    private int lastX;
    private int lastY;
    private boolean isView = false;
    private int startTop, startBottom, startLeft, startRight;
    private boolean isFirstGet = false;

    public CustomVerImageView(Context context) {
        super(context);
    }

    public CustomVerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomVerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!isFirstGet) {
            startTop = top;
            startBottom = bottom;
            startLeft = left;
            startRight = right;
            isFirstGet = true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                //获取控件在屏幕的位置
                int[] location = new int[2];
                getLocationOnScreen(location);
                //控件相对于屏幕的x与y坐标
                int x = location[0];
                int y = location[1];
                //圆半径 通过左右坐标计算获得getLeft
                int r = (getRight() - getLeft()) / 2;
                //圆心坐标
                int vCenterX = x + r;
                int vCenterY = y + r;
                //点击位置x坐标与圆心的x坐标的距离
                int distanceX = Math.abs(vCenterX - lastX);
                //点击位置y坐标与圆心的y坐标的距离
                int distanceY = Math.abs(vCenterY - lastY);
                //点击位置与圆心的直线距离
                int distanceZ = (int) Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
                //如果点击位置与圆心的距离大于圆的半径，证明点击位置没有在圆内
                if (distanceZ > r) {
                    return false;
                }
                isView = true;
                break;

            case MotionEvent.ACTION_MOVE:
                if (isView) {
                    int moveX = (int) event.getRawX();
                    int moveY = (int) event.getRawY();
                    moveView(this, moveX, moveY);
                }
                break;

            case MotionEvent.ACTION_UP:
                isView = false;
                verMoveIsInVerCircle();
                break;
        }
        return true;
    }


    private void moveView(View v, int moveX, int moveY) {
        int disX = moveX - lastX;
        int disY = moveY - lastY;

        int left = v.getLeft() + disX;
        int right = v.getRight() + disX;
        int top = v.getTop() + disY;
        int bottom = v.getBottom() + disY;
        v.layout(left, top, right, bottom);
        lastX = moveX;
        lastY = moveY;
    }


    private boolean verMoveIsInVerCircle(View moveView, View verView) {
        //获取控件在屏幕的位置
        int[] location = new int[2];
        moveView.getLocationOnScreen(location);
        //控件相对于屏幕的x与y坐标
        int x = location[0];
        int y = location[1];
        //圆半径 通过左右坐标计算获得getLeft
        int r = (moveView.getRight() - moveView.getLeft()) / 2;
        //圆心坐标
        int vCenterX = x + r;
        int vCenterY = y + r;
        return isTouchPointInView(verView, vCenterX, vCenterY);
    }

    private boolean isTouchPointInView(View v, int centX, int centY) {
        if (v == null) {
            return false;
        }
        //获取控件在屏幕的位置
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        //控件相对于屏幕的x与y坐标
        int x = location[0];
        int y = location[1];
        //圆半径 通过左右坐标计算获得getLeft
        int r = (v.getRight() - v.getLeft()) / 2;
        //圆心坐标
        int vCenterX = x + r;
        int vCenterY = y + r;
        if (Math.abs(vCenterX - centX) < 19 && Math.abs(vCenterY - centY) < 19) {
            return true;
        }
        return false;
    }

    private void verMoveIsInVerCircle() {

        if (verMoveIsInVerCircle(this, mVerCircleIv)) {
            if (mOnVerCallback != null) {
                mOnVerCallback.onSuccess();
            }
        } else {
            if (mOnVerCallback != null) {
                mOnVerCallback.onFailure();
            }
        }
        isFirstGet = true;
        layout(startLeft, startTop, startRight, startBottom);

    }


    private OnVerCallback mOnVerCallback;
    private View mVerCircleIv;

    public void setOnVerCallBack(View verCircleView, OnVerCallback onVerCallback) {
        this.mOnVerCallback = onVerCallback;
        this.mVerCircleIv = verCircleView;
    }

    public interface OnVerCallback {
        void onSuccess();

        void onFailure();
    }
}
