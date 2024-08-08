package app.ecosynergy.api.unittests.mapper.mocks;

import app.ecosynergy.api.data.vo.v1.MQ7ReadingVO;
import app.ecosynergy.api.models.MQ7Reading;
import app.ecosynergy.api.models.Team;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class MockMQ7Reading {
    public MQ7Reading mockEntity(){
        return mockEntity(0);
    }

    public MQ7Reading mockEntity(Integer number){
        Team team = new Team();
        team.setHandle("ecosynergyofc");
        team.setTimeZone(ZoneId.of("UTC"));

        MQ7Reading entity = new MQ7Reading();
        entity.setId(number.longValue());
        entity.setTeam(team);
        entity.setTimestamp(ZonedDateTime.now());
        entity.setValue(number.doubleValue());

        return entity;
    }

    public MQ7ReadingVO mockVO(){
        return mockVO(0);
    }

    public MQ7ReadingVO mockVO(Integer number){
        MQ7ReadingVO vo = new MQ7ReadingVO();
        vo.setKey(number.longValue());
        vo.setTeamHandle("ecosynergyofc");
        vo.setTimestamp(ZonedDateTime.now());
        vo.setValue(number.doubleValue());

        return vo;
    }

    public List<MQ7Reading> mockEntityList(){
        List<MQ7Reading> entityList = new ArrayList<>();

        for(int i = 0; i < 14; i++){
            entityList.add(mockEntity(i));
        }

        return entityList;
    }
}
