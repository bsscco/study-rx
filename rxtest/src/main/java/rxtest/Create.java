package rxtest;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class Create {

    public static void main(String[] args) {
        Observable.just(1)
                .map(i -> i)
                .subscribe(i -> {
                });
        Observable
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
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(item -> {
                    int mapped = item * item;
                    System.out.println("Thread:" + Thread.currentThread().getName() + "\tMap: " + mapped);
                    return mapped;
                })
                .observeOn(Schedulers.computation())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onNext(Integer item) {
                        System.out.println("Thread:" + Thread.currentThread().getName() + "\tNext: " + item);
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("Thread:" + Thread.currentThread().getName() + "\tSequence complete.");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.err.println("Thread:" + Thread.currentThread().getName() + "\tError: " + e.getMessage());
                    }
                });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}