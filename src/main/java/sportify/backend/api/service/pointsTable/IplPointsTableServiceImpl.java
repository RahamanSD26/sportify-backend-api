package sportify.backend.api.service.pointsTable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sportify.backend.api.dto.matches.IplAllMatchesApiDto;
import sportify.backend.api.dto.pointsTable.IplPointsTableDto;
import sportify.backend.api.dto.scoreCard.IplScoreCardApiDto;
import sportify.backend.api.mapper.pointsTable.IplPointsTableMapper;
import sportify.backend.api.repository.pointsTable.PointsTableRepository;
import sportify.backend.api.service.matches.IplAllMatchesApiService;
import sportify.backend.api.service.scoreCard.IplScoreCardApiService;
import sportify.backend.api.util.JavaApiClass.iplscorecard.Score;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class IplPointsTableServiceImpl implements IplPointsTableService{
    @Autowired
    IplAllMatchesApiService iplAllMatchesApiService;
    @Autowired
    IplScoreCardApiService iplScoreCardApiService;
    @Autowired
    PointsTableRepository pointsTableRepository;

    @Override
    public IplPointsTableDto createEntity(String teamName)throws Exception {
        List<IplAllMatchesApiDto> iplAllMatchesApiDtoList = iplAllMatchesApiService.getEntitiesByTeamNameAndStatus(teamName, false);

        if (!iplAllMatchesApiDtoList.isEmpty()) {
            int p = 0;
            int w = 0;
            int l = 0;
            int totalrunsScored = 0;
            int totalRunsConceded = 0;
            double totalOverFaced = 0;
            double totalOverBowled = 0;
            for (IplAllMatchesApiDto iplAllMatchesApiDto : iplAllMatchesApiDtoList) {
                p++;
                String matchId = iplAllMatchesApiDto.getMatchId();
                IplScoreCardApiDto iplScoreCardApiDto = iplScoreCardApiService.getEntityById(matchId);
                String teamFullName;
                String OppTeamFullName;

                if (iplAllMatchesApiDto.getTeamInfo().get(0).getShortname().equals(teamName)) {
                    teamFullName = iplAllMatchesApiDto.getTeamInfo().get(0).getName();
                    OppTeamFullName = iplAllMatchesApiDto.getTeamInfo().get(1).getName();
                } else {
                    teamFullName = iplAllMatchesApiDto.getTeamInfo().get(1).getName();
                    OppTeamFullName = iplAllMatchesApiDto.getTeamInfo().get(0).getName();
                }

                if (iplAllMatchesApiDto.getStatus().contains(teamFullName)) w++;
                else l++;

                Score teamScore = iplScoreCardApiDto.getScoreList().get(teamFullName);
                Score OppTeamScore = iplScoreCardApiDto.getScoreList().get(OppTeamFullName);

                totalrunsScored += (double) teamScore.getR();
                totalRunsConceded += (double) OppTeamScore.getR();
                totalOverFaced += (double) teamScore.getO();
                totalOverBowled += (double) OppTeamScore.getO();
            }

            // Calculate Run Rate
            double runRate = (long) totalrunsScored / totalOverFaced;

            // Calculate Opposition Run Rate
            double oppositionRunRate = (double) totalRunsConceded / totalOverBowled;

            // Calculate Net Run Rate
            double netRunRate = runRate - oppositionRunRate;

            Optional<IplPointsTableDto> existingRow=pointsTableRepository.findByTeam(teamName);

            IplPointsTableDto iplPointsTableDto;
            if(existingRow.isPresent()){
                iplPointsTableDto=existingRow.get();
            }else{
                iplPointsTableDto=new IplPointsTableDto();
            }
            iplPointsTableDto.setTeam(teamName);
            iplPointsTableDto.setP(String.valueOf(p));
            iplPointsTableDto.setW(String.valueOf(w));
            iplPointsTableDto.setL(String.valueOf(l));
            iplPointsTableDto.setNR(0);
            iplPointsTableDto.setNRR(Float.valueOf(String.valueOf(netRunRate)));
            iplPointsTableDto.setFOR(totalrunsScored + "/" + totalOverFaced);
            iplPointsTableDto.setAGAINST(totalRunsConceded + "/" + totalOverBowled);
            iplPointsTableDto.setPTS(w * 2);

            return IplPointsTableMapper.toDTO(pointsTableRepository.save(IplPointsTableMapper.toEntity(iplPointsTableDto)));
        }
        throw new Exception("Matches not found with the given team name");
    }
    @Override
    public List<IplPointsTableDto> getEntitiesAsPerOrder() throws Exception {
        List<IplPointsTableDto> iplPointsTableDtos=pointsTableRepository.findAllSortedByPTSAndNRR();
        if(iplPointsTableDtos.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        return iplPointsTableDtos;
    }
}

