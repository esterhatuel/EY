package algorithm;

import java.util.Stack;

import classes.State;

public class DFS<T> extends CommonSearcher<T>{


	protected void newSearch() {
		openlist= new Stack<State<T>>();
		setEvaluatedNodes(0);
		
	}

	protected boolean addToopenList(State<T> initailstate) {
		return ((Stack<State<T>>)openlist).add(initailstate);
	}

	protected State<T> popOpenList() {
		setEvaluatedNodes(getNUmberOfNodesEvaluted()+1);
		State<T> State= ((Stack<State<T>>) openlist).pop();
		return State;
	}

}
