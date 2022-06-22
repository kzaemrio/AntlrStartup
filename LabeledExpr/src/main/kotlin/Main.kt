import LabeledExprParser.*
import org.antlr.v4.runtime.tree.ParseTreeVisitor

fun main() {
    val path = "LabeledExpr/src/main/res/Input.expr"
    val parser = Antlr.parseFile<LabeledExprLexer, LabeledExprParser>(path)
    val tree = parser.prog()
    visitor().visit(tree)
}

private fun visitor(): ParseTreeVisitor<Int> {
    return object : LabeledExprBaseVisitor<Int>() {

        var memory: MutableMap<String, Int> = mutableMapOf()

        override fun visitAssign(ctx: AssignContext): Int {
            val id = ctx.ID().text
            val value = visit(ctx.expr())
            memory[id] = value
            return value
        }

        override fun visitPrintExpr(ctx: PrintExprContext): Int {
            println(visit(ctx.expr()))
            return 0
        }

        override fun visitInt(ctx: IntContext): Int {
            return Integer.valueOf(ctx.INT().text)
        }

        override fun visitId(ctx: IdContext): Int {
            return memory.getOrDefault(ctx.ID().text, 0)
        }

        override fun visitMul(ctx: MulContext): Int {
            return visit(ctx.term()) * visit(ctx.factor())
        }

        override fun visitDiv(ctx: DivContext): Int {
            return visit(ctx.term()) / visit(ctx.factor())
        }

        override fun visitAdd(ctx: AddContext): Int {
            return visit(ctx.expr()) + visit(ctx.term())
        }

        override fun visitSub(ctx: SubContext): Int {
            return visit(ctx.expr()) - visit(ctx.term())
        }

        override fun visitPar(ctx: ParContext): Int {
            return visit(ctx.expr())
        }
    }
}
