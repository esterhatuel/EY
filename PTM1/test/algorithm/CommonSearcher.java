package algorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import classes.*;
import interfaces.*;

public abstract class CommonSearcher<T> implements Searcher<T> {
	
	protected Collection<State<T>> openlist;
	private int evaluationNodes;
	public CommonSearcher() {newSearch();}
	
	protected abstract void newSearch();
	protected abstract boolean addToopenList(State<T> initailstate);
	protected abstract State<T> popOpenList();
	
	protected int getvalutedNodes() { return evaluationNodes;}
	protected void setEvaluatedNodes( int evaluatedNodes) {this.evaluationNodes= evaluatedNodes;}
	
	
	protected Solution<T> backTrace(State<T> goalState, State<T> startState) throws Exception {
		ArrayList<State<T>> arrayList=new ArrayList<>();
		arrayList.add(goalState);
		///Maybe change arrayList.get(0)!=null to arrayList.get(0).getCameFrom()!=null
		//Or even change arrayList.get(0)!=startState   ~
		while(arrayList.get(0)!=null&&!arrayList.get(0).equals(startState)&&arrayList.get(0).getCameFrom()!=null)
		{
			arrayList.add(0,(State<T>) arrayList.get(0).getCameFrom());
		}
		if(arrayList.isEmpty())
		{
			throw new Exception("backTrace is empty");
		}
		return new Solution<T>(arrayList);
	}
	
	@Override
	public Solution<T> search(Searchable<T> s) {
		newSearch();
		addToopenList(s.getInitialState());
		Set<State<T>> closedSet=new HashSet<State<T>>();
		while(!openlist.isEmpty()){
			//System.out.println("while(!openlist.isEmpty())");
			State<T> n=popOpenList();// dequeue
			//System.out.println(n.getState().toString());
			closedSet.add(n);
			if(s.IsGoalState(n))
			{
				try {
					//System.out.println("s.IsGoalState(n)");
					return backTrace(n, s.getInitialState());
					// private method, back traces through the parents
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Collection<State<T>> successors=s.getAllPossibleStates(n);//however it is implemented
			//System.out.println("successors.size() "+successors.size());
			for(State<T> state: successors){
				//System.out.println("for(State<T> state: successors){");
				if(!closedSet.contains(state))
				{							
					//System.out.println("!closedSet.contains(state)");
					
					if(!openlist.contains(state)){
						addToopenList(state);
						//System.out.println("addtolist");

					}
					else 
					{
						if(openlist.removeIf(new Predicate<State<T>>() 
						{
							@Override
							public boolean test(State<T> t) {
								return t.getCost()>state.getCost()&&t.equals(state);
							}
						}))
						{
							addToopenList(state);
							//System.out.println("addtolist");
						}
					}
				}
				
			}
		}
		return null;
	}
	
	@Override
	public int getNUmberOfNodesEvaluted() {
		return getvalutedNodes();
	}

	
}
