package editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class TextEditor extends JFrame implements ActionListener {

    private JFileChooser fileChooser;
    private JTextArea textArea;
    private JTextField searchField;
    private JScrollPane scrollPane;

    private JButton saveButton;
    private JButton loadButton;

    private JButton searchButton;
    private JButton previousMatchButton;
    private JButton nextMatchButton;

    private JCheckBox regexCheckBox;

    private JMenuBar menuBar;

    private JMenu fileMenu;
    private JMenu searchMenu;

    private JMenuItem loadItem;
    private JMenuItem saveItem;
    private JMenuItem exitItem;

    private JMenuItem startSearchItem;
    private JMenuItem previousMatchItem;
    private JMenuItem nextMatchItem;
    private JMenuItem useRegexItem;

    private SearchEngine searchEngine;

    boolean useRegex = false;

    public TextEditor() {
        initTextEditor();

        initLoadButton();
        initSaveButton();
        initSearchField();
        initSearchButton();
        initPreviousArrow();
        initNextArrow();
        initRegexCheckBox();
        initTextArea();
        initMenuBar();

        initFileChooser();

        addComponents();

        // For testing reasons
        setComponentNames();
    }

    private void setComponentNames() {
        textArea.setName("TextArea");
        searchField.setName("SearchField");
        saveButton.setName("SaveButton");
        loadButton.setName("OpenButton");
        searchButton.setName("StartSearchButton");
        previousMatchButton.setName("PreviousMatchButton");
        nextMatchButton.setName("NextMatchButton");
        regexCheckBox.setName("UseRegExCheckbox");
        fileChooser.setName("FileChooser");
        scrollPane.setName("ScrollPane");
        fileMenu.setName("MenuFile");
        searchMenu.setName("MenuSearch");
        loadItem.setName("MenuOpen");
        saveItem.setName("MenuSave");
        exitItem.setName("MenuExit");
        startSearchItem.setName("MenuStartSearch");
        previousMatchItem.setName("MenuPreviousMatch");
        nextMatchItem.setName("MenuNextMatch");
        useRegexItem.setName("MenuUseRegExp");
    }

    private void initTextEditor() {
        setTitle("Text Editor");
        ImageIcon img = new ImageIcon("\\img\\icon.png");
        setIconImage(img.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setVisible(true);
    }

    private void initMenuBar() {
        menuBar = new JMenuBar();

        initFileMenu();
        initSearchMenu();

        setJMenuBar(menuBar);
    }

    private void initFileMenu() {
        fileMenu = new JMenu("File");

        loadItem = new JMenuItem("Load");
        saveItem = new JMenuItem("Save");
        exitItem = new JMenuItem("Exit");

        loadItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);

        fileMenu.add(loadItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);
    }

    private void initSearchMenu() {
        searchMenu = new JMenu("Search");

        startSearchItem = new JMenuItem("Start search");
        previousMatchItem = new JMenuItem("Previous search");
        nextMatchItem = new JMenuItem("Next match");
        useRegexItem = new JMenuItem("Use regular expressions");

        startSearchItem.addActionListener(this);
        previousMatchItem.addActionListener(this);
        nextMatchItem.addActionListener(this);
        useRegexItem.addActionListener(this);

        searchMenu.add(startSearchItem);
        searchMenu.add(previousMatchItem);
        searchMenu.add(nextMatchItem);
        searchMenu.add(useRegexItem);

        menuBar.add(searchMenu);
    }

    private void initTextArea() {
        textArea = new JTextArea();

        int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
        int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

        scrollPane = new JScrollPane(textArea, v, h);

        forceSize(scrollPane, 290, 290);
    }

    private void initLoadButton() {
        loadButton = new JButton(new ImageIcon("\\img\\open.png"));
        loadButton.addActionListener(this);
        forceSize(loadButton, 30, 30);
    }

    private void initSaveButton() {
        saveButton = new JButton(new ImageIcon("\\img\\save.png"));
        saveButton.addActionListener(this);
        forceSize(saveButton, 30, 30);
    }

    private void initSearchField() {
        searchField = new JTextField();
        forceSize(searchField, 200, 30);
    }

    private void initSearchButton() {
        searchButton = new JButton(new ImageIcon("\\img\\search.png"));
        searchButton.addActionListener(this);
        forceSize(searchButton, 30, 30);
    }

    private void initPreviousArrow() {
        previousMatchButton = new JButton(new ImageIcon("\\img\\previous.png"));
        previousMatchButton.addActionListener(this);
        forceSize(previousMatchButton, 30 ,30);
    }

    private void initNextArrow() {
        nextMatchButton = new JButton(new ImageIcon("\\img\\next.png"));
        nextMatchButton.addActionListener(this);
        forceSize(nextMatchButton, 30, 30);
    }

    private void initRegexCheckBox() {
        regexCheckBox = new JCheckBox("Use regex");
    }

    private void initFileChooser() {
        fileChooser = new JFileChooser();
    }

    private void addComponents() {
        //JPanel for placing all the components
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout()); //setting GridBagLayout for panel

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH; //maximum height, maximum width
        c.insets = new Insets(10, 10, 10, 10);

        // Adding Load Button
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(loadButton, c);

        // Adding Save Button
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 0;
        panel.add(saveButton, c);

        // Adding text field for searching
        c.weightx = 0.5;
        c.weighty = 0.0;
        c.gridwidth = 2;
        c.gridx = 2;
        c.gridy = 0;
        panel.add(searchField, c);

        // Adding search button
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.gridwidth = 1;
        c.gridx = 4;
        c.gridy = 0;
        panel.add(searchButton);

        // Adding arrows
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.gridwidth = 1;
        c.gridx = 5;
        c.gridy = 0;
        panel.add(previousMatchButton);

        c.weightx = 0.0;
        c.weighty = 0.0;
        c.gridwidth = 1;
        c.gridx = 6;
        c.gridy = 0;
        panel.add(nextMatchButton);

        // Adding check box
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.gridwidth = 1;
        c.gridx = 7;
        c.gridy = 0;
        panel.add(regexCheckBox);

        // Adding scroll pane
        c.weightx = 0.0;
        c.weighty = 1.0;
        c.gridwidth = 8;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(scrollPane, c);

        add(fileChooser);
        add(panel);
    }

    public void forceSize(JComponent component, int width, int height) {
        Dimension d = new Dimension(width, height);
        component.setMinimumSize(d);
        component.setMaximumSize(d);
        component.setPreferredSize(d);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loadButton || e.getSource() == loadItem) {
            readFromFile();
        }

        if (e.getSource() == saveButton || e.getSource() == saveItem) {
            writeToFile();
        }

        if (e.getSource() == exitItem) {
            exit();
        }

        if (e.getSource() == searchButton || e.getSource() == startSearchItem) {
            searchEngine = new SearchEngine(this);
            useRegex = regexCheckBox.isSelected();
            searchEngine.showSearchResult(searchField.getText(), useRegex);
        }

        if (e.getSource() == previousMatchButton || e.getSource() == previousMatchItem) {
            if (searchEngine != null) {
                searchEngine.showPreviousMatch();
            }
        }

        if (e.getSource() == nextMatchButton || e.getSource() == nextMatchItem) {
            if (searchEngine != null) {
                searchEngine.showNextMatch();
            }
        }

        if (e.getSource() == useRegexItem) {
            if (useRegex) {
                regexCheckBox.setSelected(false);
                useRegex = false;
            } else {
                regexCheckBox.setSelected(true);
                useRegex = true;
            }
        }
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    private void readFromFile() {
        int response = fileChooser.showOpenDialog(null);

        String fileName;
        if (response == JFileChooser.APPROVE_OPTION) {
            fileName = fileChooser.getSelectedFile().getAbsolutePath();
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
                textArea.read(bufferedReader, "File content");
            } catch (Exception exception) {
                textArea.setText("");
            }
        }
    }

    private void writeToFile() {
        String fileName;

        int response = fileChooser.showSaveDialog(null);

        if (response == JFileChooser.APPROVE_OPTION) {
            fileName = fileChooser.getSelectedFile().getAbsolutePath();
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
                textArea.write(bufferedWriter);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private void exit() {
        dispose();
    }
}
