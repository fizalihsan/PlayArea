package tutorial.dependencyinjection.sample2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author mohammo on 11/9/2014.
 */

public class AccountServiceImpl implements AccountService {

    @Autowired
    @Qualifier("myAccountDAO")
    private AccountDAO accountDAO;

    @Override
    public double getBalance() {
        return accountDAO.calculateBalance();
    }
}
