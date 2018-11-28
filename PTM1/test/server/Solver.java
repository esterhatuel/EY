package server;

import classes.Solution;
import interfaces.Searchable;

public interface Solver<T> {
	
	public Solution<T> solve(Searchable<T> s);
}
