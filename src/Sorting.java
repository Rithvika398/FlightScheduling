import java.util.Comparator;

public class Sorting implements Comparator<Plane> {
	public int compare(Plane a, Plane b)
    {
        return b.getPriority() - a.getPriority();
    }
}
