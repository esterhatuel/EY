package Searchable;

import java.util.*;
import java.util.function.Predicate;

import classes.*;
import interfaces.Searchable;

public class PipeGameSearchable implements Searchable<PipeGameBoard>{

	   private PipeGameBoard PipeGameBoard;
	   private HashSet<String> hashSet;
	   
	   public PipeGameSearchable(final PipeGameBoard PipeGameBoard) {
	      this.PipeGameBoard = PipeGameBoard.clone();
	      hashSet=new HashSet<>();
	   }

	   @Override
	   public State<PipeGameBoard> getInitialState() {
	      this.hashSet=new HashSet<>();
	      return new State<PipeGameBoard>(this.PipeGameBoard, 0, null);
	   }

	   @Override
	   public Collection<State<PipeGameBoard>> getAllPossibleStates(State<PipeGameBoard> s) {
	      PipeGameBoard gameMap=s.getState();
	      Collection<State<PipeGameBoard>> collection=new ArrayList<State<PipeGameBoard>>();
	      Collection<Tile> starts=gameMap.getStartsIndex();
	      Collection<Tile> goals=gameMap.getGoalsIndex();
	      double distancestart=Double.POSITIVE_INFINITY;
	      double distancegoal=Double.POSITIVE_INFINITY;
	      for(Tile start:starts)
	      {
	         distancestart=Math.min(distancestart, gameMap.CountHowManyPossibleExits(start));
	         
	      }
	      for(Tile goal:goals)
	      {
	         distancegoal=Math.min(distancestart, gameMap.CountHowManyPossibleExits(goal));
	         
	      }
	      if(distancestart<=distancegoal)
	      {
	         collection.addAll(getAllPossibleStatesHelper(s,starts,new Predicate<Tile>() {
	            
	            @Override
	            public boolean test(Tile t) {
	               return t.isStart();
	            }
	         }));
	      }
	      else
	      {
	         collection.addAll(getAllPossibleStatesHelper(s,goals,new Predicate<Tile>() {
	            
	            @Override
	            public boolean test(Tile t) {
	               return t.isGoal();
	            }
	         }));
	         
	      }
	      return collection;
	   }

	   @Override
	   public Boolean IsGoalState(State<PipeGameBoard> s) {
	      return s.getState().isSolevd();
	   }

	   @Override
	   public boolean equals(Object obj) {
	      if (!(obj instanceof PipeGameSearchable))
	         return false;  
	      if (obj == this)
	         return true;
	      return this.equals(((PipeGameSearchable) obj));
	   }
	   
	   public boolean equals(PipeGameSearchable obj) {
	      return this.PipeGameBoard.toString().equals(obj.PipeGameBoard.toString());
	   }

	   @Override
	   public int hashCode() {
	      return this.PipeGameBoard.toString().hashCode();
	   }

	   @Override
	   public String toString() {
	      return this.PipeGameBoard.toString();
	   }
	   
	   private Collection<State<PipeGameBoard>> getAllPossibleStatesHelper(State<PipeGameBoard> s,Collection<Tile> Tiles,Predicate<Tile> predicate)
	   {
	      PipeGameBoard gameMap=s.getState();
	      Collection<State<PipeGameBoard>> collection=new ArrayList<State<PipeGameBoard>>();
	      Queue<Tile> queue=new LinkedList<>();
	      HashSet<Tile> closeset=new HashSet<>();
	      queue.addAll(Tiles);
	      for(Tile p:Tiles)
	      {
	         queue.addAll(gameMap.getAllPathsfromTile(p));
	      }
	      while(!queue.isEmpty()){
	         Tile n=queue.poll();// dequeue
	         closeset.add(n);

	         // private method, back traces through the parents
	         Collection<Direction> directions=n.getDirections();
	         for(Direction d:directions)
	         {
	            try {
	               Tile n2=null;
	               switch (d) {
	                  case up:
	                  {
	                     n2=(gameMap.getTile(n.getRow()-1, n.getColumn()));
	                     break;
	                  }
	                  case down:
	                  {
	                     n2=(gameMap.getTile(n.getRow()+1, n.getColumn()));
	                     break;
	                  }
	                  case left:
	                  {
	                     n2=(gameMap.getTile(n.getRow(), n.getColumn()-1));
	                     break;
	                  }
	                  case right:
	                  {
	                     n2=(gameMap.getTile(n.getRow(), n.getColumn()+1));
	                     break;
	                  }
	                  default:
	                  {
	                     //tile2=null;
	                     break;
	                  }
	               }
	               if(n2!=null&&!n2.isBlanck()&&!n2.isStart()&&!n2.isGoal())
	               {
	                  closeset.add(n2);
	               }
	            }catch (Exception e) {
	               //e.printStackTrace();
	         }
	         Collection<Tile> successors=gameMap.getNeighbors(n);//however it is implemented
	         for(Tile t: successors){
	            if(!closeset.contains(t))
	            {
	               if(!queue.contains(t)){
	                  queue.add(t);
	               }

	            }
	         }
	      }
	   }
		   //System.out.println(closeset.toString());

	   closeset.removeAll(Tiles);
	   //System.out.println(closeset.toString());

	    //System.out.println("closeset " +closeset.size());

	   for(Tile cstile:closeset)
	   {
	      int i=cstile.getRow(),j=cstile.getColumn();
	      try {
	         Tile tile=gameMap.getTile(i, j);
	         if(!tile.isStart()&&!tile.isGoal()&&!tile.isBlanck())
	         {
	            PipeGameBoard PipeGameBoard=gameMap.clone();
	            for(int r=0;r<tile.NumofRotate();r++)
	            {
	            	//System.out.println("--------------------------------");
		              // System.out.println(PipeGameBoard.toString());
	               PipeGameBoard.rotateTile(i,j);
	               //System.out.println(PipeGameBoard.toString());
	            	//System.out.println("--------------------------------");

	               if(!PipeGameBoard.isTileLinkToOutOfBounds(PipeGameBoard.getTile(i, j))&&
	                     !PipeGameBoard.isTileNeighborIsBlank(PipeGameBoard.getTile(i, j))&&
	                     !PipeGameBoard.equals(gameMap)&&
	                     PipeGameBoard.isTileConnected(PipeGameBoard.getTile(i, j),predicate))
	               {
	                  boolean flag=true;
	                  for(Tile tile2:Tiles)
	                  {
	                     String string="";
	                     ArrayList<Tile> collection2=PipeGameBoard.getAllPathsfromTile(tile2);
	                     for(Tile tile3:collection2)
	                     {
	                        string+=tile3.getCh()+tile3.getColumn()*100+tile3.getRow()*10000+"";
	                     }
	                     if(!hashSet.contains(string))
	                     {
	                        hashSet.add(string);
	                        flag=false;
	                        break;
	                     }
	                  }
	                  if(flag)
	                  {
	                     continue;
	                  }
	                  double cost=Double.POSITIVE_INFINITY;
	                  for(Tile t:Tiles)
	                  {
	                     cost=Math.min(cost,t.getDistance(tile));
	                  }
	                  State<PipeGameBoard> state=new State<PipeGameBoard>
	                           (PipeGameBoard.clone(), cost, s);
	                  collection.add(state);
	               }
	            }
	         }
	      }catch (Exception e) {}
	   }
	  // System.out.println(collection.toString());
	   return collection;
	   }

}