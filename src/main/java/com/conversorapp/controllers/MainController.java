package com.conversorapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainController {
    @FXML private TextField inputField;
    @FXML private ComboBox<String> unidadOrigen;
    @FXML private ComboBox<String> unidadDestino;
    @FXML private Label resultadoLabel;
    @FXML private TextArea historialArea;

    @FXML
    public void initialize() {
        // Inicializar ComboBox
        unidadOrigen.getItems().addAll("Metros", "Pies", "Pulgadas", "Yardas");
        unidadDestino.getItems().addAll("Metros", "Pies", "Pulgadas", "Yardas");

        // Seleccionar valores por defecto
        unidadOrigen.getSelectionModel().selectFirst();
        unidadDestino.getSelectionModel().select(1);
    }

    @FXML
    private void convertir() {
        try {
            double valor = Double.parseDouble(inputField.getText());
            String de = unidadOrigen.getValue();
            String a = unidadDestino.getValue();

            if(de.equals(a)) {
                resultadoLabel.setText("¡Selecciona unidades diferentes!");
                return;
            }

            double resultado = calcularConversion(valor, de, a);
            String conversion = String.format("%.2f %s = %.2f %s", valor, de, resultado, a);

            resultadoLabel.setText(conversion);
            agregarAlHistorial(conversion);

        } catch (NumberFormatException e) {
            resultadoLabel.setText("Error: Ingresa un número válido");
        }
    }

    @FXML
    private void limpiar() {
        inputField.clear();
        resultadoLabel.setText("");
        unidadOrigen.getSelectionModel().selectFirst();
        unidadDestino.getSelectionModel().select(1);
    }

    private double calcularConversion(double valor, String de, String a) {
        // Conversión a metros primero
        double enMetros = switch (de) {
            case "Pies" -> valor * 0.3048;
            case "Pulgadas" -> valor * 0.0254;
            case "Yardas" -> valor * 0.9144;
            default -> valor; // Metros
        };

        // Conversión desde metros
        return switch (a) {
            case "Pies" -> enMetros / 0.3048;
            case "Pulgadas" -> enMetros / 0.0254;
            case "Yardas" -> enMetros / 0.9144;
            default -> enMetros; // Metros
        };
    }

    private void agregarAlHistorial(String conversion) {
        String historialActual = historialArea.getText();

        // Máximo de conversiones en historial
        int MAX_HISTORIAL = 10;
        if(historialActual.split("\n").length >= MAX_HISTORIAL) {
            // Eliminar la conversión más antigua
            historialActual = historialActual.substring(
                    historialActual.indexOf("\n") + 1
            );
        }

        historialArea.setText(
                (historialActual.isEmpty() ? "" : historialActual + "\n") +
                        conversion
        );
    }
}