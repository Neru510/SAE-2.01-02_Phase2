package fr.umontpellier.iut.graphes;

import org.glassfish.grizzly.utils.ArraySet;

import java.util.*;

/**
 * Graphe simple non-orienté pondéré représentant le plateau du jeu.
 * Pour simplifier, on supposera que le graphe sans sommets est le graphe vide.
 * Le poids de chaque sommet correspond au coût de pose d'un rail sur la tuile correspondante.
 * Les sommets sont indexés par des entiers (pas nécessairement consécutifs).
 */

public class Graphe {
    private final Set<Sommet> sommets;

    public Graphe(Set<Sommet> sommets) {
        this.sommets = sommets;
    }

    /**
     * Construit un graphe à n sommets 0..n-1 sans arêtes
     */
    public Graphe(int n) {
        sommets = new HashSet<>();
        for (int i = 0; i < n; i++){
            Sommet a = new Sommet.SommetBuilder().setIndice(i).setJoueurs(null).setSurcout(0).setNbPointsVictoire(0).createSommet();
            sommets.add(a);
        }
    }

    public String toStringArete(){
        String s = "";
        Set<Set<Sommet>> aretes = getAretes();
        for (Set<Sommet> sommets : aretes){
            for (Sommet sommet : sommets){
                s += sommet.getIndice();
                s += ",";
            }
            s += ";\n";

        }
        return s;
    }

    /**
     * Construit un graphe vide
     */
    public Graphe() {
        this.sommets = new HashSet<>();
    }
/*
    public Graphe(Graphe graphe){
        this.sommets = new HashSet<>();
        for (Sommet a : graphe.sommets){
            Sommet s = new Sommet.SommetBuilder().setIndice(a.getIndice()).setJoueurs(a.getJoueurs()).setSurcout(a.getSurcout()).setNbPointsVictoire(a.getNbPointsVictoire()).createSommet();
            sommets.add(s);
        }
    }
*/
    /**
     * Construit un sous-graphe induit par un ensemble de sommets
     * sans modifier le graphe donné
     *
     * @param g le graphe à partir duquel on construit le sous-graphe
     * @param X les sommets à considérer (on peut supposer que X est inclus dans l'ensemble des sommets de g,
     *          même si en principe ce n'est pas obligatoire)
     */
    public Graphe(Graphe g, Set<Sommet> X) {

       /*
        Graphe res = new Graphe();
        Set<Sommet> SommetDeg = g.sommets;
        Set<Sommet> SommetDeX = new HashSet<>();
        SommetDeX.addAll(X);


        for (int i = 0 ; i < g.getNbSommets(); i++){

            g.sommets.
            if (!g.sommets.contains(X.))
            res.ajouterSommet(new Sommet(getSommet(i)));
            //Sommet sommetDeG = getSommet(i);
            //Set<Sommet> voisinsDuSommetDeG = sommetDeG.getVoisins();

            }
            Sommet temp = X.iterator().next();
            if (g.getSommets().contains(temp)){

            }
*/


        throw new RuntimeException("Méthode à implémenter");
    }

    /**
     * @return true si et seulement si la séquence d'entiers passée en paramètre
     * correspond à un graphe simple valide dont les degrés correspondent aux éléments de la liste.
     * Pré-requis : on peut supposer que la séquence est triée dans l'ordre croissant.
     */
    public static boolean sequenceEstGraphe(List<Integer> sequence) {
        throw new RuntimeException("Méthode à implémenter");
    }

    /**
     * @param g        le graphe source, qui ne doit pas être modifié
     * @param ensemble un ensemble de sommets
     *                 pré-requis : l'ensemble donné est inclus dans l'ensemble des sommets de {@code g}
     * @return un nouveau graph obtenu en fusionnant les sommets de l'ensemble donné.
     * On remplacera l'ensemble de sommets par un seul sommet qui aura comme indice
     * le minimum des indices des sommets de l'ensemble. Le surcout du nouveau sommet sera
     * la somme des surcouts des sommets fusionnés. Le nombre de points de victoire du nouveau sommet
     * sera la somme des nombres de points de victoire des sommets fusionnés.
     * L'ensemble de joueurs du nouveau sommet sera l'union des ensembles de joueurs des sommets fusionnés.
     */
    public static Graphe fusionnerEnsembleSommets(Graphe g, Set<Sommet> ensemble) {
        throw new RuntimeException("Méthode à implémenter");
    }

    /**
     * @param i un entier
     * @return le sommet d'indice {@code i} dans le graphe ou null si le sommet d'indice {@code i} n'existe pas dans this
     */
    public Sommet getSommet(int i) {
        for (Sommet s : sommets) {
            if (s.getIndice() == i) {
                return s;
            }
        }
        return null;
    }

    /**
     * @return l'ensemble des sommets du graphe
     */
    public Set<Sommet> getSommets() {
        return sommets;
    }

