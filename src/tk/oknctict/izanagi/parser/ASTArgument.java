/* Generated By:JJTree: Do not edit this line. ASTArgument.java Version 6.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=tk.oknctict.izanagi.shell.BaseNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package tk.oknctict.izanagi.parser;

public
class ASTArgument extends SimpleNode {
  public ASTArgument(int id) {
    super(id);
  }

  public ASTArgument(ExprParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ExprParserVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=0f5bf9b7e55b56ead393a9f3bab4fa85 (do not edit this line) */
