package org.cidarlab.eugene.vm;

public interface Code {
    // target code (VM)
    final static int M_nop      =  0;
    final static int M_push     =  1;
    final static int M_load     =  2;
    final static int M_store    =  3;
    final static int M_add      =  4;
    final static int M_sub      =  5;
    final static int M_mul      =  6;
    final static int M_div      =  7;
    final static int M_if_cmpeq =  8;
    final static int M_if_cmpne =  9;
    final static int M_if_cmple = 10;
    final static int M_if_cmpge = 11;
    final static int M_goto     = 12;
    final static int M_jsr      = 13;
    final static int M_return   = 14;
    final static int M_stop     = 15;
}
