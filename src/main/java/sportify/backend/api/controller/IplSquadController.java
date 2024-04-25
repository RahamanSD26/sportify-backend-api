package sportify.backend.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sportify.backend.api.config.RestEndPoints;
import sportify.backend.api.dto.squad.IplSquadApiDto;
import sportify.backend.api.service.squad.IplSquadApiService;

@RestController
@RequestMapping(RestEndPoints.IPL_TEAM_SQUAD)
public class IplSquadController {
    @Autowired
    IplSquadApiService iplSquadApiService;
    @PostMapping("/{id}")
    public ResponseEntity<IplSquadApiDto> createEntityById(@PathVariable(name = "id")String matchId)throws Exception{
        return new ResponseEntity<>(iplSquadApiService.createEntityById(matchId), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IplSquadApiDto> getEntityById(@PathVariable(name = "id")String matchId)throws Exception{
        return new ResponseEntity<>(iplSquadApiService.getEntityById(matchId), HttpStatus.CREATED);
    }

}
