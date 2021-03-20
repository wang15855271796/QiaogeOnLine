package com.puyue.www.qiaoge.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.RoundImageView;
import com.puyue.www.qiaoge.activity.IssueEditInfoActivity;
import com.puyue.www.qiaoge.activity.IssueInfoActivity;
import com.puyue.www.qiaoge.activity.mine.IssueActivity;
import com.puyue.www.qiaoge.activity.mine.ShopDetailActivity;
import com.puyue.www.qiaoge.api.home.InfoListAPI;
import com.puyue.www.qiaoge.api.home.InfoListModel;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.dialog.LookReasonDialog;
import com.puyue.www.qiaoge.event.DeletedShopEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.StringSpecialHelper;
import com.puyue.www.qiaoge.listener.NoDoubleClickListener;
import com.puyue.www.qiaoge.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/1/4
 */
public class MyIssueAdapter extends BaseQuickAdapter<InfoListModel.DataBean.ListBean,BaseViewHolder> {

    public MyIssueAdapter(int layoutResId, @Nullable List<InfoListModel.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InfoListModel.DataBean.ListBean item) {
        RoundImageView iv_pic = helper.getView(R.id.iv_pic);
        TextView tv_look = helper.getView(R.id.tv_look);
        ImageView iv_status1 = helper.getView(R.id.iv_status1);
        TextView tv_status1 = helper.getView(R.id.tv_status1);
        tv_look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,ShopDetailActivity.class);
                intent.putExtra("msgId",item.getMsgId());
                mContext.startActivity(intent);
            }
        });

        if(item.getPictureList().size()>0) {
            for (int i = 0; i < item.getPictureList().size(); i++) {
                Glide.with(mContext).load(item.getPictureList().get(i)).into(iv_pic);
            }
        }else {
            iv_pic.setImageResource(R.mipmap.bg_emptys);
        }

        ImageView iv_status = helper.getView(R.id.iv_status);
        TextView tv_deleted = helper.getView(R.id.tv_deleted);
        if(item.getMsgType().equals("1")) {
            helper.setText(R.id.tv_title,"店铺转让");
        }else if(item.getMsgType().equals("2")) {
            helper.setText(R.id.tv_title,"器具转让");
        }else if(item.getMsgType().equals("3")) {
            helper.setText(R.id.tv_title,"厨师招聘");
        }else if(item.getMsgType().equals("4")) {
            helper.setText(R.id.tv_title,"其他信息");
        }

        helper.setText(R.id.tv_desc,item.getContent());
        helper.setText(R.id.tv_time,item.getCreateTime());
        helper.setText(R.id.tv_num,item.getBrowseNum()+"人看过");
        TextView tv_status = helper.getView(R.id.tv_status);
        TextView tv_edit = helper.getView(R.id.tv_edit);
        if(item.getCheckStatus().equals("1")) {
            //已审核
            tv_status1.setVisibility(View.GONE);
            tv_status.setVisibility(View.VISIBLE);
            iv_status1.setVisibility(View.GONE);
            iv_status.setVisibility(View.VISIBLE);
            tv_status.setText("审核信息已通过");
            iv_status.setBackgroundResource(R.mipmap.icon_dui);
            tv_edit.setVisibility(View.GONE);
            tv_look.setVisibility(View.VISIBLE);
        }else if(item.getCheckStatus().equals("0")) {
            //待审核
            tv_status1.setVisibility(View.GONE);
            tv_status.setVisibility(View.VISIBLE);
            iv_status1.setVisibility(View.GONE);
            iv_status.setVisibility(View.VISIBLE);
            tv_status.setText("审核中…");
            iv_status.setBackgroundResource(R.mipmap.icon_shengnue);
            tv_edit.setVisibility(View.VISIBLE);
            tv_look.setVisibility(View.GONE);

        }else if(item.getCheckStatus().equals("2")){
            tv_status1.setVisibility(View.VISIBLE);
            tv_status.setVisibility(View.GONE);
            iv_status1.setVisibility(View.VISIBLE);
            iv_status.setVisibility(View.GONE);
            SpannableStringBuilder text = StringSpecialHelper.buildSpanColorStyle("审核未通过查看原因", 5, 4, Color.parseColor("#FF5C00"));
            tv_status1.setText(text);
            iv_status1.setBackgroundResource(R.mipmap.icon_gantan);
            tv_edit.setVisibility(View.VISIBLE);
            tv_look.setVisibility(View.GONE);

            tv_status1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showReason(item);
                }
            });
        }
        tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,IssueEditInfoActivity.class);
                intent.putExtra("msgId",item.getMsgId());
                intent.putExtra("msgType",item.getMsgType());
                mContext.startActivity(intent);
            }
        });
        tv_deleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteCollection(item);
            }
        });
    }

    private void showReason(InfoListModel.DataBean.ListBean item) {
        final AlertDialog alertDialog = new AlertDialog.Builder(mContext, R.style.DialogStyle).create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();

        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.delete_reason);
        TextView tv_reason = (TextView) window.findViewById(R.id.tv_reason);
        TextView tv_sure = (TextView) window.findViewById(R.id.tv_sure);
        tv_reason.setText(item.getRefuseReason());

        tv_sure.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                alertDialog.dismiss();
            }
        });


    }

    private void showDeleteCollection(InfoListModel.DataBean.ListBean item) {
        final AlertDialog alertDialog = new AlertDialog.Builder(mContext, R.style.DialogStyle).create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog_delete_shop);
        TextView mTvCancel = (TextView) window.findViewById(R.id.tv_cancel);
        TextView mTvConfirm = (TextView) window.findViewById(R.id.tv_deleted);
        mTvCancel.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                alertDialog.dismiss();
            }
        });
        mTvConfirm.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                getCityList(item);
                alertDialog.dismiss();

            }
        });
    }

    private void getCityList(InfoListModel.DataBean.ListBean item) {
        InfoListAPI.InfoDeleted(mContext,item.getMsgId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(BaseModel infoListModel) {
                        if (infoListModel.success) {
                            ToastUtil.showSuccessMsg(mContext,infoListModel.message);
                            EventBus.getDefault().post(new DeletedShopEvent());
                        } else {
                            AppHelper.showMsg(mContext, infoListModel.message);
                        }
                    }
                });
    }

}
