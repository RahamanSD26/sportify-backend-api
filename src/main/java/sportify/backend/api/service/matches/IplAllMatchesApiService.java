package sportify.backend.api.service.matches;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sportify.backend.api.domain.matches.IplAllMatchesApi;
import sportify.backend.api.dto.matches.IplAllMatchesApiDto;

import java.util.List;


@Service
public interface IplAllMatchesApiService {
 List<IplAllMatchesApi> createEntity() throws Exception;
 List<IplAllMatchesApiDto> getAllEntities()throws  Exception;
 List<IplAllMatchesApiDto> getMatchListByDate(String date)throws Exception;
 IplAllMatchesApiDto getMatchByTime(String time)throws Exception;
 IplAllMatchesApiDto getMatchById(String id)throws  Exception;
 List<IplAllMatchesApi> getEntitiesByStatus(boolean isActive)throws Exception;
 List<IplAllMatchesApi> getEntitiesByVenue(String venue)throws Exception;
 List<IplAllMatchesApi> getEntitiesByTeamName(String teamName);
 List<IplAllMatchesApi> getEntitiesByTeamNameAndVenue(String shortName, String venue);
}
