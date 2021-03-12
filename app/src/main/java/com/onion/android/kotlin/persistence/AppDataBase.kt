package com.onion.android.kotlin.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.onion.android.kotlin.model.Pokemon

/*
* 整个App的持久化存储, 采用Room进行存储
* 1 - @Database() ,将一个类标记为RoomDatabase,并且定义用于访问的表和DAO类，
* 2 - exportSchema :默认情况下， exportSchema为true ，但是当您不想保留版本历史记录（例如仅内存数据库）时，可以将其禁用
* */
@Database(entities = [Pokemon::class],version = 1,exportSchema = true)
abstract class AppDataBase:RoomDatabase(){

    abstract fun pokemonDao():PokemonDao

}