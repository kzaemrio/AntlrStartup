import java.io.File

fun main() {
    val path = "Expr/src/main/res/Input.expr"
    val parser = Antlr.parseFile<ExprLexer, ExprParser>(path)
    println(parser.prog().toStringTree(parser))
}
