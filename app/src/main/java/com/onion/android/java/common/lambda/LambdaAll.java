package com.onion.android.java.common.lambda;


/** Lambda 允许把函数作为一个方法的参数（函数作为参数传递进方法中） */
public class LambdaAll {
    /*
    * 语法格式
    * (parameters) -> expression
    * (parameters) ->{ statements; }
    * */
    public static void main(String[] args) {
        /*
          作用：
          Lambda 表达式主要用来定义行内执行的方法类型接口，例如，一个简单方法接口。在例子中，我们使用各种类型的Lambda表达式来定义MathOperation接口的方法
          Lambda 表达式免去了使用匿名方法的麻烦
         */
        // 方法引用
        MathOperation addition = Integer::sum;
        // 包含类型声明
        MathOperation subtraction = (int a,int b) -> a - b;
        // 不包含类型声明
        MathOperation multiplication = (a, b) -> {
            return a * b;
        };
        // 最简洁
        MathOperation division = (a, b) -> a / b;
        /*
        * 注意：
        * 1-lambda 表达式只能引用标记了final的外层局部变量，这就是说不能在 lambda 内部修改定义在域外的局部变量，否则会编译错误。
        * 2-lambda 表达式的局部变量可以不用声明为 final，但是必须不可被后面的代码修改（即隐性的具有 final 的语义）
        * 3-在 Lambda 表达式当中不允许声明一个与局部变量同名的参数或者局部变量
        * */
    }

    interface MathOperation{
        int operation(int a, int b);
    }
}
