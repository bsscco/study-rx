# empty
아무 데이터도 발생시키지 않는 Observable을 만듭니다. onCompleted만 호출됩니다.
![empty.c.png](http://reactivex.io/documentation/operators/images/empty.c.png)
<br>

# never
기본적으로 empty와 같지만, onCompleted도 호출되지 않습니다.
![never.c.png](http://reactivex.io/documentation/operators/images/never.c.png)
<br>

# throw
기본적으로 empty와 같지만, onCompleted 대신에 onError가 호출됩니다.
![throw.c.png](http://reactivex.io/documentation/operators/images/throw.c.png)
<br>

# 샘플 코드
```java
// RxJava2
Observable.empty()
Observable.never()
Observable.error(Callable)
Observable.error(Throwable)
```


