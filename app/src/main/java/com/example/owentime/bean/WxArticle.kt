package com.example.owentime.bean
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


@Serializable
data class WxArticleTree(
    @SerialName("author")
    var author: String = "",
    @SerialName("children")
    var children: List<String> = listOf(),
    @SerialName("courseId")
    var courseId: Int = 0,
    @SerialName("cover")
    var cover: String = "",
    @SerialName("desc")
    var desc: String = "",
    @SerialName("id")
    var id: Int = 0,
    @SerialName("lisense")
    var lisense: String = "",
    @SerialName("lisenseLink")
    var lisenseLink: String = "",
    @SerialName("name")
    var name: String = "",
    @SerialName("order")
    var order: Int = 0,
    @SerialName("parentChapterId")
    var parentChapterId: Int = 0,
    @SerialName("userControlSetTop")
    var userControlSetTop: Boolean = false,
    @SerialName("visible")
    var visible: Int = 0
)

@Serializable
data class WxArticleTag(
    @SerialName("name")
    var name: String = "",
    @SerialName("url")
    var url: String = ""
)

@Serializable
data class WxArticleDetail(
    @SerialName("apkLink")
    var apkLink: String = "",
    @SerialName("audit")
    var audit: Int = 0,
    @SerialName("author")
    var author: String = "",
    @SerialName("canEdit")
    var canEdit: Boolean = false,
    @SerialName("chapterId")
    var chapterId: Int = 0,
    @SerialName("chapterName")
    var chapterName: String = "",
    @SerialName("collect")
    var collect: Boolean = false,
    @SerialName("courseId")
    var courseId: Int = 0,
    @SerialName("desc")
    var desc: String = "",
    @SerialName("descMd")
    var descMd: String = "",
    @SerialName("envelopePic")
    var envelopePic: String = "",
    @SerialName("fresh")
    var fresh: Boolean = false,
    @SerialName("host")
    var host: String = "",
    @SerialName("id")
    var id: Int = 0,
    @SerialName("link")
    var link: String = "",
    @SerialName("niceDate")
    var niceDate: String = "",
    @SerialName("niceShareDate")
    var niceShareDate: String = "",
    @SerialName("origin")
    var origin: String = "",
    @SerialName("prefix")
    var prefix: String = "",
    @SerialName("projectLink")
    var projectLink: String = "",
    @SerialName("publishTime")
    var publishTime: Long = 0,
    @SerialName("realSuperChapterId")
    var realSuperChapterId: Int = 0,
    @SerialName("selfVisible")
    var selfVisible: Int = 0,
    @SerialName("shareDate")
    var shareDate: Long = 0,
    @SerialName("shareUser")
    var shareUser: String = "",
    @SerialName("superChapterId")
    var superChapterId: Int = 0,
    @SerialName("superChapterName")
    var superChapterName: String = "",
    @SerialName("tags")
    var tags: List<WxArticleTag> = listOf(),
    @SerialName("title")
    var title: String = "",
    @SerialName("type")
    var type: Int = 0,
    @SerialName("userId")
    var userId: Int = 0,
    @SerialName("visible")
    var visible: Int = 0,
    @SerialName("zan")
    var zan: Int = 0
)