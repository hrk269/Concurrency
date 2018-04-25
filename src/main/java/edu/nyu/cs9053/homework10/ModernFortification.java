package edu.nyu.cs9053.homework10;

import java.util.concurrent.*;

/**
 * User: blangel
 */
public class ModernFortification implements Fortification<ExecutorService>, ConcurrencyFactorProvider {
    private final int concurrencyFactor;

    private final ExecutorService service;

    public ModernFortification(int concurrencyFactor){
        this.concurrencyFactor = concurrencyFactor;
        this.service = new ThreadPoolExecutor(this.concurrencyFactor, this.concurrencyFactor, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
    }

    @Override
    public void handleAttack(AttackHandler handler) {
        service.submit(new Runnable() {
            @Override
            public void run() {
                handler.soldiersReady();
            }
        });
    }

    @Override
    public void surrender(){
        this.service.shutdown();
    }

    @Override
    public int getConcurrencyFactor() {
        return concurrencyFactor;
    }
}
