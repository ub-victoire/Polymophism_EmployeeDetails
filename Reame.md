# Day 3 — Data types, polymorphism, and abstraction
## Task 1: Overloading empId()

## 1. Understand this exercise

| Requirement | Implementation |
|---|---|
| Project | `EmployeeDetails` |
| Package | `org.emp` |
| Class | `Employee` |
| Method name | `empId` |
| Main concept | Several methods with the same name but different parameter types |

We will create these overloads:

```java
public void empId(int id)
public void empId(long id)
public void empId(String id)
```

The method name stays the same. The parameter type changes. These are three **different method signatures**.

The assignment does not specify which parameter types or how many overloads are needed. Using these three is a simple, explicit interpretation.

**Scope:** this task demonstrates method overloading. It does not require inheritance, overriding, an abstract class, or an interface.

## 2. Correct the data-type notes first

A data type determines what values an expression or variable can represent and which operations are permitted. A variable stores a value; the data type itself is not a storage container.

### The eight primitive types

| Type | Defined value size | Example declaration | Default for an instance/static field |
|---|---|---|---|
| `byte` | 8 bits / 1 byte | `byte level = 10;` | `0` |
| `short` | 16 bits / 2 bytes | `short count = 300;` | `0` |
| `int` | 32 bits / 4 bytes | `int id = 1001;` | `0` |
| `long` | 64 bits / 8 bytes | `long id = 9000000001L;` | `0L` |
| `float` | 32 bits / 4 bytes | `float score = 9.5F;` | `0.0F` |
| `double` | 64 bits / 8 bytes | `double score = 9.5;` | `0.0D` |
| `boolean` | `true` or `false`; no single universal storage size specified | `boolean active = true;` | `false` |
| `char` | 16 bits / 2 bytes; one UTF-16 code unit | `char grade = 'A';` | `'\u0000'` |

These value widths do not describe the complete memory footprint of a containing object, including alignment and headers. A `char` does not necessarily represent an entire Unicode character; some characters require two UTF-16 code units.

The course's numbers such as “int 9,” “long 18,” and “float 6–7” are **not byte sizes**. Floating-point precision is a different concept from storage size. Also, `float` and `double` cannot represent every decimal fraction exactly.

### Reference types and defaults

`String`, arrays, interfaces, and class types are reference types. A reference value can refer to an object or be `null`. `String` is a class, not a ninth primitive type.

**Important:** the defaults above apply to fields; array elements also receive default initialization. Local variables must be definitely assigned before use.

```java
public class Example {
    private int count; // Automatically initialized to 0.

    public void show() {
        int localCount;
        // System.out.println(localCount); // Does not compile: not initialized.
    }
}
```

### Wrapper classes

Each primitive type has a corresponding wrapper class:

| Primitive | Wrapper |
|---|---|
| `byte` | `Byte` |
| `short` | `Short` |
| `int` | `Integer` |
| `long` | `Long` |
| `float` | `Float` |
| `double` | `Double` |
| `boolean` | `Boolean` |
| `char` | `Character` |

Boxing converts a primitive value to its corresponding wrapper reference; unboxing obtains the primitive value from a wrapper. A wrapper-typed field defaults to `null`, but a wrapper object is not itself “null by default.” Unboxing a null reference throws `NullPointerException`.

We will not add wrapper overloads to today's program because mixing boxing and primitive overloads would obscure the basic lesson.

## 3. Create a new Day 3 project safely

You already have a Day 1 repository named `EmployeeDetails`. Do not overwrite it.

From the **common parent directory where you keep your exercise projects**, create a Day 3 folder:

```bash
mkdir -p Day03/EmployeeDetails/src/org/emp
mkdir -p Day03/EmployeeDetails/.vscode
cd Day03/EmployeeDetails
code .
```

The project is still named `EmployeeDetails`, but its location is distinct:

```text
YourExercises/
├── EmployeeDetails/          # Earlier introduction exercise
└── Day03/
    └── EmployeeDetails/      # Today's overloading exercise
```

If the new directory already contains work, inspect it before editing files.

### Project structure

```text
EmployeeDetails/
├── .gitignore
├── README.md
├── .vscode/
│   └── settings.json
└── src/
    └── org/
        └── emp/
            └── Employee.java
```

Create `.vscode/settings.json`:

```json
{
    "java.project.sourcePaths": ["src"],
    "java.project.outputPath": "out"
}
```

Create `.gitignore`:

```gitignore
*.class
out/
bin/
target/
*~
*.java.save
*.swp
*.swo
*.log
```

Continue using your installed JDK 25. No preview feature or additional tool is needed.

## 4. Implement Employee.java

Create `src/org/emp/Employee.java`:

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
        var employee = new Employee();

        employee.empId(1001);
        employee.empId(9000000001L);
        employee.empId("EMP001");
    }
}
```

One object is enough to invoke all three overloads. No `@Override` annotation belongs on these methods: they are overloads, not overrides.

The assignment writes `empId()` as the method's name. That does **not** mean you must include a zero-parameter version: overloading based on parameter data types requires parameters.

## 5. Trace overload selection

| Call | Argument's compile-time type | Selected declaration |
|---|---|---|
| `employee.empId(1001)` | `int` | `empId(int id)` |
| `employee.empId(9000000001L)` | `long` | `empId(long id)` |
| `employee.empId("EMP001")` | `String` | `empId(String id)` |

`L` marks the integer literal as a `long`. Use uppercase `L` rather than lowercase `l`, which is easily confused with the digit 1. A large integer literal without `L` can fail to compile even when a long overload exists.

Double quotes make `"EMP001"` a String literal. Single quotes are used for a char literal, such as `'A'`, not a multi-character string.

### Parameters versus arguments

In the declaration:

```java
public void empId(int id)
```

`id` is a **parameter**.

In the call:

```java
employee.empId(1001);
```

`1001` is an **argument**.

### Overloading is based on parameter types, not values

This call selects the long overload:

```java
long smallId = 1001L;
employee.empId(smallId);
```

The numerical value could fit in an int, but the variable's compile-time type is long. Java does not select the int overload merely because the current value is small.

This widening example selects the int overload:

```java
short smallId = 12;
employee.empId(smallId);
```

There is no short overload. Both int and long are widening options, and int is the more specific applicable primitive overload here. A complete overload-resolution algorithm has additional rules; today's three direct calls deliberately avoid that complexity.

## 6. What counts as an overload?

Changing the parameter types, number, or order can create another signature.

Changing **only the parameter name** cannot:

```java
public void empId(int id) {
}

