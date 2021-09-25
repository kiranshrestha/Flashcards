class Problem {
    public static void main(String[] args) {
        boolean p = false;
        for (int i = 0; i < args.length; i += 2) {
            if ("mode".equals(args[i])) {
                System.out.println(args[i + 1]);
                p = true;
                break;
            }
        }
        System.out.println(p ? "" : "default");
    }
}