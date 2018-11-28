package algorithm;

import java.util.LinkedList;
import java.util.Queue;

import classes.State;

public class BFS<T> extends CommonSearcher<T> {

	public BFS() {
		newSearch();
	}
	@Override
	protected void newSearch() {
		openlist= new LinkedList<State<T>>();
		setEvaluatedNodes(0);
	}

	@Override
	protected boolean addToopenList(State<T> initailstate) {
		// TODO Auto-generated method stub
		return ((Queue<State<T>>)openlist).add(initailstate);
	}

	@Override
	protected State<T> popOpenList() {
		// TODO Auto-generated method stub
		setEvaluatedNodes(getNUmberOfNodesEvaluted()+1);
		State<T> State= ((Queue<State<T>>) openlist).poll();
		return State;
	}

}
