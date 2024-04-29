package sportify.backend.api.service.squad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sportify.backend.api.config.Constants;
import sportify.backend.api.dto.squad.IplSquadApiDto;
import sportify.backend.api.mapper.squad.IplSquadApiMapper;
import sportify.backend.api.repository.squad.IplSquadApiRepository;
import sportify.backend.api.service.CricketDataService;
import sportify.backend.api.util.JavaApiClass.iplSquad.IplSquad;
import sportify.backend.api.util.JavaApiClass.iplSquad.Team;

import java.util.List;
import java.util.Optional;

@Service
public class IplSquadApiServiceImpl implements IplSquadApiService{
    @Autowired
    CricketDataService cricketDataService;
    @Autowired
    IplSquadApiRepository iplSquadApiRepository;

    @Override
    public IplSquadApiDto createEntityById(String id)throws Exception {

        if(getEntityById(id)==null) {
            IplSquad iplSquad =cricketDataService.fetchDataFromApi(IplSquad.class,id, Constants.IPL_MATCH_SQUAD);
            cricketDataService.setCount(iplSquad.getInfo().getHitsUsed());
            if(iplSquad.getData()==null) throw new Exception("Did not Get data from CrickData");
            List<Team> teamList = iplSquad.getData();
            IplSquadApiDto iplSquadApiDto = new IplSquadApiDto();
            iplSquadApiDto.setTeamList(teamList);
            iplSquadApiDto.setMatchId(id);
            return IplSquadApiMapper.toDTO(iplSquadApiRepository.save(IplSquadApiMapper.toEntity(iplSquadApiDto)));
        }
       throw new Exception("Squad is already created with given Match_ID");
    }

    @Override
    public IplSquadApiDto getEntityById(String id) throws Exception {
        Optional<IplSquadApiDto> iplSquadApiDtoOptional=iplSquadApiRepository.findByMatchId(id);
        if(iplSquadApiDtoOptional.isEmpty()){
           return null;
        }
        return iplSquadApiDtoOptional.get();
    }
}
