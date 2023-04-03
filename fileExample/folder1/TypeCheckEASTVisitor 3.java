package compiler;

import compiler.AST.*;
import compiler.exc.*;
import compiler.lib.*;
import static compiler.TypeRels.*;

//visitNode(n) fa il type checking di un Node n e ritorna:
//- per una espressione, il suo tipo (oggetto BoolTypeNode o IntTypeNode)
//- per una dichiarazione, "null"; controlla la correttezza interna della dichiarazione
//(- per un tipo: "null"; controlla che il tipo non sia incompleto) 
//
//visitSTentry(s) ritorna, per una STentry s, il tipo contenuto al suo interno
public class TypeCheckEASTVisitor extends BaseEASTVisitor<TypeNode,TypeException> {

	TypeCheckEASTVisitor() { super(true); } // enables incomplete tree exceptions 
	TypeCheckEASTVisitor(boolean debug) { super(true,debug); } // enables print for debugging

	//checks that a type object is visitable (not incomplete)
	private TypeNode ckvisit(TypeNode t) throws TypeException {
		visit(t);
		return t;
	} 
	
	@Override
	public TypeNode visitNode(ProgLetInNode n) throws TypeException {
		if (print) printNode(n);
		for (Node dec : n.declist)
			try {
				visit(dec);
			} catch (IncomplException e) { 
			} catch (TypeException e) {
				System.out.println("Type checking error in a declaration: " + e.text);
			}
		return visit(n.exp);
	}

	@Override
	public TypeNode visitNode(ProgNode n) throws TypeException {
		if (print) printNode(n);
		return visit(n.exp);
	}

	@Override
	public TypeNode visitNode(FunNode n) throws TypeException {
		if (print) printNode(n,n.id);
		for (Node dec : n.declist)
			try {
				visit(dec);
			} catch (IncomplException e) { 
			} catch (TypeException e) {
				System.out.println("Type checking error in a declaration: " + e.text);
			}
		if ( !isSubtype(visit(n.exp),ckvisit(n.retType)) ) 
			throw new TypeException("Wrong return type for function " + n.id,n.getLine());
		return null;
	}

	@Override
	public TypeNode visitNode(VarNode n) throws TypeException {
		if (print) printNode(n,n.id);
		if ( !isSubtype(visit(n.exp),ckvisit(n.getType())) )
			throw new TypeException("Incompatible value for variable " + n.id,n.getLine());
		return null;
	}

	@Override
	public TypeNode visitNode(PrintNode n) throws TypeException {
		if (print) printNode(n);
		return visit(n.exp);
	}

	@Override
	public TypeNode visitNode(IfNode n) throws TypeException {
		if (print) printNode(n);
		if ( !(isSubtype(visit(n.cond), new BoolTypeNode())) )
			throw new TypeException("Non boolean condition in if",n.getLine());
		TypeNode t = visit(n.th);
		TypeNode e = visit(n.el);
		if (isSubtype(t, e)) return e;
		if (isSubtype(e, t)) return t;
		throw new TypeException("Incompatible types in then-else branches",n.getLine());
	}

	@Override
	public TypeNode visitNode(EqualNode n) throws TypeException {
		if (print) printNode(n);
		TypeNode l = visit(n.left);
		TypeNode r = visit(n.right);
		if ( !(isSubtype(l, r) || isSubtype(r, l)) )
			throw new TypeException("Incompatible types in equal",n.getLine());
		return new BoolTypeNode();
	}

	@Override
	public TypeNode visitNode(TimesNode n) throws TypeException {
		if (print) printNode(n);
		if ( !(isSubtype(visit(n.left), new IntTypeNode()) && isSubtype(visit(n.right), new IntTypeNode()))) {
			throw new TypeException("Non integers in multiplication", n.getLine());
		}
		return new IntTypeNode();
	}

	@Override
	public TypeNode visitNode(PlusNode n) throws TypeException {
		if (print) printNode(n);
		if ( !(isSubtype(visit(n.left), new IntTypeNode()) && isSubtype(visit(n.right), new IntTypeNode())) ) {
			throw new TypeException("Non integers in sum", n.getLine());
		}
		return new IntTypeNode();
	}

