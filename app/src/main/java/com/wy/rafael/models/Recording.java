package com.wy.rafael.models;

import com.wy.rafael.R;

import java.util.ArrayList;

/**
 * Created by christianeyee on 02/09/2016.
 */

public class Recording {
    private String text;
    private int audioId;

    public Recording(String text, int audioId) {
        this.text = text;
        this.audioId = audioId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getAudioId() {
        return audioId;
    }

    public void setAudioId(int audioId) {
        this.audioId = audioId;
    }

    public static ArrayList<Recording> getData() {
        ArrayList<Recording> dataList = new ArrayList<>();

        String[] texts = getTexts();
        int[] ids = getIds();

        for (int i = 0; i < texts.length; i++) {
            Recording r = new Recording(texts[i], ids[i]);
            dataList.add(r);
        }
        return dataList;
    }

    private static int[] getIds() {
        return new int[]{
                R.raw.saan,
                R.raw.pauwi,
                R.raw.abangan,
                R.raw.plate,
                R.raw.matagal,
                R.raw.ingat
        };
    }

    private static String[] getTexts() {
        return new String[]{
                "Saan ka na?",
                "Pauwi ka na ba?",
                "Sige abangan na kita.",
                "Anong plate number ng nasakyan mo?",
                "Matagal ka pa ba?",
                "OK sige ingat.",
        };
    }
}
