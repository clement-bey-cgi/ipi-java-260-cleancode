

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class BowlingGameTest {

    BowlingGame game;
    
    /** On initialise toutes les frames en vide avant chaque méthode. On les modifiera au besoin dans chaque Test.*/
    @Before
    public void clean() {
    	this.game = null;
    	CasualFrame[] casualFrames = {
    		new CasualFrame(0, 0, ThrowDetail.NONE, ThrowDetail.NONE),
    		new CasualFrame(0, 0, ThrowDetail.NONE, ThrowDetail.NONE),
    		new CasualFrame(0, 0, ThrowDetail.NONE, ThrowDetail.NONE),
    		new CasualFrame(0, 0, ThrowDetail.NONE, ThrowDetail.NONE),
    		new CasualFrame(0, 0, ThrowDetail.NONE, ThrowDetail.NONE),
    		new CasualFrame(0, 0, ThrowDetail.NONE, ThrowDetail.NONE),
    		new CasualFrame(0, 0, ThrowDetail.NONE, ThrowDetail.NONE),
    		new CasualFrame(0, 0, ThrowDetail.NONE, ThrowDetail.NONE),
    		new CasualFrame(0, 0, ThrowDetail.NONE, ThrowDetail.NONE)
    	};
    	FinalFrame finalFrame = new FinalFrame(0, 0, 0, ThrowDetail.NONE, ThrowDetail.NONE, ThrowDetail.NONE);
    	this.game = new BowlingGame(casualFrames, finalFrame);
    }

    @Test
    public void queDesGoutieresDonne0Points() { 
        assertThat(game.getScore(), is(0));
    	assertThat(game.getFrames().size(), is(10));
    }
    
    /** Compte des "trous"*/
    @Test
    public void deuxQuillesEtDesFautes(){
    	game.getFrames().remove(0);
    	game.getFrames().add(0, new CasualFrame(1, 0, ThrowDetail.NONE, ThrowDetail.NONE));
    	
    	game.getFrames().remove(3);
    	game.getFrames().add(3, new CasualFrame(0, 1, ThrowDetail.NONE, ThrowDetail.NONE));
    	
    	game.getFrames().remove(5);
    	game.getFrames().add(5, new CasualFrame(0, 1, ThrowDetail.FAULT, ThrowDetail.NONE));
    	
    	game.getFrames().remove(6);
    	game.getFrames().add(6, new CasualFrame(0, 1, ThrowDetail.FAULT, ThrowDetail.NONE));
    	
        assertThat(game.calculateThisScore(), is(2));
    	assertThat(game.getFrames().size(), is(10));
    }
    
    /** Test des mécanique de compte Strike et Spare. */
    @Test
    public void strikeDoubleEtSpareSuiviParFaute(){
    	game.getFrames().remove(0);
    	game.getFrames().add(0, new CasualFrame(10, 0, ThrowDetail.NONE, ThrowDetail.NONE));
    	
    	game.getFrames().remove(1);
    	game.getFrames().add(1, new CasualFrame(10, 0, ThrowDetail.NONE, ThrowDetail.NONE));
    	
    	game.getFrames().remove(2);
    	game.getFrames().add(2, new CasualFrame(7, 3, ThrowDetail.NONE, ThrowDetail.NONE));
    	
    	game.getFrames().remove(3);
    	game.getFrames().add(3, new CasualFrame(1, 0, ThrowDetail.NONE, ThrowDetail.FAULT));
    	
    	/** (10+10) strike + (10+7+3) strike + (10 + 1) spare + 0 car faute*/
        assertThat(game.calculateThisScore(), is(51));
    	assertThat(game.getFrames().size(), is(10));
    }
    
    /** Le dernier frame rajouté est incohérent, car une faute = 0 quilles. */
    @Test
    public void avecUnLancerIncoherent(){
    	game.getFrames().remove(0);
    	game.getFrames().add(0, new CasualFrame(10, 0, ThrowDetail.NONE, ThrowDetail.NONE));
    	
    	game.getFrames().remove(1);
    	game.getFrames().add(1, new CasualFrame(10, 0, ThrowDetail.NONE, ThrowDetail.NONE));
    	
    	game.getFrames().remove(2);
    	game.getFrames().add(2, new CasualFrame(7, 3, ThrowDetail.NONE, ThrowDetail.NONE));
    	
    	game.getFrames().remove(3);
    	game.getFrames().add(3, new CasualFrame(10, 0, ThrowDetail.FAULT, ThrowDetail.NONE));
    	
    	/** (10+10) strike + (10+7+3) strike + (10 + 0) spare + 0 car faute*/
        assertThat(game.calculateThisScore(), is(50));
    	assertThat(game.getFrames().size(), is(10));
    }
    
    /** Strike sur la derniere frame = 3eme lancer accordé*/
    @Test
    public void strikeSurLaDerniereFrame(){
    	game.getFrames().remove(9);
    	game.getFrames().addLast(new FinalFrame(10, 0, 10, ThrowDetail.NONE, ThrowDetail.NONE, ThrowDetail.NONE));
    	
    	assertThat(game.getFrames().size(), is(10));
        assertThat(game.calculateThisScore(), is(20));
    }
    
    /** Strike sur la derniere frame = 3eme lancer accordé mais faute dessus. Avec données incohérentes (faute = 0 quilles).*/
    @Test
    public void strikeSurLaDerniereFrameMaisFaute3emeLancer(){
    	game.getFrames().remove(9);
    	game.getFrames().addLast(new FinalFrame(10, 0, 10, ThrowDetail.NONE, ThrowDetail.NONE, ThrowDetail.FAULT));
    	
    	assertThat(game.getFrames().size(), is(10));
        assertThat(game.calculateThisScore(), is(10));
        assertThat(game.getFrames().get(9), instanceOf(FinalFrame.class));
    }
    
    /** Spare sur la derniere frame = 3eme lancer accordé*/
    @Test
    public void spareSurLaDerniereFrame(){
    	game.getFrames().remove(9);
    	game.getFrames().addLast(new FinalFrame(6, 4, 8, ThrowDetail.NONE, ThrowDetail.NONE, ThrowDetail.NONE));
    	
    	assertThat(game.getFrames().size(), is(10));
        assertThat(game.calculateThisScore(), is(18));
    }
    
    /** Test de notre matcher personnalisé. */
    @Test
    public void testDuMatcherPersonnalise(){
    	game.getFrames().remove(9);
    	game.getFrames().addLast(new FinalFrame(6, 4, 8, ThrowDetail.NONE, ThrowDetail.NONE, ThrowDetail.NONE));
    	
    	assertThat(game, is(RespectFrameImplementation.respectFrameImplementation()));
        assertThat(game.calculateThisScore(), is(18));
    }
}
