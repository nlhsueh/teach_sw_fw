class TwoPin {
    public void insert() {
        // ...
    }
}

class ThreePin {
    public void plugIn(String str) {
        // ...
    }
}

// Adapter for converting TwoPin interface to use ThreePin
public class PowerAdapter extends TwoPin {
    private ThreePin threePin;

    public PowerAdapter(ThreePin pin) {
        this.threePin = pin;
    }

    @Override
    public void insert() {
        threePin.plugIn("Powering on via adapter");
    }
}

class Computer {
    public static void main(String args[]) {
        ThreePin threePin = new ThreePin();

        // Using the adapter
        TwoPin adapter = new PowerAdapter(threePin);
        (new Computer()).powerOn(adapter);
    }

    // Existing code that only works with TwoPin interface
    public void powerOn(TwoPin pin) {
        pin.insert();
        powerOnComputer();
    }

    private void powerOnComputer() {
        // ...
    }
}
