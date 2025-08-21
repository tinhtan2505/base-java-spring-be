package nqt.base_java_spring_be.service.impl;

import lombok.RequiredArgsConstructor;
import nqt.base_java_spring_be.entity.Project;
import nqt.base_java_spring_be.exception.ResourceNotFoundException;
import nqt.base_java_spring_be.repository.ProjectRepository;
import nqt.base_java_spring_be.service.iservices.ProjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl  implements ProjectService {
    private final ProjectRepository repo;

    @Override
    public Project create(Project project) {
        // Thêm các kiểm tra/logic nếu cần
        return repo.save(project);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> findAll() {
        return repo.findByActiveTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public Project findById(UUID id) {
        return repo.findById(id)
                .filter(Project::isActive) // ẩn record đã soft-delete
                .orElseThrow(() -> new ResourceNotFoundException("Project not found: " + id));
    }

    @Override
    public Project update(UUID id, Project update) {
        Project current = findById(id);
        current.setName(update.getName());
        return repo.save(current);
    }

    @Override
    public void delete(UUID id) {
        Project current = findById(id);
        current.markDeleted();
        repo.save(current);
    }
}

