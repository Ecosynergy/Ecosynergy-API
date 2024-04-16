package app.ecosynergy.api.unittests.mapper.mocks;

import app.ecosynergy.api.data.vo.v1.MQ135ReadingVO;
import app.ecosynergy.api.models.MQ135Reading;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockMQ135Reading {
    public MQ135Reading mockEntity(){
        return mockEntity(0);
    }

    public MQ135Reading mockEntity(Integer number){
        MQ135Reading entity = new MQ135Reading();
        entity.setId(number.longValue());
        entity.setDate(ZonedDateTime.now());
        entity.setValue(number.doubleValue());

        return entity;
    }

    public MQ135ReadingVO mockVO(){
        return mockVO(0);
    }

    public MQ135ReadingVO mockVO(Integer number){
        MQ135ReadingVO vo = new MQ135ReadingVO();
        vo.setKey(number.longValue());
        vo.setDate(ZonedDateTime.now());
        vo.setValue(number.doubleValue());

        return vo;
    }

    public List<MQ135Reading> mockEntityList(){
        List<MQ135Reading> entityList = new ArrayList<>();

        for(int i = 0; i < 14; i++){
            entityList.add(mockEntity(i));
        }

        return entityList;
    }
}
