package dad.imc;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class IMC extends Application {
    private TextField pesoTextField;
    private TextField alturaTextField;
    private SimpleDoubleProperty pesoSimpleDoubleProperty;
    private SimpleDoubleProperty alturaSimpleDoubleProperty;
    private HBox pesoHbox;
    private HBox alturaHbox;
    private Label pesoLabel, alturaLabel;

    @Override
    public void start(Stage primaryStage) throws Exception {

        pesoTextField = new TextField();
        alturaTextField = new TextField();

        pesoHbox = new HBox(pesoLabel = new Label("Peso "), pesoTextField = new TextField(), pesoLabel = new Label("kg"));
        pesoHbox.setAlignment(Pos.CENTER);


        alturaHbox = new HBox(alturaLabel = new Label("Altura "), alturaTextField = new TextField(), alturaLabel = new Label("cm"));
        alturaHbox.setAlignment(Pos.CENTER);

        Label clasificaionLabel = new Label();
        clasificaionLabel.setAlignment(Pos.CENTER);
        Label estadoLabel = new Label();
        estadoLabel.setAlignment(Pos.CENTER);

        VBox root = new VBox(5);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(pesoHbox, alturaHbox, clasificaionLabel, estadoLabel);

        Scene scene = new Scene(root, 320, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("IMC.fxml");
        primaryStage.show();

        pesoSimpleDoubleProperty = new SimpleDoubleProperty();
        Bindings.bindBidirectional(pesoTextField.textProperty(), pesoSimpleDoubleProperty, new NumberStringConverter());

        alturaSimpleDoubleProperty = new SimpleDoubleProperty();
        Bindings.bindBidirectional(alturaTextField.textProperty(), alturaSimpleDoubleProperty, new NumberStringConverter());


        DoubleBinding calculoDoubleBindings = alturaSimpleDoubleProperty.divide(100);
        calculoDoubleBindings= pesoSimpleDoubleProperty.divide(calculoDoubleBindings.multiply(calculoDoubleBindings));

        SimpleDoubleProperty resultado;
        resultado = new SimpleDoubleProperty();
        resultado.bind(calculoDoubleBindings);

        clasificaionLabel.textProperty().bind(Bindings.concat("IMC: ")
                .concat(Bindings.when(alturaSimpleDoubleProperty.isEqualTo(0)).then("Peso * Altura^2")
                        .otherwise(resultado.asString("%.2f"))));

        resultado.addListener((o, ov, nv) -> {
            double calculadoIMC = nv.doubleValue();
            if(calculadoIMC < 18.5){
                estadoLabel.setText("Bajo peso");
            }else if(calculadoIMC < 25){
                estadoLabel.setText("Normal");
            }else if(calculadoIMC < 30){
                estadoLabel.setText("Sobrepeso");
            }else {
                estadoLabel.setText("Obeso");
            }
        });

    }

    public static void main(String[] args) {
        launch(args);
    }

}
