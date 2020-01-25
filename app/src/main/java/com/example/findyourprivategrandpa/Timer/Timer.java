package com.example.findyourprivategrandpa.Timer;

public abstract class Timer extends Thread
{
    private long delay;
    public Timer(long delay)
    {
        this.delay=delay;
    }
    @Override
    public void run()
    {
        super.run();
        try
        {
            Thread.sleep(delay);
            onTimeUp();
        }
        catch (InterruptedException e)
        {
            onInterrupt();
        }
    }
    public abstract void onTimeUp();
    public abstract void  onInterrupt();
}