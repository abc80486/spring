import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "springstudy")
public class MultienvConfig {
    private String demoname;

    public String getDemoname() {
        return demoname;
    }

    public void setDemoname(String demoname) {
        this.demoname = demoname;
    }
}