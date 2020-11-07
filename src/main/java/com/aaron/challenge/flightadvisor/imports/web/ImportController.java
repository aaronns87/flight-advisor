package com.aaron.challenge.flightadvisor.imports.web;

import com.aaron.challenge.flightadvisor.imports.ImportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor

@RestController
@RequestMapping("/admin/import")
@Tag(name = "Import", description = "Data import endpoints. Import airports and routes from CSV."
)
public class ImportController {

    private final ImportService importService;

    @PostMapping(value = "/airports")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Import airports from airports CSV",
            description = "Import airports from airports CSV. Headers are not expected in CSV and column order is important! " +
                    "File parameter name is \"file\". Upload as form-data.")
    public void importAirports(@RequestParam("file") MultipartFile file) {
        log.info("POST import airports, file name: {}.", file.getName());

        importService.importAirports(file);
    }

    @PostMapping(value = "/routes")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Import airports from routes CSV",
            description = "Import airports from routes CSV. Headers are not expected in CSV and column order is important! " +
                    "File parameter name is  \"file\". Upload as form-data.")
    public void importRoutes(@RequestParam("file") MultipartFile file) {
        log.info("POST import routes, file name: {}.", file.getName());

        importService.importRoutes(file);
    }
}
