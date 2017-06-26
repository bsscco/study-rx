package rxtest;

import rx.Notification;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

import java.util.concurrent.TimeUnit;

public class Interval {

    public static void main(String[] args) {
        Subscription subscription = Observable
                .interval(500, 500, TimeUnit.MILLISECONDS) // 500ms 후부터 500ms마다 onNext
                .doOnEach(notification -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tEach: " + notification))
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .map(item -> {
                    System.out.println("Thread:" + Thread.currentThread().getName() + "\tMap: " + item);
                    return item * item;
                })
                .observeOn(Schedulers.newThread())
                .doOnNext(item -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tonNext: " + item))
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