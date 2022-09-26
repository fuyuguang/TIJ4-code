//: typeinfo/toys/ToyTest.java
// Testing class Class.
package toys;
import static net.mindview.util.Print.*;

interface HasBatteries {}
interface Waterproof {}
interface Shoots {}

class Toy {
  // Comment out the following default constructor
  // to see NoSuchMethodError from (*1*)
  Toy() {}
  Toy(int i) {}
}

class FancyToy extends Toy
implements HasBatteries, Waterproof, Shoots {
  FancyToy() { super(1); }
}

public class ToyTest {
  static void printInfo(Class cc) {
    print("Class name: " + cc.getName() +
      " is interface? [" + cc.isInterface() + "]");
    print("Simple name: " + cc.getSimpleName());
    print("Canonical name : " + cc.getCanonicalName());
  }
  public static void main(String[] args) {
    Class c = null;
    try {
      c = Class.forName("toys.FancyToy");
    } catch(ClassNotFoundException e) {
      print("Can't find FancyToy");
      System.exit(1);
    }
    printInfo(c);	
    for(Class face : c.getInterfaces())
      printInfo(face);
    Class up = c.getSuperclass();
    Object obj = null;
    try {
      // Requires default constructor:
      obj = up.newInstance();
    } catch(InstantiationException e) {
      print("Cannot instantiate");
      System.exit(1);
    } catch(IllegalAccessException e) {
      print("Cannot access");
      System.exit(1);
    }
    printInfo(obj.getClass());
  }
} /* Output:
Class name: toys.FancyToy is interface? [false]
Simple name: FancyToy
Canonical name : toys.FancyToy
Class name: toys.HasBatteries is interface? [true]
Simple name: HasBatteries
Canonical name : toys.HasBatteries
Class name: toys.Waterproof is interface? [true]
Simple name: Waterproof
Canonical name : toys.Waterproof
Class name: toys.Shoots is interface? [true]
Simple name: Shoots
Canonical name : toys.Shoots
Class name: toys.Toy is interface? [false]
Simple name: Toy
Canonical name : toys.Toy
*///:~
