package compiler;

import compiler.AST.*;
import compiler.lib.*;
import compiler.exc.*;
import svm.ExecuteVM;

import java.util.ArrayList;

import static compiler.lib.FOOLlib.*;

public class CodeGenerationASTVisitor extends BaseASTVisitor<String, VoidException> {

  CodeGenerationASTVisitor() {}
  CodeGenerationASTVisitor(boolean debug) {super(false,debug);} //enables print for debugging

//tutti i  metodi restituiscono una stringa eccetoto per le funzioni e per i metodi ce restituisce putcode
	//putcode è una variabile che contiene il corpo della funzione e del metodo.

	@Override
	public String visitNode(ProgLetInNode n) {
		if (print) printNode(n);
		String declCode = null;
		for (Node dec : n.declist) declCode=nlJoin(declCode,visit(dec));
		return nlJoin(
			"push 0",	
			declCode, // generate code for declarations (allocation)			
			visit(n.exp),
			"halt",
			getCode()
		);
	}

	@Override
	public String visitNode(ProgNode n) {
		if (print) printNode(n);
		return nlJoin(
			visit(n.exp),
			"halt"
		);
	}

	@Override
	public String visitNode(FunNode n) {
		if (print) printNode(n,n.id);
		String declCode = null, popDecl = null, popParl = null;
		for (Node dec : n.declist) {
			declCode = nlJoin(declCode,visit(dec));
			popDecl = nlJoin(popDecl,"pop");
		}
		for (int i=0;i<n.parlist.size();i++) popParl = nlJoin(popParl,"pop");
		String funl = freshFunLabel();
		putCode(
			nlJoin(
				funl+":",
				"cfp", // set $fp to $sp value
				"lra", // load $ra value
				declCode, // generate code for local declarations (they use the new $fp!!!)
				visit(n.exp), // generate code for function body expression
				"stm", // set $tm to popped value (function result)
				popDecl, // remove local declarations from stack
				"sra", // set $ra to popped value
				"pop", // remove Access Link from stack
				popParl, // remove parameters from stack
				"sfp", // set $fp to popped value (Control Link)
				"ltm", // load $tm value (function result)
				"lra", // load $ra value
				"js"  // jump to to popped address
			)
		);
		return "push "+funl;		
	}

	@Override
	public String visitNode(MethodNode n) {//per ogni metodo restituisce il codice macchina
		if (print) printNode(n,n.id);
		String declCode = null, popDecl = null, popParl = null;
		for (Node dec : n.declist) {
			declCode = nlJoin(declCode,visit(dec));//crea codice delle dichiarazioni e le salva in una stringa
			popDecl = nlJoin(popDecl,"pop");
		}
		for (int i=0;i<n.parlist.size();i++) popParl = nlJoin(popParl,"pop");
		String label = freshFunLabel();//label mantiene l'indirizzo
		n.label = label;
		putCode(
				nlJoin(
						label+":",
						"cfp", // set $fp to $sp value
						"lra", // load $ra value
						declCode, // generate code for local declarations (they use the new $fp!!!)
						visit(n.exp), // generate code for function body expression
						"stm", // set $tm to popped value (function result)
						popDecl, // remove local declarations from stack
						"sra", // set $ra to popped value
						"pop", // remove Access Link from stack
						popParl, // remove parameters from stack
						"sfp", // set $fp to popped value (Control Link)
						"ltm", // load $tm value (function result)
						"lra", // load $ra value
						"js"  // jump to to popped address
				)
		);
		return null;
	}

	@Override
	public String visitNode(ClassNode n){
		ArrayList<String> dispatchTable = new ArrayList<>();
		for(MethodNode method: n.methods){
			visit(method);
			String label = method.label;
			int offset = method.offset;
			dispatchTable.add(offset,label);
		}
		String labelsCode = null;

		for(String label : dispatchTable){
			labelsCode = nlJoin(labelsCode, nlJoin(
					"push " +label,//aggiunge l'etichetta allo stack
					"lhp",//aggiunge l'indirizzo hp a cui metterla
					"sw",//inserisce label all'indirizzo hp
					"lhp",//inserisce hp sullo stack
					"push 1", //inserisce 1 sullo stack
					"add",//incrementa hp e lo rimette sullo stack
					"shp" // si alva nel registro il valore incrementato di hp
			));
		}//alla fine si lo stack è vuoto e avrà solamente al dipspatch pointer .....SU DIARIO.....
		return nlJoin(
				"lhp",
				labelsCode
		);
	}

