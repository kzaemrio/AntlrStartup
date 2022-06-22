

import org.antlr.v4.runtime.BufferedTokenStream
import org.antlr.v4.runtime.CharStreams


fun main() {
    val input = "hello world"
    val charStream = CharStreams.fromString(input)
    val helloLexer = HelloLexer(charStream)
    val helloParser = HelloParser(BufferedTokenStream(helloLexer))
    println(helloParser.root().toStringTree())
}
