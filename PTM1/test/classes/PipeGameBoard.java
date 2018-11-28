package classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.omg.CosNaming.IstringHelper;

public class PipeGameBoard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Tile[][] map;
	private int rows;
	private int columns;
	
	
	public PipeGameBoard() {}
	public PipeGameBoard(Tile[][] map) {setMap(map);}
	public PipeGameBoard(String map) {setMap(map);}
	public Tile[][] getMap() {
		return map;
	}
	public void setMap(Tile[][] map) {
		this.map = map;
		this.rows=map.length;
		this.columns=map[0].length;
	}
	public void setMap(String stmap)
	{
	   if(stmap==null||stmap.isEmpty()||stmap=="")
	   {
	      this.rows=0;
	      this.columns=0;
	      this.map=new Tile[this.rows][this.columns];
	   }
	   else
	   {
	      String[] strmap=stmap.split("\n");
	      this.rows=strmap.length;
	      char[][] alstrmap=new char[this.rows][];
	      for(int i=0;i<this.rows;i++)
	      {
	         alstrmap[i]=strmap[i].replaceAll("[\n\r]", "").toCharArray();
	      }
	      
	      this.columns=alstrmap[0].length;
	      this.map=new Tile[this.rows][this.columns];
	      for(int row=0;row<this.rows;row++)
	      {
	         for(int column=0;column<this.columns;column++)
	         {
	            this.map[row][column]=new Tile(alstrmap[row][column],row,column);
	         }
	      }
	   }
	}

	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getColumns() {
		return columns;
	}
	public void setColumns(int columns) {
		this.columns = columns;
	}
	
	public Tile getTile(int row,int column)throws Exception {
		try {
			return this.map[row][column];
			
		}
		catch(Exception e) {
			throw new Exception ("Tile Error ["+row+","+column+"]");
		}
	}
	@Override
	public String toString() {
		StringBuilder builder= new StringBuilder();
		for(int i=0 ; i<this.rows; i++)
		{
			for(int j=0;j<this.columns;j++) {
				try {
					builder.append(this.getTile(i, j).toString());
				} catch (Exception e) {
					//e.printStackTrace();
				}
			}
			builder.append("\n");
		}
		return builder.toString();
	}
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PipeGameBoard other = (PipeGameBoard) obj;
		if (columns != other.columns)
			return false;
		if (!Arrays.deepEquals(map, other.map))
			return false;
		if (rows != other.rows)
			return false;
		return true;
	}

	
	public Tile rotateTile(Tile tile) throws Exception{
		return this.getTile(tile.getRow(), tile.getColumn()).rotate();
	}
	public void rotateTile(int i, int j) throws Exception {
		this.getTile(i,j).rotate();
		
	}	 

	
	public boolean isSolevd() {
		Collection<Tile> collectiongoals=getGoalsIndex();
		boolean flag;
		for(Tile goal:collectiongoals)
		{
			flag= isTileConnected(goal, new Predicate<Tile>() {
				public boolean test(Tile t) {
					return t.isStart();
				}});
			if (!flag) {
			return false;	
			}
		}
		return true;
	}

	public boolean isTileNeighborIsBlank(Tile tile)
	{
	   Collection<Direction> directions=tile.getDirections();
	   int row=tile.getRow();
	   int col=tile.getColumn();
	   Tile tile2=null;
	   for(Direction d:directions)
	   {
	      try {
	            tile2=null;
	            switch (d) {
	               case up:
	               {
	                  tile2=(this.getTile(row-1, col));
	                  break;
	               }
	               case down:
	               {
	                  tile2=(this.getTile(row+1, col));
	                  break;
	               }
	               case left:
	               {
	                  tile2=(this.getTile(row, col-1));
	                  break;
	               }
	               case right:
	               {
	                  tile2=(this.getTile(row, col+1));
	                  break;
	               }
	               default:
	               {
	                  //tile2=null;
	                  break;
	               }
	            }
	            if(tile2.isBlanck())
	            {
	               return true;
	            }
	         }catch (Exception e) {
	            //e.printStackTrace();
	      }
	   }
	   return false;
	}

	public Collection<Tile> getNeighbors(final Tile tile)
	{
	      Collection<Tile> arrayList=new ArrayList<>();
	      int row=tile.getRow();
	      int col=tile.getColumn();
	      Tile tile2=null;
	      Collection<Direction> directions=tile.getDirections();
	      for(Direction d:directions)
	      {
	         try {
	            tile2=null;
	            switch (d) {
	               case up:
	               {
	                  tile2=(this.getTile(row-1, col));
	                  break;
	               }
	               case down:
	               {
	                  tile2=(this.getTile(row+1, col));
	                  break;
	               }
	               case left:
	               {
	                  tile2=(this.getTile(row, col-1));
	                  break;
	               }
	               case right:
	               {
	                  tile2=(this.getTile(row, col+1));
	                  break;
	               }
	               default:
	               {
	                  //tile2=null;
	                  break;
	               }
	            }
	            if(tile2!=null)
	            {
	               if(this.isTilesNeighbors(tile, tile2))
	               {
	                  arrayList.add(tile2);
	               }
	            }
	         }catch (Exception e) {
	            //e.printStackTrace();
	      }
	   }
	   
	   return arrayList;
	}
 
	public Collection<Tile>getConnectedTile(Tile tile){
		Collection<Tile>tiles=getNeighbors(tile);
		tiles.removeIf(new Predicate<Tile>() {

			@Override
			public boolean test(Tile t) {
				return !tile.IsTileConnected(t);
			}
		});
		return tiles;
	}

	
	private Collection<Tile> getTileIndex(Predicate<Tile> predicate)
	{
		Collection<Tile> tiles= new ArrayList<>();
		for(int row=0 ; row<this.rows;row++) {
			  for(int col=0 ; col<this.columns ; col++) {
					try {
						if(predicate.test(this.getTile(row, col)))
						{
							tiles.add(this.getTile(row, col));
						}
					}
					catch (Exception e) {
					}
			  }
		}
		return tiles;
	}
	public Collection<Tile> getGoalsIndex() {
		return getTileIndex(new Predicate<Tile>() {
			
			@Override
			public boolean test(Tile t) {
				return t.isGoal();
			}
		});
	}
	public Collection<Tile> getStartsIndex() {
		return getTileIndex(new Predicate<Tile>() {
			
			@Override
			public boolean test(Tile t) {
				return t.isStart();
			}
		});
	}
	
	public PipeGameBoard hashMap() {
		PipeGameBoard gameMap=clone();
		for(int i =0; i<this.rows; i++)
		{
			for(int j=0; j<this.columns;j++) {
				try {
					gameMap.getTile(i, j).setCh(gameMap.getTile(i, j).hashTile().getCh());
				
				}catch (Exception e) {
				}
			}
		}
		return gameMap;
	}

	public PipeGameBoard clone() {
		PipeGameBoard gameMap= new PipeGameBoard();
		gameMap.rows=this.rows;
		gameMap.columns=this.columns;
		gameMap.map= new Tile[rows][columns];
		for(int i =0 ; i<rows;i++) {
			for(int j=0 ; j<columns ;j++) {
				try {
					gameMap.map[i][j]= new Tile(this.getTile(i,j).getCh(),i,j);
				
				}catch (Exception e) {
				}
			}
		}
		return gameMap;
	}
	
	
	public boolean isTileConnected(Tile t1, Predicate<Tile> Pridacte) {
		Stack<Tile> queue = new Stack<>();
		HashSet<Tile> closedSet= new HashSet<>();
		queue.add(t1);
		while(!queue.isEmpty()) {
			Tile n= queue.pop();
			closedSet.add(n);
			try {
				if(Pridacte.test(n)) {
					return true;
				}
				Collection<Tile> successors= getNeighbors(n);
				for(Tile tile: successors ) {
					if(tile!=null && !closedSet.contains(tile)&&!queue.contains(tile)) {
							queue.push(tile);
						
					}
				}
			}catch (Exception e) {
			}
		}
		return false;
	}

	
	
	 public boolean isTileConnected(Tile t1 , Tile t2) {
		 return isTileConnected(t1, new Predicate<Tile>() {
			
			@Override
			public boolean test(Tile t) {
				return t.equals(t2);
			}
		});
	 }
		
	 public boolean isTileConnectedToStart(Tile t1) {
		 return isTileConnected(t1, new Predicate<Tile>() {
			
			@Override
			public boolean test(Tile t) {
				return t.isStart();
			}
		});
	 }
	
		
	 public boolean isTileConnectedToGoal(Tile t1) {
		 return isTileConnected(t1, new Predicate<Tile>() {
			
			@Override
			public boolean test(Tile t) {
				return t.isGoal();
			}
		});
	 }
	
	 public boolean isTileLinkToOutOfBounds(Tile tile)
	 {
	    int row=tile.getRow();
	    int col=tile.getColumn();
	    Collection<Direction> directions=tile.getDirections();
	    for(Direction d:directions)
	    {
	       try {
	          switch (d) {
	             case up:
	             {
	                this.getTile(row-1, col);
	                break;
	             }
	             case down:
	             {
	                this.getTile(row+1, col);
	                break;
	             }
	             case left:
	             {
	                this.getTile(row, col-1);
	                break;
	             }
	             case right:
	             {
	                this.getTile(row, col+1);
	                break;
	             }
	             default:
	             {
	                break;
	             }
	          }
	          
	       }
	       catch (Exception e) {
	          return true;
	       }
	    }
	    return false;
	 }

	

