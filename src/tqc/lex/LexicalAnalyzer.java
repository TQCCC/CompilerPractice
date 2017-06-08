package tqc.lex;

import java.io.*;
import java.util.*;

import tqc.removeanno.RemoveAnnotation;

/**
 * An easy lexical analyzer by TQC.
 * 
 * @author TQC
 * */

public class LexicalAnalyzer {


	public static String DE_ASSIGN = "assign";
	public static String DE_PLUS = "plus";
	public static String DE_DPLUS = "doubleplus";
	public static String DE_MINUS = "minus";
	public static String DE_DMINUS = "doubleminus";
	public static String DE_ARROW = "arrow";
	public static String DE_STAR = "star";
	public static String DE_POWER = "power";
	public static String DE_DIVIDE = "divide";
	public static String DE_SEMICOLON = "semicolon";
	public static String DE_LPAR = "lpar";
	public static String DE_RPAR = "rpar";
	public static String DE_LBRACE = "lbrace";
	public static String DE_RBRACE = "rbrace";
	public static String DE_QUOTATION = "quotation";
	public static String DE_COMMA = "comma";
	
	public static String DE_OR = "or";
	public static String DE_BOR = "bor";
	public static String DE_AND = "and";
	public static String DE_BAND = "band";
	
	public static String DE_EQUAL = "equal";
	public static String DE_LESS = "less";
	public static String DE_MORE = "more";
	
	public static String DE_SHIFTRIGHT = "shiftright";
	public static String DE_SHIFTLEFT = "shiftleft";
	
	
	public static String ID = "id";
	public static String CONST = "const";
	public static String STRING = "string";

	public static String NOTKEYWORD = "notkeyword";
	/*
	 * Save all the type of the key word.
	 */
	public static Vector<String> keyWordVector = new Vector<String>();

	public static void initKeyWordVector() {
		if (keyWordVector.size() == 0) {
			keyWordVector.add("int");
			keyWordVector.add("char");
			keyWordVector.add("void");
			keyWordVector.add("const");
			keyWordVector.add("static");
			
			keyWordVector.add("if");
			keyWordVector.add("else");
			keyWordVector.add("for");
			keyWordVector.add("do");
			keyWordVector.add("while");
			keyWordVector.add("continue");
			
			keyWordVector.add("switch");
			keyWordVector.add("case");
			keyWordVector.add("default");
			keyWordVector.add("break");
			
			keyWordVector.add("return");
			
			keyWordVector.add("struct");
		
		}
	}

	public static String Reserve(String s) {
		if (keyWordVector.contains(s)) {
			return s.toString();
		} else {
			return NOTKEYWORD;
		}
	}

