import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustomerManagerApp extends Application {

    // List that stores all customers
    private ObservableList<Customer> customers =
            FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {

        // -----------------------------
        // NAME FIELD
        // -----------------------------

        Label nameLabel = new Label("Name:");

        TextField nameField = new TextField();
        nameField.setPromptText("Enter customer name");


        // -----------------------------
        // PROVINCE LIST
        // -----------------------------

        Label provinceLabel = new Label("Province:");

        ComboBox<String> provinceBox = new ComboBox<>();

        provinceBox.getItems().addAll(
                "Central",
                "Copperbelt",
                "Eastern",
                "Luapula",
                "Lusaka",
                "Muchinga",
                "Northern",
                "North-Western",
                "Southern",
                "Western"
        );

        provinceBox.setPromptText("Select province");


        // -----------------------------
        // TABLE VIEW
        // -----------------------------

        TableView<Customer> table = new TableView<>();

        // Name column
        TableColumn<Customer, String> nameColumn =
                new TableColumn<>("Name");

        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );


        // Province column
        TableColumn<Customer, String> provinceColumn =
                new TableColumn<>("Province");

        provinceColumn.setCellValueFactory(
                new PropertyValueFactory<>("province")
        );


        // Add columns to table
        table.getColumns().addAll(
                nameColumn,
                provinceColumn
        );

        // Connect ObservableList to TableView
        table.setItems(customers);


        // -----------------------------
        // ADD CUSTOMER BUTTON
        // -----------------------------

        Button addButton = new Button("Add Customer");

        addButton.setOnAction(event -> {

            String name = nameField.getText().trim();

            String province = provinceBox.getValue();


            // Validate name
            if (name.isEmpty()) {

                showAlert(
                        Alert.AlertType.ERROR,
                        "Invalid Input",
                        "Please enter the customer's name."
                );

                nameField.requestFocus();

                return;
            }


            // Validate province
            if (province == null) {

                showAlert(
                        Alert.AlertType.ERROR,
                        "Invalid Input",
                        "Please select a province."
                );

                provinceBox.requestFocus();

                return;
            }


            // Create customer
            Customer customer =
                    new Customer(name, province);


            // Add customer to list
            customers.add(customer);


            // Clear form
            nameField.clear();

            provinceBox.setValue(null);

            nameField.requestFocus();
        });


        // -----------------------------
        // DELETE BUTTON
        // -----------------------------

        Button deleteButton =
                new Button("Delete Customer");

        deleteButton.setOnAction(event -> {

            // Get selected customer
            Customer selectedCustomer =
                    table.getSelectionModel().getSelectedItem();


            // Check if something was selected
            if (selectedCustomer == null) {

                showAlert(
                        Alert.AlertType.WARNING,
                        "No Customer Selected",
                        "Please select a customer to delete."
                );

                return;
            }


            // Confirmation dialog
            Alert confirmation =
                    new Alert(Alert.AlertType.CONFIRMATION);

            confirmation.setTitle("Confirm Deletion");

            confirmation.setHeaderText(
                    "Delete Customer"
            );

            confirmation.setContentText(
                    "Are you sure you want to delete "
                    + selectedCustomer.getName()
                    + "?"
            );


            // Show confirmation
            confirmation.showAndWait().ifPresent(response -> {

                if (response == ButtonType.OK) {

                    customers.remove(selectedCustomer);
                }
            });
        });


        // -----------------------------
        // FORM LAYOUT
        // -----------------------------

        GridPane form = new GridPane();

        form.setHgap(10);

        form.setVgap(10);

        form.setPadding(new Insets(10));


        form.add(nameLabel, 0, 0);

        form.add(nameField, 1, 0);

        form.add(provinceLabel, 0, 1);

        form.add(provinceBox, 1, 1);

        form.add(addButton, 1, 2);

        form.add(deleteButton, 2, 2);


        // -----------------------------
        // MAIN LAYOUT
        // -----------------------------

        VBox root = new VBox(10);

        root.setPadding(new Insets(15));

        root.getChildren().addAll(
                form,
                table
        );


        // -----------------------------
        // SCENE
        // -----------------------------

        Scene scene =
                new Scene(root, 600, 450);


        // -----------------------------
        // STAGE
        // -----------------------------

        stage.setTitle("Customer Manager");

        stage.setScene(scene);

        stage.show();
    }


    // -----------------------------
    // ALERT METHOD
    // -----------------------------

    private void showAlert(
            Alert.AlertType type,
            String title,
            String message) {

        Alert alert = new Alert(type);

        alert.setTitle(title);

        alert.setHeaderText(null);

        alert.setContentText(message);

        alert.showAndWait();
    }


    // -----------------------------
    // MAIN METHOD
    // -----------------------------

    public static void main(String[] args) {

        launch(args);
    }
}