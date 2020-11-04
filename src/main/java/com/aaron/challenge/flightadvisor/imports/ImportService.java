package com.aaron.challenge.flightadvisor.imports;

import com.aaron.challenge.flightadvisor.imports.csv.CsvImporter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor

@Service
public class ImportService {

    public static String CONTENT_TYPE_TEXT_CSV = "text/csv";

    private final CsvImporter csvImporter;

    public void importAirports(MultipartFile multipartFile) {
        validateContentTypeOrThrow(multipartFile);

        try (var inputStream = multipartFile.getInputStream()) {
            csvImporter.importAirports(inputStream);
        } catch (IOException e) {
            log.error("Unable to read airports multipart file stream.", e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to read input file.");
        } catch (Exception e) {
            log.error("Error occurred while importing airports.", e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to import airports.");
        }
    }

    public void importRoutes(MultipartFile multipartFile) {
        validateContentTypeOrThrow(multipartFile);

        try (var inputStream = multipartFile.getInputStream()) {
            csvImporter.importRoutes(inputStream);
        } catch (IOException e) {
            log.error("Unable to read routes multipart file stream.", e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to read input file.");
        } catch (Exception e) {
            log.error("Error occurred while importing routes.", e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to import routes.");
        }
    }

    private void validateContentTypeOrThrow(MultipartFile multipartFile) {
        if (isNotTextCsvContentType(multipartFile)) {
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Unsupported content type " + multipartFile.getContentType() + ". Expected " + CONTENT_TYPE_TEXT_CSV + ".");
        }
    }

    private boolean isNotTextCsvContentType(MultipartFile multipartFile) {
        return !CONTENT_TYPE_TEXT_CSV.equals(multipartFile.getContentType());
    }
}
