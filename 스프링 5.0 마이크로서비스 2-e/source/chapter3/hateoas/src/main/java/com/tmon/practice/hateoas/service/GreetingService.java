package com.tmon.practice.hateoas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import java.util.concurrent.Executor;

@Service
public class GreetingService {
    @Autowired
    @Qualifier("asyncThreadPoolTaskExecutor")
    Executor taskExecutor;

    public void rxTest01() {
        Observable.just("long", "longer", "longest")
                .doOnNext(c -> System.out.println("processing item on thread " + Thread.currentThread().getName()))
                .map(String::length)
                .subscribe(length -> System.out.println("item length " + length));
    }

    public void rxTest02() throws InterruptedException {
        Observable.just("long", "longer", "longest")
                .doOnNext(c -> System.out.println("processing item on thread " + Thread.currentThread().getName()))
                .subscribeOn(Schedulers.newThread())
                .map(String::length)
                .subscribe(length -> System.out.println("item length " + length));

        Thread.sleep(3000);
    }

    public void rxTest03() {
        Observable.just("long", "longer", "longest")
                .doOnNext(c -> System.out.println("processing item on thread " + Thread.currentThread().getName()))
                .subscribeOn(Schedulers.newThread())
                .map(String::length)
                .observeOn(Schedulers.io())
                .subscribe(length -> System.out.println("item length " + length + " received on thread " + Thread.currentThread().getName()));
    }

    public void rxTest04() {
        Observable.just("long", "longer", "longest")
                .doOnNext(c -> System.out.println("processing item on thread " + Thread.currentThread().getName()))
                .subscribeOn(Schedulers.newThread())
                .map(String::length)
                .observeOn(Schedulers.from(taskExecutor))
                .subscribe(length -> System.out.println("item length " + length + " received on thread " + Thread.currentThread().getName()));
    }

    public void rxTest05() {
        Observable.just("long", "longer", "longest", "long", "longer", "longest")
                .doOnNext(c -> System.out.println("processing item on thread " + Thread.currentThread().getName()))
                .map(str -> str.length())
                .subscribeOn(Schedulers.from(taskExecutor))
                .observeOn(Schedulers.from(taskExecutor))
                .subscribe(length -> {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("item length " + length + " received on thread " + Thread.currentThread().getName());
                });
    }
}
