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
        Observable
                .interval(1000, 500, TimeUnit.MILLISECONDS) // 1000ms 후부터 500ms마다 onNext
                .subscribe(
                        item -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tonNext: " + item),
                        e -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tonError: " + e.getMessage()),
                        () -> {
                            // 아이템을 무한히 발생시키므로 onCompleted는 호출될 일이 없습니다.
                            System.out.println("Thread:" + Thread.currentThread().getName() + "\tonCompleted");
                        }
                );

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}