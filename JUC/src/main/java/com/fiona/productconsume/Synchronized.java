package com.fiona.productconsume;


public class Synchronized {
    public static void main(String[] args) {

        Data data = new Data();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();

    }
}

//降低耦合，只有属性+方法
class Data{
   private int total = 0;

   //+1
   public synchronized void increment() throws InterruptedException {
       if(total!= 0){
           this.wait();
       }
       total++;
       System.out.println(Thread.currentThread().getName() + " = > " + total);
       this.notifyAll();
   }
    //-1
    public synchronized void decrement() throws InterruptedException {

       if(total==0){
           this.wait();
       }
       total--;
       this.notifyAll();
        System.out.println(Thread.currentThread().getName() + " = > " + total);
    }

}