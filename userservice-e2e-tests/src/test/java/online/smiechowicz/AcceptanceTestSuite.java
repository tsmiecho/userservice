package online.smiechowicz;

import net.serenitybdd.jbehave.SerenityStories;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.failures.FailingUponPendingStep;

public class AcceptanceTestSuite extends SerenityStories {

    @Override
    public Configuration configuration(){

        Configuration configuration = super.configuration();
        configuration.usePendingStepStrategy(new FailingUponPendingStep());
        return configuration;
    }

}
