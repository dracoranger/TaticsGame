package skrim

import scala.collection.mutable
import scala.util.Random
import elem._

//Worried about the number of created empts
class Hex(heigh:Int, ty:Int, here:Elem, x:Int, y:Int){
  var located=here
  var typ=ty//new Environment(typ)
  var height=heigh
  var locl=(x,y)
  def isEmpty= located.isEmpty

  def this(height:Int, typ:Int){
    this(height,typ,new Empt(),1,1)
  }

  def this()=this(0,0,new Empt(),1,1)

  def moveElem():Elem={
    var ret=located
    located=new Empt()
    ret
  }
  def placeElem(elem:Elem):Boolean={
    if(isEmpty){
      located=elem
      true
    }
    else{
      false
    }
  }

}

//holds tatics game runner
/*
*TODO: finalize hexGenrator
*/
class World(size:Int, typ:Int)
{
  //size is x by x at least for testing
  val rand=new Random()
  val map=hexGenerator(size,typ)

  def hexGenerator(size:Int,typ:Int):Array[Array[Hex]]=
  {
    //0 Flat
    //1 -1 - 2 slope up
    //2 -2 - 1 slope down
    //3 -2 - 2 hilly
    //4 -5 - 5 extreme
    //need to figure out general trends DONE
    var ma=0
    var mi=0
    var inc=0
    if(typ==1)
    {
      mi= -1
      ma=2
      inc= 1
    }
    else if(typ==2)
    {
      mi= -2
      ma=1
      inc= -1
    }
    else if(typ==3)
    {
      mi= -2
      ma=2
      inc= 0
    }
    else if(typ==4)
    {
      mi= -5
      ma=5
      inc= 0
    }
    val ret=Array.fill(size,size)(new Hex()) //empty
    for (i<-1 to size*size)
    {
      if(i%size==0)
      {
        mi+= inc
        ma+= inc
      }
      ret(i/size,i%size)=new Hex(rand.nextInt(mi,ma),0,i/size,i%size)
    }
    ret
  }
    //val grid=Array.fill(Array.fill(10,new Hex()))

  def move(from:(Int,Int),toward:(Int,Int)):Unit=
  {
      ???
  }


}

class Environment(numed:Int){
//On fire
//Water


}

/*
  TODO:Create inventory, act, magic, etc.
*/
class Action(a:Actor,b:Actor){
  val fight=new Fight(a:Actor,b:Actor)
  def handleInput(in:Int):Unit={
    ???
  }
  def fightInfo():List[Double]={
    ???
  }
  def fightEnem():Unit={
  ???
  }
}

class Fight(a:Actor,b:Actor)
{
  val rand=new Random()
  val attac=a
  val defen=b
  val roll=rand.nextFloat()
  var multMod=0.0//? need to figure out how to apply proper modifiers, thinking (mult*base)+add
  var addMod=0.0
  var damMultMod=0.0
  var damAddMod=0.0
  def getBaseHitChance(at:Actor,be:Actor):Double=at.getEmpathy(be)*(at.getHitAbil()-be.getDodgeAbil())
  def getHitDamage(a:Actor,b:Actor)=a.getWeaponDam()//-b.getArmorRes()//TODO Implement armor
  def getMods(s:State)=
  {
    multMod=s.getMultMods(attac)
    addMod=s.getAddMods(attac)
    damMultMod=s.getDamMult(attac)
    damAddMod=s.getDamAdd(attac)
  }
  def calculateHit():Double=
  {
    var ret= multMod * getBaseHitChance(attac, defen) +addMod
    ret
  }
  def doesHit():Boolean= if(calculateHit()>roll) true else false
  def calculateDamage():Double=
  {
    val dam=damMultMod*getHitDamage(attac,defen)+damAddMod
    if(doesHit())
    {
      if(getHitDamage(attac,defen)<0)return dam
      else if(dam<0)return 0.0
      else dam
    }
    else
    {
      0.0
    }
  }

}



class Move(a:Actor){
???
}

