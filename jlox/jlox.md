So this is part 1 of the project, an interpreter written in Java.

# The Beginning

So considering I haven't coded in Java, it was kind of weird to start, but yes. THANKFULLY the author gave us the basic imports, and public class definition.

The `main` method takes in a string of args, if there's more than 1 arg, it print's the usage string, and exits, otherwise either runs `runFile()` or `runPrompt()` for no argument.

- `runFile()` reads the file as bytes, passes it as a string into the `run()` method.
- `runPrompt()` takes in input line by line, runs the line, and if `line` == `null` then breaks out of the loop.
- The `run` method just calls the Scanner, and just prints the tokens for now.

Now before implementing the `Scanner` Class, we are implementing basic error handling, there's a `error` method that calls `report` which prints out the error line + the error message. Along with this, we have a boolean `hadError` for the entire class to see if there's an error and break out of the file.
The reason this happened outside the program is because good practice, it's SO MUCH BETTER if we keep the error handling outside of what could create the errors.

# Scanning

The first step in compiler/interpreter is scanning. Taking in raw source code, grouping it into tokens. Now this is a good starting point.
The aim for this section is to get a full-featured scanner, than takes in a string of Lox, and produce tokens that we'll feed into the parser.

## Lexemes and Tokens

So lexical analysis is about taking a string, figuring out what the keywords are like function definition, loops etc, grouping them together meaningfully. Lexemes are substrings of the sourcecode split meaningfully.

### Token Type

Keywords are part of the language's grammar, The parser could categorize tokens from the raw lexeme by comparing strings, but that's slow and ugly. Instead, when we parse the line of code, we also remember which kind of lexeme it represents. We have a different type of each keyword, operator, bit of punctuation and literal type.

So we have an enum defined in `TokenType.java`. Something else we'd like to store, is the tokentype, the lexeme itself, and whatever data we want like the line number as well, and wrap it in a class at `Token.java`

Now we know what we're trying to do, some basic structure for it, now we just need to put a loop, figure out the lexeme the character belongs to, emit a token. Then it does it again and again, starting from the next character.

## Scanner

- Make a new method for scanner, and a function scantokens(), which loops through again and again, calls another method scanToken();, and at the end, adds an EOF token.
