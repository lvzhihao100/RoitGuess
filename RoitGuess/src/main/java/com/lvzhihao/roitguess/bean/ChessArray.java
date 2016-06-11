package com.lvzhihao.roitguess.bean;

import android.widget.ImageView;

import com.lvzhihao.roitguess.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vzhihao on 2016/3/30.
 */
public class ChessArray {
    public static List<String> characterMeArray=new ArrayList<String>(15);
    static List<String> characterEmyArray=new ArrayList<String>(15);
    static  Random random=new Random();
    public static final int EMYCOMP=1;
    public static final int MECOMP=0;
    public static final int NOWEAPON=0;
    public static final int SCISSORS=1;
    public static final int ROCK=2;
    public static final int PAPER=3;
    int state=0;



    public static void  init(){
        characterEmyArray.clear();
        characterMeArray.clear();
           for (int j=0;j<15;j++){
               String characterMe="chess/"+0+"character"+j+".png";
               String characterEmy="chess/"+1+"character"+j+".png";
               String skillMe="chess/"+0+"skill"+j+".png";
               String skillEmy="chess/"+1+"skill"+j+".png";
               characterEmyArray.add(characterEmy);
               characterMeArray.add(characterMe);
           }
   }

    public ChessArray(ImageView imageView){
        this.imageView=imageView;
    }
    public void initCharacterAndSkill(){
        if (darkOrsun==MECOMP){
            if (characterMeArray.size()>0) {
                int r = random.nextInt(characterMeArray.size());
                character = characterMeArray.get(r);
                System.out.println("########################c"+character);
                System.out.println("########################c"+r);
                System.out.println("########################c"+characterMeArray.toString());
                Pattern p = Pattern.compile("\\d{2,}");
                Matcher m=p.matcher(character);
                if (m.find()){
                    skill = "chess/" + 0 + "skill" + m.group()+".png";
                }else {
                    skill = "chess/" + 0 + "skill" + character.charAt(16) + ".png";
                }
                characterMeArray.remove(r);
            }
        }else if(darkOrsun==EMYCOMP){
            if (characterEmyArray.size()>0) {
                int r = random.nextInt(characterEmyArray.size());
                character = characterEmyArray.get(r);
                Pattern p = Pattern.compile("\\d{2,}");
                Matcher m=p.matcher(character);
                if (m.find()){
                    skill = "chess/" + 1 + "skill" + m.group()+".png";
                }else {
                    skill = "chess/" + 1 + "skill" + character.charAt(16) + ".png";
                }
                characterEmyArray.remove(r);
            }
        }
    }

    ImageView imageView;
    int darkOrsun;
    int comp;
    int weapon;
    String character;
    String skill;

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public  void removeCharacterAndSkill(){

        characterMeArray.add(character);
        character=null;
        skill=null;
    }

    public int getDarkOrsun() {
        return darkOrsun;
    }

    public void setDarkOrsun(int darkOrsun) {
        this.darkOrsun = darkOrsun;
    }
    public int getComp() {
        return comp;
    }

    public void setComp(int comp) {
        this.comp = comp;
    }

    public int getWeapon() {
        return weapon;
    }

    public void setWeapon(int weapon) {
        this.weapon = weapon;
    }

    public String getCharacter() {
        return character;
    }
    public String getSkill() {
        return skill;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
