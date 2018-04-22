import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BowlingGame {

    private int score;
    
    private static final int BASE_SCORE_SPARE_STRIKE = 10; 
    
    LinkedList<AbstractFrame> frames = new LinkedList<>();
    
    /** Calcul le score de la partie. */
    public int calculateThisScore() {
    	int result = 0;
    	LinkedList<AbstractFrame> analyzedFrames = this.getFrames();
    	
    	for (AbstractFrame frame : analyzedFrames) {
    		if (FrameDetail.FAULT.equals(frame.getFrameDetail())) {
    			continue;
    		}
    		
    		if (FrameDetail.STRIKE.equals(frame.getFrameDetail())) {
    			result += BASE_SCORE_SPARE_STRIKE + frame.getThisNextFrameStrikeBonus(analyzedFrames);
    		}
    		else if (FrameDetail.SPARE.equals(frame.getFrameDetail())) {
    			result += BASE_SCORE_SPARE_STRIKE + frame.getThisNextFrameSpareBonus(analyzedFrames);
    		}
    		else {
    			result += frame.getFirstThrow().getKeelDownCount() + frame.getSecondThrow().getKeelDownCount();
    		}
    	}
    	
    	return result;
    }
    
    //---------------------------------------------------------
    //---------------------CONSTRUCTEUR-----------------------
    
    /** Constructeur pour les test. */
    public BowlingGame(CasualFrame[] casualFrames, FinalFrame finalFrame) {
    	int i = 0;
    	while (i <= 8) {
    		this.frames.addLast(casualFrames[i]);
        	if (i == 8) {
        		this.frames.addLast(finalFrame);
        	}
        	i++;
    	}
 
    	this.score = calculateThisScore();
    }

    //---------------------------------------------------------
    //---------------------GETTERS SETTERS---------------------
    
    public int getScore() {
        return score;
    }

	public LinkedList<AbstractFrame> getFrames() {
		return frames;
	}

	public void setFrames(LinkedList<AbstractFrame> frames) {
		this.frames = frames;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
