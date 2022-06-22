import CsvParser.*
import org.antlr.v4.runtime.tree.ParseTreeListener
import org.antlr.v4.runtime.tree.ParseTreeWalker
import java.util.*

fun main() {
    val path = "CsvParser/src/main/res/data.csv"
    val parser = Antlr.parseFile<CsvLexer, CsvParser>(path)
    val tree = parser.file()
    ParseTreeWalker().walk(listener(), tree)
}

private fun listener(): ParseTreeListener {
    return object :CsvBaseListener() {

        var header: List<String>? = null
        var cache: MutableList<String>? = null

        override fun exitHdr(ctx: HdrContext) {
            super.exitHdr(ctx)
            header = cache
        }

        override fun enterRow(ctx: RowContext) {
            super.enterRow(ctx)
            cache = LinkedList()
        }

        override fun exitRow(ctx: RowContext) {
            super.exitRow(ctx)
            if (ctx.parent is FileContext) {
                val map: MutableMap<String, String> = LinkedHashMap()
                for (i in cache!!.indices) {
                    map[header!![i]] = cache!![i]
                }
                println(map)
            }
        }

        override fun exitText(ctx: TextContext) {
            super.exitText(ctx)
            cache!!.add(ctx.TEXT().text)
        }

        override fun exitString(ctx: StringContext) {
            super.exitString(ctx)
            cache!!.add(ctx.STRING().text)
        }

        override fun exitEmpty(ctx: EmptyContext) {
            super.exitEmpty(ctx)
            cache!!.add("")
        }
    }
}
