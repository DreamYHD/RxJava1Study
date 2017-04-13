package com.androidlab.rxpack.RxBindingTest;

import android.content.Context;
import android.content.pm.LabeledIntent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidlab.rxpack.R;
import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Haodong on 2017/3/12.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


    private List<String> mStrings = new ArrayList<>();
    private Context mContext;

    public MyAdapter(List<String> strings, Context context) {
        mStrings = strings;
        mContext = context;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.binding_item, null);
        final ViewHolder viewHolder = new ViewHolder(view);
        RxView.clicks(view)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        RxBus.getInstance().post(new UserBean("hello",viewHolder.getAdapterPosition()+""));
                        Log.i("TGA",viewHolder.getAdapterPosition()+"");
                    }
                });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyAdapter.ViewHolder holder, final int position) {

        holder.textView.setText(mStrings.get(position));
    }

    @Override
    public int getItemCount() {
        return mStrings.size() - 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.binding_content)
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
