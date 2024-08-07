package app.ecosynergy.api.unittests.mockito.services;

import app.ecosynergy.api.data.vo.v1.MQ7ReadingVO;
import app.ecosynergy.api.data.vo.v1.TeamVO;
import app.ecosynergy.api.exceptions.RequiredObjectIsNullException;
import app.ecosynergy.api.mapper.DozerMapper;
import app.ecosynergy.api.models.MQ7Reading;
import app.ecosynergy.api.repositories.MQ7ReadingRepository;
import app.ecosynergy.api.services.MQ7ReadingServices;
import app.ecosynergy.api.services.TeamService;
import app.ecosynergy.api.services.UserServices;
import app.ecosynergy.api.unittests.mapper.mocks.MockMQ7Reading;
import app.ecosynergy.api.unittests.mapper.mocks.MockUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class MQ7ReadingServicesTest {
    MockMQ7Reading input;

    MockUser userInput;

    @InjectMocks
    private MQ7ReadingServices service;

    @Mock
    private TeamService teamService;

    @Mock
    private UserServices userServices;

    @Mock
    private MQ7ReadingRepository repository;
    
    @BeforeEach
    void setupMocks() {
        input = new MockMQ7Reading();
        userInput = new MockUser();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        MQ7Reading reading = input.mockEntity(1);

        when(userServices.findByAccessToken(any(String.class))).thenReturn(userInput.mockUserVO());
        when(repository.findByIdWithTeam(reading.getId())).thenReturn(Optional.of(reading));

        MQ7ReadingVO result = service.findById(reading.getId(), "");
        assertEquals("links: [</api/mq7Reading/v1/1>;rel=\"self\"]", result.toString());
        assertEquals(1L, result.getKey());
        assertNotNull(result.getTeamHandle());
        assertNotNull(result.getTimestamp());
        assertEquals(1, result.getValue());
    }

    @Test
    void create() {
        MQ7Reading entity = input.mockEntity(1);
        entity.setId(1L);

        MQ7ReadingVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(userServices.findByAccessToken(any(String.class))).thenReturn(userInput.mockUserVO());
        when(teamService.findByHandle(any(String.class), any(String.class))).thenReturn(DozerMapper.parseObject(entity.getTeam(), TeamVO.class));
        when(repository.save(any(MQ7Reading.class))).thenReturn(entity);

        var result = service.create(vo, "");

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getTeamHandle());
        assertNotNull(result.getTimestamp());
        assertNotNull(result.getValue());

        assertEquals("links: [</api/mq7Reading/v1/" + result.getKey() + ">;rel=\"self\"]", result.toString());
        assertEquals(1, result.getValue());
    }

    @Test
    void createWithNullMQ7Reading() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> service.create(null, ""));

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}