package rxtest;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Buffer {

    public static void main(String[] args) {
        Observable
                .create((Observable.OnSubscribe<Integer>) observer -> {
                    System.out.println("Thread:" + Thread.currentThread().getName() + "\tEmit items.");
                    try {
                        if (!observer.isUnsubscribed()) {
                            for (int i = 1; i <= 10; i++) {
                                observer.onNext(i);
                            }
                            observer.onCompleted();
                        }
                    } catch (Exception e) {
                        observer.onError(e);
                    }
                })
                .buffer(2, 4) // 1,2번째는 발생시키고, 3,4번째는 스킵, 다시 5,6는 발생시킵니다.
                .doOnEach(notification -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tEach: " + notification))
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .map((List<Integer> bufferedItems) -> {
                    System.out.println("Thread:" + Thread.currentThread().getName() + "\tMap: " + bufferedItems);
                    return bufferedItems.get(0);
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