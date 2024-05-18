package mbd.dev.hackmorelos.controller.file;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mbd.dev.hackmorelos.controller.file.dto.FileDto;
import mbd.dev.hackmorelos.model.file.File;
import mbd.dev.hackmorelos.service.file.FileService;
import mbd.dev.hackmorelos.utils.ApiResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api-alma/file")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor

public class FileController {

    private final FileService fileService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<File>> uploadFile(@Valid @RequestPart FileDto file, @RequestParam("filePDF") MultipartFile filePDF) {
        try {
            return fileService.uploadFile(file ,filePDF);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            null, true, HttpStatus.INTERNAL_SERVER_ERROR.value(), "No se pudo subir el archivo"
                    ),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<?> downloadFile(@PathVariable String fileName) {
        try {
            Resource resource = fileService.downloadFile(fileName);
            return ResponseEntity.ok()
                    .contentLength(resource.contentLength())
                    .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("No se pudo descargar el archivo");
        }
    }
}
