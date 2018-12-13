
import javax.swing.JLabel;

public class TaggedLabel extends JLabel {
	
	TaggedLabel(String text)
	{super(text);}
	
	public int lineNumber;
	public boolean breakpointActive = false;
	
	public void toggleBreakpoint()
	{breakpointActive = !breakpointActive;}
	
}
