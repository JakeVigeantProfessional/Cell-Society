package cellsociety.view;

import cellsociety.controller.CellSocietyController;
import com.opencsv.exceptions.CsvValidationException;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class GridScreen extends SceneCreator{

    private Pane gridPane;
    private Button play;
    private Button step;
    private Rectangle about;
    private Text title;
    private Text type;
    private Text author;
    private Text description;
    private Text Abouttitle;
    private Paint jid = Color.LIGHTGRAY;
    private ResourceBundle myLabels;
    private GridView gridView;


    public GridScreen(double size)throws CsvValidationException, IOException {
        super(size);
        gridPane = new Pane();
        cellSocietyController = new CellSocietyController();
    }

    public Pane createGridScreen(Stage stage, ResourceBundle label){
        myLabels = label;
        play = new Button("Play");
        step = new Button("Next");
        play.getStyleClass().add("buttons");

        Abouttitle = new Text(myLabels.getString("aboutText"));
        title = new Text(myLabels.getString("title"));
        type = new Text(myLabels.getString("typeText"));
        author = new Text(myLabels.getString("authorText"));
        description = new Text(myLabels.getString("descriptionText"));
        Abouttitle.getStyleClass().add("title");
        title.getStyleClass().add("info");
        type.getStyleClass().add("info");
        author.getStyleClass().add("info");
        description.getStyleClass().add("info");

        VBox fileinfo = new VBox(Abouttitle, title, type, author, description);
        fileinfo.setBackground(Background.fill(jid));
        fileinfo.getStyleClass().add("aboutbox");

        HBox controls = new HBox(play,step);
        controls.setLayoutX(400);
        controls.setLayoutY(690);
        controls.getStyleClass().add("allbuttons");

        gridPane.getChildren().addAll(controls, fileinfo);
        gridView = new GridView(mySize, cellSocietyController);
        gridPane.getChildren().add(gridView.getGrid());
        return gridPane;
    }

    public void handleButtons(Stage stage){
        play.setOnAction(event -> {}
                //call the grid view once every 60 seconds
                );
        step.setOnAction(event ->{}
        //{ update grid once}
        );
    }



}
