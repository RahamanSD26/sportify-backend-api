package sportify.backend.api.mapper.scoreCard;

import org.springframework.stereotype.Component;
import sportify.backend.api.domain.scoreCard.IplScoreCardApi;
import sportify.backend.api.dto.scoreCard.IplScoreCardApiDto;
@Component
public class IplScoreCardApiMapper {
    public static IplScoreCardApi toEntity(IplScoreCardApiDto dto){
        IplScoreCardApi entity=new IplScoreCardApi();

        //Common Fields
        entity.setId(dto.getId());
        entity.setGuid(dto.getGuid());
        entity.setIsActive(dto.getIsActive());
        entity.setCreatedOn(dto.getCreatedOn());
        entity.setLastUpdatedOn(dto.getLastUpdatedOn());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setLastUpdatedBy(dto.getLastUpdatedBy());

        //Specified Fields
        entity.setName(dto.getName());
        entity.setStatus(dto.getStatus());
        entity.setVenue(dto.getVenue());
        entity.setDateTimeGMT(dto.getDateTimeGMT());
        entity.setTeamInfo(dto.getTeamInfo());
        entity.setScoreList(dto.getScoreList());
        entity.setTossWinner(dto.getTossWinner());
        entity.setTossChoice(dto.getTossChoice());
        entity.setSeriesId(dto.getSeriesId());
        entity.setBatting(dto.getBatting());
        entity.setBowling(dto.getBowling());
        entity.setCatching(dto.getCatching());
        entity.setExtras(dto.getExtras());
//        entity.setTotals(dto.getTotals());
        entity.setInning(dto.getInning());
        entity.setMatchId(dto.getMatchId());
        return entity;
    }

    public static IplScoreCardApiDto toDTO(IplScoreCardApi entity){
        IplScoreCardApiDto dto=new IplScoreCardApiDto();

        //Common Fields
        dto.setId(entity.getId());
        dto.setGuid(entity.getGuid());
        dto.setIsActive(entity.getIsActive());
        dto.setCreatedOn(entity.getCreatedOn());
        dto.setLastUpdatedOn(entity.getLastUpdatedOn());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setLastUpdatedBy(entity.getLastUpdatedBy());

        //Specified Fields
        dto.setName(entity.getName());
        dto.setStatus(entity.getStatus());
        dto.setVenue(entity.getVenue());
        dto.setDateTimeGMT(entity.getDateTimeGMT());
        dto.setTeamInfo(entity.getTeamInfo());
        dto.setScoreList(entity.getScoreList());
        dto.setTossWinner(entity.getTossWinner());
        dto.setTossChoice(entity.getTossChoice());
        dto.setSeriesId(entity.getSeriesId());
        dto.setBatting(entity.getBatting());
        dto.setBowling(entity.getBowling());
        dto.setCatching(entity.getCatching());
        dto.setExtras(entity.getExtras());
//        dto.setTotals(entity.getTotals());
        dto.setInning(entity.getInning());
        dto.setMatchId(entity.getMatchId());
        return dto;
    }

    public static IplScoreCardApiDto toUpdatedDTO(IplScoreCardApiDto currentDto, IplScoreCardApiDto newDto){
        if(newDto.getName()!=null){
            currentDto.setName(newDto.getName());
        }
        if(newDto.getMatchId()!=null){
            currentDto.setMatchId(newDto.getMatchId());
        }
        if(newDto.getStatus()!=null){
            currentDto.setStatus(newDto.getStatus());
        }
        if(newDto.getVenue()!=null){
            currentDto.setVenue(newDto.getVenue());
        }
        if(newDto.getDateTimeGMT()!=null){
            currentDto.setDateTimeGMT(newDto.getDateTimeGMT());
        }
        if(newDto.getTeamInfo()!=null){
            currentDto.setTeamInfo(newDto.getTeamInfo());
        }
        if(newDto.getScoreList()!=null){
            currentDto.setScoreList(newDto.getScoreList());
        }
        if(newDto.getTossWinner()!=null){
            currentDto.setTossWinner(newDto.getTossWinner());
        }
        if(newDto.getTossChoice()!=null){
            currentDto.setTossChoice(newDto.getTossChoice());
        }
        if(newDto.getSeriesId()!=null){
            currentDto.setSeriesId(newDto.getSeriesId());
        }
        if(newDto.getBatting()!=null){
            currentDto.setBatting(newDto.getBatting());
        }
        if(newDto.getBowling()!=null){
            currentDto.setBowling(newDto.getBowling());
        }
        if(newDto.getCatching()!=null){
            currentDto.setCatching(newDto.getCatching());
        }
        if(newDto.getExtras()!=null){
            currentDto.setExtras(newDto.getExtras());
        }
//        if(newDto.getTotals()!=null){
//            currentDto.setExtras(newDto.getExtras());
//        }
        if(newDto.getInning()!=null){
            currentDto.setInning(newDto.getInning());
        }
        return currentDto;
    }
}
