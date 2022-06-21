package com.example.owentime.wxapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.owentime.R
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler

/***
 * 微信支付
 */
class WXPayEntryActivity : AppCompatActivity(),IWXAPIEventHandler {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wxpay_entry)
    }

    override fun onReq(p0: BaseReq?) {

    }

    override fun onResp(p0: BaseResp?) {

    }
}