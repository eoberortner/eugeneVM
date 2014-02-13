package org.cidarlab.eugene.vm;

//virtual machine

public class VM implements Code 
{
 private static int p[]; // program code
 private static int ip;  // instruction pointer
 private static int s[]; // stack
 private static int sp;  // stack pointer 
 private static int fp;  // frame pointer
 private static int fs;  // frame size
 
 static void init(int code[],int stack_max) {
     p = code;
     ip = 0;
     s = new int[stack_max];
     sp = 0;
     fp = 0;
     fs = Symtab.n; }
         
 static int exec(int arg) throws Error {
     s[0] = arg;
     sp++;
     while (p[ip] != M_stop) {
         switch(p[ip]) {
         case M_push:
             s[sp] = p[ip+1];
             sp++;
             ip = ip+2;
             break;
         case M_load:
             s[sp] = s[fp+p[ip+1]];
             sp++;
             ip = ip+2;
             break;
         case M_store:
             s[fp+p[ip+1]] = s[sp-1];
             sp--;
             ip = ip+2;
             break;
         case M_add:
             sp--;
             s[sp-1] = s[sp-1] + s[sp];
             ip++;
             break;
         case M_sub:
             sp--;
             s[sp-1] = s[sp-1] - s[sp];
             ip++;
             break;
         case M_mul:
             sp--;
             s[sp-1] = s[sp-1] * s[sp];
             ip++;
             break;
         case M_div:
             sp--;
             s[sp-1] = s[sp-1] / s[sp];
             ip++;
             break;
         case M_if_cmpeq:
             sp = sp-2;
             ip = s[sp] == s[sp+1] ? p[ip+1]:ip+2;
             break;
         case M_if_cmpne:
             sp = sp-2;
             ip = s[sp] != s[sp+1] ? p[ip+1]:ip+2;
             break;
         case M_if_cmple:
             sp = sp-2;
             ip = s[sp] <= s[sp+1] ? p[ip+1]:ip+2;
             break;
         case M_if_cmpge:
             sp = sp-2;
             ip = s[sp] >= s[sp+1] ? p[ip+1]:ip+2;
             break;
         case M_goto:
             ip = p[ip+1];
             break;
         case M_jsr:
             s[sp] = ip+2;      // save return address
             s[sp+1] = fp;      // save fp
             fp = sp+2;         // set fp
             sp = fp+fs;        // set sp
             s[fp+1] = s[fp-3]; // copy argument
             ip = p[ip+1];      // goto start address
             break;
         case M_return:
             s[fp-3] = s[sp-1]; // copy return value
             sp = fp-2;         // reset sp
             fp = s[sp+1];      // reset fp
             ip = s[sp];        // goto return address
             break;
         default:
             throw new Error("illegal vm code "+p[ip]); } }
     return s[0]; }
}

