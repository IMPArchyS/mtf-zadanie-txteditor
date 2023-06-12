package sk.stuba.mtf.oop.controls;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;

public class Logic extends UniversalAdapter {
    @Getter
    private TextArea textArea;
    private String fontString;
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
    public Logic() {
        this.textArea = new TextArea();
        this.textArea.setFocusable(false);
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
        this.fontBox.addItem("Arial");
        this.fontBox.addItem("Roboto");
        this.fontBox.addItem("Monospace");
        this.fontBox.setSelectedItem(0);
        this.fontBox.setFocusable(false);
        this.fontBox.addItemListener(this);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        this.fontString = (String) ((JComboBox<?>) (e.getSource())).getSelectedItem();
        System.out.println("changed");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.saveButton)) {
            System.out.println("saved");
        } else if (e.getSource().equals(this.loadButton)) {
            System.out.println("load");
        } else if (e.getSource().equals(this.undoButton)) {
            System.out.println("undo");
        } else if (e.getSource().equals(this.copyButton)) {
            System.out.println("copy");
        } else if (e.getSource().equals(this.pasteButton)) {
            System.out.println("paste");
        } else return;
    }
}
