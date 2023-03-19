package dataLayer;

import java.io.*;
import java.util.ArrayList;

/***
 * @author Justin
 */

//a member is a node
public class Member implements Serializable, Comparable {

    public static final long serialVersionUID = 42L;
    // Member info:
    private String name = "";
    private int[] birthday = new int[3]; //year/month/day
    private int[] deathday = new int[3]; //year/month/day
    private GenderType gender = GenderType.unknown; //female male unknown
    private MarriageState ifMarried = MarriageState.unknown; //unmarried married unknown
    private String address = "";
    private LiveState isAlive = LiveState.unknown; //died alive unknown
    private String extraMessage = "";

    //Relationship info:
    private int generation;
    private Member father = null;
    private Member mother = null;
    private ArrayList<Member> descendents = new ArrayList<Member>();

    public Member(String name, int year, GenderType a) {
        setName(name);
        setBirthday(year, 0, 0);
        gender = a;
    }

    public Member(
            String name,
            GenderType gender,
            LiveState isAlive,
            int birthyear,
            int birthmonth,
            int birthday,
            int deathyear,
            int deathmonth,
            int deathday, //000 if alive
            MarriageState ifMarried,
            String address,
            String extraMessage
    ) {
        setName(name);
        setIsAlive(isAlive);
        setBirthday(birthyear, birthmonth, birthday);
        setDeathday(deathyear, deathmonth, deathday);
        setGender(gender);
        setIfMarried(ifMarried);
        setAddress(address);
        setExtraMessage(extraMessage);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getBirthday() {
        return birthday;
    }

    public String getBirthdayString() {
        return birthday[0] + "/" + birthday[1] + "/" + birthday[2];
    }

    public void setBirthday(int year, int month, int day) {
        this.birthday[0] = year;
        this.birthday[1] = month;
        this.birthday[2] = day;
    }

    public int[] getDeathday() {
        return deathday;
    }

    public String getDeathdayString() {
        return deathday[0] + "/" + deathday[1] + "/" + deathday[2];
    }

    public void setDeathday(int year, int month, int day) {
        this.deathday[0] = year;
        this.deathday[1] = month;
        this.deathday[2] = day;
    }

    public GenderType getGender() {
        return gender;
    }

    public String getGenderString() {
        return gender.toString();
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public MarriageState getIfMarried() {
        return ifMarried;
    }

    public String getIfMarriedString() {
        return ifMarried.toString();
    }

    public void setIfMarried(MarriageState ifMarried) {
        this.ifMarried = ifMarried;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LiveState getIsAlive() {
        return isAlive;
    }

    public String getIsAliveString() {
        return isAlive.toString();
    }

    public void setIsAlive(LiveState isAlive) {
        this.isAlive = isAlive;
    }

    public String getExtraMessage() {
        return extraMessage;
    }

    public void setExtraMessage(String extraMessage) {
        this.extraMessage = extraMessage;
    }

    public int getGeneration() {
        return generation;
    }

    public void setGenerationUnder(Member parent) {
        this.generation = parent.getGeneration() + 1;
    }

    public Member getFather() {
        return father;
    }

    public void setFather(Member father) {
        this.father = father;
    }

    public Member getMother() {
        return mother;
    }

    public void setMother(Member mother) {
        this.mother = mother;
    }

    public ArrayList<Member> getDescendents() {
        return descendents;
    }

    public String getDescendentsString() {
        String temp = "";
        for (int i = 0; i < descendents.size(); i++) {
            temp = temp + descendents.get(i).name + " ";
        }
        return temp;
    }

    public String toString() {
        System.out.println(name);
        return name;
    }

    public void addinDesendents(Member newMem) {
        //whether add at the flag position
        boolean flag = false;
        for (int i = 0; i < descendents.size() - 1; i++) {
            //sorted when adding
            flag =
                    newMem.compareTo(descendents.get(i)) >= 0 &
                            newMem.compareTo(descendents.get(i + 1)) < 0;
            if (flag) {
                descendents.add(i + 1, newMem);
                break;
            }
        }
        if (!flag) descendents.add(newMem);
        freshGeneration(newMem, this);
    }

    //refresh the generation number when add descendents
    //based on parent's generation number
    private void freshGeneration(Member newMem, Member parent) {
        newMem.setGenerationUnder(parent);
        for (int i = 0; i < newMem.getDescendents().size(); i++) {
            freshGeneration(newMem.getDescendents().get(i), newMem);
        }
    }

    @Override
    //compare birthday
    public int compareTo(Object o) {
        Member B = (Member) o;
        if (this.birthday[0] > B.birthday[0]) return 1; else if (
                this.birthday[0] == B.birthday[0]
        ) if (this.birthday[1] > B.birthday[1]) return 1; else if (
                    this.birthday[1] == B.birthday[1]
            ) if (this.birthday[2] > B.birthday[2]) return 1; else if (
                        this.birthday[2] == B.birthday[2]
                ) return 0;
        return -1;
    }
}