    /**
     * @return l'ordre du graphe, c'est-à-dire le nombre de sommets
     */
    public int getNbSommets() {
        return sommets.size();
    }

    /**
     * @return l'ensemble d'arêtes du graphe sous forme d'ensemble de paires de sommets
     */
    public Set<Set<Sommet>> getAretes() {
        Set<Set<Sommet>> aretes = new HashSet<>();
        for (Sommet s : sommets){
            Set<Sommet> voisins = s.getVoisins();
            for (Sommet voisin : voisins){
                Set<Sommet> arr = new HashSet<>();
                arr.add(s);
                arr.add(voisin);
                aretes.add(arr);
            }
        }

        return aretes;
    }

    /**
     * @return le nombre d'arêtes du graphe
     */
    public int getNbAretes() {
        Set<Set<Sommet>> aretes = new HashSet<>();
        for (Sommet s : sommets){
            Set<Sommet> voisins = s.getVoisins();
            for (Sommet voisin : voisins){
                Set<Sommet> arete = new HashSet<>();
                arete.add(s);
                arete.add(voisin);
                aretes.add(arete);
                arete.add(voisin);
                arete.add(s);
                aretes.add(arete);
            }

        }
        return aretes.size();
    }

    /**
     * Ajoute un sommet d'indice i au graphe s'il n'est pas déjà présent
     *
     * @param i l'entier correspondant à l'indice du sommet à ajouter dans le graphe
     */
    public boolean ajouterSommet(int i) {
        Sommet sommet = new Sommet.SommetBuilder().setIndice(i).setJoueurs(null).setSurcout(0).setNbPointsVictoire(0).createSommet();
        if (!sommets.contains(sommet)){
            sommets.add(sommet);
            return true;
        }
        return false;
    }

    /**
     * Ajoute un sommet au graphe s'il n'est pas déjà présent
     *
     * @param s le sommet à ajouter
     * @return true si le sommet a été ajouté, false sinon
     */
    public boolean ajouterSommet(Sommet s) {
        return sommets.add(s);
    }

    /**
     * @param s le sommet dont on veut connaître le degré
     *          pré-requis : {@code s} est un sommet de this
     * @return le degré du sommet {@code s}
     */
    public int degre(Sommet s) {
        throw new RuntimeException("Méthode à implémenter");
    }

    /**
     * @return true si et seulement si this est complet.
     */
    public boolean estComplet() {
        int  ordre = this.sommets.size();
        int tailleMax = ordre*(ordre-1)/2;
        return ordre == tailleMax;
    }

    /**
     * @return true si et seulement si this est une chaîne. On considère que le graphe vide est une chaîne.
     */
    public boolean estChaine() {
        if (sommets.size() < 2) return true;
        Sommet a = null;
        boolean check = false;
        for (Sommet s : sommets){
            Set<Sommet> sommetSet = s.getVoisins();
            if (sommetSet.size() > 2){
                return false;
            }
            else if (sommetSet.size() == 1){
                if (a == null) a = s; // 1er sommet de degré 1
                else if (!check) check = true; // 2d sommet de degré 1
                else return false; // s'il y a 3 sommets de degré 1
            }
        }
        if (a == null || !check){ // s'il n'y a pas de sommet de degré 1 OU s'il y a seulement 1 sommet de degré 1
            return false;
        }
        // le graphe a donc 2 sommets de degré 1 et que des sommets de degré 2
        Set<Sommet> sommetDejaVisite = new HashSet<>();
        Sommet generique = a;
        Sommet avantGuardiste = null;
        check = false;
        while (!check){
            sommetDejaVisite.add(generique);
            Set<Sommet> voisins = generique.getVoisins();
            for (Sommet s : voisins){
                if (!sommetDejaVisite.contains(s)){
                    avantGuardiste = generique;
                    generique = s;
                }
                else if (!a.equals(s) && voisins.size() == 1){
                    check = true;
                }
                else if (avantGuardiste != null && !avantGuardiste.equals(s)){
                    return false;
                }
            }
        }
        return sommetDejaVisite.size() == sommets.size();
    }

    /**
     * @return true si et seulement si this est un cycle. On considère que le graphe vide n'est pas un cycle.
     */
    public boolean estCycle() {
        if (sommets.size() < 3) return false;
        if (!estConnexe()) return false;
        boolean pasCycle = true;
        Set<Sommet> sommet = new HashSet<>(sommets);
        for (Sommet res : sommet){
            if (res.getVoisins().size() != 2){
                return false;
            }
        }
        return true;
    }

    /**
     * @return true si et seulement si this est une forêt. On considère qu'un arbre est une forêt
     * et que le graphe vide est un arbre.
     */
    public boolean estForet() {
        throw new RuntimeException("Méthode à implémenter");
    }

