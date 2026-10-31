package org.example.classes;

public abstract class AbstractMessage {
    private final String text;

    public AbstractMessage(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
