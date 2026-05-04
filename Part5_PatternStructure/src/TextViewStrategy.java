// Comparison: Using Strategy (or multiple subclasses) which leads to combinatorial explosion
abstract class TextView {
    abstract void draw();
}

class BorderTextView extends TextView { void draw() { /* draw border and text */ } }
class ScrollTextView extends TextView { void draw() { /* draw scroll and text */ } }
class BorderScrollTextView extends TextView { void draw() { /* draw border, scroll and text */ } }

// Imagine adding Shadow, Animation, etc. -> Subclass explosion!
