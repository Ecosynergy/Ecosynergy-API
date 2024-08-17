package app.ecosynergy.api.services;

import app.ecosynergy.api.data.vo.v1.ActivityVO;
import app.ecosynergy.api.data.vo.v1.SectorVO;
import app.ecosynergy.api.exceptions.RequiredObjectIsNullException;
import app.ecosynergy.api.exceptions.ResourceAlreadyExistsException;
import app.ecosynergy.api.exceptions.ResourceNotFoundException;
import app.ecosynergy.api.mapper.DozerMapper;
import app.ecosynergy.api.models.Activity;
import app.ecosynergy.api.models.Sector;
import app.ecosynergy.api.repositories.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class ActivityServices {
    private final ActivityRepository repository;
    private final SectorServices sectorServices;

    @Autowired
    public ActivityServices(ActivityRepository repository, @Lazy SectorServices sectorServices) {
        this.repository = repository;
        this.sectorServices = sectorServices;
    }

    private static final Logger logger = Logger.getLogger(ActivityServices.class.getName());

    public ActivityVO findById(Long id) {
        if(id == null) throw new RequiredObjectIsNullException();

        Activity activity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Activity not found with given ID: " + id));

        return DozerMapper.parseObject(activity, ActivityVO.class);
    }

    public ActivityVO findByName(String name) {
        if(name == null) throw new RequiredObjectIsNullException();

        Activity activity = repository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Activity not found with given name: " + name));

        return DozerMapper.parseObject(activity, ActivityVO.class);
    }

    public List<ActivityVO> findBySectorId(Long sectorId) {
        if(sectorId == null) throw new RequiredObjectIsNullException();

        SectorVO sectorVO = sectorServices.findById(sectorId);

        List<Activity> activities = repository.findActivitiesBySectorId(sectorVO.getKey());

        return DozerMapper.parseListObjects(activities, ActivityVO.class);
    }

    public List<ActivityVO> findAll() {
        List<Activity> entityList = repository.findAllByOrderByNameAsc();

        return DozerMapper.parseListObjects(entityList, ActivityVO.class);
    }

    public ActivityVO create(ActivityVO activityVO) {
        if(activityVO == null) throw new RequiredObjectIsNullException();

        boolean isActivityAlreadyExists = repository.findByName(activityVO.getName()).isPresent();

        if(isActivityAlreadyExists) throw new ResourceAlreadyExistsException("Activity with name: '" + activityVO.getName() + "' already exists");

        Activity activity = convertToEntity(activityVO);

        activity = repository.save(activity);

        return DozerMapper.parseObject(activity, ActivityVO.class);
    }

    public ActivityVO update(Long id, ActivityVO activityVO) {
        if(id == null || activityVO == null) throw new RequiredObjectIsNullException();

        Activity entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Activity not found with given ID: " + id));
        entity.setName(activityVO.getName() != null ? activityVO.getName() : entity.getName());

        if(activityVO.getSector() != null) {
            entity.setSector(DozerMapper.parseObject(sectorServices.findByName(activityVO.getSector()), Sector.class));
        }

        Activity updatedActivity = repository.save(entity);

        return DozerMapper.parseObject(updatedActivity, ActivityVO.class);
    }

    public void delete(Long id) {
        if(id == null) throw new RequiredObjectIsNullException();

        ActivityVO activityVO = findById(id);

        repository.deleteById(activityVO.getKey());
    }

    Activity convertToEntity(ActivityVO activityVO) {
        Activity entity = new Activity();
        entity.setId(activityVO.getKey());
        entity.setName(activityVO.getName());

        SectorVO sectorVO = sectorServices.findByName(activityVO.getSector());

        entity.setSector(DozerMapper.parseObject(sectorVO, Sector.class));

        return entity;
    }
}
