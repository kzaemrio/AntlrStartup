import JsonParser.*
import org.antlr.v4.runtime.tree.*

fun main() {
    val path = "JsonToXml/src/main/res/t.json"
    val parser = Antlr.parseFile<JsonLexer, JsonParser>(path)
    val tree = parser.json()

    printByListener(tree)

    printByVisitor(tree)
}


private fun printByVisitor(tree: ParseTree) {
    println(visitor().visit(tree))
}

private fun printByListener(tree: ParseTree) {
    val wrapper = Wrapper.create()
    ParseTreeWalker().walk(listener(wrapper), tree)
    println(wrapper.getXml(tree))
}

private fun visitor(): ParseTreeVisitor<String> {
    return object : JsonBaseVisitor<String>() {
        override fun visitAtom(ctx: AtomContext): String {
            return ctx.text
        }

        override fun visitString(ctx: StringContext): String {
            return stripQuotes(ctx.text)
        }

        override fun visitAnObject(ctx: AnObjectContext): String {
            val builder = StringBuilder()
            for (context in ctx.pair()) {
                builder.append(visit(context))
            }
            return builder.toString()
        }

        override fun visitEmptyObject(ctx: EmptyObjectContext): String {
            return ""
        }

        override fun visitArrayOfValues(ctx: ArrayOfValuesContext): String {
            val builder = StringBuilder()
            for (context in ctx.value()) {
                builder.append("<element>")
                builder.append(visit(context))
                builder.append("</element>")
            }
            return builder.toString()
        }

        override fun visitEmptyArray(ctx: EmptyArrayContext): String {
            return ""
        }

        override fun visitPair(ctx: PairContext): String {
            val tag = stripQuotes(ctx.STRING().text)
            return String.format("<%s>%s</%s>", tag, visit(ctx.value()), tag)
        }

        private fun stripQuotes(text: String): String {
            return text.replace("\"", "")
        }
    }
}

private fun listener(wrapper: Wrapper): ParseTreeListener {
    return object : JsonBaseListener() {
        override fun exitAtom(ctx: AtomContext) {
            super.exitAtom(ctx)
            wrapper.setXml(ctx, ctx.text)
        }

        override fun exitString(ctx: StringContext) {
            super.exitString(ctx)
            wrapper.setXml(ctx, stripQuotes(ctx.text))
        }

        override fun exitObjectValue(ctx: ObjectValueContext) {
            super.exitObjectValue(ctx)
            wrapper.setXml(ctx, wrapper.getXml(ctx.obj()))
        }

        override fun exitPair(ctx: PairContext) {
            super.exitPair(ctx)
            val tag = stripQuotes(ctx.STRING().text)
            val valueContext = ctx.value()
            val text = String.format("<%s>%s</%s>", tag, wrapper.getXml(valueContext), tag)
            wrapper.setXml(ctx, text)
        }

        override fun exitAnObject(ctx: AnObjectContext) {
            super.exitAnObject(ctx)
            val builder = StringBuilder()
            for (context in ctx.pair()) {
                builder.append(wrapper.getXml(context))
            }
            wrapper.setXml(ctx, builder.toString())
        }

        override fun exitEmptyObject(ctx: EmptyObjectContext) {
            super.exitEmptyObject(ctx)
            wrapper.setXml(ctx, "")
        }

        override fun exitArrayValue(ctx: ArrayValueContext) {
            super.exitArrayValue(ctx)
            val builder = StringBuilder()
            for (child in ctx.children) {
                builder.append(wrapper.getXml(child))
            }
            wrapper.setXml(ctx, builder.toString())
        }

        override fun exitArrayOfValues(ctx: ArrayOfValuesContext) {
            super.exitArrayOfValues(ctx)
            val builder = StringBuilder()
            for (context in ctx.value()) {
                builder.append("<element>")
                builder.append(wrapper.getXml(context))
                builder.append("</element>")
            }
            wrapper.setXml(ctx, builder.toString())
        }

        override fun exitEmptyArray(ctx: EmptyArrayContext) {
            super.exitEmptyArray(ctx)
            wrapper.setXml(ctx, "")
        }

        override fun exitJson(ctx: JsonContext) {
            super.exitJson(ctx)
            wrapper.setXml(ctx, wrapper.getXml(ctx.getChild(0)))
        }

        private fun stripQuotes(text: String): String {
            return text.replace("\"", "")
        }
    }
}

private interface Wrapper {
    fun getXml(ctx: ParseTree): String
    fun setXml(ctx: ParseTree, text: String)

    companion object {
        fun create(): Wrapper {
            return object : Wrapper {
                val xml = ParseTreeProperty<String>()
                override fun getXml(ctx: ParseTree): String {
                    return xml[ctx]
                }

                override fun setXml(ctx: ParseTree, text: String) {
                    xml.put(ctx, text)
                }
            }
        }
    }
}
