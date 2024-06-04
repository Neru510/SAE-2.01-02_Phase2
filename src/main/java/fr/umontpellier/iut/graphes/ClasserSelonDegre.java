package fr.umontpellier.iut.graphes;

import java.util.Comparator;

public class ClasserSelonDegre implements Comparator<Sommet> {

    @Override
    public int compare(Sommet sommet, Sommet t1) {
        return t1.getVoisins().size() - sommet.getVoisins().size();
    }
}
