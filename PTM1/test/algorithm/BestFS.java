package algorithm;

import java.util.PriorityQueue;

import classes.State;

public class BestFS<T> extends CommonSearcher<T>{

	
	public BestFS() {
		newSearch();
	}

	@Override	
	protected void newSearch() {
		openlist= new PriorityQueue<State<T>>();
		setEvaluatedNodes(0);
	}
		
	@Override
	protected boolean addToopenList(State<T> initailstate) {
		return ((PriorityQueue<State<T>>)openlist).add(initailstate);
	}


	@Override
	protected State<T> popOpenList() {
		setEvaluatedNodes(getNUmberOfNodesEvaluted()+1);
		State<T> State= ((PriorityQueue<State<T>>) openlist).poll();
		return State;
	}

}