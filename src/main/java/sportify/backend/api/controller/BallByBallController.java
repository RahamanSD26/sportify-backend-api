package sportify.backend.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sportify.backend.api.config.RestEndPoints;
import sportify.backend.api.dto.iplBallByBall.IplBallByBallApiDto;
import sportify.backend.api.service.iplBallbyBall.IplBallByBallApiService;

@RestController
@RequestMapping(RestEndPoints.IPL_BALL_BY_BALL_DATA)
public class BallByBallController {
    @Autowired
    IplBallByBallApiService iplBallByBallApiService;

    @PostMapping("/{id}")
    public ResponseEntity<IplBallByBallApiDto> createEntityById(@PathVariable(name = "id")String matchId)throws Exception{
        return new ResponseEntity<>(iplBallByBallApiService.createEntityById(matchId), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IplBallByBallApiDto> getEntityById(@PathVariable(name = "id")String matchId)throws Exception{
        return new ResponseEntity<>(iplBallByBallApiService.getEntityById(matchId), HttpStatus.OK);
    }
}
