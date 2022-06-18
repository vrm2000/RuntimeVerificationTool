package Parsing;// DO NOT EDIT

// Generated by JFlex 1.8.2 http://jflex.de/
// source: parsing.flex

import java_cup.runtime.Symbol;

// See https://github.com/jflex-de/jflex/issues/222
@SuppressWarnings("FallThrough")
class Yylex implements java_cup.runtime.Scanner {

	/** This character denotes the end of file. */
	public static final int YYEOF = -1;

	/** Initial size of the lookahead buffer. */
	private static final int ZZ_BUFFERSIZE = 16384;

	// Lexical states.
	public static final int YYINITIAL = 0;

	/**
	 * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
	 * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l at the
	 * beginning of a line l is of the form l = 2*k, k a non negative integer
	 */
	private static final int ZZ_LEXSTATE[] = { 0, 0 };

	/**
	 * Top-level table for translating characters to character classes
	 */
	private static final int[] ZZ_CMAP_TOP = zzUnpackcmap_top();

	private static final String ZZ_CMAP_TOP_PACKED_0 = "\1\0\1\u0100\u10fe\u0200";

	private static int[] zzUnpackcmap_top() {
		int[] result = new int[4352];
		int offset = 0;
		offset = zzUnpackcmap_top(ZZ_CMAP_TOP_PACKED_0, offset, result);
		return result;
	}

	private static int zzUnpackcmap_top(String packed, int offset, int[] result) {
		int i = 0; /* index in packed string */
		int j = offset; /* index in unpacked array */
		int l = packed.length();
		while (i < l) {
			int count = packed.charAt(i++);
			int value = packed.charAt(i++);
			do
				result[j++] = value;
			while (--count > 0);
		}
		return j;
	}

	/**
	 * Second-level tables for translating characters to character classes
	 */
	private static final int[] ZZ_CMAP_BLOCKS = zzUnpackcmap_blocks();

	private static final String ZZ_CMAP_BLOCKS_PACKED_0 = "\12\0\1\1\25\0\1\1\1\2\4\0\1\3\1\0"
			+ "\1\4\1\5\2\0\1\6\1\7\2\0\1\10\11\11" + "\2\0\1\12\1\13\1\14\2\0\1\15\2\16\1\17"
			+ "\1\20\1\21\2\16\1\22\2\16\1\23\1\24\1\25" + "\1\26\1\27\1\16\1\30\1\31\1\32\1\33\1\34"
			+ "\1\35\1\16\1\36\1\16\1\37\1\0\1\40\1\0" + "\1\41\1\0\1\15\2\16\1\17\1\20\1\21\2\16"
			+ "\1\22\2\16\1\23\1\24\1\25\1\26\1\27\1\16" + "\1\30\1\31\1\32\1\33\1\34\1\35\1\16\1\36"
			+ "\1\16\1\37\1\42\1\40\262\0\2\43\115\0\1\44" + "\u0180\0";

	private static int[] zzUnpackcmap_blocks() {
		int[] result = new int[768];
		int offset = 0;
		offset = zzUnpackcmap_blocks(ZZ_CMAP_BLOCKS_PACKED_0, offset, result);
		return result;
	}

	private static int zzUnpackcmap_blocks(String packed, int offset, int[] result) {
		int i = 0; /* index in packed string */
		int j = offset; /* index in unpacked array */
		int l = packed.length();
		while (i < l) {
			int count = packed.charAt(i++);
			int value = packed.charAt(i++);
			do
				result[j++] = value;
			while (--count > 0);
		}
		return j;
	}

	/**
	 * Translates DFA states to action switch labels.
	 */
	private static final int[] ZZ_ACTION = zzUnpackAction();

	private static final String ZZ_ACTION_PACKED_0 = "\1\0\1\1\1\2\1\3\1\1\1\4\1\5\1\6"
			+ "\1\1\2\7\1\10\1\1\1\11\10\12\1\13\1\14" + "\2\1\1\15\1\16\1\17\1\20\1\21\2\12\1\22"
			+ "\1\12\1\23\3\12\1\24\1\12\1\24\1\0\1\12" + "\1\25\3\12\1\3\1\12\1\0\3\12\1\0\1\12"
			+ "\1\26\1\0\2\12\2\27\1\12\1\0\1\12\1\0" + "\2\12\1\0\1\22\1\12\2\30\3\12";

