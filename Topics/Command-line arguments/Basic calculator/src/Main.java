class Problem {
    public static void main(String[] args) {
        var a = Integer.parseInt(args[1]);
        var b = Integer.parseInt(args[2]);
        switch (args[0]) {
            case "+" : {
                System.out.println(a + b);
                break;
            }

            case "*" : {
                System.out.println(a * b);
                break;
            }

            case "-" : {
                System.out.println(a - b);
                break;
            }

            default: {
                System.out.println("Unknown operator");
                break;
            }
        }
    }
}