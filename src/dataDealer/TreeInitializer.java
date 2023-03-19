package dataDealer;

import dataLayer.GenderType;
import dataLayer.Member;
import dataLayer.Tree;

/***
 * @author Justin
 */
public class TreeInitializer {

  public static void main(String[] args) {
    String treeInfoPath = "src/data/tree.data";
    //Initialise a tree node
    Tree d = new Tree(new Member("Ancestor", 0, GenderType.unknown));
    IODealer.writeObjectToFile(d, treeInfoPath);
  }
}
