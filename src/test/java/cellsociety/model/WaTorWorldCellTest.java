package cellsociety.model;

import cellsociety.model.cells.WatorWorldCell;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WaTorWorldCellTest {
    int state = 1;
    int id = 0;
    WatorWorldCell c = new WatorWorldCell(state, id);


    @Test
    void testCellBasics () {
        assertEquals(state, c.getCurrentState());
        assertEquals(id, c.getId());
    }

    @Test
    void testSetFutureState () {
        c.setFutureStateValue(2);
        assertEquals(2, c.getFutureState());
    }

    @Test
    void testNeighborsFishesWithFishesMove () {
        WatorWorldCell fish = new WatorWorldCell(1, 0);
        WatorWorldCell c1 = new WatorWorldCell(1, 0);
        WatorWorldCell c2 = new WatorWorldCell(1, 1);
        WatorWorldCell c3 = new WatorWorldCell(0, 2);
        WatorWorldCell c4 = new WatorWorldCell(0, 3);

        fish.setFutureState(List.of(c1, c2, c3, c4));

        assertEquals(1, fish.getFutureState());
        assertEquals(true, fish.getWantsToMove());
    }

    @Test
    void testNeighborsFishesWithFishesStay () {
        WatorWorldCell fish = new WatorWorldCell(1, 0);
        WatorWorldCell c1 = new WatorWorldCell(1, 0);
        WatorWorldCell c2 = new WatorWorldCell(1, 1);
        WatorWorldCell c3 = new WatorWorldCell(1, 2);
        WatorWorldCell c4 = new WatorWorldCell(1, 3);

        fish.setFutureState(List.of(c1, c2, c3, c4));

        assertEquals(1, fish.getFutureState());
        assertEquals(false, fish.getWantsToMove());
    }

    @Test
    void testNeighborsShark () {
        WatorWorldCell shark = new WatorWorldCell(2, 0);
        WatorWorldCell c1 = new WatorWorldCell(1, 0);
        WatorWorldCell c2 = new WatorWorldCell(1, 1);
        WatorWorldCell c3 = new WatorWorldCell(1, 2);
        WatorWorldCell c4 = new WatorWorldCell(1, 3);

        shark.setFutureState(List.of(c1, c2, c3, c4));

        assertEquals(2, shark.getFutureState());
        assertEquals(true, shark.getWantsToMove());
    }

    @Test
    void testNeighborsSharkNoEat () {
        WatorWorldCell shark = new WatorWorldCell(2, 0);
        WatorWorldCell c1 = new WatorWorldCell(2, 0);
        WatorWorldCell c2 = new WatorWorldCell(2, 1);
        WatorWorldCell c3 = new WatorWorldCell(2, 2);
        WatorWorldCell c4 = new WatorWorldCell(2, 3);

        shark.setFutureState(List.of(c1, c2, c3, c4));

        assertEquals(2, shark.getFutureState());
        assertEquals(false, shark.getWantsToMove());
        assertEquals(0, shark.getSharkStarve());
    }

    @Test
    void testNeighborStates () {
        WatorWorldCell c1 = new WatorWorldCell(1, 0);
        WatorWorldCell c2 = new WatorWorldCell(0, 1);
        WatorWorldCell c3 = new WatorWorldCell(1, 2);
        WatorWorldCell c4 = new WatorWorldCell(0, 3);

        List<Integer> neighborStates = c.getNeighborStates(List.of(c1, c2, c3, c4));

        assertEquals(neighborStates, List.of(1, 0, 1, 0));
    }

}
