package nqt.base_java_spring_be.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import nqt.base_java_spring_be.dto.CustomResponse;
import nqt.base_java_spring_be.dto.request.VnpayCallbackRequest;
import nqt.base_java_spring_be.dto.response.VnpayCallbackResponse;
import nqt.base_java_spring_be.entity.Project;
import nqt.base_java_spring_be.service.iservices.ProjectService;
import nqt.base_java_spring_be.utils.ErrorUtils;
import nqt.base_java_spring_be.utils.ThanhToanOnlineUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/project")
@Tag(name = "Project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService service;

    @GetMapping("find-all")
    public ResponseEntity<?> getOptions() {
        var data = service.findAll();
        return ResponseEntity.ok(CustomResponse.success(data, "Thành công"));
    }
}
