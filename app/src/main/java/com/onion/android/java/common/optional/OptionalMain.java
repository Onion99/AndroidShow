package com.onion.android.java.common.optional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class OptionalMain {
    static class User {
        App app = new App();

        public App getApp() {
            return app;
        }

        public void setApp(App app) {
            this.app = app;
        }
    }

    static class App {
        AppMsg appMsg = new AppMsg();

        public AppMsg getAppMsg() {
            return appMsg;
        }

    }

    static class AppMsg{
        String appName = "OpenSource";

        public String getAppName() {
            return appName;
        }

    }

    public static void main(String[] args) {
        // 使用 flatMap 链接 Optional 对象, 通过 Objects.requireNonNull会抛出空指针异常
        Optional.of(new User())
                .flatMap( user -> Optional.ofNullable(user.app))
                .flatMap( app -> Optional.ofNullable(app.appMsg))
                .flatMap( appMsg -> Optional.ofNullable(appMsg.appName))
                .ifPresent(System.out::println);
        // 使用 map 从 Optional 对象提取和转换值 ， 通过 Objects.requireNonNull会抛出空指针异常
        Optional.of(new User())
                .map(User::getApp)
                .map(App::getAppMsg)
                .map(AppMsg::getAppName)
                .ifPresent(System.out::println);
        // flatMap 与 Map
        Optional<User> test1 = Optional.of(new User());
        String result1 = test1.flatMap(user -> Optional.of(user.app))
                .map(app -> app.getAppMsg())
                .map(AppMsg::getAppName)
                .orElse("FreeSource");
        Optional.of(result1).ifPresent(System.out::println);
        // Supplier, 实现函数存储与传递

        Supplier<ClassFun> classFunSupplier = ClassTest1::new;
        ClassFun classTest1 = classFunSupplier.get();
        classTest1.printClassName();

        Supplier<String> stringSupplier = () -> {
            String className = OptionalMain.class.getSimpleName();
            String typeName = OptionalMain.class.getTypeName();
            StringBuilder builder = new StringBuilder();
            builder.append("class name:");
            builder.append(className);
            builder.append("\ntype name:");
            builder.append(typeName);
            return builder.toString();
        };
        System.out.println(stringSupplier.get());

        Map<String, Supplier<ClassFun>> map = new HashMap<>();
        map.put("ClassTest2", ClassTest2::new);

        whatResolve(() -> new App().getAppMsg().getAppName())
                .ifPresent(System.out::println);
    }

    private interface ClassFun{
        default void printClassName(){
            System.out.println(getClass().getSimpleName());
        }
    }
    static private class  ClassTest1 implements ClassFun{}
    static private class  ClassTest2 implements ClassFun{}


    // Supplier: 代表结果的提供者，不需要每次调用供应商都返回新的或不同的结果
    private  static <T> Optional<T> whatResolve(Supplier<T> resolver){
        try {
            T result = resolver.get();
            return Optional.ofNullable(result);
        }catch (NullPointerException e){
            // 返回一个空的Optional
            return Optional.empty();
        }
    }
}
