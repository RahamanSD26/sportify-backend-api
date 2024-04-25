package sportify.backend.api.service.squad;

import sportify.backend.api.dto.squad.IplSquadApiDto;

import java.util.Optional;

public interface IplSquadApiService {
   IplSquadApiDto createEntityById(String id)throws Exception;
   IplSquadApiDto getEntityById(String id)throws Exception;
}
