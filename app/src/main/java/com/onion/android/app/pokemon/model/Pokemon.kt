package com.onion.android.app.pokemon.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
/*
* @Parcelize 通过注解自动实现序列化
* @Entity 将类标记为实体。 此类将在数据库中具有映射SQLite表
*  1 - 每个实体必须至少具有1个用PrimaryKey注释的字段
*  2 - 使用primaryKeys()属性定义主键
* @JsonClass 自动Json解析
* */
@Entity
@Parcelize
@JsonClass(generateAdapter = true)
data class Pokemon(
    var page:Int = 0,
    @field:Json(name = "name") @PrimaryKey val name:String,
    @field:Json(name = "url") val url:String
):Parcelable{

    fun getImageUrl(): String {
        val index = url.split("/".toRegex()).dropLast(1).last()
        return "https://pokeres.bastionbot.org/images/pokemon/$index.png"
    }
}