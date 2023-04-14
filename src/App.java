
@FunctionalInterface
interface Conversor<T, R> {
    R converter(T t);
}


public class App {

    public static void main(String[] args) {
        
        Conversor<String, Double> lambda = new Conversor<String, Double>() {
            @Override 
            public Double converter(String v) {
                final String regex = "^\\d+(\\.\\d+)*$";
                if (v != null && v.matches(regex)) {
                    return Double.parseDouble(v);
                } else {
                    return null;
                }
            }
        };

        System.out.println(lambda.converter("125.24"));


    }
}