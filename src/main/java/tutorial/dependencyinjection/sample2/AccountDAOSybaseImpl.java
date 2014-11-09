package tutorial.dependencyinjection.sample2;

/**
 * @author mohammo on 11/9/2014.
 */
public class AccountDAOSybaseImpl implements AccountDAO {

    @Override
    public double calculateBalance() {
        //query the database here
        return Math.random();
    }
}
