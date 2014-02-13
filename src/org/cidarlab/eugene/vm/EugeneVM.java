package org.cidarlab.eugene.vm;

/* =========================== MiniVM.java ===============================

The Mini language is a modified small subset of Java/C. 
A Mini program consists of a single possibly recursive function.
The language has no declarations (implicit type is integer).

MiniVM.java is a compiler-interpreter for Mini written in Java.
The compiler generates code for a virtual machine, which is a modified 
small subset of the Java VM (integer code instead of byte code).

The one-pass compiler is implemented by a top-down recursive descent
parser calling the methods of lexical analysis and code generation.
The parser routines correspond to the grammar rules in EBNF notation.
The regular right parts of EBNF are suitable to postfix generation.
Lexical analysis takes advantage of the Java class StreamTokenizer.

====================== source language syntax (EBNF) =====================

Program    = Function
Function   = identifier "(" identifier ")" Block
Block      = "{" [Statements] "}"
Statements = Statement Statements
Statement  = identifier "=" Expression ";" |
             "if" Condition Statement "else" Statement |
             "while" Condition Statement |
             "return" Expression ";" |
             Block |
             ";"
Condition  = "(" Expression ("=="|"!="|">"|"<") Expression ")"
Expression = Term {("+"|"-") Term}
Term       = Factor {("*"|"/") Factor}
Factor     = number |
             identifier |
             "(" Expression ")" |
             identifier "(" Expression ")" 

================================ VM code =================================

 0    do nothing
 1 c  push constant c onto stack
 2 v  load variable v onto stack 
 3 v  store stack value into variable v
 4    add two top elements of stack, replace by result 
 5    subtract ...
 6    multiply ...
 7    divide ...
 8 a  jump to a if the two top elements of stack are equal
 9 a  jump if ... not equal
10 a  jump if ... less or equal
11 a  jump if ... greater or equal
12 a  unconditional jump to a
13 a  jump to subroutine start address a
14    return from function
15    stop execution

================================ example =================================

source file "fac.mini":
-----------------------
fac(n) {
    if (n == 0)
        return 1;
    else
        return n * fac(n-1);
}

run:
----
<Java Compiler> MiniVM.java
<Java VM> MiniVM fac.mini 8

VMCode: 13 3 15 2 1 1 0 9 14 1 1 14 12 25 2 1 2 1 1 1 5 13 3 6 14 0 
Result: 40320

======================================================================= */

//////////////////////////////////////////////////////////////////////////


public class EugeneVM  {
    static int code_max = 1000; 
    static int stack_max = 10000;
    
    public static int execute(String script, int n) 
    		throws Exception {
    	
    	try {
            Lexer.init(script);                 // init lexer
            Gen.init(code_max);                 // init generator
            Parser.program();                   // call parser 
            //Gen.show();                         // show VM code
            VM.init(Gen.code,stack_max);        // init VM
            int y = VM.exec(n);                 // call VM with n
            return y;                           // return result
        } catch (Error e) {
        	throw new Exception("error "+e.getMessage()); 
        } 
    }
}