	private static int[] zzUnpackAction() {
		int[] result = new int[76];
		int offset = 0;
		offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
		return result;
	}

	private static int zzUnpackAction(String packed, int offset, int[] result) {
		int i = 0; /* index in packed string */
		int j = offset; /* index in unpacked array */
		int l = packed.length();
		while (i < l) {
			int count = packed.charAt(i++);
			int value = packed.charAt(i++);
			do
				result[j++] = value;
			while (--count > 0);
		}
		return j;
	}

	/**
	 * Translates a state to a row index in the transition table
	 */
	private static final int[] ZZ_ROWMAP = zzUnpackRowMap();

	private static final String ZZ_ROWMAP_PACKED_0 = "\0\0\0\45\0\45\0\112\0\157\0\45\0\45\0\45"
			+ "\0\224\0\45\0\271\0\336\0\u0103\0\u0128\0\u014d\0\u0172"
			+ "\0\u0197\0\u01bc\0\u01e1\0\u0206\0\u022b\0\u0250\0\45\0\45"
			+ "\0\u0275\0\u029a\0\45\0\45\0\45\0\45\0\45\0\u02bf"
			+ "\0\u02e4\0\u0172\0\u0309\0\u0172\0\u032e\0\u0353\0\u0378\0\u0172"
			+ "\0\u039d\0\45\0\u03c2\0\u03e7\0\u0172\0\u040c\0\u0431\0\u0456"
			+ "\0\u0172\0\u047b\0\u04a0\0\u04c5\0\u04ea\0\u050f\0\u0534\0\u0559"
			+ "\0\u0172\0\u057e\0\u05a3\0\u05c8\0\u0172\0\45\0\u05ed\0\u0612"
			+ "\0\u0637\0\u065c\0\u0681\0\u06a6\0\u06cb\0\45\0\u06f0\0\u0172" + "\0\45\0\u0715\0\u073a\0\u075f";

	private static int[] zzUnpackRowMap() {
		int[] result = new int[76];
		int offset = 0;
		offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
		return result;
	}

	private static int zzUnpackRowMap(String packed, int offset, int[] result) {
		int i = 0; /* index in packed string */
		int j = offset; /* index in unpacked array */
		int l = packed.length();
		while (i < l) {
			int high = packed.charAt(i++) << 16;
			result[j++] = high | packed.charAt(i++);
		}
		return j;
	}

	/**
	 * The transition table of the DFA
	 */
	private static final int[] ZZ_TRANS = zzUnpackTrans();

