package rxtest;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import java.util.List;

public class FlatMap {

    public static void main(String[] args) {
        Observable
                .create((Observable.OnSubscribe<Observable>) observer -> {
                    System.out.println("Thread:" + Thread.currentThread().getName() + "\tEmit items.");
                    try {
                        if (!observer.isUnsubscribed()) {
                            for (int i = 1; i <= 5; i++) {
                                observer.onNext(Observable.range(0, i));
                            }
                            observer.onCompleted();
                        }
                    } catch (Exception e) {
                        observer.onError(e);
                    }
                })
                .flatMap(new Func1<Observable, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable observable) {
                        return null;
                    }
                })
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