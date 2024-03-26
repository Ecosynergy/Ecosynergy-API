package app.ecosynergy.api.unittests.mockito.services;

import app.ecosynergy.api.data.vo.v1.MQ135ReadingVO;
import app.ecosynergy.api.models.MQ135Reading;
import app.ecosynergy.api.repositories.MQ135ReadingRepository;
import app.ecosynergy.api.services.MQ135ReadingServices;
import app.ecosynergy.api.unittests.mapper.mocks.MockMQ135Reading;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class MQ135ReadingServicesTest {
    MockMQ135Reading input;

    @InjectMocks
    private MQ135ReadingServices service;

    @Mock
    private MQ135ReadingRepository repository;
    
    @BeforeEach
    void setupMocks(){
        input = new MockMQ135Reading();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        MQ135Reading reading = input.mockEntity(1);

        when(repository.findById(reading.getId())).thenReturn(Optional.of(reading));
        MQ135ReadingVO result = service.findById(reading.getId());
        assertEquals("links: [</api/mq135reading/v1/1>;rel=\"self\"]", result.toString());
        assertEquals(1L, result.getKey());
        assertEquals(new Date(1), result.getDate());
        assertEquals(1, result.getValue());
    }

    @Test
    void findAll() {
        List<MQ135Reading> entityList = input.mockEntityList();

        when(repository.findAll()).thenReturn(entityList);

        List<MQ135ReadingVO> voList = service.findAll();

        voList.forEach(result -> {
            assertEquals("links: [</api/mq135reading/v1/" + result.getKey() + ">;rel=\"self\"]", result.toString());
            assertEquals(new Date(result.getKey().intValue()), result.getDate());
            assertEquals(result.getKey().intValue(), result.getValue());
        });
    }

    @Test
    void create() {
        MQ135Reading entity = input.mockEntity(1);

        MQ135Reading persisted = entity;
        persisted.setId(1L);

        MQ135ReadingVO vo = input.mockVO(1);

        when(repository.save(entity)).thenReturn(persisted);

        MQ135ReadingVO result = service.create(vo);
        assertEquals("links: [</api/mq135reading/v1/" + result.getKey() + ">;rel=\"self\"]", result.toString());
        assertEquals(new Date(1), result.getDate());
        assertEquals(1, result.getValue());
    }
}