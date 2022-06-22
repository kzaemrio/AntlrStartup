import JavaParser.ClassDeclarationContext
import JavaParser.MethodDeclarationContext
import org.antlr.v4.runtime.tree.ParseTreeListener
import org.antlr.v4.runtime.tree.ParseTreeWalker

fun main() {
    val path = "ExtractInterface/src/main/res/Demo.java.txt"
    val parser = Antlr.parseFile<JavaLexer, JavaParser>(path)
    val tree = parser.compilationUnit()
    ParseTreeWalker().walk(listener(parser), tree)
}

private fun listener(parser: JavaParser): ParseTreeListener {
    return object :JavaParserBaseListener() {
        override fun enterClassDeclaration(ctx: ClassDeclarationContext) {
            super.enterClassDeclaration(ctx)
            println(String.format("interface I%s {", ctx.IDENTIFIER()))
        }

        override fun exitClassDeclaration(ctx: ClassDeclarationContext?) {
            super.exitClassDeclaration(ctx)
            println("}")
        }

        override fun enterMethodDeclaration(ctx: MethodDeclarationContext) {
            super.enterMethodDeclaration(ctx)
            val tokenStream = parser.tokenStream
            val type = tokenStream.getText(ctx.typeTypeOrVoid())
            val args = tokenStream.getText(ctx.formalParameters())
            println(String.format("\t%s %s%s;", type, ctx.IDENTIFIER(), args))
        }
    }
}
