package sportify.backend.api.service.pointsTable;

import sportify.backend.api.dto.pointsTable.IplPointsTableDto;

import java.util.List;

public interface IplPointsTableService {
    IplPointsTableDto createEntity(String teamName) throws Exception;
    List<IplPointsTableDto> getEntitiesAsPerOrder() throws Exception;

}
