package tutorial.dependencyinjection.sample1;

/**
 * @author mohammo on 11/9/2014.
 */
public class Main {
    public static void main(String[] args) {
        AccountService accountService = new AccountServiceImpl();
        System.out.println(accountService.getBalance());
    }
}
