package app.ecosynergy.api.unittests.mockito.services;

import app.ecosynergy.api.controllers.MQ7ReadingController;
import app.ecosynergy.api.data.vo.v1.MQ7ReadingVO;
import app.ecosynergy.api.models.MQ7Reading;
import app.ecosynergy.api.repositories.MQ7ReadingRepository;
import app.ecosynergy.api.services.MQ7ReadingServices;
import app.ecosynergy.api.unittests.mapper.mocks.MockMQ7Reading;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class MQ7ReadingServicesTest {
    MockMQ7Reading input;

    @InjectMocks
    private MQ7ReadingServices service;

    @Mock
    private MQ7ReadingRepository repository;
    
    @BeforeEach
    void setupMocks(){
        input = new MockMQ7Reading();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        MQ7Reading reading = input.mockEntity(1);

        when(repository.findById(reading.getId())).thenReturn(Optional.of(reading));
        MQ7ReadingVO result = service.findById(reading.getId());
        assertEquals("links: [</api/mq7reading/v1/1>;rel=\"self\"]", result.toString());
        assertEquals(1L, result.getKey());
        assertEquals(new Date(1), result.getDate());
        assertEquals(1, result.getValue());
    }

    @Test
    void findAll() {
        List<MQ7Reading> entityList = input.mockEntityList();

        when(repository.findAll()).thenReturn(entityList);

        List<MQ7ReadingVO> voList = service.findAll();

        voList.forEach(result -> {
            assertEquals("links: [</api/mq7reading/v1/" + result.getKey() + ">;rel=\"self\"]", result.toString());
            assertEquals(new Date(result.getKey().intValue()), result.getDate());
            assertEquals(result.getKey().intValue(), result.getValue());
        });
    }

    @Test
    void create() {
        MQ7Reading entity = input.mockEntity(1);

        MQ7Reading persisted = entity;
        persisted.setId(1L);

        MQ7ReadingVO vo = input.mockVO(1);

        when(repository.save(entity)).thenReturn(persisted);

        MQ7ReadingVO result = service.create(vo);
        assertEquals("links: [</api/mq7reading/v1/" + result.getKey() + ">;rel=\"self\"]", result.toString());
        assertEquals(new Date(1), result.getDate());
        assertEquals(1, result.getValue());
    }
}