public int CountHowManyPossibleExits(Tile tile)
{
   int i=0;
   int row=tile.getRow();
   int col=tile.getColumn();
   Tile tile2=null;
   Direction[] directions= Direction.values();
   for(Direction d:directions)
   {
      try {
         tile2=null;
         switch (d) {
            case up:
            {
               tile2=(this.getTile(row-1, col));
               break;
            }
            case down:
            {
               tile2=(this.getTile(row+1, col));
               break;
            }
            case left:
            {
               tile2=(this.getTile(row, col-1));
               break;
            }
            case right:
            {
               tile2=(this.getTile(row, col+1));
               break;
            }
            default:
            {
               //tile2=null;
               break;
            }
         }
         if(!tile2.isBlanck())
         {
            i++;
         }
      }catch (Exception e) {
      }
   }
   return i;
}


private int countAllTiles(Predicate<Tile> predicate)
{
   int count=0;
   for(int i=0;i<this.rows;i++)
   {
      for(int j=0;j<this.columns;j++)
      {
         try {
            if(predicate.test(getTile(i, j)))
            {
               count++;
            }
         } catch (Exception e) {
            //e.printStackTrace();
         }
      }
   }
   return count;
}

public int countAllStarts()
{
   return countAllTiles(new Predicate<Tile>() {
      
      @Override
      public boolean test(Tile t) {
         return t.isStart();
      }
   });
}
public int countAllGoals()
{
   return countAllTiles(new Predicate<Tile>() {
      
      @Override
      public boolean test(Tile t) {
         return t.isGoal();
      }
   });
}
public int countAllBlanks()
{
   return countAllTiles(new Predicate<Tile>() {
      
      @Override
      public boolean test(Tile t) {
         return t.isBlanck();
      }
   });
}
public int countAllStraightLines()
{
   return countAllTiles(new Predicate<Tile>() {
      
      @Override
      public boolean test(Tile t) {
         return t.isStraightLine();
      }
   });
}
public int countAllRightAngles()
{
   return countAllTiles(new Predicate<Tile>() {
      
      @Override
      public boolean test(Tile t) {
         return t.isRIghtangle();
      }
   });
}

