package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PartImportDto;
import softuni.exam.models.entity.Part;
import softuni.exam.repository.PartsRepository;
import softuni.exam.service.PartsService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static softuni.exam.models.Constants.*;

@Service
public class PartsServiceImpl implements PartsService {
    private final static String PARTS_FILE_PATH = "C:\\Users\\Plamen Kamenov\\Desktop\\exercises\\SpringData\\examPrep\\CarReapairShop\\CarReapirServiceExamPrep122022\\src\\main\\resources\\files\\json\\parts.json";
    private final PartsRepository partsRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtils validationUtils;

    @Autowired
    public PartsServiceImpl(PartsRepository partsRepository, ModelMapper modelMapper, Gson gson, ValidationUtils validationUtils) {
        this.partsRepository = partsRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtils = validationUtils;
    }



    @Override
    public boolean areImported() {
        return this.partsRepository.count()>0;
    }

    @Override
    public String readPartsFileContent() throws IOException {
        return Files.readString(Path.of(PARTS_FILE_PATH));
    }

    @Override
    public String importParts() throws IOException {
        final StringBuilder sb = new StringBuilder();

        final List<PartImportDto> parts = Arrays.stream(this.gson
                .fromJson(readPartsFileContent(), PartImportDto[].class))
                .collect(Collectors.toList());

        for (PartImportDto part : parts) {
            sb.append(System.lineSeparator());

            if (this.partsRepository.findFirstByPartName(part.getPartName()).isPresent()
            || !this.validationUtils.isValid(part)) {
                sb.append(String.format(INVALID_FORMAT,PART));
                continue;
            }
            if (this.validationUtils.isValid(part)) {

                this.partsRepository.save(this.modelMapper.map(part, Part.class));
                sb.append(String.format(SUCCESSFUL_FORMAT,
                        PART,
                        part.getPartName() + " -",
                        part.getPrice()));
                continue;
            }
        }


        return sb.toString().trim();
    }
}
