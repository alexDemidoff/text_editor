package editor;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchEngine extends SwingWorker<ArrayList<Integer>, Void> {

    String textToSearch;
    TextEditor textEditor;
    ArrayList<Integer> indexes;
    ArrayList<String> regexMatches;

    boolean useRegex;
    int currentIndex;

    public SearchEngine(TextEditor textEditor) {
        this.textEditor = textEditor;
    }

    @Override
    protected ArrayList<Integer> doInBackground() {
        String text = textEditor.getTextArea().getText();
        indexes = new ArrayList<>();
        regexMatches = new ArrayList<>();
        int matchIndex = 0;
        int fromIndex = 0;

        if (useRegex) {
            String patternStr = textToSearch;
            Pattern pattern = Pattern.compile(patternStr);
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                matchIndex = matcher.start();//this will give you index
                indexes.add(matchIndex);
                regexMatches.add(matcher.group());
            }
        } else {
            while (matchIndex != -1 && matchIndex < text.length()) {
                matchIndex = text.indexOf(textToSearch, fromIndex);
                if (matchIndex != -1) {
                    indexes.add(matchIndex);
                    fromIndex = matchIndex + textToSearch.length();
                }
            }
        }

        return indexes;
    }

    @Override
    protected void done() {
        try {
            indexes = get();
            if (!indexes.isEmpty()) {
                currentIndex = 0;
                selectText();
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void showPreviousMatch() {
        if (isDone() && !indexes.isEmpty()) {
            if (currentIndex > 0) {
                currentIndex--;
            } else {
                currentIndex = indexes.size() - 1;
            }
            selectText();
        }
    }

    public void showNextMatch() {
        if (isDone() && !indexes.isEmpty()) {
            if (currentIndex < indexes.size() - 1) {
                currentIndex++;
            } else {
                currentIndex = 0;
            }
            selectText();
        }
    }

    public void showSearchResult(String textToSearch, boolean useRegex) {
        if (!textToSearch.isEmpty()) {
            this.textToSearch = textToSearch;
            this.useRegex = useRegex;
            execute();
        }
    }

    private void selectText() {
        if (useRegex) {
            textEditor.getTextArea().setCaretPosition(indexes.get(currentIndex) + regexMatches.get(currentIndex).length());
            textEditor.getTextArea().select(indexes.get(currentIndex), indexes.get(currentIndex) + regexMatches.get(currentIndex).length());
            textEditor.getTextArea().grabFocus();
        } else {
            textEditor.getTextArea().setCaretPosition(indexes.get(currentIndex) + textToSearch.length());
            textEditor.getTextArea().select(indexes.get(currentIndex), indexes.get(currentIndex) + textToSearch.length());
            textEditor.getTextArea().grabFocus();
        }
    }
}
