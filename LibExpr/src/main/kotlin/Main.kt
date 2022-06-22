
fun main() {
    val path = "Expr/src/main/res/Input.expr"
    val parser = Antlr.parseFile<LibExprLexer, LibExprParser>(path)
    println(parser.prog().toStringTree(parser))
}
