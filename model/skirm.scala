package skirm

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
      ret(i/size)(i%size)=new Hex(rand.nextInt(ma)+mi,0,new Empt(),i/size,i%size)
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
  //Not drawn from here, i think
  //def getBaseHitChance(at:Actor,be:Actor):Double=at.getEmpathy(be)*(at.getHitAbil-be.getDodgeAbil)
  def getHitDamage(a:Actor,b:Actor)=a.getWeaponDam//-b.getArmorRes()//TODO Implement armor
  def getMods(s:State)=
  {
    //Done in Modifiers
    ???
    //multMod=s.getMultMods(attac)
    //addMod=s.getAddMods(attac)
    //damMultMod=s.getDamMult(attac)
    //damAddMod=s.getDamAdd(attac)
  }
  def calculateHit():Double=
  {
    var ret= multMod //* getBaseHitChance(attac, defen) +addMod
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
  A state is poorly defined
  right now it is being used as a relationship between two actors
  and as the definition of a World
  should establish it.
*/

class State(actor:Actor, target:Actor){
  //var force=0
  //var location=
  //val move=new Move(a:Actor)

  //val modif= new Modifiers()
}


/*
TODO:completely redo
*/
class Combat(a:Actor,b:Actor){
  //for(i<-actors) ???
}
