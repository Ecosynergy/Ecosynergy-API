package app.ecosynergy.api.unittests.mockito.services;

import app.ecosynergy.api.data.vo.v1.FireReadingVO;
import app.ecosynergy.api.data.vo.v1.TeamVO;
import app.ecosynergy.api.exceptions.RequiredObjectIsNullException;
import app.ecosynergy.api.mapper.DozerMapper;
import app.ecosynergy.api.models.FireReading;
import app.ecosynergy.api.repositories.FireReadingRepository;
import app.ecosynergy.api.services.FireReadingService;
import app.ecosynergy.api.services.TeamService;
import app.ecosynergy.api.unittests.mapper.mocks.MockFireReading;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class FireReadingServiceTest {
    MockFireReading input;

    @InjectMocks
    private FireReadingService service;

    @Mock
    private TeamService teamService;

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

        when(teamService.findByHandle(any(String.class))).thenReturn(DozerMapper.parseObject(reading.getTeam(), TeamVO.class));
        when(repository.findByIdWithTeam(reading.getId())).thenReturn(Optional.of(reading));

        FireReadingVO result = service.findById(reading.getId());

        assertNotNull(result.getTeamHandle());
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

        when(teamService.findByHandle(any(String.class))).thenReturn(DozerMapper.parseObject(entity.getTeam(), TeamVO.class));
        when(repository.save(any(FireReading.class))).thenReturn(entity);

        FireReadingVO result = service.create(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getTeamHandle());
        assertNotNull(result.getFire());
        assertNotNull(result.getTimestamp());

        assertEquals("links: [</api/fireReading/v1/" + result.getKey() + ">;rel=\"self\"]", result.toString());
        assertFalse(result.getFire());
    }

    @Test
    void createWithNullFireReading() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> service.create(null));

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}