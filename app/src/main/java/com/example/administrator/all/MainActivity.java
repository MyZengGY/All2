package com.example.administrator.all;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bakerj.infinitecards.AnimationTransformer;
import com.bakerj.infinitecards.CardItem;
import com.bakerj.infinitecards.InfiniteCardView;
import com.bakerj.infinitecards.ZIndexTransformer;
import com.bakerj.infinitecards.transformer.DefaultCommonTransformer;
import com.bakerj.infinitecards.transformer.DefaultTransformerToBack;
import com.bakerj.infinitecards.transformer.DefaultTransformerToFront;
import com.bakerj.infinitecards.transformer.DefaultZIndexTransformerCommon;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private InfiniteCardView mCardView;
    private BaseAdapter mAdapter1, mAdapter2;
    Context context;
    public static Button pre,next,change;
    public static TextView text1;
private static int  xx=1;
    private int[] resId = {R.mipmap.pic1, R.mipmap.pic2, R.mipmap.pic3, R.mipmap
            .pic4, R.mipmap.pic5, R.mipmap.pic6, R.mipmap.pic7};
    private boolean mIsAdapter1 = false;
    private long firstTime = 0;
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {                                         //如果两次按键时间间隔大于2秒，则不退出
                    Toast.makeText(this, "再次点击退出程序", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;//更新firstTime
                    return true;
                } else {                                                    //两次按键小于2秒时，退出应用
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(context==null){
            context=this;
        }
        mCardView = (InfiniteCardView) findViewById(R.id.view);
        text1= (TextView) findViewById(R.id.text_riqi);
        mAdapter1 = new MyAdapter(resId,context);
        mAdapter2 = new MyAdapter(resId,context);
        mCardView.setAdapter(mAdapter1);
        initButton();
    }

    private void initButton() {
        pre= (Button) findViewById(R.id.pre);
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xx=1;
                if (mIsAdapter1) {
                    setStyle2();
                    mCardView.bringCardToFront(mAdapter1.getCount() - 1);
                } else {
                    setStyle1();
                    mCardView.bringCardToFront(mAdapter2.getCount() - 1);
                }
            }
        });
        next= (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xx=1;
                if (mIsAdapter1) {
                    setStyle2();
                } else {
                    setStyle3();
                }
                mCardView.bringCardToFront(1);
            }
        });
        change= (Button) findViewById(R.id.change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xx=1;
                if (mCardView.isAnimating()) {
                    return;
                }
              //  mIsAdapter1 = !mIsAdapter1;
                if (mIsAdapter1) {
                    setStyle2();
                    mCardView.setAdapter(mAdapter1);
                } else {
                    setStyle1();
                    mCardView.setAdapter(mAdapter2);
                }
            }
       });

    }



    private void setStyle1() {
        mCardView.setClickable(true);
        mCardView.setAnimType(InfiniteCardView.ANIM_TYPE_FRONT);
        mCardView.setAnimInterpolator(new LinearInterpolator());
        mCardView.setTransformerToFront(new DefaultTransformerToFront());
        mCardView.setTransformerToBack(new DefaultTransformerToBack());
        mCardView.setZIndexTransformerToBack(new DefaultZIndexTransformerCommon());
    }

    private void setStyle2() {
        mCardView.setClickable(true);
        mCardView.setAnimType(InfiniteCardView.ANIM_TYPE_SWITCH);
        mCardView.setAnimInterpolator(new OvershootInterpolator(-18));
        mCardView.setTransformerToFront(new DefaultTransformerToFront());
        mCardView.setTransformerToBack(new AnimationTransformer() {
            @Override
            public void transformAnimation(View view, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {
                int positionCount = fromPosition - toPosition;
                float scale = (0.8f - 0.1f * fromPosition) + (0.1f * fraction * positionCount);
                ViewHelper.setScaleX(view, scale);
                ViewHelper.setScaleY(view, scale);
                if (fraction < 0.5) {
                    ViewCompat.setRotationX(view, 180 * fraction);
                } else {
                    ViewCompat.setRotationX(view, 180 * (1 - fraction));
                }
            }

            @Override
            public void transformInterpolatedAnimation(View view, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {
                int positionCount = fromPosition - toPosition;
                float scale = (0.8f - 0.1f * fromPosition) + (0.1f * fraction * positionCount);
                ViewHelper.setTranslationY(view, -cardHeight * (0.8f - scale) * 0.5f - cardWidth * (0.02f *
                        fromPosition - 0.02f * fraction * positionCount));
            }
        });
        mCardView.setZIndexTransformerToBack(new ZIndexTransformer() {
            @Override
            public void transformAnimation(CardItem card, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {
                if (fraction < 0.4f) {
                    card.zIndex = 1f + 0.01f * fromPosition;
                } else {
                    card.zIndex = 1f + 0.01f * toPosition;
                }
            }

            @Override
            public void transformInterpolatedAnimation(CardItem card, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {

            }
        });
    }

    private void setStyle3() {
        mCardView.setClickable(false);
        mCardView.setAnimType(InfiniteCardView.ANIM_TYPE_FRONT_TO_LAST);
        mCardView.setAnimInterpolator(new OvershootInterpolator(-8));
        mCardView.setTransformerToFront(new DefaultCommonTransformer());
        mCardView.setTransformerToBack(new AnimationTransformer() {
            @Override
            public void transformAnimation(View view, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {
                int positionCount = fromPosition - toPosition;
                float scale = (0.8f - 0.1f * fromPosition) + (0.1f * fraction * positionCount);
                ViewHelper.setScaleX(view, scale);
                ViewHelper.setScaleY(view, scale);
                if (fraction < 0.5) {
                    ViewCompat.setTranslationX(view, cardWidth * fraction * 1.5f);
                    ViewCompat.setRotationY(view, -45 * fraction);
                } else {
                    ViewCompat.setTranslationX(view, cardWidth * 1.5f * (1f - fraction));
                    ViewCompat.setRotationY(view, -45 * (1 - fraction));
                }
            }

            @Override
            public void transformInterpolatedAnimation(View view, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {
                int positionCount = fromPosition - toPosition;
                float scale = (0.8f - 0.1f * fromPosition) + (0.1f * fraction * positionCount);
                ViewHelper.setTranslationY(view, -cardHeight * (0.8f - scale) * 0.5f - cardWidth * (0.02f *
                        fromPosition - 0.02f * fraction * positionCount));
            }
        });
        mCardView.setZIndexTransformerToBack(new ZIndexTransformer() {
            @Override
            public void transformAnimation(CardItem card, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {
                if (fraction < 0.5f) {
                    card.zIndex = 1f + 0.01f * fromPosition;
                } else {
                    card.zIndex = 1f + 0.01f * toPosition;
                }
            }

            @Override
            public void transformInterpolatedAnimation(CardItem card, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {

            }
        });
    }

    private static class MyAdapter extends BaseAdapter {
        private int[] resIds = {};
        EditText edit_heji;
        private TextView text;
        RecyclerView recyclerView;
        Button button_heji;
        Context context;
        ArrayList<Data_event> list_event_tag=new ArrayList<>();
        ArrayList<Data_event> list_event_dd=new ArrayList<>();
        ArrayList<Data_day> list_tag=new ArrayList<>();
        ArrayList<Data_day> list_dd=new ArrayList<>();

        CustomerAdapter ca=null;
        MyAdapter(int[] resIds,Context context) {
            this.resIds = resIds;
            this.context=context;
        }

        @Override
        public int getCount() {
            return DataString.StringData2();
        }

        @Override
        public Integer getItem(int position) {
            return resIds[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Log.e("MyAdapter", "打断点ition:" + position);
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout
                        .item_card, parent, false);
            init(convertView,position);
            initAdapter();
            button_heji.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(xx==1){
                        button_heji.setText("加条");}
                    if(xx>1){
                        ca.addData(xx-1);
                        button_heji.setText("合计");
                    }
                    initJL(ca);
                    xx=xx+1;
                }
            });
            return convertView;
        }



        private void initAdapter() {
            ca=new CustomerAdapter(xx,context);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(ca);
        }
        private void init(View convertView,int position) {
            convertView.setBackgroundResource(resIds[position]);
            edit_heji= (EditText) convertView.findViewById(R.id.edit_heji);
            button_heji= (Button) convertView.findViewById(R.id.button_heji);
            recyclerView= (RecyclerView) convertView.findViewById(R.id.recyclerView);
            text1.setText(DataString.StringData(position));
            text=(TextView)convertView.findViewById(R.id.text_xq);
            text.setText(DataString.StringData1(position));
        }
        private void initJL(CustomerAdapter ca) {
            Double hj=0.0;
            list_dd.clear();
            list_event_dd.clear();
            for (int i = 0; i < ca.list.size(); i++) {
                for (int i1 = 0; i1 < ca.list.size(); i1++) {
                    if(i==Integer.parseInt(ca.list.get(i1).getTag())){
                        list_tag.add(ca.list.get(i1));
                    }
                }
                if(list_tag.size()!=0){
                    list_dd.add(list_tag.get(list_tag.size()-1));
                }
                list_tag.clear();
            }
            for (int i = 0; i < ca.list_event.size(); i++) {
                for (int i1 = 0; i1 < ca.list_event.size(); i1++) {
                    if(i==Integer.parseInt(ca.list_event.get(i1).getEvent_tag())){
                        list_event_tag.add(ca.list_event.get(i1));
                    }
                }
                if(list_event_tag.size()!=0){
                    list_event_dd.add(list_event_tag.get(list_event_tag.size()-1));
                }
                list_event_tag.clear();
            }
            for (Data_day data_day : list_dd) {
                hj=data_day.getJq()+hj;
            }
            edit_heji.setText(hj+"¥");
        }
    }
}
