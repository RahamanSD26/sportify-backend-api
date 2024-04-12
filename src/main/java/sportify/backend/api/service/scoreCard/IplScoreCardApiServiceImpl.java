package sportify.backend.api.service.scoreCard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sportify.backend.api.config.Constants;
import sportify.backend.api.domain.scoreCard.IplScoreCardApi;
import sportify.backend.api.dto.matches.IplAllMatchesApiDto;
import sportify.backend.api.dto.scoreCard.IplScoreCardApiDto;
import sportify.backend.api.mapper.scoreCard.IplScoreCardApiMapper;
import sportify.backend.api.repository.scoreCard.IplScoreCardApiRepository;
import sportify.backend.api.service.CricketDataService;
import sportify.backend.api.service.matches.IplAllMatchesApiService;
import util.JavaApiClass.iplscorecard.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class IplScoreCardApiServiceImpl implements IplScoreCardApiService {
    @Autowired
    CricketDataService cricketDataService;
    @Autowired
    IplAllMatchesApiService iplAllMatchesApiService;
    @Autowired
    IplScoreCardApiRepository iplScoreCardApiRepository;

    @Override
    public IplScoreCardApiDto createEntity(String time) throws Exception {
        IplAllMatchesApiDto match = iplAllMatchesApiService.getMatchByTime(time);

        if (match == null) {
            throw new Exception("Match not found");
        }

        IplScoreCard iplScoreCard = cricketDataService.fetchDataFromApi(IplScoreCard.class, match.getMatchId(), Constants.IPL_SCORE_CARD);

        IplScoreCardApiDto iplScoreCardApiDto = new IplScoreCardApiDto();
        Optional<IplScoreCardApiDto> iplScoreCardApiDtoOptional = iplScoreCardApiRepository.findByMatchId(iplScoreCard.getMatch().getId());

        // Checking existing ScoreCard
        if (iplScoreCardApiDtoOptional.isEmpty()) {
            iplScoreCardApiDto.setName(iplScoreCard.getMatch().getName());
            iplScoreCardApiDto.setVenue(iplScoreCard.getMatch().getVenue());
            iplScoreCardApiDto.setStatus(iplScoreCard.getStatus());
            iplScoreCardApiDto.setDateTimeGMT(iplScoreCard.getMatch().getDateTimeGMT());
            iplScoreCardApiDto.setTeamInfo(iplScoreCard.getMatch().getTeamInfo());
            iplScoreCardApiDto.setTossWinner(iplScoreCard.getMatch().getTossWinner());
            iplScoreCardApiDto.setTossChoice(iplScoreCard.getMatch().getTossChoice());
            iplScoreCardApiDto.setSeriesId(iplScoreCard.getMatch().getSeries_id());
            iplScoreCardApiDto.setMatchId(iplScoreCard.getMatch().getId());
            iplScoreCardApiDto.setIsMatchStarted(iplScoreCard.getMatch().isMatchStarted());
            iplScoreCardApiDto.setIsMatchEnded(iplScoreCard.getMatch().isMatchEnded());

            // Initialize scoreList as a map
            Map<String,Score> scoreMap = new HashMap<>();

            List<Score> scoreList = iplScoreCard.getMatch().getScore();
            if (scoreList != null) {
                // Accumulating details from scoreCardList
                for (Score score : scoreList) {
                    // Extracting inning from each scoreCard
                    String inning = score.getInning().replace("Inning 1","").trim();
                    scoreMap.put(inning,score);
                }
            }

            // Initialize scoreCardList as a map
            Map<String,ScoreCard> scoreCardMap=new HashMap<>();

            List<ScoreCard>scoreCardList= iplScoreCard.getMatch().getScorecard();
            if(scoreCardList != null){

                for(ScoreCard scoreCard:scoreCardList){
                    String inning=scoreCard.getInning().replace("Inning 1","").trim();
                    scoreCardMap.put(inning,scoreCard);
                }
            }

            iplScoreCardApiDto.setScoreList(scoreMap);
            iplScoreCardApiDto.setScoreCardList(scoreCardMap);
            return IplScoreCardApiMapper.toDTO(iplScoreCardApiRepository.save(IplScoreCardApiMapper.toEntity(iplScoreCardApiDto)));
        }
        throw new Exception("ScoreCard already created");
    }

    @Override
    public IplScoreCardApiDto getEntityById(String id) throws Exception {
        Optional<IplScoreCardApiDto> iplScoreCardApiDtoOptional=iplScoreCardApiRepository.findByMatchId(id);
        if(iplScoreCardApiDtoOptional.isEmpty()){
            throw new Exception("ScoreCard not found with the given id");
        }
       return iplScoreCardApiDtoOptional.get();
    }

    @Override
    public Page<IplScoreCardApiDto> getAllEntities(Pageable pageable) throws Exception {
        Page<IplScoreCardApi> entityPage= iplScoreCardApiRepository.findAll(pageable);
        if (entityPage.getContent().size() > 0) {
            return new PageImpl<>(entityPage.getContent().stream().map(IplScoreCardApiMapper::toDTO).collect(Collectors.toList()), pageable, entityPage.getTotalElements());
        } else {
            return new PageImpl<>(new ArrayList<>(), pageable, entityPage.getTotalElements());
        }
    }

}
