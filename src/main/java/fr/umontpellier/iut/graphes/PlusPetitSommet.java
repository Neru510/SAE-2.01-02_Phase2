package fr.umontpellier.iut.graphes;

import java.util.Comparator;

public class PlusPetitSommet implements Comparator<Sommet> {
    @Override
    public int compare(Sommet sommet, Sommet t1) {
        return sommet.getIndice() - t1.getIndice();
    }

}
