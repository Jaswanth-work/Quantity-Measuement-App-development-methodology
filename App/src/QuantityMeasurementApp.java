public class QuantityMeasurementApp {

    private static final double EPSILON = 1e-6;

    // Enum for Length Units
    enum LengthUnit {
        FEET(1.0),
        INCHES(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(0.0328084);

        private final double conversionFactor; // relative to feet

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        public double getConversionFactor() {
            return conversionFactor;
        }
    }

    // Quantity Length Class
    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            validate(value, unit);
            this.value = value;
            this.unit = unit;
        }

        public double getValue() {
            return value;
        }

        public LengthUnit getUnit() {
            return unit;
        }

        // Convert current object to target unit
        public QuantityLength convertTo(LengthUnit targetUnit) {
            double convertedValue = convert(value, unit, targetUnit);
            return new QuantityLength(convertedValue, targetUnit);
        }

        // Convert any value from source to target
        public static double convert(double value, LengthUnit sourceUnit, LengthUnit targetUnit) {
            validate(value, sourceUnit);
            if (targetUnit == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }

            double baseValue = value * sourceUnit.getConversionFactor(); // to feet
            return baseValue / targetUnit.getConversionFactor();
        }

        private static void validate(double value, LengthUnit unit) {
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Value must be finite");
            }
        }

        private double toBaseUnit() {
            return value * unit.getConversionFactor();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof QuantityLength)) return false;

            QuantityLength other = (QuantityLength) obj;
            return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < EPSILON;
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // Overloaded Method 1
    public static void demonstrateLengthConversion(double value, LengthUnit from, LengthUnit to) {
        double result = QuantityLength.convert(value, from, to);
        System.out.println(value + " " + from + " = " + result + " " + to);
    }

    // Overloaded Method 2
    public static void demonstrateLengthConversion(QuantityLength length, LengthUnit to) {
        QuantityLength converted = length.convertTo(to);
        System.out.println(length + " = " + converted);
    }

    public static void demonstrateLengthEquality(QuantityLength q1, QuantityLength q2) {
        System.out.println(q1 + " and " + q2 + " are equal? " + q1.equals(q2));
    }

    public static void demonstrateLengthComparison(double v1, LengthUnit u1, double v2, LengthUnit u2) {
        QuantityLength q1 = new QuantityLength(v1, u1);
        QuantityLength q2 = new QuantityLength(v2, u2);
        demonstrateLengthEquality(q1, q2);
    }

    public static void main(String[] args) {

        // Conversions
        demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCHES);
        demonstrateLengthConversion(3.0, LengthUnit.YARDS, LengthUnit.FEET);
        demonstrateLengthConversion(36.0, LengthUnit.INCHES, LengthUnit.YARDS);
        demonstrateLengthConversion(2.54, LengthUnit.CENTIMETERS, LengthUnit.INCHES);
        demonstrateLengthConversion(0.0, LengthUnit.FEET, LengthUnit.INCHES);

        System.out.println();

        // Using object conversion
        QuantityLength length = new QuantityLength(2.0, LengthUnit.YARDS);
        demonstrateLengthConversion(length, LengthUnit.INCHES);

        System.out.println();

        // Equality checks
        demonstrateLengthComparison(1.0, LengthUnit.FEET, 12.0, LengthUnit.INCHES);
        demonstrateLengthComparison(1.0, LengthUnit.YARDS, 3.0, LengthUnit.FEET);
    }
}