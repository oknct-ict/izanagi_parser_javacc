/* Generated By:JJTree: Do not edit this line. ASTEq.java Version 6.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=tk.oknctict.izanagi.shell.BaseNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package tk.oknctict.izanagi.parser;

public
class ASTEq extends SimpleNode {
  public ASTEq(int id) {
    super(id);
  }

  public ASTEq(ExprParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ExprParserVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=510fed4fe4098a664df7ab603175bf65 (do not edit this line) */
