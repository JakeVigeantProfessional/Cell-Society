package cellsociety.controller;

import cellsociety.model.InitialModelImplementation;
import cellsociety.model.SimType;
import cellsociety.parser.CSVParser;
import cellsociety.model.cells.Cell;
import cellsociety.model.Model;
import cellsociety.parser.Parser;
import cellsociety.view.GridWrapper;
import cellsociety.view.GridWrapper;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javafx.beans.property.IntegerProperty;
import javafx.stage.Stage;

import javax.swing.text.View;

public class CellSocietyController {
    private static final String INITIAL_STATES = "InitialStates";
    public static final String TITLE = "Title";
    private final int numRows;
    private final int numCols;
    public Properties properties;
    private Model myModel;
    private View myView;
    private File simFile;
    private Map<Integer, Cell> backEndCellsByID;

    public CellSocietyController(File simFile) throws IOException, CsvValidationException {
        this.simFile = simFile;
        getSimData();
        String csvPath = (String) properties.get(INITIAL_STATES);
        Parser gridParser = new CSVParser(csvPath);
        GridWrapper gridWrapper = gridParser.parseData();
        myModel = new InitialModelImplementation(gridWrapper, properties);
        backEndCellsByID = myModel.getCells();

        String[] parseRowCol = new CSVParser(csvPath).parseFirstLine();

        numCols = Integer.parseInt(parseRowCol[0]);
        numRows = Integer.parseInt(parseRowCol[1]);
    }

    public void getSimData() throws IOException {
        properties = new Properties();
        properties.load(new FileReader(simFile));
    }

    public void loadSimulation(Stage stage) {
        stage.setTitle((String) properties.get(TITLE));
        stage.show();
    }

    public Properties getProperties() {
        return properties;
    }

    public void update(GridWrapper GridWrapper) throws CsvValidationException, IOException {
        myModel = new InitialModelImplementation(GridWrapper, properties);
        backEndCellsByID = myModel.getCells();
    }

    public GridWrapper getViewGrid() {
        GridWrapper stateGrid = new GridWrapper(numRows, numCols);
        for (Integer key : backEndCellsByID.keySet()) {
            stateGrid.set((key - 1) / numCols, (key - 1) % numCols, backEndCellsByID.get(key).getCurrentState());
        }
        return stateGrid;
    }

    //For test purpose
    public void setBackEndCellsByID(Map<Integer, Cell> backEndCellsByID) {
        this.backEndCellsByID = backEndCellsByID;
    }

    public GridWrapper updateGrid() {
        myModel.computeStates();
        return getViewGrid();
    }

    /**
     * Resets the cells to the original file inputted
     *
     * @throws CsvValidationException
     * @throws IOException
     */
    public void resetController() throws CsvValidationException, IOException {
        String csvPath = (String) properties.get(INITIAL_STATES);
        SimType simType = SimType.valueOf((String) properties.get("Type"));
        Parser gridParser = new CSVParser(csvPath);
        GridWrapper gridWrapper = gridParser.parseData();
        myModel = new InitialModelImplementation(gridWrapper, properties);
        backEndCellsByID = myModel.getCells();
    }

    public void saveGrid(File file) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {
            GridWrapper grid = getViewGrid();
            for (int i = 0; i < grid.row(); i++) {
                List<Integer> row = grid.getRow(i);
                String[] rowArray = new String[row.size()];
                for (int j = 0; j < row.size(); j++) {
                    rowArray[j] = row.get(j).toString();
                }
                writer.writeNext(rowArray);
            }
        }
    }
}
