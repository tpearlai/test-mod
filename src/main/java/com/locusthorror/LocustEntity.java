package com.locusthorror;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import java.util.List;

/** Server-authoritative prototype: stealth -> stalk -> scare -> hunt. */
public class LocustEntity extends Monster {
 public enum State { DORMANT, STALK, HAUNT, CHASE, RETREAT }
 private State state=State.STALK;
 private int stateTicks=0, soundCooldown=0, doorCooldown=0, scareCooldown=0;
 private boolean chasing=false;
 public LocustEntity(EntityType<? extends Mob> type,Level level){super(type,level);this.xpReward=0;}
 @Override protected void registerGoals(){
  goalSelector.addGoal(0,new FloatGoal(this));
  goalSelector.addGoal(2,new MeleeAttackGoal(this,1.25,true));
  goalSelector.addGoal(5,new RandomStrollGoal(this,0.8));
  goalSelector.addGoal(6,new LookAtPlayerGoal(this,Player.class,40));
  // Target is assigned by state machine only during CHASE.
 }
 @Override public void tick(){super.tick();if(level().isClientSide)return;
  stateTicks++;if(soundCooldown>0)soundCooldown--;if(doorCooldown>0)doorCooldown--;if(scareCooldown>0)scareCooldown--;
  Player p=level().getNearestPlayer(this,48);if(p==null||p.isCreative()||p.isSpectator()){chasing=false;state=State.STALK;setTarget(null);return;}
  double dist=distanceTo(p);boolean looking=isLookingAt(p);
  // Only escalate to chase at close range, or after a prolonged encounter.
  if(state==State.STALK && looking && dist<26){state=State.HAUNT;stateTicks=0;}
  if(state==State.HAUNT && dist<5 && scareCooldown==0){play("impact",1.0f,0.8f);play("teleport",0.8f,1f);scareCooldown=240;state=State.RETREAT;stateTicks=0;teleportAway(p);setTarget(null);return;}
  if(state==State.HAUNT && stateTicks>180){state=State.CHASE;stateTicks=0;}
  if(state==State.RETREAT && stateTicks>140){state=State.STALK;stateTicks=0;}
  if(state==State.CHASE){setTarget(p);if(!chasing){play("chase_music",1f,1f);chasing=true;} if(dist>56){state=State.RETREAT;chasing=false;setTarget(null);}}
  else {chasing=false;setTarget(null);getNavigation().stop();}
  if(state==State.STALK && soundCooldown==0 && dist<30){play("paranoia_ambient",0.45f,1f);soundCooldown=240;}
  if(state==State.HAUNT && soundCooldown==0){play("breathing",0.7f,1f);soundCooldown=100;}
  // Door knocking when the player is behind a nearby door. Does not break protected blocks.
  if((state==State.HAUNT||state==State.CHASE) && doorCooldown==0){
   BlockPos bp=blockPosition();boolean door=false;
   for(BlockPos test:BlockPos.betweenClosed(bp.offset(-2,-1,-2),bp.offset(2,2,2))){BlockState bs=level().getBlockState(test);if(bs.getBlock() instanceof DoorBlock){door=true;break;}}
   if(door){play("door_knock",1f,0.85f);doorCooldown=120;}
  }
  // Boats cannot capture it: dismount if external mods force it into a vehicle.
  if(getVehicle() instanceof Boat)stopRiding();
 }
 private boolean isLookingAt(Player p){Vec3 to=position().add(0,getEyeHeight(),0).subtract(p.getEyePosition());if(to.lengthSqr()<0.01)return true;return p.getLookAngle().normalize().dot(to.normalize())>0.90 && p.hasLineOfSight(this);}
 private void teleportAway(Player p){for(int i=0;i<12;i++){double a=random.nextDouble()*Math.PI*2;double r=18+random.nextDouble()*12;double x=p.getX()+Math.cos(a)*r,z=p.getZ()+Math.sin(a)*r;BlockPos bp=BlockPos.containing(x,p.getY(),z);if(level().getBlockState(bp).isAir()&&level().getBlockState(bp.above()).isAir()&&level().getBlockState(bp.below()).isSolidRender(level(),bp.below())){teleportTo(x,p.getY(),z);return;}}}
 private void play(String name,float volume,float pitch){playSound(SoundEvent.createVariableRangeEvent(new ResourceLocation(LocustHorror.MODID,name)),volume,pitch);}
 @Override public MobType getMobType(){return MobType.UNDEFINED;}
}
