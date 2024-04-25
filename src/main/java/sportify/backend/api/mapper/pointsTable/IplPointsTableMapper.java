package sportify.backend.api.mapper.pointsTable;

import sportify.backend.api.domain.pointsTable.IplPointsTable;
import sportify.backend.api.dto.pointsTable.IplPointsTableDto;

public class IplPointsTableMapper {

    public static IplPointsTable toEntity(IplPointsTableDto dto){
        IplPointsTable entity=new IplPointsTable();

        //Common Fields
        entity.setId(dto.getId());
        entity.setGuid(dto.getGuid());
        entity.setIsActive(dto.getIsActive());
        entity.setCreatedOn(dto.getCreatedOn());
        entity.setLastUpdatedOn(dto.getLastUpdatedOn());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setLastUpdatedBy(dto.getLastUpdatedBy());

        //Specified Fields
        entity.setPos(dto.getPos());
        entity.setTeam(dto.getTeam());
        entity.setP(dto.getP());
        entity.setW(dto.getW());
        entity.setL(dto.getL());
        entity.setNR(dto.getNR());
        entity.setNRR(dto.getNRR());
        entity.setPTS(dto.getPTS());
        entity.setFOR(dto.getFOR());
        entity.setAGAINST(dto.getAGAINST());
        return entity;
    }

    public static IplPointsTableDto toDTO(IplPointsTable entity){
        IplPointsTableDto dto=new IplPointsTableDto();

        //Common Fields
        entity.setId(dto.getId());
        entity.setGuid(dto.getGuid());
        entity.setIsActive(dto.getIsActive());
        entity.setCreatedOn(dto.getCreatedOn());
        entity.setLastUpdatedOn(dto.getLastUpdatedOn());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setLastUpdatedBy(dto.getLastUpdatedBy());

        //Specified Fields
        dto.setPos(entity.getPos());
        dto.setTeam(entity.getTeam());
        dto.setP(entity.getP());
        dto.setW(entity.getW());
        dto.setL(entity.getL());
        dto.setNR(entity.getNR());
        dto.setNRR(entity.getNRR());
        dto.setPTS(entity.getPTS());
        dto.setFOR(entity.getFOR());
        dto.setAGAINST(entity.getAGAINST());
        return dto;
    }

    public static IplPointsTableDto toUpdatedDTO(IplPointsTableDto currentDto, IplPointsTableDto newDto){
        if(newDto.getPos()!=null){
            currentDto.setPos(newDto.getPos());
        }
        if(newDto.getTeam()!=null){
            currentDto.setTeam(newDto.getTeam());
        }
        if(newDto.getP()!=null){
            currentDto.setP(newDto.getP());
        }
        if(newDto.getW()!=null){
            currentDto.setW(newDto.getW());
        }
        if(newDto.getL()!=null){
            currentDto.setL(newDto.getL());
        }
        if(newDto.getNR()!=null){
            currentDto.setNR(newDto.getNR());
        }
        if(newDto.getNRR()!=null){
            currentDto.setNRR(newDto.getNRR());
        }
        if(newDto.getPTS()!=null){
            currentDto.setPTS(newDto.getPTS());
        }
        if(newDto.getFOR()!=null){
            currentDto.setFOR(newDto.getFOR());
        }
        if(newDto.getAGAINST()!=null){
            currentDto.setAGAINST(newDto.getAGAINST());
        }
        return currentDto;
    }
}
