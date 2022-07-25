package com.example.time_project.wxapi

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.time_project.*
import com.example.time_project.ui.PaySuccessActivity
import com.jeremyliao.liveeventbus.LiveEventBus
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory

/***
 * 微信支付
 */
class WXPayEntryActivity : AppCompatActivity(),IWXAPIEventHandler {
    private lateinit var wxapi: IWXAPI
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wxpay_entry)
        wxapi = WXAPIFactory.createWXAPI(this, AppConfig.APP_ID)
        wxapi.handleIntent(intent, this)
    }

    override fun onReq(p0: BaseReq?) {

    }

    override fun onResp(baseResp: BaseResp) {
        if (baseResp.errCode == 0) {
//            val intent = Intent()
//            //            intent.setClass(WXPayEntryActivity.this, MainActivity.class);
//            intent.setClass(this@WXPayEntryActivity, PaySuccessActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//            intent.putExtra("id", 0)
//            startActivity(intent)
    LiveEventBus.get<String>("refresh").post("buy")
            start(this@WXPayEntryActivity,PaySuccessActivity().javaClass,false)
        } else if (baseResp.errCode == -2) {
//            LiveEventBus.get("dfk").post(1000)
    toast("未付款")
//            val intent = Intent()
//            intent.putExtra("ordersn", SPUtil.get(SPContans.Order_sn, ""))
//            intent.setClass(this@WXPayEntryActivity, OrderDfkActivity::class.java)
//            startActivity(intent)
        }
    }
}