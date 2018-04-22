import java.util.Iterator;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/** Matcher Personnalisé pour vérifier qu'une BowlingGame rempli certains critères : 
 * - 10 frames Total dont les 9 premiers sont des casualFrame + 1 FinalFrame
 * - le nombre de quille de chaque lancer ne peut pas être supérieur a 10*/
public class RespectFrameImplementation extends TypeSafeMatcher<BowlingGame> {

	@Override
	public void describeTo(Description description) {
		description.appendText("is a valid BowlingGame");
	}

	@Override
	protected boolean matchesSafely(BowlingGame o) {
		if (o.getFrames().size() != 10) {
			return false;
		}
		
		Iterator<AbstractFrame> framesIterator = o.getFrames().iterator();
		AbstractFrame currentFrame;
		int i=0;
		int keelsFirstThrow;
		int keelsSecondThrow;
		int keelThirdThrow;
		
		while (framesIterator.hasNext()) {
			currentFrame = framesIterator.next();
			keelsFirstThrow = currentFrame.getFirstThrow().getKeelDownCount();
			keelsSecondThrow = currentFrame.getSecondThrow().getKeelDownCount();
			
			// KeelsCountDowVerification
			if ((keelsFirstThrow < 0 || keelsFirstThrow > 10)
			|| (keelsSecondThrow < 0 || keelsSecondThrow > 10)) {
				return false;
			}
			
			if (i == 9) {
				if (!(currentFrame instanceof FinalFrame)) return false;
			}
			else {
				if (!(currentFrame instanceof CasualFrame)) return false;
			}
			
			i++;
		}
		
		return true;
	}
	
	@Factory
	public static <T> Matcher<BowlingGame> respectFrameImplementation() {
		return new RespectFrameImplementation();
	}

}