    /**
     * @return true si et seulement si this a au moins un cycle. On considère que le graphe vide n'est pas un cycle.
     */
    public boolean possedeUnCycle() {
        throw new RuntimeException("Méthode à implémenter");
    }

    /**
     * @return true si et seulement si this a un isthme
     */
    public boolean possedeUnIsthme() {
        throw new RuntimeException("Méthode à implémenter");
    }

    public void ajouterArete(Sommet s, Sommet t) {
        s.ajouterVoisin(t);
        t.ajouterVoisin(s);
    }

    public void supprimerArete(Sommet s, Sommet t) {
        s.getVoisins().remove(t);
        t.getVoisins().remove(s);
    }

    /**
     * @return une coloration gloutonne du graphe sous forme d'une Map d'ensemble indépendants de sommets.
     * L'ordre de coloration des sommets est suivant l'ordre décroissant des degrés des sommets
     * (si deux sommets ont le même degré, alors on les ordonne par indice croissant).
     */
    public Map<Integer, Set<Sommet>> getColorationGloutonne() {
        throw new RuntimeException("Méthode à implémenter");
    }

    /**
     * @param depart  - ensemble non-vide de sommets
     * @param arrivee
     * @return le surcout total minimal du parcours entre l'ensemble de depart et le sommet d'arrivée
     * pré-requis : l'ensemble de départ et le sommet d'arrivée sont inclus dans l'ensemble des sommets de this
     */
    public int getDistance(Set<Sommet> depart, Sommet arrivee) {
        throw new RuntimeException("Méthode à implémenter");
    }

    /**
     * @return le surcout total minimal du parcours entre le sommet de depart et le sommet d'arrivée
     */
    public int getDistance(Sommet depart, Sommet arrivee) {
        throw new RuntimeException("Méthode à implémenter");
    }

    /**
     * @return l'ensemble des classes de connexité du graphe sous forme d'un ensemble d'ensembles de sommets.
     */
    public Set<Set<Sommet>> getEnsembleClassesConnexite() {
        Set<Set<Sommet>> ensembleClassesConnexite = new HashSet<>();
        if (sommets.isEmpty())
            return ensembleClassesConnexite;
        Set<Sommet> sommets = new HashSet<>(this.sommets);
        while (!sommets.isEmpty()) {
            Sommet v = sommets.iterator().next();
            Set<Sommet> classe = getClasseConnexite(v);
            sommets.removeAll(classe);
            ensembleClassesConnexite.add(classe);
        }
        return ensembleClassesConnexite;
    }

    /**
     * @param v un sommet du graphe this
     * @return la classe de connexité du sommet {@code v} sous forme d'un ensemble de sommets.
     */
    public Set<Sommet> getClasseConnexite(Sommet v) {
        if (!sommets.contains(v))
            return new HashSet<>();
        Set<Sommet> classe = new HashSet<>();
        calculerClasseConnexite(v, classe);
        return classe;
    }

    private void calculerClasseConnexite(Sommet v, Set<Sommet> dejaVus) {
        dejaVus.add(v);
        Set<Sommet> voisins = v.getVoisins();

        for (Sommet voisin : voisins) {
            if (dejaVus.add(voisin))
                calculerClasseConnexite(voisin, dejaVus);
        }
    }

    /**
     * @return true si et seulement si this est connexe.
     */
    public boolean estConnexe() {
        throw new RuntimeException("Méthode à implémenter");
    }

    /**
     * @return le degré maximum des sommets du graphe
     */
    public int degreMax() {
        throw new RuntimeException("Méthode à implémenter");
    }

    /**
     * @return une coloration propre optimale du graphe sous forme d'une Map d'ensemble indépendants de sommets.
     * Chaque classe de couleur est représentée par un entier (la clé de la Map).
     * Pré-requis : le graphe est issu du plateau du jeu Train (entre autres, il est planaire).
     */
    public Map<Integer, Set<Sommet>> getColorationPropreOptimale() {
        throw new RuntimeException("Méthode à implémenter");
    }

    /**
     * @return true si et seulement si this possède un sous-graphe complet d'ordre {@code k}
     */
    public boolean possedeSousGrapheComplet(int k) {
        throw new RuntimeException("Méthode à implémenter");
    }

    /**
     * @param g un graphe
     * @return true si et seulement si this possède un sous-graphe isomorphe à {@code g}
     */
    public boolean possedeSousGrapheIsomorphe(Graphe g) {
        throw new RuntimeException("Méthode à implémenter");
    }

    /**
     * @param s
     * @param t
     * @return un ensemble de sommets qui forme un ensemble critique de plus petite taille entre {@code s} et {@code t}
     */
    public Set<Sommet> getEnsembleCritique(Sommet s, Sommet t){
        throw new RuntimeException("Méthode à implémenter");
    }
}
