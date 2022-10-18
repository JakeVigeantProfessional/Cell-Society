    package cellsociety.view;

    import javafx.scene.control.Label;
    import javafx.scene.layout.StackPane;
    import javafx.scene.paint.Color;
    import javafx.scene.shape.Rectangle;

    public class CellView extends StackPane {


        public CellView(String name, double x, double y, double width, Color color) {

            // create rectangle
            Rectangle rectangle = new Rectangle(width, width);
            rectangle.setStroke(Color.BLACK);
            rectangle.setFill(color);

            // create label
            Label label = new Label(name);

            // set position
//            setTranslateX(x);
//            setTranslateY(y);

            getChildren().addAll(rectangle, label);

        }

    }