	@Override
	public TypeNode visitNode(IdNode n) throws TypeException {
		if (print) printNode(n,n.id);
		TypeNode t = visit(n.entry); 
		if (t instanceof ArrowTypeNode || t instanceof ClassTypeNode || t instanceof MethodTypeNode)
			throw new TypeException("Wrong usage of function identifier " + n.id,n.getLine());
		return t;
	}

	@Override
	public TypeNode visitNode(BoolNode n) {
		if (print) printNode(n,n.val.toString());
		return new BoolTypeNode();
	}

	@Override
	public TypeNode visitNode(IntNode n) {
		if (print) printNode(n,n.val.toString());
		return new IntTypeNode();
	}

	@Override
	public TypeNode visitNode(EmptyNode n) throws TypeException {
		if (print) printNode(n);
		return new EmptyTypeNode();
	}

	@Override
	public TypeNode visitNode(ArrowTypeNode n) throws TypeException {
		if (print) printNode(n);
		for (Node par: n.parlist) visit(par);
		visit(n.ret,"->"); //marks return type
		return null;
	}

	@Override
	public TypeNode visitNode(BoolTypeNode n) {
		if (print) printNode(n);
		return null;
	}

	@Override
	public TypeNode visitNode(IntTypeNode n) {
		if (print) printNode(n);
		return null;
	}

	@Override
	public TypeNode visitSTentry(STentry entry) throws TypeException {
		if (print) printSTentry("type");
		return ckvisit(entry.type); 
	}

	//estensione degli operatori

	@Override
	public TypeNode visitNode(DivNode n) throws TypeException {
		if (print) printNode(n);
		if ( !(isSubtype(visit(n.left), new IntTypeNode()) && isSubtype(visit(n.right), new IntTypeNode())) ) {
			throw new TypeException("Non integers in division", n.getLine());
		}
		return new IntTypeNode();
	}

	@Override
	public TypeNode visitNode(MinusNode n) throws TypeException {
		if (print) printNode(n);
		if ( !(isSubtype(visit(n.left), new IntTypeNode())  && isSubtype(visit(n.right), new IntTypeNode())) ) {
			throw new TypeException("Non integers in minus", n.getLine());
		}
		return new IntTypeNode();
	}

	@Override
	public TypeNode visitNode(AndNode n) throws TypeException {
		if (print) printNode(n);
		if ( !(visit(n.left) instanceof BoolTypeNode) || !(visit(n.right) instanceof BoolTypeNode) ){
			throw new TypeException("Incompatible types in and",n.getLine());
		}
		return new BoolTypeNode();
	}

	@Override
	public TypeNode visitNode(OrNode n) throws TypeException {
		if (print) printNode(n);
		if ( !(visit(n.left) instanceof BoolTypeNode) || !(visit(n.right) instanceof BoolTypeNode)) {
			throw new TypeException("Incompatible types in or", n.getLine());
		}
		return new BoolTypeNode();
	}

	@Override
	public TypeNode visitNode(NotNode n) throws TypeException {
		if (print) printNode(n);
		if (!(visit(n.exp) instanceof BoolTypeNode)) {
			throw new TypeException("Incompatible types in not", n.getLine());
		}
		return new BoolTypeNode();
	}

	@Override
	public TypeNode visitNode(GreaterEqualNode n) throws TypeException {
		if (print) printNode(n);
		if (!(visit(n.left) instanceof IntTypeNode) || !(visit(n.right) instanceof IntTypeNode)) {
			throw new TypeException("Incompatible types in greater equal", n.getLine());
		}
		return new BoolTypeNode();
	}

	@Override
	public TypeNode visitNode(LessEqualNode n) throws TypeException {
		if (print) printNode(n);
		if (!(visit(n.left) instanceof IntTypeNode) || !(visit(n.right) instanceof IntTypeNode)) {
			throw new TypeException("Incompatible types in less equal", n.getLine());
		}
		return new BoolTypeNode();
	}

	//estensione object oriented

