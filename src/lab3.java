// PKmon.java
// Mateusz Sliwa, 01.04.2022

class Producent extends Thread {
    private final Bufor _buf;

    public Producent(Bufor b) {
        _buf = b;
    }     // inicjalizacja bufora

    public void run() {
        for (int i = 1; i < 101; i++) {         // petla od 0 do 100
            try { sleep(50); }
            catch (Exception e) { System.out.println(e); }
            _buf.put(i);                        // utworzenie elementu i , start od 0
        }
    }
}

class Konsument extends Thread {
    private final Bufor _buf;

    public Konsument(Bufor b) { _buf = b; }     // inicjalizacja bufora

    public void run() {
        for (int i = 1; i < 101; i++) {         // petla od 0 do 100
            try { sleep(50); }
            catch (Exception e) { System.out.println(e); }
            _buf.get();                         // pobranie elementu i, start od 0
        }
    }
}

class Bufor {
    private int zawartoscBufora;
    private boolean czyDostepny = false;

    public synchronized void put(int wartosc) {
        while (czyDostepny) {                           // warunek oczekiwania
            try { wait(); }
            catch (Exception e) { System.out.println(e); }
        }
        System.out.println("Producent " + (wartosc));   // wyswietl aktualna wartosc
        zawartoscBufora = wartosc;                      // podstaw wartosc pod zawartoscBufora
        czyDostepny = true;                             // zmien wartosc dostepnosci bufora na true
        notify();                                       // obudz uspiony watek
    }

    public synchronized void get() {
        while (!czyDostepny) {                          // warunek oczekiwania
            try { wait(); }
            catch (Exception e) { System.out.println(e); }
        }
        System.out.println("Konsument " + (zawartoscBufora) + "\n");    // wyswietl zawartosc bufora
        czyDostepny = false;                            // zmien wartosc dostepnosci bufora na false
        notify();                                       // obudz uspiony watek
    }
}

public class lab3 {
    public static void main(String[] args) {

        // utworzenie obiektow
        Bufor b = new Bufor();
        Producent p = new Producent(b);
        Konsument k = new Konsument(b);

        // start watkow
        p.start();
        k.start();
    }
}