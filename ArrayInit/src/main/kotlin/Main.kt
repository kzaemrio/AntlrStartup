import ArrayInitParser.InitContext
import ArrayInitParser.ValueContext
import org.antlr.v4.runtime.tree.ParseTreeListener
import org.antlr.v4.runtime.tree.ParseTreeWalker

fun main() {
    val parser = Antlr.parseString<ArrayInitLexer, ArrayInitParser>("{1, 2, 3, 4}")
    val init = parser.init()
    println(init.toStringTree(parser))
    ParseTreeWalker().walk(listener(), init)
}

private fun listener(): ParseTreeListener {
    return object :ArrayInitBaseListener() {
        override fun enterInit(ctx: InitContext?) {
            super.enterInit(ctx)
            print('"')
        }

        override fun exitInit(ctx: InitContext?) {
            super.exitInit(ctx)
            print('"')
        }

        override fun enterValue(ctx: ValueContext) {
            super.enterValue(ctx)
            val node = ctx.INT()
            if (node != null) {
                val value = node.text.toInt()
                System.out.printf("\\u%04x", value)
            }
        }
    }
}
