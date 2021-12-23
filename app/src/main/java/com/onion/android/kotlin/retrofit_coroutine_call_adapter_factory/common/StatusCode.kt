package com.onion.android.kotlin.retrofit_coroutine_call_adapter_factory.common

/*
* 枚举类 （enum class） - 实现一个类型安全的枚举
* 1 - 每一个枚举都是枚举类的实例，它们可以被初始化
* 2 - 使用枚举常量
*   a - EnumClass.valueOf(value: String): EnumClass  // 转换指定 name 为枚举值，若未匹配成功，会抛出IllegalArgumentException
*   b - EnumClass.values(): Array<EnumClass>        // 以数组的形式，返回枚举值
* */
enum class StatusCode(val code: Int) {


    Unknown(0),

    Continue(100),
    SwitchingProtocols(101),
    Processing(102),
    EarlyHints(103),

    OK(200),
    Created(201),
    Accepted(202),
    NonAuthoritative(203),
    NoContent(204),
    ResetContent(205),
    PartialContent(206),
    MultiStatus(207),
    AlreadyReported(208),
    IMUsed(209),

    MultipleChoices(300),
    MovePermanently(301),
    Found(302),
    SeeOther(303),
    NotModified(304),
    UseProxy(305),
    SwitchProxy(306),
    TemporaryRedirect(307),
    PermanentRedirect(308),

    BadRequest(400),
    Unauthorized(401),
    PaymentRequired(402),
    Forbidden(403),
    NotFound(404),
    MethodNotAllowed(405),
    NotAcceptable(406),
    ProxyAuthenticationRequired(407),
    RequestTimeout(408),
    Conflict(409),
    Gone(410),
    LengthRequired(411),
    PreconditionFailed(412),
    PayloadTooLarge(413),
    URITooLong(414),
    UnsupportedMediaType(415),
    RangeNotSatisfiable(416),
    ExpectationFailed(417),
    IMATeapot(418),
    MisdirectedRequest(421),
    UnProcessableEntity(422),
    Locked(423),
    FailedDependency(424),
    TooEarly(425),
    UpgradeRequired(426),
    PreconditionRequired(428),
    TooManyRequests(429),
    RequestHeaderFieldsTooLarge(431),
    UnavailableForLegalReasons(451),

    InternalServerError(500),
    NotImplemented(501),
    BadGateway(502),
    ServiceUnavailable(503),
    GatewayTimeout(504),
    HTTPVersionNotSupported(505),
    NotExtended(510),
    NetworkAuthenticationRequired(511);
}