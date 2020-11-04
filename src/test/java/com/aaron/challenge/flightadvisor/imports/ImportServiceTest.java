package com.aaron.challenge.flightadvisor.imports;

import com.aaron.challenge.flightadvisor.imports.csv.CsvImporter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ImportServiceTest {

    @InjectMocks
    private ImportService importService;

    @Mock
    private CsvImporter csvImporter;

    @Test
    public void importAirports_invalidMultipartFile_exceptionThrown() {
        assertThatThrownBy(() -> importService.importAirports(new NotCsvFile()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    public void importAirports_validMultipartFile() throws IOException {
        importService.importAirports(new CsvFile());

        verify(csvImporter).importAirports(any());
    }

    @Test
    public void importRoutes_invalidMultipartFile_exceptionThrown() {
        assertThatThrownBy(() -> importService.importRoutes(new NotCsvFile()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    public void importRoutes_validMultipartFile() throws IOException {
        importService.importRoutes(new CsvFile());

        verify(csvImporter).importRoutes(any());
    }

    static class CsvFile implements MultipartFile {

        @Override
        public String getName() {
            return null;
        }

        @Override
        public String getOriginalFilename() {
            return null;
        }

        @Override
        public String getContentType() {
            return "text/csv";
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public long getSize() {
            return 0;
        }

        @Override
        public byte[] getBytes() throws IOException {
            return new byte[0];
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return null;
        }

        @Override
        public void transferTo(File dest) throws IOException, IllegalStateException {

        }
    }

    static class NotCsvFile implements MultipartFile {

        @Override
        public String getName() {
            return null;
        }

        @Override
        public String getOriginalFilename() {
            return null;
        }

        @Override
        public String getContentType() {
            return "someContentType";
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public long getSize() {
            return 0;
        }

        @Override
        public byte[] getBytes() throws IOException {
            return new byte[0];
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return null;
        }

        @Override
        public void transferTo(File dest) throws IOException, IllegalStateException {

        }
    }
}
