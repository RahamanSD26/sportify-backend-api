package sportify.backend.api.service.matches;

import sportify.backend.api.domain.matches.IplAllMatchesApi;
import sportify.backend.api.dto.matches.IplAllMatchesApiDto;

import java.util.List;

public interface IplAllMatchesApiService {
 List<IplAllMatchesApi> createEntity() throws Exception;
 List<IplAllMatchesApiDto> getAllEntities()throws  Exception;
 List<IplAllMatchesApiDto> getMatchListByDate(String date)throws Exception;
 IplAllMatchesApiDto getMatchByTime(String time)throws Exception;
 IplAllMatchesApiDto getMatchById(String id)throws  Exception;
 List<IplAllMatchesApiDto> getEntitiesByStatus(Boolean isActive)throws Exception;
 List<IplAllMatchesApiDto> getEntitiesByVenue(String venue)throws Exception;
 List<IplAllMatchesApiDto> getEntitiesByVenueAndStatus(String venue,Boolean status)throws Exception;
 List<IplAllMatchesApiDto> getEntitiesByTeamName(String teamName) throws Exception;
 List<IplAllMatchesApiDto> getEntitiesByTeamNameAndStatus(String teamName,Boolean status) throws Exception;
 List<IplAllMatchesApiDto> getEntitiesByTeamNameAndVenue(String shortName, String venue)throws Exception;
 List<IplAllMatchesApiDto> getEntitiesByTeamNameAndVenueAndStatus(Boolean status,String shortName, String venue)throws Exception;
 List<IplAllMatchesApiDto> getEntitiesAsPerUser(String status,String shortName, String venue) throws Exception;
 void scheduledMethod() throws Exception;
}