	private static final String ZZ_TRANS_PACKED_0 = "\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11"
			+ "\1\12\1\13\1\14\1\15\1\16\1\17\2\20\1\21" + "\1\22\1\23\2\20\1\24\1\25\3\20\1\26\4\20"
			+ "\1\27\1\30\1\2\1\31\1\32\1\2\60\0\1\33" + "\34\0\1\34\52\0\1\13\43\0\2\13\46\0\1\35"
			+ "\44\0\1\36\44\0\1\37\41\0\2\20\3\0\6\20" + "\1\40\1\20\1\41\11\20\2\0\1\42\13\0\2\20"
			+ "\3\0\22\20\2\0\1\20\13\0\2\20\3\0\17\20" + "\1\43\2\20\2\0\1\44\13\0\2\20\3\0\1\45"
			+ "\21\20\2\0\1\20\13\0\2\20\3\0\7\20\1\46" + "\12\20\2\0\1\20\13\0\2\20\3\0\11\20\1\47"
			+ "\10\20\2\0\1\20\13\0\2\20\3\0\13\20\1\50" + "\6\20\2\0\1\20\13\0\2\20\3\0\13\20\1\51"
			+ "\6\20\2\0\1\20\45\0\1\52\26\0\1\53\30\0" + "\2\20\3\0\20\20\1\54\1\20\2\0\1\20\13\0"
			+ "\2\20\3\0\2\20\1\55\17\20\2\0\1\20\13\0" + "\2\20\3\0\3\20\1\56\16\20\2\0\1\20\13\0"
			+ "\2\20\3\0\6\20\1\57\13\20\2\0\1\20\13\0" + "\2\20\3\0\12\20\1\60\7\20\2\0\1\20\13\0"
			+ "\2\20\3\0\15\20\1\61\4\20\2\0\1\20\13\0" + "\2\20\3\0\16\20\1\62\3\20\2\0\1\20\32\0"
			+ "\1\63\25\0\2\20\3\0\1\64\21\20\2\0\1\20" + "\13\0\2\20\3\0\10\20\1\65\11\20\2\0\1\20"
			+ "\13\0\2\20\3\0\14\20\1\66\5\20\2\0\1\20" + "\2\0\1\67\10\0\2\20\3\0\6\20\1\70\13\20"
			+ "\2\0\1\20\13\0\2\20\3\0\3\20\1\71\16\20" + "\2\0\1\20\26\0\1\72\31\0\2\20\3\0\21\20"
			+ "\1\73\2\0\1\20\13\0\2\20\3\0\15\20\1\74" + "\4\20\2\0\1\20\13\0\2\20\3\0\3\20\1\75"
			+ "\16\20\2\0\1\20\23\0\1\76\34\0\2\20\3\0" + "\5\20\1\77\14\20\2\0\1\20\1\0\1\100\23\0"
			+ "\1\100\20\0\1\100\11\0\2\20\3\0\14\20\1\101" + "\5\20\2\0\1\20\2\0\1\102\10\0\2\20\3\0"
			+ "\16\20\1\103\3\20\2\0\1\20\13\0\2\20\3\0" + "\3\20\1\104\16\20\2\0\1\20\23\0\1\105\34\0"
			+ "\2\20\3\0\22\20\2\0\1\42\44\0\1\106\13\0" + "\2\20\3\0\1\107\21\20\2\0\1\20\13\0\2\20"
			+ "\3\0\14\20\1\110\5\20\2\0\1\20\2\0\1\111" + "\31\0\1\111\12\0\1\111\10\0\2\20\3\0\6\20"
			+ "\1\112\13\20\2\0\1\20\13\0\2\20\3\0\6\20" + "\1\113\13\20\2\0\1\20\13\0\2\20\3\0\21\20"
			+ "\1\114\2\0\1\20\13\0\2\20\3\0\22\20\2\0" + "\1\44\3\0";

	private static int[] zzUnpackTrans() {
		int[] result = new int[1924];
		int offset = 0;
		offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
		return result;
	}

	private static int zzUnpackTrans(String packed, int offset, int[] result) {
		int i = 0; /* index in packed string */
		int j = offset; /* index in unpacked array */
		int l = packed.length();
		while (i < l) {
			int count = packed.charAt(i++);
			int value = packed.charAt(i++);
			value--;
			do
				result[j++] = value;
			while (--count > 0);
		}
		return j;
	}

	/** Error code for "Unknown internal scanner error". */
	private static final int ZZ_UNKNOWN_ERROR = 0;
	/** Error code for "could not match input". */
	private static final int ZZ_NO_MATCH = 1;
	/** Error code for "pushback value was too large". */
	private static final int ZZ_PUSHBACK_2BIG = 2;

	/**
	 * Error messages for {@link #ZZ_UNKNOWN_ERROR}, {@link #ZZ_NO_MATCH}, and
	 * {@link #ZZ_PUSHBACK_2BIG} respectively.
	 */
	private static final String ZZ_ERROR_MSG[] = { "Unknown internal scanner error", "Error: could not match input",
			"Error: pushback value was too large" };

	/**
	 * ZZ_ATTRIBUTE[aState] contains the attributes of state {@code aState}
	 */
	private static final int[] ZZ_ATTRIBUTE = zzUnpackAttribute();

	private static final String ZZ_ATTRIBUTE_PACKED_0 = "\1\0\2\11\2\1\3\11\1\1\1\11\14\1\2\11"
			+ "\2\1\5\11\12\1\1\11\1\0\7\1\1\0\3\1" + "\1\0\2\1\1\0\3\1\1\11\1\1\1\0\1\1"
			+ "\1\0\2\1\1\0\1\11\2\1\1\11\3\1";

