package sk.stuba.mtf.oop.controls;

import lombok.Getter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.UndoableEditEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.undo.UndoManager;

import java.awt.*;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Logic extends UniversalAdapter {

    private static final int FONT_SIZE = 16;
    @Getter
    private JTextArea textArea;
    @Getter
    private JButton saveButton;
    @Getter
    private JButton loadButton;
    @Getter
    private JButton undoButton;
    @Getter
    private JButton copyButton;
    @Getter
    private JButton pasteButton;
    @Getter
    private JComboBox<String> fontBox;

    private UndoManager undoManager;
    private String fontString;
    private String currentText;
    private String previousText;
    public Logic() {
        this.undoManager = new UndoManager();
        this.textArea = new JTextArea();
        this.fontString = "Default";

        this.saveButton = new JButton("Save");
        this.saveButton.addActionListener(this);
        this.saveButton.setFocusable(false);

        this.loadButton = new JButton("Load");
        this.loadButton.addActionListener(this);
        this.loadButton.setFocusable(false);

        this.undoButton = new JButton("Undo");
        this.undoButton.addActionListener(this);
        this.undoButton.setFocusable(false);
        
        this.copyButton = new JButton("Copy");
        this.copyButton.addActionListener(this);
        this.copyButton.setFocusable(false);

        this.pasteButton = new JButton("Paste");
        this.pasteButton.addActionListener(this);
        this.pasteButton.setFocusable(false);

        this.fontBox = new JComboBox<String>();
        this.fontBox.addItem("Times New Roman");
        this.fontBox.addItem("Arial");
        this.fontBox.addItem("Consolas");
        this.fontBox.setSelectedItem(0);
        this.fontBox.setFocusable(false);
        this.fontBox.addItemListener(this);
        this.fontString = (String) this.fontBox.getSelectedItem();
        this.previousText = "";        
        this.currentText = "";
        this.textArea.getDocument().addDocumentListener(this);
        this.textArea.getDocument().addUndoableEditListener(this);
    }

    private void changeFontBasedOnString() {
        switch (this.fontString) {
            case "Times New Roman":
                this.textArea.setFont(new Font("Times New Roman", Font.PLAIN, FONT_SIZE));
                break;

            case "Arial":
                this.textArea.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
                break;

            case "Consolas":
                this.textArea.setFont(new Font("Consolas", Font.PLAIN, FONT_SIZE));
                break;
        }
    }

    private void saveCurrentFile() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(textArea.getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void loadFile() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
                }
                textArea.setText(sb.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void updateUndo() {
        this.previousText = this.currentText;
        this.currentText = this.textArea.getText();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            this.fontString = (String) ((JComboBox<?>) (e.getSource())).getSelectedItem();
            this.changeFontBasedOnString();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.saveButton)) {
            this.saveCurrentFile();
        } else if (e.getSource().equals(this.loadButton)) {
            this.loadFile();
        } else if (e.getSource().equals(this.undoButton)) {
            if (this.undoManager.canUndoOrRedo()) {
                this.undoManager.undo();
            }
        } else if (e.getSource().equals(this.copyButton)) {
            this.textArea.copy();
        } else if (e.getSource().equals(this.pasteButton)) {
            this.textArea.paste();
        } else return;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        Document document = e.getDocument();
        try {
            int offset = e.getOffset();
            int length = e.getLength();
            String insertedText = document.getText(offset, length);

            if (insertedText.length() > 0) {
                char lastInsertedChar = insertedText.charAt(insertedText.length() - 1);
                if (lastInsertedChar == ' ') {
                    return;
                }
            }
            this.previousText = this.currentText;
            this.currentText = document.getText(0, document.getLength());
            System.out.println("c: " + currentText);
            System.out.println("p: " + previousText);
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        this.updateUndo();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        this.updateUndo();
    }
    @Override
    public void undoableEditHappened(UndoableEditEvent e) {
        this.undoManager.addEdit(e.getEdit());
    }
}
