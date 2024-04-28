package sportify.backend.api.mapper.iplBallByBall;

import org.springframework.stereotype.Component;
import sportify.backend.api.domain.iplBallByBall.IplBallByBallApi;
import sportify.backend.api.dto.iplBallByBall.IplBallByBallApiDto;

@Component
public class IplBallByBallApiMapper {

    public static IplBallByBallApi toEntty(IplBallByBallApiDto dto){
        IplBallByBallApi entity=new IplBallByBallApi();

        //Common Fields
        entity.setId(dto.getId());
        entity.setGuid(dto.getGuid());
        entity.setIsActive(dto.getIsActive());
        entity.setCreatedOn(dto.getCreatedOn());
        entity.setLastUpdatedOn(dto.getLastUpdatedOn());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setLastUpdatedBy(dto.getLastUpdatedBy());

        //Specified Fields
        entity.setMatchId(dto.getMatchId());
        entity.setName(dto.getName());
        entity.setMatchType(dto.getMatchType());
        entity.setStatus(dto.getStatus());
        entity.setVenue(dto.getVenue());
        entity.setDate(dto.getDate());
        entity.setDateTimeGmt(dto.getDateTimeGmt());
        entity.setTeamInfoList(dto.getTeamInfoList());
        entity.setScoreList(dto.getScoreList());
        entity.setTossWinner(dto.getTossWinner());
        entity.setTossChoice(dto.getTossChoice());
//        entity.setSeries_id(dto.getSeries_id());
        entity.setCommentary(dto.getCommentary());
        return entity;
    }

    public static IplBallByBallApiDto toDTO(IplBallByBallApi entity){
        IplBallByBallApiDto dto=new IplBallByBallApiDto();

        //Common Fields
        dto.setId(entity.getId());
        dto.setGuid(entity.getGuid());
        dto.setIsActive(entity.getIsActive());
        dto.setCreatedOn(entity.getCreatedOn());
        dto.setLastUpdatedOn(entity.getLastUpdatedOn());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setLastUpdatedBy(entity.getLastUpdatedBy());

        //Specified Fields
        dto.setMatchId(entity.getMatchId());
        dto.setName(entity.getName());
        dto.setMatchType(entity.getMatchType());
        dto.setStatus(entity.getStatus());
        dto.setVenue(entity.getVenue());
        dto.setDate(entity.getDate());
        dto.setDateTimeGmt(entity.getDateTimeGmt());
        dto.setTeamInfoList(entity.getTeamInfoList());
        dto.setScoreList(entity.getScoreList());
        dto.setTossWinner(entity.getTossWinner());
        dto.setTossChoice(entity.getTossChoice());
//        dto.setSeries_id(entity.getSeries_id());
        dto.setCommentary(entity.getCommentary());
        return dto;
    }

    public static IplBallByBallApiDto toUpdatedDTO(IplBallByBallApiDto currentDto,IplBallByBallApiDto newDto){
        if(newDto.getMatchId()!=null){
            currentDto.setMatchId(newDto.getMatchId());
        }
        if(newDto.getName()!=null){
            currentDto.setName(newDto.getName());
        }
        if(newDto.getMatchType()!=null){
            currentDto.setMatchType(newDto.getMatchType());
        }
        if(newDto.getStatus()!=null){
            currentDto.setStatus(newDto.getStatus());
        }
        if(newDto.getVenue()!=null){
         currentDto.setVenue(newDto.getVenue());
        }
        if(newDto.getDate()!=null){
            currentDto.setDate(newDto.getDate());
        }
        if(newDto.getDateTimeGmt()!=null){
            currentDto.setDateTimeGmt(newDto.getDateTimeGmt());
        }
        if(newDto.getTeamInfoList()!=null){
            currentDto.setTeamInfoList(newDto.getTeamInfoList());
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
//        if(newDto.getSeries_id()!=null){
//            currentDto.setSeries_id(newDto.getSeries_id());
//        }
        if(newDto.getCommentary()!=null){
            currentDto.setCommentary(newDto.getCommentary());
        }
        return currentDto;
    }
}
