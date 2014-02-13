package org.cidarlab.eugene.vm;

public class Gen 
	implements Code 
{
    static int code[];    // target code
    static int pc;        // program counter
    static int start_adr; // start address
        
    static void init(int code_max) {
        code = new int[code_max];
        pc = 0; }
    
    static void start() {
        instr(M_jsr,3);
        instr(M_stop);
        start_adr = pc; }       
    
    static void instr(int operator) {
        code[pc] = operator;
        pc = pc+1; }

    static void instr(int operator,int operand) {
        code[pc] = operator;
        code[pc+1] = operand;
        pc = pc+2; }
        
    static void setjump(int adr) {
        code[adr] = pc; }
    
    static void show() {
        System.out.print("VMCode: ");
        for (int i=0;i < pc;i++) 
            System.out.print(code[i]+" ");
        System.out.println(); }
}
