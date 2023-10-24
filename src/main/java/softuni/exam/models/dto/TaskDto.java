package softuni.exam.models.dto;

import java.math.BigDecimal;

public class TaskDto {

    private Long id;
    private BigDecimal price;

    private MechanicInfo mechanic;

    private CarInfoDto car;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public MechanicInfo getMechanic() {
        return mechanic;
    }

    public void setMechanic(MechanicInfo mechanic) {
        this.mechanic = mechanic;
    }

    public CarInfoDto getCar() {
        return car;
    }

    public void setCar(CarInfoDto car) {
        this.car = car;
    }

    @Override
    public String toString() {
        String FORMAT = "Car %s %s with %dkm\n-Mechanic: %s %s - task №%d:\n --Engine: %.1f\n---Price: %s$\n";

        return String.format(FORMAT,
                this.car.getCarMake(),
                this.car.getCarModel(),
                this.car.getKilometers(),
                this.mechanic.getFirstName(),
                this.mechanic.getLastName(),
                this.id,
                this.car.getEngine(),
                this.price.setScale(2)
        );
    }
}
