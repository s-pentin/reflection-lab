package org.example.classes;

import org.example.generator.Generatable;

@Generatable(impls = {TextMessage.class})
public abstract class AbstractMessage {
    private final String text;

    public AbstractMessage(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
