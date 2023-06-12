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
        
        menu.setLayout(new GridLayout(1, 6));
        menu.add(logic.getSaveButton());       
        menu.add(logic.getLoadButton());
        menu.add(logic.getUndoButton());
        menu.add(logic.getCopyButton());
        menu.add(logic.getPasteButton());
        menu.add(logic.getFontBox());

        frame.add(logic.getTextArea(), BorderLayout.CENTER);
        frame.add(menu, BorderLayout.PAGE_START);

        frame.setVisible(true);
    }

}
