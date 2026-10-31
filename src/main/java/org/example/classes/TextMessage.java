package org.example.classes;

import org.example.generator.Generatable;

@Generatable(implement = AbstractMessage.class)
public final class TextMessage extends AbstractMessage {

    public TextMessage(String text) {
        super(text);
    }
}

