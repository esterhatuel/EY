package classes;

import java.util.ArrayList;
import java.util.Collection;

public class Tile {
	private char ch;
	private int row;
	private int column;
	public Tile(char ch, int row, int column) {
		super();
		this.ch = ch;
		this.row = row;
		this.column = column;
	}
	public char getCh() {
		return ch;
	}
	public void setCh(char ch) {
		this.ch = ch;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	@Override
	public String toString() {
		return ch + "";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ch;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}
	@Override
public boolean equals(Object obj) {
   if (!(obj instanceof Tile))
      return false;  
   if (obj == this)
      return true;
   return this.equals(((Tile) obj));
}
public boolean equals(Tile tile) {
   return this.getCh()==tile.getCh()&&
         this.getColumn()==tile.getColumn()&&
         this.getRow()==tile.getRow();
}
	@Override
	protected Tile clone() {
		return new Tile(getCh(),this.getRow(), this.getColumn());
	}
	public double getDistance(Tile tile) {
		return getDistance(tile.row, tile.column);
	}
	public double getDistance(int row, int column) {
		return Math.hypot(row-this.getRow(), column-this.getColumn());
	}
	
	public boolean isStart() {return getCh()=='s';}
	public boolean isGoal() {return getCh()== 'g';}
	public boolean isBlanck() {return getCh()== ' ';}
	public boolean isStraightLine() {return getCh()== '-'|| getCh()=='|';}
	public boolean isRIghtangle() {return getCh()=='7' || getCh()=='J'|| getCh()=='L'|| getCh()== 'F';} 
	
	public Tile hashTile() {
		char hashch=getCh();
		if(isStraightLine()) {
			hashch= '|';
		}
		else if(isRIghtangle()) {
			hashch= 'L'; 
		}
		return new Tile(hashch, row, column);
	}
	 public int NumofRotate() {
		 if(isGoal()||isStart()||isBlanck()) {
			 return 0;
		 }
		 if(isStraightLine()) {
			 return 2;
		 }
	 	if(isRIghtangle()){
	 		return 4;
	 	}
	 	return 1;
	 }
	 

public Tile rotate()
	{
		switch (this.ch) {
			case '-': {
				this.ch = '|';
				break;
			}
			case '|': {
				this.ch = '-';
				break;
			}
			case '7': {
				this.ch = 'J';
				break;
			}
			case 'J': {
				this.ch = 'L';
				break;
			}
			case 'L': {
				this.ch = 'F';
				break;
			}
			case 'F': {
				this.ch = '7';
				break;
			}
			default:
			{
				break;
			}
		}
		return this;
	}

	
	public Collection<Direction> getDirections()
	{
		Collection<Direction> directions=new ArrayList<Direction>();
		switch (this.ch) {
			case '-': {
				directions.add(Direction.left);
				directions.add(Direction.right);
				break;
			}
			case '|': {
				directions.add(Direction.up);
				directions.add(Direction.down);
				break;
			}
			case '7': {
				directions.add(Direction.left);
				directions.add(Direction.down);
				break;
			}
			case 'J': {
				directions.add(Direction.left);
				directions.add(Direction.up);
				break;
			}
			case 'L': {
				directions.add(Direction.up);
				directions.add(Direction.right);
				break;
			}
			case 'F': {
				directions.add(Direction.down);
				directions.add(Direction.right);
				break;
			}
			case 's': {
				directions.add(Direction.up);
				directions.add(Direction.down);
				directions.add(Direction.right);
				directions.add(Direction.left);
				break;
			}
			case 'g': {
				directions.add(Direction.up);
				directions.add(Direction.down);
				directions.add(Direction.right);
				directions.add(Direction.left);
				break;
			}
		}
		return directions;
	}

	 public Direction IsTilesNeighbors(Tile tile) {
		 if ((this.getRow()==tile.row+1&& this.getColumn()==tile.column))
		 {
			 return Direction.down;
		 }
		 else if (this.getRow()==tile.row && this.getColumn()==tile.column+1) {
		    return Direction.right;
		}
		else if(this.getRow()==tile.row-1&& this.getColumn()==tile.column) {
			return Direction.up;
		}
		
		else if(this.getRow()==tile.row && this.getColumn()==tile.column-1) {
			 return Direction.left;
		 }
		 return null;
	 }

	public boolean IsTileConnected(Tile tile) {
    	return (tile!=null&&this.getDirections().contains(this.IsTilesNeighbors(tile))&&
    			tile.getDirections().contains(tile.IsTilesNeighbors(this)));
    }
	
}

	 

	

// why to do hash code -andddd- equals if can use only hash to verify what we looking for 