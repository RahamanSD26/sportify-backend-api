package sportify.backend.api.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Configuration
public class CommonConfig {

    @Value("${sidpik.environment}")
    private String environment;
}
