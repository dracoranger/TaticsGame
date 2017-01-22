//Holds actors, ai, and equipment
package elem

import scala.collection.mutable
import scala.util.Random


class Elem(){
  def isEmpty= false
}

class Empt() extends Elem{
  override def isEmpty=true
}

class Actor() extends Elem{
  val name="Dave Fault"
  val empathy=Map[Actor,Double]()
  val kills=Map[Actor,Int]()
  val isAlly=Map[Actor,Boolean]()
  val isEnemy=Map[Actor,Boolean]()
  val defaultWeapon=new Weapon()
  var weapon=defaultWeapon
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
  def getHealth():Double=health
  def getSpeed():Double=speed
  def getStrength():Double=strength
  def getEndurance():Double=endurance
  def getEmpathy(act:Actor)=empathy(act)
  //def getHitAbil(state:State)=calculateHitAbil(state)
  //def getDodgeAbil()= ???
  def getAI():Int= ???;//aiType
  def getWeaponDam=weapon.getDamage()

  def updateHealth(delta:Int)={
    health=health-delta
    if(health<1) alive=false
  }
  def updateEmpathy()={
    ???
  }
  /*
  * TODO: make this logical
  * Need to figure out how much needs to be taken care of in state versus here
  *
  def calculateHitAbil(state:State):Double={
    ???
    //if(weapon.isMelee()) speed*(state.force-strength)+skill state.targetDistance weapon.distance
  }
  def calculateDistanceEffect

  def calculateDodgeAbil(state:State):Double={
    ???
  }
  */
}

class PhPerson() extends Actor{
???
}

class PhAnimal extends Actor{
???
}

class AI(){
???
}
class Tanker() extends AI{
???
}
//Aggressive charger
class Agger() extends AI{
???
}
class Fleer() extends Agger{
???
}
class Sniper() extends AI{
???
}


/*
* Modifies how capable the actor is of dodging, also used to tell if asleep, terrified, or similar
*  ? Should create alertlevels?
*/
class AlertLevel(lvl:Int){

}

class Pickup extends Elem{

}

class Weapon() extends Pickup{
  var name="Name"
  var weight=0.0
  var damage=0.0
  var range=0.0
  var accuracy=0.0
  val weap=new Array[(String,Double,Double,Double,Double)](12)
  weap(0)=("Name",1.0,1.0,1.0,1.0)//Name, damage, weight, accuracy, reach


  def getWeight():Double= weight
  def getDamage():Double= damage
  def getRange():Double= range
  def getAcc():Double= accuracy


  def lookup(num:Int, weapo:Array[(String,Double,Double,Double,Double)]):Unit={
    var temp=weapo(num)
    name=temp._1
    damage=temp._2
    weight=temp._3
    accuracy=temp._4
    range=temp._5
  }
  /*
  def lookup(weapo:Array[(String,Double,Double,Double,Double)]):Unit={
    name=weapo(1)
    weight=weapo(3)
    damage=weapo(2)
    range=weapo(5)
    accuracy=weapo(4)
  }
  */
}



class Melee(num:Int) extends Weapon{
  //Range from 0-3
  val isMel=true
  val items=List( ("Shiv",1.0,1.0,1.0,1.0),("Bat",1.0,1.0,1.0,2.0),("Nail Board",1.0,1.0,1.0,3.0),
    ("Pocket",1.0,1.0,1.0,1.0),("Machette",1.0,1.0,1.0,2.0),("Spear",1.0,1.0,1.0,3.0),
    ("Fighting",1.0,1.0,1.0,1.0),("Spring",1.0,1.0,1.0,1.0),("Saber",1.0,1.0,1.0,2.0),
    ("Mace",1.0,1.0,1.0,2.0),("Harpoon",1.0,1.0,1.0,3.0),("Halberd",1.0,1.0,1.0,1.0))
  var x=0
  for(i<-items){
      weap(x)=i
      x= x+1
  }





  def isMelee():Boolean=isMelee
}

class Ranged(num:Int) extends Weapon{
  //
  val isMel=false
  var ammo=0
  val speed=0.0
  val items=List(("PPistol",1.0,1.0,1.0,1.0),("PShot",1.0,1.0,1.0,2.0),("PRifle",1.0,1.0,1.0,3.0),
  ("Target",1.0,1.0,1.0,1.0),("20 Gague",1.0,1.0,1.0,2.0),("Bolt",1.0,1.0,1.0,3.0),
  ("9 mil",1.0,1.0,1.0,1.0),("M1911",1.0,1.0,1.0,1.0),("Slug",1.0,1.0,1.0,2.0),
  ("12 Gague",1.0,1.0,1.0,2.0),("Semi",1.0,1.0,1.0,3.0),("Scoped",1.0,1.0,1.0,1.0))

  var x=0
  for(i<-items){
      weap(x)=i
      x= x+1
  }

  def isMelee():Boolean= isMelee
  def getAmmo:Int= ammo
  def getSpeed():Double= speed
  //def getAcc():Double= spread
}
