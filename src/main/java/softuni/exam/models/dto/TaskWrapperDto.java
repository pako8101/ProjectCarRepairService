package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "tasks")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskWrapperDto {
@XmlElement(name = "task")
    private List<TaskImportDto> tasks;

    public TaskWrapperDto() {
    }

    public TaskWrapperDto(List<TaskImportDto> tasks) {
        this.tasks = tasks;
    }

    public List<TaskImportDto> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskImportDto> tasks) {
        this.tasks = tasks;
    }
}