	private static int[] zzUnpackAttribute() {
		int[] result = new int[76];
		int offset = 0;
		offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
		return result;
	}

	private static int zzUnpackAttribute(String packed, int offset, int[] result) {
		int i = 0; /* index in packed string */
		int j = offset; /* index in unpacked array */
		int l = packed.length();
		while (i < l) {
			int count = packed.charAt(i++);
			int value = packed.charAt(i++);
			do
				result[j++] = value;
			while (--count > 0);
		}
		return j;
	}

	/** Input device. */
	private java.io.Reader zzReader;

	/** Current state of the DFA. */
	private int zzState;

	/** Current lexical state. */
	private int zzLexicalState = YYINITIAL;

	/**
	 * This buffer contains the current text to be matched and is the source of the
	 * {@link #yytext()} string.
	 */
	private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

	/** Text position at the last accepting state. */
	private int zzMarkedPos;

	/** Current text position in the buffer. */
	private int zzCurrentPos;

	/** Marks the beginning of the {@link #yytext()} string in the buffer. */
	private int zzStartRead;

	/** Marks the last character in the buffer, that has been read from input. */
	private int zzEndRead;

	/**
	 * Whether the scanner is at the end of file.
	 * 
	 * @see #yyatEOF
	 */
	private boolean zzAtEOF;

	/**
	 * The number of occupied positions in {@link #zzBuffer} beyond
	 * {@link #zzEndRead}.
	 *
	 * <p>
	 * When a lead/high surrogate has been read from the input stream into the final
	 * {@link #zzBuffer} position, this will have a value of 1; otherwise, it will
	 * have a value of 0.
	 */
	private int zzFinalHighSurrogate = 0;

	/** Number of newlines encountered up to the start of the matched text. */
	@SuppressWarnings("unused")
	private int yyline;

	/**
	 * Number of characters from the last newline up to the start of the matched
	 * text.
	 */
	@SuppressWarnings("unused")
	private int yycolumn;

	/** Number of characters up to the start of the matched text. */
	@SuppressWarnings("unused")
	private long yychar;

	/** Whether the scanner is currently at the beginning of a line. */
	@SuppressWarnings("unused")
	private boolean zzAtBOL = true;

	/** Whether the user-EOF-code has already been executed. */
	private boolean zzEOFDone;

	/**
	 * Creates a new scanner
	 *
	 * @param in the java.io.Reader to read input from.
	 */
	Yylex(java.io.Reader in) {
		this.zzReader = in;
	}

	/**
	 * Translates raw input code points to DFA table row
	 */
	private static int zzCMap(int input) {
		int offset = input & 255;
		return offset == input ? ZZ_CMAP_BLOCKS[offset] : ZZ_CMAP_BLOCKS[ZZ_CMAP_TOP[input >> 8] | offset];
	}

	/**
	 * Refills the input buffer.
	 *
	 * @return {@code false} iff there was new input.
	 * @exception java.io.IOException if any I/O-Error occurs
	 */
	private boolean zzRefill() throws java.io.IOException {

		/* first: make room (if you can) */
		if (zzStartRead > 0) {
			zzEndRead += zzFinalHighSurrogate;
			zzFinalHighSurrogate = 0;
			System.arraycopy(zzBuffer, zzStartRead, zzBuffer, 0, zzEndRead - zzStartRead);

			/* translate stored positions */
			zzEndRead -= zzStartRead;
			zzCurrentPos -= zzStartRead;
			zzMarkedPos -= zzStartRead;
			zzStartRead = 0;
		}

		/* is the buffer big enough? */
		if (zzCurrentPos >= zzBuffer.length - zzFinalHighSurrogate) {
			/* if not: blow it up */
			char newBuffer[] = new char[zzBuffer.length * 2];
			System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
			zzBuffer = newBuffer;
			zzEndRead += zzFinalHighSurrogate;
			zzFinalHighSurrogate = 0;
		}

		/* fill the buffer with new input */
		int requested = zzBuffer.length - zzEndRead;
		int numRead = zzReader.read(zzBuffer, zzEndRead, requested);

		/* not supposed to occur according to specification of java.io.Reader */
		if (numRead == 0) {
			throw new java.io.IOException(
					"Reader returned 0 characters. See JFlex examples/zero-reader for a workaround.");
		}
		if (numRead > 0) {
			zzEndRead += numRead;
			if (Character.isHighSurrogate(zzBuffer[zzEndRead - 1])) {
				if (numRead == requested) { // We requested too few chars to encode a full Unicode character
					--zzEndRead;
					zzFinalHighSurrogate = 1;
				} else { // There is room in the buffer for at least one more char
					int c = zzReader.read(); // Expecting to read a paired low surrogate char
					if (c == -1) {
						return true;
					} else {
						zzBuffer[zzEndRead++] = (char) c;
					}
				}
			}
			/* potentially more input available */
			return false;
		}

		/* numRead < 0 ==> end of stream */
		return true;
	}

