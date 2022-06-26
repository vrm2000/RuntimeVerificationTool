import java_cup.runtime.*;
%%

/*  Declaraciones */   

%standalone
%cup 
%ignorecase


%%   
	AND							{ return new Symbol(sym.AND);}
	TRUE						{ return new Symbol(sym.TRUE);}
	FALSE						{ return new Symbol(sym.FALSE);}
	\&\&				 		{ return new Symbol(sym.AND) ;}
	OR							{ return new Symbol(sym.OR) ;}
	\|\|						{ return new Symbol(sym.OR) ;}
	\!	 						{ return new Symbol(sym.NOT) ;}
	NOT					 		{ return new Symbol(sym.NOT) ;}
	EVENTUALLY_					{ return new Symbol(sym.EVENTUALLY_) ;}
	E_							{ return new Symbol(sym.EVENTUALLY_) ;}
	ALWAYS_						{ return new Symbol(sym.ALWAYS_) ;}
	A_							{ return new Symbol(sym.ALWAYS_) ;}
	IMPLIES 					{ return new Symbol(sym.IMPLIES) ;}
	\(					 		{ return new Symbol(sym.AP) ;}
	\)					 		{ return new Symbol(sym.CP) ;}
	[\[\{]					 	{ return new Symbol(sym.AC) ;}
	[\]\}]					 	{ return new Symbol(sym.CC) ;}
	\,							{ return new Symbol(sym.COMA) ;}
	\>							{ return new Symbol(sym.MAYOR) ;}
	\<							{ return new Symbol(sym.MENOR) ;}
	\>\=						{ return new Symbol(sym.MAYEQ) ;}
	\<\=						{ return new Symbol(sym.MENEQ) ;}
	\=\=						{ return new Symbol(sym.IGUAL) ;}
	\!\=						{ return new Symbol(sym.DESIGUAL) ;}
	[-]?[0-9]+([.][0-9]+)?		{ return new Symbol(sym.NUM ,yytext() ); }
	
	[a-zA-Z][a-zA-Z0-9_]*		{ return new Symbol(sym.CHARTEXT,yytext());}
	\/\/[^\n\r]*		   		{}
	\/\*([^*]|\*+[^/])*\*+\/ 	{}
    [^ \n]                  	{}

