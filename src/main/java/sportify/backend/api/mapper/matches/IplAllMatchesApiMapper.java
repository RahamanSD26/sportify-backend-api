package sportify.backend.api.mapper.matches;

import org.springframework.stereotype.Component;
import sportify.backend.api.domain.matches.IplAllMatchesApi;
import sportify.backend.api.dto.matches.IplAllMatchesApiDto;
@Component
public class IplAllMatchesApiMapper {

    public static IplAllMatchesApi toEntity(IplAllMatchesApiDto dto){

        IplAllMatchesApi entity=new IplAllMatchesApi();

        //Common Fields
        entity.setId(dto.getId());
        entity.setGuid(dto.getGuid());
        entity.setIsActive(dto.getIsActive());
        entity.setCreatedOn(dto.getCreatedOn());
        entity.setLastUpdatedOn(dto.getLastUpdatedOn());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setLastUpdatedBy(dto.getLastUpdatedBy());

        //Specified Fields
        entity.setVenue(dto.getVenue());
        entity.setDate(dto.getDate());
        entity.setTime(dto.getTime());
        entity.setTeamsName(dto.getTeamsName());
        entity.setMatchNumber(dto.getMatchNumber());
        entity.setTeamInfo(dto.getTeamInfo());
        entity.setMatchId(dto.getMatchId());
        entity.setStatus(dto.getStatus());
        entity.setIntMatchNumber(dto.getIntMatchNumber());
        return entity;
    }

    public static IplAllMatchesApiDto toDTO(IplAllMatchesApi entity){

        IplAllMatchesApiDto dto=new IplAllMatchesApiDto();
        //Common Fields
        dto.setId(entity.getId());
        dto.setGuid(entity.getGuid());
        dto.setIsActive(entity.getIsActive());
        dto.setCreatedOn(entity.getCreatedOn());
        dto.setLastUpdatedOn(entity.getLastUpdatedOn());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setLastUpdatedBy(entity.getLastUpdatedBy());

        //Specified Fields
        dto.setVenue(entity.getVenue());
        dto.setDate(entity.getDate());
        dto.setTime(entity.getTime());
        dto.setTeamsName(entity.getTeamsName());
        dto.setMatchNumber(entity.getMatchNumber());
        dto.setTeamInfo(entity.getTeamInfo());
        dto.setMatchId(entity.getMatchId());
        dto.setStatus(entity.getStatus());
        dto.setIntMatchNumber(entity.getIntMatchNumber());
        return dto;
    }

    public static IplAllMatchesApiDto toUpdatedDTo(IplAllMatchesApiDto currentDto, IplAllMatchesApiDto newDto){
        if(newDto.getVenue()!=null){
            currentDto.setVenue(newDto.getVenue());
        }
        if(newDto.getDate()!=null){
            currentDto.setDate(newDto.getDate());
        }
        if(newDto.getTime()!=null){
            currentDto.setTeamsName(newDto.getTeamsName());
        }
        if(newDto.getTeamsName()!=null) {
            currentDto.setTeamsName(newDto.getTeamsName());
        }
        if(newDto.getMatchNumber()!=null){
            currentDto.setMatchNumber(newDto.getMatchNumber());
        }
        if(newDto.getTeamInfo()!=null){
            currentDto.setTeamInfo(newDto.getTeamInfo());
        }
        if(newDto.getMatchId()!=null){
            currentDto.setMatchId(newDto.getMatchId());
        }
        if(newDto.getStatus()!=null){
            currentDto.setStatus(newDto.getStatus());
        }
        if(newDto.getIntMatchNumber()!=null){
            currentDto.setIntMatchNumber(newDto.getIntMatchNumber());
        }
             return currentDto;
    }

}
