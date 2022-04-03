// PKmon.java
// Mateusz Sliwa, 01.04.2022

class Producent extends Thread {
    private final Bufor _buf;

    public Producent(Bufor b) {
        _buf = b;
    }

    public void run() {
        for (int i = 0; i < 100; i++) {
            try { sleep(50); }
            catch (Exception e) { System.out.println(e); }
            _buf.put(i);
        }
    }
}

class Konsument extends Thread {
    private final Bufor _buf;

    public Konsument(Bufor b) {
        _buf = b;
    }

    public void run() {
        for (int i = 0; i < 100; i++) {
            try { sleep(50); }
            catch (Exception e) { System.out.println(e); }
            _buf.get();
        }
    }
}

class Bufor {
    private int zawartoscBufora;
    private boolean czyDostepny = false;

    public synchronized void put(int wartosc) {
        while (czyDostepny) {
            try { wait(); }
            catch (Exception e) { System.out.println(e); }
        }
        System.out.println("Producent " + (wartosc+1));
        zawartoscBufora = wartosc;
        czyDostepny = true;
        notify();
    }

    public synchronized void get() {
        while (!czyDostepny) {
            try { wait(); }
            catch (Exception e) { System.out.println(e); }
        }
        System.out.println("Konsument " + (zawartoscBufora+1) + "\n");
        czyDostepny = false;
        notify();
    }
}

public class lab3 {
    public static void main(String[] args) {

        Bufor b = new Bufor();
        Producent p = new Producent(b);
        Konsument k = new Konsument(b);

        p.start();
        k.start();
    }
}