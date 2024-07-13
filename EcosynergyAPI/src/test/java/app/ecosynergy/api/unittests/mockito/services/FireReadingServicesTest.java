package app.ecosynergy.api.unittests.mockito.services;

import app.ecosynergy.api.data.vo.v1.FireReadingVO;
import app.ecosynergy.api.exceptions.RequiredObjectIsNullException;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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

        assertNotNull(result.getTimestamp());
        assertEquals("links: [</api/fireReading/v1/1>;rel=\"self\"]", result.toString());
        assertEquals(1L, result.getKey());
        assertFalse(result.getFire());
    }

    @Test
    void create() {
        FireReading entity = input.mockEntity(1);
        entity.setId(1L);

        FireReadingVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.save(any(FireReading.class))).thenReturn(entity);

        FireReadingVO result = service.create(vo, ZoneId.systemDefault());

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getFire());
        assertNotNull(result.getTimestamp());

        assertEquals("links: [</api/fireReading/v1/" + result.getKey() + ">;rel=\"self\"]", result.toString());
        assertFalse(result.getFire());
    }

    @Test
    void createWithNullFireReading() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> service.create(null, ZoneId.of("America/Sao_Paulo")));

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}