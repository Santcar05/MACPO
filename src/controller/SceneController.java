package controller;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import model.Macpo;

public class SceneController {
	public void addTaskScene(ActionEvent event, Macpo macpo) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/pantallaAgregarTarea.fxml"));
		Parent root = loader.load();
		
		// Obtener la nueva instancia del controlador y configurar el ControladorNegocio
		pantallaAgregarTareaController ccV = loader.getController();
		ccV.setMacpo(macpo);
		
		// Cambiar la escena
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		//scene.getStylesheets().add(getClass().getResource("/view/estilos.css").toExternalForm());
		stage.setScene(scene);
		stage.setX(0);
		stage.setY(0);
		stage.setFullScreen(true);
		stage.setFullScreenExitHint("Para salir oprimir esc");
		stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("Esc"));
		
		
		stage.setOnCloseRequest(evento->{
			try {
				ccV.getMacpo().saveSystem();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		
		stage.show();
	}
	
	public void addActivityScene(ActionEvent event, Macpo macpo) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/pantallaAgregarActividad.fxml"));
		Parent root = loader.load();
		
		// Obtener la nueva instancia del controlador y configurar el ControladorNegocio
		pantallaAgregarActividadController ccV = loader.getController();
		ccV.setMacpo(macpo);
		
		// Cambiar la escena
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		//scene.getStylesheets().add(getClass().getResource("/view/estilos.css").toExternalForm());
		stage.setScene(scene);
		stage.setX(0);
		stage.setY(0);
		stage.setFullScreen(true);
		stage.setFullScreenExitHint("Para salir oprimir esc");
		stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("Esc"));
		
		stage.setOnCloseRequest(evento->{
			try {
				ccV.getMacpo().saveSystem();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		stage.show();
	}
	
	public void principalScene(ActionEvent event, Macpo macpo) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/pantallaPrincipal.fxml"));
		Parent root = loader.load();
		
		// Obtener la nueva instancia del controlador y configurar el ControladorNegocio
		pantallaPrincipalController ccV = loader.getController();
		ccV.setMacpo(macpo);
		ccV.updateTaskSection();
		ccV.updateActivitySection();
		ccV.updateRates();
		
		// Cambiar la escena
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		//scene.getStylesheets().add(getClass().getResource("/view/estilos.css").toExternalForm());
		stage.setScene(scene);
		stage.setX(0);
		stage.setY(0);
		stage.setFullScreen(true);
		stage.setFullScreenExitHint("Para salir oprimir esc");
		stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("Esc"));
		
		stage.setOnCloseRequest(evento->{
			try {
				ccV.getMacpo().saveSystem();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		
		
		stage.show();
	}
}
