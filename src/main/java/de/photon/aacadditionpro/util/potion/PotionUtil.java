package de.photon.aacadditionpro.util.potion;


import de.photon.aacadditionpro.ServerVersion;
import de.photon.aacadditionpro.util.exceptions.UnknownMinecraftVersion;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Contains util methods regarding the {@link org.bukkit.potion.PotionEffect}s and {@link org.bukkit.potion.PotionEffectType}s.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PotionUtil
{
    /**
     * Gets a {@link PotionEffect} of a {@link LivingEntity}.
     *
     * @param livingEntity the {@link LivingEntity} which should be tested
     * @param type         the {@link PotionEffectType} which should be tested for
     *
     * @return the {@link PotionEffect} with the provided {@link PotionEffectType} or null if the {@link LivingEntity}
     * doesn't have such a {@link PotionEffect}.
     */
    public static PotionEffect getPotionEffect(final LivingEntity livingEntity, final InternalPotionEffectType type)
    {
        if (!type.isAvailable()) {
            return null;
        }

        final PotionEffectType mapping = type.getMapping();
        switch (ServerVersion.getActiveServerVersion()) {
            case MC188:
                for (final PotionEffect effect : livingEntity.getActivePotionEffects()) {
                    if (effect.getType().equals(mapping)) {
                        return effect;
                    }
                }
                return null;
            case MC112:
            case MC113:
            case MC114:
            case MC115:
            case MC116:
                return livingEntity.getPotionEffect(mapping);
            default:
                throw new UnknownMinecraftVersion();
        }
    }

    /**
     * Checks if a {@link LivingEntity} has a certain {@link PotionEffectType}
     *
     * @param livingEntity the {@link LivingEntity} which should be tested
     * @param type         the {@link PotionEffectType} which should be tested for
     *
     * @return true if the {@link PotionEffectType} is found, else false.
     */
    public static boolean hasPotionEffect(final LivingEntity livingEntity, final InternalPotionEffectType type)
    {
        return type.isAvailable() && livingEntity.hasPotionEffect(type.getMapping());
    }

    /**
     * Used to get the Amplifier of a {@link PotionEffect}.
     *
     * @param effect the effect which should be tested.
     *
     * @return the amplifier of the {@link PotionEffect} or null if the player doesn't have it.
     */
    public static Integer getAmplifier(final PotionEffect effect)
    {
        return effect == null ?
               null :
               effect.getAmplifier();
    }
}
