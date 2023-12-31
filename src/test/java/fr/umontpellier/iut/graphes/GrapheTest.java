package fr.umontpellier.iut.graphes;


import fr.umontpellier.iut.rails.Route;
import fr.umontpellier.iut.rails.RouteMaritime;
import fr.umontpellier.iut.rails.RouteTerrestre;
import fr.umontpellier.iut.rails.data.Couleur;
import fr.umontpellier.iut.rails.data.Plateau;
import fr.umontpellier.iut.rails.data.Ville;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.reflections.vfs.SystemDir;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GrapheTest {
    private Graphe  graphe;

    @BeforeEach
    void setUp() {
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(0, 1));
        aretes.add(new Arete(0, 3));
        aretes.add(new Arete(1, 2));
        aretes.add(new Arete(2, 3));
        aretes.add(new Arete(8, 42));
        graphe = new Graphe(aretes);
    }

    private boolean collectionsDansLeMemeOrdre(Iterable<Integer> listeAttendue, Iterable<Integer> listeObtenue) {
        Iterator<Integer> it1 = listeAttendue.iterator();
        Iterator<Integer> it2 = listeObtenue.iterator();
        while (it1.hasNext() && it2.hasNext()) {
            int elem1 = it1.next();
            int elem2 = it2.next();
            if (elem1 != elem2) {
                return false;
            }
        }
        return !it1.hasNext() && !it2.hasNext();
    }

    @Test
    void testNbAretes() {
        assertEquals(5, graphe.nbAretes());
    }


    @Test
    void testContientSommet() {
        assertTrue(graphe.contientSommet(0));
        assertTrue(graphe.contientSommet(1));
        assertTrue(graphe.contientSommet(2));
        assertTrue(graphe.contientSommet(3));
        assertTrue(graphe.contientSommet(8));
        assertTrue(graphe.contientSommet(42));
        assertFalse(graphe.contientSommet(7));
    }

    @Test
    void testAjouterSommet() {
        int nbSommets = graphe.nbSommets();
        graphe.ajouterSommet(59);
        assertTrue(graphe.contientSommet(59));
        assertEquals(nbSommets + 1, graphe.nbSommets());
        graphe.ajouterSommet(59);
        assertEquals(nbSommets + 1, graphe.nbSommets());
    }

    @Test
    void testAjouterArete() {
        int nbAretes = graphe.nbAretes();
        graphe.ajouterArete(new Arete(0, 3));
        assertEquals(nbAretes, graphe.nbAretes());
        graphe.ajouterArete(new Arete(9, 439, null));
        assertEquals(nbAretes + 1, graphe.nbAretes());
        graphe.ajouterArete(new Arete(0, 3, new RouteMaritime(new Ville("Athina", true), new Ville("Marseille", true), Couleur.ROUGE, 2) {
        }));
        assertEquals(nbAretes + 2, graphe.nbAretes());
    }

    @Test
    void testSupprimerArete() {
        int nbAretes = graphe.nbAretes();
        graphe.supprimerArete(new Arete(0, 3));
        assertEquals(nbAretes - 1, graphe.nbAretes());
        graphe.supprimerArete(new Arete(0, 3));
        assertEquals(nbAretes - 1, graphe.nbAretes());
        graphe.supprimerArete(new Arete(0, 3, null));
        assertEquals(nbAretes - 1, graphe.nbAretes());
    }

    @Test
    void testSupprimerSommet() {
        int nbSommets = graphe.nbSommets();
        int nbAretes = graphe.nbAretes();
        graphe.supprimerSommet(42);
        assertEquals(nbSommets - 1, graphe.nbSommets());
        assertEquals(nbAretes - 1, graphe.nbAretes());
        graphe.supprimerSommet(2);
        assertEquals(nbSommets - 2, graphe.nbSommets());
        assertEquals(nbAretes - 3, graphe.nbAretes());
    }

    @Test
    void testExisteArete() {
        assertTrue(graphe.existeArete(new Arete(0, 1)));
        assertTrue(graphe.existeArete(new Arete(0, 3)));
        assertTrue(graphe.existeArete(new Arete(1, 2)));
        assertTrue(graphe.existeArete(new Arete(2, 3)));
        assertTrue(graphe.existeArete(new Arete(8, 42)));
        assertFalse(graphe.existeArete(new Arete(0, 2)));
        assertFalse(graphe.existeArete(new Arete(0, 4)));
        assertFalse(graphe.existeArete(new Arete(1, 3)));
        assertFalse(graphe.existeArete(new Arete(2, 4)));
        assertFalse(graphe.existeArete(new Arete(8, 43)));
    }

    @Test
    void testEstConnexe1(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(0, 1));
        aretes.add(new Arete(1, 3));
        aretes.add(new Arete(3, 4));
        Graphe graphe2 = new Graphe(aretes);
        assertTrue(graphe2.estConnexe());
    }

    @Test
    void testEstUneChaine1(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(0, 1));
        aretes.add(new Arete(1, 3));
        aretes.add(new Arete(3, 4));
        Graphe graphe2 = new Graphe(aretes);
        assertTrue(graphe2.estUneChaine());
    }

    @Test
    void testEstUneChaine2(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(0, 1));
        aretes.add(new Arete(1, 3));
        aretes.add(new Arete(1, 4));
        Graphe graphe2 = new Graphe(aretes);
        assertFalse(graphe2.estUneChaine());
    }

    @Test
    void testEstUneChaine3(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(0, 1));
        aretes.add(new Arete(1, 2));
        Graphe graphe2 = new Graphe(aretes);
        assertTrue(graphe2.estUneChaine());
    }

    @Test
    void testEstUneChaine4(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(0, 1));
        Graphe graphe2 = new Graphe(aretes);
        assertTrue(graphe2.estUneChaine());
    }

    @Test
    void testEstUneChaine5(){
        Graphe graphe2 = new Graphe(1);
        assertTrue(graphe2.estUneChaine());
    }

    @Test
    void testEstUneChaine6(){
        Graphe graphe2 = new Graphe(2);
        assertFalse(graphe2.estUneChaine());
    }

    @Test
    void testEstUneChaine7(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(0, 1));
        aretes.add(new Arete(1, 2));
        aretes.add(new Arete(2, 3));
        aretes.add(new Arete(4, 5));
        aretes.add(new Arete(5, 6));
        aretes.add(new Arete(6, 4));
        Graphe graphe2 = new Graphe(aretes);
        assertFalse(graphe2.estUneChaine());
    }

    @Test
    void testEstUnCycle1(){
        Graphe graphe2 = new Graphe(0);
        assertTrue(graphe2.estUnCycle());
    }

    @Test
    void testEstUnCycle2(){
        Graphe graphe2 = new Graphe(1);
        assertFalse(graphe2.estUnCycle());
    }

    @Test
    void testEstUnCycle3(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(0, 1));
        aretes.add(new Arete(1, 0));
        Graphe graphe2 = new Graphe(aretes);
        assertEquals(graphe2.nbAretes(), 1);
        assertEquals(graphe2.nbSommets(), 2);
        assertFalse(graphe2.estUnCycle());
    }

    @Test
    void testEstUnCycle4(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(0, 1));
        aretes.add(new Arete(1, 2));
        aretes.add(new Arete(2, 0));
        Graphe graphe2 = new Graphe(aretes);
        assertTrue(graphe2.estUnCycle());
    }

    @Test
    void testEstUnCycle5(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(0, 1));
        aretes.add(new Arete(1, 2));
        aretes.add(new Arete(2, 0));
        aretes.add(new Arete(3, 4));
        aretes.add(new Arete(4, 5));
        aretes.add(new Arete(5, 3));
        Graphe graphe2 = new Graphe(aretes);
        assertFalse(graphe2.estUnCycle());
    }

    @Test
    void testEstAcyclique1(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(0, 1));
        aretes.add(new Arete(1, 3));
        aretes.add(new Arete(1, 4));
        Graphe graphe2 = new Graphe(aretes);
        assertTrue(graphe2.estAcyclique());
    }

    @Test
    void testEstAcyclique2(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(0, 1));
        aretes.add(new Arete(1, 3));
        aretes.add(new Arete(1, 4));
        aretes.add(new Arete(4, 5));
        aretes.add(new Arete(5, 0));
        Graphe graphe2 = new Graphe(aretes);
        assertFalse(graphe2.estAcyclique());
    }

    @Test
    void testEstAcyclique3(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(0, 1));
        aretes.add(new Arete(1, 2));
        aretes.add(new Arete(2, 0));
        Graphe graphe2 = new Graphe(aretes);
        assertFalse(graphe2.estAcyclique());
    }

    @Test
    void testEstAcyclique4(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(3, 4));
        aretes.add(new Arete(4, 5));
        aretes.add(new Arete(5, 6));
        Graphe graphe2 = new Graphe(aretes);
        assertTrue(graphe2.estAcyclique());
    }    

    @Test
    void testGetClasseConnexite(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(0, 1));
        aretes.add(new Arete(1, 2));
        aretes.add(new Arete(2, 0));

        aretes.add(new Arete(3, 4));
        aretes.add(new Arete(4, 5));
        aretes.add(new Arete(5, 6));
        Graphe graphe2 = new Graphe(aretes);
        assertTrue(Arrays.asList(0,1,2).containsAll(graphe2.getClasseConnexite(0)) && graphe2.getClasseConnexite(0).containsAll(Arrays.asList(0,1,2)));
        assertTrue(Arrays.asList(3,4,5,6).containsAll(graphe2.getClasseConnexite(5)) && graphe2.getClasseConnexite(5).containsAll(Arrays.asList(3,4,5,6)));
    }

    @Test
    void testGetEnsembleClasseConnexite(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(0, 1));
        aretes.add(new Arete(1, 2));
        aretes.add(new Arete(2, 0));

        aretes.add(new Arete(3, 4));
        aretes.add(new Arete(4, 5));
        aretes.add(new Arete(5, 6));
        Graphe graphe2 = new Graphe(aretes);
        assertEquals(graphe2.getEnsembleClassesConnexite().size(), 2);
        assertTrue(graphe2.getEnsembleClassesConnexite().contains(new HashSet<Integer>(Arrays.asList(0,1,2))));
        assertTrue(graphe2.getEnsembleClassesConnexite().contains(new HashSet<Integer>(Arrays.asList(3,4,5,6))));
    }

    @Test
    void testEstUnArbre(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(3, 4));
        aretes.add(new Arete(4, 5));
        aretes.add(new Arete(5, 6));

        Graphe graphe2 = new Graphe(aretes);
        assertTrue(graphe2.estUnArbre());
    }    

    @Test
    void testEstUneForet1(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(0, 1));
        aretes.add(new Arete(1, 2));

        aretes.add(new Arete(3, 4));
        aretes.add(new Arete(4, 5));
        aretes.add(new Arete(5, 6));
        Graphe graphe2 = new Graphe(aretes);
        assertTrue(graphe2.estUneForet());
    }
    @Test
    void testEstUneForet2(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(0, 1));
        aretes.add(new Arete(1, 2));
        aretes.add(new Arete(0, 2));

        aretes.add(new Arete(3, 4));
        aretes.add(new Arete(4, 5));
        aretes.add(new Arete(5, 6));
        Graphe graphe2 = new Graphe(aretes);
        assertFalse(graphe2.estUneForet());
    }

    @Test
    void testEstUnIsthme(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(0, 1));
        aretes.add(new Arete(1, 2));
        aretes.add(new Arete(2, 0));

        aretes.add(new Arete(3, 4));
        Arete candidat = new Arete(4, 5);
        aretes.add(candidat);
        aretes.add(new Arete(5, 6));
        Graphe graphe2 = new Graphe(aretes);
        assertTrue(graphe2.estUnIsthme(candidat));
    }

    @Test
    void testEstUnIsthme2(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(0, 1));
        aretes.add(new Arete(1, 2));
        aretes.add(new Arete(2, 0));

        aretes.add(new Arete(3, 4));
        aretes.add(new Arete(4, 5));
        aretes.add(new Arete(5, 6));
        Graphe graphe2 = new Graphe(aretes);
        assertFalse(graphe2.estUnIsthme(new Arete(0, 1)));
    }

    @Test
    void testEstUnIsthme3(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(0, 1));
        aretes.add(new Arete(1, 2));
        aretes.add(new Arete(2, 0));

        aretes.add(new Arete(3, 4));
        aretes.add(new Arete(4, 5));
        aretes.add(new Arete(5, 6));
        Graphe graphe2 = new Graphe(aretes);
        assertFalse(graphe2.estUnIsthme(new Arete(0, 2)));
    }

    @Test
    void testEstUnIsthme4(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(0, 1));
        aretes.add(new Arete(1, 2));
        aretes.add(new Arete(2, 0));

        aretes.add(new Arete(3, 4));
        aretes.add(new Arete(4, 5));
        aretes.add(new Arete(5, 6));

        aretes.add(new Arete(7,8));
        Graphe graphe2 = new Graphe(aretes);
        assertTrue(graphe2.estUnIsthme(new Arete(7, 8)));
    }

    @Test
    void testSontAdjacents(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(0, 1));
        aretes.add(new Arete(1, 2));
        aretes.add(new Arete(2, 0));

        aretes.add(new Arete(3, 4));
        aretes.add(new Arete(4, 5));
        aretes.add(new Arete(5, 6));
        Graphe graphe2 = new Graphe(aretes);
        assertTrue(graphe2.sontAdjacents(0, 1));
        assertTrue(graphe2.sontAdjacents(1, 0));
        assertTrue(graphe2.sontAdjacents(1, 2));
        assertTrue(graphe2.sontAdjacents(2, 1));
        assertTrue(graphe2.sontAdjacents(2, 0));
        assertTrue(graphe2.sontAdjacents(0, 2));
        assertTrue(graphe2.sontAdjacents(3, 4));
        assertTrue(graphe2.sontAdjacents(4, 3));
        assertTrue(graphe2.sontAdjacents(4, 5));
        assertTrue(graphe2.sontAdjacents(5, 4));
        assertTrue(graphe2.sontAdjacents(5, 6));
        assertTrue(graphe2.sontAdjacents(6, 5));
        assertFalse(graphe2.sontAdjacents(0, 3));
        assertFalse(graphe2.sontAdjacents(3, 0));
        assertFalse(graphe2.sontAdjacents(0, 4));
        assertFalse(graphe2.sontAdjacents(4, 0));
        assertFalse(graphe2.sontAdjacents(0, 5));
        assertFalse(graphe2.sontAdjacents(5, 0));
        assertFalse(graphe2.sontAdjacents(0, 6));
        assertFalse(graphe2.sontAdjacents(6, 0));
        assertFalse(graphe2.sontAdjacents(1, 3));
        assertFalse(graphe2.sontAdjacents(3, 1));
        assertFalse(graphe2.sontAdjacents(1, 4));
        assertFalse(graphe2.sontAdjacents(4, 1));
        assertFalse(graphe2.sontAdjacents(1, 5));
    }

    @Test
    void testSequenceEstGraphe(){
        List<Integer> sequence = new ArrayList<>();
        sequence.add(2);
        sequence.add(2);
        sequence.add(2);
        assertTrue(Graphe.sequenceEstGraphe(sequence));
    }

    @Test
    void testSequenceEstGraphe2(){
        List<Integer> sequence = new ArrayList<>();
        sequence.add(2);
        sequence.add(2);
        sequence.add(2);
        sequence.add(2);
        assertTrue(Graphe.sequenceEstGraphe(sequence));
    }

    @Test
    void testSequenceEstGraphe3(){
        List<Integer> sequence = new ArrayList<>();
        sequence.add(2);
        sequence.add(2);
        sequence.add(2);
        sequence.add(2);
        sequence.add(2);
        assertTrue(Graphe.sequenceEstGraphe(sequence));
    }

    @Test
    void testSequenceEstGraphe4(){
        List<Integer> sequence = new ArrayList<>();
        sequence.add(2);
        sequence.add(2);
        sequence.add(2);
        sequence.add(2);
        sequence.add(1);
        assertFalse(Graphe.sequenceEstGraphe(sequence));
    }

    @Test
    void testSequenceEstGraphe5(){
        List<Integer> sequence = new ArrayList<>();
        sequence.add(1);
        sequence.add(2);
        sequence.add(3);
        sequence.add(4);
        sequence.add(5);
        assertFalse(Graphe.sequenceEstGraphe(sequence));
    }

    @Test
    void testSequenceEstGraphe6(){
        List<Integer> sequence = new ArrayList<>();
        sequence.add(0);
        sequence.add(1);
        sequence.add(2);
        sequence.add(3);
        sequence.add(4);
        assertFalse(Graphe.sequenceEstGraphe(sequence));
    }

    @Test
    void testSequenceEstGraphe7(){
        List<Integer> sequence = new ArrayList<>();
        sequence.add(1);
        sequence.add(2);
        sequence.add(3);
        sequence.add(4);
        sequence.add(4);
        assertFalse(Graphe.sequenceEstGraphe(sequence));
    }

    @Test
    void testSequenceEstGraphe8() {
        List<Integer> sequence = new ArrayList<>();
        sequence.add(2);
        sequence.add(2);
        sequence.add(3);
        sequence.add(3);
        sequence.add(4);
        assertTrue(Graphe.sequenceEstGraphe(sequence));
    }

    @Test
    void testSequenceEstGraphe9() {
        List<Integer> sequence = new ArrayList<>();
        sequence.add(1);
        sequence.add(1);
        sequence.add(2);
        sequence.add(2);
        sequence.add(2);
        sequence.add(3);
        assertFalse(Graphe.sequenceEstGraphe(sequence));
    }

    @Test
    void testSequenceEstGraphe10() {
        List<Integer> sequence = new ArrayList<>();
        sequence.add(0);
        sequence.add(1);
        sequence.add(1);
        sequence.add(2);
        assertTrue(Graphe.sequenceEstGraphe(sequence));
    }

    @Test
    void testOrdonnerSequence() {
        List<Integer> sequence = new ArrayList<>();
        sequence.add(2);
        sequence.add(2);
        sequence.add(2);
        sequence.add(2);
        sequence.add(1);

        System.out.println("sequence avant ordonnancement" + sequence);
        System.out.println("sequence apres ordonnancement" + Graphe.ordonnerSequence(sequence));
        assertEquals(1, Graphe.ordonnerSequence(sequence).get(0));
        assertEquals(2, Graphe.ordonnerSequence(sequence).get(1));
    }

    @Test
    void testFusionnerSommets(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(0, 1));
        aretes.add(new Arete(1, 2));
        aretes.add(new Arete(2, 0));

        aretes.add(new Arete(3, 4));
        aretes.add(new Arete(4, 5));
        aretes.add(new Arete(5, 6));
        Graphe graphe2 = new Graphe(aretes);
        System.out.println("avant fusion" + graphe2);
        graphe2.fusionnerSommets(3, 2);
        System.out.println("apres fusion" + graphe2);

        assertTrue(graphe2.sontAdjacents(0, 1));
        assertTrue(graphe2.sontAdjacents(1, 0));
        assertTrue(graphe2.sontAdjacents(1, 2));
        assertTrue(graphe2.sontAdjacents(2, 1));
        assertTrue(graphe2.sontAdjacents(2, 0));
        assertTrue(graphe2.sontAdjacents(0, 2));
        assertTrue(graphe2.sontAdjacents(2, 4));
    }

    @Test
    void testSontIsomorphes(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(0, 1));
        aretes.add(new Arete(1, 2));
        aretes.add(new Arete(3, 4));
        aretes.add(new Arete(5, 6));

        Graphe graphe2 = new Graphe(aretes);
        assertTrue(Graphe.sontIsomorphes(graphe2, graphe2));
    }

    @Test
    void testSontIsomorphes2(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(1, 2));
        aretes.add(new Arete(1, 3));
        aretes.add(new Arete(2, 3));
        aretes.add(new Arete(2, 4));
        aretes.add(new Arete(3, 5));
        aretes.add(new Arete(4, 5));


        List<Arete> aretes2 = new ArrayList<>();
        aretes2.add(new Arete(6, 7));
        aretes2.add(new Arete(6, 8));
        aretes2.add(new Arete(7, 8));
        aretes2.add(new Arete(7, 9));
        aretes2.add(new Arete(8, 10));
        aretes2.add(new Arete(9, 10));
        Graphe graphe2 = new Graphe(aretes);
        Graphe graphe3 = new Graphe(aretes2);
        assertTrue(Graphe.sontIsomorphes(graphe2, graphe3));
    }

    @Test
    void testSontIsomorphes3(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(1, 2));
        aretes.add(new Arete(2, 3));
        aretes.add(new Arete(3, 4));
        aretes.add(new Arete(3, 5));
        aretes.add(new Arete(5, 4));


        List<Arete> aretes2 = new ArrayList<>();
        aretes2.add(new Arete(1, 2));
        aretes2.add(new Arete(2, 3));
        aretes2.add(new Arete(3, 4));
        aretes2.add(new Arete(2, 5));
        aretes2.add(new Arete(5, 4));

        Graphe graphe2 = new Graphe(aretes);
        Graphe graphe3 = new Graphe(aretes2);
        assertFalse(Graphe.sontIsomorphes(graphe2, graphe3));
    }

    @Test
    void testGetSequenceDegre(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(0, 1));
        aretes.add(new Arete(1, 2));
        aretes.add(new Arete(2, 0));
        aretes.add(new Arete(3, 4));
        aretes.add(new Arete(4, 5));
        aretes.add(new Arete(5, 6));
        Graphe graphe2 = new Graphe(aretes);
        assertEquals(1, graphe2.getSequenceDegre().get(0));
        assertEquals(1, graphe2.getSequenceDegre().get(1));
        assertEquals(2, graphe2.getSequenceDegre().get(2));
        assertEquals(2, graphe2.getSequenceDegre().get(3));
        assertEquals(2, graphe2.getSequenceDegre().get(4));
        assertEquals(2, graphe2.getSequenceDegre().get(5));
        assertEquals(2, graphe2.getSequenceDegre().get(6));
    }



    @Test
    void testGetSequenceDegre2(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(1, 2));
        aretes.add(new Arete(1, 3));
        aretes.add(new Arete(2, 3));
        aretes.add(new Arete(2, 4));
        aretes.add(new Arete(3, 5));
        aretes.add(new Arete(4, 5));


        List<Arete> aretes2 = new ArrayList<>();
        aretes2.add(new Arete(6, 7));
        aretes2.add(new Arete(6, 8));
        aretes2.add(new Arete(7, 8));
        aretes2.add(new Arete(7, 9));
        aretes2.add(new Arete(8, 10));
        aretes2.add(new Arete(9, 10));
        Graphe graphe2 = new Graphe(aretes);
        Graphe graphe3 = new Graphe(aretes2);
        System.out.println(graphe2.getSequenceDegre());
        System.out.println(graphe3.getSequenceDegre());
    }

    @Test
    void testparcoursSansRepetition1(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(1, 2));
        aretes.add(new Arete(1, 3));
        aretes.add(new Arete(2, 3));
        aretes.add(new Arete(2, 4));
        aretes.add(new Arete(3, 5));
        aretes.add(new Arete(4, 5));
        Graphe graphe2 = new Graphe(aretes);

        List<Integer> parcoursAttendu = Arrays.asList(1, 3, 5);
        List<Integer> resultat = graphe2.parcoursSansRepetition(1,5,false);
        
        assertTrue(collectionsDansLeMemeOrdre(parcoursAttendu, resultat));
    }

    @Test
    void testparcoursSansRepetitionSousListe1(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(1, 2));
        aretes.add(new Arete(2, 3));
        aretes.add(new Arete(2, 5));

        Graphe graphe2 = new Graphe(aretes);

        List<Integer> sousListe = Arrays.asList(1, 2, 3, 2, 5);

        List<Integer> resultat = graphe2.parcoursSansRepetition(sousListe);
        
        assertTrue(collectionsDansLeMemeOrdre(new ArrayList<>(), resultat));
    }

    @Test
    void testparcoursSansRepetitionSousListe2(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(1, 2));
        aretes.add(new Arete(2, 3));
        aretes.add(new Arete(2, 5));
        aretes.add(new Arete(3, 6));
        aretes.add(new Arete(5, 6));
        aretes.add(new Arete(5, 7));

        Graphe graphe2 = new Graphe(aretes);

        List<Integer> sousListe = Arrays.asList(1, 3, 7);

        List<Integer> parcoursAttendu1 = Arrays.asList(1, 2, 3, 6, 5, 7);
        List<Integer> parcoursAttendu2 = Arrays.asList(1, 2, 3, 2, 5, 7);

        List<Integer> resultat = graphe2.parcoursSansRepetition(sousListe);
        
        assertTrue(collectionsDansLeMemeOrdre(parcoursAttendu1, resultat) || collectionsDansLeMemeOrdre(parcoursAttendu2, resultat));
    }

    @Test
    void testparcoursSansRepetitionSousListe3(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(1, 2));
        aretes.add(new Arete(2, 3));
        aretes.add(new Arete(1, 4));
        aretes.add(new Arete(4, 3));

        Graphe graphe2 = new Graphe(aretes);

        List<Integer> sousListe = Arrays.asList(1, 3, 2);

        List<Integer> parcoursAttendu = Arrays.asList(1, 4, 3, 2);

        List<Integer> resultat = graphe2.parcoursSansRepetition(sousListe);
        
        assertTrue(collectionsDansLeMemeOrdre(parcoursAttendu, resultat));
    }

    @Test
    void testparcoursSansRepetitionSousListe4(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(1, 2));
        aretes.add(new Arete(2, 3));
        aretes.add(new Arete(1, 4));
        aretes.add(new Arete(4, 3));

        Graphe graphe2 = new Graphe(aretes);

        List<Integer> sousListe = Arrays.asList(1, 2, 3);

        List<Integer> parcoursAttendu = Arrays.asList(1, 2, 3);

        List<Integer> resultat = graphe2.parcoursSansRepetition(sousListe);
        
        assertTrue(collectionsDansLeMemeOrdre(parcoursAttendu, resultat));
    }

    @Test
    void testparcoursSansRepetitionSousListe5(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(1, 2));
        aretes.add(new Arete(2, 3));

        Graphe graphe2 = new Graphe(aretes);

        List<Integer> sousListe = Arrays.asList(1, 3, 2);

        List<Integer> parcoursAttendu = Arrays.asList();

        List<Integer> resultat = graphe2.parcoursSansRepetition(sousListe);
        
        assertTrue(collectionsDansLeMemeOrdre(parcoursAttendu, resultat));
    }

    @Test
    void testparcoursSansRepetition2(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(1, 2, new RouteTerrestre(new Ville("1", false), new Ville("2", false), null, 2)));
        aretes.add(new Arete(2, 3, new RouteTerrestre(new Ville("2", false), new Ville("3", false), null, 2)));
        aretes.add(new Arete(1, 3, new RouteTerrestre(new Ville("1", false), new Ville("3", false), null, 5)));


        Graphe graphe2 = new Graphe(aretes);
        List<Integer> resultat = graphe2.parcoursSansRepetition(1, 3, true);
        List<Integer> parcoursAttendu = Arrays.asList(1,2,3);

        assertTrue(collectionsDansLeMemeOrdre(parcoursAttendu, resultat));
    }

    @Test
    void testparcoursSansRepetition3(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(1, 2, new RouteTerrestre(new Ville("1", false), new Ville("2", false), null, 2)));
        aretes.add(new Arete(2, 3, new RouteTerrestre(new Ville("2", false), new Ville("3", false), null, 2)));
        aretes.add(new Arete(1, 3, new RouteTerrestre(new Ville("1", false), new Ville("3", false), null, 3)));


        Graphe graphe2 = new Graphe(aretes);
        List<Integer> resultat = graphe2.parcoursSansRepetition(1, 3, true);
        List<Integer> parcoursAttendu = Arrays.asList(1,3);

        assertTrue(collectionsDansLeMemeOrdre(parcoursAttendu, resultat));
    }

    @Test
    void testEstComplet1(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(1, 2));
        aretes.add(new Arete(1, 3));
        aretes.add(new Arete(1, 4));

        aretes.add(new Arete(2, 1));
        aretes.add(new Arete(2, 3));
        aretes.add(new Arete(2, 4));

        aretes.add(new Arete(3, 1));
        aretes.add(new Arete(3, 2));
        aretes.add(new Arete(3, 4));

        aretes.add(new Arete(4, 1));
        aretes.add(new Arete(4, 2));
        aretes.add(new Arete(4, 3));
        

        Graphe graphe2 = new Graphe(aretes);
        assertTrue(graphe2.estComplet());
    }

    @Test
    void testEstComplet2(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(1, 2));
        aretes.add(new Arete(1, 3));
        aretes.add(new Arete(1, 4));

        aretes.add(new Arete(2, 1));
        aretes.add(new Arete(2, 3));
        aretes.add(new Arete(2, 4));

        Graphe graphe2 = new Graphe(aretes);
        assertFalse(graphe2.estComplet());
    }

    @Test
    void testDegreMax(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(1, 2));
        aretes.add(new Arete(1, 3));
        aretes.add(new Arete(1, 4));
        aretes.add(new Arete(3, 4));

        Graphe graphe2 = new Graphe(aretes);
        assertEquals(graphe2.degreMax(), 3);
    }

    @Test
    void testEstSimple1(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(1, 2));
        aretes.add(new Arete(1, 3));
        aretes.add(new Arete(1, 4));
        aretes.add(new Arete(3, 4));

        Graphe graphe2 = new Graphe(aretes);

        assertTrue(graphe2.estSimple());
    }

    @Test
    void testEstSimple2(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(1, 2));
        aretes.add(new Arete(1, 3));
        aretes.add(new Arete(1, 1));
        aretes.add(new Arete(3, 4));

        Graphe graphe2 = new Graphe(aretes);

        assertFalse(graphe2.estSimple());
    }

    @Test
    void testParcoursSansRepetitionPions1(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(1, 2, new RouteTerrestre(new Ville("1", false), new Ville("2", false), null, 2)));
        aretes.add(new Arete(2, 3, new RouteTerrestre(new Ville("2", false), new Ville("3", false), null, 2)));
        aretes.add(new Arete(1, 3, new RouteMaritime(new Ville("1", false), new Ville("3", false), null, 3)));

        Graphe graphe2 = new Graphe(aretes);

        List<Integer> resultat = graphe2.parcoursSansRepetition(1, 3, 3,4);
        List<Integer> parcoursAttendu = Arrays.asList(1,3);

        assertTrue(collectionsDansLeMemeOrdre(resultat, parcoursAttendu));
    }

    @Test
    void testParcoursSansRepetitionPions2(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(1, 2, new RouteTerrestre(new Ville("1", false), new Ville("2", false), null, 2)));
        aretes.add(new Arete(2, 3, new RouteTerrestre(new Ville("2", false), new Ville("3", false), null, 2)));
        aretes.add(new Arete(1, 3, new RouteMaritime(new Ville("1", false), new Ville("3", false), null, 3)));

        Graphe graphe2 = new Graphe(aretes);

        List<Integer> resultat = graphe2.parcoursSansRepetition(1, 3, 4,3);
        List<Integer> parcoursAttendu = Arrays.asList(1,3);

        assertTrue(collectionsDansLeMemeOrdre(resultat, parcoursAttendu));
    }

    @Test
    void testParcoursSansRepetitionPions3(){
        List<Arete> aretes = new ArrayList<>();
        aretes.add(new Arete(1, 2, new RouteTerrestre(new Ville("1", false), new Ville("2", false), null, 2)));
        aretes.add(new Arete(2, 3, new RouteTerrestre(new Ville("2", false), new Ville("3", false), null, 2)));
        aretes.add(new Arete(1, 3, new RouteMaritime(new Ville("1", false), new Ville("3", false), null, 3)));

        Graphe graphe2 = new Graphe(aretes);

        List<Integer> resultat = graphe2.parcoursSansRepetition(1, 3, 4,2);
        List<Integer> parcoursAttendu = Arrays.asList(1,2,3);

        assertTrue(collectionsDansLeMemeOrdre(resultat, parcoursAttendu));
    }

