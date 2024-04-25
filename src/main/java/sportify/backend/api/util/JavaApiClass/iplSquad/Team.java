package sportify.backend.api.util.JavaApiClass.iplSquad;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    private String teamName;
    private String shortname;
    private String img;
    private List<Player> players;
}
