package org.cidarlab.eugene.vm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;

public class Lexer implements Token 
{
    static int num_val;        // attribute of number
    static int id_val;         // attribute of id (index of symbol table)

    private static Reader source;
    private static StreamTokenizer st;
    private static int tok;    // tokenizer token 

    static void init(String script) 
    		throws Error {
    	
        try {
            source = new BufferedReader(new StringReader(script)); 
        } catch (Exception e) {
            throw new Error(e.getMessage()); 
        }
        
        st = new StreamTokenizer(source);
        st.ordinaryChar('/');
        st.ordinaryChar('-'); 
    } 
    
    static int scan() throws Error {
        try {
            tok = st.nextToken();
            switch(tok) {
            case StreamTokenizer.TT_EOF:
                return T_eof;
            case StreamTokenizer.TT_NUMBER:
                num_val = (int)st.nval;
                return T_num;
            case StreamTokenizer.TT_WORD:
                int i = look_kw(st.sval);
                if (i >= 0)
                    return kw0 + i;
                else {
                    id_val = Symtab.enter(st.sval);
                    return T_id; 
                }
            default:
                char c = (char)tok;
                switch(c) {
                case '=':
                    if ((char)st.nextToken() == '=') 
                        return T_eql;
                    else 
                        st.pushBack();
                    break; 
                case '!':
                    if ((char)st.nextToken() == '=') 
                        return T_neq; 
                    else 
                        st.pushBack();
                    break; } } } 
        catch (IOException e) {
            throw new Error ("IO"+" "+e.getMessage()); }
        return tok; }
        
    private static int look_kw(String s) {
        int i;
        for (i=0; i < kw.length && !s.equals(kw[i]); i++) {}
        if (i < kw.length) 
            return i;
        else
            return -1; }
}

