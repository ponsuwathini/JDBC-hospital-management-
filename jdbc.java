/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbmsproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;



    
   
        

public class HospitalManagementApp extends JFrame {
    JTextField nameField, ageField, genderField, diagnosisField;
    JButton addButton, viewButton,removebutton,updatebutton;
    JTextArea outputArea;

    Connection conn;

    public HospitalManagementApp() {
        setTitle("Hospital Management System");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        nameField = new JTextField(20);
        ageField = new JTextField(5);
        genderField = new JTextField(10);
        diagnosisField = new JTextField(20);

        addButton = new JButton("Add Patient");
        viewButton = new JButton("View All Patients");
        removebutton = new JButton("remove Patient");
        updatebutton=new JButton("update patient");
        outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);

        add(new JLabel("Name:")); add(nameField);
        add(new JLabel("Age:")); add(ageField);
        add(new JLabel("Gender:")); add(genderField);
        add(new JLabel("Diagnosis:")); add(diagnosisField);
        add(addButton); add(viewButton);add(removebutton);add(updatebutton);
        add(new JScrollPane(outputArea));

        connectToDatabase();

        addButton.addActionListener(e -> addPatient());
        viewButton.addActionListener(e -> viewPatients());
        removebutton.addActionListener(e -> deletePatient());
         updatebutton.addActionListener(e -> updatePatient());
        setVisible(true);
    }

    private void connectToDatabase() {
        try {
            String url = "jdbc:mysql://localhost:3306/hospitalmanagement";
            String user = "root"; // change as per your DB user
            String password = "suba@12345"; // change as per your DB password
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database Connection Failed: " + ex.getMessage());
        }
    }

    private void addPatient() {
        String name = nameField.getText();
        int age = Integer.parseInt(ageField.getText());
        String gender = genderField.getText();
        String diagnosis = diagnosisField.getText();

        try {
            String query = "INSERT INTO patients (name, age, gender, diagnosis) VALUES (?, ?, ?,?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            ps.setString(4, diagnosis);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Patient added successfully!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding patient: " + ex.getMessage());
        }
    }

    private void viewPatients() {
        outputArea.setText("");
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM patients");

            while (rs.next()) {
                outputArea.append("sno: " + rs.getInt("sno") +
                                  ", Name: " + rs.getString("name") +
                                  ", Age: " + rs.getInt("age") +
                                  ", Gender: " + rs.getString("gender") +
                                  ", Diagnosis: " + rs.getString("diagnosis") + "\n");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error fetching patients: " + ex.getMessage());
        }
    }
       private void deletePatient() {
        String name = nameField.getText();
        int age = Integer.parseInt(ageField.getText());
        String gender = genderField.getText();
        String diagnosis = diagnosisField.getText();

        try {
            String query = "delete from patients where name=? and age=? and gender=? and diagnosis=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            ps.setString(4, diagnosis);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Patient removed successfully!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error removing patient: " + ex.getMessage());
        }
    
    }
       void updatePatient() {
    String name = nameField.getText();
    int age = Integer.parseInt(ageField.getText());
    String gender = genderField.getText();
    String diagnosis = diagnosisField.getText();

    try {
        String query = "UPDATE patients SET age = ?, gender = ?, diagnosis = ? WHERE name = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, age);
        ps.setString(2, gender);
        ps.setString(3, diagnosis);
        ps.setString(4, name);
        ps.executeUpdate();
        JOptionPane.showMessageDialog(this, "Patient updated successfully!");
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error updating patient: " + ex.getMessage());
    }
}
       


    public static void main(String[] args) {
        new HospitalManagementApp();
    }
}
        // TODO code application logic here
    
    

