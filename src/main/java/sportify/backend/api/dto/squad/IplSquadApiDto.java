package sportify.backend.api.dto.squad;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sportify.backend.api.dto.BaseEntityDTO;
import sportify.backend.api.util.JavaApiClass.iplSquad.Player;
import sportify.backend.api.util.JavaApiClass.iplSquad.Team;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IplSquadApiDto extends BaseEntityDTO {
//    private String image;
//    private String teamName;
//    private String shortName;
//    List<Player> playersList;
    private String matchId;
    private List<Team> teamList;
}
