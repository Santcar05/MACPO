package controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;
import model.Activity;
import model.Constants;
import model.Macpo;
import model.Task;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class pantallaPrincipalController {
	@FXML
	private ComboBox<String> monthComboBox;
	@FXML
	private ComboBox<Integer> yearComboBox;
	@FXML
	private GridPane schedule;
	@FXML
	private Label toDoRate;
	@FXML
	private Label InProgressRate;
	@FXML
	private Label completedRate;
	@FXML
	private Button addTaskButton;
	@FXML
	private VBox vBoxTasks;
	@FXML
	private VBox vBoxActivities;
	@FXML
	private Button addActivityButton;

    // SAve reference of each Task and Activity of HBox
    private Map<Task, HBox> taskMap = new HashMap<>();
    private Map<Activity, HBox>  activityMap = new HashMap<>();
	
	private Macpo macpo = new Macpo();
	
	private SceneController sceneController = new SceneController();
	
	
	public void initialize() throws ClassNotFoundException, IOException {
	    macpo = macpo.loadSystem();
	    
		createNumbersIntoGridPane(null,0);
		createChoicesMonthComboBox();
		createChoicesYearComboBox();
		
		updateTaskSection();
		updateActivitySection();
		
		//create a lambda function to update the schedule every time the month or the year is selected
		yearComboBox.setOnAction(event ->{
			createNumbersIntoGridPane(null,(int)yearComboBox.getValue());
		});
		
		monthComboBox.setOnAction(event ->{
			createNumbersIntoGridPane(monthComboBox.getValue(),0);
		});
		
		macpo.saveSystem();
	}
	
	public void updateRates() {
		//Find rates by Status
		float to_do_rate = macpo.findRateToDo();
		System.out.println(to_do_rate);
		float in_progress_rate = macpo.findRateInProgress();
		System.out.println(in_progress_rate);
		float completed_rate = macpo.findRateCompleted();
		
		toDoRate.setText(String.valueOf(to_do_rate)+"%");
		InProgressRate.setText(String.valueOf(in_progress_rate)+"%");
		completedRate.setText(String.valueOf(completed_rate)+"%");
		
		
	}
	public void createNumbersIntoGridPane(String month, int year ) {
		//Delete every label, the row 0 still 
		schedule.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) > 0);
		//Check the day to know where to start
		LocalDate localdate = LocalDate.now();
		LocalDate localDate2 ;
		
		int monthInt = 0;
		if(month != null) {
			try {
				monthInt = Month.valueOf(month.toUpperCase()).getValue(); // Cast month number to onlu number
			} catch (IllegalArgumentException e) {
				monthInt = localdate.getMonthValue();
				}
			}
        
	    
		if(month == null && year == 0)
			localDate2 = LocalDate.of(localdate.getYear(), localdate.getMonth(), 1);
		else if(month != null && year != 0)
			localDate2 = LocalDate.of(year, monthInt, 1);
		else if (month == null && year != 0)
			localDate2 = LocalDate.of(year, localdate.getMonth(), 1);
		else
			localDate2 = LocalDate.of(localdate.getYear(), monthInt, 1);
		
		DayOfWeek dayOfWeek = localDate2.getDayOfWeek();
        // Check if the day is  monday, tuesday, etc, to put the x initial .
		int columnStart = 0;
        switch (dayOfWeek) {
            case MONDAY:
            	columnStart = 1;
                break;
            case TUESDAY:
            	columnStart = 2;
                break;
            case WEDNESDAY:
            	columnStart = 3;
                break;
            case THURSDAY:
            	columnStart = 4;
                break;
            case FRIDAY:
            	columnStart = 5;
                break;
            case SATURDAY:
            	columnStart = 6;
                break;
            case SUNDAY:
            	columnStart = 0;
                break;
        }
        //Fill the fields with the numbers of the month
        int daysOfTheMonth = localDate2.lengthOfMonth();
        int rows = 1;
        for(int i = 1; i <= daysOfTheMonth; i++) {
        	Label numberLabel = new Label(String.valueOf(i));
        	schedule.add(numberLabel, columnStart, rows);
        	columnStart++;
        	if(columnStart % 7 == 0) {
        		columnStart = 0;
        		rows++;
        	}
        	
        }
	}
	public void createChoicesMonthComboBox() {
        // get all the months
        ObservableList<String> monthsList = FXCollections.observableArrayList();
        for (Month month : Month.values()) {
            monthsList.add(month.toString()); // Nombres en inglés en mayúscula
        }
        monthComboBox.setItems(monthsList);
	}
	public void createChoicesYearComboBox(){
        // year range  1900 - 2200
        ObservableList<Integer> yearsList = FXCollections.observableArrayList();
        for (int year = 1900; year <= 2200; year++) {
            yearsList.add(year); // Cast int a String
        }
        yearComboBox.setItems(yearsList);
	}
	
	public void updateTaskSection() {
		//sort all task by status(to do, in progress, done) and date.
		macpo.sortTasks();
		
		
	    // keep the first HBox 
	    if (!vBoxTasks.getChildren().isEmpty()) {
	        vBoxTasks.getChildren().remove(1, vBoxTasks.getChildren().size());
	    }
	    
	    taskMap.clear();
	    
	    System.out.println( macpo.getTasksModelController().getTasks().size());
        // Create and add a HBox for each task
        for (Task task : macpo.getTasksModelController().getTasks()) {
        	System.out.println(task.getTitle());
            HBox hbox = createHboxTask(task);
            vBoxTasks.getChildren().add(hbox);
            taskMap.put(task, hbox); // Guardar referencia en el mapa
    	    configureTaskHBox(hbox);
        }
        
	}
	
	private HBox createHboxTask(Task task) {
	    
	    Label titleLabel = new Label(task.getTitle());
	    String stringStatus = castToStringStatus(task);
	    Label statusLabel = new Label(stringStatus);

	    // Modify Button
	    Button modifyButton = new Button("...");
	    modifyButton.setOnAction(e -> modifyTask(task));

	    // delete Button
	    Button deleteButton = new Button("X");
	    deleteButton.setOnAction(e -> deleteTask(task));

	    VBox vBoxButtons = new VBox(modifyButton, deleteButton);
	    
	    // Set the spaced and alignment
	    HBox hbox = new HBox(10, titleLabel, statusLabel, vBoxButtons);
	    hbox.setAlignment(Pos.CENTER_LEFT);

	    return hbox;
	}
	public void configureTaskHBox(HBox hbox) {
		ObservableList<Node> children = vBoxTasks.getChildren();

		// Iterar sobre los HBox dentro del VBox, saltando el primero
		for (int i = 1; i < children.size(); i++) { // Empieza desde 1 para ignorar el primer HBox
		    Node node = children.get(i);

		    if (node instanceof HBox) {
		        HBox hhbox = (HBox) node;
		        hhbox.setStyle("-fx-background-color: #E8F5E9; -fx-background-radius: 15px; -fx-padding: 10px; -fx-spacing: 5px;");
		        hhbox.prefWidth(100000);
		        HBox.setHgrow(hhbox, Priority.ALWAYS);
		        for (Node child : hbox.getChildren()) {
		        	
		        	 HBox.setHgrow(child, Priority.ALWAYS);
		             child.prefWidth(100000);
		        	
		        	
		            if (child instanceof Label) {
		                Label label = (Label) child;
		                label.setStyle("-fx-font-size: 14px; -fx-alignment: center; -fx-wrap-text: true;");
		            } else if (child instanceof VBox) {
		                VBox vbox = (VBox) child;
		                vbox.setStyle("-fx-spacing: 5px; -fx-alignment: center;");

		                for (Node buttonNode : vbox.getChildren()) {
		                    if (buttonNode instanceof Button) {
		                        Button button = (Button) buttonNode;
		                        button.setStyle("-fx-background-color: #FFCDD2; -fx-text-fill: #D32F2F; -fx-font-weight: bold; -fx-background-radius: 10px;");
		                    }
		                }
		            }
		        }
		    }
		}
	}
	
	
	private String castToStringStatus(Task task) {
		if(task.getStatus() == Constants.TO_DO)
			return "Por hacer";
		else if(task.getStatus() == Constants.IN_PROGRESS)
			return "En progreso";
		else
			return "Completado";
	}
	
	private void modifyTask(Task task) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Modificar Tarea");
        dialog.setHeaderText("Editar Tarea");
        
        TextField titleField = new TextField(task.getTitle());
        TextField descriptionField = new TextField(task.getDescription());
        ComboBox statusComboBox = new ComboBox();
        statusComboBox.getItems().addAll("Por hacer","En progreso", "Completado");
        TextField priorityField = new TextField(String.valueOf(task.getPriority()));
        
        VBox content = new VBox(10, new Label("Título:"), titleField,new Label("Descripción:"),descriptionField, new Label("Estado:"), statusComboBox, new Label("Prioridad:"), priorityField);
        dialog.getDialogPane().setContent(content);
        
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        
        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                task.setTitle(titleField.getText());
                task.setStatus(castStatusValue((String) statusComboBox.getValue()));
                task.setDescription(descriptionField.getText());
                task.setPriority(Integer.parseInt(priorityField.getText()));
            }
        });
        
        

		// Refresh the modified Task
        HBox oldHbox = taskMap.get(task);
        if (oldHbox != null) {
            int index = vBoxTasks.getChildren().indexOf(oldHbox);
            vBoxTasks.getChildren().remove(oldHbox);
            HBox newHbox = createHboxTask(task);
            vBoxTasks.getChildren().add(index, newHbox); //Replace at the same place
            taskMap.put(task, newHbox);
        }
        
        updateTaskSection();
        updateRates();
        
	    try {
			macpo.saveSystem();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private void deleteTask(Task task) {
		macpo.deleteTask(task);
		//Delete all the HBox
		
        // Delete just the task selected
        HBox hbox = taskMap.remove(task);
        if (hbox != null) {
        	
            vBoxTasks.getChildren().remove(hbox);
        }
        updateRates();
	    try {
			macpo.saveSystem();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void updateActivitySection() {
		//sort all task by date.
		macpo.sortActivities();
		
		
		//Keep the first Activity 
	    if (!vBoxActivities.getChildren().isEmpty()) {
	        vBoxActivities.getChildren().remove(1, vBoxActivities.getChildren().size());
	    }
	    
	    activityMap.clear();
	    
	    System.out.println( macpo.getActivitiesModelController().getActivities().size());
        // Create and add a HBox for each task
        for (Activity activity : macpo.getActivitiesModelController().getActivities()) {
        	System.out.println(activity.getTitle());
            HBox hbox = createHboxActivity(activity);
            vBoxActivities.getChildren().add(hbox);
            activityMap.put(activity, hbox); 
            configureActivityHBox(hbox);
        }
        
	    try {
			macpo.saveSystem();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
	}
	
	private HBox createHboxActivity(Activity activity) {

	    Label titleLabel = new Label(activity.getTitle());
	    String stringDay = null;
	    if(activity.getStartDate().isEqual(activity.getEndDate())) {
	    	 stringDay = activity.getStartDate().getDayOfWeek() + " " + String.valueOf(activity.getStartDate().getDayOfMonth());
	
	    }else {
	    	stringDay = activity.getStartDate().getDayOfWeek() + " " + String.valueOf(activity.getStartDate().getDayOfMonth()) + "\n" +activity.getEndDate().getDayOfWeek() + " " + String.valueOf(activity.getEndDate().getDayOfMonth());
	    	
	    	
	    }
	       Label dayLabel = new Label(stringDay);
	       dayLabel.setWrapText(true);
	    
	    VBox vBoxTitleDayLabel = new VBox(titleLabel, dayLabel);
	    
	 // Formatear la hora en formato HH:MM
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        
        String formattedStartHour = activity.getStartDate().format(formatter);
        String formattedEndHour = activity.getEndDate().format(formatter);
        
	    Label hourTime = new Label(formattedStartHour +" - "+ formattedEndHour);

	    // Botón para modificar
	    Button modifyButton = new Button("...");
	    modifyButton.setOnAction(e -> modifyActivity(activity));

	    // Botón para eliminar
	    Button deleteButton = new Button("X");
	    deleteButton.setOnAction(e -> deleteActivity(activity));

	    
	    VBox vBoxButtons = new VBox(modifyButton, deleteButton);
	    // Set the spaced and alignment
	    HBox hbox = new HBox(10, vBoxTitleDayLabel, hourTime, vBoxButtons);
	    hbox.setAlignment(Pos.CENTER_LEFT);

	    return hbox;
	}
	
	public void configureActivityHBox(HBox hbox) {
		//hbox.getStyleClass().add("activity-hbox"); // apply style since CSS
		hbox.setPrefWidth(100000);


		// get a sons's list from HBox
		ObservableList<Node> children = vBoxActivities.getChildren();

		// Skip the first HBox(titles)
		for (int i = 1; i < children.size(); i++) { 
		    Node node = children.get(i);

		    if (node instanceof HBox) {
		        HBox hhbox = (HBox) node;
		        hhbox.setStyle("-fx-background-color: #E8F5E9; -fx-background-radius: 15px; -fx-padding: 10px;");

		        for (Node child : hbox.getChildren()) {
		            if (child instanceof VBox) {
		                VBox vbox = (VBox) child;
		                vbox.setStyle("-fx-padding: 5px; -fx-alignment: center;");

		                for (Node labelNode : vbox.getChildren()) {
		                    if (labelNode instanceof Label) {
		                        Label label = (Label) labelNode;
		                        label.setStyle("-fx-font-size: 14px; -fx-alignment: center;");
		                    }
		                    
		                    else if(labelNode instanceof Button) {
		                    	Button button = (Button) labelNode;
		                    	button.setStyle("-fx-background-color: #b7eace; -fx-font-size: 14px; -fx-alignment: center; -fx-background-radius: 15px");
		                    }
		                }
		            } else if (child instanceof Label) {
		                Label label = (Label) child;
		                label.setStyle("-fx-font-size: 14px; -fx-alignment: center;");
		            }
		        }
		    }
		}

		// Estilo para el botón de agregar plan
		addActivityButton.setStyle("-fx-background-color: #E8F5E9; -fx-background-radius: 15px; -fx-font-size: 14px; -fx-font-weight: bold;");

		//-----------------
	}
	
	public void modifyActivity(Activity activity) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Modificar Actividad");
        dialog.setHeaderText("Editar Actividad");
        
        TextField titleField = new TextField(activity.getTitle());
        TextField descriptionField = new TextField(activity.getDescription());
        
        DatePicker startDatePicker = new DatePicker(activity.getStartDate().toLocalDate());
        ComboBox<String> startHourComboBox = new ComboBox<>();
        IntStream.range(0, 24).mapToObj(i -> String.format("%02d:00", i)).forEach(startHourComboBox.getItems()::add);
        //startHourComboBox.setValue(String.valueOf(activity.getStartDate().getHour()));
        
        DatePicker endDatePicker = new DatePicker(activity.getEndDate().toLocalDate());
        ComboBox<String> endHourComboBox = new ComboBox<>();
        IntStream.range(0, 24).mapToObj(i -> String.format("%02d:00", i)).forEach(endHourComboBox.getItems()::add);
        //endHourComboBox.setValue(String.valueOf(activity.getEndDate().getHour()));
        
        VBox content = new VBox(10, 
            new Label("Título:"), titleField,
            new Label("Descripción:"), descriptionField,
            new Label("Fecha de inicio:"), startDatePicker, new Label("Hora de inicio:"), startHourComboBox,
            new Label("Fecha de fin:"), endDatePicker, new Label("Hora de fin:"), endHourComboBox
        );
        dialog.getDialogPane().setContent(content);
        
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
            	activity.setTitle(titleField.getText());
            	activity.setDescription(descriptionField.getText());
            	ZonedDateTime start = castToZonedDateTime(startDatePicker.getValue(), (String)startHourComboBox.getValue());
            	activity.setStartDate(start);
            	ZonedDateTime end = castToZonedDateTime(endDatePicker.getValue(), (String)endHourComboBox.getValue());
            	activity.setEndDate(end);
            	
            }
        });
        
        
		// Refresh the modified Task
        HBox oldHbox = activityMap.get(activity);
        if (oldHbox != null) {
            int index = vBoxActivities.getChildren().indexOf(oldHbox);
            vBoxActivities.getChildren().remove(oldHbox);
            HBox newHbox = createHboxActivity(activity);
            vBoxActivities.getChildren().add(index, newHbox); //Replace at the same place
            activityMap.put(activity, newHbox);
        }
        updateRates();
        
	}
	
	public void deleteActivity(Activity activity) {
		macpo.deleteActivity(activity);
		//Delete all the HBox
		
        // Delete just the task selected
        HBox hbox = activityMap.remove(activity);
        if (hbox != null) {
        	
            vBoxActivities.getChildren().remove(hbox);
        }
        updateRates();
	    try {
			macpo.saveSystem();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	// Event Listener on Button[#addTaskButton].onAction
	@FXML
	public void addTask(ActionEvent event) throws IOException{
		//Change to addTask scene
		macpo.saveSystem();
		sceneController.addTaskScene(event, macpo);
	}
	// Event Listener on Button[#addActivityButton].onAction
	@FXML
	public void addActivity(ActionEvent event) throws IOException{
		//Change to addActivity scene
		macpo.saveSystem();
		sceneController.addActivityScene(event, macpo);
	}
	public Macpo getMacpo() {
		return macpo;
	}
	public void setMacpo(Macpo macpo) {
		this.macpo = macpo;
	}
	
	private int castStatusValue(String status) {
		if(String.valueOf(status).equals("Por hacer")) {
			return Constants.TO_DO;
		}
		else if(status.toString().equals("En progreso")) {
			return Constants.IN_PROGRESS;
		}
		else
			return Constants.DONE;
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
