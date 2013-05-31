package sudoku.multithreaded;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import sudoku.Backtrack;
import sudoku.Grid;

/**
 * A Worker Thread
 *
 * @author Antoine MOISE and Adrien RICCIARDI
 */
public class ThreadSlave extends Thread {
    /** This the same version than the monothreaded backtrack except that it can be interrupted at any time. */
    private class BacktrackInterruptible extends Backtrack
    {
        /** Create a new interruptible backtrack solving algorithm.
         * @param grid The grid to solve.
         */
        public BacktrackInterruptible(Grid grid)
        {
            super(grid);
        }
        
        @Override
        public boolean solve()
        {
            if (interrupted()) return true;
            return super.solve();
        }
    }

    private Grid work;
    
    /** The backtrack solving job done by this thread. */
    private Backtrack _backtrack;
    /** Tell if the thread has found the solution or not. */
    private boolean _isGridSolved;
    
    public ThreadSlave(int threadNumber, Grid work) {
        this.setName("Slave" + Integer.toString(threadNumber));
        this.work = work;
        _backtrack = new BacktrackInterruptible(work);
    }

    @Override
    public void run() {
        //backtrack();
        _isGridSolved = _backtrack.solve();
    }
    
    /** Tell if the thread has found or not the solution.
     * This function must be called when the thread terminated.
     * @return true if the solution was found or false if not.
     */
    public boolean isGridSolved()
    {
        return _isGridSolved;
    }
    
    /** Get the thread working grid.
     * @return The grid.
     */
    public Grid getGrid()
    {
        return work;
    }
    
    /** How many loops were necessaty to solve the grid.
     * @return The loops count.
     */
    public long getLoopsCount()
    {
        return _backtrack.getLoopsCount();
    }
    
    /*public void backtrack(){
        
        if(work.isCorrectlyFilled())
            return;
        else if(!work.isEntirelyFilled()){
        ArrayList<Point> emptyCells = work.getEmptyCells();
        Iterator<Point> it = emptyCells.iterator();
        while(it.hasNext()){
            Point current = it.next();
            int iCurrent = (int)current.getX();
            int jCurrent = (int)current.getY();
           ArrayList<Byte> possibleNumber = work.getPossibleNumberAt(iCurrent,jCurrent);
           Iterator<Byte> it2 = possibleNumber.iterator();
           while(it2.hasNext()){
               work.setCell(iCurrent, jCurrent, it2.next());
               backtrack();
               work.setCell(iCurrent, jCurrent, (byte)0);
           }
        }
    }
   }*/
}
    