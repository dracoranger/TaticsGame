import scala.collection.mutable
import scala.util.random
import elem.scala

//holds tatics game runner

class World(){

  }

  class Hex(height:Int, type:Int, here:Elem){
    def this(height:Int, type:Int){
      this(height:Int,type:Int, here:new Empt())
    }
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

  class Fight(a:Actor,b:Actor){
    val rand=new Random()
    val att=a
    val def=b
    val roll=rand.nextFloat()
    var multMod=0.0//? need to figure out how to apply proper modifiers, thinking (mult*base)+add
    var addMod=0.0
    var damMultMod=0.0
    var damAddMod=0.0
    def getBaseHitChance(a:Actor,b:Actor):Double=a.getEmpathy(b)*(a.getHitAbil()-b.getDodgeAbil())
    def getHitDamage(a:Actor,b:Actor)=a.getWeaponDam()//-b.getArmorRes()//TODO Implement armor
    def getMods(s:State)={
      multMod=s.getMultMods(att)
      addMod=s.getAddMods(att)
      damMultMod=s.getDamMult(att)
      damAddMod=s.getDamAdd(att)
    }
    def calculateHit():Double= multMod*getBaseHitChance(att,def)+addMod
    def doesHit():Boolean= if(calculateHit()>roll) true else false
    def calculateDamage():Double={
      val dam=damMultMod*getHitDamage(att,def)+damAddMod
      if(doesHit()){
        if(getHitDamage(att,def)<0)return dam
        else if(dam<0)return 0.0
        else dam
      }
      else{
        0.0
      }
    }

    }
  }


  class Move(a:Actor){

  }

  class Modifiers(state:State){
    //accur perc,accur num, dam perc, dam num
    val mods=Array(("Lunge",.1,0.0,.25,0.0,false),("Parry",0.25,0.0,0.0,0.0,false),("Blocked",-0.40,0.0,0.0,0.0,false),
    ("Broken Arm",-0.25,0.0,-0.25,0.0,false),("Exausted",-0.1,0.0,-0.1,0.0,false),
    ("Stunned",-0.4,0.0,0.0,0.0,false),("Concussed",-0.5,0.0,0.0,0.0,false),("Blinded",-0.75,0.0,0.0,0.0,false),
    ("Bloodloss",0.0,0.0,0.0,0.0,false),("Weap",0.0,0.0,0.0,0.0,false),("Movem",0.0,0.0,0.0,0.0,false))
    val specials=Array(("Sprinting",false),("Adrenaline",false), ("Angry",false),("Broken Leg",false),("Bloodloss",false),("Slowed",false))
    val mun=Array(("Hunger",false),("Thirst",false))
    val mag=Array(("Mana"),false),("Entropy",false)

    //bloodloss
    var leakRate=0
    var loss=0
    //stores where the boolean is for mods and specials
    final val EnabMod=6
    final val EnabSpec=2
    def incrimentBloodLoss():Unit=loss+=leakRate
    def calculateBloodLoss():[(String,Double,Double,Double,Double,Boolean)]={
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
              var temp=i._j<0
              if(specials(1)._EnabSpec) temp=temp*.5
              if(specials(2)._EnabSpec) temp=temp*.25
              stor._(j-2)+=temp*.25

            }
            else(i._j>0) stor._(j+4-2)+=i._j
          }
        }
      }
      var ret=(0.0,0.0,0.0,0.0)
      for(i<-0 to (stor.length-1)/2)ret._i+=(stor._i+stor._(stor.length-1)/2))
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
    for(i<-actors)
  }
