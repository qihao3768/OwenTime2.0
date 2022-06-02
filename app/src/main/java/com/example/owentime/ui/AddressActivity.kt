package com.example.owentime.ui

import android.view.View
import android.widget.Toast
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.owentime.R
import com.example.owentime.base.BaseActivity
import com.example.owentime.databinding.ActivityAddressBinding
import com.example.owentime.databinding.ActivityUpOrderBinding
import com.gyf.immersionbar.ktx.immersionBar
import com.lxj.xpopup.XPopup
import com.lxj.xpopupext.listener.CityPickerListener
import com.lxj.xpopupext.popup.CityPickerPopup


class AddressActivity : BaseActivity(R.layout.activity_address) {

    private val mBinding by viewBinding (ActivityAddressBinding::bind)

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

    }

    private fun showCity(){
        val popup = CityPickerPopup(this)
        popup.setCityPickerListener(object : CityPickerListener {
            override fun onCityConfirm(province: String, city: String, area: String, v: View) {
                mBinding.edtAddressCity.text=province.plus(" ").plus(city).plus(" ").plus(area)
            }

            override fun onCityChange(province: String, city: String, area: String) {

            }
        })
        XPopup.Builder(this).asCustom(popup).show()
    }

}