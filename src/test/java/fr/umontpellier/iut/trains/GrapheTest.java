package fr.umontpellier.iut.trains;

import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Timeout;

import fr.umontpellier.iut.graphes.Graphe;
import fr.umontpellier.iut.trains.plateau.Plateau;

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

    @Test
    public void main(){
        Jeu jeu = new Jeu(new String[]{"Batman", "Robin"}, new String[]{}, Plateau.TOKYO);
        Graphe graphe = jeu.getGraphe();

        System.out.println(graphe.toStringArete());
    }
}