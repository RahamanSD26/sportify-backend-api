package sportify.backend.api.mapper.squad;


import sportify.backend.api.domain.squad.IplSquadApi;
import sportify.backend.api.dto.squad.IplSquadApiDto;

public class IplSquadApiMapper {

    public static IplSquadApi toEntity(IplSquadApiDto dto){
        IplSquadApi entity=new IplSquadApi();

        //Common Fields
        entity.setId(dto.getId());
        entity.setGuid(dto.getGuid());
        entity.setIsActive(dto.getIsActive());
        entity.setCreatedOn(dto.getCreatedOn());
        entity.setLastUpdatedOn(dto.getLastUpdatedOn());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setLastUpdatedBy(dto.getLastUpdatedBy());

//        //Specified Fields
//        entity.setImage(dto.getImage());
//        entity.setTeamName(dto.getTeamName());
//        entity.setShortName(dto.getShortName());
//        entity.setPlayersList(dto.getPlayersList());
        entity.setTeamList(dto.getTeamList());
        entity.setMatchId(dto.getMatchId());
        return entity;
    }

    public static IplSquadApiDto toDTO(IplSquadApi entity){
        IplSquadApiDto dto=new IplSquadApiDto();
        //Common Fields
        dto.setId(entity.getId());
        dto.setGuid(entity.getGuid());
        dto.setIsActive(entity.getIsActive());
        dto.setCreatedOn(entity.getCreatedOn());
        dto.setLastUpdatedOn(entity.getLastUpdatedOn());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setLastUpdatedBy(entity.getLastUpdatedBy());

        //Specified Fields
//        dto.setImage(entity.getImage());
//        dto.setShortName(entity.getShortName());
//        dto.setTeamName(dto.getTeamName());
//        dto.setPlayersList(entity.getPlayersList());
        dto.setTeamList(entity.getTeamList());
        dto.setMatchId(entity.getMatchId());
        return dto;
    }

    public static IplSquadApiDto toUpdatedDTO(IplSquadApiDto currentDto, IplSquadApiDto newDto){
//        if(newDto.getImage()!=null){
//            currentDto.setImage(newDto.getImage());
//        }
//        if(newDto.getTeamName()!=null){
//            currentDto.setTeamName(newDto.getTeamName());
//        }
//        if(newDto.getShortName()!=null){
//            currentDto.setShortName(newDto.getShortName());
//        }
//        if(newDto.getPlayersList()!=null){
//            currentDto.setPlayersList(newDto.getPlayersList());
//        }
        if(newDto.getTeamList()!=null){
            currentDto.setTeamList(newDto.getTeamList());
        }
        if(newDto.getMatchId()!=null){
            currentDto.setMatchId(newDto.getMatchId());
        }
        return currentDto;
    }
}
