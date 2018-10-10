// Madeleine Godwin   
// CSCI 4200
// Program Name: Lexical Analyzer 
// Dr. Salimi
package lexicalAnalyzerPackage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LexicalAnalyzer {
	/* Variables */
	public static String charClass;
	public static char[] lexeme = new char[100];
	public static char nextChar;
	public static int lexLen;
	public static String token;
	public static String nextToken;
	static int index = 0;
	static String line;
	/* character classes */
	public static final String LETTER = "LETTER";
	public static final String DIGIT = "DIGIT";
	public static final String UNKNOWN = "UNKOWN";
	/* Token codes */
	public static final String INT_LIT = "INT-LIT";
	public static final String IDENT = "IDENT";
	public static final String ASSIGN_OP = "ASSIGN_OP";
	public static final String ADD_OP = "ADD_OP";
	public static final String SUB_OP = "SUB_OP";
	public static final String MULT_OP = "MULT_OP";
	public static final String DIV_OP = "DIV_OP";
	public static final String LEFT_PAREN = "LEFT_PAREN";
	public static final String RIGHT_PAREN = "RIGHT_PAREN";
	public static final String EOF = "EOF";

	/* ***************************************************** */
	/* main driver */
	public static void main(String[] args) throws IOException {
		try {
			System.out.println("Madeleine Godwin, Student, CSCI4200-DA, Fall 2018, Lexical Analyzer");
			System.out.println("********************************************************************************");
			/* Open the input data file and process its contents */
			BufferedReader br = new BufferedReader(new FileReader("src/lexicalanalyzerPackage/lexInput.txt"));
			while ((line = br.readLine()) != null) {
				index = 0;
				System.out.println(("Input: ") + line);
				getChar();
				do {
					lex();
				} while (nextToken != EOF);
				System.out.println("********************************************************************************"); // 80
			} // end of while loop

		} // end of try loop

		catch (IOException e) {
			System.out.println("ERROR - cannot open file");
		}
		System.out.printf("Next token is: %-15s Next lexeme is ", nextToken);
		System.out.println(lexeme);
		System.out.println("Lexical analysis of the program is complete!");
	}// end of main
	/* *************************************************** */
	/* lookup - a function to lookup operators and parentheses and return the token*/
	static String lookup(char ch) {
		switch (ch) {
		case '(':
			addChar();
			nextToken = LEFT_PAREN;
			break;
		case ')':
			addChar();
			nextToken = RIGHT_PAREN;
			break;
		case '+':
			addChar();
			nextToken = ADD_OP;
			break;
		case '-':
			addChar();
			nextToken = SUB_OP;
			break;
		case '*':
			addChar();
			nextToken = MULT_OP;
			break;
		case '/':
			addChar();
			nextToken = DIV_OP;
			break;
		case '=':
			addChar();
			nextToken = ASSIGN_OP;
			break;
		default:
			addChar();
			nextToken = EOF;
			break;
		}
		return nextToken;
	}

	/* *************************************************** */
	// addChar - a function to add nextChar to lexeme
	static void addChar() {
		if (lexLen <= 98) {
			lexeme[lexLen++] = nextChar;
			lexeme[lexLen] = 0;
		} else
			System.out.println("Error - lexeme is too long \n");
	}

	/* *************************************************** */
	/*
	 * getChar - a function to get the next character of input and determine its
	 * character class
	 */
	static void getChar() throws IOException {
		if (checkIfExist()) {
			if (Character.isLetter(nextChar))
				charClass = LETTER;
			else if (Character.isDigit(nextChar))
				charClass = DIGIT;
			else
				charClass = UNKNOWN;
		} else
			charClass = EOF;
	}// end of getChar

	/* **************************************************** */
	private static boolean checkIfExist() throws java.lang.StringIndexOutOfBoundsException {
		try {
			nextChar = line.charAt(index);
			index++;
			return true;
		} catch (java.lang.StringIndexOutOfBoundsException e) {
			return false;
		}
	}

	/* *************************************************** */
	/*
	 * getNonBlank - a function to call getChar until it returns a non-whitespace
	 * character
	 */
	static void getNonBlank() throws IOException {
		while (Character.isWhitespace(nextChar))
			getChar();
	}

	/* **************************************************** */
	// lex - a simple lexical analyzer for arithmetic
	static int lex() throws IOException {
		lexLen = 0;
		lexeme = new char[100]; // Empties lexeme
		getNonBlank();
		switch (charClass) {
		/* Parse identifiers */
		case LETTER:
			addChar();
			getChar();
			while (charClass == LETTER || charClass == DIGIT) {
				addChar();
				getChar();
			}
			nextToken = IDENT;
			break;
		/* Parse integer literals */
		case DIGIT:
			addChar();
			getChar();
			while (charClass == DIGIT) {
				addChar();
				getChar();
			}
			nextToken = INT_LIT;
			break;
		/* Parentheses and operators */
		case UNKNOWN:
			lookup(nextChar);
			getChar();
			break;
		/* EOF */
		case EOF:
			nextToken = EOF;
			lexeme[0] = 'E';
			lexeme[1] = 'O';
			lexeme[2] = 'F';
			lexeme[3] = 0;
			break;
		} /* End of switch */
		if (nextToken != EOF) {
			System.out.printf("Next token is: %-15s Next lexeme is ", nextToken);
			System.out.println(lexeme);
		}
		return 0;
	} // End of function lex
}// end of class

 
