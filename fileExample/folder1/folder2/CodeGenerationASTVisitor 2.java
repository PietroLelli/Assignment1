package compiler;

import compiler.AST.*;
import compiler.lib.*;
import compiler.exc.*;
import visualsvm.ExecuteVM;

import java.util.ArrayList;

import static compiler.lib.FOOLlib.*;

public class CodeGenerationASTVisitor extends BaseASTVisitor<String, VoidException> {

  CodeGenerationASTVisitor() {}
  CodeGenerationASTVisitor(boolean debug) {super(false,debug);} //enables print for debugging

	@Override
	public String visitNode(ProgLetInNode n) {
		if (print) printNode(n);
		String declCode = null;
		for (Node dec : n.declist) declCode=nlJoin(declCode,visit(dec));
		return nlJoin(
			"push 0",
			declCode, //codice delle dichiarazioni
			visit(n.exp), //codice dell'espressione
			"halt",
			getCode()
		);
	}

	@Override
	public String visitNode(ProgNode n) {
		if (print) printNode(n);
		return nlJoin(
			visit(n.exp), //codice dell'espressione
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
		for (int i=0;i<n.parlist.size();i++){
			popParl = nlJoin(popParl,"pop");
		}
		String funl = freshFunLabel();
		putCode(
			nlJoin(
				funl+":",
				"cfp",
				"lra",
				declCode,
				visit(n.exp),
				"stm",
				popDecl,
				"sra",
				"pop",
				popParl,
				"sfp",
				"ltm",
				"lra",
				"js"
			)
		);
		return "push "+funl;		
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
	public String visitNode(TimesNode n) {
		if (print) printNode(n);
		return nlJoin(
			visit(n.left),
			visit(n.right),
			"mult"
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
	public String visitNode(CallNode n) {
		if (print) printNode(n, n.id);
		String argCode = null;
		String getAR = null;
		for(int i=n.arglist.size()-1; i>=0; i--){
			argCode = nlJoin(argCode, visit(n.arglist.get(i)));
		}
		for(int i = 0; i<n.nl-n.entry.nl; i++){
			getAR = nlJoin(getAR, "lw");
		}
		if(n.entry.type instanceof MethodTypeNode){
			return nlJoin(
					"lfp",
					argCode,
					"lfp",
					getAR,
					"stm",
					"ltm",
					"ltm",
					"lw",
					"push " + n.entry.offset,
					"add",
					"lw",
					"js"
			);
		}else{
			return nlJoin(
					"lfp",
					argCode,
					"lfp",
					getAR,
					"stm",
					"ltm",
					"ltm",
					"push "+n.entry.offset,
					"add",
					"lw",
					"js"
			);
		}
	}

	@Override
	public String visitNode(IdNode n) {
		if (print) printNode(n,n.id);
		String getAR = null;
		for (int i=0; i<n.nl-n.entry.nl; i++){
			getAR=nlJoin(getAR,"lw");
		}
		return nlJoin(
			"lfp",
				getAR,
			"push "+n.entry.offset,
			"add",
			"lw"
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

	//estensione degli operatori

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
	public String visitNode(DivNode n) {
		if (print) printNode(n);
		return nlJoin(
				visit(n.left),
				visit(n.right),
				"div"
		);
	}

	@Override
	public String visitNode(AndNode n) {
		if (print) printNode(n);
		String l1 = freshLabel();
		String l2 = freshLabel();
		return nlJoin(
				visit(n.left), "push 0", "beq "+l1, //if left is false push 0
				visit(n.right), "push 0", "beq "+l1, //if right is false push 0
				"push 1", //in other case push 1 (true)
				"b "+l2,
				l1+":",
				"push 0",
				l2+":"
		);
	}

	@Override
	public String visitNode(OrNode n) {
		if (print) printNode(n);
		String l1 = freshLabel();
		String l2 = freshLabel();
		return nlJoin(
				visit(n.left), "push 1", "beq "+l1, //if left is true push 1
				visit(n.right), "push 1", "beq "+l1, //if right is true push 1
				"push 0", //in other case push 0
				"b "+l2,
				l1+":",
				"push 1",
				l2+":"
		);
	}

	@Override
	public String visitNode(NotNode n) {
		if (print) printNode(n);
		String l1 = freshLabel();
		String l2 = freshLabel();
		return nlJoin(
				visit(n.exp), "push 0", //if zero push one
				"beq "+l1,
				"push 0", //else push 0
				"b "+l2,
				l1+":",
				"push 1",
				l2+":"
		);
	}

	@Override
	public String visitNode(LessEqualNode n) {
		if (print) printNode(n);
		final String l1 = freshLabel();
		final String l2 = freshLabel();
		return nlJoin(
				visit(n.left), visit(n.right), "bleq " + l1,
				"push 0",
				"b " + l2,
				l1 + ":",
				"push 1",
				l2 + ":"
		);
	}

	@Override
	public String visitNode(GreaterEqualNode n) {
		if (print) printNode(n);
		String l1 = freshLabel();
		String l2 = freshLabel();
		return nlJoin(
				visit(n.left), visit(n.right), "sub", //compute the difference
				"push 1", "add", //sum 1 (to manage equal case)
				"push 0", "bleq "+l1, //compare the result with zero
				"push 1",
				"b "+l2,
				l1+":",
				"push 0",
				l2+":"
		);
	}

	//estensione object oriented

	@Override
	public String visitNode(EmptyNode n){
		if (print) printNode(n);
		return nlJoin(
				"push -1"
		);
	}

	@Override
	public String visitNode(MethodNode n) {
		if (print) printNode(n,n.id);

		n.label = freshFunLabel(); //generazione della label per il metodo

		String declCode = null;
		String popDecl = null;
		for (Node dec : n.declist) {
			declCode = nlJoin(declCode, visit(dec));
			popDecl = nlJoin(popDecl, "pop");
		}

		String popParl = null;
		for (int i=0; i<n.parlist.size() ; i++){
			popParl = nlJoin(popParl, "pop");
		}

		putCode(
				nlJoin(
						n.label + ":",
						"cfp",
						"lra",
						declCode,
						visit(n.exp),
						"stm",
						popDecl,
						"sra",
						"pop",
						popParl,
						"sfp",
						"ltm",
						"lra", "js" //jump to the return address (the method execution is ended)
				)
		);
		return null;
	}

	/*
	 * 1 - 	carico sullo stack l'heap pointer corrente (sarà il dispatch pointer della
	 * 		classe che sto visitando).
	 * 2 - 	visito tutti i metodi della classe, aggiungendoli alla dispatch table (uso la
	 * 		primitiva sw per caricare i valori nello heap e incremento il valore dell'heap pointer
	 * 		dopo ogni nuovo metodo)
	 * */
	@Override
	public String visitNode(ClassNode n){
		//visit every method of the class in order to build the dispatch table
		final ArrayList<String> dispatchTable = new ArrayList<>();
		for(MethodNode m : n.methods){
			visit(m);
			dispatchTable.add(m.offset, m.label);
		}

		//code to build the dispatch table on the heap
		String dtBuilding = null;
		for (String label : dispatchTable){
			dtBuilding = nlJoin(dtBuilding, nlJoin(
					"push " + label, "lhp", "sw", //push the function address on the heap
					"lhp", "push 1", "add", "shp" //update the heap pointer
			));
		}

		return nlJoin(
				"lhp", //push the class dispatch pointer on the stack
				dtBuilding //build the dispatch table
		);
	}

	/*
	 *	1 -	visito in ordine tutti i parametri del costruttore e pusho
	 * 		il loro valore sullo stack (solo temporaneamente)
	 * 	2 -	sposto i valori dallo stack allo heap
	 *	3 - pusho sullo heap il dispatch pointer della classe corrispondente
	 * 		(calcolato come memsize-entry)
	 * 	4 -	pusho sullo stack l'object pointer (valore di hp corrente)
	 */
	@Override
	public String visitNode(NewNode n){
		if (print) printNode(n,n.id);

		//load every parameter of the constructor on the stack
		//(the first in the list is the first one to be pushed)
		String loadParams = null;
		for (int i=0; i<n.fields.size(); i++){
			loadParams = nlJoin(loadParams, visit(n.fields.get(i)));
		}

		//mode the parameters from the stack to the heap through sw
		//sposto i parametri dallo stack allo heap (sw) e aggiorno l'hp
		String moveFromStackToHeap = null;
		for (int i=0; i<n.fields.size(); i++){
			moveFromStackToHeap = nlJoin(moveFromStackToHeap, nlJoin(
					"lhp", "sw", //move parameter 'i' from stack to heap
					"lhp", "push 1", "add", "shp" //update the heap pointer
			));
		}

		return nlJoin(
				loadParams,
				moveFromStackToHeap,
				"push " + ExecuteVM.MEMSIZE, "push "+ n.entry.offset, "add", //compute the dispatch pointer of the class
				"lhp", "sw", //push the dispatch pointer on the heap
				"lhp", //load the object pointer on the stack
				"lhp", "push 1", "add", "shp" //update the heap pointer
		);
	}

	@Override
	public String visitNode(ClassCallNode n){
		if (print) printNode(n,n.idMethod);

		//load every argument on the stack after its visit
		String argsCode = null;
		for (int i=n.arglist.size()-1; i>=0; i--){
			argsCode =nlJoin(argsCode,visit(n.arglist.get(i)));
		}

		//compute the AR address
		String getAR = null;
		for (int i = 0; i<n.nl-n.entry.nl; i++){
			getAR=nlJoin(getAR,"lw");
		}

		return nlJoin(
				"lfp",
				argsCode,
				"lfp",
				getAR,
				"push " + n.entry.offset, "add", "lw", //compute the object address
				"stm", "ltm", "ltm", //duplicate the top of the stack
				"lw", "lw", //compute the dispatch pointer of the class
				"push " + n.methodEntry.offset, "add", "lw", //then compute the address of the function
				"js" //jump to the function address
		);
	}

}