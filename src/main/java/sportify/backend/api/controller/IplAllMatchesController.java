package sportify.backend.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sportify.backend.api.config.Constants;
import sportify.backend.api.config.RestEndPoints;
import sportify.backend.api.domain.matches.IplAllMatchesApi;
import sportify.backend.api.dto.matches.IplAllMatchesApiDto;
import sportify.backend.api.pagination.CustomPage;
import sportify.backend.api.service.matches.IplAllMatchesApiService;

import java.util.List;

@RestController
@RequestMapping(RestEndPoints.IPL_ALL_MATCHES)
public class IplAllMatchesController {

    @Autowired
    private IplAllMatchesApiService iplAllMatchesApiService;
    @PostMapping
    public ResponseEntity<CustomPage<IplAllMatchesApi>> createEntity()throws Exception{
        return new ResponseEntity<>(new CustomPage<>(iplAllMatchesApiService.createEntity(), Constants.BLANK_CONSTANT), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<IplAllMatchesApiDto>> getAllEntity()throws Exception{
        return new ResponseEntity<>(iplAllMatchesApiService.getAllEntities(), HttpStatus.CREATED);
    }
    @GetMapping("/getIplMatchByTime")
    public IplAllMatchesApiDto getEntityByTime()throws Exception{
        return iplAllMatchesApiService.getMatchByTime("2024-03-26T14:00:00");
    }
    @GetMapping("/getIplMatchByDate")
    public List<IplAllMatchesApiDto> getEntityByDate()throws Exception{
        return iplAllMatchesApiService.getMatchListByDate("2024-03-26");
    }

    @GetMapping("/{id}") //Name Team vs team b
    public ResponseEntity<CustomPage<IplAllMatchesApiDto>> getEntityById(@PathVariable(name = "id") String id)throws Exception{
        return new ResponseEntity<>(new CustomPage<>(iplAllMatchesApiService.getMatchById(id), Constants.BLANK_CONSTANT), HttpStatus.OK);
    }
    @GetMapping("/listbystatus/{status}")
    public ResponseEntity<List<IplAllMatchesApi>> getEntitiesByStatus(@PathVariable(name = "status")boolean isActive) throws Exception {
        return new ResponseEntity<>(iplAllMatchesApiService.getEntitiesByStatus(isActive),HttpStatus.OK);
    }
    @GetMapping("/listbyvenue/{venue}")
    public ResponseEntity<List<IplAllMatchesApi>> getEntitiesByVenue(@PathVariable(name = "venue")String venue) throws Exception {
        return new ResponseEntity<>(iplAllMatchesApiService.getEntitiesByVenue(venue),HttpStatus.OK);
    }
    @GetMapping("/listbyteamname/{shortName}")
    public ResponseEntity<List<IplAllMatchesApi>> getEntitiesByTeamName(@PathVariable(name = "shortName")String shortName) throws Exception {
        return new ResponseEntity<>(iplAllMatchesApiService.getEntitiesByTeamName(shortName),HttpStatus.OK);
    }

    @GetMapping("/listbyteamnameandvenue")
    public ResponseEntity<List<IplAllMatchesApi>> getEntitiesByTeamNameAndVenue(@RequestParam String shortName,@RequestParam String venue) throws Exception {
        return new ResponseEntity<>(iplAllMatchesApiService.getEntitiesByTeamNameAndVenue(shortName,venue),HttpStatus.OK);
    }
}