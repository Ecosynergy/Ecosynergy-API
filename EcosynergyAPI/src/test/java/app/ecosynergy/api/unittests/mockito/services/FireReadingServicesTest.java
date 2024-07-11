package app.ecosynergy.api.unittests.mockito.services;

import app.ecosynergy.api.data.vo.v1.FireReadingVO;
import app.ecosynergy.api.models.FireReading;
import app.ecosynergy.api.repositories.FireReadingRepository;
import app.ecosynergy.api.services.FireReadingServices;
import app.ecosynergy.api.unittests.mapper.mocks.MockFireReading;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class FireReadingServicesTest {
    MockFireReading input;

    @InjectMocks
    private FireReadingServices service;

    @Mock
    private FireReadingRepository repository;
    
    @BeforeEach
    void setupMocks(){
        input = new MockFireReading();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        FireReading reading = input.mockEntity(1);

        when(repository.findById(reading.getId())).thenReturn(Optional.of(reading));
        FireReadingVO result = service.findById(reading.getId(), ZoneId.systemDefault());
        assertEquals("links: [</api/firereading/v1/1>;rel=\"self\"]", result.toString());
        assertEquals(1L, result.getKey());
        assertEquals(new Date(1), result.getTimestamp());
        assertEquals(false, result.getFire());
    }

//    @Test
//    void findAll() {
//        List<FireReading> entityList = input.mockEntityList();
//
//        when(repository.findAll()).thenReturn(entityList);
//
//        List<FireReadingVO> voList = service.findAll();
//
//        voList.forEach(result -> {
//            assertEquals("links: [</api/firereading/v1/" + result.getKey() + ">;rel=\"self\"]", result.toString());
//            assertEquals(new Date(result.getKey().intValue()), result.getTimestamp());
//            assertEquals(result.getKey().intValue() % 2 == 0, result.getFire());
//        });
//    }

    @Test
    void create() {
        FireReading entity = input.mockEntity(1);

        FireReading persisted = entity;
        persisted.setId(1L);

        FireReadingVO vo = input.mockVO(1);

        when(repository.save(entity)).thenReturn(persisted);

        FireReadingVO result = service.create(vo, ZoneId.systemDefault());
        assertEquals("links: [</api/firereading/v1/" + result.getKey() + ">;rel=\"self\"]", result.toString());
        assertEquals(new Date(1), result.getTimestamp());
        assertEquals(false, result.getFire());
    }
}