package com.example.geoquiz.model;

import java.io.Serializable;

public class Setting implements Serializable {

    private boolean buttonTrue, buttonFalse, buttonNext, buttonPrev,
            buttonFirst, buttonLast, buttonCheat;

    private int questionSize;
    private String color;

    public boolean isButtonTrue() {
        return buttonTrue;
    }

    public void setButtonTrue(boolean buttonTrue) {
        this.buttonTrue = buttonTrue;
    }

    public boolean isButtonFalse() {
        return buttonFalse;
    }

    public void setButtonFalse(boolean buttonFalse) {
        this.buttonFalse = buttonFalse;
    }

    public boolean isButtonNext() {
        return buttonNext;
    }

    public void setButtonNext(boolean buttonNext) {
        this.buttonNext = buttonNext;
    }

    public boolean isButtonPrev() {
        return buttonPrev;
    }

    public void setButtonPrev(boolean buttonPrev) {
        this.buttonPrev = buttonPrev;
    }

    public boolean isButtonFirst() {
        return buttonFirst;
    }

    public void setButtonFirst(boolean buttonFirst) {
        this.buttonFirst = buttonFirst;
    }

    public boolean isButtonLast() {
        return buttonLast;
    }

    public void setButtonLast(boolean buttonLast) {
        this.buttonLast = buttonLast;
    }

    public boolean isButtonCheat() {
        return buttonCheat;
    }

    public void setButtonCheat(boolean buttonCheat) {
        this.buttonCheat = buttonCheat;
    }

    public int getQuestionSize() {
        return questionSize;
    }

    public void setQuestionSize(int questionSize) {
        this.questionSize = questionSize;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
