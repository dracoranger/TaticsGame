//Holds actors, ai, and equipment
import scala.collection.mutable
import scala.util.random

class Elem(){
  def isEmpty= false
}

class Empt extends Elem{
  def isEmpty=true
}

class Actor() extends Elem{
  val name="Dave Fault"
  val empathy=Map[actor,double]()
  val kills=Map[actor,int]()
  val isAlly=Map[actor,Boolean]()
  val isEnemy=Map[actor,Boolean]()
  var weapon=defaultWeapon
  val defaultWeapon=new Weapon()
  val isPC=false
  val ai=new AI()


  //Stats
  var alive=true
  var health=100.0
  var persSpeed=50.0
  var speed=persSpeed-weapon.getWeight
  var strength=1.0
  var endurance=1.0
  var awareness=new AlertLevel(3)

  def isAlive():Boolean=alive
  def getHealth():Int=health
  def getSpeed():Int=speed
  def getStrength():Int=strength
  def getEndurance():Int=endurance
  def getEmpathy(act:Actor)=empathy(act)
  def getHitAbil(state:State)=calculateHitAbil(state)
  def getDodgeAbil()=???
  def getAI():Int=aiType
  def getWeaponDam=weapon.getDamage()

  def updateHealth(delta:Int)={
    health=health-delta
    if(health<1) isAlive=false
  }
  def updateEmpathy()={
    ???
  }
  /*
  * TODO: make this logical
  * Need to figure out how much needs to be taken care of in state versus here
  */
  def calculateHitAbil(state:State):Double={
    ???
    //if(weapon.isMelee()) speed*(state.force-strength)/*+skill*/state.targetDistance weapon.distance
  }
  //def calculateDistanceEffect
+
  def calculateDodgeAbil(state:State):Double={
    ???
  }

}

class PhPerson() extends Actor{

}

class PhAnimal extends Actor{

}

class AI(){

}
class Tanker() extends AI{

}
//Aggressive charger
class Agger() extends AI{

}
class Fleer() extends Agger{

}
class Sniper() extends AI{

}


/*
* Modifies how capable the actor is of dodging, also used to tell if asleep, terrified, or similar
*  ? Should create alertlevels?
*/
class AlertLevel(){

}

class Pickup extends Elem{

}

class Weapon() extends Pickup{
  val weight=0.0
  val damage=0.0
  val range=0.0

  def getWeight():Double= weight
  def getDamage():Double= damage
  def getRange():Double= range
}



class Melee extends Weapon{
  //Range from 0-3
  val isMelee=true

  def isMelee():Boolean=isMelee
}

class Ranged extends Weapon{
  //
  val isMelee=false
  var ammo=0
  val speed=0.0
  val spread=0.0

  def isMelee():Boolean= isMelee
  def getAmmo:Int= ammo
  def getSpeed():Double= speed
  def getSpread():Double= spread
}

class