	/**
	 * Closes the input reader.
	 *
	 * @throws java.io.IOException if the reader could not be closed.
	 */
	public final void yyclose() throws java.io.IOException {
		zzAtEOF = true; // indicate end of file
		zzEndRead = zzStartRead; // invalidate buffer

		if (zzReader != null) {
			zzReader.close();
		}
	}

	/**
	 * Resets the scanner to read from a new input stream.
	 *
	 * <p>
	 * Does not close the old reader.
	 *
	 * <p>
	 * All internal variables are reset, the old input stream <b>cannot</b> be
	 * reused (internal buffer is discarded and lost). Lexical state is set to
	 * {@code ZZ_INITIAL}.
	 *
	 * <p>
	 * Internal scan buffer is resized down to its initial length, if it has grown.
	 *
	 * @param reader The new input stream.
	 */
	public final void yyreset(java.io.Reader reader) {
		zzReader = reader;
		zzEOFDone = false;
		yyResetPosition();
		zzLexicalState = YYINITIAL;
		if (zzBuffer.length > ZZ_BUFFERSIZE) {
			zzBuffer = new char[ZZ_BUFFERSIZE];
		}
	}

	/**
	 * Resets the input position.
	 */
	private final void yyResetPosition() {
		zzAtBOL = true;
		zzAtEOF = false;
		zzCurrentPos = 0;
		zzMarkedPos = 0;
		zzStartRead = 0;
		zzEndRead = 0;
		zzFinalHighSurrogate = 0;
		yyline = 0;
		yycolumn = 0;
		yychar = 0L;
	}

	/**
	 * Returns whether the scanner has reached the end of the reader it reads from.
	 *
	 * @return whether the scanner has reached EOF.
	 */
	public final boolean yyatEOF() {
		return zzAtEOF;
	}

	/**
	 * Returns the current lexical state.
	 *
	 * @return the current lexical state.
	 */
	public final int yystate() {
		return zzLexicalState;
	}

	/**
	 * Enters a new lexical state.
	 *
	 * @param newState the new lexical state
	 */
	public final void yybegin(int newState) {
		zzLexicalState = newState;
	}

	/**
	 * Returns the text matched by the current regular expression.
	 *
	 * @return the matched text.
	 */
	public final String yytext() {
		return new String(zzBuffer, zzStartRead, zzMarkedPos - zzStartRead);
	}

	/**
	 * Returns the character at the given position from the matched text.
	 *
	 * <p>
	 * It is equivalent to {@code yytext().charAt(pos)}, but faster.
	 *
	 * @param position the position of the character to fetch. A value from 0 to
	 *                 {@code yylength()-1}.
	 *
	 * @return the character at {@code position}.
	 */
	public final char yycharat(int position) {
		return zzBuffer[zzStartRead + position];
	}

	/**
	 * How many characters were matched.
	 *
	 * @return the length of the matched text region.
	 */
	public final int yylength() {
		return zzMarkedPos - zzStartRead;
	}

