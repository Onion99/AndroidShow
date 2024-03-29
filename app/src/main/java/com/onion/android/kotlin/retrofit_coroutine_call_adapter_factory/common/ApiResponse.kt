package com.onion.android.kotlin.retrofit_coroutine_call_adapter_factory.common

import okhttp3.Headers
import okhttp3.ResponseBody
import retrofit2.Response

// ------------------------------------------------------------------------
// 密封类 (sealed class)
// 1 - 用来表示受限的类继承结构：当一个值为有限几种的类型, 而不能有任何其他类型时。
// 2 - 是枚举类的扩展：枚举类型的值集合 也是受限的，但每个枚举常量只存在一个实例，而密封类 的一个子类可以有可包含状态的多个实例
// 3 - 密封类可以有子类，但是所有的子类都必须要内嵌在密封类中
// 4 - sealed 不能修饰 interface ,abstract class(会报 warning,但是不会出现编译错误)
// ------------------------------------------------------------------------
sealed class ApiResponse<out T> {


    // ------------------------------------------------------------------------
    //  伴生对象
    //  @JvmField 标记静态属性
    //  @JvmStatic 标记静态方法
    // ------------------------------------------------------------------------
    companion object {
        @JvmField
        var successCodeRange: IntRange = 200..299


        // ------------------------------------------------------------------------
        // 网路请求成功处理
        // ------------------------------------------------------------------------
        fun <T> of(
            successCodeRange: IntRange = Companion.successCodeRange,
            result: () -> Response<T>
        ): ApiResponse<T> = try {
            // 拿取响应正文
            val response = result();
            // 请求成功
            if (response.raw().code in successCodeRange) {
                Success(response)
            } else {
                // 服务器错误返回
                Failure.Error(response)
            }
        } catch (exception: Exception) {
            // 请求异常
            Failure.Exception(exception)
        }

        // ------------------------------------------------------------------------
        // 网路请求异常处理
        // ------------------------------------------------------------------------
        fun <T> error(exception: Throwable) = Failure.Exception<T>(exception)
    }

    // ------------------------------------------------------------------------
    // 网络请求成功的响应体
    // ------------------------------------------------------------------------
    data class Success<T>(val response: Response<T>) : ApiResponse<T>() {
        val statusCode = getStatusCodeFromResponse(response)
        val headers: Headers = response.headers()
        val raw: okhttp3.Response = response.raw()
        val data: T? = response.body()
        override fun toString() = "[ApiResponse.Success](data=$data)"
    }

    // ------------------------------------------------------------------------
    // 网络请求失败的响应体
    // ------------------------------------------------------------------------
    sealed class Failure<T> {
        // ------------------------------------------------------------------------
        // 响应错误体
        // ------------------------------------------------------------------------
        data class Error<T>(val response: Response<T>) : ApiResponse<T>() {
            val statusCode = getStatusCodeFromResponse(response)
            val headers: Headers = response.headers()
            val raw: okhttp3.Response = response.raw()
            val errorBody: ResponseBody? = response.errorBody()
            override fun toString(): String =
                "[ApiResponse.Failure.Error-$statusCode](errorResponse=$response)"
        }

        // ------------------------------------------------------------------------
        // 异常体
        // ------------------------------------------------------------------------
        data class Exception<T>(val exception: Throwable) : ApiResponse<T>() {
            val message: String? = exception.localizedMessage
            override fun toString(): String = "[ApiResponse.Failure.Exception](message=$message)"
        }
    }


    // ------------------------------------------------------------------------
    // 获取HTTP响应状态码
    // ------------------------------------------------------------------------
    fun <T> getStatusCodeFromResponse(response: Response<T>): StatusCode {
        return StatusCode.values().find { it.code == response.code() } ?: StatusCode.Unknown
    }
}


// ------------------------------------------------------------------------
// Todo 型变in & out
// in - 相当于java里面的 <? super> ,父类泛型对象可以赋值给子类泛型对象，用 in , 消费者 in
// out - 相当于java里面的 <? extend> , 子类泛型对象可以赋值给父类泛型对象，用 out , 生产者 out
// ------------------------------------------------------------------------

// ------------------------------------------------------------------------
// Todo <T> ,泛型,为类型安全提供保证，消除类型强转的烦恼。
// fun <T> boxIn(value: T) = Box(value)
// val box4 = boxIn<Int>(1)
// val box5 = boxIn(1)     // 编译器会进行类型推断
// 关于星号投射，其实就是 * 代指了所有类型，相当于Any?
// val map:Pair<*,Any?> = Pair(1,2)
// val map:Triple<*,Any?,Int> = Triple(1,2,3)
// ------------------------------------------------------------------------

// ------------------------------------------------------------------------
// 数据类(data class)
// 1 - 自动构建这些函数，equals() / hashCode()，toString()，componentN() functions 对应于属性，按声明顺序排列，scopy() 函数,get/set()
// 2 - 需要满足的条件
//   a - 主构造函数至少包含一个参数
//   b - 所有的主构造函数的参数必须标识为val 或者 var ;
//   c - 数据类不可以声明为 abstract, open, sealed 或者 inner;
//   d - 数据类不能继承其他类 (但是可以实现接口)。
// ------------------------------------------------------------------------