package tutorial.dependencyinjection.sample2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mohammo on 11/9/2014.
 */
@Configuration
public class SpringConfiguration {

    @Bean
    public AccountService getAccountService(){
        return new AccountServiceImpl();
    }

    @Bean (name = "sybaseAccountDAO")
    public AccountDAO getAccountSybaseDAO(){
        return new AccountDAOSybaseImpl();
    }

    @Bean (name = "myAccountDAO")
    public AccountDAO getAccountOracleDAO(){
        return new AccountDAOOracleImpl();
    }
}
