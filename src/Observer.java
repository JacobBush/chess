
/**
	View interface
 */
public interface Observer {
	public void update(Object observable, String message);
    public void update(Object observable);
}
