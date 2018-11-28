package interfaces;

import classes.Solution;

public interface Searcher<T> {

	public Solution<T> search(Searchable<T> s);
	public int getNUmberOfNodesEvaluted();
	
}
