package com.puyue.www.qiaoge.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
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


import com.luck.picture.lib.tools.BitmapUtils;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.home.CommonGoodsDetailActivity;
import com.puyue.www.qiaoge.activity.home.SpecialGoodDetailActivity;
import com.puyue.www.qiaoge.api.cart.OrderPayAPI;
import com.puyue.www.qiaoge.api.mine.GetShareInfoAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.dialog.HelpPayDialog;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.listener.NoDoubleClickListener;
import com.puyue.www.qiaoge.model.HelpPersonInfoModel;
import com.puyue.www.qiaoge.model.PayInfoListModel;
import com.puyue.www.qiaoge.model.ShareInfoModel;
import com.puyue.www.qiaoge.model.ToFriendModel;
import com.puyue.www.qiaoge.model.mine.GetShareInfoModle;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.utils.Utils;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMMin;
import com.umeng.socialize.media.UMWeb;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.internal.Constants;
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
    int orderDeliveryType;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        orderId = getIntent().getStringExtra("orderId");
        orderDeliveryType = getIntent().getIntExtra("orderDeliveryType",0);
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
                .subscribe(new Subscriber<ToFriendModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(ToFriendModel toFriendModel) {
                        if (toFriendModel.code==1) {
                            Intent intent = new Intent(mContext,HelpPayDescActivity.class);
                            intent.putExtra("data", (Serializable) toFriendModel.getData());
                            intent.putExtra("orderDeliveryType", orderDeliveryType);
                            startActivity(intent);
                            finish();
                        } else {
                            AppHelper.showMsg(mContext, toFriendModel.message);
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
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.popwindow_invite1, null);
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

        LinearLayout mLlInviteWeChat = dialogView.findViewById(R.id.ll_invite_dialog_wechat);
        TextView mTvInviteCancel = dialogView.findViewById(R.id.tv_invite_dialog_cancel);
        dialog.show();
        mTvInviteCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        mLlInviteWeChat.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                if (StringHelper.notEmptyAndNull(title)
                        && StringHelper.notEmptyAndNull(path)) {

                    String appId = "wx24c9fe5477c95b47"; // 填应用AppId
                    IWXAPI api = WXAPIFactory.createWXAPI(mActivity, appId);

                    WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
                    miniProgramObj.webpageUrl = "http://www.qq.com"; // 兼容低版本的网页链接
                    miniProgramObj.miniprogramType = 0;// 正式版:0，测试版:1，体验版:2
                    miniProgramObj.userName = "gh_02750c16f80b";     // 小程序原始id
                    miniProgramObj.path = path;            //小程序页面路径
                    WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
                    msg.title = title;                    // 小程序消息title
                    msg.thumbData = getThumb(amount);                      // 小程序消息封面图片，小于128k 这个字节数组不能为空 否则无法调起微信页面 调试的时候可以先随便赋值一个new byte[n]
                    msg.messageExt = "";               // 小程序消息desc

                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                    req.transaction = buildTransaction("webpage");
                    req.message = msg;
                    req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前支持会话
                    api.sendReq(req);

                } else {
                    Toast.makeText(HelpPayActivity.this, "数据不全!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(HelpPayActivity.this, " 收藏成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(HelpPayActivity.this, " 分享成功", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
//            Toast.makeText(MyInviteActivity.this, " 分享失败啦", Toast.LENGTH_SHORT).show();
//            if (t != null) {
//            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
//            Toast.makeText(MyInviteActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

//    private byte[] getThumb(){
//        byte[] thumb;
//        Bitmap bitmap = BitmapFactory.decodeResource(HelpPayActivity.this.getResources(),R.mipmap.bg_help_pay1);
//        Bitmap sendBitmap = Bitmap.createScaledBitmap(bitmap,180,180,true);
//        bitmap.recycle();
//        thumb = Utils.bmpToByteArray(sendBitmap);
//        return thumb;

//    }

    private byte[] getThumb(String amount){
        byte[] thumb;
        Bitmap bitmap = BitmapFactory.decodeResource(HelpPayActivity.this.getResources(),R.mipmap.bg_help_pay1);
        Bitmap sendBitmap = Bitmap.createScaledBitmap(bitmap,180,180,true);
        bitmap.recycle();

        Canvas canvas = new Canvas(sendBitmap);

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(15);
        paint.setAntiAlias(true);

        Paint paint2 = new Paint();
        paint2.setColor(Color.BLACK);
        paint2.setTypeface(Typeface.DEFAULT_BOLD);
        paint2.setAntiAlias(true);
        paint2.setTextSize(15);

        Paint paint1 = new Paint();
        paint1.setColor(Color.BLACK);
        paint1.setTypeface(Typeface.DEFAULT_BOLD);
        paint1.setAntiAlias(true);
        paint1.setTextSize(25);

        //计算得出文字的绘制起始x、y坐标
        int posX = 180/2 - 15*"帮我付".length()/2;
        int posY = 130/2 - 15/2;


        int posX1 = 160/2 - 15*("￥"+amount).length()/2;
        int posY1 = 200/2 - 25/2;

        canvas.drawText("帮我付", posX, posY, paint);
        canvas.drawText("￥"+amount, posX1, posY1, paint1);

        thumb = Utils.bmpToByteArray(sendBitmap);

        return thumb;
    }
    private static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

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
