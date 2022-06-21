package com.example.time_project.ui

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.time_project.*
import com.example.time_project.base.BasePopWindow
import com.example.time_project.databinding.ActivitySplashBinding
import com.example.time_project.util.NoDoubleClickListener
import com.gyf.immersionbar.ktx.immersionBar

import com.hjq.shape.view.ShapeButton
import com.tencent.mmkv.MMKV
import com.umeng.commonsdk.UMConfigure
import razerdp.basepopup.BasePopupWindow

class SplashActivity : AppCompatActivity() {
    private lateinit var timer:CountDownTimer
    private val TIME=3000L
    private val STEP=1000L
    private val mBinding by viewBinding(ActivitySplashBinding::bind)
    private val mmkv=MMKV.defaultMMKV()
    private lateinit var privacy_url:String
    private lateinit var server_url:String
    private lateinit var children_url:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_splash)
        immersionBar {
            statusBarColor(R.color.white)
        }
        timer= object : CountDownTimer(TIME,STEP) {
            override fun onTick(p0: Long) {
                mBinding.skip.text=(p0/1000).toString().plus("s")
            }

            override fun onFinish() {
                val show=mmkv.decodeBool("splashDialog",false)
                when(show){
                    true->{
                        gotoHome()
                    }else->{
                        showDialog()
                    }
                }
            }
        }
        timer.start()
    }

    fun showDialog(){
        val popupWindow: BasePopupWindow = BasePopWindow(this)
        val view = popupWindow.createPopupById(R.layout.layout_splash_dialog)
        initXieyi(view.findViewById<TextView>(R.id.xieyi_content))
        (view.findViewById<View>(R.id.btn_splash_no) as ShapeButton).setOnClickListener(object :
            NoDoubleClickListener() {
            protected override fun onNoDoubleClick(v: View) {
                popupWindow.dismiss()
                System.exit(0)

            }

        })
        (view.findViewById<View>(R.id.btn_splash_yes) as ShapeButton).setOnClickListener(object :
            NoDoubleClickListener() {
            protected override fun onNoDoubleClick(v: View) {
                popupWindow.dismiss()
                mmkv.encode("splashDialog",true)
//                SPUtil.put("splashDialog", true)
//                //按照合规政策要求，确保App首次冷启动时，在用户阅读您的《隐私政策》并取得用户授权之后，才调用正式初始化函数UMConfigure.init()
                UMConfigure.submitPolicyGrantResult(applicationContext, true)
                val umInitConfig = UmInitConfig()
                umInitConfig.UMinit(applicationContext)
                gotoHome()
            }
        })
        popupWindow.contentView = view
        popupWindow.setBackPressEnable(false)
        popupWindow.popupGravity = Gravity.CENTER
        popupWindow.isOutSideTouchable = false
        popupWindow.setOutSideDismiss(false)
        popupWindow.showPopupWindow()

    }

    private fun initXieyi(textView: TextView) {
        server_url = if (!TextUtils.isEmpty(AppConfig.SERVICE_AGREEMENT_URL)) {
            AppConfig.SERVICE_AGREEMENT_URL
        } else {
            "https://www.owentime.cn/api/dict/getUserAgreement"
        }
        privacy_url = if (!TextUtils.isEmpty(AppConfig.PRIVACY_AGREEMENT_URL)) {
            AppConfig.PRIVACY_AGREEMENT_URL
        } else {
            "https://www.owentime.cn/api/dict/getPrivacy"
        }
        children_url = if (!TextUtils.isEmpty(AppConfig.CHILDREN_AGREEMENT_URL)) {
            AppConfig.CHILDREN_AGREEMENT_URL
        } else {
            "https://www.owentime.cn/api/dict/getChildrenPrivacy"
        }
        val beforeStr0 = "我们将依据"
        val style0 = SpannableStringBuilder()
        style0.append(beforeStr0)
        val clickableSpan1: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val keys = arrayOf("title", "url")
                val values = arrayOf("用户服务协议", server_url)
//                gotoWeb(keys, values)
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.color = ds.linkColor
                /**Remove the underline */
                ds.isUnderlineText = false
            }
        }
        val beforeStr1 = "《“欧文时光”用户使用协议》、"
        val style1 = SpannableStringBuilder()
        style1.append(beforeStr1)
        style1.setSpan(clickableSpan1, 0, beforeStr1.length - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)


        //设置部分文字颜色
        val foregroundColorSpan1 = ForegroundColorSpan(resources.getColor(R.color.FE9520))
        style1.setSpan(
            foregroundColorSpan1,
            0,
            beforeStr1.length - 1,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        val clickableSpan2: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val keys = arrayOf("title", "url")
                val values = arrayOf("隐私保护政策", privacy_url)
//                gotoWeb(keys, values)
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.color = ds.linkColor
                /**Remove the underline */
                ds.isUnderlineText = false
            }
        }
        val beforeStr2 = "《“欧文时光”隐私保护政策》、"
        val style2 = SpannableStringBuilder()
        style2.append(beforeStr2)
        style2.setSpan(clickableSpan2, 0, beforeStr2.length - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)


        //设置部分文字颜色
        val foregroundColorSpan2 = ForegroundColorSpan(resources.getColor(R.color.FE9520))
        style2.setSpan(
            foregroundColorSpan2,
            0,
            beforeStr2.length - 1,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        val clickableSpan3: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val keys = arrayOf("title", "url")
                val values = arrayOf("儿童隐私政策", children_url)
//                gotoWeb(keys, values)
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.color = ds.linkColor
                /**Remove the underline */
                ds.isUnderlineText = false
            }
        }
        val beforeStr3 = "《“欧文时光”儿童隐私政策》"
        val style3 = SpannableStringBuilder()
        style3.append(beforeStr3)
        style3.setSpan(clickableSpan3, 0, beforeStr3.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)


        //设置部分文字颜色
        val foregroundColorSpan3 = ForegroundColorSpan(resources.getColor(R.color.FE9520))
        style3.setSpan(
            foregroundColorSpan3,
            0,
            beforeStr3.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        val beforeStr4 = "来帮助您了解我们为您提供的服务，及处理您的个人信息的情况。"
        val style4 = SpannableStringBuilder()
        style4.append(beforeStr4)
        textView.highlightColor = resources.getColor(R.color.transparent, theme)
        //设置光标如何移动计量的方法
        textView.movementMethod = LinkMovementMethod.getInstance()
        //配置给TextView
        textView.text = style0.append(style1).append(style2).append(style3).append(style4)
    }
    fun gotoHome(){
        start(this,MainActivity().javaClass,true)
    }
}