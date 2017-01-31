//Holds actors, ai, and equipment
package elem

import scala.collection.mutable
import scala.util.Random


class Elem(){
  def isEmpty()= false
  def canOverlap()=true
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
  override def canOverlap()=true


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
  def pickup(item:Pickup)={
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

class Modifier(in:Int){
  val n=Array("Lunge","Parry","Blocked","Broken Arm","Exausted","Stunned","Concussed","Blinded","Bloodloss","Weap","Movem","Sprinting","Adrenaline","Angry","Broken Leg","Bloodloss","Slowed","Hunger","Thirst","Mana","Entropy")
  val ap=Array(-.1, 0.25, -0.40,-0.25,  -0.1, -0.4, -0.5, -0.75, 0.0, 0.0, 0.0, 0.0, 0.0,  0.0,  0.0,    0.0,  0.0,  0.0,  0.0,   0.0, 0.0)
  val an=Array(0.0, 0.0,  0.0,  0.0,    0.0,  0.0,  0.0,  0.0,   0.0, 0.0, 0.0, 0.0, 0.0,  0.0,  0.0,    0.0,  0.0,  0.0,  0.0,   0.0, 0.0)
  val dp=Array(.25, 0.0,  0.0,  -0.25,  -0.1, 0.0,  0.0,  0.0,   0.0, 0.0, 0.0, 0.0, 0.0,  0.0,  0.0,    0.0,  0.0,  0.0,  0.0,   0.0, 0.0)
  val dn=Array(0.0, 0.0,  0.0,  0.0,    0.0,  0.0,  0.0,  0.0,   0.0, 0.0, 0.0, 0.0, 0.0,  0.0,  0.0,    0.0,  0.0,  0.0,  0.0,   0.0, 0.0)
  val en=Array(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false)

  var name=n(in)
  var accPerc=ap(in)
  var accNum=an(in)
  var damPerc=dp(in)
  var damNum=dn(in)
  var enabled=en(in)

  def getName()=name
  def getAccPerc()=accPerc
  def getAccNum()=accNum
  def getDamPerc()=damPerc
  def getDamNum()=damNum
  def getEnabled()=enabled

  def updateBloodLoss(loss:Int):Unit={
    if(in==8){
      if(loss>90) {
        accPerc= -.9
        enabled= true
      }
      else if(loss>80){
        accPerc= -.75
        enabled= true
      }
      else if(loss>70){
        accPerc= -.6
        enabled= true
      }
      else if(loss>60){
        accPerc= -.45
        enabled= true
      }
      else if(loss>50){
        accPerc= -.3
        enabled= true
      }
      else if(loss>40){
        accPerc= -.2
        enabled= true
      }
      else if(loss>30){
        accPerc= -.15
        enabled= true
      }
      else if(loss>20){
        accPerc= -.1
        enabled= true
      }
      else{
        accPerc= 0.0
        enabled= false
      }
    }
  }

}

class Modifiers(){
  //accur perc,accur num, dam perc, dam num
  val mods=Array(new Modifier(0),new Modifier(1),new Modifier(2),new Modifier(3),new Modifier(4),new Modifier(5),new Modifier(6),new Modifier(7),new Modifier(8))
  val specials=Array(new Modifier(9),new Modifier(10),new Modifier(11),new Modifier(12),new Modifier(13),new Modifier(14),new Modifier(15),new Modifier(16))
  val mun=Array(new Modifier(17),new Modifier(18))
  val mag=Array(new Modifier(19),new Modifier(20))

  //bloodloss
  var leakRate=0
  var loss=0
  //stores where the boolean is for mods and specials
  final val enabMod=6
  final val enabSpec=2
  def incrimentBloodLoss():Unit=loss+=leakRate
  def modBloodLoss():Int={
    if(loss>90) 1
    else if(loss>80) 2
    else if(loss>70) 3
    else if(loss>60) 4
    else if(loss>50) 5
    else if(loss>40) 6
    else if(loss>30) 7
    else if(loss>20) 8
    else if(loss>10&&specials(2).getEnabled()) 11
    else if(specials(5).getEnabled()&&specials(2).getEnabled()) 12
    else if(loss>10&&specials(6).getEnabled()) 13
    else if(specials(5).getEnabled()&&specials(6).getEnabled()) 14
    else if(loss>10) 9
    else if(specials(5).getEnabled()) 10
    else 0
  }

  def update():Unit={
    mods(8).updateBloodLoss(loss)
    //mods(9)=state.calculateWeap()
    //mods(10)=state.calculateMovem()
  }

  def totaledMods():Array[Double]=
  {
    //neg(accur perc,accur num, dam perc, dam num)pos(accur perc,accur num, dam perc, dam num)
    //arrays 0
    // tuple ._1
    update()
    var stor=Array(1.0,0.0,1.0,0.0)//,0.0,0.0,0.0,0.0)
    var num=0
    for(i<-mods)
    {
      if(i.getEnabled())
      {//TODO: use for comprihensions
        stor(0) *=
        {
          var temp=i.getAccPerc
          if(i.getAccPerc()<0&&specials(12).getEnabled) temp=.5*temp
          if(i.getAccPerc()<0&&specials(13).getEnabled) temp=.25*temp
          temp
        }
        stor(1) +=
        {
          i.getAccNum()
        }
        stor(2) *=
        {
          var temp=i.getDamPerc
          if(i.getDamPerc()<0&&specials(12).getEnabled) temp=.5*temp
          if(i.getDamPerc()<0&&specials(13).getEnabled) temp=.25*temp
          temp
        }
        stor(3) +=
        {
          i.getDamNum()
        }
      }
    }
    stor
  }
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
