import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Park {
    @Getter @Setter private Type type;
    @Getter @Setter private Size size;
    @Getter @Setter private Integer numberOfCar;

    public Park(Type type, Size size, Integer numberOfCar) {
        this.type = type;
        this.size = size;
        this.numberOfCar = numberOfCar;
    }
}
