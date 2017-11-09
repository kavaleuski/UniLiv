public class Copy {

public static void main(String[] args) {

    Pencil p1 = new Pencil();
    Pencil p2 = new Pencil();
    Pencil p3 = new Pencil();
    Pencil p4 = new Pencil();

    Eidograph e1 = new Eidograph();
    Eidograph e2 = new Eidograph();
    Eidograph e3 = new Eidograph();
    Eidograph e4 = new Eidograph();

    Copyist Copyist0 = new Copyist(0, e1, p1);
    Copyist Copyist1 = new Copyist(1, e1, p2);
    Copyist Copyist2 = new Copyist(2, e2, p2);
    Copyist Copyist3 = new Copyist(3, e2, p3);
    Copyist Copyist4 = new Copyist(4, e3, p3);
    Copyist Copyist5 = new Copyist(5, e3, p4);
    Copyist Copyist6 = new Copyist(6, e4, p4);
    Copyist Copyist7 = new Copyist(7, e4, p1);

    Copyist0.start();
    Copyist1.start();
    Copyist2.start();
    Copyist3.start();
    Copyist4.start();
    Copyist5.start();
    Copyist6.start();
    Copyist7.start();

    }
}


class Copyist extends Thread {

    private int copyistIndex;
    private Pencil p0;
    private Eidograph e0;
    private int numberToProduce = 5;
    private int numberProduced = 0;
    private int TimeToCheck = 50;
    private int TimetoDraw = 50;
    private boolean active = true;

    public Copyist(int c, Eidograph e, Pencil p) {
        copyistIndex = c;
        e0 = e;
        p0 = p;
    }

    public void run() {
        while(active){
            try{
                p0.pickUp(copyistIndex);
                e0.pickUp(copyistIndex);
                System.out.println("Copyist " + copyistIndex + " is making a drawing ");
                sleep(100);
            }
            catch(InterruptedException e) {}
            numberProduced++;
            System.out.println("Copyist" + copyistIndex +" has finished copy "  + numberToProduce );
            p0.free(copyistIndex);
            e0.free(copyistIndex);
            try{
                System.out.println("Copyist " + copyistIndex + " is checking diagram " + numberToProduce);
                sleep(100);
            }
            catch(InterruptedException e) {}
            if (numberToProduce == numberProduced) {
                System.out.println("Copyist" + copyistIndex + " has finished work and gone to pub" );
                stopCopying();
            }
        }
    }
    public void stopCopying() {active = false;}
}



class Eidograph extends Thread {

    private  boolean eStatus = true;

    public Eidograph(){
    }

    public synchronized void free(int copyistIndex) {
        eStatus = true;
        notifyAll();
    }

    public synchronized void pickUp(int copyistIndex){
        while(eStatus = false){
            try{
                wait();
                System.out.println("Copyist " + copyistIndex + "is waiting for eidograph");
            }
            catch (InterruptedException e) {}

        }
        eStatus = true;
        System.out.println("Copyist " + copyistIndex + " is using eidograph ");
        notifyAll();
    }
}

class Pencil extends Thread {

    private  boolean pStatus = true;

    public Pencil(){
    }

    public synchronized void free(int copyistIndex) {
        pStatus = true;
        notifyAll();
    }

    public synchronized void pickUp(int copyistIndex){
        while(pStatus = false){
            try{
                wait();
                System.out.println("Copyist " + copyistIndex + "is waiting for pencil");
            }
            catch (InterruptedException e) {}
        }
        pStatus = true;
        System.out.println("Copyist " + copyistIndex + " is using pencil ");
        notifyAll();
    }
}
