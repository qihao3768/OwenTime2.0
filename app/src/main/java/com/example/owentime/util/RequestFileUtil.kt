package com.example.owentime.util

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

/**
 * @author David
 * @description: 上传文件帮助工具
 * @date :2020/8/10 19:17
 */
object RequestFileUtil {
    /**
     * 上传单个文件
     * @param fileName
     * @param file 为参数字段名
     * @return
     */
    fun uploadFile(fileName: String, file: File): MultipartBody.Part {
//        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data;charset=UTF-8"), file); 过时
        val requestBody = getRequestBody(file)
        return MultipartBody.Part.createFormData(fileName, file.name, requestBody)
    }

    private fun getRequestBody(file: File): RequestBody {
//        MediaType mediaType=MediaType.parse("text/x-markdown; charset=utf-8");
        val mediaType: MediaType ?= "multipart/form-data; charset=utf-8".toMediaTypeOrNull()
        return file.asRequestBody(mediaType)
    }

    /**
     * 上传多个文件
     * @param files
     * @return
     */
    fun filesToMultipartBodyParts(files: List<File>): List<MultipartBody.Part> {
        val parts: MutableList<MultipartBody.Part> = ArrayList<MultipartBody.Part>(files.size)
        for (file in files) {
//            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data;charset=UTF-8"), file);
            val requestBody = getRequestBody(file)
            val part: MultipartBody.Part = MultipartBody.Part.createFormData("multipartFiles", file.name, requestBody)
            parts.add(part)
        }
        return parts
    }

    /**
     * 请求参数转化为RequestBody
     * @param value
     * @return
     */
    fun toRequestBody(value: String): RequestBody {
//        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        val mediaType: MediaType = "application/json;charset=utf-8".toMediaType()
        return value.toRequestBody(mediaType)
    }
}