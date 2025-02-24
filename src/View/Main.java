package view;

import controller.pantallaPrincipalController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/pantallaPrincipal.fxml"));
	        Parent root = loader.load();    
	        pantallaPrincipalController ppC = loader.getController();
	        
	        
	        Scene scene = new Scene(root);
	        //scene.getStylesheets().add(getClass().getResource("estilos.css").toExternalForm());
	        //scene.getStylesheets().add(getClass().getResource("/view/styles.css").toExternalForm());
			primaryStage.setX(0);
			primaryStage.setY(0);
			primaryStage.setFullScreen(true);
			primaryStage.setFullScreenExitHint("Para salir oprimir esc");
			primaryStage.setFullScreenExitKeyCombination(KeyCombination.valueOf("Esc"));
			
	        primaryStage.setScene(scene);
	        primaryStage.setTitle("MACPO");
	        primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		
		launch(args);
		
	}
}