// Illegal duplicate signature if added to the same class:
// public void empId(int employeeNumber) {
// }
```

Changing **only the return type** cannot distinguish overloads either:

```java
// These cannot both be declared in one class:
// public void empId(int id) { }
// public int empId(int id) { return id; }
```

For this basic example, think of a method signature as its name plus its ordered parameter types. Return type and parameter variable names are not part of what distinguishes these overloads.

## 7. Overloading versus overriding

| Overloading | Overriding |
|---|---|
| Same name, different parameter signatures | Subclass supplies a new implementation of an inherited instance method |
| Selected using compile-time type information and overload rules | Runtime dispatch selects the overriding implementation for the chosen instance method |
| No inheritance required | Requires an appropriate inherited method |
| No `@Override` for an overload alone | Use `@Override` to let the compiler check your intention |

Overloads can also be inherited; they do not all have to be declared in the same class. The course's “same class” description is a useful first example, not a universal restriction.

For overriding, parameter **types** must match after the applicable Java rules—not the parameter variable names. Return types must be compatible, and an overriding method cannot reduce access. Static methods are hidden rather than overridden; private methods are not inherited for overriding.

Overload selection happens at compile time. Runtime overriding can then apply to the selected instance-method signature. These are separate mechanisms.

## 8. Compile and run

From the new Day 3 `EmployeeDetails` project root:

```bash
javac --release 25 -d out src/org/emp/Employee.java && java -cp out org.emp.Employee
```

Expected output:

```text
Employee ID (int): 1001
Employee ID (long): 9000000001
Employee ID (String): EMP001
```

The long literal's `L` is source syntax and is not printed as part of the number.

Use the compiled class name in the launch command so you test the output under `out/`.

## 9. Corrections to remember for the upcoming abstraction tasks

These are corrections to the lesson notes, not extra features to implement today:

- An abstract class cannot be instantiated **directly**, even when it contains no abstract methods. It can contain constructors, fields, concrete methods, and abstract methods.
- The abstract class is commonly the **superclass**, and a concrete subclass implements its abstract methods. The notes reverse those roles.
- Abstract classes can contain business logic in concrete methods. They are not limited to signatures.
- An abstract method has no implementation body, but it does not have to be public: protected or package-private abstract methods are possible. Private abstract methods are not allowed.
- Modern interfaces are not limited to abstract methods. They can contain default and static methods with implementations, and private helper methods. They do not have per-instance fields or constructors.
- Ordinary interface methods declared without a body are implicitly public and abstract. That does not make every possible interface method public and abstract.
- A class **implements** an interface. An interface **extends** other interfaces.
- “Partial abstraction” and “full abstraction” are teaching shorthand, not a reliable modern distinction between abstract classes and interfaces.

For real employee data, choose one consistent ID representation based on the domain. String is often useful for identifiers that include letters or meaningful leading zeros. These three ID representations are an exercise in overload selection, not a recommendation to mix ID types in a production system.

## 10. Add a README

Create `README.md`:

````markdown
# EmployeeDetails — Method Overloading

Day 3, Task 1: overload empId using different parameter data types.

## Structure

- Package: org.emp
- Class: Employee
- Overloads: empId(int), empId(long), empId(String)

## Requirements

JDK 25. No external dependencies.

## Compile and run

From the project root:

```bash
javac --release 25 -d out src/org/emp/Employee.java
java -cp out org.emp.Employee
```

## Concepts practiced

- Primitive and reference argument types
- Method overloading
- Compile-time overload selection
- Long literals and String literals

This project is separate from the Day 1 EmployeeDetails exercise.
The ID values are fictional demonstration data.
````

## 11. Commit without reusing the old repository

From this new project's root, check:

```bash
git rev-parse --show-toplevel
```

If no repository contains this directory, initialize:

```bash
git init -b main
```

If it reports a parent repository, stop and resolve the layout before creating a nested repository. If it reports this project root, skip initialization.

Stage, review, and commit:

```bash
git add src/org/emp/Employee.java .vscode/settings.json .gitignore README.md
git diff --cached
git commit -m "Demonstrate empId method overloading"
```

The local project can retain the required name `EmployeeDetails`, while the hosted repository has a distinct name such as **EmployeeDetails-Overloading**. Create an empty repository under your account and use its actual URL as a new origin. Do not attach this project to your old EmployeeDetails remote.

Then push:

```bash
git push -u origin main
```

## 12. Review checklist

- [ ] Day 1's EmployeeDetails remains untouched.
- [ ] The class declares three empId overloads with different parameter types.
- [ ] No duplicate method signatures or unnecessary inheritance.
- [ ] The three calls select the intended overloads.
- [ ] The program compiles and prints three lines.
- [ ] Only source, documentation, and portable settings are committed.
- [ ] The new repository uses a separate remote from Day 1.

**Your checkpoint:** before running it, predict which overload receives `1001`, `1001L`, and `"1001"`. Then share your source and terminal output for review.
