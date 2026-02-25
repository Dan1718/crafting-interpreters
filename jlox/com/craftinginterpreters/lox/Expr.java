package com.craftinginterpreters.lox;

import java.util.List; 

abstract class Expr {
 static class Binary extends Expr {
   Binary(Expr left, Token operateo, Expr right) {
    this.left = left;
    this.operateo = operateo;
    this.right = right;
    }

   final Expr left;
   final Token operateo;
   final Expr right;
   }
 static class Grouping extends Expr {
   Grouping(Expr expression) {
    this.expression = expression;
    }

   final Expr expression;
   }
 static class Literal extends Expr {
   Literal(Object value) {
    this.value = value;
    }

   final Object value;
   }
static class Unary extends Expr {
   Unary(Token Operator, Expr right) {
    this.Operator = Operator;
    this.right = right;
    }

   final Token Operator;
   final Expr right;
   }
}
