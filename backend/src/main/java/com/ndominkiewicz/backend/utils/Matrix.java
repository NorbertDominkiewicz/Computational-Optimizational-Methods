package com.ndominkiewicz.backend.utils;

public class Matrix {
    public double[][]tab;
    double[]wyniki;
    int wiersze;
    int kolumny;
    public Matrix(double[][] tab){
        this.tab = tab;
        wiersze = tab.length;
        kolumny = tab[0].length;
    }
    Matrix(int size){
        wiersze = size;
        kolumny = size +1;
        tab = new double[wiersze][kolumny];
    }
    public void reverse() {
        if (wiersze != kolumny) {
            throw new IllegalStateException("Macierz musi być kwadratowa");
        }

        if (wiersze == 2) {
            double a = tab[0][0], b = tab[0][1];
            double c = tab[1][0], d = tab[1][1];

            double determinant = a * d - b * c;
            if (determinant == 0) {
                throw new ArithmeticException("Macierz nieodwracalna - wyznacznik 0");
            }

            double invDet = 1.0 / determinant;
            tab[0][0] = d * invDet;
            tab[0][1] = -b * invDet;
            tab[1][0] = -c * invDet;
            tab[1][1] = a * invDet;
        } else {
            throw new UnsupportedOperationException("Tylko dla macierzy 2x2");
        }
    }
    public void writeOut() {
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[0].length; j++) {
                System.out.print(" " + tab[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void setWartosc(int i, int j, double value) {
        tab[i][j] = value;
    }

    public void setWartosc(Matrix m, int i, int j, double value) {
        m.tab[i][j] = value;
    }

    public void oblicz(){
        zerowanie();
        rownanie();
    }

    public Matrix zerowanie(){
        double[][] tab2 = new double[tab.length][tab[0].length];
        for (int i = 0; i < wiersze; i++) {
            for (int j = 0; j < kolumny; j++) {
                tab2[i][j] = tab[i][j];
            }
        }
        Matrix wynikowa = new Matrix(tab2);
        int k=0;
        while(k<wiersze-1){
            double shrWartosc;
            for (int i = k+1; i < wiersze; i++) {
                shrWartosc = wynikowa.tab[i][k];
                for (int j = k; j < kolumny; j++) {
                    if(i==1){
                        setWartosc(wynikowa,0, j, wynikowa.tab[0][j]);
                    }
                    double wynik = wynikowa.tab[i][j]-(wynikowa.tab[k][j])*shrWartosc/wynikowa.tab[k][k];
                    setWartosc(wynikowa,i, j, wynik);
                }
            }
            this.tab = wynikowa.tab;
            k++;
        }
        return wynikowa;
    }

    private void rownanie() {
        wyniki = new double[wiersze];
        for (int i = wiersze - 1; i >= 0; i--) {
            double suma = tab[i][kolumny - 1];
            for (int j = i + 1; j < kolumny - 1; j++) {
                suma -= tab[i][j] * wyniki[j];
            }
            try{
                if (tab[i][i] == 0) throw new ArithmeticException("Dzielenie przez zero!");
                wyniki[i] = suma / tab[i][i];
            } catch (ArithmeticException e) {
                //System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                if (j == 0) {
                    result.append("|");
                }
                result.append(" " + tab[i][j] + " ");
                if (j == tab.length) {
                    result.append("|");
                }
            }
            result.append("\n");
        }
        return result.toString();
    }

    public static void main(String[] args) {
    }

}