/*
    @Test
    void testGetGraphe{
        List<Route> routes = new ArrayList<>();
        routes.add(new Route(0, 1));
        List<Ville> villes = new ArrayList<>();
        villes.add(new Ville(Sydney, 0));
        Plateau plateau = new Plateau(3,3);

    }

 */
    
    
    @Disabled
    @Test
    void testGetVoisins() {
        /*
        Set<Integer> voisins0 = graphe.getVoisins(0);
        assertEquals(2, voisins0.size());
        assertTrue(voisins0.contains(1));
        assertTrue(voisins0.contains(3));
        List<Integer> voisins1 = graphe.getVoisins(1);
        assertEquals(2, voisins1.size());
        assertTrue(voisins1.contains(0));
        assertTrue(voisins1.contains(2));
        List<Integer> voisins2 = graphe.getVoisins(2);
        assertEquals(2, voisins2.size());
        assertTrue(voisins2.contains(1));
        assertTrue(voisins2.contains(3));
        List<Integer> voisins3 = graphe.getVoisins(3);
        assertEquals(2, voisins3.size());
        assertTrue(voisins3.contains(0));
        assertTrue(voisins3.contains(2));
        List<Integer> voisins8 = graphe.getVoisins(8);
        assertEquals(1, voisins8.size());
        assertTrue(voisins8.contains(42));
        List<Integer> voisins42 = graphe.getVoisins(42);
        assertEquals(1, voisins42.size());
        assertTrue(voisins42.contains(8));

         */
    }

    @Test
    void testEnsembleSommets(){
        /*
        List<Integer> sommets = graphe.ensembleSommets();
        assertEquals(5, sommets.size());
        assertTrue(sommets.contains(0));
        assertTrue(sommets.contains(1));
        assertTrue(sommets.contains(2));
        assertTrue(sommets.contains(3));
        assertTrue(sommets.contains(8));
        assertTrue(sommets.contains(42));

         */
    }
}