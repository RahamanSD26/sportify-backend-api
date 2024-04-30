package sportify.backend.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sportify.backend.api.config.Constants;
import sportify.backend.api.config.RestEndPoints;
import sportify.backend.api.dto.scoreCard.IplScoreCardApiDto;
import sportify.backend.api.pagination.CustomPage;
import sportify.backend.api.service.scoreCard.IplScoreCardApiService;
import sportify.backend.api.util.JavaApiClass.iplscorecard.Score;
import sportify.backend.api.util.JavaApiClass.iplscorecard.ScoreCard;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(RestEndPoints.IPL_SCORE_CARD)
public class ScoreCardController {
    @Autowired
    private IplScoreCardApiService iplScoreCardApiService;
    @PostMapping("/createByDateTime/{date_time}")
    public ResponseEntity<CustomPage<IplScoreCardApiDto>> createEntity(@PathVariable(name="date_time") String date_time)throws Exception{
        return new ResponseEntity<>(new CustomPage<>(iplScoreCardApiService.createEntity(date_time), Constants.BLANK_CONSTANT), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<CustomPage<IplScoreCardApiDto>> getAllEntity(Pageable pageable)throws Exception{
        return new ResponseEntity<>(new CustomPage<>(iplScoreCardApiService.getAllEntities(pageable), Constants.BLANK_CONSTANT), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
        public ResponseEntity<IplScoreCardApiDto> getEntityById(@PathVariable(name="id") String id)throws Exception{
        return new ResponseEntity<>(iplScoreCardApiService.getEntityById(id), HttpStatus.CREATED);
    }
    @GetMapping("/getscorecard/{id}")
    public ResponseEntity<IplScoreCardApiDto> getScoreCardById(@PathVariable(name="id") String id)throws Exception{
        return new ResponseEntity<>(iplScoreCardApiService.getEntityById(id), HttpStatus.CREATED);
    }
    @GetMapping("/getcountbyteamname/{name}")
    public ResponseEntity<Long> getEntitiesCountById(@PathVariable(name="name") String name)throws Exception{
        return new ResponseEntity<>(iplScoreCardApiService.getEntitiesCountByTeamName(name), HttpStatus.OK);
    }

}
