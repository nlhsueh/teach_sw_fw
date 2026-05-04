// Version 1: Rigid structure, high coupling
class WindowV1 {
    Button[] buttons;
    Menu[] menus;
    TextArea[] textAreas;
    WidgetContainer[] containers;

    public void update() {
        if (buttons != null) 
            for (int k = 0; k < buttons.length; k++) 
                buttons[k].draw();
        if (menus != null) 
            for (int k = 0; k < menus.length; k++)
                menus[k].refresh();
        if (containers != null)
            for (int k = 0; k < containers.length; k++)
                containers[k].updateWidgets();
    }
}

// Version 2: Common interface for Leaves, but distinct from Composite
abstract class Widget { abstract void update(); }
class WidgetContainer { void updateWidgets() { /* ... */ } }

class WindowV2 {
    Widget[] widgets;
    WidgetContainer[] containers;

    public void update() {
        if (widgets != null)
            for (int k = 0; k < widgets.length; k++) 
                widgets[k].update();
        if (containers != null)
            for (int k = 0; k < containers.length; k++)
                containers[k].updateWidgets();
    }
}

// Version 3: Composite Pattern - Unified Interface
abstract class GUIComponent { abstract void update(); }

class WindowV3 {
    GUIComponent[] components;
    public void update() {
        if (components != null)
            for (int k = 0; k < components.length; k++)
                components[k].update();
    }
}

// Mock classes for demonstration
class Button extends Widget { void update() { draw(); } void draw() {} }
class Menu extends Widget { void update() { refresh(); } void refresh() {} }
class TextArea extends Widget { void update() {} }