	/**
	 * Reports an error that occurred while scanning.
	 *
	 * <p>
	 * In a well-formed scanner (no or only correct usage of {@code yypushback(int)}
	 * and a match-all fallback rule) this method will only be called with things
	 * that "Can't Possibly Happen".
	 *
	 * <p>
	 * If this method is called, something is seriously wrong (e.g. a JFlex bug
	 * producing a faulty scanner etc.).
	 *
	 * <p>
	 * Usual syntax/scanner level error handling should be done in error fallback
	 * rules.
	 *
	 * @param errorCode the code of the error message to display.
	 */
	private static void zzScanError(int errorCode) {
		String message;
		try {
			message = ZZ_ERROR_MSG[errorCode];
		} catch (ArrayIndexOutOfBoundsException e) {
			message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
		}

		throw new Error(message);
	}

	/**
	 * Pushes the specified amount of characters back into the input stream.
	 *
	 * <p>
	 * They will be read again by then next call of the scanning method.
	 *
	 * @param number the number of characters to be read again. This number must not
	 *               be greater than {@link #yylength()}.
	 */
	public void yypushback(int number) {
		if (number > yylength())
			zzScanError(ZZ_PUSHBACK_2BIG);

		zzMarkedPos -= number;
	}

	/**
	 * Contains user EOF-code, which will be executed exactly once, when the end of
	 * file is reached
	 */
	private void zzDoEOF() throws java.io.IOException {
		if (!zzEOFDone) {
			zzEOFDone = true;

			yyclose();
		}
	}

