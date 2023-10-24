package softuni.exam.models.entity;

import javax.persistence.*;
import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class Task extends BaseEntity{

    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private String date;

    @ManyToOne
    @JoinColumn(name = "parts_id")
    private Part part;
    @ManyToOne
    @JoinColumn(name = "mechanic_id")
    private Mechanic mechanic;
    @ManyToOne
    @JoinColumn(name = "cars_id")
    private Car car;

    public Task() {
    }

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

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public Mechanic getMechanic() {
        return mechanic;
    }

    public void setMechanic(Mechanic mechanic) {
        this.mechanic = mechanic;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
