package ru.gb.space_shooter.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.StringBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;

public class HiScores {
    StringBuilder[] name;
    int[] score;

    public HiScores(){
        this.name = new StringBuilder[5];
        for (int i = 0; i < 5; i++) {
            name[i] = new StringBuilder();
        }
        this.score = new int[5];
        checkFile();
    }

    public void loadScores(){
        BufferedReader br = null;
        try {
            br = Gdx.files.local("hiscores.txt").reader(8192);
            for (int i = 0; i < 5; i++) {
                String str = br.readLine();
                String[] arr = str.split("\\t");
                name[i].append(arr[0]);
                score[i] = Integer.parseInt(arr[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveScore() {
        Writer writer = null;
        try {
            writer = Gdx.files.local("hiscores.txt").writer(false);
            for (int i = 0; i < 5; i++) {
                StringBuilder str = new StringBuilder().append(name[i]);
                str.append("\t").append(score[i]).append("\r\n");
                writer.write(str.toString());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getScore (int i){
        return Integer.toString(score[i]);
    }

    public String getName(int i) {
        if(name[i].length()>10)
            name[i].setLength(10);
        return name[i].toString();
    }

    public void setScore (int p, StringBuilder name, int score){
        for(int i = 4; i > p && i > 0; i--){
            this.name[i] = this.name[i-1];
            this.score[i] = this.score[i-1];
        }
        this.name[p] = name;
        this.score[p] = score;
    }

    public int isHiscore(int score){
        int n = 5;
        for (int i = 4; i >=0 ; i--) {
            if(score>this.score[i])
                n=i;
        }
        return n;
    }

    private void checkFile(){
        if(!Gdx.files.local("hiscores.txt").exists()) {
            FileHandle from = Gdx.files.internal("hiscores.txt");
            from.copyTo(Gdx.files.local("hiscores.txt"));
        }
    }
}
