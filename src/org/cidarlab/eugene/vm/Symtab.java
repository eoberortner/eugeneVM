package org.cidarlab.eugene.vm;

//symbol table
public class Symtab {
    static String t[] = new String[100];    // table array 
    static int n = 0;                       // number of variables
        
    static int enter(String s) {
        int i;
        for (i=0; i < n && !s.equals(t[i]); i++) {}
        if (i == n) {
            t[i] = s;
            n++; }
        return i; 
    }
}
