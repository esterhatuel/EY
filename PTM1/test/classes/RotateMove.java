package classes;

import java.util.ArrayList;
import java.util.Collection;

public class RotateMove {
   private int row;
   private int column;
   private int numofrotate;
   public RotateMove() {
   }
   private RotateMove(Tile toriginal,Tile tsolved) throws Exception
   {
      if(toriginal.getColumn()==tsolved.getColumn()&&
            toriginal.getRow()==tsolved.getRow())
      {
         this.column=toriginal.getColumn();
         this.row=toriginal.getRow();
         Tile ctoriginal=toriginal.clone();
         if(ctoriginal.hashTile().getCh()==tsolved.hashTile().getCh())
         {
            int maxrotatesmoves=ctoriginal.NumofRotate();
            for(this.numofrotate=0;this.numofrotate<maxrotatesmoves;
               this.numofrotate++)
            {
               if((ctoriginal.getCh()==tsolved.getCh()))
               {
                  break;
               }
               ctoriginal=ctoriginal.rotate();
            }
         }
         else
         {
            throw new Exception("tiles from diffrent type");
         }
         
      }
      else
      {
         throw new Exception("tiles from diffrent location");
      }
   }
   public final int getRow() {
      return row;
   }

   public final int getColumn() {
      return column;
   }

   public final int getNumofrotate() {
      return numofrotate;
   }

   @Override
   public String toString() {
      return (row) + "," + (column) + "," + numofrotate;
   }
   
   
   ///if have a error : remove the static and switch everywhere that there is 
   ///RotateMove.getRotatesMoves(o,s)
   ///to
   ///(new RotateMove).getRotatesMoves(o,s)
   public static Collection<RotateMove> getRotatesMoves(PipeGameBoard pipeGameMapO,PipeGameBoard pipeGameMapS) throws Exception
   {
      Collection<RotateMove> arrayList=new ArrayList<>();
      if(pipeGameMapO.getColumns()!=pipeGameMapS.getColumns()||
            pipeGameMapO.getRows()!=pipeGameMapS.getRows()||
            !(pipeGameMapO.hashMap().equals(pipeGameMapS.hashMap())))
      {
         throw new Exception("Not the same Bord");
      }
      int columns=pipeGameMapO.getColumns();
      int rows=pipeGameMapO.getRows();
      
      for(int r=0;r<rows;r++)
      {
         for (int c = 0; c < columns; c++) {
            try {
               RotateMove rotateMove=new RotateMove(pipeGameMapO.getTile(r, c),
                                          pipeGameMapS.getTile(r, c));
               //System.out.println("rotateMove: "+rotateMove.toString());
               if(rotateMove.numofrotate!=0)
               {
                  arrayList.add(rotateMove);
               }
            } catch (Exception e) {
               //e.printStackTrace();
            }
         }
      }
      return arrayList;
   }  

}
