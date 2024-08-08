package app.ecosynergy.api.unittests.mapper.mocks;

import app.ecosynergy.api.data.vo.v1.TeamVO;
import app.ecosynergy.api.models.Team;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class MockTeam {
    public Team mockEntity(){
        return mockEntity(0);
    }

    public TeamVO mockTeamVO(){
        return mockTeamVO(0);
    }

    public Team mockEntity(Integer number){
        Team entity = new Team();
        entity.setId(number.longValue());
        entity.setHandle("team" + number);
        entity.setName("Team" + number);
        entity.setDescription("");
        entity.setTimeZone(ZoneId.of("America/Sao_Paulo"));
        entity.setCreatedAt(ZonedDateTime.now());
        entity.setUpdatedAt(ZonedDateTime.now());

        return entity;
    }

    public TeamVO mockTeamVO(Integer number){
        TeamVO entity = new TeamVO();
        entity.setKey(number.longValue());
        entity.setHandle("team" + number);
        entity.setName("Team" + number);
        entity.setDescription("");
        entity.setTimeZone(ZoneId.of("America/Sao_Paulo"));
        entity.setCreatedAt(ZonedDateTime.now());
        entity.setUpdatedAt(ZonedDateTime.now());

        return entity;
    }

    public List<Team> mockEntityList(){
        List<Team> entityList = new ArrayList<>();
        for(int i = 0; i < 14; i++){
            entityList.add(mockEntity(i));
        }

        return entityList;
    }

    public List<TeamVO> mockVOList() {
        List<TeamVO> voList = new ArrayList<>();
        for(int i = 0; i < 14; i++){
            voList.add(mockTeamVO(i));
        }

        return voList;
    }
}
