package rxtest;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import java.util.List;

public class FlatMap {

    public static void main(String[] args) {
        Observable
                .create((Observable.OnSubscribe<Integer[]>) observer -> {
                    System.out.println("Thread:" + Thread.currentThread().getName() + "\tEmit items.");
                    try {
                        if (!observer.isUnsubscribed()) {
                            for (int i = 1; i <= 5; i++) {
                                observer.onNext(new Integer[]{1, 2});
                            }
                            observer.onCompleted();
                        }
                    } catch (Exception e) {
                        observer.onError(e);
                    }
                })
                .observeOn(Schedulers.io())
                .doOnEach(notification -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tEachBeforeFlatMap: " + notification))
                .observeOn(Schedulers.newThread())
                .flatMap(new Func1<Integer[], Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(Integer[] ints) {
                        return Observable.from(ints);
                    }
                })
                .doOnEach(notification -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tEachAfterFlatMap: " + notification))
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .map((Integer item) -> {
                    System.out.println("Thread:" + Thread.currentThread().getName() + "\tMap: " + item);
                    return item * item;
                })
                .observeOn(Schedulers.newThread())
                .doOnNext((Integer item) -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tonNext: " + item))
                .observeOn(Schedulers.io())
                .doOnCompleted(() -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tonCompleted")) // 호출되지 않습니다.
                .observeOn(Schedulers.computation())
                .doOnError(e -> System.err.println("Thread:" + Thread.currentThread().getName() + "\tonError: " + e.getMessage()))
                .subscribe();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}