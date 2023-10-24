package softuni.exam.models.dto;

import softuni.exam.models.entity.Car;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.models.entity.Part;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.sql.Timestamp;

@XmlRootElement(name = "task")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskImportDto {
    @XmlElement
    @NotNull
    @Positive
    private BigDecimal price;
    @XmlElement
    @NotNull
    private String date;
    @XmlElement
    @NotNull
    private CarBase car;
    @XmlElement
    @NotNull
    private  MechanicBasic mechanic;
    @XmlElement
    @NotNull
    private PartBase part;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public CarBase getCar() {
        return car;
    }

    public void setCar(CarBase car) {
        this.car = car;
    }

    public MechanicBasic getMechanic() {
        return mechanic;
    }

    public void setMechanic(MechanicBasic mechanic) {
        this.mechanic = mechanic;
    }

    public PartBase getPart() {
        return part;
    }

    public void setPart(PartBase part) {
        this.part = part;
    }
}
