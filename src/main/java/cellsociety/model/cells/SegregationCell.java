package cellsociety.model.cells;

import java.awt.*;
import java.util.List;

public class SegregationCell extends Cell {
  private double myThreshold;
  private double totalNeighbors;
  private double sameNeighborsAgent1;
  private double sameNeighborsAgent2;
  private boolean wantsToMove;
  private static final int EMPTY = 0;
  private static final int AGENT1 = 1;
  private static final int AGENT2 = 2;

  // Key States
  // 0 = empty
  // 1 = Agent 1
  // 2 = Agent 2

  /**
   * Constructor for SchellingCell class
   * @param state is the state of the cell
   * @param id is the id of the cell
   */
  public SegregationCell(int state, Point id, double parameter){
    super(state, id);
    sameNeighborsAgent1 = 0;
    sameNeighborsAgent2 = 0;
    totalNeighbors = 0;
    myThreshold = parameter;
    wantsToMove = false;
  }

  /**
   * Method that sets the next state of the cell
   * @param neighbors is the list of neighbors of the cell
   * @return next state of the cell
   */
  @Override
  public void setFutureState(List<Cell> neighbors) {
    if (getCurrentState() == EMPTY){
      setFutureStateValue(EMPTY);
    }
    else {
      countNeighbors(neighbors);

      if (getCurrentState() == AGENT1){
        agentBehavior(sameNeighborsAgent1, AGENT1);
      }

      if (getCurrentState() == AGENT2){
        agentBehavior(sameNeighborsAgent2, AGENT2);
      }
    }
  }

  /**
   * Method that controls the behavior of the agents
   * @param sameNeighborsAgent
   * @param agent
   */
  private void agentBehavior(double sameNeighborsAgent, int agent) {
    if (sameNeighborsAgent/totalNeighbors < myThreshold){ // if the percentage of same neighbors is less than the threshold, then the agent will move
      setFutureStateValue(agent);
      wantsToMove = true;
    }
    else {
      setFutureStateValue(agent);
      wantsToMove = false;
    }
  }

  /**
   * Method that counts the number of neighbors of the same type
   * @param neighbors
   */
  private void countNeighbors(List<Cell> neighbors) {
    for (Cell neighbor : neighbors){
      if (neighbor.getCurrentState() == AGENT1){
        sameNeighborsAgent1++;
      }
      if (neighbor.getCurrentState() == AGENT2){
        sameNeighborsAgent2++;
      }
      totalNeighbors++;
    }
  }

  /**
   * Method that returns whether the cell wants to move
   * @return true if the cell wants to move, false otherwise
   */
  public boolean getWantsToMove(){
    return wantsToMove;
  }

}
