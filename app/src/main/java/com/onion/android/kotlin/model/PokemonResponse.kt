package com.onion.android.kotlin.model

import com.onion.android.kotlin.model.Pokemon
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


/*
* 这里使用 moshi库 进行映射,moshi 与 kotlin 语法有很好的结合
* @JsonClass 为True会触发注释处理器为该类型生成适配器。
* @field:Json 自定义字段如何编码为JSON
* */
@JsonClass(generateAdapter = true)
data class PokemonResponse(
        @field:Json(name = "count") val count: Int,
        @field:Json(name = "next") val next: String?,
        @field:Json(name = "previous") val previous: String?,
        @field:Json(name = "results") val results: List<Pokemon>,
)