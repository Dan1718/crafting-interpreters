Hi so this is basically me following through the book `craftinginterpreters`, available for free at <https://craftinginterpreters.com>

As far as I know we're implementing a language the author calls LOX, first in Java as an interpreted language, then a bytecode compiler in C.
The language specification follows below, this is a very informal repo, and isn't that detailed either. The original author does it a lot better at <https://craftinginterpreters.com/the-lox-language.html>, and these are just my personal notes.
C-Like language - familiarity
Dynamically typed. If operation on values of the wrong types (division on a string etc, ) it's reported at runtime. Dynamic types are easier to implement and learn, and this is an interpreter as well.

First implementation in Java, then in C.

# Memory Management

Automatic memory management - This is supposed to be a (pseudo) high-level language, and they exist to eliminate error-prone low-level work like manually managing memory. Of the two main techniques for managing memory(reference counting, and garbage collection), Ref counters are much simpler to implement, but is heavily limited. Perl, PHP, Python starteD there, but switched to a full tracing GC soon. Tracing GC is a little hard to work at raw memory, because debugging it is a pain in the ass. SOO we'll make a simpler gc like I already did earlier.

# Data Types

- Booleans `true` and `false`

- Numbers only a double precision floating point because it can represent basically everything while only having one datatype for it.

- String - Just a string

- Nil our implementation of `null`, we call it `nil`

# Expressions

## Arithmetic

- `+` `-` `*` `/`
  Also `condtion ? then : else;`

- also `-` which is a negation operator.

- Everything here only works on numbers but `+` can also concat two strings.

## Comparison and Equality

- `<` `<=` `>` `>=`
- `==` `!=` Can check for values, and types. different types are never equivalent.

## Logical Operators

- `!` `and` `or`

## Precedence

- We're basically inheriting that from C. More details will be added later. `()` works to group stuff for higher precedence similiar to c.

# Statements

- `print` - We're implementing print as a statement makes it a hack because we can use it to debug while working on the language as well. It evalutes oen single expression and displays the result basically.

An expression preceeded by a `;` promotes it to statement. If you want to pack a series of statements where only one is expected, it's similiar to c, we can wrap it up in a `{}` block.

# Variables

- Declare variables using `var` statements, if initializer is omitted then default is `nil`.

# Control flow

An `if` statement is similiar to C, executes on of two statements based on condition some condition.

A `while` loop executes the next statement as long as condition expression evaluates to `true`.

A `for` loop does the same thing, but it can also do some statement inside the condition as well.

# Functions

Function call expression is basically the same as it does in C.

`funcName(arg1,arg2);` or `funcName();`

The parantheses are mandatory unlike Ruby.

- an argument is an actual value you pass to a function when you call it.
- a parameter is a variable that holds the value of the arguement inside the body of the function, thus a function declaration has a parameter list.

- The body of a function is always a block, inside it you can return a value using the `return` statement. If execution reaches the end of the block without hitting a `return` it implicitly returns `nil`

You define a function using the `fun` statement.

# Closures

Functions are first class in Lox, which just means they are real values that you can get a reference to, store in variables, pass around etc.

Since function declarations are statements, you can declare local functions inside another function. If you combine local functions, first class functions and block scope, you run into situations like this.

```c
fun returnFunction() {
  var outside = "outside";

  fun inner() {
    print outside;
  }

  return inner;
}

var fn = returnFunction();
fn();
```

Here inner accesses a local variable declared outside of it's body, for which to work, inner has to hold on to references of the outside variable. This can do it, and it's called closures, implementing these adds a decent amount of complexity because we can no longer assume variable scope works strictly like a stack where local variables evaporate the moment the fucntion returns. Kind of does add to the challenge.

# Classes

Since Lox has dynamic typing, scope, and closures it's almost a functional language, but also halfway to an object-oriented language. Lox is object oriented because most people that implements languages, leaves objects out, and since it's a wild thing the original author decided to do it. Lowkey sounds interesting ;).

## Classes or prototypes

In Class-based languages, there's instances and classes, instances storing state, and classes containing methods and inheritance. Prototype-based languages merge these two concepts, there are only objects, no classes, and each individual object may contain state and methods. Prototypes are easier to implement, but they do so by pushing the compleity onto the user. So for Lox, we'll be going with a class-based language.

## Classes in Lox

```c
class Breakfast {
  cook() {
    print "Eggs a-fryin'!";
  }

  serve(who) {
    print "Enjoy your breakfast, " + who + ".";
  }
}
```

The body of the class contains it's methods, they look like function declarations but without the `fun` keyword. When a class is declared, it creates an object and stores it in the variable. Classes are also first class in Lox.

To create instances, call it like a function.

```
var breakfast = Breakfast();
print breakfast;

```

### Instantiation and initialization

We also need state for classes, and we can add properties onto objects, if it didn't exist, it would be created. To access a field or method on the current object from within a method, we use `this`.

```
breakfast.meat = "sausage";
breakfast.bread = "sourdough";
class Breakfast {
  serve(who) {
    print "Enjoy your " + this.meat + " and " +
        this.bread + ", " + who + ".";
  }

  // ...
}
```

If your class has a method name init() it is called automatically when the object is constructed. Any parameters passed to the class are forwarded to it as well.

```c
class Breakfast {
  init(meat, bread) {
    this.meat = meat;
    this.bread = bread;
  }

  // ...
}

var baconAndToast = Breakfast("bacon", "toast");
baconAndToast.serve("Dear Reader");
// "Enjoy your bacon and toast, Dear Reader."
```

# Inheritance

```c
class Brunch < Breakfast {
  drink() {
    print "How about a Bloody Mary?";
  }
}
```

Here we use the `<` keyword for inheritance. Every method defined in the super/parent class is also available to its subclasses. Even `init()` gets inherited, but we want our own, while also calling the initial one.
To access the superclasses's methods, we can use the `super` keyword.

```c
class Brunch < Breakfast {
  init(meat, bread, drink) {
    super.init(meat, bread);
    this.drink = drink;
  }
}
```

That's basically it for the `core` language, now we work on the standard library, the set of functionality implemented directly in the interpreter. But, the author says we'll not be doing that.
