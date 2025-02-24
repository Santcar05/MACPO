package controller;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import model.Constants;
import model.Macpo;

import java.io.IOException;

import javafx.event.ActionEvent;

import javafx.scene.control.ComboBox;

public class pantallaAgregarTareaController {
	@FXML
	private TextField titleTaskTextField;
	@FXML
	private TextField descriptionTaskTextField;
	@FXML
	private ComboBox<String> statusTaskComboBox;
	
	private int statusValue = Constants.TO_DO;
	@FXML
	private TextField priorityTaskTextField;
	@FXML
	private Button backButton;
	@FXML
	private Button createTaskButton;

	private Macpo macpo = new Macpo();
	
	
	private SceneController sceneController = new SceneController();
	
	public void initialize() {
		//Create options for ComboBox
		createChoicesCombobox();
	}
	
	private void createChoicesCombobox() {
		statusTaskComboBox.getItems().addAll(new String("Por hacer"), new String("En proceso"), new String("Hecho"));
	}
	
	// Event Listener on Button[#backButton].onAction
	@FXML
	public void back(ActionEvent event) throws Exception {
		sceneController.principalScene(event, macpo);
	}
	// Event Listener on Button[#createTaskButton].onAction
	@FXML
	public void createTask(ActionEvent event) throws IOException {
		//Check out if fields are in blank
		if(!isTextFilled()) {
			try {
				throw new Exception("Textos no llenados");
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
		//Create new Task with fields
		try {
			//Check the status value
			castStatusValue(statusTaskComboBox.getValue());
			
			
			if(!macpo.addTaskToModelController(titleTaskTextField.getText(),
					descriptionTaskTextField.getText(),
					statusValue,
					Integer.parseInt(priorityTaskTextField.getText())))
			throw new Exception("La tarea no se pudo crear");
			
			System.out.println(macpo.getTasksModelController().getTasks().size());
			
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		macpo.saveSystem();
		//Come back to principal scene
		sceneController.principalScene(event, macpo);
	}
	
	private boolean isTextFilled() {
		if(!titleTaskTextField.getText().isEmpty() &&
				!descriptionTaskTextField.getText().isBlank() &&
				statusTaskComboBox.getValue() != null &&
				!priorityTaskTextField.getText().isBlank())
			return true;
		
		else return false;
	}
	
	
	private void castStatusValue(Object status) {
		if(String.valueOf(status).equals("Por hacer")) {
			statusValue = Constants.TO_DO;
		}
		else if(status.toString().equals("En proceso")) {
			statusValue = Constants.IN_PROGRESS;
		}
		else
			statusValue = Constants.DONE;
	}

	public Macpo getMacpo() {
		return macpo;
	}

	public void setMacpo(Macpo macpo) {
		this.macpo = macpo;
	}
}
