package com.puyue.www.qiaoge.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.home.CommonGoodsDetailActivity;
import com.puyue.www.qiaoge.api.cart.OrderPayAPI;
import com.puyue.www.qiaoge.api.mine.GetShareInfoAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.dialog.HelpPayDialog;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.listener.NoDoubleClickListener;
import com.puyue.www.qiaoge.model.HelpPersonInfoModel;
import com.puyue.www.qiaoge.model.PayInfoListModel;
import com.puyue.www.qiaoge.model.ShareInfoModel;
import com.puyue.www.qiaoge.model.mine.GetShareInfoModle;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMMin;
import com.umeng.socialize.media.UMWeb;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HelpPayActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_next)
    TextView tv_next;
    @BindView(R.id.iv_share)
    ImageView iv_share;
    String queryPhone;
    String orderId;
    String receiveUserId;
    String title;
    String path;
    String amount;
    String userName;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        orderId = getIntent().getStringExtra("orderId");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_help_pay);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void setViewData() {

    }

    @Override
    public void setClickEvent() {
        iv_back.setOnClickListener(this);
        tv_next.setOnClickListener(this);
        iv_share.setOnClickListener(this);
    }

    //查询帮付人信息口
    private void getHelpPerson() {
        OrderPayAPI.getHelpPerson(mActivity,queryPhone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HelpPersonInfoModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(HelpPersonInfoModel helpPersonInfoModel) {
                        if (helpPersonInfoModel.getCode()==1) {
                            if(helpPersonInfoModel.getData()!=null) {
                                receiveUserId = helpPersonInfoModel.getData().getReceiveUserId();
                                HelpPayDialog helpPayDialog = new HelpPayDialog(mActivity,helpPersonInfoModel.getData()) {
                                    @Override
                                    public void sure() {
                                        dismiss();
                                        sendOrder();
                                    }
                                };
                                helpPayDialog.show();
                            }
                        } else {
                            AppHelper.showMsg(mContext, helpPersonInfoModel.getMessage());
                        }
                    }
                });
    }


    //发送订单给朋友
    private void sendOrder() {
        OrderPayAPI.sendOrder(mActivity,orderId,receiveUserId)
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
                    public void onNext(BaseModel baseModel) {
                        if (baseModel.code==1) {
                            Intent intent = new Intent(mContext,HelpPayDeliveryDetailActivity.class);
                            intent.putExtra("orderId",orderId);
                            startActivity(intent);
                            finish();
                        } else {
                            AppHelper.showMsg(mContext, baseModel.message);
                        }
                    }
                });
    }


    // 获取分享内容
    private void getShareInfo() {
        GetShareInfoAPI.getShareInfo(mContext,orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ShareInfoModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ShareInfoModel shareInfoModel) {
                        if(shareInfoModel.getCode() == 1) {
                            if(shareInfoModel.getData()!=null) {
                                ShareInfoModel.DataBean data = shareInfoModel.getData();
                                title = data.getTitle();
                                amount = data.getAmount();
                                path = data.getPath();
                                userName = data.getUserName();
                                showShareDialog();
                            }
                        }
                    }
                });
    }

    private void showShareDialog() {
        final Dialog dialog = new Dialog(mContext, R.style.Theme_Light_Dialog);
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.popwindow_invite, null);
        //获得dialog的window窗口
        Window window = dialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        dialog.setContentView(dialogView);
        LinearLayout mLlInviteQQ = dialogView.findViewById(R.id.ll_invite_dialog_qq);
        LinearLayout mLlInviteWxCircle = dialogView.findViewById(R.id.ll_invite_dialog_wxcircle);
        LinearLayout mLlInviteWeChat = dialogView.findViewById(R.id.ll_invite_dialog_wechat);
        TextView mTvInviteText = dialogView.findViewById(R.id.tv_invite_dialog_text);
        TextView mTvInviteCancel = dialogView.findViewById(R.id.tv_invite_dialog_cancel);
        dialog.show();


        mLlInviteWeChat.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                if (StringHelper.notEmptyAndNull(title)
                        && StringHelper.notEmptyAndNull(path)) {

                    UMMin umMin =new UMMin("pages/index/index");
//兼容低版本的网页链接

// 小程序消息封面图片
                    umMin.setTitle(title);
// 小程序消息title
                    umMin.setDescription(amount);
                    umMin.setPath(path);
//小程序页面路径
                    umMin.setUserName(userName);
// 小程序原始id,在微信平台查询
                    new ShareAction(HelpPayActivity.this)
                            .withMedia(umMin)
                            .setPlatform(SHARE_MEDIA.WEIXIN)
                            .setCallback(umShareListener).share();

//                    UMWeb umWeb = new UMWeb(path);
//                    umWeb.setDescription(amount);
//
//                    umWeb.setTitle(title);
//                    new ShareAction(HelpPayActivity.this)
//                            .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
//                            .withMedia(umWeb)//分享内容
//                            .setCallback(umShareListener)//回调监听器
//                            .share();

                } else {
//                    Toast.makeText(CommonGoodsDetailActivity.this, "数据不全!", Toast.LENGTH_SHORT).show();
                }

                dialog.dismiss();
            }
        });

    }

    /**
     * 分享回调
     */
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                ToastUtil.showSuccessMsg(mContext,"收藏成功");
            } else {
                ToastUtil.showSuccessMsg(mContext,"分享成功");

            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {

        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {

        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_next:
                if(TextUtils.isEmpty(et_phone.getText().toString().trim())) {
                    ToastUtil.showSuccessMsg(mContext,"请填写号码");
                    return;
                }
                queryPhone = et_phone.getText().toString().trim();
                getHelpPerson();
                break;

            case R.id.iv_share:
                getShareInfo();
                break;
        }
    }
}
