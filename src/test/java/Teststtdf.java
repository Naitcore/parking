import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

public class Teststtdf {

    public static void main(String[] args) {
        HashMap<Spot, Integer> spotsList = new HashMap<>();
        spotsList.put(new Spot(Type.REGULAR, Size.LARGE), 10);
        spotsList.put(new Spot(Type.REGULAR, Size.MEDIUM), 24);
        spotsList.put(new Spot(Type.REGULAR, Size.SMALL), 9);
        spotsList.put(new Spot(Type.HANDICAP, Size.MEDIUM), 5);

        List<Park> vehiclesList = new ArrayList<>();
        vehiclesList.add(new Park(Type.REGULAR, Size.LARGE, 8));
        vehiclesList.add(new Park(Type.REGULAR, Size.MEDIUM, 3));
        vehiclesList.add(new Park(Type.HANDICAP, Size.LARGE, 1));
        vehiclesList.add(new Park(Type.REGULAR, Size.LARGE, 1));
        vehiclesList.add(new Park(Type.HANDICAP, Size.LARGE, 1));
        vehiclesList.add(new Park(Type.REGULAR, Size.SMALL, 3));
        vehiclesList.add(new Park(Type.REGULAR, Size.MEDIUM, 5));
        vehiclesList.add(new Park(Type.HANDICAP, Size.MEDIUM, 10));

        park(spotsList, vehiclesList);

        spotsList.forEach((s, i) -> {
            if (i > 0) {
                System.out.println(String.format("Type: %1$s, Size: %2$s, Number Of Places: %3$s", s.getType(), s.getSize(), i));
            }
        });
    }

    private static void park(HashMap<Spot, Integer> spots, List<Park> cars) {
        cars.forEach((c) -> {
            Integer places = spots.get(new Spot(c.getType(), c.getSize()));
            if (places != null && places >= c.getNumberOfCar()) {
                spots.replace(new Spot(c.getType(), c.getSize()), places - c.getNumberOfCar());
            } else if (places != null) {
                spots.replace(new Spot(c.getType(), c.getSize()), 0);
                setAnotherType(spots, c, c.getNumberOfCar() - places);
            } else {
                setAnotherType(spots, c, c.getNumberOfCar());
            }
        });
    }

    private static void setAnotherType(HashMap<Spot, Integer> spots, Park vehicle, Integer notEnoughPlaces) {
        switch (vehicle.getType()) {
            case HANDICAP: {
                Integer morePlacesRequired = setAnotherSize(spots, Type.HANDICAP, vehicle.getSize(), notEnoughPlaces);
                if (morePlacesRequired > 0) {
                    setAnotherSize(spots, Type.REGULAR, vehicle.getSize(), morePlacesRequired);
                }
                break;
            }
            case REGULAR: {
                setAnotherSize(spots, Type.REGULAR, vehicle.getSize(), notEnoughPlaces);
                break;
            }
            default:
                break;
        }
    }

    private static Integer setAnotherSize(HashMap<Spot, Integer> spots, Type type, Size size, Integer notEnoughPlaces) {
        Integer inf = spots.get(new Spot(type, size)) != null ? spots.get(new Spot(type, size)) : 0;
        switch (size) {
            case SMALL: {
                return setSize(spots, type, Size.SMALL, notEnoughPlaces, () -> setAnotherSize(spots, type, Size.MEDIUM,notEnoughPlaces - inf));
            }
            case MEDIUM: {
                return setSize(spots, type, Size.MEDIUM, notEnoughPlaces, () -> setAnotherSize(spots, type, Size.LARGE,notEnoughPlaces - inf));
            }
            case LARGE: {
                return setSize(spots, type, Size.LARGE, notEnoughPlaces, () -> notEnoughPlaces - inf);
            }
            default: {
                return notEnoughPlaces;
            }
        }
    }

    private static Integer setSize(HashMap<Spot, Integer> spots, Type type, Size size, Integer notEnoughPlaces, Supplier supplier) {
        Integer places = spots.get(new Spot(type, size));
        if (places != null && places >= notEnoughPlaces) {
            spots.replace(new Spot(type, size), places - notEnoughPlaces);
            return 0;
        } else if (places != null) {
            spots.replace(new Spot(type, size), 0);
        }
        return (Integer) supplier.get();
    }
}
