/**
 * A cloud consisting of two circles that can be manipulated and that draws itself on a canvas.
 * This class is an example of composition, as it "contains" Circle objects.
 */
public class Cloud
{
    private Circle circle1;
    private Circle circle2;

    /**
     * Create a new cloud at a default position with a default color.
     */
    public Cloud()
    {
        circle1 = new Circle();
        circle2 = new Circle();

        // Position cloud shape
        circle1.changeSize(50);
        circle1.moveHorizontal(150);
        circle1.moveVertical(-80);
        circle1.changeColor("blue");

        circle2.changeSize(70);
        circle2.moveHorizontal(180);
        circle2.moveVertical(-70);
        circle2.changeColor("blue");
    }

    /**
     * Make this cloud visible.
     */
    public void makeVisible()
    {
        circle1.makeVisible();
        circle2.makeVisible();
    }

    /**
     * Make this cloud invisible.
     */
    public void makeInvisible()
    {
        circle1.makeInvisible();
        circle2.makeInvisible();
    }

    /**
     * Slowly move the cloud horizontally by 'distance' pixels.
     * It moves both circles together, pixel by pixel.
     */
    public void slowMoveHorizontal(int distance)
    {
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        }
        else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++) {
            // Move clouds together
            circle1.moveHorizontal(delta);
            circle2.moveHorizontal(delta);
        }
    }
}