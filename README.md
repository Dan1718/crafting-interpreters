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

The parantheses are mandatory.
