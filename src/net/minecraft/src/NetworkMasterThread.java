package src.net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


class NetworkMasterThread extends Thread
{

    NetworkMasterThread(NetworkManager networkmanager)
    {
        netManager = networkmanager;
    }

    public void run()
    {
        try
        {
            Thread.sleep(5000L);
            if(NetworkManager.getReadThread(netManager).isAlive())
            {
                try
                {
                    NetworkManager.getReadThread(netManager).stop();
                }
                catch(Throwable throwable) { }
            }
            if(NetworkManager.getWriteThread(netManager).isAlive())
            {
                try
                {
                    NetworkManager.getWriteThread(netManager).stop();
                }
                catch(Throwable throwable1) { }
            }
        }
        catch(InterruptedException interruptedexception)
        {
            interruptedexception.printStackTrace();
        }
    }

    final NetworkManager netManager; /* synthetic field */
}
