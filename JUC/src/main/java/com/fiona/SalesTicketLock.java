package com.fiona;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SalesTicketLock {


    public static void main(String[] args) {
        final Ticket1 ticket = new Ticket1();
        new Thread(() -> {
            for (int i = 0; i < 20; i++) ticket.sale();
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                ticket.sale();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                ticket.sale();
            }
        }, "C").start();

    }
}

//OOP思想
//Lock 锁
class Ticket1{
    private int number = 30;

    Lock lock = new ReentrantLock();

    public void sale(){
          lock.lock();
        try {
            if(number >0){
                System.out.println(Thread.currentThread().getName() + "卖出了" + number-- + " 票，剩余 " + number );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}

