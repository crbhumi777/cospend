public class Main {
    public static void main(String[] args) {
        ExpenseSharingService service = new ExpenseSharingService();
        service.solve("SHOW");
        System.out.println("**********");
        service.solve("SHOW u1");
        System.out.println("**********");
        service.solve("EXPENSE u1 1000 4 u1 u2 u3 u4 EQUAL");
        System.out.println("**********");
        service.solve("SHOW u4");
        System.out.println("**********");
        service.solve("SHOW u1");
        System.out.println("**********");
        service.solve("EXPENSE u1 1250 2 u2 u3 EXACT 370 880");
        System.out.println("**********");
        service.solve("SHOW");
        System.out.println("**********");
        service.solve("EXPENSE u4 1200 4 u1 u2 u3 u4 PERCENT 40 20 20 20");
        System.out.println("**********");
        service.solve("SHOW u1");
        System.out.println("**********");
        service.solve("SHOW");
        System.out.println("********** END **********");
    }
}