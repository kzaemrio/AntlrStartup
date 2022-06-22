import org.antlr.v4.runtime.*

object Antlr {
    inline fun <reified SubLexer : Lexer, reified SubParser : Parser> parseString(input: String): SubParser {
        return parse<SubLexer, SubParser>(CharStreams.fromString(input))
    }

    inline fun <reified SubLexer : Lexer, reified SubParser : Parser> parse(charStream: CharStream): SubParser {
        val lexer: SubLexer = SubLexer::class.java.declaredConstructors.first().newInstance(charStream) as SubLexer
        val tokenStream = BufferedTokenStream(lexer)
        return SubParser::class.java.declaredConstructors.first().newInstance(tokenStream) as SubParser
    }
}
