import me.kz.HelloLexer
import me.kz.HelloParser
import org.antlr.v4.runtime.BufferedTokenStream
import org.antlr.v4.runtime.CharStreams
import org.junit.jupiter.api.Test

class AntlrTest {
    @Test fun f() {
        val input = "hello world"
        val charStream = CharStreams.fromString(input)
        val helloLexer = HelloLexer(charStream)
        val helloParser = HelloParser(BufferedTokenStream(helloLexer))
        println(helloParser.root().toStringTree())
    }
}
