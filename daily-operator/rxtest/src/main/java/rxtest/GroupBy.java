package rxtest;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;
import rx.observables.GroupedObservable;
import rx.schedulers.Schedulers;


public class GroupBy {

    public static void main(String[] args) {
        Observable
                .range(1, 10)
                .groupBy(item -> item % 3)
                .subscribe(
                        (GroupedObservable<Integer, Integer> item) -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tonNext: " + item),
                        e -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tonError: " + e.getMessage()),
                        () -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tonCompleted")
                );

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}