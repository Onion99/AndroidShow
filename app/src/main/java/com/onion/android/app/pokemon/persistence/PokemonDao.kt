package com.onion.android.app.pokemon.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.onion.android.app.pokemon.model.Pokemon

/*
* @Dao : 将类标记为数据访问对象。数据访问对象是定义数据库交互的主要类。 它们可以包括各种查询方法
* 1 - 标有@Dao的类应该是接口或抽象类。 在编译时，Room被Database引用时将生成此类的实现
* 2 - 抽象的@Dao类可以选择具有一个将Database作为其唯一参数的构造函数
* 3 - 建议您在代码库中有多个Dao类，具体取决于它们接触的表
* */
@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun insertPokemonList(pokemonList:List<Pokemon>)

    @Query("SELECT * FROM Pokemon WHERE page = :page_")
    suspend fun getPokemonList(page_ :Int):List<Pokemon>

    @Query("SELECT * FROM Pokemon WHERE page <= :page_ ")
    suspend fun getAllPokemonList(page_:Int): List<Pokemon>
}