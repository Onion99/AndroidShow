package com.onion99.android.kotlin.di

import android.app.Application
import androidx.room.Room
import com.onion99.android.kotlin.persistence.AppDataBase
import com.onion99.android.kotlin.persistence.PokemonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
* object
* 1 - 用于对象表达式的声明，即用于接口的声明
* 2 - 对象的声明,用object 修饰的类为静态类，里面的方法和变量都为静态的。
* */

/*
* @Module - 表明这个类是Module,为了解决@Inject不不能使用的场景，比如我们使用第三方库,就无法修改，无法加入,
* @Providers - 是标注 Module中能够提供实例化对象的方法
* @InstallIn - Hilt模块也需要添加@Module注释，与Dagger不同的是它还必须使用@InstallIn为模块添加注释。目的是告知模块用在哪个Android类中
* @Singleton - 标注依赖注入只实例化一次
*
* Hilt 中组件的生命周期
* 提供的组件	            创建对应的生命周期	        结束对应的生命周期	         作用范围
* ApplicationComponent	Application#onCreate()	Application#onDestroy()	 @Singleton
* ActivityRetainedComponent	Activity#onCreate()	Activity#onDestroy()	@ActivityRetainedScope
* ActivityComponent	    Activity#onCreate()	    Activity#onDestroy()	@ActivityScoped
* FragmentComponent	    Fragment#onAttach()	   Fragment#onDestroy()	    @FragmentScoped
* ViewComponent	        View#super()	       View destroyed	        @ViewScoped
* ViewWithFragmentComponent	View#super()	  View destroyed	        @ViewScoped
* ServiceComponent	Service#onCreate()	      View destroyed	       @ViewScoped
* */
@Module
@InstallIn(SingletonComponent::class)
object Persistence {

    @Provides
    @Singleton
    fun provideAppDataBase(
        application: Application
    ):AppDataBase{
        return Room.databaseBuilder(application,AppDataBase::class.java,"Pokemon.db").fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providePokemonDao(appDataBase: AppDataBase):PokemonDao{
        return appDataBase.pokemonDao()
    }
}