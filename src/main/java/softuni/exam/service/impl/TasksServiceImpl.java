package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.TaskDto;
import softuni.exam.models.dto.TaskImportDto;
import softuni.exam.models.dto.TaskWrapperDto;
import softuni.exam.models.entity.*;
import softuni.exam.repository.CarsRepository;
import softuni.exam.repository.MechanicsRepository;
import softuni.exam.repository.PartsRepository;
import softuni.exam.repository.TasksRepository;
import softuni.exam.service.TasksService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.models.Constants.*;

@Service
public class TasksServiceImpl implements TasksService {
    private static String TASKS_FILE_PATH = "C:\\Users\\Plamen Kamenov\\Desktop\\exercises\\SpringData\\examPrep\\CarReapairShop\\CarReapirServiceExamPrep122022\\src\\main\\resources\\files\\xml\\tasks.xml";
    private final TasksRepository tasksRepository;
    private final MechanicsRepository mechanicsRepository;
    private final CarsRepository carsRepository;
private final PartsRepository partsRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtils validationUtils;

@Autowired
    public TasksServiceImpl(TasksRepository tasksRepository,
                            MechanicsRepository mechanicsRepository,
                            CarsRepository carsRepository,
                            PartsRepository partsRepository, ModelMapper modelMapper,
                            XmlParser xmlParser,
                            ValidationUtils validationUtils) {
        this.tasksRepository = tasksRepository;
    this.mechanicsRepository = mechanicsRepository;
    this.carsRepository = carsRepository;
    this.partsRepository = partsRepository;
    this.modelMapper = modelMapper;
    this.xmlParser = xmlParser;
    this.validationUtils = validationUtils;
}

    @Override
    public boolean areImported() {
        return this.tasksRepository.count()>0;
    }

    @Override
    public String readTasksFileContent() throws IOException {
        return Files.readString(Path.of(TASKS_FILE_PATH));
    }

    @Override
    public String importTasks() throws IOException, JAXBException {
    StringBuilder sb = new StringBuilder();
        final List<TaskImportDto> tasks = this.xmlParser
                .fromFile(Path.of(TASKS_FILE_PATH).toFile(), TaskWrapperDto.class).getTasks();

        for (TaskImportDto task : tasks) {
            sb.append(System.lineSeparator());
if ( this.validationUtils.isValid(task)){
            final Optional<Mechanic> mechanic = this.mechanicsRepository
                    .findFirstByFirstName(task.getMechanic().getFirstName());
            final Optional<Car> car = this.carsRepository.findById(task.getCar().getId());
            final Optional<Part> part = this.partsRepository.findById(task.getPart().getId());

            if (mechanic.isEmpty()||car.isEmpty()|| part.isEmpty()){
                sb.append(String.format(INVALID_FORMAT,TASK));
                continue;
            }

                final Task taskToSave = modelMapper.map(task, Task.class);
                taskToSave.setMechanic(mechanic.get());
                taskToSave.setPart(part.get());
                taskToSave.setCar(car.get());

                this.tasksRepository.save(taskToSave);

                sb.append(String.format(SUCCESSFUL_FORMAT,
                        TASK, task.getPrice().setScale(2), "").trim());
continue;
            }
            sb.append(String.format(INVALID_FORMAT,TASK));

        }

        return sb.toString().trim();
    }

    @Override
    public String getCoupeCarTasksOrderByPrice() {

   return this.tasksRepository.findAllByCarCarTypeOrderByPriceDesc(CarType.coupe)
            .stream()
            .map(task-> this.modelMapper.map(task, TaskDto.class))
            .map(TaskDto::toString)
            .collect(Collectors.joining()).trim();

    }
}
