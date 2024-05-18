package mbd.dev.hackmorelos.service.institute;

import com.mongodb.MongoException;
import lombok.RequiredArgsConstructor;
import mbd.dev.hackmorelos.controller.institute.dto.InstituteDto;
import mbd.dev.hackmorelos.model.institute.Institute;
import mbd.dev.hackmorelos.model.institute.InstituteRepository;
import mbd.dev.hackmorelos.utils.ApiResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor

public class InstituteService {

    private final InstituteRepository instituteRepository;

    @Transactional(rollbackFor = {MongoException.class})
    public ResponseEntity<ApiResponse<Institute>> create (InstituteDto institute) {
        if (instituteRepository.existsByName(institute.getName())) {
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            null, true, HttpStatus.BAD_REQUEST.value(), "La institución ya se encuentra registrada"),
                    HttpStatus.BAD_REQUEST);
        }
        Institute newInstitute = new Institute();
        String newId = String.valueOf(java.util.UUID.randomUUID());
        newInstitute.setId(newId);
        BeanUtils.copyProperties(institute, newInstitute);
        Institute savedInstitute = instituteRepository.save(newInstitute);
        return new ResponseEntity<>(
                new ApiResponse<>(
                        savedInstitute, false, HttpStatus.OK.value(), "Institución creada correctamente"),
                HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<java.util.List<Institute>>> getAll () {
        java.util.List<Institute> institutes = instituteRepository.findAll();
        if (institutes.isEmpty()) {
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            null, true, HttpStatus.NOT_FOUND.value(), "No se encontraron instituciones"),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(
                new ApiResponse<>(
                        institutes, false, HttpStatus.OK.value(), "Instituciones encontradas"),
                HttpStatus.OK);
    }
}