	@Override
	public String visitNode(VarNode n) {
		if (print) printNode(n,n.id);
		return visit(n.exp);
	}

	@Override
	public String visitNode(PrintNode n) {
		if (print) printNode(n);
		return nlJoin(
			visit(n.exp),
			"print"
		);
	}

	@Override
	public String visitNode(IfNode n) {
		if (print) printNode(n);
	 	String l1 = freshLabel();
	 	String l2 = freshLabel();		
		return nlJoin(
			visit(n.cond),
			"push 1",
			"beq "+l1,
			visit(n.el),
			"b "+l2,
			l1+":",
			visit(n.th),
			l2+":"
		);
	}

	@Override
	public String visitNode(EqualNode n) {
		if (print) printNode(n);
	 	String l1 = freshLabel();
	 	String l2 = freshLabel();
		return nlJoin(
			visit(n.left),
			visit(n.right),
			"beq "+l1,
			"push 0",
			"b "+l2,
			l1+":",
			"push 1",
			l2+":"
		);
	}

	@Override
	public String visitNode(LessEqualNode n) {
		if (print) printNode(n);
		String l1 = freshLabel();
		String l2 = freshLabel();
		return nlJoin(
				visit(n.left),
				visit(n.right),
				"bleq "+l1,
				"push 0",
				"b "+l2,
				l1+":",
				"push 1",
				l2+":"
		);
	}

	@Override
	public String visitNode(GreaterEqualNode n) {
		if (print) printNode(n);
		String l1 = freshLabel(); //push1
		String l2 = freshLabel(); //push0
		return nlJoin(
				visit(n.right),
				visit(n.left),
				"bleq "+l1,
				"push 0",
				"b "+l2,
				l1+":",
				"push 1",
				l2+":"
		);
	}

	@Override
	public String visitNode(AndNode n) {
		if (print) printNode(n);
		String l1 = freshLabel();//l1 and errato (pusha 0)
		String l2 = freshLabel();// l2 non fa nulla
		return nlJoin(
				visit(n.left),
				"push 0",
				"beq "+l1,
				visit(n.right),
				"push 0",
				"beq "+l1,
				"push 1",
				"b "+l2,
				l1 + ":",
				"push 0",
				l2+":"

		);
	}

	@Override
	public String visitNode(OrNode n) {
		if (print) printNode(n);
		String l1 = freshLabel();//l1 or corretto (pusha 1)
		String l2 = freshLabel();// l2 non fa nulla
		return nlJoin(
				visit(n.left),
				"push 1",
				"beq "+l1,
				visit(n.right),
				"push 1",
				"beq "+l1,
				"push 0",
				"b "+l2,
				l1 + ":",
				"push 1",
				l2+":"

		);
	}

	@Override
	public String visitNode(NotNode n) {
		if (print) printNode(n);
		String l1 = freshLabel();//l1 inverte true con false
		String l2 = freshLabel();// l2 non fa nulla
		return nlJoin(
				visit(n.val),
				"push 1",
				"beq "+l1,
				"push 1",
				"b "+l2,
				l1 + ":",
				"push 0",
				l2+":"

		);
	}

	@Override
	public String visitNode(EmptyNode n){
	  if (print) printNode(n);
	  return nlJoin(
			  "push -1"
	  );
	}

	@Override
	public String visitNode(TimesNode n) {
		if (print) printNode(n);
		return nlJoin(
			visit(n.left),
			visit(n.right),
			"mult"
		);	
	}

	@Override
	public String visitNode(DivNode n) {
		if (print) printNode(n);
		return nlJoin(
				visit(n.left),
				visit(n.right),
				"div"
		);
	}

	@Override
	public String visitNode(PlusNode n) {
		if (print) printNode(n);
		return nlJoin(
			visit(n.left),
			visit(n.right),
			"add"				
		);
	}

