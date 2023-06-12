package sk.stuba.mtf.oop.gui;

import sk.stuba.mtf.oop.controls.Logic;

import javax.swing.*;
import java.awt.*;

public class Window {
    public Window() {
        JFrame frame = new JFrame("Text Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setResizable(false);
        frame.setFocusable(true);
        frame.setLocationRelativeTo(null);
        frame.requestFocusInWindow();
        
        Logic logic = new Logic();
        JPanel menu = new JPanel();
        
        JButton saveButton = new JButton("Save");
        saveButton.addKeyListener(logic);
        saveButton.setFocusable(false);

        JButton loadButton = new JButton("Load");
        loadButton.addKeyListener(logic);
        loadButton.setFocusable(false);

        JButton undoButton = new JButton("Undo");
        undoButton.addKeyListener(logic);
        undoButton.setFocusable(false);
        
        JButton copyButton = new JButton("Copy");
        copyButton.addKeyListener(logic);
        copyButton.setFocusable(false);

        JButton pasteButton = new JButton("Paste");
        pasteButton.addKeyListener(logic);
        pasteButton.setFocusable(false);

        JComboBox<String> fontBox = new JComboBox<String>();
        fontBox.setFocusable(false);
        fontBox.addItemListener(logic);

        menu.setLayout(new GridLayout(1, 6));
        menu.add(saveButton);       
        menu.add(loadButton);
        menu.add(undoButton);
        menu.add(copyButton);
        menu.add(pasteButton);
        menu.add(fontBox);

        frame.add(menu, BorderLayout.PAGE_START);

        frame.setVisible(true);
    }

}
