package sportify.backend.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sportify.backend.api.config.Constants;
import sportify.backend.api.config.RestEndPoints;
import sportify.backend.api.domain.matches.IplAllMatchesApi;
import sportify.backend.api.dto.pointsTable.IplPointsTableDto;
import sportify.backend.api.pagination.CustomPage;
import sportify.backend.api.service.pointsTable.IplPointsTableService;

@RestController
@RequestMapping(RestEndPoints.IPL_POINTS_TABLE)
public class PointsTableController {
    @Autowired
    IplPointsTableService iplPointsTable;
    @PostMapping("/{teamname}")
    public ResponseEntity<IplPointsTableDto> createEntity(@PathVariable(name = "teamname")String teamname)throws Exception{
        return new ResponseEntity<>(iplPointsTable.createEntity(teamname), HttpStatus.CREATED);
    }
}
