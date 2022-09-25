//: generics/Wildcards.java
// Exploring the meaning of wildcards.

/**
 * https://www.codenong.com/cs106859945/
 * Thinking In Java Part13（无界通配符，确切泛型参数，超类型通配符 之间区别。用上界通配符确定返回类型，用下界通配符确定返回类型）
 */
public class Wildcards {
   // Raw argument:
  //原始论点
  static void rawArgs(Holder holder, Object arg) {


    /** 未经检查地调用“set(T)”作为原始类型“Holder”的成员 */
     holder.set(arg); // Warning:
    //   Unchecked call to set(T) as a
    //   member of the raw type Holder
    // holder.set(new Wildcards()); // Same warning

    // Can't do this; don't have any 'T':
//     T t = holder.get();

    // OK, but type information has been lost:
    Object obj = holder.get();
  }

  // Similar to rawArgs(), but errors instead of warnings:
  /** 类似于 rawArgs()，但错误而不是警告： */
  static void unboundedArg(Holder<?> holder, Object arg) {
    // holder.set(arg); // Error:
    //   set(capture of ?) in Holder<capture of ?>
    //   cannot be applied to (Object)
    // holder.set(new Wildcards()); // Same error

    // Can't do this; don't have any 'T':
    // T t = holder.get();

    // OK, but type information has been lost:
    Object obj = holder.get();
  }	
  /** 精确的 */
  static <T> T exact1(Holder<T> holder) {
    T t = holder.get();
    return t;
  }
  static <T> T exact2(Holder<T> holder, T arg) {
    holder.set(arg);
    T t = holder.get();
    return t;
  }

  /** 荒野子类型 */
  static <T> T wildSubtype(Holder<? extends T> holder, T arg) {
    // holder.set(arg); // Error:
    //   set(capture of ? extends T) in
    //   Holder<capture of ? extends T>
    //   cannot be applied to (T)
    T t = holder.get();  //只能调用 get 获取方法，不能设置
    return t;
  }

  /** 荒野超类型 */
  static <T> void wildSupertype(Holder<? super T> holder, T arg) {
    /** 只能设置值， */
    holder.set(arg);
    // T t = holder.get();  // Error:
    /** 不兼容的类型：找到 Object，需要 T */
    //   Incompatible types: found Object, required T

    // OK, but type information has been lost:
    Object obj = holder.get();
  }
  public static void main(String[] args) {
    //变量 'raw' 初始化器 'new Holder<Long>()' 是多余的
    //Variable 'raw' initializer 'new Holder<Long>()' is redundant
    Holder raw = new Holder<Long>();
    // Or:
    raw = new Holder();
    //合格的
    Holder<Long> qualified = new Holder<Long>();

    //无界
    Holder<?> unbounded = new Holder<Long>();

    //有界的
    Holder<? extends Long> bounded = new Holder<Long>();

    Long lng = 1L;

    rawArgs(raw, lng);
    rawArgs(qualified, lng);
    rawArgs(unbounded, lng);
    rawArgs(bounded, lng);
	
    unboundedArg(raw, lng);
    unboundedArg(qualified, lng);
    unboundedArg(unbounded, lng);
    unboundedArg(bounded, lng);

    //Object r1 = exact1(raw); // Warnings:
    //   Unchecked conversion from Holder to Holder<T>
    //   Unchecked method invocation: exact1(Holder<T>)
    //   is applied to (Holder)
    Long r2 = exact1(qualified);
    Object r3 = exact1(unbounded); // Must return Object
    Long r4 = exact1(bounded);
	
    // Long r5 = exact2(raw, lng); // Warnings:
    //   Unchecked conversion from Holder to Holder<Long>
    //   Unchecked method invocation: exact2(Holder<T>,T)
    //   is applied to (Holder,Long)
    Long r6 = exact2(qualified, lng);
     Long r7 = exact2(unbounded, lng); // Error:
    //   exact2(Holder<T>,T) cannot be applied to
    //   (Holder<capture of ?>,Long)
    // Long r8 = exact2(bounded, lng); // Error:
    //   exact2(Holder<T>,T) cannot be applied
    //   to (Holder<capture of ? extends Long>,Long)


     Long r9 = wildSubtype(raw, lng); // Warnings:
    //   Unchecked conversion from Holder
    //   to Holder<? extends Long>
    //   Unchecked method invocation:
    //   wildSubtype(Holder<? extends T>,T) is
    //   applied to (Holder,Long)
    Long r10 = wildSubtype(qualified, lng);
    // OK, but can only return Object:
    Object r11 = wildSubtype(unbounded, lng);
    Long r12 = wildSubtype(bounded, lng);

    Wildcards.wildSupertype(raw, lng); // Warnings:
    Wildcards.<Object>wildSupertype(raw, lng); // Warnings:

    //   Unchecked conversion from Holder
    //   to Holder<? super Long>
    //   Unchecked method invocation:
    //   wildSupertype(Holder<? super T>,T)
    //   is applied to (Holder,Long)

    wildSupertype(qualified, lng);
    /** 原因：不存在类型变量的实例，因此 Long 符合捕获 ? */
     wildSupertype(unbounded, lng); // Error:

    //   wildSupertype(Holder<? super T>,T) cannot be
    //   applied to (Holder<capture of ?>,Long)

     wildSupertype(bounded, lng); // Error:
    //   wildSupertype(Holder<? super T>,T) cannot be
    //  applied to (Holder<capture of ? extends Long>,Long)

  }
} ///:~
