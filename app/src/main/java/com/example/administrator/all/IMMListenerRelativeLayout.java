package com.example.administrator.all;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;


import static com.example.administrator.all.MainActivity.pre;
import static com.example.administrator.all.MainActivity.next;
import static com.example.administrator.all.MainActivity.change;
import static com.example.administrator.all.MainActivity.text1;

/**
 * Created by Administrator on 2017/5/25 0025.
 */

public class IMMListenerRelativeLayout extends FrameLayout {
    public InputWindowListener listener=new InputWindowListener() {
        @Override
        public void show() {
            pre.setVisibility(View.INVISIBLE);
            next.setVisibility(View.INVISIBLE);
            change.setVisibility(View.INVISIBLE);
            text1.setVisibility(View.INVISIBLE);
        }
        @Override
        public void hidden() {
            pre.setVisibility(View.VISIBLE);
            next.setVisibility(View.VISIBLE);
            change.setVisibility(View.VISIBLE);
            text1.setVisibility(View.VISIBLE);
        }
    };
    public IMMListenerRelativeLayout(Context context) {
        super(context);
    }
    public IMMListenerRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IMMListenerRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
            if (oldh > h) {
                listener.show();
            } else{
                listener.hidden();
            }
    }
}
