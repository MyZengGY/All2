package com.example.administrator.all;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Administrator on 2017/5/26 0026.
 */

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder> {

    private Context mContext;
    private int xx;
    public static ArrayList<Data_day> list=new ArrayList<>();
    public static ArrayList<Data_event> list_event=new ArrayList<>();

    public CustomerAdapter(int xx,Context mContext) {
        this.xx=xx;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext).inflate(
                R.layout.item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,final int position) {

        //添加editText的监听事件
        holder.editText.addTextChangedListener(new TextSwitcher(holder));
        //通过设置tag，防止position紊乱
        holder.editText.setTag(position);

        holder.editText1.addTextChangedListener(new TextSwitcher_event(holder));
        //通过设置tag，防止position紊乱
        holder.editText1.setTag(position);
    }

    @Override
    public int getItemCount() {
        return xx;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        EditText editText,editText1;
        public MyViewHolder(View view)
        {
            super(view);
            editText = (EditText) view.findViewById(R.id.editText);
            editText1= (EditText) view.findViewById(R.id.editText1);
        }
    }
    //自定义EditText的监听类
    class TextSwitcher implements TextWatcher {
        private MyViewHolder mHolder;
        public TextSwitcher(MyViewHolder mHolder) {
            this.mHolder = mHolder;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void afterTextChanged(Editable s) {
            //用户输入完毕后，处理输入数据，回调给主界面处理

          if(s.toString().equals("")){
            }
            else {
            list.add(new Data_day(mHolder.editText.getTag().toString(), Double.parseDouble(s.toString())));
            }
        }
    }
    //自定义EditText的监听类
    class TextSwitcher_event implements TextWatcher {
        private MyViewHolder mHolder;
        public TextSwitcher_event(MyViewHolder mHolder) {
            this.mHolder = mHolder;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void afterTextChanged(Editable s) {
            //用户输入完毕后，处理输入数据，回调给主界面处理
        list_event.add(new Data_event(mHolder.editText1.getTag().toString(), s.toString()));
            }
        }
    public void addData(int position) {
        //保证列表没有数据时，首先添加
        if(xx==0){
        }else {
            //更新列表
            xx+=1;
            notifyItemInserted(position);
            notifyItemRangeChanged(position,xx);
        }
    }

 /*   public void removeData(int position) {
        //保证列表有数据，并且最少有一条
        if(xx<2&&xx!=0){
            xx-=1;
            notifyDataSetChanged();
        }else if(datas.size()==0){//当列表没有数据提示用户，免得造成系统崩溃
            Toast.makeText(context,"搞毛啊，没数据了",Toast.LENGTH_SHORT).show();
        }else{//更新列表
            datas.remove(position);
            notifyDataSetChanged();
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,datas.size());
        }
    }*/

    }


