package cellsociety.model.cells;

import java.awt.Point;;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class WatorWorldCell extends Cell {
  private static final int WATER = 0;
  private static final int FISH = 1;
  private static final int SHARK = 2;
  private static final int INTERMEDIATESHARK = 3;
  private int fishTurns;
  private int sharkTurns;
  private int sharkStarve;
  private List<Integer> myNeighborStates;
  private Map<Integer, String> stateMap;

  // Cell Key
  // 0 = water
  // 1 = fish
  // 2 = shark
  // 3 = intermediaryShark (cleared at end of iteration)

  /**
   * Constructor for WaTorWorldCell class
   * @param state is the state of the cell
   * @param id is the id of the cell
   */
  public WatorWorldCell(int state, Point id){
    super(state, id);
    fishTurns = 0;
    sharkTurns = 0;
    sharkStarve = 0;
    stateMap = Map.of(WATER, "WATER", FISH, "FISH", SHARK, "SHARK", INTERMEDIATESHARK, "INTERMEDIATESHARK");
  }
  @Override
  public void swapCells(Cell cellToSwap) {
    //Swap the current cell into the other cell's future state and vice versa
    cellToSwap.setFutureStateValue(this.getCurrentState());
    this.setFutureStateValue(cellToSwap.getCurrentState());
  }

  public int getState(){
    return getCurrentState();
  }
  public boolean readyToReproduce(){
    if(getCurrentState() == FISH){
      if(fishTurns == 3){
        return true;
      }
      return false;
    }
    if(getCurrentState() == INTERMEDIATESHARK){
      if(sharkTurns == 2){
        return true;
      }
      return false;
    }
    return false;
  }
  public boolean readyToDie(){
    if(getCurrentState() == SHARK){
      if(sharkStarve == 3){
        return true;
      }
    }
    return false;
  }
  public void resetStateParameters(){
    fishTurns = 0;
    sharkTurns = 0;
    sharkStarve = 0;
  }
  @Override
  public void setFutureState(List<Cell> neighbors) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
    myNeighborStates = getNeighborStates(neighbors);

    try {
      this.getClass().getDeclaredMethod("set" + stateMap.get(getCurrentState())).invoke(this);
    } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
      throw e;
    }
  }

  private void setWATER(){
    setFutureStateValue(WATER); // do nothing
    fishTurns = 0;
    sharkTurns = 0;
    sharkStarve = 0;
  }

  private void setFISH(){
    if (myNeighborStates.contains(WATER)){
      fishTurns++;
      if (fishTurns == 3){ // if the fish has been alive for 3 turns, then it will breed
        setFutureStateValue(FISH);
        fishTurns = 0;
      }
      else {
        setFutureStateValue(FISH); // Needs to swap with a water cell
      }
    }
    else {
      setFutureStateValue(FISH); // If there are no empty cells, then the fish stays in the same spot
    }
  }

  private void setSHARK(){
    if (myNeighborStates.contains(WATER) && !myNeighborStates.contains(1)) { // if there are empty cells and no fish, then the shark will starve
      sharkStarve++;
      if (sharkStarve == 3) {
        setFutureStateValue(WATER); // Shark dies
        sharkStarve = 0;
      }
      else {
        setFutureStateValue(SHARK); // Shark stays alive
      }
    }
    else if (myNeighborStates.contains(FISH)) { // if there is a fish, then the shark will eat it
      sharkTurns++;
      if (sharkTurns == 3) { // if the shark has been alive for 3 turns, then it will breed
        setFutureStateValue(SHARK);
        sharkTurns = 0;
      } else {
        setFutureStateValue(SHARK); // Needs to swap with a water cell
      }
    }
    else {
      setFutureStateValue(SHARK);
    }
  }

  private void setINTERMEDIATESHARK(){
    setFutureStateValue(SHARK);
  }
}