public int totalTiles()
{
   return this.columns*this.rows;
}


public ArrayList<Tile> getAllPathsfromTile(Tile t)
{
   Queue<Tile> queue=new LinkedList<Tile>();
   ArrayList<Tile> closedSet=new ArrayList<Tile>();
   queue.add(t);
   while(!queue.isEmpty())
   {
      Tile n=queue.remove();// dequeue
      closedSet.add(n);
      try {
         Collection<Tile> successors=getNeighbors(n);
         for(Tile tile:successors) {
            if(tile!=null&&!closedSet.contains(tile))
            {
               if(!queue.contains(tile)){
                  queue.add(tile);
               }
            }
         }
      }catch (Exception e) {
         //e.printStackTrace();
      }
   }
   return closedSet;
   
}

public boolean isTilesNeighbors(Tile t1,Tile t2)
{
   try {
      int row1=t1.getRow();
      int col1=t1.getColumn();
      int row2=t2.getRow();
      int col2=t2.getColumn();
   if(row2==row1-1&&col2==col1)
   {
      if(t1.getDirections().contains(Direction.up)
            &&t2.getDirections().contains(Direction.down))
      {
         return true;
      }
   }
   else if(row1==row2)
   {
      if(col2==col1-1)
      {
         if(t1.getDirections().contains(Direction.left)
               &&t2.getDirections().contains(Direction.right))
         {
            return true;
         }
      }
      else if(col2==col1)
      {
         throw new Exception("Error get the same tiles");
      }
      else if(col1+1==col2)
      {
         if(t1.getDirections().contains(Direction.right)
               &&t2.getDirections().contains(Direction.left))
         {
            return true;
         }
      }
      else 
      {
         return false;
      }
   }
   else if(row1+1==row2&&col2==col1)
   {
      if(t1.getDirections().contains(Direction.down)
            &&t2.getDirections().contains(Direction.up))
      {
         return true;
      }
   }
   }catch (Exception e) {
      return false;
   }
   return false;
}











}
