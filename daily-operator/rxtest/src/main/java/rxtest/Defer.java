package rxtest;

import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class Defer {

    public static void main(String[] args) {
        Observable
                .defer(() -> {
                    return Observable
                            .create((Observable.OnSubscribe<Integer>) observer -> {
                                System.out.println("Thread:" + Thread.currentThread().getName() + "\tEmit items.");
                                try {
                                    if (!observer.isUnsubscribed()) {
                                        for (int i = 1; i < 5; i++) {
                                            observer.onNext(i);
                                        }
                                        observer.onCompleted();
                                    }
                                } catch (Exception e) {
                                    observer.onError(e);
                                }
                            })
                            .subscribeOn(Schedulers.computation())
                            .observeOn(Schedulers.io())
                            .map(item -> {
                                System.out.println("Thread:" + Thread.currentThread().getName() + "\tMap: " + item);
                                return item * item;
                            })
                            .observeOn(Schedulers.computation())
//                            .doOnNext(item -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tonNext: " + item))
                            .doOnCompleted(() -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tonCompleted"))
                            .doOnError(e -> System.err.println("Thread:" + Thread.currentThread().getName() + "\tonError: " + e.getMessage()));
                })
                .doOnNext(item -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tonNext: " + item)) // 여기서 onNext 등의 메소드를 호출해도 적용이 됩니다.
                .subscribe();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}