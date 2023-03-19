package dataLayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

/***
 * @author Justin
 */
public class Tree implements Serializable {
    public static final long serialVersionUID = 42L;
    private Member root;
    private LinkedList<Member> sortedlist;//sorted on DOB
    private ArrayList<Integer> generationCount;//count the number of members in each generation
    static Member tempmem = null;

    //constructor
    public Tree(Member ancient) {
        root = ancient;
        generationCount = new ArrayList<Integer>();
        resort();
    }


    public void insert(Member x) {
        insert(root, x);
    }

    public boolean insert(String parentName, Member mem2add) {
        Member a = find(parentName);
        boolean temp = insert(a,mem2add);
        resort();
        return temp;
    }

    //insert member
    private boolean insert(Member parent, Member mem2add) {
        if (find(parent.getName())!=null) {
            parent.addinDesendents(mem2add);
            switch (parent.getGender()) {
                case male:
                    mem2add.setFather(parent);
                    break;
                case female:
                    mem2add.setMother(parent);
                    break;
                case unknown:
            }
            mem2add.setGenerationUnder(parent);
            return true;
        }else{
            return false;
        }
    }

    //remove member
    public void remove(String moveName){
        remove(root,moveName);
        resort();
    }

    private void remove(Member t, String name) {
        //remove data in parents' data
        if (name.equals(t.getName())) {
            if (t.getFather() != null) {
                t.getFather().getDescendents().remove(t);
            }
            if (t.getMother() != null) {
                t.getMother().getDescendents().remove(t);
            }
            t = null;
        }
        //search recursively
        ArrayList<Member> temp = t.getDescendents();
        for (int i = 0; i < temp.size(); i++) {
            remove(temp.get(i), name);
        }
    }

    //find member from root
    public Member find(String name){
        tempmem = null;
        return find(root,name);
    }

    //find member from a certain node
    //set tempmem to null when use this method
    private Member find(Member t, String name){
        boolean have=name.equals(t.getName());
        if(have){
            tempmem = t;
            return tempmem;
        }
        ArrayList<Member> temp = t.getDescendents();
        for (int i = 0; i < temp.size(); i++) {
            find(temp.get(i),name);
        }
        return tempmem;//if return root, not founded
    }


    public boolean isDirectRelated(String a,String b){
        Member memberA = find(a);
        Member memberB = find(b);
        tempmem =null;
        Member temp1 = find(memberA,b);
        tempmem = null;
        Member temp2 = find(memberB,a);
        if (temp1==null&&temp2 == null) {
            return false;
        }else{
            return true;
        }
    }


    //resorting
    private static boolean sortflag = false;

    public void resort(){
        this.sortedlist = new LinkedList<Member>();
        resort(root);
    }


    private void resort(Member t){
        //more than 1, compare 2 neighbours
        for (int i = 0; i < sortedlist.size()-1; i++) {
            if (t.compareTo(sortedlist.get(i))>=0 && t.compareTo(sortedlist.get(i+1))<0){
                sortedlist.add(i+1,t);
                sortflag =true;
                break;
            }
        }

        //1 exists or more than 1, compare the first
        if (sortedlist.size()>0){
            if (t.compareTo(sortedlist.get(0))<0){
                sortedlist.add(0,t);
                sortflag=true;
            }
        }
        //add at the end
        if (!sortflag){
            sortedlist.add(sortedlist.size(),t);
        }
        sortflag = false;
        //resort all with recursion
        ArrayList<Member> temp = t.getDescendents();
        for (int i = 0; i < temp.size(); i++) {
            resort(temp.get(i));
        }
    }


    public LinkedList<Member> getSortedlist() {
        return sortedlist;
    }


    //find a member
    //move a member and his children under the found member
    public boolean move2des(String moved,String targetParent){
        Member movedMem = find(moved);
        Member targetParentMem = find(targetParent);
        if (movedMem==null||targetParentMem==null){
            return false;
        }else {
            this.moveHelper(moved);
            targetParentMem.addinDesendents(movedMem);
            switch (targetParentMem.getGender()) {
                case male:
                    movedMem.setFather(targetParentMem);
                    break;
                case female:
                    movedMem.setMother(targetParentMem);
                    break;
                case unknown:
            }
            resort();
            return true;
        }
    }
    public void moveHelper(String moveName){
        moveHelper2(root,moveName);
        resort();
    }

    private void moveHelper2(Member t, String name) {
        //remove data in parents' data
        if (name.equals(t.getName())) {
            if (t.getFather() != null) {
                t.getFather().getDescendents().remove(t);
            }
            if (t.getMother() != null) {
                t.getMother().getDescendents().remove(t);
            }
        }
        //search recursively
        ArrayList<Member> temp = t.getDescendents();
        for (int i = 0; i < temp.size(); i++) {
            moveHelper2(temp.get(i), name);
        }
    }

    public Member getRoot() {
        return root;
    }

     public ArrayList<Integer> generationStatistic(){
        generationCount = new ArrayList<Integer>();
        generationStatistic(root);
        System.out.println("generation statistic over");
        return generationCount;
    }

    //count the number of members in each generation
    //to draw tree graph
    private void generationStatistic(Member member){
        int tempGen = member.getGeneration();
        if (tempGen<generationCount.size()){
            generationCount.set(tempGen, generationCount.get(tempGen)+1);
        }else{
            generationCount.add(1);
        }
        ArrayList<Member> tempDes = member.getDescendents();
        for (int i = 0; i<tempDes.size(); i++) {
            generationStatistic(tempDes.get(i));
        }
    }
}
