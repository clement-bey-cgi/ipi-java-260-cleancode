import java.util.LinkedList;
import java.util.List;

public abstract class AbstractFrame {
	
	protected FrameDetail frameDetail = FrameDetail.NONE;
	
	protected Throw firstThrow;
	
	protected Throw secondThrow;
	
	/** Renvoi le bonus de point apport� par les lancers suivants en cas de strike. */
	abstract int getThisNextFrameStrikeBonus(LinkedList<AbstractFrame> frames);
	
	/** Renvoi le bonus de point apport� par les lancers suivants en cas de strike. */
	abstract int getThisNextFrameSpareBonus(LinkedList<AbstractFrame> frames);
	
	/** Analyse les deux lancers pour �tablir s'il y a faute strike ou spare.*/
	public void searchForParticularitiesInThrows() {
		if (this.getFirstThrow().getKeelDownCount() == 10) {
			this.frameDetail = FrameDetail.STRIKE;
		}
		
		else if (ThrowDetail.FAULT.equals(this.firstThrow.getThrowDetail()) 
				|| (!this.frameDetail.equals(FrameDetail.STRIKE) && ThrowDetail.FAULT.equals(this.secondThrow.getThrowDetail()))) {
			this.frameDetail = FrameDetail.FAULT;
		}
		
		else if (this.getFirstThrow().getKeelDownCount() + this.getSecondThrow().getKeelDownCount() == 10) {
			this.frameDetail = FrameDetail.SPARE;
		}
	}
	
    //---------------------------------------------------------
    //---------------------GETTERS SETTERS---------------------

	public FrameDetail getFrameDetail() {
		return frameDetail;
	}

	public void setFrameDetail(FrameDetail frameDetail) {
		this.frameDetail = frameDetail;
	}

	public Throw getFirstThrow() {
		return firstThrow;
	}

	public void setFirstThrow(Throw firstThrow) {
		this.firstThrow = firstThrow;
	}

	public Throw getSecondThrow() {
		return secondThrow;
	}

	public void setSecondThrow(Throw secondThow) {
		this.secondThrow = secondThow;
	}
}
