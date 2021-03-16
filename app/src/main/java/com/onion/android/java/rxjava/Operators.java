package com.onion.android.java.rxjava;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;

/**
 * RxJava 操作符学习
 */
public class Operators {
    private final String TAG = Operators.class.getSimpleName();
    public static void main(String[] args) {
        Observable.intervalRange(1,1320,1,1, TimeUnit.SECONDS,Schedulers.trampoline()).subscribe(
                new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        System.out.println("aLong = " + aLong);
                    }
                }
        );
    }
    /**
     * Map 操作: 接受一个,输出另一个
     */
    private  void rxMap(){
        observableForMap().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map(
                new Function<List<Integer>, List<String>>() {
                    @Override
                    public List<String> apply(@NonNull List<Integer> integers) throws Exception {
                        // 处理传送过来的,然后输出另一个
                        return new ArrayList<String>();
                    }
                }).subscribe(observerForMap());
    }
    private Observable<List<Integer>> observableForMap() {
        return Observable.create(new ObservableOnSubscribe<List<Integer>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<Integer>> emitter) throws Exception {
                if(!emitter.isDisposed()){
                    emitter.onNext(new ArrayList<Integer>());
                    emitter.onComplete();
                }
            }
        });
    }
    private Observer<List<String>> observerForMap(){
        return  new Observer<List<String>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) { Log.d(TAG, " onSubscribe : " + d.isDisposed()); }

            @Override
            public void onNext(@NonNull List<String> strings) { Log.d(TAG, " onNext : " + strings.size()); }

            @Override
            public void onError(@NonNull Throwable e) { Log.d(TAG, " onError : " + e.getMessage()); }

            @Override
            public void onComplete() {  Log.d(TAG, " onComplete");}
        };
    }
    /**
     * Zip 操作: 接受多个,输出另一个
     */
    private void rxZip() {
        Observable.zip(observableForZip1(), observableForZip2(),
                new BiFunction<List<String>, List<String>, List<String>>() {
                    @NonNull
                    @Override
                    public List<String> apply(@NonNull List<String> strings, @NonNull List<String> strings2) throws Exception {
                        // 接受两个或多个,然后输出
                        return null;
                    } })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observerForZip());
    }

    private Observable<List<String>> observableForZip1(){
        return Observable.create((ObservableOnSubscribe<List<String>>) emitter -> {
            if(!emitter.isDisposed()) { emitter.onNext(new ArrayList<String>());emitter.onComplete(); }
        }).subscribeOn(Schedulers.io());
    }
    private Observable<List<String>> observableForZip2(){
        return Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<String>> emitter) throws Exception {
                if(!emitter.isDisposed()) { emitter.onNext(new ArrayList<String>());emitter.onComplete(); }
            }
        }).subscribeOn(Schedulers.io());
    }

    private Observer<List<String>> observerForZip(){
        return new Observer<List<String>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) { Log.d(TAG, " onSubscribe : " + d.isDisposed()); }

            @Override
            public void onNext(@NonNull List<String> strings) { Log.d(TAG, " onNext : " + strings.size()); }

            @Override
            public void onError(@NonNull Throwable e) { Log.d(TAG, " onError : " + e.getMessage()); }

            @Override
            public void onComplete() {  Log.d(TAG, " onComplete");}
        };
    }
    /**
     * Disposable : 统一管理订阅
     * RxJava容易造成内存泄漏,在某些情况下没有及时取消订阅导致内存泄漏
     */
    private final CompositeDisposable disposables = new CompositeDisposable();
    private  Observable<String> observableForDisposable(){
        return Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                return Observable.just("ONE");
            }
        });
    }
    private void useDisposable(){
        disposables.add(
                observableForDisposable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                       new DisposableObserver<String>(){
                           @Override
                           public void onNext(@NonNull String s) {  Log.d(TAG, " onNext value : " + s); }

                           @Override
                           public void onError(@NonNull Throwable e) { Log.d(TAG, " onError : " + e.getMessage()); }

                           @Override
                           public void onComplete() { Log.d(TAG, " onComplete"); }
                       }
                )
        );
    }
    /**
     * Take : 指定操作数次
     * 如设为1,不管传多少,都只执行一次
     */
    private void useTake(){
        disposables.add(
                observableForDisposable().subscribeOn(Schedulers.io())
                        .take(1)
                        .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableObserver<String>(){
                            @Override
                            public void onNext(@NonNull String s) {  Log.d(TAG, " onNext value : " + s); }

                            @Override
                            public void onError(@NonNull Throwable e) { Log.d(TAG, " onError : " + e.getMessage()); }

                            @Override
                            public void onComplete() { Log.d(TAG, " onComplete"); }
                        }
                )
        );
    }
    /**
     * Timer : 倒计时操作
     */
    private  Observable<String> observableForTimer(){
        return Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                return Observable.timer(2, TimeUnit.SECONDS).just("ONE");
            }
        });
    }
    /**
     * Interval : 轮询操作
     * interval - 永久轮询
     * intervalRange - 指定轮询
     * period - 周期 , start 开始步数 , count 总步数 , initialDelay 开始延时
     */
    public   Observable<String> observableForInterval(){
        return Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                return Observable.intervalRange(0,1,0,10, TimeUnit.SECONDS).just("ONE");
            }
        });
    }
    /**
     * SingleObserver : 只发射单个数据或错误事件。
     * Observable : 能够发射0或n个数据，并以成功或错误事件终止。
     * Completable : 它从来不发射数据，只处理 onComplete 和 onError 事件。可以看成是Rx的Runnable。
     */
    private void useSingle(){
        Single.just(new ArrayList<String>(1)).subscribe();
    }
    public SingleObserver<String> observerForSingle(){
        return new SingleObserver<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) { Log.d(TAG, " onSubscribe : " + d.isDisposed()); }

            @Override
            public void onSuccess(@NonNull String s) { Log.d(TAG, " onNext value : " + s); }

            @Override
            public void onError(@NonNull Throwable e) { Log.d(TAG, " onError : " + e.getMessage()); }
        };
    }
    private void useCompletable(){
        Completable.timer(1000,TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(observerCompletable());
    }
    private CompletableObserver observerCompletable(){
        return new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {  Log.d(TAG, " onSubscribe : " + d.isDisposed()); }
            @Override
            public void onComplete() { Log.d(TAG, " onComplete"); }
            @Override
            public void onError(@NonNull Throwable e) { Log.d(TAG, " onError : " + e.getMessage()); }
        };
    }
    /**
     * Buffer - 缓冲操作
     * count 缓冲数 , skip 跨步
     */
    private void userBuffer(){
        observableForBuffer().buffer(3, 1).subscribe(observerForBuffer());
    }
    private Observable<String> observableForBuffer(){
        return Observable.just("one", "two", "three", "four", "five");
    }
    private Observer<List<String>> observerForBuffer(){
        return new Observer<List<String>>() {
            @Override
            public void onSubscribe(Disposable d) { Log.d(TAG, " onSubscribe : " + d.isDisposed()); }

            @Override
            public void onNext(List<String> stringList) {
                for (String value : stringList) {
                    Log.d(TAG, " : value :" + value);
                }
            }

            @Override
            public void onError(Throwable e) { Log.d(TAG, " onError : " + e.getMessage()); }

            @Override
            public void onComplete() { Log.d(TAG, " onComplete"); }
        };
    }
    /**
     * Filter - 过滤操作
     */
    private void useFilter(){
        observableForBuffer().filter(new Predicate<String>() {
            @Override
            public boolean test(@NonNull String s) throws Exception {
                return s.length() > 3;
            }
        }).subscribe(observerForFilter());
    }
    private Observer<String> observerForFilter() {
        return new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onNext(String value) {
                Log.d(TAG, " onNext ");
                Log.d(TAG, " value : " + value);
            }

            @Override
            public void onError(Throwable e) { Log.d(TAG, " onError : " + e.getMessage()); }
            @Override
            public void onComplete() { Log.d(TAG, " onComplete"); }
        };
    }

    private Observer<String> observerForFilterOther() {
        return new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onNext(String value) {
                Log.d(TAG, " onNext ");
                Log.d(TAG, " value : " + value);
            }

            @Override
            public void onError(Throwable e) { Log.d(TAG, " onError : " + e.getMessage()); }
            @Override
            public void onComplete() { Log.d(TAG, " onComplete"); }
        };
    }

    /**
     * Skip - 从第几步开始
     */
    private  void useSkip(){
        observableForBuffer().skip(2).subscribe(observerForFilter());
    }

    /**
     * Delay : 延迟操作
     */
    private  void useDelay() { observableForBuffer().delay(2,TimeUnit.SECONDS).subscribe(observerForFilter());}
    /**
     * Debounce : 超时操作,超过指定时间,才接受
     */
    private  void useDebounce() {observableForBuffer().debounce(2,TimeUnit.SECONDS).subscribe(observerForFilter());}

    /**
     * ThrottleFirst : 间隔时间内,只接受第一个,然后等待下个间隔进入的
     *
     */
    private  void useThrottleFirst() {observableForBuffer().throttleFirst(500,TimeUnit.MILLISECONDS).subscribe(observerForFilter());}
    /**
     * ThrottleLast : 间隔时间内,只接受最后一个,然后等待下个间隔进入的
     *
     */
    private  void useThrottleLast() {observableForBuffer().throttleLast(500,TimeUnit.MILLISECONDS).subscribe(observerForFilter());}

    /**
     * Distinct : 排除重复数据
     */
    private  void useDistinct() {observableForBuffer().distinct().subscribe(observerForFilter());}

    /**
     * Merge: 合并处理
     */
    private  void useMerge() { Observable.merge(observableForBuffer(),observableForDisposable()).subscribe(observerForFilter());}
    /**
     * Concat: 联合处理
     */
    private  void useConcat() { Observable.concat(observableForBuffer(),observableForDisposable()).subscribe(observerForFilter());}
    /**
     * Async : 看例子
     */
    private  void useAsync() {
        AsyncSubject<String> source = AsyncSubject.create();
        // Three onComplete
        source.subscribe(observerForFilter());

        source.onNext("ONE");
        source.onNext("TWO");
        source.onNext("THREE");
        // can use a other observer  , also Three onComplete
        source.subscribe(observerForFilterOther());
        source.onComplete();
    }
    /**
     * Behavior : 看例子
     */
    private void useBehavior(){
        BehaviorSubject<String> source = BehaviorSubject.create();
        // One,Two,Three,Four ,onComplete
        source.subscribe(observerForFilter());

        source.onNext("ONE");
        source.onNext("TWO");
        source.onNext("THREE");
        // can use a other observer  ,Three Four onComplete
        source.subscribe(observerForFilterOther());
        source.onNext("FOUR");
        source.onComplete();
    }
    /**
     * Publish : 按顺序接受
     */
    private void usePublish(){
        BehaviorSubject<String> source = BehaviorSubject.create();
        // One,Two,Three,Four ,onComplete
        source.subscribe(observerForFilter());
        source.onNext("ONE");
        source.onNext("TWO");
        source.onNext("THREE");
        // can use a other observer  , Four onComplete
        source.subscribe(observerForFilterOther());
        source.onNext("FOUR");
        source.onComplete();
    }
    /**
     * Replay : 每次都重新接受
     */
    private void useReplay(){
        BehaviorSubject<String> source = BehaviorSubject.create();
        // One,Two,Three,Four ,onComplete
        source.subscribe(observerForFilter());
        source.onNext("ONE");
        source.onNext("TWO");
        source.onNext("THREE");
        source.onNext("FOUR");
        source.onComplete();
        // One,Two,Three,Four ,onCompletee
        source.subscribe(observerForFilterOther());
    }

}