	@Override
	public String visitNode(MinusNode n) {
		if (print) printNode(n);
		return nlJoin(
				visit(n.left),
				visit(n.right),
				"sub"
		);
	}
	@Override
	public String visitNode(CallNode n) {
		if (print) printNode(n,n.id);
		String argCode = null, getAR = null;
		for (int i=n.arglist.size()-1;i>=0;i--) argCode=nlJoin(argCode,visit(n.arglist.get(i)));
		for (int i = 0;i<n.nl-n.entry.nl;i++) getAR=nlJoin(getAR,"lw");
		if(n.entry.type instanceof MethodTypeNode){
			return nlJoin(
					"lfp", // load Control Link (pointer to frame of function "id" caller)
					argCode, // generate code for argument expressions in reversed order
					"lfp", getAR, // retrieve address of frame containing "id" declaration
					// by following the static chain (of Access Links)
					"stm", // set $tm to popped value (with the aim of duplicating top of stack)
					"ltm", // load Access Link (pointer to frame of function "id" declaration)
					"ltm", // duplicate top of stack
					"lw", //Push on the stack the value of the object pointer (address to the dispatch table)
					"push "+n.entry.offset, "add", // compute address of "id" declaration
					"lw", // load address of "id" function
					"js"  // jump to popped address (saving address of subsequent instruction in $ra)
					//TODO DA MODIFICARE

			);
		}else{
			return nlJoin(
					"lfp", // load Control Link (pointer to frame of function "id" caller)
					argCode, // generate code for argument expressions in reversed order
					"lfp", getAR, // retrieve address of frame containing "id" declaration
					// by following the static chain (of Access Links)
					"stm", // set $tm to popped value (with the aim of duplicating top of stack)
					"ltm", // load Access Link (pointer to frame of function "id" declaration)
					"ltm", // duplicate top of stack
					"push "+n.entry.offset, "add", // compute address of "id" declaration
					"lw", // load address of "id" function
					"js"  // jump to popped address (saving address of subsequent instruction in $ra)
			);
		}

	}

	@Override
	public  String visitNode(NewNode n){
	  	if (print) printNode(n, n.id_class);
	  	String argCode = null;//argcode: codice generato dalla visita di ciascun argomento
	  	for (int i=0;i<n.fields.size();i++) argCode=nlJoin(argCode,visit(n.fields.get(i)));
	  	String storeInHeap = null;
		for (int i=0;i<n.fields.size();i++) {
			storeInHeap = nlJoin(storeInHeap, nlJoin(
				"lhp",//Put the heap pointer on the top of the stack
					"sw",//pop hp and arg value and store on the heap
					"lhp",//Increment hp
					"push 1",
					"add",
					"shp"
			));
		}
	  	return nlJoin(
				argCode,
				storeInHeap,
				"push " + ExecuteVM.MEMSIZE,
				"push "+ n.entry.offset,//Push ID offset on the stack
				"add", //Calculate the address of the dispatch pointer
				"lhp", //load the heap pointer on the stack
				"sw",
				"lhp",
				"lhp",
				"push 1",
				"add",
				"shp"
				);
	}

	@Override
	public String visitNode(ClassCallNode n){
		if (print) printNode(n,n.id_method);
		String argCode = null, getAR = null;
		for (int i=n.arglist.size()-1;i>=0;i--) argCode=nlJoin(argCode,visit(n.arglist.get(i)));
		for (int i = 0;i<n.nl-n.entry.nl;i++) getAR=nlJoin(getAR,"lw");

		return nlJoin(
				"lfp", // load Control Link (pointer to frame of function "id" caller)
				argCode, // generate code for argument expressions in reversed order
				"lfp", getAR, // retrieve address of frame containing "id" declaration
				// by following the static chain (of Access Links)
				"push "+n.entry.offset, "add", // compute address of "id" declaration
				"lw", // load value of "id" variable
				"stm", //Set $tm to the popped value
				"ltm",
				"ltm", //Duplicate the top of the stack
				"lw",
				"lw", // load address of "id" function
				"push "+n.methodEntry.offset, "add", //Find ID2 in the dispatch table
				"lw",
				"js"  // jump to popped address (saving address of subsequent instruction in $ra)
		);
	}

	@Override
	public String visitNode(IdNode n) {//quando trova una variabile (dichiarazione locale o oggetto sull'heap)
		if (print) printNode(n,n.id);

		String getAR = null;
		for (int i = 0;i<n.nl-n.entry.nl;i++) getAR=nlJoin(getAR,"lw");//per ogni nesting level prende dallo stack un indirizzo, viene letto cosa c'è in quell'indirizzo e viene messo nello stack. Come effetto si ottiene una risalita dei nesting level. AL termine viene preso l'indirizzo della dichiarazione di ID utilizzando l'offset
		return nlJoin(
			"lfp", getAR, // retrieve address of frame containing "id" declaration
			              // by following the static chain (of Access Links)
			"push "+n.entry.offset, "add", // compute address of "id" declaration
			"lw" // load value of "id" variable
		);
	}

	@Override
	public String visitNode(BoolNode n) {
		if (print) printNode(n,n.val.toString());
		return "push "+(n.val?1:0);
	}

	@Override
	public String visitNode(IntNode n) {
		if (print) printNode(n,n.val.toString());
		return "push "+n.val;
	}
}