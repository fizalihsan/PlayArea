package tutorial.dependencyinjection.sample1;

/**
 * @author mohammo on 11/9/2014.
 */
public class AccountDAOImpl implements AccountDAO {

    @Override
    public double calculateBalance() {
        //query the database here
        return Math.random();
    }
}
