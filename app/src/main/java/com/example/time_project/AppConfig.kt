package com.example.time_project


object AppConfig {
    val APP_ID:String="wx58dff157a444095f"
     var IS_DEBUG: Boolean = false //debug模式，上线时关闭

     var SERVICE_AGREEMENT: String = "" //用户服务协议
    var  SERVICE_AGREEMENT_URL : String = "https://www.owentime.cn/api/dict/getUserAgreement"//用户服务协议


     var PRIVACY_AGREEMENT: String = "" //用户隐私保护政策

     var PRIVACY_AGREEMENT_URL : String = "https://www.owentime.cn/api/dict/getPrivacy"//用户隐私保护政策


     var CHILDREN_AGREEMENT : String = ""//儿童服务协议

     var  CHILDREN_AGREEMENT_URL: String = "https://www.owentime.cn/api/dict/getChildrenPrivacy" //儿童服务协议
}