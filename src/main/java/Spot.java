import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Spot {
    @Getter @Setter private Type type;
    @Getter @Setter private Size size;

    public Spot(Type type, Size size) {
        this.type = type;
        this.size = size;
    }
}
