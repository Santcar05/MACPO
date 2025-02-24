package controller;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import model.Macpo;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.ComboBox;

import javafx.scene.control.DatePicker;

public class pantallaAgregarActividadController {
	@FXML
	private TextField titleActivityTextField;
	@FXML
	private TextField descriptionActivityTextField;
	@FXML
	private DatePicker startTimeActivityDatePicker;
	@FXML
	private ComboBox<String> startHourActivityComboBox;
	@FXML
	private DatePicker endTimeActivityDatePicker;
	@FXML
	private ComboBox<String> endTimeActivityComboBox;
	@FXML
	private Button backButton;
	@FXML
	private Button createActivityButton;
	
	private Macpo macpo = new Macpo();

	private SceneController sceneController = new SceneController();
	
	public void initialize() {
		//Create options for ComboBox
		createChoicesCombobox();
	}
	
	private void createChoicesCombobox() {
	    // Crear la lista de opciones con las 24 horas en formato HH:00
	    ObservableList<String> hoursList = FXCollections.observableArrayList();
	    for (int i = 0; i < 24; i++) {
	        hoursList.add(String.format("%02d:00", i));
	    }

	    // Agregar la lista a los ComboBox
	    startHourActivityComboBox.setItems(hoursList);
	    endTimeActivityComboBox.setItems(hoursList);
	}
	
	
	// Event Listener on Button[#backButton].onAction
	@FXML
	public void back(ActionEvent event)throws IOException{
		sceneController.principalScene(event, macpo);
	}
	// Event Listener on Button[#createActivityButton].onAction
	@FXML
	public void createActivity(ActionEvent event) {
		//Check out if fields are in blank
		if(!isTextFilled()) {
			try {
				throw new Exception("Los espacios todavía no han sido llenados");
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
		//Create new Activity with fields
		ZonedDateTime StartTime = null;
		ZonedDateTime EndTime = null;
		try {
			StartTime = castToZonedDateTime(startTimeActivityDatePicker.getValue(),startHourActivityComboBox.getValue());
			 EndTime = castToZonedDateTime(endTimeActivityDatePicker.getValue(),endTimeActivityComboBox.getValue());
		
		}catch(Exception e) {
			System.out.println("ERROR EN EL CASTEO DE LA FECHA");
		}
		
		try {
			if(!macpo.addActivityToModelController(titleActivityTextField.getText(),
				descriptionActivityTextField.getText(),
				StartTime,
				EndTime
				)) {
			throw new Exception("No se pudo crear la actividad");
		}
			System.out.println(macpo.getActivitiesModelController().getActivities().size());
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		try {
			macpo.saveSystem();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		//Come back to principal scene
		try {
			sceneController.principalScene(event, macpo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private boolean isTextFilled() {
		if(!titleActivityTextField.getText().isBlank() &&
				!descriptionActivityTextField.getText().isBlank() &&
				startTimeActivityDatePicker.getValue() != null &&
				startHourActivityComboBox.getValue() != null &&
				endTimeActivityDatePicker.getValue() != null &&
				endTimeActivityComboBox.getValue() != null
				)
			return true;
		else return false;
	}
	
	public Macpo getMacpo() {
		return macpo;
	}
	public void setMacpo(Macpo macpo) {
		this.macpo = macpo;
	}
	
	private ZonedDateTime castToZonedDateTime(LocalDate date, String hour){
        if (date == null || hour == null) {
            throw new IllegalArgumentException("Fecha u hora inválida");
        }

        // Get System zone
        ZoneId SystemZone = ZoneId.systemDefault();
        // Convert hour (HH:mm) to LocalTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime hourLocalTime = LocalTime.parse(hour, formatter);

        // Combine LocalDate and LocalTime on LocalDateTime
        LocalDateTime dateTime = LocalDateTime.of(date, hourLocalTime);

        // Convert to ZonedDateTime with the specific Zone 
        
        return dateTime.atZone(SystemZone);
	}
	
}
