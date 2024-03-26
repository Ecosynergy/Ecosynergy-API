package app.ecosynergy.api.unittests.mapper.mocks;

import app.ecosynergy.api.data.vo.v1.FireReadingVO;
import app.ecosynergy.api.models.FireReading;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockFireReading {
    public FireReading mockEntity(){
        return mockEntity(0);
    }

    public FireReading mockEntity(Integer number){
        FireReading entity = new FireReading();
        entity.setId(number.longValue());
        entity.setDate(new Date(number));
        entity.setFire(number % 2 == 0);

        return entity;
    }

    public FireReadingVO mockVO(){
        return mockVO(0);
    }

    public FireReadingVO mockVO(Integer number){
        FireReadingVO vo = new FireReadingVO();
        vo.setKey(number.longValue());
        vo.setDate(new Date(number));
        vo.setFire(number % 2 == 0);

        return vo;
    }

    public List<FireReading> mockEntityList(){
        List<FireReading> entityList = new ArrayList<>();

        for(int i = 0; i < 14; i++){
            entityList.add(mockEntity(i));
        }

        return entityList;
    }
}
