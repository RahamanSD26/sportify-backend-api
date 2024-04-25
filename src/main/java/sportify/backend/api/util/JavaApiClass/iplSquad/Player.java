package sportify.backend.api.util.JavaApiClass.iplSquad;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    private String id;
    private String name;
    private String role;
    private String battingStyle;
    private String bowlingStyle;
    private String country;
    private String playerImg;
}
