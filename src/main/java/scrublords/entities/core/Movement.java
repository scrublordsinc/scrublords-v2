package scrublords.entities.core;

/**
 * @author Denis Dimitrov <denis.k.dimitrov@gmail.com>.
 */
public class Movement {
    public double moveSpeed;
    public double maxSpeed;
    public double stopSpeed;
    public double fallSpeed;
    public double maxFallSpeed;
    public double jumpStart;
    public double stopJumpSpeed;

    public Movement(double moveSpeed, double maxSpeed, double stopSpeed, double fallSpeed, double maxFallSpeed, double jumpStart, double stopJumpSpeed) {
        this.moveSpeed = moveSpeed;
        this.maxSpeed = maxSpeed;
        this.stopSpeed = stopSpeed;
        this.fallSpeed = fallSpeed;
        this.maxFallSpeed = maxFallSpeed;
        this.jumpStart = jumpStart;
        this.stopJumpSpeed = stopJumpSpeed;
    }
}
