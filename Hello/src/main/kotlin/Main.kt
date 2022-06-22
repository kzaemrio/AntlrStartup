fun main() {
    val input = "hello world"
//    val charStream = CharStreams.fromString(input)
//    val helloLexer = HelloLexer(charStream)
//    val helloParser = HelloParser(BufferedTokenStream(helloLexer))
//    println(helloParser.root().toStringTree())

    val parser = Antlr.parseString<HelloLexer, HelloParser>(input)
    println(parser.root().toStringTree(parser))
}
