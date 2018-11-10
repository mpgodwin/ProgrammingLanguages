package lexAnalyzer;

//Madeleine Godwin    

//CSCI 4200
//Program Name: Parser
//Dr. Salimi

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Parse {
	/* Variables */
	static final int MAX_LEXEME_LENGTH = 400;
	static final int MAX_TOKEN_LENGTH = 400;
	static int charClass;
	static char[] lexeme = new char[MAX_LEXEME_LENGTH];
	static char nextChar;
	static int lexLen;
	static int token;
	static String thisLine;
	static int nextToken;
	static int index=0;
	static int read;
	static char endChar;
	static char[] newChar = new char[100];
	static String nextLine;
	static FileReader file;
	static BufferedReader reader;
	static String line;
	/* character classes */
	static final int LETTER = 0;
	static final int DIGIT = 1;
	static final int UNKNOWN = 99;

	/* Token codes */
	static String[] token_dict = new String[MAX_LEXEME_LENGTH];
	static final int INT_LIT = 10;
	static final int IDENT = 11;
	static final int ASSIGN_OP = 20;
	static final int ADD_OP = 21;
	static final int SUB_OP = 22;
	static final int MULT_OP = 23;
	static final int DIV_OP = 24;
	static final int LEFT_PAREN = 25;
	static final int RIGHT_PAREN = 26;
	static final int END_OF_FILE = 98;

	/* ***************************************************** */
	/* main driver */
	public static void main(String[] args) throws IOException {

		token_dict[INT_LIT] = "INT_LIT\t";
		token_dict[IDENT] = "IDENT\t";
		token_dict[ASSIGN_OP] = "ASSIGN_OP";
		token_dict[ADD_OP] = "ADD_OP\t";
		token_dict[SUB_OP] = "SUB_OP\t";
		token_dict[MULT_OP] = "MULT_OP\t";
		token_dict[DIV_OP] = "DIV_OP\t";
		token_dict[LEFT_PAREN] = "LEFT_PAREN";
		token_dict[RIGHT_PAREN] = "RIGHT_PAREN";
		token_dict[END_OF_FILE] = "END_OF_FILE";

		try {

			System.out.println("Madeleine Godwin, Student, CSCI4200-DA, Fall 2018, Lexical Parser");
			System.out.println("********************************************************************************");
			/* Open the input data file and process its contents */
			BufferedReader br = new BufferedReader(new FileReader("src\\lexAnalyzer\\input.txt"));

			while ((line = br.readLine()) != null) {
				index = 0;
				read=0;
				getChar();

				// System.out.println(("Input: ") + line);

				System.out.println("Parsing the statement: " + line + "\n");
				do {
					lex();
					//index++;
				} while (nextToken != END_OF_FILE);
				System.out.println("********************************************************************************"); // 80
			} // end of while loop

		} // end of try loop

		catch (IOException e) {
			System.out.println("ERROR - cannot open file");
		} // end of catch
			// System.out.printf("Next token is: %-15s Next lexeme is ", nextToken);
			// System.out.println(lexeme);
		System.out.println("Lexical analysis of the program is complete!");
	}// end of main
	/* *************************************************** */
	/*
	 * lookup - a function to lookup operators and parentheses and return the token
	 */

	static int lookup(char ch) {
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
			System.out.println("Enter <assign>");
			nextToken = ASSIGN_OP;
			break;
		default:
			addChar();
			nextToken = END_OF_FILE;
			break;
		}
		return nextToken;
	}// end of look up

	/* *************************************************** */
	// addChar - a function to add nextChar to lexeme
	static void addChar() {
		if (lexLen <= 98) {
			lexeme[lexLen++] = nextChar;
			lexeme[lexLen] = 0;
		} else
			System.out.println("Error - lexeme is too long \n");
	}// end of addchar

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
			charClass = END_OF_FILE;
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
	}// end of checkifexists

	/* *************************************************** */
	/*
	 * getNonBlank - a function to call getChar until it returns a non-whitespace
	 * character
	 */
	static void getNonBlank() throws IOException {
		while (Character.isWhitespace(nextChar))
			getChar();
	} // end of getnonblank

	/* **************************************************** */
	// lex - a simple lexical analyzer for arithmetic
	static int lex() throws IOException{

        lexLen = 0;
        getNonBlank();
        switch (charClass){
      
       /* Determines parse identifiers */
            case LETTER:
                addChar();
                getChar();
                while(charClass == LETTER || charClass == DIGIT){
                    addChar();
                    getChar();
                }
                nextToken = IDENT;
                break;
          
       /* Determines parse integer literals */
            case DIGIT:
                addChar();
                getChar();
                while(charClass == DIGIT){
                    addChar();
                    getChar();
                }
                nextToken = INT_LIT;
                break;
          
       /* Determines parentheses and operators */
            case UNKNOWN:
                lookup(nextChar);
                getChar();
                break;
          
       /* Determines the end of the file*/
            case END_OF_FILE:
                nextToken = END_OF_FILE;;
                lexeme[0] = 'E';
                lexeme[1] = 'O';
                lexeme[2] = 'F';
                lexeme[3] = 0;
                lexLen = 4;
                break;
        }
      
       /* The following is the output calls for parser,
       * which in turn, defines when to insert either
       * <assign> <expr> <term> and/or <factor> */

        String s = new String(lexeme);
        s = s.substring(0,lexLen);
        char lexLast = s.charAt(s.length()-1);
        if(nextToken != 98)
            System.out.printf("Next token is: %s\t\t Next lexeme is %s\n", token_dict[nextToken], s);
        if(token_dict[nextToken].equals("LEFT_PAREN"))
        {
            System.out.println("Enter <expr>");
            System.out.println("Enter <term>");
            System.out.println("Enter <factor>");
        }

        if(nextToken == 11 && read !=0 && lexLast != endChar )
        {
            System.out.println("Enter <expr>");
            System.out.println("Enter <term>");
            System.out.println("Enter <factor>");
        }

        if(nextToken == 21 && lexLast != endChar)
        {
            System.out.println("Exit <factor>");
            System.out.println("Exit <term>");
        }

        if(nextToken == 10 && lexLast != endChar)
        {
            System.out.println("Enter <term>");
            System.out.println("Enter <factor>");
        }

        if(nextToken == 26 && lexLast != endChar)
        {
            System.out.println("Exit <factor>");
            System.out.println("Exit <term>");
            System.out.println("Exit <expr>");
        }

        if(nextToken == 24 && lexLast != endChar)
        {
            System.out.println("Exit <factor>");
        }
        if(nextToken == 98)
        {
            System.out.println("Enter <factor>");
            System.out.println("Exit <factor>");
            System.out.println("Exit <term>");
            System.out.println("Exit <expr>");
            System.out.println("Exit <assign>");
        }
        return nextToken;
    }
  

	   /* The following returns true if the char is a letter */

	    static boolean isalpha(char c){

	        int ascii = (int) c;
	        if((ascii > 64 && ascii < 91) || (ascii > 96 && ascii < 123)){
	            return true;
	        }else {return false;}
	    }
	  
	   /* The following returns true if the char is a digit */

	    static boolean isdigit(char c){

	        int ascii = (int) c;
	        if(ascii > 47 && ascii < 58){
	            return true;
	        }else {return false;}
	    }

	   /* The following returns true if the char is a space*/

	    static boolean isspace(char c){

	        int ascii = (int) c;
	        if(ascii == 32){
	            return true;
	        }else {return false;}
	    }
}// end of class
