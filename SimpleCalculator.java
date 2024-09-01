PARSER_BEGIN(SimpleCalculator)

public class SimpleCalculator {
    public static void main(String[] args) throws ParseException {
        System.out.println("Digite uma expressão:");
        SimpleCalculator parser = new SimpleCalculator(System.in);
        try {
            double result = parser.Expression();
            System.out.println("Resultado: " + result);
        } catch (ParseException e) {
            System.out.println("Erro de análise: " + e.getMessage());
        }
    }
}

PARSER_END(SimpleCalculator)

SKIP :
{
    " " | "\t" | "\n" | "\r"
}

TOKEN :
{
    < PLUS: "+" > |
    < MINUS: "-" > |
    < MULTIPLY: "*" > |
    < DIVIDE: "/" > |
    < NUMBER: (< DIGIT >)+ ( "." (< DIGIT >)+ )? > |
    < #DIGIT: ["0"-"9"] >
}

double Expression():
{
    double left, right;
}
{
    left = Term()
    (
        <PLUS> right = Term() { left += right; } |
        <MINUS> right = Term() { left -= right; }
    )*
    { return left; }
}

double Term():
{
    double left, right;
}
{
    left = Factor()
    (
        <MULTIPLY> right = Factor() { left *= right; } |
        <DIVIDE> right = Factor() { left /= right; }
    )*
    { return left; }
}

double Factor():
{
    double value;
}
{
    <NUMBER> { value = Double.parseDouble(token.image); }
    { return value; }
}
