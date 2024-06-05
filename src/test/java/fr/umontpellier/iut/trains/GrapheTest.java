package fr.umontpellier.iut.trains;

import fr.umontpellier.iut.graphes.PlusPetitSommet;
import fr.umontpellier.iut.graphes.Sommet;
import fr.umontpellier.iut.trains.plateau.Tuile;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Timeout;

import fr.umontpellier.iut.graphes.Graphe;
import fr.umontpellier.iut.trains.plateau.Plateau;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@Timeout(value = 1, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
public class GrapheTest {
    @Test
    public void test_graphe_tokyo() {
        Jeu jeu = new Jeu(new String[]{"Batman", "Robin"}, new String[]{}, Plateau.TOKYO);
        Graphe graphe = jeu.getGraphe();

        assertEquals(66, graphe.getNbSommets());
        assertEquals(156, graphe.getNbAretes());
        assertTrue(graphe.estConnexe());
        assertTrue(graphe.possedeUnCycle());
        assertEquals(6, graphe.degreMax());
    }

    @Disabled
    @Test
    public void test_distances_tokyo() {
        Jeu jeu = new Jeu(new String[]{"Rick", "Morty"}, new String[]{}, Plateau.TOKYO);
        Graphe graphe = jeu.getGraphe();

        assertEquals(4, graphe.getDistance(graphe.getSommet(0), graphe.getSommet(54)));
        assertEquals(0, graphe.getDistance(graphe.getSommet(13), graphe.getSommet(54)));
        assertEquals(0, graphe.getDistance(graphe.getSommet(3), graphe.getSommet(54)));
        assertEquals(11, graphe.getDistance(graphe.getSommet(67), graphe.getSommet(9)));
        assertEquals(2, graphe.getDistance(graphe.getSommet(34), graphe.getSommet(35)));
    }

    @Test
    public void test_graphe_osaka() {
        Jeu jeu = new Jeu(new String[]{"Lois", "Clark"}, new String[]{}, Plateau.OSAKA);
        Graphe graphe = jeu.getGraphe();

        assertEquals(66, graphe.getNbSommets());
        assertEquals(151, graphe.getNbAretes());
        assertTrue(graphe.estConnexe());
        assertTrue(graphe.possedeUnCycle());
        assertEquals(6, graphe.degreMax());
    }

    @Test
    public void test_getGraphe_test(){
        Jeu jeu = new Jeu(new String[]{"Batman", "Robin"}, new String[]{}, Plateau.TEST);
        Graphe graphe = jeu.getGraphe();
        assertEquals(9, graphe.getNbSommets());
        assertEquals(16, graphe.getNbAretes());
    }

    @Test
    public void test_getNbArete(){
        Graphe g = new Graphe(5);
        g.ajouterArete(g.getSommet(0), g.getSommet(1));
        g.ajouterArete(g.getSommet(1), g.getSommet(2));
        g.ajouterArete(g.getSommet(1), g.getSommet(3));
        g.ajouterArete(g.getSommet(2), g.getSommet(4));
        g.ajouterArete(g.getSommet(3), g.getSommet(4));
        assertEquals(5, g.getNbSommets());
        assertEquals(5, g.getNbAretes());
    }

    @Test
    public void test_getNbArete_doublon(){
        Graphe g = new Graphe(5);
        g.ajouterArete(g.getSommet(0), g.getSommet(1));
        g.ajouterArete(g.getSommet(1), g.getSommet(2));
        g.ajouterArete(g.getSommet(1), g.getSommet(3));
        g.ajouterArete(g.getSommet(2), g.getSommet(4));
        g.ajouterArete(g.getSommet(3), g.getSommet(4));
        g.ajouterArete(g.getSommet(4), g.getSommet(3));
        assertEquals(5, g.getNbSommets());
        assertEquals(5, g.getNbAretes());
    }

    @Test
    public void test_getNbArete_doublon_1_sommet_seul(){
        Graphe g = new Graphe(6);
        g.ajouterArete(g.getSommet(0), g.getSommet(1));
        g.ajouterArete(g.getSommet(1), g.getSommet(2));
        g.ajouterArete(g.getSommet(1), g.getSommet(3));
        g.ajouterArete(g.getSommet(2), g.getSommet(4));
        g.ajouterArete(g.getSommet(3), g.getSommet(4));
        g.ajouterArete(g.getSommet(4), g.getSommet(3));
        assertEquals(6, g.getNbSommets());
        assertEquals(5, g.getNbAretes());
    }

    @Test
    public void test_estChaine_vrai(){
        Graphe g = new Graphe(6);
        g.ajouterArete(g.getSommet(0), g.getSommet(1));
        g.ajouterArete(g.getSommet(1), g.getSommet(2));
        g.ajouterArete(g.getSommet(2), g.getSommet(3));
        g.ajouterArete(g.getSommet(3), g.getSommet(4));
        g.ajouterArete(g.getSommet(4), g.getSommet(5));
        assertTrue(g.estChaine());
    }

    @Test
    public void test_estChaine_faux_1(){
        Graphe g = new Graphe(6);
        g.ajouterArete(g.getSommet(0), g.getSommet(1));
        g.ajouterArete(g.getSommet(1), g.getSommet(2));
        g.ajouterArete(g.getSommet(2), g.getSommet(3));
        g.ajouterArete(g.getSommet(3), g.getSommet(4));
        g.ajouterArete(g.getSommet(3), g.getSommet(5));
        g.ajouterArete(g.getSommet(4), g.getSommet(5));
        assertFalse(g.estChaine());
    }

    @Test
    public void test_estChaine_faux_cycle(){
        Graphe g = new Graphe(6);
        g.ajouterArete(g.getSommet(0), g.getSommet(1));
        g.ajouterArete(g.getSommet(1), g.getSommet(2));
        g.ajouterArete(g.getSommet(2), g.getSommet(3));
        g.ajouterArete(g.getSommet(3), g.getSommet(4));
        g.ajouterArete(g.getSommet(4), g.getSommet(5));
        g.ajouterArete(g.getSommet(5), g.getSommet(0));
        assertFalse(g.estChaine());
    }
    @Test
    public void test_degreMax_3(){
        Graphe g = new Graphe(6);
        g.ajouterArete(g.getSommet(0), g.getSommet(1));
        g.ajouterArete(g.getSommet(1), g.getSommet(2));
        g.ajouterArete(g.getSommet(2), g.getSommet(3));
        g.ajouterArete(g.getSommet(3), g.getSommet(4));
        g.ajouterArete(g.getSommet(3), g.getSommet(5));
        g.ajouterArete(g.getSommet(4), g.getSommet(5));
        g.ajouterArete(g.getSommet(5), g.getSommet(0));
        assertEquals(3, g.degreMax());
    }

    @Test
    public void test_estConnexe_4_sommet_cyclique(){
        Graphe g = new Graphe(4);
        g.ajouterArete(g.getSommet(0), g.getSommet(1));
        g.ajouterArete(g.getSommet(1), g.getSommet(2));
        g.ajouterArete(g.getSommet(2), g.getSommet(3));
        g.ajouterArete(g.getSommet(3), g.getSommet(0));
        assertTrue(g.estConnexe());
    }
    @Test
    public void test_estConnexe_4_sommet_acyclique(){
        Graphe g = new Graphe(4);
        g.ajouterArete(g.getSommet(0), g.getSommet(1));
        g.ajouterArete(g.getSommet(1), g.getSommet(2));
        g.ajouterArete(g.getSommet(2), g.getSommet(3));
        assertTrue(g.estConnexe());
    }
    @Test
    public void test_estConnexe_5_sommet_acyclique_1_sommet_sans_aretes(){
        Graphe g = new Graphe(5);
        g.ajouterArete(g.getSommet(0), g.getSommet(1));
        g.ajouterArete(g.getSommet(1), g.getSommet(2));
        g.ajouterArete(g.getSommet(2), g.getSommet(3));
        assertFalse(g.estConnexe());
    }

    @Test
    public void test_estConnexe_10_sommet_acyclique_connexe(){
        Graphe g = new Graphe(10);
        g.ajouterArete(g.getSommet(0), g.getSommet(1));
        g.ajouterArete(g.getSommet(1), g.getSommet(2));
        g.ajouterArete(g.getSommet(2), g.getSommet(3));
        g.ajouterArete(g.getSommet(3), g.getSommet(4));
        g.ajouterArete(g.getSommet(4), g.getSommet(5));
        g.ajouterArete(g.getSommet(5), g.getSommet(6));
        g.ajouterArete(g.getSommet(6), g.getSommet(7));
        g.ajouterArete(g.getSommet(7), g.getSommet(8));
        g.ajouterArete(g.getSommet(8), g.getSommet(9));
        assertTrue(g.estConnexe());
    }

    @Test
    public void test_estConnexe_10_sommet_forment_2_cicle_pas_lier(){
        Graphe g = new Graphe(10);
        g.ajouterArete(g.getSommet(0), g.getSommet(1));
        g.ajouterArete(g.getSommet(1), g.getSommet(2));
        g.ajouterArete(g.getSommet(2), g.getSommet(3));
        g.ajouterArete(g.getSommet(3), g.getSommet(4));
        g.ajouterArete(g.getSommet(4), g.getSommet(0));
        g.ajouterArete(g.getSommet(5), g.getSommet(6));
        g.ajouterArete(g.getSommet(6), g.getSommet(7));
        g.ajouterArete(g.getSommet(7), g.getSommet(8));
        g.ajouterArete(g.getSommet(8), g.getSommet(9));
        g.ajouterArete(g.getSommet(9), g.getSommet(5));
        assertFalse(g.estConnexe());
    }

    @Disabled
    @Test
    public void test_eplucherDegres1_1(){
        Graphe g = new Graphe(6);
        g.ajouterArete(g.getSommet(0), g.getSommet(1));
        g.ajouterArete(g.getSommet(1), g.getSommet(2));
        g.ajouterArete(g.getSommet(2), g.getSommet(3));
        g.ajouterArete(g.getSommet(3), g.getSommet(4));
        g.ajouterArete(g.getSommet(3), g.getSommet(5));
        g.ajouterArete(g.getSommet(4), g.getSommet(5));
        //Graphe gg = g.eplucherDegres(1);
        //assertEquals(3, gg.getNbSommets());
    }

    @Disabled
    @Test
    public void test_eplucherDegres1_2(){
        Graphe g = new Graphe(6);
        g.ajouterArete(g.getSommet(0), g.getSommet(1));
        g.ajouterArete(g.getSommet(1), g.getSommet(2));
        g.ajouterArete(g.getSommet(2), g.getSommet(3));
        g.ajouterArete(g.getSommet(3), g.getSommet(4));
        g.ajouterArete(g.getSommet(4), g.getSommet(5));
        //Graphe gg = g.eplucherDegres(1);
        //assertEquals(0, gg.getNbSommets());
    }

    @Test
    public void test_possedeUnCycle_vrai(){
        Graphe g = new Graphe(6);
        g.ajouterArete(g.getSommet(0), g.getSommet(1));
        g.ajouterArete(g.getSommet(1), g.getSommet(2));
        g.ajouterArete(g.getSommet(2), g.getSommet(3));
        g.ajouterArete(g.getSommet(3), g.getSommet(4));
        g.ajouterArete(g.getSommet(4), g.getSommet(5));
        g.ajouterArete(g.getSommet(5), g.getSommet(0));
        assertTrue(g.possedeUnCycle());
        assertFalse(g.estForet());
    }

    @Test
    public void test_possedeUnCycle_faux(){
        Graphe g = new Graphe(6);
        g.ajouterArete(g.getSommet(0), g.getSommet(1));
        g.ajouterArete(g.getSommet(1), g.getSommet(2));
        g.ajouterArete(g.getSommet(2), g.getSommet(3));
        g.ajouterArete(g.getSommet(3), g.getSommet(4));
        g.ajouterArete(g.getSommet(4), g.getSommet(5));
        assertFalse(g.possedeUnCycle());
        assertTrue(g.estForet());
    }
    @Test
    public void test_estForet_vrai(){
        Graphe g = new Graphe(6);
        g.ajouterArete(g.getSommet(0), g.getSommet(1));
        g.ajouterArete(g.getSommet(1), g.getSommet(2));
        g.ajouterArete(g.getSommet(2), g.getSommet(3));
        g.ajouterArete(g.getSommet(3), g.getSommet(4));
        g.ajouterArete(g.getSommet(4), g.getSommet(5));
        assertFalse(g.possedeUnCycle());
        assertTrue(g.estForet());
    }

    @Test
    public void test_sousGrapheInduit(){
        Graphe g = new Graphe(6);
        g.ajouterArete(g.getSommet(0), g.getSommet(1));
        g.ajouterArete(g.getSommet(1), g.getSommet(2));
        g.ajouterArete(g.getSommet(2), g.getSommet(3));
        g.ajouterArete(g.getSommet(3), g.getSommet(4));
        g.ajouterArete(g.getSommet(4), g.getSommet(5));
        g.ajouterArete(g.getSommet(5), g.getSommet(0));
        Graphe g2 = new Graphe(6); // enlève 3 et 2
        g.ajouterArete(g.getSommet(0), g.getSommet(1));
        g.ajouterArete(g.getSommet(4), g.getSommet(5));
        g.ajouterArete(g.getSommet(5), g.getSommet(0));
        g2.supprimerSommet(g2.getSommet(2));
        g2.supprimerSommet(g2.getSommet(3));
        Set<Sommet> X = g2.getSommets();
        Graphe gg = new Graphe(g, X);
        assertEquals(g2, gg);
    }

    @Test
    public void test_Comparator_PlusPetitIndice(){
        Set<Sommet> X = new HashSet<>();
        Sommet x1 = new Sommet.SommetBuilder().setIndice(3).setSurcout(0).setNbPointsVictoire(0).createSommet();
        Sommet x2 = new Sommet.SommetBuilder().setIndice(1).setSurcout(0).setNbPointsVictoire(0).createSommet();
        Sommet x3 = new Sommet.SommetBuilder().setIndice(6).setSurcout(0).setNbPointsVictoire(0).createSommet();
        Sommet x4 = new Sommet.SommetBuilder().setIndice(9).setSurcout(0).setNbPointsVictoire(0).createSommet();
        X.add(x1);
        X.add(x4);
        X.add(x3);
        X.add(x4);
        X.add(x2);
        List<Sommet> sommetList = new ArrayList<>(X);
        sommetList.sort(new PlusPetitSommet());
        assertEquals(x2, sommetList.get(0));
    }

    @Test
    public void test_fusionnerEnsembleSommets_1(){
        Graphe g = new Graphe();
        Sommet s0 = new Sommet.SommetBuilder().setIndice(0).setNbPointsVictoire(0).setSurcout(0).createSommet();
        Sommet s1 = new Sommet.SommetBuilder().setIndice(1).setNbPointsVictoire(0).setSurcout(0).createSommet();
        Sommet s2 = new Sommet.SommetBuilder().setIndice(2).setNbPointsVictoire(0).setSurcout(0).createSommet();
        Sommet s3 = new Sommet.SommetBuilder().setIndice(3).setNbPointsVictoire(0).setSurcout(0).createSommet();
        Sommet s4 = new Sommet.SommetBuilder().setIndice(4).setNbPointsVictoire(0).setSurcout(0).createSommet();
        Sommet s5 = new Sommet.SommetBuilder().setIndice(5).setNbPointsVictoire(0).setSurcout(0).createSommet();
        g.ajouterSommet(s0);
        g.ajouterSommet(s1);
        g.ajouterSommet(s2);
        g.ajouterSommet(s3);
        g.ajouterSommet(s4);
        g.ajouterSommet(s5);
        g.ajouterArete(g.getSommet(0), g.getSommet(1));
        g.ajouterArete(g.getSommet(1), g.getSommet(2));
        g.ajouterArete(g.getSommet(2), g.getSommet(3));
        g.ajouterArete(g.getSommet(3), g.getSommet(4));
        g.ajouterArete(g.getSommet(3), g.getSommet(5));
        Set<Sommet> X = new HashSet<>();
        X.add(s2);
        X.add(s3);
        Graphe gFusion = Graphe.fusionnerEnsembleSommets(g, X);
        Graphe g2 = new Graphe();
        g2.ajouterSommet(s0);
        g2.ajouterSommet(s1);
        g2.ajouterSommet(s2);
        g2.ajouterSommet(s4);
        g2.ajouterSommet(s5);
        g2.ajouterArete(g2.getSommet(0), g2.getSommet(1));
        g2.ajouterArete(g2.getSommet(1), g2.getSommet(2));
        g2.ajouterArete(g2.getSommet(2), g2.getSommet(4));
        g2.ajouterArete(g2.getSommet(2), g2.getSommet(5));

        Set<Sommet> Xcopie = new HashSet<>();
        Xcopie.add(s2);

        assertEquals(g2.getSommets(), gFusion.getSommets());
        assertEquals(Xcopie, X);
    }

    @Test
    public void test_fusionnerEnsembleSommets_1_Seul_Sommet(){
        Graphe g = new Graphe();
        Sommet s0 = new Sommet.SommetBuilder().setIndice(0).setNbPointsVictoire(0).setSurcout(0).createSommet();
        g.ajouterSommet(s0);
        Set<Sommet> X = new HashSet<>();
        X.add(s0);
        Graphe gFusion = Graphe.fusionnerEnsembleSommets(g, X);

        assertEquals(g.getSommets(), gFusion.getSommets());
    }

    @Test
    public void test_sequenceEstGraphe(){
        List<Integer> X = new ArrayList<>();
        X.add(1);
        X.add(2);
        X.add(1);
        assertTrue(Graphe.sequenceEstGraphe(X));
        X.add(1);
        assertFalse(Graphe.sequenceEstGraphe(X));
        X.remove(1);
        X.add(5);
        assertFalse(Graphe.sequenceEstGraphe(X));
    }

    @Test
    public void test_getDistance_1(){
        Graphe g = new Graphe();
        Sommet s1 = new Sommet.SommetBuilder().setIndice(1).setSurcout(1).setJoueurs(null).setNbPointsVictoire(0).createSommet();
        Sommet s2 = new Sommet.SommetBuilder().setIndice(2).setSurcout(3).setJoueurs(null).setNbPointsVictoire(0).createSommet();
        Sommet s3 = new Sommet.SommetBuilder().setIndice(3).setSurcout(2).setJoueurs(null).setNbPointsVictoire(0).createSommet();
        g.ajouterSommet(s1);
        g.ajouterSommet(s2);
        g.ajouterSommet(s3);
        g.ajouterArete(s1, s2);
        g.ajouterArete(s2, s3);
        assertEquals(5, g.getDistance(s1, s3));
    }

    @Test
    public void test_getDistance_2(){
        Graphe g = new Graphe();
        Sommet s1 = new Sommet.SommetBuilder().setIndice(1).setSurcout(1).setJoueurs(null).setNbPointsVictoire(0).createSommet();
        Sommet s2 = new Sommet.SommetBuilder().setIndice(2).setSurcout(3).setJoueurs(null).setNbPointsVictoire(0).createSommet();
        Sommet s3 = new Sommet.SommetBuilder().setIndice(3).setSurcout(1).setJoueurs(null).setNbPointsVictoire(0).createSommet();
        Sommet s4 = new Sommet.SommetBuilder().setIndice(4).setSurcout(2).setJoueurs(null).setNbPointsVictoire(3).createSommet();
        Sommet s5 = new Sommet.SommetBuilder().setIndice(5).setSurcout(2).setJoueurs(null).setNbPointsVictoire(0).createSommet();
        g.ajouterSommet(s1);
        g.ajouterSommet(s2);
        g.ajouterSommet(s3);
        g.ajouterSommet(s4);
        g.ajouterArete(s1, s2);
        g.ajouterArete(s2, s3);
        g.ajouterArete(s2, s4);
        g.ajouterArete(s4, s5);
        assertEquals(7, g.getDistance(s1, s5));
    }

    @Test
    public void test_getDistance_3(){
        Graphe g = new Graphe();
        Sommet s1 = new Sommet.SommetBuilder().setIndice(1).setSurcout(1).setJoueurs(null).setNbPointsVictoire(0).createSommet();
        Sommet s2 = new Sommet.SommetBuilder().setIndice(2).setSurcout(3).setJoueurs(null).setNbPointsVictoire(0).createSommet();
        Sommet s3 = new Sommet.SommetBuilder().setIndice(3).setSurcout(1).setJoueurs(null).setNbPointsVictoire(0).createSommet();
        Sommet s4 = new Sommet.SommetBuilder().setIndice(4).setSurcout(2).setJoueurs(null).setNbPointsVictoire(3).createSommet();
        Sommet s5 = new Sommet.SommetBuilder().setIndice(5).setSurcout(2).setJoueurs(null).setNbPointsVictoire(0).createSommet();
        g.ajouterSommet(s1);
        g.ajouterSommet(s2);
        g.ajouterSommet(s3);
        g.ajouterSommet(s4);
        g.ajouterArete(s1, s2);
        g.ajouterArete(s2, s3);
        g.ajouterArete(s4, s5);
        assertEquals(Integer.MAX_VALUE, g.getDistance(s1, s5));
    }

    @Test
    public void main(){
        Jeu jeu = new Jeu(new String[]{"Batman", "Robin"}, new String[]{}, Plateau.TOKYO);
        Graphe graphe = jeu.getGraphe();
    }

    @Test
    public void test_possedeSousGrapheComplet_avec_graphe_deja_complet() {
        Graphe g = new Graphe(4);
        g.ajouterArete(g.getSommet(0), g.getSommet(1));
        g.ajouterArete(g.getSommet(0), g.getSommet(2));
        g.ajouterArete(g.getSommet(0), g.getSommet(3));
        g.ajouterArete(g.getSommet(1), g.getSommet(2));
        g.ajouterArete(g.getSommet(1), g.getSommet(3));
        g.ajouterArete(g.getSommet(2), g.getSommet(3));
        assertTrue(g.estComplet());
        assertFalse(g.possedeUnIsthme());
        assertTrue(g.possedeSousGrapheComplet(4));
    }

    @Disabled
    @Test
    public void test_possedeSousGrapheComplet_pas_complet() {
        Graphe g = new Graphe(4);
        g.ajouterArete(g.getSommet(0), g.getSommet(1));
        g.ajouterArete(g.getSommet(0), g.getSommet(2));
        g.ajouterArete(g.getSommet(0), g.getSommet(3));
        g.ajouterArete(g.getSommet(1), g.getSommet(2));
        g.ajouterArete(g.getSommet(1), g.getSommet(3));
        assertFalse(g.estComplet());
        assertFalse(g.possedeSousGrapheComplet(4));
    }

    @Disabled
    @Test
    public void test_possedeSousGrapheComplet_pour_k_egale_a_4() {
        Graphe g = new Graphe(6);
        g.ajouterArete(g.getSommet(0), g.getSommet(1));
        g.ajouterArete(g.getSommet(0), g.getSommet(2));
        g.ajouterArete(g.getSommet(0), g.getSommet(3));
        g.ajouterArete(g.getSommet(0), g.getSommet(4));
        g.ajouterArete(g.getSommet(0), g.getSommet(5));
        g.ajouterArete(g.getSommet(0), g.getSommet(6));
        g.ajouterArete(g.getSommet(1), g.getSommet(2));
        g.ajouterArete(g.getSommet(1), g.getSommet(3));
        g.ajouterArete(g.getSommet(1), g.getSommet(4));
        g.ajouterArete(g.getSommet(1), g.getSommet(5));
        g.ajouterArete(g.getSommet(1), g.getSommet(6));
        g.ajouterArete(g.getSommet(2), g.getSommet(3));
        g.ajouterArete(g.getSommet(2), g.getSommet(4));
        g.ajouterArete(g.getSommet(2), g.getSommet(5));
        g.ajouterArete(g.getSommet(2), g.getSommet(6));
        g.ajouterArete(g.getSommet(3), g.getSommet(4));
        g.ajouterArete(g.getSommet(3), g.getSommet(5));
        g.ajouterArete(g.getSommet(3), g.getSommet(6));
        g.ajouterArete(g.getSommet(4), g.getSommet(5));
        g.ajouterArete(g.getSommet(4), g.getSommet(6));
        g.ajouterArete(g.getSommet(5), g.getSommet(6));
        assertFalse(g.possedeSousGrapheComplet(4));
    }

    @Test
    public void test_getColorationGloutonne_1(){
        Graphe g = new Graphe(4);
        g.ajouterArete(g.getSommet(0), g.getSommet(1));
        g.ajouterArete(g.getSommet(1), g.getSommet(2));
        g.ajouterArete(g.getSommet(2), g.getSommet(3));
        g.ajouterArete(g.getSommet(3), g.getSommet(0));
        Map<Integer, Set<Sommet>> map1 = g.getColorationGloutonne();
        assertEquals(2, map1.size());
    }

    @Test
    public void test_getColorationGloutonne_2(){
        Graphe g = new Graphe(5);
        g.ajouterArete(g.getSommet(0), g.getSommet(1));
        g.ajouterArete(g.getSommet(1), g.getSommet(2));
        g.ajouterArete(g.getSommet(2), g.getSommet(3));
        g.ajouterArete(g.getSommet(3), g.getSommet(4));
        g.ajouterArete(g.getSommet(4), g.getSommet(0));
        Map<Integer, Set<Sommet>> map1 = g.getColorationGloutonne();
        assertEquals(3, map1.size());
    }
    @Test
    public void test_getColorationGloutonne_3(){
        Graphe g = new Graphe(6);
        g.ajouterArete(g.getSommet(0), g.getSommet(3));
        g.ajouterArete(g.getSommet(3), g.getSommet(5));
        g.ajouterArete(g.getSommet(5), g.getSommet(4));
        g.ajouterArete(g.getSommet(4), g.getSommet(1));
        g.ajouterArete(g.getSommet(1), g.getSommet(2));
        g.ajouterArete(g.getSommet(2), g.getSommet(0));
        Map<Integer, Set<Sommet>> map1 = g.getColorationGloutonne();
        ArrayList<Integer> expectedResults = new ArrayList<>();
        expectedResults.add(2);
        expectedResults.add(3);
        assertTrue(expectedResults.contains(map1.size()));
    }

    @Test
    public void test_possedeUnIsthme_faux(){
        Graphe g = new Graphe(6);
        g.ajouterArete(g.getSommet(0), g.getSommet(1));
        g.ajouterArete(g.getSommet(1), g.getSommet(2));
        g.ajouterArete(g.getSommet(2), g.getSommet(3));
        g.ajouterArete(g.getSommet(3), g.getSommet(4));
        g.ajouterArete(g.getSommet(4), g.getSommet(5));
        g.ajouterArete(g.getSommet(5), g.getSommet(0));
        assertFalse(g.possedeUnIsthme());
        assertTrue(g.possedeUnCycle());
        assertTrue(g.estConnexe());
    }

    @Test
    public void test_possedeUnIsthme_vrai(){
        /*
        forme du graphe :

        /|_|\
        \| |/

        * */

        Graphe g = new Graphe(6);
        g.ajouterArete(g.getSommet(0), g.getSommet(1));
        g.ajouterArete(g.getSommet(1), g.getSommet(2));
        g.ajouterArete(g.getSommet(2), g.getSommet(0));
        g.ajouterArete(g.getSommet(2), g.getSommet(3));
        g.ajouterArete(g.getSommet(3), g.getSommet(4));
        g.ajouterArete(g.getSommet(4), g.getSommet(5));
        g.ajouterArete(g.getSommet(5), g.getSommet(3));
        assertTrue(g.possedeUnIsthme());
        assertTrue(g.possedeUnCycle());
        assertTrue(g.estConnexe());
    }
}