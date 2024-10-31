package app.ecosynergy.api.services;

import app.ecosynergy.api.data.vo.v1.ActivityVO;
import app.ecosynergy.api.data.vo.v1.SectorVO;
import app.ecosynergy.api.exceptions.RequiredObjectIsNullException;
import app.ecosynergy.api.exceptions.ResourceAlreadyExistsException;
import app.ecosynergy.api.exceptions.ResourceNotFoundException;
import app.ecosynergy.api.mapper.DozerMapper;
import app.ecosynergy.api.models.Sector;
import app.ecosynergy.api.repositories.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectorService {
    private final ActivityService activityService;
    private final SectorRepository repository;

    @Autowired
    public SectorService(ActivityService activityService, SectorRepository repository) {
        this.activityService = activityService;
        this.repository = repository;
    }


    public List<SectorVO> findAll() {
        List<Sector> sectors = repository.findAllByOrderByNameAsc();

        return DozerMapper.parseListObjects(sectors, SectorVO.class);
    }

    public SectorVO findById(Long id) {
        if (id == null) throw new RequiredObjectIsNullException();

        Sector sector = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sector not found with given ID: " + id));

        return DozerMapper.parseObject(sector, SectorVO.class);
    }

    public SectorVO findByName(String name) {
        if (name.isBlank()) throw new RequiredObjectIsNullException();

        Sector sector = repository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Sector not found with given name: " + name));

        return DozerMapper.parseObject(sector, SectorVO.class);
    }

    public SectorVO findByActivity(Long activityId) {
        if (activityId == null) throw new RequiredObjectIsNullException();

        ActivityVO activityVO = activityService.findById(activityId);

        return findByName(activityVO.getSector());
    }

    public SectorVO create(SectorVO sectorVO) {
        if (sectorVO == null) throw new RequiredObjectIsNullException();

        boolean isSectorAlreadyExists = repository.findByName(sectorVO.getName()).isPresent();

        if (isSectorAlreadyExists)
            throw new ResourceAlreadyExistsException("Activity with name: '" + sectorVO.getName() + "' already exists");

        Sector sector = repository.save(DozerMapper.parseObject(sectorVO, Sector.class));

        return DozerMapper.parseObject(sector, SectorVO.class);
    }

    public SectorVO update(Long id, SectorVO sectorVO) {
        if (id == null || sectorVO.getName() == null) throw new RequiredObjectIsNullException();

        Sector sector = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sector not found with given ID: " + id));

        sector.setName(sectorVO.getName());

        Sector updatedSector = repository.save(sector);

        return DozerMapper.parseObject(updatedSector, SectorVO.class);
    }

    public void delete(Long id) {
        if (id == null) throw new RequiredObjectIsNullException();

        SectorVO sectorVO = findById(id);

        repository.deleteById(sectorVO.getKey());
    }
}
