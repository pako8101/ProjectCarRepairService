package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.MechanicImportDto;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.models.entity.Part;
import softuni.exam.repository.MechanicsRepository;
import softuni.exam.service.MechanicsService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static softuni.exam.models.Constants.*;
import static softuni.exam.models.Constants.PART;

@Service
public class MechanicsServiceImpl implements MechanicsService {
    private static String MECHANIC_FILE_PATH = "C:\\Users\\Plamen Kamenov\\Desktop\\exercises\\SpringData\\examPrep\\CarReapairShop\\CarReapirServiceExamPrep122022\\src\\main\\resources\\files\\json\\mechanics.json";
    private final MechanicsRepository mechanicsRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtils validationUtils;
    @Autowired
    public MechanicsServiceImpl(MechanicsRepository mechanicsRepository,
                                ModelMapper modelMapper, Gson gson, ValidationUtils validationUtils) {
        this.mechanicsRepository = mechanicsRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtils = validationUtils;
    }




    @Override
    public boolean areImported() {
        return this.mechanicsRepository.count()>0;
    }

    @Override
    public String readMechanicsFromFile() throws IOException {
        return Files.readString(Path.of(MECHANIC_FILE_PATH));
    }

    @Override
    public String importMechanics() throws IOException {
        final StringBuilder sb = new StringBuilder();

        final List<MechanicImportDto> mechanics = Arrays.stream(gson.fromJson(readMechanicsFromFile(),
                MechanicImportDto[].class)).collect(Collectors.toList());
        for (MechanicImportDto mechanic : mechanics) {
            sb.append(System.lineSeparator());

            if (this.mechanicsRepository.findFirstByEmail(mechanic.getEmail()).isPresent()
            || this.mechanicsRepository.findFirstByFirstName(mechanic.getFistName()).isPresent()
            ||!this.validationUtils.isValid(mechanic)) {
                sb.append(String.format(INVALID_FORMAT,MECHANIC));
                continue;

            }

            this.mechanicsRepository.save( this.modelMapper.map(mechanic, Mechanic.class));

            sb.append(String.format(SUCCESSFUL_FORMAT,
                    MECHANIC,
                    mechanic.getFistName(),
                    mechanic.getLastName()));



        }

        return sb.toString().trim();
    }
}
