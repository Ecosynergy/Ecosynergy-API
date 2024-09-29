package app.ecosynergy.api.unittests.mockito.services;

import app.ecosynergy.api.data.vo.v1.MQ135ReadingVO;
import app.ecosynergy.api.data.vo.v1.TeamVO;
import app.ecosynergy.api.exceptions.RequiredObjectIsNullException;
import app.ecosynergy.api.mapper.DozerMapper;
import app.ecosynergy.api.models.MQ135Reading;
import app.ecosynergy.api.repositories.MQ135ReadingRepository;
import app.ecosynergy.api.services.MQ135ReadingService;
import app.ecosynergy.api.services.TeamService;
import app.ecosynergy.api.unittests.mapper.mocks.MockMQ135Reading;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class MQ135ReadingServiceTest {
    MockMQ135Reading input;

    @InjectMocks
    private MQ135ReadingService service;

    @Mock
    private TeamService teamService;

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

        when(teamService.findByHandle(any(String.class))).thenReturn(DozerMapper.parseObject(reading.getTeam(), TeamVO.class));
        when(repository.findByIdWithTeam(reading.getId())).thenReturn(Optional.of(reading));
        MQ135ReadingVO result = service.findById(reading.getId());

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getTeamHandle());
        assertNotNull(result.getTimestamp());
        assertNotNull(result.getValue());

        assertEquals("links: [</api/mq135Reading/v1/1>;rel=\"self\"]", result.toString());
        assertEquals(1L, result.getKey());
        assertEquals(1, result.getValue());
    }

    @Test
    void create() {
        MQ135Reading entity = input.mockEntity(1);

        MQ135ReadingVO vo = input.mockVO(1);

        when(teamService.findByHandle(any(String.class))).thenReturn(DozerMapper.parseObject(entity.getTeam(), TeamVO.class));
        when(repository.save(any(MQ135Reading.class))).thenReturn(entity);

        MQ135ReadingVO result = service.create(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getTeamHandle());
        assertNotNull(result.getTimestamp());
        assertNotNull(result.getValue());

        assertEquals("links: [</api/mq135Reading/v1/" + result.getKey() + ">;rel=\"self\"]", result.toString());
        assertEquals(1, result.getValue());
    }

    @Test
    void createWithNullMQ135Reading() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> service.create(null));

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}