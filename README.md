# Java Method Overloading: Employee ID Example

**Prepared by:** **Ushindi B. Victoire**  
Software QA Engineer | Requirements Specialist | @ub-victoire | @ub-victor

This lesson introduces Java method overloading through a small `Employee` class. It is written for developers and students who are learning how Java chooses between methods with the same name.

## Learning goals

By the end of this lesson, you should be able to:

- distinguish a method parameter from an argument;
- explain how overloads differ from one another;
- predict which overload Java selects for a call;
- compile and run a Java class that declares a package.

## 1. Project structure

The source file's folders should match its package declaration:

```text
EmployeeDetails/
└── src/
    └── org/
        └── emp/
            └── Employee.java
```

The first line of `Employee.java` is:

```java
package org.emp;
```

This places `Employee` in the `org.emp` package. When running the program, use its fully qualified class name, `org.emp.Employee`.

## 2. A few Java types used in this example

Java is statically typed: a variable or expression has a type that the compiler uses to check the program and choose methods.

| Type | Kind | Example | Notes |
|---|---|---|---|
| `int` | Primitive | `int id = 1001;` | A 32-bit signed integer, commonly used for whole numbers. |
| `long` | Primitive | `long id = 9000000001L;` | A 64-bit signed integer. The `L` marks this literal as a `long`. |
| `String` | Reference type | `String id = "EMP001";` | A class used for text. A String literal uses double quotes. |

Java has eight primitive types: `byte`, `short`, `int`, `long`, `float`, `double`, `char`, and `boolean`. `String` is not primitive.

An identifier such as an employee ID is not necessarily a number. If IDs can contain letters or leading zeroes, represent them as strings. This lesson uses several types to demonstrate overloading; it is not a recommendation to mix ID types in a production system.

## 3. The `Employee` class

The following is the complete program in `src/org/emp/Employee.java`:

```java
package org.emp;

public class Employee {

    public void empId(int id) {
        System.out.println("Employee ID (int): " + id);
    }

    public void empId(long id) {
        System.out.println("Employee ID (long): " + id);
    }

    public void empId(String id) {
        System.out.println("Employee ID (String): " + id);
    }

    public static void main(String[] args) {
        Employee employee = new Employee();

        employee.empId(1001);
        employee.empId(9000000001L);
        employee.empId("EMP001");
    }
}
```

`Employee` declares three methods named `empId`. Each method accepts a different parameter type, so the methods have different signatures. This is **method overloading**.

### Parameters and arguments

In this declaration:

```java
public void empId(int id)
```

`int id` is the **parameter**: it declares the type and name of the value the method accepts.

In this call:

```java
employee.empId(1001);
```

`1001` is the **argument**: the value passed to the method.

The return type is `void`, which means the method does not return a value. Each method instead prints a message.

## 4. How Java selects an overload

Java resolves an overloaded method call at compile time. It uses the method name and the argument's compile-time type, then applies its method-invocation rules to find the most specific applicable method.

| Call | Argument type | Selected overload |
|---|---|---|
| `employee.empId(1001)` | `int` | `empId(int id)` |
| `employee.empId(9000000001L)` | `long` | `empId(long id)` |
| `employee.empId("EMP001")` | `String` | `empId(String id)` |

The suffix `L` is important: it tells Java that `9000000001` is a `long` literal. Without it, this value is too large for an `int` literal. Use uppercase `L` because lowercase `l` can look like the digit `1`.

Selection depends on the argument's type, not just its current numeric value:

```java
long smallId = 1001L;
employee.empId(smallId); // Calls empId(long), even though the value fits in an int.
```

Java can widen some primitive values when looking for an applicable method. For example, with the three overloads above, a `short` argument can be widened to `int`:

```java
short smallId = 12;
employee.empId(smallId); // Calls empId(int).
```

This example shows a straightforward case. More complicated overload sets can involve boxing, reference-type conversions, or ambiguity, so prefer overloads that make call sites clear.

## 5. What makes methods different overloads?

