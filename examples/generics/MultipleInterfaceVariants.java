//: generics/MultipleInterfaceVariants.java
// {CompileTimeError} (Won't compile)

interface Payable<T> {}

/** Payable' 不能用不同的类型参数继承：'Employee' 和 'Hourly' */
class Employee implements Payable<Employee> {}
class Hourly extends Employee implements Payable<Hourly> {} ///:~
