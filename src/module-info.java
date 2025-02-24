module MACPO {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	
	opens model to javafx.graphics, javafx.fxml, javafx.base;
	opens view to javafx.graphics, javafx.fxml, javafx.base;
	opens controller to javafx.graphics, javafx.fxml, javafx.base;
}