package com.fiona;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class  ReadWriteLock {
    public static void main(String[] args) {
        LockCache myCache = new LockCache();
        //读取
        for (int i = 1; i < 10; i++) {
            final int temp = i;
            new Thread(()->{
                myCache.put(temp+"", temp);
            },String.valueOf(i)).start();
        }
        //写入
        for (int i = 1; i < 10; i++) {
            final int temp = i;
            new Thread(()->{
                myCache.get(temp+"");
            },String.valueOf(i)).start();
        }
    }
}

/**r
加锁缓存
 */
class LockCache{
    private ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();

    private volatile Map<String, Object> map = new HashMap<>();
    //存 （写）写只希望一个线程操作
    public void put(String key, Object value){
        rwlock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "写入" + key);
            map.put(key,value);
            System.out.println(Thread.currentThread().getName() + "写入OK" );
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwlock.writeLock().unlock();
        }
    }

    //取 （读）可以多个写成操作
    public void get(String key){

        rwlock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "读取" + key);
            Object o = map.get(key);
            System.out.println(Thread.currentThread().getName() + "读取OK" );
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwlock.readLock().unlock();
        }
    }
}



/**
自定义缓存
 */
class MyCache{

    private volatile Map<String, Object> map = new HashMap<>();
    //存 （写）
    public void put(String key, Object value){
        System.out.println(Thread.currentThread().getName() + "写入" + key);
        map.put(key,value);
        System.out.println(Thread.currentThread().getName() + "写入OK" );
    }

    //取 （读）
    public void get(String key){
        System.out.println(Thread.currentThread().getName() + "读取" + key);
        Object o = map.get(key);
        System.out.println(Thread.currentThread().getName() + "读取OK" );
    }
}