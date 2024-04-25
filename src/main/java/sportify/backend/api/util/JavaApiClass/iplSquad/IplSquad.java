package sportify.backend.api.util.JavaApiClass.iplSquad;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class IplSquad {
    private String apikey;
    private List<Team> data;
    private String status;
    private SquadInfo info;
}
