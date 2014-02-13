package org.cidarlab.eugene.vm;

public class Parser implements Token, Code 
{
    private static int token; 

    static void program() throws Error {
        next();
        Gen.start();
        function(); }

    private static void function() throws Error {
        match(T_id);
        match(T_lbr);
        match(T_id);
        match(T_rbr);
        block();
        Gen.instr(M_nop); }

    private static void block() throws Error {
        match(T_clb);
        statements();
        match(T_crb); }

    private static void statements() throws Error {
        if (token != T_crb) { 
            statement();
            statements(); } }
        
    private static void statement() throws Error {
        int p1,p2;
        switch(token) {
        case T_id:
            int adr = Lexer.id_val;
            next();
            match(T_ass);
            expression();
            Gen.instr(M_store,adr);
            match(T_sem);
            break;
        case T_if:
            next();
            condition();
            p1 = Gen.pc-1; 
            statement();
            Gen.instr(M_goto,0);
            p2 = Gen.pc-1;
            match(T_else);
            Gen.setjump(p1);
            statement();
            Gen.setjump(p2);
            break;
        case T_while:
            next();
            p1 = Gen.pc;
            condition();
            p2 = Gen.pc-1;
            statement();
            Gen.instr(M_goto,p1);
            Gen.setjump(p2);
            break;
        case T_return:
            next();
            expression();
            Gen.instr(M_return);
            match(T_sem);
            break;
        case T_clb:
            block();
            break;
        case T_sem:
            next();
            break;
        default:
            throw new Error("statement "+token); } }
        
    private static void condition() throws Error {
        match(T_lbr);
        expression();
        int rop = token;
        next();
        expression();
        match(T_rbr);
        switch(rop) {
        case T_eql:
            Gen.instr(M_if_cmpne,0);
            break;
        case T_neq:
            Gen.instr(M_if_cmpeq,0);
            break;
        case T_grt:
            Gen.instr(M_if_cmple,0);
            break;
        case T_les:
            Gen.instr(M_if_cmpge,0);
            break;
        default:
            throw new Error("condition "+token); } }
        
    private static void expression() throws Error {
        term();
        while (token == T_add || token == T_sub) {
            switch (token) {
            case T_add:
                next();
                term();
                Gen.instr(M_add);
                break;
            case T_sub:
                next();
                term();
                Gen.instr(M_sub);
                break; } } }
        
    private static void term() throws Error {
        factor();
        while (token == T_mul || token == T_div) {
            switch (token) {
            case T_mul:
                next();
                term();
                Gen.instr(M_mul);
                break;
            case T_div:
                next();
                term();
                Gen.instr(M_div);
                break; } } }
        
    private static void factor() throws Error {
        switch(token) {
        case T_num:
            Gen.instr(M_push,Lexer.num_val);
            next();
            break;
        case T_id:
            int id = Lexer.id_val;
            next();
            if (token != T_lbr)
                Gen.instr(M_load,id);
            else {
                next();
                expression();
                match(T_rbr);
                Gen.instr(M_jsr,Gen.start_adr); }
            break;
        case T_lbr:
            next();
            expression();
            match(T_rbr);
            break;
        default:
            throw new Error("expression "+token); } }

    private static void next() throws Error {
        token = Lexer.scan(); }
        
    private static void match(int x) throws Error {
        if (token == x)
            next();
        else
            throw new Error("syntax "+token); }
}
