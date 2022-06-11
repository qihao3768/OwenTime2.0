package com.example.owentime.serializer

import com.example.owentime.bean.BaseModel
import com.example.owentime.bean.Studying
import com.example.owentime.bean.User
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonTransformingSerializer

class UserListSerializer : JsonTransformingSerializer<List<User>>(ListSerializer(User.serializer())) {
    // If response is not an array, then it is a single object that should be wrapped into the array
    override fun transformDeserialize(element: JsonElement): JsonElement =
        if (element !is JsonArray)
            JsonArray(listOf(element))
        else element
}

class StuydingListSerializer : JsonTransformingSerializer<List<Studying>>(ListSerializer(Studying.serializer())) {
    // If response is not an array, then it is a single object that should be wrapped into the array
    override fun transformDeserialize(element: JsonElement): JsonElement =
        if (element !is JsonArray)
            JsonArray(listOf(element))
        else element
}