class Modifiers(state:State){
  //accur perc,accur num, dam perc, dam num
  val mods=Array(("Lunge",.1,0.0,.25,0.0,false),("Parry",0.25,0.0,0.0,0.0,false),("Blocked",-0.40,0.0,0.0,0.0,false),
  ("Broken Arm",-0.25,0.0,-0.25,0.0,false),("Exausted",-0.1,0.0,-0.1,0.0,false),
  ("Stunned",-0.4,0.0,0.0,0.0,false),("Concussed",-0.5,0.0,0.0,0.0,false),("Blinded",-0.75,0.0,0.0,0.0,false),
  ("Bloodloss",0.0,0.0,0.0,0.0,false),("Weap",0.0,0.0,0.0,0.0,false),("Movem",0.0,0.0,0.0,0.0,false))
  val specials=Array(("Sprinting",false),("Adrenaline",false), ("Angry",false),("Broken Leg",false),("Bloodloss",false),("Slowed",false))
  val mun=Array(("Hunger",false),("Thirst",false))
  val mag=Array(("Mana",false),("Entropy",false))

  //bloodloss
  var leakRate=0
  var loss=0
  //stores where the boolean is for mods and specials
  final val EnabMod=6
  final val EnabSpec=2
  def incrimentBloodLoss():Unit=loss+=leakRate
  def calculateBloodLoss():(String,Double,Double,Double,Double,Boolean)={
    if(loss>90) ("Bloodloss",-0.9,0.0,0.0,0.0,mods(9).EnabMod)
    else if(loss>80) ("Bloodloss",-0.75,0.0,0.0,0.0,true)
    else if(loss>70) ("Bloodloss",-0.60,0.0,0.0,0.0,true)
    else if(loss>60) ("Bloodloss",-0.45,0.0,0.0,0.0,true)
    else if(loss>50) ("Bloodloss",-0.30,0.0,0.0,0.0,true)
    else if(loss>40) ("Bloodloss",-0.20,0.0,0.0,0.0,true)
    else if(loss>30) ("Bloodloss",-0.15,0.0,0.0,0.0,true)
    else if(loss>20) ("Bloodloss",-0.10,0.0,0.0,0.0,true)
  }
  def modBloodLoss():Int={
    if(loss>90) 1
    else if(loss>80) 2
    else if(loss>70) 3
    else if(loss>60) 4
    else if(loss>50) 5
    else if(loss>40) 6
    else if(loss>30) 7
    else if(loss>20) 8
    else if(loss>10&&specials(2)._EnabSpec) 11
    else if(specials(5).EnabSpec&&specials(2).EnabSpec) 12
    else if(loss>10&&specials(6)._2) 13
    else if(specials(5).EnabSpec&&specials(6).EnabSpec) 14
    else if(loss>10) 9
    else if(specials(5).EnabSpec) 10
    else 0
  }

  def update():Unit={
    mods(8)=calculateBloodLoss()
    mods(9)=state.calculateWeap()
    mods(10)=state.calculateMovem()
  }

  def totaledMods():Double={
    //neg(accur perc,accur num, dam perc, dam num)pos(accur perc,accur num, dam perc, dam num)
    //arrays 0
    // tuple ._1
    update()
    var stor=(0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0)

    for(i<-mods){
      if(i._EnabMod){
        for(j<-2 to i.length-1){
          if (i._j<0){
            var temp=i._j
            var locl=j-2
            if(specials(1)._EnabSpec) temp=temp*.5
            if(specials(2)._EnabSpec) temp=temp*.25
            stor._locl +=temp*.25

          }
          else(i._j>0){
            var locl=j+4-2
            stor._locl +=i._j
          }
        }
      }
    }
    var ret=(0.0,0.0,0.0,0.0)
    for(i<-0 to (stor.length-1)/2){
      var add=((stor.length-1)/2)+i
      ret._i+= stor._i + stor._add//Might need to be 1
    }
    ret
  }
}
/*
  A state is poorly defined
  right now it is being used as a relationship between two actors
  and as the definition of a World
  should establish it.
*/

class State(actor:Actor, target:Actor){
  var force=0
  var location=locl
  val move=new Move(a:Actor)

  val modif= new Modifiers()
}


/*
TODO:completely redo
*/
class Combat(a:Actor,b:Actor){
  for(i<-actors) ???
}
