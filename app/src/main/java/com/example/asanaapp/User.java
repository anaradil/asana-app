package com.example.asanaapp;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Usser on 17.11.2016.
 */
public class User {
    private String name;
    private String gender;
    private int xp;
    private ArrayList<PoseObject> poses = null;
    private ArrayList<Poses> backendlessPoses = null;
    public User(String name, String gender, int xp, PoseObject[] p, Poses[] bPoses) {
        this.name = name;
        this.gender = gender;
        this.xp = xp;
        if(p != null || bPoses != null) {
            backendlessPoses = new ArrayList<Poses>(Arrays.asList(bPoses));
            poses = new ArrayList<PoseObject>(Arrays.asList(p));
        } else {
            backendlessPoses = new ArrayList<Poses>();
            poses = new ArrayList<PoseObject>();
        }
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public ArrayList<PoseObject> getPoses() {
        return poses;
    }

    public void addPose(PoseObject p) {
        poses.add(p);
    }

   public ArrayList<Poses> getBackendlessPoses() {
        return backendlessPoses;
    }

    public Poses[] getBackPosesArray() {
        Poses[] tmp = new Poses[backendlessPoses.size()];
        int i = 0;
        for(Poses p:backendlessPoses) {
            tmp[i] = p;
            i++;
        }
        return tmp;
    }

    public void addBPoses(Poses poses) {
        backendlessPoses.add(poses);
    }

    public String getLevel() {
        String level = null;
        if(xp < 300)
            level = "1";
        else if(xp >=300 && xp < 700)
            level = "2";
        else
            level = "3";
        return level;
    }
}
