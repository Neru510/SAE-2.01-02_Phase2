package fr.umontpellier.iut.graphes;

import java.util.*;

/**
 * Graphe simple non-orienté pondéré représentant le plateau du jeu.
 * Pour simplifier, on supposera que le graphe sans sommets est le graphe vide.
 * Le poids de chaque sommet correspond au coût de pose d'un rail sur la tuile correspondante.
 * Les sommets sont indexés par des entiers (pas nécessairement consécutifs).
 */

public class Graphe {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Graphe graphe = (Graphe) o;
        return Objects.equals(sommets, graphe.sommets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sommets);
    }

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
            Sommet a = new Sommet.SommetBuilder().setIndice(i).setSurcout(0).setNbPointsVictoire(0).createSommet();
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

    public Graphe(Graphe graphe){
        this.sommets = new HashSet<>();
        for (Sommet a : graphe.sommets){
            Sommet s = new Sommet.SommetBuilder().setIndice(a.getIndice()).setJoueurs(a.getJoueurs()).setSurcout(a.getSurcout()).setNbPointsVictoire(a.getNbPointsVictoire()).createSommet();
            sommets.add(s);
        }
        for (Sommet a : graphe.sommets) {
            for (Sommet voisin : a.getVoisins()) {
                graphe.ajouterArete(this.getSommet(a.getIndice()), this.getSommet(voisin.getIndice()));
            }
        }
    }

    /**
     * Construit un sous-graphe induit par un ensemble de sommets
     * sans modifier le graphe donné
     *
     * @param g le graphe à partir duquel on construit le sous-graphe
     * @param X les sommets à considérer (on peut supposer que X est inclus dans l'ensemble des sommets de g,
     *          même si en principe ce n'est pas obligatoire)
     */
    public Graphe(Graphe g, Set<Sommet> X) {
        Set<Sommet> sommetAEnlever = new HashSet<>();
        for (Sommet s : g.sommets){
            if (!X.contains(s)){
                sommetAEnlever.add(s);
            }
        }
        Graphe gInduit = new Graphe(g);
        for (Sommet s : sommetAEnlever){
            gInduit.supprimerSommet(gInduit.getSommet(s.getIndice())); // gInduit.supprimerSommet(s);
        }
        this.sommets = gInduit.getSommets();
    }

    /**
     * @return true si et seulement si la séquence d'entiers passée en paramètre
     * correspond à un graphe simple valide dont les degrés correspondent aux éléments de la liste.
     * Pré-requis : on peut supposer que la séquence est triée dans l'ordre croissant.
     */
    public static boolean sequenceEstGraphe(List<Integer> sequence) {
        double sum = 0;
        for (Integer x : sequence){
            sum += x;
            if (x >= sequence.size()) return false;
        }
        sum = sum/2;
        return (int) sum == sum;
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
        Graphe gFusion = new Graphe(g);
        for (Sommet t : ensemble){ // gFusion a mtn les sommets qui ne sont pas en ensemble MAIS avec les arêtes des sommets supprimés
            gFusion.sommets.remove(t);
        }

        fusionnerSommets(ensemble, g); // calcul pour le sommet de l'ensemble

        gFusion.sommets.add(ensemble.iterator().next()); // rajoute le sommet fusionné à gFusion + rattache

        return gFusion;
    }

    private static void fusionnerSommets(Set<Sommet> ensemble, Graphe g){
        List<Sommet> sommetEnsemble = new ArrayList<>(ensemble); // conversion de Set en List pour pouvoir trier
        int surcout = 0; // calcul du surcout total
        int nbPoints = 0; // calcul du nbPointVictoire total
        Set<Integer> joueurs = new HashSet<>();
        for (Sommet s : ensemble){
            surcout += s.getSurcout();
            nbPoints += s.getNbPointsVictoire();
            joueurs.addAll(s.getJoueurs()); //no repeat puisque HashSet<>
        }

        Sommet minimum = new Sommet.SommetBuilder().setIndice(sommetEnsemble.get(0).getIndice()).setJoueurs(joueurs).setSurcout(surcout).setNbPointsVictoire(nbPoints).createSommet();

        g.rebrancherSommets(ensemble, minimum);

        ensemble.clear();
        ensemble.add(minimum);
        sommetEnsemble.sort(new PlusPetitSommet());
    }

    private void rebrancherSommets(Set<Sommet> ensemble, Sommet minimum){
        for (Sommet t : this.sommets){
            if (t.getVoisins().containsAll(ensemble)){
                t.getVoisins().removeAll(ensemble);
                t.ajouterVoisin(minimum);
            }
        }
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
        Set<Set<Sommet>> aretes = getAretes();
        return aretes.size();
    }

    /**
     * Ajoute un sommet d'indice i au graphe s'il n'est pas déjà présent
     *
     * @param i l'entier correspondant à l'indice du sommet à ajouter dans le graphe
     */
    public boolean ajouterSommet(int i) {
        Sommet sommet = new Sommet.SommetBuilder().setIndice(i).setSurcout(0).setNbPointsVictoire(0).createSommet();
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
        return s.getVoisins().size();
    }

    /**
     * @return true si et seulement si this est complet.
     */
    public boolean estComplet() {
        int ordre = this.sommets.size();
        int tailleMax = ordre*(ordre-1)/2;
        return tailleMax == getNbAretes();
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
        if (getNbAretes() != getNbSommets()) return false;
        if (!estConnexe()) return false;
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
        return estConnexe() && !possedeUnCycle();
    }

    /**
     * @return true si et seulement si this a au moins un cycle. On considère que le graphe vide n'est pas un cycle.
     */
    public boolean possedeUnCycle() {
        if (sommets.size() < 3) return false;
        Graphe g = eplucherDegres(1);
        return !(g.sommets.isEmpty());
    }

    private Graphe eplucherDegres(int n){ //enlève tous les sommets de degré 1
        Graphe g = new Graphe(this);
        boolean check = false;
        while (!check){
            check = true;
            for (Sommet s : sommets){
                if (g.sommets.contains(s) && degre(s) <= n){
                    g.supprimerSommet(s);
                    check = false;
                }
            }
        }
        return g;
    }

    /**
     * @return true si et seulement si this a un isthme
     */
    public boolean possedeUnIsthme() {
        Set<Graphe> graphes = this.separerEnPlusieursComposantesConnexes();
        for (Graphe g : graphes){
            for (Sommet s : g.sommets){ // méthode non optimisée à voir si assez de temps
                if (this.testerSommetTousSesVoisins(s)) return true;
            }
        }
        return false;
    }

    public Set<Graphe> separerEnPlusieursComposantesConnexes(){
        Set<Graphe> graphes = new HashSet<>();
        if (this.estConnexe()){
            graphes.add(this);
            return graphes;
        }
        List<Sommet> sommetSet = new ArrayList<>(sommets);
        while (sommetSet.size() < sommets.size()){
            Graphe g = new Graphe(getClasseConnexite(sommetSet.get(0)));
            graphes.add(g);
            sommetSet.removeAll(g.sommets);
        }
        return graphes;
    }

    public boolean testerSommetTousSesVoisins(Sommet s){
        Graphe g = new Graphe(this);
        Set<Sommet> voisins = new HashSet<>(g.getSommet(s.getIndice()).getVoisins());
        for (Sommet voisin : voisins){
            g.supprimerArete(s, voisin);
            if (!g.estConnexe()){
                return true;
            }
            g.ajouterArete(s, voisin);
        }
        return false;
    }

    public void ajouterArete(Sommet s, Sommet t) {
        s.ajouterVoisin(t);
        t.ajouterVoisin(s);
    }

    public void supprimerArete(Sommet s, Sommet t) {
        s.getVoisins().remove(t);
        t.getVoisins().remove(s);
    }
    public void supprimerSommet(Sommet s){
        Set<Sommet> voisins = s.getVoisins();
        for (Sommet v : voisins){
            v.getVoisins().remove(s);
        }
        sommets.remove(s);
    }

    /**
     * @return une coloration gloutonne du graphe sous forme d'une Map d'ensemble indépendants de sommets.
     * L'ordre de coloration des sommets est suivant l'ordre décroissant des degrés des sommets
     * (si deux sommets ont le même degré, alors on les ordonne par indice croissant).
     */
    public Map<Integer, Set<Sommet>> getColorationGloutonne() {
        List<Integer> couleurs = new ArrayList<>();
        for (int i = 0; i < this.getNbSommets(); i++){ // création des couleurs
            couleurs.add(i);
        }
        List<Sommet> ordre = new ArrayList<>(this.getSommets());
        ordre.sort(new ClasserSelonDegre());

        Map<Integer, Set<Sommet>> colorationGloutonne = new HashMap<>();
        Map<Sommet, Integer> coloration = new HashMap<>();
        Set<Integer> couleursMises = new HashSet<>();

        for (Sommet s : ordre){
            List<Integer> couleursPossibles = new ArrayList<>(couleurs);
            Set<Sommet> voisins = s.getVoisins();
            for (Sommet voisin : voisins){
                if (coloration.containsKey(voisin)){
                    couleursPossibles.remove(coloration.get(voisin));
                }
            }
            coloration.put(s, couleursPossibles.get(0));
            couleursMises.add(couleursPossibles.get(0));
        }

        for (Integer i : couleursMises){
            Set<Sommet> sommetSet = new HashSet<>();
            for (Map.Entry<Sommet, Integer> entry : coloration.entrySet()){
                if (Objects.equals(entry.getValue(), i)){
                    sommetSet.add(entry.getKey());
                }
            }
            colorationGloutonne.put(i, sommetSet);
        }

        return colorationGloutonne;
    }

    /**
     * @param depart  - ensemble non-vide de sommets
     * @param arrivee
     * @return le surcout total minimal du parcours entre l'ensemble de depart et le sommet d'arrivée
     * pré-requis : l'ensemble de départ et le sommet d'arrivée sont inclus dans l'ensemble des sommets de this
     */
    public int getDistance(Set<Sommet> depart, Sommet arrivee) {
        int distance = -1;
        for (Sommet s : depart){
            int distance2 = getDistance(s, arrivee);
            if (distance == -1 || distance2 < distance){
                distance = distance2;
            }
        }
        return distance;
    }

    /**
     * @return le surcout total minimal du parcours entre le sommet de depart et le sommet d'arrivée
     */
    public int getDistance(Sommet depart, Sommet arrivee) {
        boolean check = false;
        List<List<Sommet>> possiblites = new ArrayList<>();
        List<Sommet> deb = new ArrayList<>();
        List<Sommet> sommetsEnleves = new ArrayList<>();
        deb.add(depart);
        possiblites.add(deb);
        List<Sommet> derniereList = new ArrayList<>();
        Sommet sommetCourant = depart;
        while (!sommetCourant.equals(arrivee)){
            sommetsEnleves.add(sommetCourant);
            Set<Sommet> voisins = sommetCourant.getVoisins();
            List<Sommet> lastList = findTheLastOne(sommetCourant, possiblites);
            if (lastList != null){
                for (Sommet s : voisins){
                    if (!sommetsEnleves.contains(s)) {
                        List<Sommet> liste = new ArrayList<>(lastList);
                        liste.add(s);
                        possiblites.add(liste);
                    }
                }
                possiblites.remove(lastList);
                List<Sommet> listLeastCostly = returnTheLeastCostly(possiblites);
                if (listLeastCostly == null){
                    return Integer.MAX_VALUE;
                }
                derniereList = listLeastCostly;
                sommetCourant = listLeastCostly.get(listLeastCostly.size()-1);
            }
            else {
                break;
            }
            if (sommetCourant.equals(arrivee)){
                check = true;
            }

        }

        if (check){
            int surcout = 0;

            for (Sommet s : derniereList){
                surcout += s.getSurcout();
            }
            surcout -= derniereList.get(0).getSurcout();

            return surcout;
        }
        else {
            return Integer.MAX_VALUE;
        }

    }

    private static List<Sommet> findTheLastOne(Sommet sommet, List<List<Sommet>> possiblites){
        for (List<Sommet> sommetList : possiblites){
            if (sommetList.get(sommetList.size() - 1).equals(sommet)){
                return sommetList;
            }
        }
        return null;
    }

    private static List<Sommet> returnTheLeastCostly(List<List<Sommet>> possibilites){
        List<Sommet> leastCostly = null;
        int surcout = -1;
        for (List<Sommet> ls : possibilites){
            int surcoutlist = 0;
            for (Sommet s : ls){
                surcoutlist += s.getSurcout();
            }
            if (surcout == -1 || surcout > surcoutlist){
                surcout = surcoutlist;
                leastCostly = ls;
            }
        }
        return leastCostly;
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
        return getClasseConnexite(getSommet(0)).equals(sommets);
    }

    /**
     * @return le degré maximum des sommets du graphe
     */
    public int degreMax() {
        int degreMax = 0;
        for (Sommet s : sommets){
            if (s.getVoisins().size() > degreMax){
                degreMax = s.getVoisins().size();
            }
        }
        return degreMax;
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
        if (getNbSommets() == k && this.estComplet() == true) {
            return true;
        }
        boolean nEstPasComplet = true;
        Graphe test = new Graphe(this);
        while (nEstPasComplet) {
            List<Sommet> sommetsASup = new ArrayList<>(sommetAvecLeMoinsDArete(test));
            //int rd = (int) Math.random() * ((this.getNbSommets()-k) - 0);
            test.supprimerSommet(sommetsASup.get(0));
            if (test.estComplet()){
                return true;
            }
        }
        return false;
    }

    public List<Sommet> sommetAvecLeMoinsDArete(Graphe g){
        List<Sommet> res = new ArrayList<>();
        int min = g.getSommet(0).getVoisins().size();
        res.add(g.getSommet(0));
        for (int i = 0; i < g.getSommets().size(); i++) {
            if (min > g.getSommet(i).getVoisins().size()){
                min = g.getSommet(i).getVoisins().size();
                res.clear();
                res.add(g.getSommet(i));
            } else if (min == g.getSommet(i).getVoisins().size()) {
                res.add(g.getSommet(i));
            }
        }
        return res;
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