	/**
	 * Resumes scanning until the next regular expression is matched, the end of
	 * input is encountered or an I/O-Error occurs.
	 *
	 * @return the next token.
	 * @exception java.io.IOException if any I/O-Error occurs.
	 */
	@Override
	public java_cup.runtime.Symbol next_token() throws java.io.IOException {
		int zzInput;
		int zzAction;

		// cached fields:
		int zzCurrentPosL;
		int zzMarkedPosL;
		int zzEndReadL = zzEndRead;
		char[] zzBufferL = zzBuffer;

		int[] zzTransL = ZZ_TRANS;
		int[] zzRowMapL = ZZ_ROWMAP;
		int[] zzAttrL = ZZ_ATTRIBUTE;

		while (true) {
			zzMarkedPosL = zzMarkedPos;

			zzAction = -1;

			zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

			zzState = ZZ_LEXSTATE[zzLexicalState];

			// set up zzAction for empty match case:
			int zzAttributes = zzAttrL[zzState];
			if ((zzAttributes & 1) == 1) {
				zzAction = zzState;
			}

			zzForAction: {
				while (true) {

					if (zzCurrentPosL < zzEndReadL) {
						zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
						zzCurrentPosL += Character.charCount(zzInput);
					} else if (zzAtEOF) {
						zzInput = YYEOF;
						break zzForAction;
					} else {
						// store back cached positions
						zzCurrentPos = zzCurrentPosL;
						zzMarkedPos = zzMarkedPosL;
						boolean eof = zzRefill();
						// get translated positions and possibly new buffer
						zzCurrentPosL = zzCurrentPos;
						zzMarkedPosL = zzMarkedPos;
						zzBufferL = zzBuffer;
						zzEndReadL = zzEndRead;
						if (eof) {
							zzInput = YYEOF;
							break zzForAction;
						} else {
							zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
							zzCurrentPosL += Character.charCount(zzInput);
						}
					}
					int zzNext = zzTransL[zzRowMapL[zzState] + zzCMap(zzInput)];
					if (zzNext == -1)
						break zzForAction;
					zzState = zzNext;

					zzAttributes = zzAttrL[zzState];
					if ((zzAttributes & 1) == 1) {
						zzAction = zzState;
						zzMarkedPosL = zzCurrentPosL;
						if ((zzAttributes & 8) == 8)
							break zzForAction;
					}

				}
			}

			// store back cached position
			zzMarkedPos = zzMarkedPosL;

			if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
				zzAtEOF = true;
				zzDoEOF();
				{
					return new java_cup.runtime.Symbol(sym.EOF);
				}
			} else {
				switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
				case 1: {
				}
				// fall through
				case 25:
					break;
				case 2: {
					System.out.print(yytext());
				}
				// fall through
				case 26:
					break;
				case 3: {
					return new Symbol(sym.NOT);
				}
				// fall through
				case 27:
					break;
				case 4: {
					return new Symbol(sym.AP);
				}
				// fall through
				case 28:
					break;
				case 5: {
					return new Symbol(sym.CP);
				}
				// fall through
				case 29:
					break;
				case 6: {
					return new Symbol(sym.COMA);
				}
				// fall through
				case 30:
					break;
				case 7: {
					return new Symbol(sym.NUM, yytext());
				}
				// fall through
				case 31:
					break;
				case 8: {
					return new Symbol(sym.MENOR);
				}
				// fall through
				case 32:
					break;
				case 9: {
					return new Symbol(sym.MAYOR);
				}
				// fall through
				case 33:
					break;
				case 10: {
					return new Symbol(sym.CHARTEXT, yytext());
				}
				// fall through
				case 34:
					break;
				case 11: {
					return new Symbol(sym.AC);
				}
				// fall through
				case 35:
					break;
				case 12: {
					return new Symbol(sym.CC);
				}
				// fall through
				case 36:
					break;
				case 13: {
					return new Symbol(sym.DESIGUAL);
				}
				// fall through
				case 37:
					break;
				case 14: {
					return new Symbol(sym.AND);
				}
				// fall through
				case 38:
					break;
				case 15: {
					return new Symbol(sym.MENEQ);
				}
				// fall through
				case 39:
					break;
				case 16: {
					return new Symbol(sym.IGUAL);
				}
				// fall through
				case 40:
					break;
				case 17: {
					return new Symbol(sym.MAYEQ);
				}
				// fall through
				case 41:
					break;
				case 18: {
					return new Symbol(sym.ALWAYS_);
				}
				// fall through
				case 42:
					break;
				case 19: {
					return new Symbol(sym.EVENTUALLY_);
				}
				// fall through
				case 43:
					break;
				case 20: {
					return new Symbol(sym.OR);
				}
				// fall through
				case 44:
					break;
				case 21: {
					return new Symbol(sym.AND);
				}
				// fall through
				case 45:
					break;
				case 22: {
					return new Symbol(sym.TRUE);
				}
				// fall through
				case 46:
					break;
				case 23: {
					return new Symbol(sym.FALSE);
				}
				// fall through
				case 47:
					break;
				case 24: {
					return new Symbol(sym.IMPLIES);
				}
				// fall through
				case 48:
					break;
				default:
					zzScanError(ZZ_NO_MATCH);
				}
			}
		}
	}

	/**
	 * Runs the scanner on input files.
	 *
	 * This is a standalone scanner, it will print any unmatched text to System.out
	 * unchanged.
	 *
	 * @param argv the command line, contains the filenames to run the scanner on.
	 */
	public static void main(String[] argv) {
		if (argv.length == 0) {
			System.out.println("Usage : java Yylex [ --encoding <name> ] <inputfile(s)>");
		} else {
			int firstFilePos = 0;
			String encodingName = "UTF-8";
			if (argv[0].equals("--encoding")) {
				firstFilePos = 2;
				encodingName = argv[1];
				try {
					// Side-effect: is encodingName valid?
					java.nio.charset.Charset.forName(encodingName);
				} catch (Exception e) {
					System.out.println("Invalid encoding '" + encodingName + "'");
					return;
				}
			}
			for (int i = firstFilePos; i < argv.length; i++) {
				Yylex scanner = null;
				try {
					java.io.FileInputStream stream = new java.io.FileInputStream(argv[i]);
					java.io.Reader reader = new java.io.InputStreamReader(stream, encodingName);
					scanner = new Yylex(reader);
					while (!scanner.zzAtEOF)
						scanner.next_token();
				} catch (java.io.FileNotFoundException e) {
					System.out.println("File not found : \"" + argv[i] + "\"");
				} catch (java.io.IOException e) {
					System.out.println("IO error scanning file \"" + argv[i] + "\"");
					System.out.println(e);
				} catch (Exception e) {
					System.out.println("Unexpected exception:");
					e.printStackTrace();
				}
			}
		}
	}

}