	public static Vector<LexItem> getKeyWords(String fileName) throws IOException {
		initKeyWordVector();

		Vector<LexItem> lv = new Vector<LexItem>();
		RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
		int ch = 32;
		StringBuilder strToken = new StringBuilder();

		
		while (ch != -1) {
			
			ch = raf.read();
			while (ch == 32 || ch == 13 || ch == 10 || ch == 9) {
				ch = raf.read();
			}
			if (Character.isLetter(ch)) {
				while (Character.isLetterOrDigit(ch)) {
					strToken.append((char) ch);
					ch = raf.read();
				}
				raf.seek(raf.getFilePointer()-1);	/* seek back 1 byte */

				String code = Reserve(strToken.toString());
				if (code == NOTKEYWORD) {
					lv.add(new LexItem(ID, strToken.toString()));	// ID
				} else {
					lv.add(new LexItem(code, "-")); // key word
				}
			} else if (Character.isDigit(ch)) {
				while (Character.isDigit(ch)) {
					strToken.append((char) ch);
					ch = raf.read();
				}
				raf.seek(raf.getFilePointer()-1); 	/* seek back 1 byte */

				lv.add(new LexItem(CONST, Integer.parseInt(strToken.toString())));
				
			}else if (ch == '=') {
				
				ch = raf.read();
				if (ch == '=') {
					lv.add(new LexItem(DE_EQUAL, "=="));
				}else {
					raf.seek(raf.getFilePointer()-1);ch = 32;	/* Retract */
					lv.add(new LexItem(DE_ASSIGN, "="));
				}
				
			}else if (ch == '+') {
				ch = raf.read();
				if (ch == '+') {
					lv.add(new LexItem(DE_DPLUS, "++"));
				}else{
					raf.seek(raf.getFilePointer()-1);ch = 32;	/* Retract */
					lv.add(new LexItem(DE_PLUS, "+"));
				}
				
			}else if (ch == '-') {
				ch = raf.read();
				if (ch == '>') {
					lv.add(new LexItem(DE_ARROW, "->"));
				}else if (ch == '-') {
					lv.add(new LexItem(DE_DMINUS, "--"));
				}else {
					raf.seek(raf.getFilePointer()-1);ch = 32;	/* Retract */
					lv.add(new LexItem(DE_MINUS, "-"));
				}
			}else if (ch == '*') {
				ch = raf.read();
				if (ch == '*') {
					lv.add(new LexItem(DE_POWER, "**"));
				} else {
					raf.seek(raf.getFilePointer()-1);ch = 32;	/* Retract */
					lv.add(new LexItem(DE_STAR, "*"));
				}
				
			}else if (ch == '/') {
				lv.add(new LexItem(DE_DIVIDE, "/"));
			}else if (ch == ';') {
				lv.add(new LexItem(DE_SEMICOLON, ";"));
			}else if (ch == '(') {
				lv.add(new LexItem(DE_LPAR, "("));
			}else if (ch == ')') {
				lv.add(new LexItem(DE_RPAR, ")"));
			}else if (ch == '{') {
				lv.add(new LexItem(DE_LBRACE,"{"));
			}else if (ch == '}') {
				lv.add(new LexItem(DE_RBRACE, "}"));
			}else if (ch == ',') {
				lv.add(new LexItem(DE_COMMA, ","));
			}else if (ch == '|') {
				ch = raf.read();
				if (ch == '|') {
					lv.add(new LexItem(DE_OR, "||"));
				}else{
					raf.seek(raf.getFilePointer()-1);ch = 32;	/* Retract */
					lv.add(new LexItem(DE_BOR, "|"));
				}
				
			}else if (ch == '&') {
				ch = raf.read();
				if (ch == '&') {
					lv.add(new LexItem(DE_AND, "&&"));
				}else{
					raf.seek(raf.getFilePointer()-1);ch = 32;	/* Retract */
					lv.add(new LexItem(DE_BAND, "&"));
				}
			}else if (ch == '>') {
				ch = raf.read();
				if (ch == '>') {
					lv.add(new LexItem(DE_SHIFTRIGHT, ">>"));
				}else {
					raf.seek(raf.getFilePointer()-1);ch = 32;	/* Retract */
					lv.add(new LexItem(DE_MORE, ">"));
				}
			}else if (ch == '<') {
				ch = raf.read();
				if (ch == '<') {
					lv.add(new LexItem(DE_SHIFTLEFT, "<<"));
				}else {
					raf.seek(raf.getFilePointer()-1);ch = 32;	/* Retract */
					lv.add(new LexItem(DE_LESS, "<"));
				}
				
			}else if (ch == '"'){	//Const string
				ch = raf.read();
				while(ch != '"'){
					strToken.append((char) ch);
					ch = raf.read();
				}
				lv.add(new LexItem(STRING, strToken.toString()));
			}
			
			/* More delimiters or operators. */
			
			else {
				// error
				if (ch!=-1) {
					System.out.println("Unknown token: " + (char)ch);	
				}
			}

			strToken.delete(0, strToken.length());
		}

		raf.close();
		return lv;
	}

	public static void main(String[] args) throws IOException {
		System.out.println("p2 App starts...");
		RemoveAnnotation.removeAnnotations("./main.c", "./target.c");
		
		Vector<LexItem> v = getKeyWords("./target.c");
		
		/* Output */
		for(LexItem item : v){
			System.out.println(item.getKeyWord() + ":" + item.getValue());
		}
		
	}

}
