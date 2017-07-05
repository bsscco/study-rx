package rxtest;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Observer;
import rx.Subscription;
import rx.schedulers.Schedulers;

public class Create {

    public static void main(String[] args) {
        Observable
                .create((OnSubscribe<Integer>) observer -> {
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
                .observeOn(Schedulers.computation())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onNext(Integer item) {
                        System.out.println("Thread:" + Thread.currentThread().getName() + "\tonNext: " + item);
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("Thread:" + Thread.currentThread().getName() + "\tonCompleted\n");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.err.println("Thread:" + Thread.currentThread().getName() + "\tonError: " + e.getMessage());
                    }
                });

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}