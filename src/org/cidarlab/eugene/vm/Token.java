package org.cidarlab.eugene.vm;

public interface Token {
    // token 
    final static int T_num = 1; // number
    final static int T_id  = 2; // identifier
    final static int T_eql = 3; // ==
    final static int T_neq = 4; // !=
    final static int T_grt = '>';
    final static int T_les = '<';
    final static int T_add = '+';
    final static int T_sub = '-';
    final static int T_mul = '*';
    final static int T_div = '/';
    final static int T_lbr = '(';
    final static int T_rbr = ')';
    final static int T_clb = '{';
    final static int T_crb = '}';
    final static int T_com = ',';
    final static int T_sem = ';';
    final static int T_ass = '=';
    final static int T_eof = '$';

    final static String kw[]  = {"if","else","while","return"};
    final static int kw0      = 10;
    final static int T_if     = 10;
    final static int T_else   = 11;
    final static int T_while  = 12;
    final static int T_return = 13;
}
