// Not all the units that can deal damage
// are sentient, so need to make an interface
public interface DealsDamage 
{
	public abstract void attack(Sentient sentient);
	public abstract void inflictDamage(Sentient sentient);
	public abstract int getDamage();
}
