package com.nytimes.application.eventbus;

public class DeleteNewsEvent {
    private int position;

    public DeleteNewsEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
