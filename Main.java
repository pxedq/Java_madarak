import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
    public class Madar {
        public String mNev;
        public String lNev;
        public int aSuly;
        public int aMagassag;
        public int aRepulesiTavolsag;

        public Madar(String sor) {
            String[] s = sor.split(";");
            mNev = s[0];
            lNev = s[1];
            aSuly = Integer.parseInt(s[2]);
            aMagassag = Integer.parseInt(s[3]);
            aRepulesiTavolsag = Integer.parseInt(s[4]);
        }
    }

    private ArrayList<Madar> madarak = new ArrayList<>();

    public Main() {
        // --- 1. feladat ---
        betolt("madarak.csv");
        System.out.printf("1) A madarak.csv fájlból %d madár adata beolvasva\n", madarak.size());

        // --- 2. feladat ---
        Madar legmesszebbRepulo = madarak.getFirst();
        for (Madar m : madarak) {
            if (m.aRepulesiTavolsag > legmesszebbRepulo.aRepulesiTavolsag) {
                legmesszebbRepulo = m;
            }
        }
        System.out.printf("2) Legmesszebb (%d km) repül: %s\n", legmesszebbRepulo.aRepulesiTavolsag, legmesszebbRepulo.mNev);

        // --- 3. feladat ---
        ArrayList<Madar> szazGrammAlattiak = new ArrayList<>();
        for (Madar m : madarak) {
            if (m.aSuly < 100) szazGrammAlattiak.add(m);
        }

        double sumSZGA = 0;
        for (Madar m : szazGrammAlattiak) {
            sumSZGA += m.aRepulesiTavolsag;
        }
        System.out.printf("3) A 100g alatti madarak (%d darab) átlagos repülési távolsága: %.2f km\n", szazGrammAlattiak.size(), sumSZGA/szazGrammAlattiak.size());

        // --- 4. feladat ---
        ArrayList<String> egyNevuek = new ArrayList<>();
        for (Madar m : madarak) {
            if (!m.mNev.contains(" ")) egyNevuek.add(m.mNev);
        }

        double randomEgynevu = Math.floor(Math.random() * egyNevuek.size());
        System.out.printf("4) Véletlen választott egyetlen szóból álló magyar nevű madár: %s\n", egyNevuek.get((int) randomEgynevu));

        // --- 5. feladat ---
        System.out.println("5) A latin név két egyforma szóból áll:");
        for (Madar m : madarak) {
            String[] latinNev = m.lNev.split(" ");
            if (latinNev.length >= 2 && latinNev[0].toLowerCase().equals(latinNev[1].toLowerCase())) {
                System.out.printf("   - %s (%s)\n", m.mNev, m.lNev);
            }
        }

        // --- 6. feladat ---
        TreeMap<Integer, Integer> magassagok = new TreeMap<>();
        for (Madar m : madarak) {
            if (!magassagok.containsKey(m.aMagassag)) {
                magassagok.put(m.aMagassag, 1);
            } else {
                int eddig = magassagok.get(m.aMagassag);
                magassagok.put(m.aMagassag, eddig+1);
            }
        }
        System.out.print("6) Magasságok: ");
        ArrayList<String> ertekek = new ArrayList<>();
        for (Integer magas : magassagok.keySet()) {
            if (magassagok.get(magas) > 1) {
                ertekek.add(String.format("%d cm (%d)", magas, magassagok.get(magas)));
            }
        }
        System.out.printf("%s\n", String.join(", ", ertekek));

        // --- 7. feladat ---
        int fecskekDb = 0;
        for (Madar m : madarak) {
            if (m.mNev.contains("fecske")) fecskekDb++;
        }
        System.out.printf("7) Összesen %d féle fecske található az adatok között\n", fecskekDb);

        // --- 8. feladat ---
        PrintWriter ki = null;
        try {
            ki = new PrintWriter(new File("nagyok.txt"), "UTF-8");
            for (Madar m : madarak) {
                if (m.aSuly > 500) {
                    ki.printf("%s (%s): %d g\n", m.mNev, m.lNev, m.aSuly);
                }
            }
            System.out.print("8) A nagy madarak adatai a nagyok.txt fájlba elmentve");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (ki != null) ki.close();
        }

    }

    private void betolt(String fajlnev) {
        Scanner be = null;
        try {
            be = new Scanner(new File(fajlnev), "UTF-8");
            be.nextLine();
            while (be.hasNextLine()) {
                madarak.add(new Madar(be.nextLine()));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (be != null) be.close();
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}