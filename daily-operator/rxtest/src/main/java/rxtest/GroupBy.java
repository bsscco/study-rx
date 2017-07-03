package rxtest;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;
import rx.observables.GroupedObservable;
import rx.schedulers.Schedulers;


public class GroupBy {

    public static void main(String[] args) {
        Observable
                .create((Observable.OnSubscribe<Integer>) observer -> {
                    System.out.println("Thread:" + Thread.currentThread().getName() + "\tEmit items.");
                    try {
                        if (!observer.isUnsubscribed()) {
                            for (int i = 1; i <= 5; i++) {
                                observer.onNext(i);
                            }
                            observer.onCompleted();
                        }
                    } catch (Exception e) {
                        observer.onError(e);
                    }
                })
                .observeOn(Schedulers.io())
                .doOnEach(notification -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tEachBeforeGroupBy: " + notification))
                .observeOn(Schedulers.computation())
                .groupBy(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer item) {
                        return item % 2 == 0; /*짝수그룹, 홀수그룹으로 나눕니다.*/
                    }
                })
                .doOnEach(notification -> System.out.println("Thread:" + Thread.currentThread().getName() + "\tEachAfterGroupBy: " + notification))
                .observeOn(Schedulers.io())
                .flatMap(new Func1<GroupedObservable<Boolean, Integer>, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(GroupedObservable<Boolean, Integer> booleanIntegerGroupedObservable) {
                        return booleanIntegerGroupedObservable;
                    }
                })
                .subscribeOn(Schedulers.computation())
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