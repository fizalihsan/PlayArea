package tutorial.dependencyinjection.sample2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author mohammo on 11/9/2014.
 */
public class Main {
    public static void main(String[] args) {
        //load the spring configuration class annotated with @Configuration
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);

        //from the configuration class, get me a bean of type 'AccountService'
        AccountService accountService = context.getBean(AccountService.class);

        System.out.println("Balance = " + accountService.getBalance());
    }


}

