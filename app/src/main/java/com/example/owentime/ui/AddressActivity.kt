package com.example.owentime.ui

import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.owentime.R
import com.example.owentime.base.BaseActivity
import com.example.owentime.bean.AddressRequestBody
import com.example.owentime.bean.ChangeAddressRequestBody
import com.example.owentime.checked
import com.example.owentime.databinding.ActivityAddressBinding
import com.example.owentime.databinding.ActivityUpOrderBinding
import com.example.owentime.fastClick
import com.example.owentime.toast
import com.example.owentime.ui.UpOrderActivity.IntentOptions.iaddress
import com.example.owentime.ui.UpOrderActivity.IntentOptions.iarea
import com.example.owentime.ui.UpOrderActivity.IntentOptions.icity
import com.example.owentime.ui.UpOrderActivity.IntentOptions.iflag
import com.example.owentime.ui.UpOrderActivity.IntentOptions.iid
import com.example.owentime.ui.UpOrderActivity.IntentOptions.iname
import com.example.owentime.ui.UpOrderActivity.IntentOptions.iphone
import com.example.owentime.ui.UpOrderActivity.IntentOptions.iprovince
import com.example.owentime.vm.OwenViewModel
import com.gyf.immersionbar.ktx.immersionBar
import com.lxj.xpopup.XPopup
import com.lxj.xpopupext.listener.CityPickerListener
import com.lxj.xpopupext.popup.CityPickerPopup
import java.util.HashMap


class AddressActivity : BaseActivity(R.layout.activity_address) {

    private val mBinding by viewBinding(ActivityAddressBinding::bind)

    private val viewModel by viewModels<OwenViewModel>()

    private var mProvince: String = ""//省份
    private var mCity: String = ""//城市
    private var mArea: String = ""//区县

    override fun initData() {
        immersionBar {
            statusBarColor(R.color.white)
            keyboardEnable(true)
            statusBarDarkFont(true)
            fitsSystemWindows(true)
        }
        mBinding.edtAddressCity.setOnClickListener {
            showCity()
        }
        mBinding.btnSaveAddress.fastClick {
            val name: String = mBinding.edtAddressName.checked("请填写收货人") ?: return@fastClick
            val phone: String = mBinding.edtAddressPhone.checked("请填写手机号") ?: return@fastClick
            val address: String = mBinding.edtAddressCity.checked("请选择所在地区") ?: return@fastClick
            val detail: String = mBinding.edtAddressDetail.checked("请填写详细地址") ?: return@fastClick

            when (intent.iflag) {
                "change" -> {
                    val body =
                        ChangeAddressRequestBody(
                            name,
                            phone,
                            mProvince,
                            mCity,
                            mArea,
                            detail,
                            1.toString(),
                            intent.iid.toString(),
                            ""
                        )
                    viewModel.changeAddress(body.toMap()).observe(this, Observer {
                      it?.run {
                          toast(message.toString())
                          finish()
                      }
                    })
                }
                else -> {
                    val body = AddressRequestBody(
                        name,
                        phone,
                        mProvince,
                        mCity,
                        mArea,
                        detail,
                        1.toString()
                    )
                    viewModel.saveAddress(body.toMap()).observe(this, Observer {
                       it?.run {
                           toast(message.toString())
                           finish()
                       }
                    })
                }
            }

        }
        when (intent.iflag) {
            "change" -> {
                getAddress()
            }
        }

    }

    private fun showCity() {
        val popup = CityPickerPopup(this)
        popup.setCityPickerListener(object : CityPickerListener {
            override fun onCityConfirm(province: String, city: String, area: String, v: View) {
                mProvince = province
                mCity = city
                mArea = area
                mBinding.edtAddressCity.text = province.plus(" ").plus(city).plus(" ").plus(area)
            }

            override fun onCityChange(province: String, city: String, area: String) {

            }
        })
        XPopup.Builder(this).asCustom(popup).show()
    }

    private fun getAddress() {
        mProvince=intent.iprovince?:""
        mCity=intent.icity?:""
        mArea=intent.iarea?:""
        mBinding.edtAddressCity.text = intent.iprovince.plus(intent.icity).plus(intent.iarea)
        mBinding.edtAddressDetail.setText(intent.iaddress)
        mBinding.edtAddressPhone.setText(intent.iphone)
        mBinding.edtAddressName.setText(intent.iname)
    }

}