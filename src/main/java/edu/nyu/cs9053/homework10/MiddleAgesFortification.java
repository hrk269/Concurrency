package edu.nyu.cs9053.homework10;

import java.util.concurrent.CountDownLatch;

/**
 * User: blangel
 */
public class MiddleAgesFortification implements Fortification<Thread>, ConcurrencyFactorProvider {

    private final int concurrencyFactor;

    public MiddleAgesFortification(int concurrencyFactor){
        this.concurrencyFactor = concurrencyFactor;
    }

    @Override
    public void handleAttack(AttackHandler handler) {
        CountDownLatch countDownLatch = new CountDownLatch(this.concurrencyFactor);
        while (countDownLatch.getCount() > 0){
            countDownLatch.countDown();
            handler.soldiersReady();
        }
        try{
            countDownLatch.await();
        }
        catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void surrender() {
        Thread.currentThread().interrupt();
    }

    @Override
    public int getConcurrencyFactor() {
        return this.concurrencyFactor;
    }
}