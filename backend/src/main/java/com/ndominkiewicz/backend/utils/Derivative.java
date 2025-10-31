package com.ndominkiewicz.backend.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Derivative {
    public static String obliczPochodna(String funkcja) {
        // Najpierw uprość ułamki
        funkcja = uproscUlamki(funkcja);

        // Reszta pozostaje bez zmian
        funkcja = funkcja.replaceAll("\\s+", "").toLowerCase();
        String[] wyrazy = funkcja.split("(?=[+-])");

        List<String> pochodne = new ArrayList<>();

        for (String wyraz : wyrazy) {
            if (wyraz.isEmpty()) continue;
            String pochodnaWyrazu = obliczPochodnaWyrazu(wyraz);
            if (!pochodnaWyrazu.equals("0")) {
                pochodne.add(pochodnaWyrazu);
            }
        }

        return pochodne.isEmpty() ? "0" : String.join("", pochodne);
    }

    private static String uproscUlamki(String funkcja) {
        // Proste zastąpienie ułamków zwykłych dziesiętnymi
        Map<String, String> ulamki = Map.of(
                "1/2", "0.5",
                "1/3", "0.333",
                "1/4", "0.25",
                "1/5", "0.2",
                "2/3", "0.667"
        );

        String result = funkcja;
        for (Map.Entry<String, String> entry : ulamki.entrySet()) {
            result = result.replace(entry.getKey(), entry.getValue());
        }
        return result;
    }

    private static String obliczPochodnaWyrazu(String wyraz) {
        // Obsługa stałych
        if (!wyraz.contains("x")) {
            if (wyraz.equals("+") || wyraz.equals("-")) {
                return "0";
            }
            return "0";
        }

        // Współczynnik
        double wspolczynnik = 1.0;
        String czescNumeryczna = wyraz.split("x")[0];

        if (!czescNumeryczna.isEmpty() && !czescNumeryczna.equals("+") && !czescNumeryczna.equals("-")) {
            if (czescNumeryczna.equals("+") || czescNumeryczna.equals("-")) {
                czescNumeryczna += "1";
            }
            wspolczynnik = Double.parseDouble(czescNumeryczna);
        } else if (czescNumeryczna.equals("-")) {
            wspolczynnik = -1.0;
        }

        // Wykładnik
        int wykladnik = 1;
        if (wyraz.contains("^")) {
            String[] parts = wyraz.split("\\^");
            wykladnik = Integer.parseInt(parts[1]);
        }

        // Obliczanie pochodnej
        if (wykladnik == 1) {
            return formatujWspolczynnik(wspolczynnik);
        } else {
            double nowyWspolczynnik = wspolczynnik * wykladnik;
            int nowyWykladnik = wykladnik - 1;

            if (nowyWykladnik == 1) {
                return formatujWspolczynnik(nowyWspolczynnik) + "x";
            } else {
                return formatujWspolczynnik(nowyWspolczynnik) + "x^" + nowyWykladnik;
            }
        }
    }

    private static String formatujWspolczynnik(double wspolczynnik) {
        if (wspolczynnik == 1.0) {
            return "+";
        } else if (wspolczynnik == -1.0) {
            return "-";
        } else if (wspolczynnik > 0) {
            return "+" + (wspolczynnik == (int)wspolczynnik ?
                    String.valueOf((int)wspolczynnik) : String.valueOf(wspolczynnik));
        } else {
            return (wspolczynnik == (int)wspolczynnik ?
                    String.valueOf((int)wspolczynnik) : String.valueOf(wspolczynnik));
        }
    }
}