Methods can be overloaded by changing the **number**, **types**, or **order** of their parameters. For example, these declarations have different parameter lists:

```java
void display(int id) { }
void display(int id, String name) { }
void display(String name, int id) { }
```

Changing only the parameter name does not create an overload:

```java
void display(int id) { }
// Not allowed in the same class: void display(int employeeId) { }
```

Changing only the return type does not create an overload either:

```java
// These cannot coexist in the same class:
// int display(int id) { return id; }
// void display(int id) { }
```

For these examples, you can think of a method signature as its name plus its ordered parameter types. Parameter names and return type do not distinguish overloads.

## 6. Overloading and overriding are different

| Overloading | Overriding |
|---|---|
| Methods share a name but have different parameter lists. | A subclass provides an implementation of an inherited instance method. |
| Does not require inheritance. | Requires an inheritance relationship and an overridable method. |
| The compiler selects an overload using the call's compile-time types. | At runtime, Java dispatches an instance-method call to the appropriate overriding implementation. |
| `@Override` is not used just because a method is overloaded. | `@Override` is recommended so the compiler can check that the method really overrides one. |

The `Employee` example demonstrates overloading only. It does not need a subclass, an abstract class, an interface, or `@Override`.

## 7. Access modifiers in this example

Access modifiers control where classes and members can be used.

| Modifier | General access | Typical use |
|---|---|---|
| `private` | Only within the declaring top-level class. | Hide implementation details and internal helper methods. |
| *(no modifier)* | Within the same package. Also called package-private access. | Keep an API available to related classes in one package. |
| `protected` | Within the same package, and in subclasses subject to Java's protected-access rules. | Provide a deliberate extension point for subclasses. |
| `public` | Wherever the declaring type is accessible. | Expose a class or member as part of a public API. |

In the example, `public class Employee` and its `public` methods can be accessed by code outside `org.emp`. A top-level class can be `public` or package-private; it cannot be `private` or `protected`.

Choose the narrowest access that meets the program's needs. For example, fields are often kept `private` and accessed through methods when appropriate. The example uses public methods so that `main` can call them directly.

## 8. Compile and run

Open a terminal at the project root (`EmployeeDetails`) and check that a JDK is available:

```bash
javac -version
java -version
```

Compile the source into an `out` directory:

```bash
mkdir -p out
javac -d out src/org/emp/Employee.java
```

Run the class by specifying its package-qualified name:

```bash
java -cp out org.emp.Employee
```

Expected output:

```text
Employee ID (int): 1001
Employee ID (long): 9000000001
Employee ID (String): EMP001
```

The `L` in the source code marks the literal's type; it is not part of the printed value.

## 9. Common mistakes

- **Using `ind` instead of `int`:** Java's primitive type is spelled `int`.
- **Omitting a parameter name:** A declaration such as `empId(String)` is incomplete; write `empId(String id)`.
- **Using single quotes for text:** `'A'` is a `char`; `"EMP001"` is a `String`.
- **Forgetting `L` on a large long literal:** `9000000001L` is a `long`; `9000000001` is outside the `int` range.
- **Running with the wrong class name:** Because the class is in `org.emp`, run `org.emp.Employee`, not just `Employee`.
- **Treating a different return type as an overload:** Overloads must differ in their parameter lists.

## 10. Check your understanding

1. Which overload is called by `employee.empId(42L)`? Why?
2. Which overload is called by `employee.empId("0042")`? Why might a string be appropriate for this ID?
3. Would `void empId(int employeeNumber)` be a valid additional overload? Explain.
4. Add an overload that accepts both an integer ID and a name:

   ```java
   public void empId(int id, String name) {
       System.out.println("Employee ID: " + id + ", name: " + name);
   }
   ```

   Call it from `main` and observe that it is selected by its two-argument parameter list.

## Further reading

- [The Java Tutorials: Defining Methods](https://docs.oracle.com/javase/tutorial/java/javaOO/methods.html)
- [Java Language Specification: Method Overloading](https://docs.oracle.com/javase/specs/jls/se25/html/jls-15.html#jls-15.12)
