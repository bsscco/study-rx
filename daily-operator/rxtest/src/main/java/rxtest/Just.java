package rxtest;

import rx.Observable;
import rx.schedulers.Schedulers;

public class Just {

    public static void main(String[] args) {
        Observable
//                .just(new Integer[]{1, 2, 3}) // 이렇게 넣으면 배열 객체 자체를 하나의 each로 간주합니다.
                .just(1, 2, 3)
                .doOnEach(notification -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tEach: " + notification))
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .map(item -> {
                    System.out.println("Thread:" + Thread.currentThread().getName() + "\tMap: " + item);
                    return item + " is mapped value";
                })
                .observeOn(Schedulers.newThread())
                .doOnNext(item -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tonNext: " + item))
                .observeOn(Schedulers.io())
                .doOnCompleted(() -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tonCompleted"))
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