package mbd.dev.hackmorelos.service.file;

import lombok.RequiredArgsConstructor;
import mbd.dev.hackmorelos.controller.file.dto.FileDto;
import mbd.dev.hackmorelos.model.file.File;
import mbd.dev.hackmorelos.model.file.FileResository;
import mbd.dev.hackmorelos.utils.ApiResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor

public class FileService {

    @Value("${url.base}")
    private String urlBase;

    private final FileResository fileResository;

    @Transactional(rollbackFor = {Exception.class})
    public ResponseEntity<ApiResponse<File>> uploadFile(FileDto file, MultipartFile filePDF) {
        try {
            String fileName = UUID.randomUUID().toString().substring(0, 7);
            System.out.println(filePDF.getOriginalFilename());
            Path uploadPath = Paths.get(urlBase, fileName + ".pdf");
            Files.write(uploadPath, filePDF.getBytes());
            File newFile = new File();
            String newId = UUID.randomUUID().toString();
            newFile.setId(newId);
            newFile.setUrl("/api-alma/file/download/" + fileName + ".pdf");
            BeanUtils.copyProperties(file, newFile);
            File savedFile = fileResository.save(newFile);
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            savedFile, false, HttpStatus.OK.value(), "Archivo subido correctamente"),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            null, true, HttpStatus.INTERNAL_SERVER_ERROR.value(), "No se pudo subir el archivo"),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }


    public Resource downloadFile(String fileName) throws  IOException {
        Path downloadPath = Paths.get(urlBase, fileName);
        Resource resource = new UrlResource(downloadPath.toUri());
        if (resource.exists()) {
            return resource;
        } else {
            throw new IOException("No se pudo descargar el archivo" + fileName);
        }
    }

}
