package com.example.localuser.retrofittest.kotlinstudy

//Kotlin 获取泛型类型（超清晰总结）
//Kotlin（和 Java）因为泛型擦除，运行时直接拿不到泛型具体类型，但 Kotlin 提供了完美解决方案：reified 实化泛型 + 反射。
//我直接给你最常用、能直接复制用的写法！
//一、最简单方案：inline + reified（90% 场景用这个）
//必须是内联函数 + reified 关键字，就能在运行时拿到泛型的真实类型。
//示例代码
//kotlin
//// 关键：inline + reified
//inline fun <reified T> getGenericType(): Class<T> {
//    return T::class.java
//}
//
//// 测试
//fun main() {
//    val type = getGenericType<String>()
//    println(type) // 输出：class java.lang.String
//}
//你可以直接用的工具方法
//kotlin
//// 判断泛型是不是某个类型
//inline fun <reified T> isType(value: Any): Boolean {
//    return value is T
//}
//
//// 获取泛型的 KClass（Kotlin 类型）
//inline fun <reified T> getKotlinType() = T::class
//二、普通类 / 属性 里获取泛型（带超类）
//如果你的类继承了泛型父类，可以用反射获取父类的泛型参数。
//示例
//kotlin
//abstract class BaseRepository<T> {
//    // 获取泛型 T 的类型
//    fun getGenericType(): Class<*> {
//        // 获取父类的泛型类型
//        val type = javaClass.genericSuperclass as ParameterizedType
//        return type.actualTypeArguments[0] as Class<*>
//    }
//}
//
//// 子类
//class UserRepository : BaseRepository<String>()
//
//// 使用
//fun test() {
//    val repo = UserRepository()
//    println(repo.getGenericType()) // class java.lang.String
//}
//三、获取 List<T> / Map<K,V> 这种复杂泛型
//复杂泛型需要用 object : TypeToken<T>(){} 方式保留类型。
//示例（最常用）
//kotlin
//// 保留泛型类型
//abstract class TypeToken<T> {
//    val type: Type = javaClass.genericSuperclass.let {
//        (it as ParameterizedType).actualTypeArguments[0]
//    }
//}
//
//// 使用
//fun main() {
//    val token = object : TypeToken<List<String>>() {}
//    println(token.type) // 输出：java.util.List<java.lang.String>
//}
//Gson / Moshi 等库底层就是用这个原理。
//四、核心知识点（一句话记住）
//直接获取不行：T::class 报错，因为泛型擦除。
//能获取的唯一方式：
//函数：inline + reified
//类：继承父类 + 反射拿 ParameterizedType
//复杂泛型：用 TypeToken 匿名内部类保留类型。
//总结
//日常开发 90% 用 inline + reified 就够了，最简单安全。
//类里面拿泛型：继承泛型父类 + 反射。
//集合泛型：TypeToken。
class GenericTest {
    private inline fun  <reified T> genericFun1() : T? {
        return when (T::class.java) {
            Boolean::class.java -> true as T
            java.lang.Boolean::class.java -> true as T
            String::class.java -> "123" as T
            else -> null
        }
        // as 是强制类型转换，类型不一致会报异常
        // as? T,比较安全的转换， 转换失败返回null
    }

    fun test1() {
        val result1 = genericFun1<Boolean>()
        val result2 = genericFun1() as Boolean?
        val result3 : Boolean? = genericFun1()
    }
}