package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class XieShangDialog extends Dialog implements View.OnClickListener {
    public Unbinder binder;
    Context context;
    View view;
    @BindView(R.id.iv_close)
    ImageView iv_close;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_desc)
    TextView tv_desc;
    int type;
    public XieShangDialog(Context context,int type) {
        super(context, R.style.dialog);
        this.context = context;
        this.type = type;
        init();
    }


    public void init() {
        view = View.inflate(context, R.layout.dialog_xie, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        binder = ButterKnife.bind(this, view);
        setContentView(view);
        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = Utils.getScreenWidth(context);
        getWindow().setAttributes(attributes);
        iv_close.setOnClickListener(this);

        if(type==1) {
            tv_title.setText("费用协商说明");
            tv_desc.setText("费用协商：发起协商后，账单费用将冻结，在发起协商后，账单费用将冻结，在协商中的费用无法发起支付；进入协商后，司机将主动联系用户进行费用沟通，用户也可以主动联系司机进行沟通；沟通成功后，司机按协商金额修改费用，可重新进行支付");
        }else {
            tv_title.setText("账单协商说明");
            tv_desc.setText("账单申诉：发起费用申诉，账单费用将冻结，发起费用申诉，账单费用将冻结，在申诉中的费用无法发起支付；申诉成功后，客服将作为中间人，针对费用金额进行协商，直至用户司机双方对费用达成一致，客服将手动将费用修改为最终协商金额，可重新进行支付");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                dismiss();
                break;

            default:
                break;

        }
    }

}
