package com.fiona;



public class SalesTicketSync {

    public static void main(String[] args) {
        final Ticket ticket = new Ticket();
        new Thread(()->{
            for (int i = 0; i < 20; i++) {
                ticket.sale();
            }
        }, "A").start();
        new Thread(()->{
            for (int i = 0; i < 20; i++) {
                ticket.sale();
            }}, "B").start();
        new Thread(()->{
            for (int i = 0; i < 20; i++) {
                ticket.sale();
            }
        }, "C").start();

    }
}

//OOP思想d
class Ticket{
    private int number = 50;

    public synchronized void sale(){
        if(number >0){
            System.out.println(Thread.currentThread().getName() + "卖出了" + number-- + " 票，剩余 " + number );
        }
    }

}