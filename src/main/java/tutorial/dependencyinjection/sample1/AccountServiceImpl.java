package tutorial.dependencyinjection.sample1;

/**
 * @author mohammo on 11/9/2014.
 */
public class AccountServiceImpl implements AccountService {

    private AccountDAO accountDAO = new AccountDAOImpl();

    @Override
    public double getBalance() {
        return accountDAO.calculateBalance();
    }
}