	@Override
	public TypeNode visitNode(MethodNode n) throws TypeException {
		if (print) printNode(n,n.id);
		for (Node declaration : n.declist) {
			try {
				visit(declaration);
			} catch (IncomplException e) {
			} catch (TypeException e) {
				System.out.println("Type checking error in a declaration: " + e.text);
			}
		}
		if ( !isSubtype(visit(n.exp),ckvisit(n.retType)) ) {
			throw new TypeException("Wrong return type for method " + n.id, n.getLine());
		}
		return null;
	}

	@Override
	public TypeNode visitNode(ClassNode n) throws TypeException {
		if (print) printNode(n,n.id);
		for (Node method : n.methods) {
			visit(method);
		}
		return null;
	}

	@Override
	public TypeNode visitNode(RefTypeNode n){
		if (print) printNode(n, n.id);
		return null;
	}

	@Override
	public TypeNode visitNode(ClassTypeNode n) throws TypeException {
		if (print) printNode(n);
		for(TypeNode field : n.allFields){
			visit(field);
		}
		for(ArrowTypeNode method : n.allMethods) {
			visit(method);
		}
		return null;
	}

	@Override
	public TypeNode visitNode(EmptyTypeNode n) throws TypeException{
		if (print) printNode(n);
		return null;
	}

	@Override
	public TypeNode visitNode(CallNode n) throws TypeException {
		if (print) printNode(n,n.id);
		TypeNode t = visit(n.entry);
		if ( !(t instanceof ArrowTypeNode) && !(t instanceof MethodTypeNode) ) {
			throw new TypeException("Invocation of a non-function " + n.id, n.getLine());
		}

		ArrowTypeNode arrowType;
		if(t instanceof MethodTypeNode){
			arrowType = ((MethodTypeNode) t).fun;
		}else{
			arrowType = (ArrowTypeNode) t;
		}

		if (arrowType.parlist.size() != n.arglist.size()) {
			throw new TypeException("Wrong number of parameters in the invocation of " + n.id, n.getLine());
		}
		for (int i = 0; i < n.arglist.size(); i++) {
			if (!(isSubtype(visit(n.arglist.get(i)), arrowType.parlist.get(i)))) {
				throw new TypeException("Wrong type for " + (i + 1) + "-th parameter in the invocation of " + n.id, n.getLine());
			}
		}
		return arrowType.ret;
	}

	@Override
	public TypeNode visitNode(ClassCallNode n) throws TypeException {
		if (print) printNode(n,n.idMethod);
		TypeNode t = visit(n.methodEntry);
		if ( !(t instanceof ArrowTypeNode) && !(t instanceof MethodTypeNode) ) {
			throw new TypeException("Invocation of a non-function " + n.idMethod, n.getLine());
		}

		ArrowTypeNode arrowType;
		if(t instanceof MethodTypeNode){
			arrowType = ((MethodTypeNode) t).fun;
		}else{
			arrowType = (ArrowTypeNode) t;
		}

		if (arrowType.parlist.size() != n.arglist.size()) {
			throw new TypeException("Wrong number of parameters in the invocation of " + n.idMethod, n.getLine());
		}
		for (int i = 0; i < n.arglist.size(); i++) {
			if (!(isSubtype(visit(n.arglist.get(i)), arrowType.parlist.get(i)))) {
				throw new TypeException("Wrong type for " + (i + 1) + "-th parameter in the invocation of " + n.idMethod, n.getLine());
			}
		}
		return arrowType.ret;
	}

	@Override
	public TypeNode visitNode(NewNode n) throws TypeException {
		if (print) printNode(n,n.id);

		final ClassTypeNode classType = (ClassTypeNode) visit(n.entry);
		if (classType.allFields.size() != n.fields.size()) {
			throw new TypeException("Wrong number of parameters in the creation of an object of class " + n.id, n.getLine());
		}

		for (int i = 0; i < n.fields.size(); i++){
			if ( !(isSubtype(visit(n.fields.get(i)),classType.allFields.get(i))) ) {
				throw new TypeException("Wrong type for " + (i + 1) + "-th parameter in the creation of an object of class " + n.id, n.getLine());
			}
		}

		return new RefTypeNode(n.id);
	}

}