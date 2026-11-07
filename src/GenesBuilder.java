public class GenesBuilder {

    //default values are for sheep
    private double perception = 160;
    private double speed = 1;
    private int lifeSpan = 2500;
    private int maxStamina = 3000;
    private int staminaRecoverRate = 2;
    private int staminaUseRate = 20;
    private double hungerCapacity = 1;
    private double sprintMultiplier = 2;
    private double stealth = 20;
    private int reproduceCooldown = 100;

    public static GenesBuilder sheep()
    {
        return new GenesBuilder();
    }

    public static GenesBuilder wolf()
    {
        return new GenesBuilder()
            .perception(90)
            .speed(2)
            .lifeSpan((int)(Math.random() * 1000) + 2500)
            .maxStamina((int)(Math.random() * 1500) + 750)
            .staminaRecoverRate((int)(Math.random() * 15) + 5)
            .staminaUseRate((int)(Math.random() * 10) + 10)
            .reproduceCooldown(1000);
    }

    public GenesBuilder perception(double perception)
    {
        this.perception = perception;
        return this;
    }

    public GenesBuilder speed(double speed)
    {
        this.speed = speed;
        return this;
    }

    public GenesBuilder lifeSpan(int lifeSpan)
    {
        this.lifeSpan = lifeSpan;
        return this;
    }

    public GenesBuilder maxStamina(int maxStamina)
    {
        this.maxStamina = maxStamina;
        return this;
    }

    public GenesBuilder staminaRecoverRate(int staminaRecoverRate)
    {
        this.staminaRecoverRate = staminaRecoverRate;
        return this;
    }

    public GenesBuilder staminaUseRate(int staminaUseRate)
    {
        this.staminaUseRate = staminaUseRate;
        return this;
    }

    public GenesBuilder hungerCapacity(double hungerCapacity)
    {
        this.hungerCapacity = hungerCapacity;
        return this;
    }

    public GenesBuilder sprintMultiplier(double sprintMultiplier)
    {
        this.sprintMultiplier = sprintMultiplier;
        return this;
    }

    public GenesBuilder stealth(double stealth)
    {
        this.stealth = stealth;
        return this;
    }

    public GenesBuilder reproduceCooldown(int reproduceCooldown)
    {
        this.reproduceCooldown = reproduceCooldown;
        return this;
    }

    public Genes build()
    {
        return new Genes(perception, speed, lifeSpan, maxStamina, staminaRecoverRate,
            staminaUseRate, hungerCapacity, sprintMultiplier, stealth, reproduceCooldown);
    }
}